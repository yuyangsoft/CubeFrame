package com.cube.framework.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.cube.framework.annotation.Log;
import com.cube.framework.utils.PageUtil;
import com.cube.framework.utils.WhereFilter;

/**
 * 
 * @author YuYang
 * @version 1.0
 * @param <T>
 */
public abstract class BaseService<T extends BasePojo> {
	
	@Log
	@Transactional(readOnly = false)
	public Integer save(T obj) {
		return this.getBaseMapper().insert(obj);
	}
	
	@Log
	@Transactional(readOnly = false)
	public Integer editById(T obj) {
		return this.getBaseMapper().updateById(obj);
	}
	
	@Log
	@Transactional(readOnly = false)
	public Integer edit(T obj, List<WhereFilter> filterList) {
		return this.getBaseMapper().update(obj, filterList);
	}
	
	@Log
	@Transactional(readOnly = false)
	public Integer removeById(Object id) {
		return this.getBaseMapper().deleteById(this.getEntity(id));
	}
	
	@Log
	@Transactional(readOnly = false)
	public Integer remove(List<WhereFilter> filterList) {
		return this.getBaseMapper().delete(this.getEntity(null), filterList);
	}
	
	@SuppressWarnings("unchecked")
	@Log
	@Transactional(readOnly = true)
	public T getById(Object id) {
		T t = this.getEntity(id);
		Map<String, Object> obj = this.getBaseMapper().selectById(t);
		return (T)t.parsePojo(obj);
	}
	
	@SuppressWarnings("unchecked")
	@Log
	@Transactional(readOnly = true)
	public PageUtil<T> getByPage(List<WhereFilter> filterList, Integer pageNo, Integer pageSize) {
		List<T> list = new ArrayList<T>();
		PageUtil<T> page = new PageUtil<T>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(this.getBaseMapper().count(this.getEntity(null), filterList));
		List<Map<String, Object>> objList = this.getBaseMapper().selectByPage(this.getEntity(null), filterList, page);
		for(Map<String, Object> obj : objList) {
			T t = this.getEntity(null);
			list.add((T)t.parsePojo(obj));
		}
		page.setResult(list);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	@Log
	@Transactional(readOnly = true)
	public List<T> getAll(List<WhereFilter> filterList) {
		List<T> list = new ArrayList<T>();
		List<Map<String, Object>> objList = this.getBaseMapper().selectAll(this.getEntity(null), filterList);
		for(Map<String, Object> obj : objList) {
			T t = this.getEntity(null);
			list.add((T)t.parsePojo(obj));
		}
		return list;
	}
	
	public abstract T getEntity(Object id);
	
	public abstract BaseMapper<T> getBaseMapper();

}
