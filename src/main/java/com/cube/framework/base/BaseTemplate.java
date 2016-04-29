package com.cube.framework.base;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cube.framework.utils.PageUtil;
import com.cube.framework.utils.WhereFilter;

/**
 * 
 * @author YuYang
 * @version 1.0
 * @param <T>
 */
public class BaseTemplate<T extends BasePojo> {

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public String insert(T obj) {
		BEGIN();
		INSERT_INTO(obj.tablename());
		obj.caculationColumnList();
		VALUES(obj.insertColumnsName(), obj.insertColumnsValues());
		return SQL();
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public String updateById(T obj) {
		String[] idname = obj.id();
		BEGIN();
		UPDATE(obj.tablename());
		obj.caculationColumnList();
		SET(obj.updateSet());
		WHERE(idname[1] + "=#{" + idname[0] + "}");
		return SQL();
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String update(Map<String, Object> map) {
		T obj = (T) map.get("0");
		List<WhereFilter> filterList = (List<WhereFilter>) map.get("1");
		BEGIN();
		obj.caculationColumnList();
		UPDATE(obj.tablename());
		SET(obj.updateSet());
		String where = obj.whereColumns(filterList);
		if (StringUtils.isNotBlank(where)) {
			WHERE(where);
		}
		return SQL();
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public String deleteById(T obj) {
		String[] idname = obj.id();
		BEGIN();
		DELETE_FROM(obj.tablename());
		WHERE(idname[1] + "=#{" + idname[0] + "}");
		return SQL();
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String delete(Map<String, Object> map) {
		T obj = (T) map.get("0");
		List<WhereFilter> filterList = (List<WhereFilter>) map.get("1");
		BEGIN();
		DELETE_FROM(obj.tablename());
		String where = obj.whereColumns(filterList);
		if (StringUtils.isNotBlank(where)) {
			WHERE(where);
		}
		return SQL();
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String count(Map<String, Object> map) {
		T obj = (T) map.get("0");
		List<WhereFilter> filterList = (List<WhereFilter>) map.get("1");
		BEGIN();
		obj.caculationColumnList();
		SELECT("count(*)");
		FROM(obj.tablename());
		String where = obj.whereColumns(filterList);
		if (StringUtils.isNotBlank(where)) {
			WHERE(where);
		}
		return SQL();
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public String selectById(T obj) {
		String[] idname = obj.id();
		BEGIN();
		obj.caculationColumnList();
		SELECT(obj.selectColumnsName());
		FROM(obj.tablename());
		WHERE(idname[1] + "=#{" + idname[0] + "}");
		return SQL();
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String selectAll(Map<String, Object> map) {
		T obj = (T) map.get("0");
		List<WhereFilter> filterList = (List<WhereFilter>) map.get("1");
		BEGIN();
		obj.caculationColumnList();
		SELECT(obj.selectColumnsName());
		FROM(obj.tablename());
		String where = obj.whereColumns(filterList);
		if (StringUtils.isNotBlank(where)) {
			WHERE(where);
		}
		return SQL();
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String selectByPage(Map<String, Object> map) {
		T obj = (T) map.get("0");
		List<WhereFilter> filterList = (List<WhereFilter>) map.get("1");
		PageUtil<T> page = (PageUtil<T>) map.get("2");
		BEGIN();
		obj.caculationColumnList();
		SELECT(obj.selectColumnsName());
		FROM(obj.tablename());
		String where = obj.whereColumns(filterList);
		if (StringUtils.isNotBlank(where)) {
			WHERE(where);
		}
		String sql = SQL();
		if (page != null) {
			sql += " limit " + (page.getPageNo() - 1) * page.getPageSize() + "," + page.getPageSize();
		}
		return sql;
	}

}
