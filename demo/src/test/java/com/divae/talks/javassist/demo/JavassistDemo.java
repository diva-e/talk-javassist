package com.divae.talks.javassist.demo;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Mnemonic;
import org.junit.jupiter.api.Test;

public class JavassistDemo {

    @Test
    public void presentJavassist() throws Exception {

        // fetch ClassPool
        ClassPool classPool = ClassPool.getDefault();

        // load CtClass
        CtClass ctClass = classPool.get("com.divae.talks.javassist.demo.JavassistDemo$Point");

        System.out.println("The contains the following fields:");
        for (CtField ctField : ctClass.getDeclaredFields()) {
            System.out.println(ctField);
        }

        System.out.println("Opcodes of toString method:");
        CtMethod ctMethod = ctClass.getDeclaredMethod("toString");
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        CodeIterator codeIterator = codeAttribute.iterator();
        while (codeIterator.hasNext()) {
            int index = codeIterator.next();
            int op = codeIterator.byteAt(index);
            System.out.println(Mnemonic.OPCODE[op]);
        }

    }

    public static class Point {
        private int x;
        private int y;

        @Override
        public String toString() {
            return "Point[x=" + x + ",y=" + y + "]";
        }
    }
}