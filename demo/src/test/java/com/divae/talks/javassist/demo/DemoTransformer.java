package com.divae.talks.javassist.demo;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class DemoTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classFileBuffer) {
        System.out.println("Transforming " + className);

        return null;
    }

}
