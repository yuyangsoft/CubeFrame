package com.cube.framework.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONUtils {

	/**
	 * @param object
	 *            任意对象
	 * @return java.lang.String
	 */
	public static String objectToJsonStr(Object object) {
		StringBuilder json = new StringBuilder();
		if (object == null) {
			json.append("\"\"");
		} else if (object instanceof String || object instanceof Integer) {
			json.append("\"").append((String) object).append("\"");
		} else {
			json.append(beanToJsonStr(object));
		}
		return json.toString();
	}

	/**
	 * 功能描述:传入任意一个 javabean 对象生成一个指定规格的字符串
	 * 
	 * @param bean
	 *            bean对象
	 * @return String
	 */
	public static String beanToJsonStr(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = objectToJsonStr(props[i].getName());
					String value = objectToJsonStr(props[i].getReadMethod()
							.invoke(bean));
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串
	 * 
	 * @param list
	 *            列表对象
	 * @return java.lang.String
	 */
	public static String listToJsonStr(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(objectToJsonStr(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}
	
	/**
	 * List转换JSONArray
	 * @param list
	 * @return
	 */
	public static JSONArray listToJson(List<?> list) {
		return JSONArray.parseArray(JSONArray.toJSONString(list));
	}
	
	/**
	 * Map转换JSONObject
	 * @param map
	 * @return
	 */
	public static JSONObject mapToJson(Map<String, ?> map) {
		return JSONObject.parseObject(JSONObject.toJSONString(map));
	}
	
	/**
	 * bean转换JSONObject
	 * @param obj
	 * @return
	 */
	public static JSONObject beanToJson(Object obj) {
		return JSONObject.parseObject(JSONObject.toJSONString(obj));
	}

}
