package com.divae.talks.javassist.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class ModifyBytecodeDemo {

	@Test
	@SuppressWarnings("unchecked")
	public void setAndGetValues() throws Exception {
		ClassPool classPool = ClassPool.getDefault();
		CtClass ctClass = classPool.get("com.divae.talks.javassist.demo.ModifyBytecodeDemo$Entity");

		for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {

		}

		Entity entity = (Entity) ctClass.toClass().getDeclaredConstructor().newInstance();
		entity.setValue(123);

		assertThat(entity.getValue(), is(123));
	}

	public static class Entity {
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}

}
