package com.divae.talks.javassist.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

public class AddTransformerAgent {

    public static void premain(String argument, Instrumentation instrumentation) throws Exception {

        Class<?> transformerClass = Class.forName(argument);
        ClassFileTransformer transformerInstance = (ClassFileTransformer) transformerClass.getDeclaredConstructor().newInstance();

        instrumentation.addTransformer(transformerInstance);

    }

}
