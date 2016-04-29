package com.cube.framework.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cube.framework.exception.PojoStructureException;
import com.cube.framework.utils.JSONUtils;
import com.cube.framework.utils.ReflectUtils;
import com.cube.framework.utils.WhereFilter;

/**
 * 基础POJO类
 * @author YuYang
 * @version 1.0
 */
public class BasePojo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 通过@Table(name = "table_name")获取POJO对应的数据库表名
	 * @return 数据库表名，如：table_name
	 */
	public String tablename() {
		Table table = this.getClass().getAnnotation(Table.class);
		if (table != null) {
			return table.name();
		} else {
			throw new PojoStructureException("undefine POJO @Table, need Tablename(@Table(name))");
		}
	}

	/**
	 * 通过@Id获取POJO的主键属性名，通过@Column(name = "user_id")获取POJO的主键字段名
	 * @return 主键名，如：{"userId","user_id"}
	 */
	public String[] id() {
		for (Field field : this.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				return new String[] { field.getName(), field.getAnnotation(Column.class).name() };
			}
		}
		throw new RuntimeException("undefine POJO @Id");
	}

	/**
	 * 用于存放POJO的属性与列信息，如：{BaseUserPojo.class:[{"userId","user_id"},{"userName","user_name"}],BaseRolePojo.class:[{"roleId","role_id"},{"roleName","role_name"}]}
	 */
	private transient static Map<Class<? extends BasePojo>, List<String[]>> columnMap = new HashMap<Class<? extends BasePojo>, List<String[]>>();

	private boolean isNull(String fieldname) {
		try {
			Field field = this.getClass().getDeclaredField(fieldname);
			return isNull(field);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return false;
	}

	private boolean isNull(Field field) {
		try {
			field.setAccessible(true);
			return field.get(this) == null;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 根据@Column(name = "user_name")获取POJO中属性名及字段名列表与POJO的class存入columnMap中
	 */
	public void caculationColumnList() {
		if (columnMap.containsKey(this.getClass())) {
			return;
		}
		Field[] fields = this.getClass().getDeclaredFields();
		List<String[]> columnList = new ArrayList<String[]>(fields.length);
		for (Field field : fields) {
			if (field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).name() != null) {
				columnList.add(new String[] { field.getName(), field.getAnnotation(Column.class).name() });
			}
		}
		columnMap.put(this.getClass(), columnList);
	}

	/**
	 * 根据POJO属性列数据columnMap与WhereFilter过滤条件集合拼接过滤条件SQL
	 * @param filterList
	 * @return
	 */
	public String whereColumns(List<WhereFilter> filterList) {
		StringBuilder sb = new StringBuilder();
		List<String[]> propertyNameList = columnMap.get(this.getClass());
		for (int i = 0; i < filterList.size(); i++) {
			WhereFilter wf = filterList.get(i);
			for (String[] propertyName : propertyNameList) {
				if (propertyName[0].equals(wf.fieldName)) {
					sb.append(propertyName[1]);
					break;
				}
			}
			switch (wf.operator) {
			case EQ:
				sb.append(" = ");
				sb.append("#{param2[" + i + "].value}");
				break;
			case NOT_EQ:
				sb.append(" <> ");
				sb.append("#{param2[" + i + "].value}");
				break;
			case GT:
				sb.append(" > ");
				sb.append("#{param2[" + i + "].value}");
				break;
			case GTE:
				sb.append(" >= ");
				sb.append("#{param2[" + i + "].value}");
				break;
			case IN:
				sb.append(" in ");
				wf.value = "(" + wf.value + ")";
				sb.append("#{param2[" + i + "].value}");
				break;
			case LIKE:
				sb.append(" like ");
				wf.value = "%" + wf.value + "%";
				sb.append("#{param2[" + i + "].value}");
				break;
			case LIKE_L:
				sb.append(" like ");
				wf.value = "%" + wf.value;
				sb.append("#{param2[" + i + "].value}");
				break;
			case LIKE_R:
				sb.append(" like ");
				wf.value = wf.value + "%";
				sb.append("#{param2[" + i + "].value}");
				break;
			case LT:
				sb.append(" < ");
				sb.append("#{param2[" + i + "].value}");
				break;
			case LTE:
				sb.append(" <= ");
				sb.append("#{param2[" + i + "].value}");
				break;
			case NOT_IN:
				sb.append(" not in ");
				wf.value = "(" + wf.value + ")";
				sb.append("#{param2[" + i + "].value}");
				break;
			case NOT_LIKE:
				sb.append(" not like ");
				wf.value = "%" + wf.value + "%";
				sb.append("#{param2[" + i + "].value}");
				break;
			default:
				break;
			}
			if (i != filterList.size() - 1) {
				sb.append(" and ");
			}
		}
		return sb.toString();
	}

	/**
	 * 根据POJO属性列数据columnMap拼接select结果SQL
	 * @return
	 */
	public String selectColumnsName() {
		StringBuilder sb = new StringBuilder();
		List<String[]> propertyNameList = columnMap.get(this.getClass());
		for (int i = 0; i < propertyNameList.size(); i++) {
			String[] propertyName = propertyNameList.get(i);
			sb.append(propertyName[1]);
			sb.append(" as ");
			sb.append(propertyName[0]);
			if (i != propertyNameList.size() - 1) {
				sb.append(" , ");
			}
		}
		return sb.toString();
	}

	/**
	 * 根据POJO属性列数据columnMap拼接insert字段SQL
	 * @return
	 */
	public String insertColumnsName() {
		StringBuilder sb = new StringBuilder();
		List<String[]> list = columnMap.get(this.getClass());
		int i = 0;
		for (String[] column : list) {
			if (isNull(column[0])) {
				continue;
			}
			if (i++ != 0) {
				sb.append(',');
			}
			sb.append(column[1]);
		}
		return sb.toString();
	}

	/**
	 * 根据POJO属性列数据columnMap拼接insert值SQL
	 * @return
	 */
	public String insertColumnsValues() {
		StringBuilder sb = new StringBuilder();
		List<String[]> list = columnMap.get(this.getClass());
		int i = 0;
		for (String[] column : list) {
			if (isNull(column[0])) {
				continue;
			}
			if (i++ != 0) {
				sb.append(',');
			}
			sb.append("#{").append(column[0]).append('}');
		}
		return sb.toString();
	}

	/**
	 * 根据POJO属性列数据columnMap拼接update字段SQL
	 * @return
	 */
	public String updateSet() {
		StringBuilder sb = new StringBuilder();
		List<String[]> list = columnMap.get(this.getClass());
		int i = 0;
		for (String[] column : list) {
			if (isNull(column[0])) {
				continue;
			}
			if (i++ != 0) {
				sb.append(',');
			}
			sb.append(column[1]).append("=#{").append(column[0]).append('}');
		}
		return sb.toString();
	}

	public Integer getId() {
		return 0;
	}

	/**
	 * 将当前POJO对象转为JSON字符串
	 * @return
	 */
	public String toJSONString() {
		return JSONUtils.beanToJson(this).toJSONString();
	}

	/**
	 * 将当前POJO对象转为字符串
	 */
	@Override
	public String toString() {
		Field[] fields = this.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (Field f : fields) {
			if (Modifier.isStatic(f.getModifiers()) || Modifier.isFinal(f.getModifiers()))
				continue;
			Object value = null;
			try {
				f.setAccessible(true);
				value = f.get(this);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (value != null)
				sb.append(f.getName()).append('=').append(value).append(',');
		}
		sb.append(']');

		return sb.toString();
	}
	
	/**
	 * 将Map对象转为POJO对象
	 * @param obj
	 * @return
	 */
	public BasePojo parsePojo(Map<String, Object> obj) {
		try {
			ReflectUtils.batchSetBeanValue(this, obj);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

}
