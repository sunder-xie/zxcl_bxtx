package com.zxcl.webapp.dto.port.funde.quota;

import java.util.List;








import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@XmlAccessorType()
@XmlRootElement
public class RESPONSE {
	
	@XmlElement(name="TRAN_CODE")
	private String TRAN_CODE;
	
	@XmlElement(name="BANK_CODE")
	private String BANK_CODE;
	
	@XmlElement(name="BK_SERIAL")
	private String BK_SERIAL;
	
	@XmlElement(name="SL_ACCT_DATE")
	private String SL_ACCT_DATE;
	
	@XmlElement(name="SL_ACCT_TIME")
	private String SL_ACCT_TIME;
	
	@XmlElement(name="SL_RSLT_CODE")
	private String SL_RSLT_CODE;
	
	@XmlElement(name="SL_RSLT_MESG")
	private String SL_RSLT_MESG;
	
	@XmlElementWrapper(name="GgCarModelDtoList")
	@XmlElement(name="VehicleModelData")
	private List<VehicleModelData> GgCarModelDtoList;
	

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	public RESPONSE(String tRAN_CODE, String bANK_CODE, String bK_SERIAL,
			String sL_ACCT_DATE, String sL_ACCT_TIME, String sL_RSLT_CODE,
			String sL_RSLT_MESG, List<VehicleModelData> ggCarModelDtoList) {
		super();
		TRAN_CODE = tRAN_CODE;
		BANK_CODE = bANK_CODE;
		BK_SERIAL = bK_SERIAL;
		SL_ACCT_DATE = sL_ACCT_DATE;
		SL_ACCT_TIME = sL_ACCT_TIME;
		SL_RSLT_CODE = sL_RSLT_CODE;
		SL_RSLT_MESG = sL_RSLT_MESG;
		GgCarModelDtoList = ggCarModelDtoList;
	}
	public RESPONSE() {
		super();
	}
}
