package com.divae.talks.javassist.demo.cache;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.divae.talks.javassist.demo.cache.closedsource.DataStructure;

public class FileBaseCacheDemo {

	private FileBasedCache cache = new FileBasedCache();

	@Test
	public void cacheString() throws IOException, ClassNotFoundException {
		cache.put("someid", "Hello World!");
		String result = (String) cache.get("someid");

		assertThat(result, is("Hello World!"));
	}

	/**
	 * This test only works of the {@link AddSerializabilityTransformer} is
	 * present.
	 * 
	 * After the agent module was built you can add the following agent
	 * parameter to the VM to install the
	 * {@link AddSerializabilityTransformer}:<br>
	 * <em>-javaagent:../agent/target/agent.jar=com.divae.talks.javassist.demo.cache.AddSerializabilityTransformer</em>
	 */
	@Test
	public void cacheDataStructureWithoutSource() throws IOException, ClassNotFoundException {
		DataStructure data = new DataStructure();
		data.setVlaue("Hello World!");

		cache.put("someid", data);
		DataStructure result = (DataStructure) cache.get("someid");

		assertThat(result.getVlaue(), is("Hello World!"));
	}

}
