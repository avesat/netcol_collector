package com.training.netcol_collector.service;

import com.training.netcol_collector.dao.inmemory.InMemoryIpAddressDao;
import com.training.netcol_collector.model.IpAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpAddressService {
    @Autowired
    private /*IpAddressDao*/InMemoryIpAddressDao ipAddressDao;

    public void save(IpAddress ipAddress) {
        try {
            ipAddressDao.save(ipAddress);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
