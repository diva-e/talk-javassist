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

class ExploreBytecodeDemo {

    @Test
    void exploreClass() throws Exception {
        // fetch ClassPool
        ClassPool classPool = ClassPool.getDefault();

        // load CtClass
        CtClass ctClass = classPool.get("com.divae.talks.javassist.demo.ExploreBytecodeDemo$Point");

        System.out.println("## The class contains the following fields:");
        for (CtField ctField : ctClass.getDeclaredFields()) {
            System.out.println(ctField);
        }

        System.out.println("## Opcodes of the toString method:");
        CtMethod ctMethod = ctClass.getDeclaredMethod("toString");
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();

        CodeIterator codeIterator = codeAttribute.iterator();
        while (codeIterator.hasNext()) {
            int index = codeIterator.next();
            int op = codeIterator.byteAt(index);
            String opcodeName = Mnemonic.OPCODE[op];
            System.out.printf("Index at %2d: %s\n", index, opcodeName);
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