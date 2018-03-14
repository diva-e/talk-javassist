package com.divae.talks.javassist.demo;

import org.junit.Test;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

public class JavassistDemo {

	@Test
	public void test() throws Exception {
		ClassPool classPool = ClassPool.getDefault();

		CtClass ctClass = classPool.get("com.divae.talks.javassist.demo.JavassistDemo$Point");

		for (CtField ctField : ctClass.getDeclaredFields()) {
			System.out.println(ctField);
		}
		CtMethod ctMethod = ctClass.getDeclaredMethod("toString");
		ctMethod.instrument(new ExprEditor() {
			@Override
			public void edit(FieldAccess f) throws CannotCompileException {
				System.out.println("Access to " + f.getFieldName());
			}
		});

		Point object = (Point) ctClass.toClass().getDeclaredConstructor().newInstance();
		System.out.println(object);
	}

	public static class Point {
		private int x;
		private int y;

		@Override
		public String toString() {
			return "Point mit x=" + x + " und y=" + y;
		}

	}

}
