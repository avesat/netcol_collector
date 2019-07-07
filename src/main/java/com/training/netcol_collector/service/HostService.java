package com.training.netcol_collector.service;

import com.google.gson.Gson;
import com.google.protobuf.util.JsonFormat;
import com.training.netcol_collector.dao.inmemory.InMemoryHostDao;
import com.training.netcol_collector.model.*;
import com.training.proto.gen.HostProto;
import com.training.netcol_collector.task.HostTask;
import com.training.netcol_collector.task.TaskExecutor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HostService {
    @Autowired
    @Getter
    private TaskExecutor taskExecutor;
    @Autowired
    private /*HostDao*/InMemoryHostDao hostDao;
    @Autowired
    private IpInterfaceService ipInterfaceService;
    @Autowired
    private IpRouteService ipRouteService;

    public HostResponse getHostInterfaceRoute(HostRequest hostReq) {
        HostTask hostTask = new HostTask(hostReq, this);
        taskExecutor.executeTask(hostTask);

        HostResponse resp = new HostResponse();
        resp.setId(hostTask.getId());
        resp.setStatus(hostTask.getStatus());

        return resp;
    }

    public void save(Host host) {
        try {
            for (IpInterface intf: host.getIpInterfaces()) {
                ipInterfaceService.save(intf);
            }
            for (IpRoute route: host.getIpRoutes()) {
                ipRouteService.save(route);
            }
            hostDao.save(host);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Host> getAll() {
        return StreamSupport.stream(hostDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Host getById(Long id) {
        return hostDao.findById(id).get();
    }

    public Host getByRequestId(Long id) {
        return hostDao.findByRequestId(id).get();
    }

    public void remove(Host host) {
        hostDao.delete(host);
    }

    //TODO: check for null Exception in case if some field equals to null
    public HostProto.Host protoSerializeById(Long id) {
        HostProto.Host.Builder hostProto = HostProto.Host.newBuilder();

        //TODO: Is it possible copy Host model directly to protoHost?
        for (Host host : hostDao.findAll()) {
            if (host.getRequestId() == id) {
                hostProto
                        .setId(host.getId())
                        .setRequestId(host.getRequestId())
                        .setIpAddress(host.getIpAddress())
                        .setUser(host.getUser())
                        .setPassword(host.getPassword());

                for (IpInterface intf : host.getIpInterfaces()) {
                    hostProto.addIpInterfaces(HostProto.Host.IpInterface.newBuilder()
                            .setId(intf.getId())
                            .setName(intf.getName())
                            .setMacAddress(intf.getMacAddress())
                            .setIpAddress(HostProto.Host.IpAddress.newBuilder()
                                    .setId(intf.getIpAddress().getId())
                                    .setAddr(intf.getIpAddress().getAddr())
                                    .setNetmask(intf.getIpAddress().getNetmask())
                                    .build())
                            .setMtu(intf.getMtu())
                            .build()
                    );
                }

                for (IpRoute route : host.getIpRoutes()) {
                    /* Find interface which was added to hostProto */
                    HostProto.Host.IpInterface ipInterface = null;
                    for (HostProto.Host.IpInterface intf : hostProto.getIpInterfacesList()) {
                        if (intf.getId() == route.getIface().getId()) {
                            ipInterface = intf;
                        }
                    }
                    if (ipInterface == null) {
                        ipInterface = HostProto.Host.IpInterface.newBuilder().build();
                    }

                    hostProto.addIpRoutes(HostProto.Host.IpRoute.newBuilder()
                            .setId(route.getId())
                            .setDestination(route.getDestination())
                            .setGateway(route.getGateway())
                            .setGenmask(route.getGenmask())
                            .setFlags(route.getFlags())
                            .setMetric(route.getMetric())
                            .setRefs(route.getRefs())
                            .setIface(ipInterface)
                            .build()
                    );
                }
                break;
            }
        }

        return hostProto.build();
    }

    public HostProto.Host protoSerializeViaJsonById(Long id) {
        HostProto.Host.Builder hostProto = HostProto.Host.newBuilder();

        Host host = getByRequestId(id);
        try {
            JsonFormat.parser().merge((new Gson()).toJson(host), hostProto);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return hostProto.build();
    }

    public Host protoDeserialize(HostProto.Host hostProto) {
        JsonFormat.Printer jsonFormat = JsonFormat.printer();
        String hostProtoJson = null;

        try {
            hostProtoJson = jsonFormat.print(hostProto);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return (new Gson()).fromJson(hostProtoJson, Host.class);
    }
}
