package com.mooc.zbs.web.handler;

import com.mooc.zbs.web.mvc.Controller;
import com.mooc.zbs.web.mvc.RequestMapping;
import com.mooc.zbs.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class HandlerManager {

    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    public static void resolveMappingHandler(List<Class<?>> classList) {
        for (Class<?> clazz : classList) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                parseHandlerFromController(clazz);
            }
        }
    }

    private static void parseHandlerFromController(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {

                String uri = method.getDeclaredAnnotation(RequestMapping.class).value();


                List<String> parameterList = new ArrayList<>();
                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    if (parameter.isAnnotationPresent(RequestParam.class)) {
                        parameterList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                    }
                }
                // 转换成参数名数组
                String[] args = parameterList.toArray(new String[parameterList.size()]);

                mappingHandlerList.add(new MappingHandler(uri, method, clazz, args));
            }
        }
    }
}
