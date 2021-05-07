package com.divae.talks.javassist.demo.cache;

import java.io.*;

public class FileBasedCache {

    public void put(String key, Object value) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(key + ".bin"))) {
            out.writeObject(value);
        }
    }

    public Object get(String key) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(key + ".bin"))) {
            return in.readObject();
        }
    }

}
