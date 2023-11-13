package org.example.jvm.classpath;

import org.example.jvm.classpath.impl.CompositeEntry;
import org.example.jvm.classpath.impl.DirEntry;
import org.example.jvm.classpath.impl.WildcardEntry;
import org.example.jvm.classpath.impl.ZipEntry;

import java.io.File;

/**
 * 类路径接口
 * todo: 此处的pathName和className之间的关系是什么
 */
public interface Entry {

    byte[] readClass(String className) throws Exception;

    static Entry create(String path) {
        if(path.contains(File.pathSeparator)) {
            return new CompositeEntry(path);
        }
        if(path.endsWith("*")) {
            return new WildcardEntry(path);
        }
        if(path.endsWith(".jar") || path.endsWith(".JAR") || path.endsWith(".zip") || path.endsWith(".ZIP")) {
            return new ZipEntry(path);
        }
        return new DirEntry(path);
    }

}
