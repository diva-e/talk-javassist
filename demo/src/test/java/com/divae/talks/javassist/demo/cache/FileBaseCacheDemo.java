package com.divae.talks.javassist.demo.cache;

import com.divae.talks.javassist.demo.cache.closedsource.DataStructure;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

 class FileBaseCacheDemo {

     FileBasedCache cache = new FileBasedCache();

    @Test
     void cacheString() throws IOException, ClassNotFoundException {
        cache.put("someId", "Hello World!");
        String result = (String) cache.get("someId");

        assertThat(result, is("Hello World!"));
    }

    /**
     * This test only works of the {@link AddSerializabilityTransformer} is
     * present.
     * <p>
     * After the agent module was built you can add the following agent
     * parameter to the VM to install the
     * {@link AddSerializabilityTransformer}:<br>
     * <em>-javaagent:../agent/target/agent.jar=com.divae.talks.javassist.demo.cache.AddSerializabilityTransformer</em>
     */
    @Test
     void cacheDataStructureWithoutSource() throws IOException, ClassNotFoundException {
        DataStructure data = new DataStructure();
        data.setValue("Hello World!");

        cache.put("someId", data);
        DataStructure result = (DataStructure) cache.get("someId");

        assertThat(result.getValue(), is("Hello World!"));
    }

}
