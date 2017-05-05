package com.zxcl.webapp.dto;


/**
 * 地区
 * 
 * @author 5555
 *
 */
public class AreaDTO {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 地区名称
	 */
	private String name;
	/**
	 * 车牌前缀
	 */
	private String shortname;

	/**
	 * 主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 主键
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 编号
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 编号
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 地区名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 地区名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 车牌前缀
	 */
	public String getShortname() {
		return shortname;
	}

	/**
	 * 车牌前缀
	 */
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Override
	public String toString() {
		return "Area [id=" + id + ", code=" + code + ", name=" + name
				+ ", shortname=" + shortname + "]";
	}

}
