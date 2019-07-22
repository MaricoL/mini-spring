package com.mooc.zbs.web.server;

import com.mooc.zbs.web.servlet.TestServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {
    private Tomcat tomcat;
    private String[] args;

    public TomcatServer(String[] args) {
        this.args = args;
    }

    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(6699);
        tomcat.start();


        // 将 /test 与 TestServlet 进行关联
        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        TestServlet testServlet = new TestServlet();
        Tomcat.addServlet(context, "testServlet", testServlet).setAsyncSupported(true);
        context.addServletMappingDecoded("/test", "testServlet");
        tomcat.getHost().addChild(context);


        // Tomcat 等待线程，如果不启动等待线程，Tomcat已启动就会关闭
        Thread awaitThread = new Thread("tomcat_await_thread ") {
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };

        awaitThread.setDaemon(false);
        awaitThread.start();
    }


}
