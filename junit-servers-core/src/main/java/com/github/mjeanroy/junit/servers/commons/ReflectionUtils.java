/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.junit.servers.commons;

import static com.github.mjeanroy.junit.servers.commons.CollectionUtils.filter;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.asList;
import static java.util.Collections.addAll;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import com.github.mjeanroy.junit.servers.exceptions.ReflectionException;

/**
 * Static reflection utilities.
 */
public final class ReflectionUtils {

	// Ensure non instantiation.
	private ReflectionUtils() {
	}

	/**
	 * Get all fields on given object and look for fields of
	 * super classes.
	 *
	 * @param type Class to inspect.
	 * @return Fields.
	 */
	public static List<Field> findAllFields(Class type) {
		List<Field> fields = new LinkedList<>();
		if (type != null) {
			addAll(fields, type.getDeclaredFields());

			if (type.getSuperclass() != null) {
				fields.addAll(findAllFields(type.getSuperclass()));
			}
		}
		return fields;
	}

	/**
	 * Get all static fields on given class object.
	 *
	 * @param type Class to inspect.
	 * @return Fields.
	 */
	public static List<Field> findStaticFields(Class type) {
		return filter(asList(type.getDeclaredFields()), new FieldStaticPredicate());
	}

	/**
	 * Get all static methods on given class object.
	 *
	 * @param type Class to inspect.
	 * @return Fields.
	 */
	public static List<Method> findStaticMethods(Class type) {
		return filter(asList(type.getDeclaredMethods()), new MethodStaticPredicate());
	}

	/**
	 * Get all static fields on given class object
	 * annotated with given annotation.
	 *
	 * @param type Class to inspect.
	 * @param klass Annotation class.
	 * @return Fields.
	 */
	public static List<Field> findStaticFieldsAnnotatedWith(Class type, Class<? extends Annotation> klass) {
		List<Field> fields = findStaticFields(type);
		return filter(fields, new FieldAnnotatedWithPredicate(klass));
	}

	/**
	 * Get all static methods on given class object
	 * annotated with given annotation.
	 *
	 * @param type Class to inspect.
	 * @param klass Annotation class.
	 * @return Fields.
	 */
	public static List<Method> findStaticMethodsAnnotatedWith(Class type, Class<? extends Annotation> klass) {
		List<Method> methods = findStaticMethods(type);
		return filter(methods, new MethodAnnotatedWithPredicate(klass));
	}

	/**
	 * Set value of given field on given instance.
	 *
	 * @param instance Instance.
	 * @param field Field.
	 * @param value Field value.
	 * @throws ReflectionException if set operation is not permitted.
	 */
	public static void setter(Object instance, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(instance, value);
		}
		catch (IllegalAccessException ex) {
			throw new ReflectionException(ex);
		}
	}

	/**
	 * Get value of static field.
	 *
	 * @param field Field.
	 * @param <T> Type of expected value.
	 * @return Value of static field.
	 */
	public static <T> T getter(Field field) {
		return getter(null, field);
	}

	/**
	 * Get value of field on target object.
	 * If target is null, it means that field is static and do not
	 * need any target instance.
	 *
	 * @param target Target object.
	 * @param field Field.
	 * @param <T> Type of expected value.
	 * @return Field value.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getter(Object target, Field field) {
		try {
			field.setAccessible(true);
			return (T) field.get(target);
		}
		catch (IllegalAccessException ex) {
			throw new ReflectionException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T invoke(Method method) {
		try {
			method.setAccessible(true);
			return (T) method.invoke(null);
		}
		catch (InvocationTargetException | IllegalAccessException ex) {
			throw new ReflectionException(ex);
		}
	}

	private static class FieldAnnotatedWithPredicate implements Predicate<Field> {

		private final Class<? extends Annotation> annotationKlass;

		public FieldAnnotatedWithPredicate(Class<? extends Annotation> annotationKlass) {
			this.annotationKlass = annotationKlass;
		}

		@Override
		public boolean apply(Field field) {
			return field.isAnnotationPresent(annotationKlass);
		}
	}

	private static class FieldStaticPredicate implements Predicate<Field> {

		@Override
		public boolean apply(Field field) {
			return isStatic(field.getModifiers());
		}
	}

	private static class MethodAnnotatedWithPredicate implements Predicate<Method> {

		private final Class<? extends Annotation> annotationKlass;

		public MethodAnnotatedWithPredicate(Class<? extends Annotation> annotationKlass) {
			this.annotationKlass = annotationKlass;
		}

		@Override
		public boolean apply(Method method) {
			return method.isAnnotationPresent(annotationKlass);
		}
	}

	private static class MethodStaticPredicate implements Predicate<Method> {

		@Override
		public boolean apply(Method object) {
			return isStatic(object.getModifiers());
		}
	}
}
