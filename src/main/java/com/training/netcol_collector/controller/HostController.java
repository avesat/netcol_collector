package com.training.netcol_collector.controller;

import com.training.netcol_collector.model.Host;
import com.training.netcol_collector.model.HostRequest;
import com.training.netcol_collector.model.HostResponse;
import com.training.netcol_collector.service.HostService;

import com.training.proto.gen.HostProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HostController {
    @Autowired
    private HostService hostService;

    @RequestMapping(value = "/host", method = RequestMethod.POST)
    public HostResponse addRoute(@RequestBody HostRequest hostReq) {
        return hostService.getHostInterfaceRoute(hostReq);
    }

    @RequestMapping(value = "/host/all", method = RequestMethod.GET)
    public List<Host> getAllHosts() {
        return hostService.getAll();
    }

    @RequestMapping(value = "/host/{id}", method = RequestMethod.GET)
    public HostProto.Host getHost(@PathVariable("id") long hostId) {
        return hostService.protoSerializeViaJsonById(hostId);
    }
}
