package com.zxcl.webapp.biz.util.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PageParam extends BaseParam {
	// 当前页
	private Integer currentPage;

	// 一页数
	private Integer pageSize;

	// 分页开始位置
	private Integer start;
	
	private Integer mealId;
	
	
	
	
	public Integer getMealId() {
		return mealId;
	}
	public void setMealId(Integer mealId) {
		this.mealId = mealId;
	}
	public PageParam(String operateUser, Date opetateDate){
		super(operateUser, opetateDate);
	}
	public PageParam(){
		super();
	}
	public PageParam(String operateUser, Date opetateDate,Integer currentPage, Integer pageSize){
		super(operateUser, opetateDate);
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		if(null == currentPage){
			currentPage = 1;
		}
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		if(null == pageSize){
			pageSize = 30;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStart() {
		if(null == start){
			start = 0;
		}
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
