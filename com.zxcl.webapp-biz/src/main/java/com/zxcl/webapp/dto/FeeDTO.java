package com.zxcl.webapp.dto;

import java.math.BigDecimal;

public class FeeDTO {
	private BigDecimal ratio;
	
	private String isShow;

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	@Override
	public String toString() {
		return "FeeDTO [ratio=" + ratio + ", isShow=" + isShow + "]";
	}
	
	
}
