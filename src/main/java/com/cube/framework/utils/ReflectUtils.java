package com.cube.framework.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 反射工具类
 * 
 * @author 于洋
 * @version 0.2 
 * 			**版本说明** 
 * 			2012-07-25 0.1ver 版本建立,初始化工具类方法. 
 * 			2012-07-26 0.2var 新增方法： 
 * 								批量设置Bean属性值(batchSetBeanValue[Object, Map<String, Object>])
 * 								批量获取Bean属性值(batchGetBeanValue[Object])
 */
public class ReflectUtils {

	/**
	 * 批量设置Bean属性值(批量调用set方法)
	 * 
	 * @param obj
	 *            Bean对象
	 * @param values
	 *            需要设置属性的名称与值,Key为属性名称,Value为属性值
	 * @throws RuntimeException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static void batchSetBeanValue(Object obj, Map<String, Object> values)
			throws RuntimeException, NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {
		Iterator<String> ite = values.keySet().iterator();
		while (ite.hasNext()) {
			String propertyName = (String) ite.next();
			Object propertyValue = values.get(propertyName);
			String methodName = "set"
					+ StringUtils.upCaseFirstChar(propertyName);
			ReflectUtils.invokeMethod(obj, methodName,
					new Object[] { propertyValue });
		}
	}

	/**
	 * 批量获取Bean属性值(批量调用get方法)
	 * 
	 * @param obj
	 *            Bean对象
	 * @return Map<String, Object> 属性的名称与值,Key为属性名称,Value为属性值
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static Map<String, Object> batchGetBeanValue(Object obj)
			throws IllegalArgumentException, NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {
		Map<String, Object> beanValue = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			String propertyName = field.getName();
			String methodName = "get"
					+ StringUtils.upCaseFirstChar(propertyName);
			Object propertyValue = ReflectUtils.invokeMethod(obj, methodName,
					null);
			beanValue.put(propertyName, propertyValue);
		}
		return beanValue;
	}

	/**
	 * 根据属性名称获取指定对象的公共属性
	 * 
	 * @param obj
	 *            指定对象
	 * @param fieldname
	 *            属性名称
	 * @return Object 公共属性对象
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static Object getProperty(Object obj, String fieldname)
			throws SecurityException, IllegalArgumentException,
			NoSuchFieldException, IllegalAccessException {
		Class<? extends Object> objClass = obj.getClass();
		Field field = objClass.getField(fieldname);
		Object result = field.get(obj);
		return result;
	}

	/**
	 * 根据属性名称获取指定类名的静态属性
	 * 
	 * @param className
	 *            指定类名
	 * @param fieldName
	 *            属性名称
	 * @return Object 静态属性对象
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 */
	public static Object getStaticProperty(String className, String fieldName)
			throws SecurityException, IllegalArgumentException,
			NoSuchFieldException, ClassNotFoundException,
			IllegalAccessException {
		Class<?> cls = Class.forName(className);
		Field field = cls.getField(fieldName);
		Object provalue = field.get(cls);
		return provalue;
	}

	/**
	 * 根据属性名称获取指定对象的私有属性
	 * 
	 * @param obj
	 *            指定对象
	 * @param propertyName
	 *            属性名称
	 * @return Object 私有属性
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static Object getPrivatePropertyValue(Object obj, String propertyName)
			throws NoSuchFieldException, IllegalAccessException {
		Class<? extends Object> cls = obj.getClass();
		Field field = cls.getDeclaredField(propertyName);
		field.setAccessible(true);
		Object retvalue = field.get(obj);
		return retvalue;
	}

	/**
	 * 根据方法名称调用指定对象中的指定方法
	 * 
	 * @param owner
	 *            指定对象
	 * @param methodName
	 *            方法名称
	 * @param args
	 *            方法参数,可为null
	 * @return Object 方法返回值
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static Object invokeMethod(Object owner, String methodName,
			Object[] args) throws NoSuchMethodException,
			IllegalArgumentException, InvocationTargetException,
			IllegalAccessException {
		args = ValidatorUtils.isEmpty(args) ? new Object[] {} : args;
		Class<? extends Object> cls = owner.getClass();
		Class<?>[] argclass = new Class[args.length];
		for (int i = 0, j = argclass.length; i < j; i++) {
			argclass[i] = args[i].getClass();
		}
		Method method = cls.getMethod(methodName, argclass);
		return method.invoke(owner, args);
	}

	/**
	 * 根据方法名调用指定类名中的静态方法
	 * 
	 * @param className
	 *            指定类名
	 * @param methodName
	 *            方法名称
	 * @param args
	 *            方法参数,可为null
	 * @return Object 方法返回值
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static Object invokeStaticMethod(String className,
			String methodName, Object[] args) throws ClassNotFoundException,
			NoSuchMethodException, IllegalArgumentException,
			InvocationTargetException, IllegalAccessException {
		args = ValidatorUtils.isEmpty(args) ? new Object[] {} : args;
		Class<?> cls = Class.forName(className);
		Class<?>[] argclass = new Class[args.length];
		for (int i = 0, j = argclass.length; i < j; i++) {
			argclass[i] = args[i].getClass();
		}
		Method method = cls.getMethod(methodName, argclass);
		return method.invoke(null, args);
	}

	/**
	 * 获取指定类名类的对象
	 * 
	 * @param className
	 *            指定类名
	 * @param args
	 *            构造方法参数,可为null
	 * @return Object 类的对象
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object newInstance(String className, Object[] args)
			throws ClassNotFoundException, NoSuchMethodException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		args = ValidatorUtils.isEmpty(args) ? new Object[] {} : args;
		Class<?> clss = Class.forName(className);
		Class<?>[] argclass = new Class[args.length];
		for (int i = 0, j = argclass.length; i < j; i++) {
			argclass[i] = args[i].getClass();
		}
		Constructor<?> cons = clss.getConstructor(argclass);
		return cons.newInstance();
	}

}
