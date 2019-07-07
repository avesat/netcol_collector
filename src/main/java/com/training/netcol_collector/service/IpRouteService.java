package com.training.netcol_collector.service;

import com.training.netcol_collector.dao.inmemory.InMemoryIpRouteDao;
import com.training.netcol_collector.model.IpRoute;
import com.training.netcol_collector.model.RouteCmd;
import com.training.netcol_collector.model.RouteCmdRespose;
import com.training.netcol_collector.task.IpRouteCmdTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class IpRouteService {
    @Autowired
    private /*IpRouteDao*/InMemoryIpRouteDao ipRouteDao;

    public void save(IpRoute ipRoute) {
        try {
            ipRouteDao.save(ipRoute);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<IpRoute> getAll() {
        return StreamSupport.stream(ipRouteDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public RouteCmdRespose addRemoteHostRoute(RouteCmd routeCmd) {
        IpRouteCmdTask ipRouteCmdTask = new IpRouteCmdTask(routeCmd, this);
        ipRouteCmdTask.run();

        RouteCmdRespose resp = new RouteCmdRespose();
        resp.setStatus(ipRouteCmdTask.getStatus());

        return resp;
    }
}
