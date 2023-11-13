package org.example.jvm.classpath.impl;

import org.example.jvm.classpath.Entry;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirEntry implements Entry {

    private Path absolutionPath;

    public DirEntry(String pathName) {
        this.absolutionPath = Paths.get(pathName).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String className) throws Exception {
        return Files.readAllBytes(absolutionPath.resolve(className));
    }

    @Override
    public String toString() {
        return this.absolutionPath.toString();
    }

}
