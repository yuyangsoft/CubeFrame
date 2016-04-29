package com.cube.framework.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import com.cube.framework.constants.Operator;

public class WhereFilter {
	public String fieldName;
	public Object value;
	public Operator operator;

	public WhereFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	public static Map<String, WhereFilter> parse(Map<String, Object> filterParams) {
		Map<String, WhereFilter> filters = new HashMap<String, WhereFilter>();
		for (Entry<String, Object> entry : filterParams.entrySet()) {
			String[] names = StringUtils.split((String) entry.getKey(), "_");
			if (names.length != 2) {
				throw new IllegalArgumentException((String) entry.getKey() + " is not a valid search filter name");
			}
			WhereFilter filter = new WhereFilter(names[1], Operator.valueOf(names[0]), entry.getValue());
			filters.put(filter.fieldName, filter);
		}
		return filters;
	}
}