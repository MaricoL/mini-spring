package com.mooc.zbs.starter;

import com.mooc.zbs.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

public class MiniApplication {

    public static void run(Class<?> cls , String[] args) {
        System.out.println("Hello Mini-Spring!!");


        try {
            TomcatServer tomcatServer = new TomcatServer(args);
            tomcatServer.startServer();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
