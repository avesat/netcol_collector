package com.training.netcol_collector.model;

import lombok.Data;

@Data
public class HostRequest {
    private String addr;
    private String user;
    private String password;
}
