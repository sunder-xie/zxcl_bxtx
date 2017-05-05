package com.zxcl.webapp.dto;

/**
 * 车型库A套，B套关联关系
 * @author xiaoxi
 *
 */
public class CarModelRelatedDTO {

	/**
	 * 精友车型库编码A
	 */
	private String modelCodeA;
	
	/**
	 * 精友车型库编码B
	 */
	private String modelCodeB;

	public String getModelCodeA() {
		return modelCodeA;
	}

	public void setModelCodeA(String modelCodeA) {
		this.modelCodeA = modelCodeA;
	}

	public String getModelCodeB() {
		return modelCodeB;
	}

	public void setModelCodeB(String modelCodeB) {
		this.modelCodeB = modelCodeB;
	}

	@Override
	public String toString() {
		return "CarModelRelatedDTO [modelCodeA=" + modelCodeA + ", modelCodeB="
				+ modelCodeB + "]";
	}
	
}
