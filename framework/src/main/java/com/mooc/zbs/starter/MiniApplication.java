package com.mooc.zbs.starter;

import com.mooc.zbs.core.ClassScanner;
import com.mooc.zbs.web.handler.HandlerManager;
import com.mooc.zbs.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

public class MiniApplication {

    public static void run(Class<?> cls , String[] args) {
        System.out.println("Hello Mini-Spring!!");


        try {
            TomcatServer tomcatServer = new TomcatServer(args);
            List<Class<?>> classes = ClassScanner.scanClasses(cls.getPackage().getName());

            HandlerManager.resolveMappingHandler(classes);
            tomcatServer.startServer();
        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
