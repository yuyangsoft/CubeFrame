package com.cube.framework.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.cube.framework.utils.PageUtil;
import com.cube.framework.utils.WhereFilter;

/**
 * 
 * @author YuYang
 * @version 1.0
 * @param <T>
 */
public interface BaseMapper<T extends BasePojo> {

	/**
	 * 
	 * @param obj
	 * @return
	 */
	@InsertProvider(type = BaseTemplate.class, method = "insert")
	public int insert(T obj);

	/**
	 * 
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type = BaseTemplate.class, method = "updateById")
	public int updateById(T obj);
	
	/**
	 * 
	 * @param obj
	 * @param filterList
	 * @return
	 */
	@UpdateProvider(type = BaseTemplate.class, method = "update")
	public int update(T obj, List<WhereFilter> filterList);

	/**
	 * 
	 * @param obj
	 * @return
	 */
	@DeleteProvider(type = BaseTemplate.class, method = "deleteById")
	public int deleteById(T obj);
	
	/**
	 * 
	 * @param obj
	 * @param filterList
	 * @return
	 */
	@DeleteProvider(type = BaseTemplate.class, method = "delete")
	public int delete(T obj, List<WhereFilter> filterList);
	
	/**
	 * 
	 * @param obj
	 * @param filterList
	 * @return
	 */
	@SelectProvider(type = BaseTemplate.class, method = "count")
	public int count(T obj, List<WhereFilter> filterList);
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	@SelectProvider(type = BaseTemplate.class, method = "selectById")
	public Map<String, Object> selectById(T obj);
	
	/**
	 * 
	 * @param obj
	 * @param filterList
	 * @return
	 */
	@SelectProvider(type = BaseTemplate.class, method = "selectAll")
	public List<Map<String, Object>> selectAll(T obj, List<WhereFilter> filterList);
	
	/**
	 * 
	 * @param obj
	 * @param filterList
	 * @param page
	 * @return
	 */
	@SelectProvider(type = BaseTemplate.class, method = "selectByPage")
	public List<Map<String, Object>> selectByPage(T obj, List<WhereFilter> filterList, PageUtil<T> page);

}
