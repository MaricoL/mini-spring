package com.mooc.zbs.core;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {

    public static List<Class<?>> scanClasses(String packagename) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();

        String path = packagename.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().contains("jar")) {
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                String jarFileName = jarURLConnection.getJarFile().getName();
                classList.addAll(getClassesFromJar(jarFileName, path));
            }
        }
        return classList;
    }

    private static List<Class<?>> getClassesFromJar(String jarFileName, String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFileName);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String entryName = jarEntry.getName();
            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                String fileFullName = entryName.replace("/", ".").substring(0, entryName.length() - 6);
                System.out.println(fileFullName);
                classList.add(Class.forName(fileFullName));
            }
        }

        return classList;
    }
}
