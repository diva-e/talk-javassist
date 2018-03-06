package com.divae.talks.javassist.demo.cache;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
