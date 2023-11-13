package org.example.jvm;

import org.example.jvm.classpath.Classpath;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {


    public static void main(String[] args) {
        Cmd cmd = Cmd.parse(args);
        if(!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }
        if(cmd.versionFlag) {
            System.out.println("java version 1.8.0");
            return;
        }
        startJVM(cmd);
    }

    private static void startJVM(Cmd cmd) {
        Classpath cp = new Classpath("/tttt/cccc", "");
        System.out.printf("classpath: %s class: %s, args: %s\n", cmd.classpath, cmd.getMathClass(), cmd.getAppArgs());
        String className = cmd.getMathClass().replace(".", "/"); // java.lang.String
        try {
            byte[] bytes = cp.readClass(className);
            System.out.println(Arrays.toString(bytes));
        } catch (Exception e) {
            System.out.println("Could not find or load main class");
            e.getStackTrace();
        }

    }

}
