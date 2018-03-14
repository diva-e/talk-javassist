package com.divae.talks.javassist.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Mnemonic;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

public class VerifyClassDemo {

	@Test
	public void test() throws IOException, NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException, BadBytecode {
		ClassPool classPool = ClassPool.getDefault();

		CtClass ctClass = classPool.get(Entity.class.getName());

		CtMethod ctMethod = ctClass.getDeclaredMethod("equals");
		MethodInfo methodInfo = ctMethod.getMethodInfo();
		ctMethod.instrument(new ExprEditor() {
			@Override
			public void edit(FieldAccess f) throws CannotCompileException {
				// TODO Auto-generated method stub
				super.edit(f);
			}
		});
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		CodeIterator codeIterator = codeAttribute.iterator();
		while (codeIterator.hasNext()) {
			int index = codeIterator.next();
			int op = codeIterator.byteAt(index);
			System.out.println(Mnemonic.OPCODE[op]);
		}

		FieldAccessRecorder equalsFieldAccessRecorder = new FieldAccessRecorder();
		ctClass.getDeclaredMethod("equals").instrument(equalsFieldAccessRecorder);
		FieldAccessRecorder hashCodeFieldAccessRecorder = new FieldAccessRecorder();
		ctClass.getDeclaredMethod("hashCode").instrument(hashCodeFieldAccessRecorder);

		assertThat(equalsFieldAccessRecorder.getAccessedFields(), is(hashCodeFieldAccessRecorder.getAccessedFields()));
	}

	static class FieldAccessRecorder extends ExprEditor {
		private Set<CtField> accessedFields = new HashSet<>();

		@Override
		public void edit(FieldAccess f) throws CannotCompileException {
			try {
				accessedFields.add(f.getField());
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}

		public Set<CtField> getAccessedFields() {
			return accessedFields;
		}
	}

	public static class Entity {
		private int id;
		private String value;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Entity other = (Entity) obj;
			if (id != other.id) {
				return false;
			}
			if (value == null) {
				if (other.value != null) {
					return false;
				}
			} else if (!value.equals(other.value)) {
				return false;
			}
			return true;
		}

	}

}
