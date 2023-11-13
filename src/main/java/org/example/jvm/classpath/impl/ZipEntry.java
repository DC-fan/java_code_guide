package org.example.jvm.classpath.impl;

import org.example.jvm.classpath.Entry;

import java.nio.file.*;

public class ZipEntry implements Entry {

    private Path absolutionPath;

    public ZipEntry(String pathName) {
        this.absolutionPath = Paths.get(pathName).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String className) throws Exception {
        try (FileSystem zipFs = FileSystems.newFileSystem(absolutionPath, null)){
            return Files.readAllBytes(zipFs.getPath(className));
        }
    }

    @Override
    public String toString() {
        return this.absolutionPath.toString();
    }
}
