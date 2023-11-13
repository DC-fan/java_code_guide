package org.example.jvm.classpath.impl;

import org.example.jvm.classpath.Entry;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * 继承CompositeEntry实现的
 * *指的是文件目录下的多个文件
 * 将这些文件中的所有jar都保存到Entry中
 */
public class WildcardEntry extends CompositeEntry {

    public WildcardEntry(String pathName) {
        super(toPathList(pathName));

    }

    private static String toPathList(String wildcardPath) {
        String baseDir = wildcardPath.replace("*", "");
        try {
            return Files.walk(Paths.get(baseDir))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(p -> p.endsWith(".jar") || p.endsWith(".JAR"))
                    .collect(Collectors.joining(File.pathSeparator));
        } catch (Exception e) {
            return "";
        }
    }
}
