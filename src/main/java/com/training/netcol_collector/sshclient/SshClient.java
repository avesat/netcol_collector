package com.training.netcol_collector.sshclient;

import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.training.netcol_collector.utils.Collector;
import lombok.extern.java.Log;

@Log
public class SshClient {
    private JSch ssh;
    private Session session;
    private Channel channel;

    private String host;
    private String user;
    private String password;
    private static final int PORT = 22;

    public SshClient(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
        log.info(String.format("sshClient parameters: host: '%s'; user: '%s'; password: '%s'", host, user, password));
    }

    public void connect() {
        try {
            ssh = new JSch();
            session = ssh.getSession(user, host, PORT);
            session.setPassword(password);

            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            log.info(String.format("sshClient connected to '%s' by '%s'", host, user));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exec(String cmd, Collector collector) {
        try {
            channel = session.openChannel("exec");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        log.info(String.format("sshClient exec command '%s'", cmd));
        ((ChannelExec)channel).setCommand(cmd);
        channel.setInputStream(null);
        ((ChannelExec)channel).setErrStream(System.err);

        try {
            InputStream inputStream = channel.getInputStream();

            channel.connect();
            while (true) {
                collector.collect(inputStream);

                if (channel.isClosed()) {
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        channel.disconnect();
    }

    public void disconnect() {
        session.disconnect();
        log.info(String.format("sshClient disconnect"));
    }
}
