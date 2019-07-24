package com.mooc.zbs.web.handler;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MappingHandler {
    private String uri;
    private Method method;
    private Class<?> controller;
    private String[] args;

    public MappingHandler(String uri, Method method, Class<?> controller, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.args = args;
    }


    public boolean handler(ServletRequest request, ServletResponse response) throws InvocationTargetException, IllegalAccessException, InstantiationException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        if (!this.uri.equals(requestURI)) {
            return false;
        }

        Object[] parameters = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            parameters[i] = httpServletRequest.getParameter(args[i]);
        }

        Object ctl = this.controller.newInstance();
        Object result = method.invoke(ctl, parameters);
        response.getWriter().println(result);

        return true;
    }
}
