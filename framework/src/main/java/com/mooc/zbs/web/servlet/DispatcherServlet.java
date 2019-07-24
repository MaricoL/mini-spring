package com.mooc.zbs.web.servlet;

import com.mooc.zbs.web.handler.HandlerManager;
import com.mooc.zbs.web.handler.MappingHandler;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class DispatcherServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
//        res.getWriter().println("abc");
        for (MappingHandler mappingHandler : HandlerManager.mappingHandlerList) {
            try {
                if (mappingHandler.handler(req, res)) {
                    return;
                }
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
