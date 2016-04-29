package com.cube.framework.utils;

import java.io.Serializable;
import java.util.List;

import com.cube.framework.base.BasePojo;

public class PageUtil<T extends BasePojo> implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer pageSize = Integer.valueOf(1);
	private Integer pageNo = Integer.valueOf(1);
	private Integer pageCount = Integer.valueOf(1);
	private Integer totalCount = Integer.valueOf(1);
	private List<T> result;

	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize.intValue() < 0) {
			pageSize = Integer.valueOf(1);
		}
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(Integer pageNo) {
		if (pageNo.intValue() < 0) {
			pageNo = Integer.valueOf(1);
		}
		this.pageNo = pageNo;
	}

	public Integer getPageCount() {
		this.pageCount = Integer.valueOf(this.totalCount.intValue() / this.pageSize.intValue()
				+ (this.totalCount.intValue() % this.pageSize.intValue() != 0 ? 1 : 0));
		return this.pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getResult() {
		return this.result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
}