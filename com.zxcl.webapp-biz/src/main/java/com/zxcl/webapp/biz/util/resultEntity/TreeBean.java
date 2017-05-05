package com.zxcl.webapp.biz.util.resultEntity;

import java.util.List;

public class TreeBean {
	private Integer id;
	private String text;
	private List<TreeBean> child;
	private Integer level;
	private Integer def;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TreeBean> getChild() {
		return this.child;
	}

	public void setChild(List<TreeBean> child) {
		this.child = child;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getDef() {
		return this.def;
	}

	public void setDef(Integer def) {
		this.def = def;
	}
}