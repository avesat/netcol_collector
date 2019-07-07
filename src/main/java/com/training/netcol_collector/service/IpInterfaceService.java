package com.training.netcol_collector.service;

import com.training.netcol_collector.dao.inmemory.InMemoryIpInterfaceDao;
import com.training.netcol_collector.model.IpInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class IpInterfaceService {
    @Autowired
    private /*IpInterfaceDao*/InMemoryIpInterfaceDao ipInterfaceDao;
    @Autowired
    private IpAddressService ipAddressService;

    public void save(IpInterface ipInterface) {
        try {
            ipAddressService.save(ipInterface.getIpAddress());
            ipInterfaceDao.save(ipInterface);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<IpInterface> getAll() {
        return StreamSupport.stream(ipInterfaceDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
