package com.cube.framework.base;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cube.framework.utils.JSONUtils;
import com.cube.framework.utils.WebResultInfo;

public abstract class BaseController<T extends BasePojo> {
	
	@RequestMapping("list")
	public WebResultInfo list() {
		WebResultInfo resultInfo = new WebResultInfo();
		return resultInfo;
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public WebResultInfo add() {
		WebResultInfo resultInfo = new WebResultInfo();
		return resultInfo;
	}
	
	@RequestMapping(value="/view/{id}")
	public String view(@PathVariable("id") Object id) {
		WebResultInfo resultInfo = new WebResultInfo();
		if(id == null) {
			resultInfo.setCode(-1);
			resultInfo.setMsg("view by id failed! because id is null!");
			return JSONUtils.beanToJson(resultInfo).toJSONString();
		}
		T t = getService().getById(id);
		resultInfo.setCode(0);
		resultInfo.setMsg("view by id success!");
		resultInfo.setData(t);
		return JSONUtils.beanToJson(resultInfo).toJSONString();
	}
	
	@RequestMapping(value="edit",method=RequestMethod.POST)
	public WebResultInfo edit() {
		WebResultInfo resultInfo = new WebResultInfo();
		return resultInfo;
	}
	
	@RequestMapping(value="/del/{id}")
	public WebResultInfo del() {
		WebResultInfo resultInfo = new WebResultInfo();
		return resultInfo;
	}

	public abstract BaseService<T> getService();

}
