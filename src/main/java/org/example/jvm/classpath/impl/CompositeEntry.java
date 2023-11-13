package org.example.jvm.classpath.impl;

import org.example.jvm.classpath.Entry;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * 存在多个路径 从其中的一个路径加载成功即可
 */
public class CompositeEntry implements Entry {

    private final List<Entry> entries = new ArrayList<>();

    public CompositeEntry(String pathName) {
        String[] paths = pathName.split(File.pathSeparator);
        for(String path : paths) {
            this.entries.add(Entry.create(path));
        }
    }

    @Override
    public byte[] readClass(String className) throws Exception {
        for(Entry entry : this.entries) {
            try {
                entry.readClass(className);
            } catch (Exception e) {

            }
        }
        throw new Exception("class not found " + className);
    }
}
