package com.training.netcol_collector.task;

import com.training.netcol_collector.model.IpInterface;
import com.training.netcol_collector.model.IpRoute;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.util.Scanner;

@Log
public class IpRouteTask extends Task {
    private HostTask hostTask;
    private final static String COMMAND = "route";
    private final static int TOKEN_NUM = 8;

    IpRouteTask(Long id, HostTask hostTask) {
        this.hostTask = hostTask;
        setId(id);
        setCmd(COMMAND);
    }

    private IpInterface getIpInterfaceByName(String name) {
        return hostTask.getHostBuilder().getIpInterfaces().stream()
                .filter(intf -> intf.getName().equals(name))
                .findFirst()
                .get();
    }

    public void collect(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            log.info(line);

            if (line.isEmpty() || line.contains("Kernel") || line.contains("Destination")) {
                continue;
            }

            String[] tokens = line.split(" +");
            if (tokens.length != TOKEN_NUM) {
                log.warning("Bad route line");
                continue;
            }

            IpRoute.Builder routeTableLine = new IpRoute.Builder();
            routeTableLine.
                    destination(tokens[0]).
                    gateway(tokens[1]).
                    genmask(tokens[2]).
                    flags(tokens[3]).
                    metric(Integer.parseInt(tokens[4])).
                    refs(Integer.parseInt(tokens[5])).
                    use(Integer.parseInt(tokens[6])).
                    iface(getIpInterfaceByName(tokens[7]));

            hostTask.getHostBuilder().
                    ipRoutes(routeTableLine.build());
        }
        log.info(String.format("Finish ipRouteTask with id='%d'", getId()));
    }

    public void run() {
        log.info(String.format("Running ipRouteTask with id='%d'", getId()));
        hostTask.getSsh().connect();
        hostTask.getSsh().exec(getCmd(), this);
        hostTask.getSsh().disconnect();
    }
}
