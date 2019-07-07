package com.training.netcol_collector.task;

import com.training.netcol_collector.model.RouteCmd;
import com.training.netcol_collector.service.IpRouteService;
import com.training.netcol_collector.sshclient.SshClient;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.util.Scanner;

@Log
public class IpRouteCmdTask extends Task {
    @Getter private IpRouteService ipRouteService;
    @Getter private SshClient ssh;
    private RouteCmd routeCmd;

    public IpRouteCmdTask(RouteCmd cmd, IpRouteService service) {
        routeCmd = cmd;
        ipRouteService = service;

        ssh = new SshClient(
                cmd.getHostRequest().getAddr(),
                cmd.getHostRequest().getUser(),
                cmd.getHostRequest().getPassword()
        );
    }

    private String formRouteCommand(RouteCmd cmd) {
        StringBuilder netmaskStr = new StringBuilder();

        if (cmd.getNetmask().isEmpty() == false) {
            netmaskStr.append("netmask ").append(cmd.getNetmask());
        }

        String finalCmd = new StringBuilder()
                .append("route ")
                .append(cmd.getCommand()).append(" ")
                .append(cmd.getNetwork()).append(" ")
                .append(cmd.getRoutingIp()).append(" ")
                .append(netmaskStr.toString()).append(" ")
                .append("gw ").append(cmd.getGatewayIp())
                .toString();

        return finalCmd;
    }

    @Override
    public void collect(InputStream inputStream) {

        /* TODO: partially works. If command fails inputStream is empty */
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            log.info(line);

            /* In case if route command failed we will have some messages */
            setStatus(TaskStatus.ERROR);

        }
        log.info("Finish IpRouteCmdTask");
    }

    @Override
    public void run() {
        log.info("Running IpRouteCmdTask");

        setStatus(TaskStatus.INPROGRESS);
        ssh.connect();
        ssh.exec(formRouteCommand(routeCmd), this);
        ssh.disconnect();

        if (getStatus() != TaskStatus.ERROR) {
            setStatus(TaskStatus.DONE);
        }
        log.info("Done IpRouteCmdTask");
    }
}
