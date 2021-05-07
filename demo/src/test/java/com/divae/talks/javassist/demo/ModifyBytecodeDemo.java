package com.divae.talks.javassist.demo;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ModifyBytecodeDemo {

    /**
     * <em>Hint:</em> In order to successfully execute this test you need to switch
     * the fork mode in test execution of IntelliJ to method!
     */
    @Test
    public void modifyMethods() throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("com.divae.talks.javassist.demo.ModifyBytecodeDemo$Entity");

        for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
            ctMethod.instrument(new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    try {
                        if (f.isReader() && f.getField().getType().equals(CtClass.intType)) {
                            f.replace("$_ = 42;");
                        }
                    } catch (NotFoundException e) {
                        e.printStackTrace(); // Acceptable in a demo? :-)
                    }
                }
            });
        }

        Entity entity = (Entity) ctClass.toClass().getDeclaredConstructor().newInstance();
        entity.setValue(123);

        assertThat(entity.getValue(), is(42));
        assertThat(entity.toString(), is("Entity with value=42"));
    }

    public static class Entity {
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Entity with value=" + value;
        }
    }

}