package org.example.jvm.classpath;


import org.example.jvm.classpath.impl.WildcardEntry;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 启动类路径
 * 扩展类路径
 * 用户类路径
 */
public class Classpath {

    private Entry bootstrapClasspath;

    private Entry extensionClasspath;

    private Entry userClasspath;

    public Classpath(String jreOption, String cpOption) {
        // 启动类 & 扩展类
        bootstrapAndExtensionClasspath(jreOption);
        // 用户类
        parseUserClasspath(cpOption);
    }


    private void bootstrapAndExtensionClasspath(String jreOption) {
        // 获取jre的目录
        String jreDir = getJreDir(jreOption);
        System.out.println("jreDir: " + jreDir);

        // ..jre/lib/*
        String jreLibPath = Paths.get(jreDir, "lib") + File.separator + "*";
        bootstrapClasspath = new WildcardEntry(jreLibPath);

        // ..jre/lib/ext/*
        String jreExtPath = Paths.get(jreDir, "lib", "ext") + File.separator +
                "*";
        extensionClasspath = new WildcardEntry(jreExtPath);
    }

    private static String getJreDir(String jreOption) {
        if(null != jreOption && Files.exists(Paths.get(jreOption))) {
            return jreOption;
        }
        if(Files.exists(Paths.get("./jre"))) {
            return "./jre";
        }
        String jh = System.getenv("JAVA_HOME");
        System.out.println("获取系统变量: " + jh);
        if(null != jh) {
            return Paths.get(jh, "jre").toString();
        }
        throw new RuntimeException("Can not find JRE folder!!!!");
    }

    private void parseUserClasspath(String cpOption) {
        if(null == cpOption) {
            cpOption = ".";
        }
        userClasspath = Entry.create(cpOption);
    }

    public byte[] readClass(String className) throws Exception {
        // 启动类加载器
        try {
            return bootstrapClasspath.readClass(className);
        } catch (Exception e) {

        }
        // 扩展类加载器
        try {
            return extensionClasspath.readClass(className);
        } catch (Exception e) {

        }
        return userClasspath.readClass(className);
    }

}
