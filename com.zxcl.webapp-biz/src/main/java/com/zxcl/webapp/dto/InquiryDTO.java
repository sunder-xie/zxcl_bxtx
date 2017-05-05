package com.zxcl.webapp.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.biz.util.DateUtils;
import com.zxcl.webapp.dto.port.comdto.AttestationDTO;
import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;

/**
 * 询价单信息
 * 
 * @author 5555
 *
 */
public class InquiryDTO extends BaseDTO implements Cloneable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private String inquiryId;

	/**
	 * 行驶区域
	 */
	private String drivingReg;

	/**
	 * 车牌号
	 */
	private String plateNo;

	/**
	 * 省级编码
	 */
	private String provinceCode;

	/**
	 * 市级编码
	 */
	private String cityCode;
	
	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 车主名称
	 */
	private String ownerName;

	/**
	 * 发动机号
	 */
	private String engNo;

	/**
	 * 车架号
	 */
	private String frmNo;

	/**
	 * 初登年月:年月日(20130201)
	 */
	private Date fstRegNo;
	
	private String fstRegNoStr;

	/**
	 * 座位数
	 */
	private int seatNum;

	/**
	 * 排量
	 */
	private BigDecimal displacement;
	private String displacementStr;

	/**
	 * 车型编码
	 */
	private String modelCode;

	/**
	 * 车辆的名称
	 */
	private String vehicleName;

	/**
	 * 车辆价格
	 * 
	 */
	private String vehiclePrice;
	
	private String ejinTaivehiclePrice;

	/**
	 * 过户日期
	 */
	private Date transferDate;
	private String transferDateStr;

	/**
	 * 车子的描述
	 */
	private String remark;

	/**
	 * 新车标志
	 * 富德：N新车 0旧车
	 */
	private String newCarSign ;
	/**
	 * 过户车辆标志
	 */
	private String transferSign;
	


	/**
	 * 交强险开始时间
	 */
	private Date tciStartDate;

	/**
	 * 交强险结束时间
	 */
	private Date tciEndDate;

	/**
	 * 商业险开始时间
	 */
	private Date vciStartDate;

	/**
	 * 商业险结束时间
	 */
	private Date vciEndDate;
	

	/**
	 * 交强险开始时间字符串类型
	 */
	private String tciStartDateStr;

	/**
	 * 交强险结束时间字符串类型
	 */
	private String tciEndDateStr;

	/**
	 * 商业险开始时间字符串类型
	 */
	private String vciStartDateStr;

	/**
	 * 商业险结束时间字符串类型
	 */
	private String vciEndDateStr;
	

	/**
	 * 交强险标识
	 */
	private String tciSign;

	/**
	 * 商业险标识
	 */
	private String vciSign;
	
	private String queryStage;

	private OrderDTO order;

	private List<QuotaDTO> quotaList = new ArrayList<QuotaDTO>();

	private String insId;
	
	/**
	 * 证件类型
	 */
	private String certfCdeType;
	/**
	 * 证号
	 */
	private String certfCde;

	/**
	 * 流水号
	 */
	private String serialNumber;
	
	/**
	 * 状态0-正常(默认) 1-暂存数据
	 */
	private String state="0";
	/**
	 * 状态中文说明
	 */
	private String stateName;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 车主身份证号
	 */
	private String  ownerCertNo;
	/**
	 * 车主生日
	 */
	private String  ownerBirthday;
	/**
	 * 车主性别(1-男士 2-女士)
	 */
	private String  ownerSex ;
	/**
	 * 车主年龄
	 */
	private String  ownerAge;

	
	private Date crtTm;
	
	/**
	 * 报价失败保险公司数
	 */
	private String insQuotaFailCount;
	
	/**
	 * 报价失败信息
	 */
	private String insQuotaFailInfo;
	
	/**
	 * 富德：业务号
	 */
	private String businessNo;
	
	/**
	 * 生日
	 */
	private Date birth;
	private String birthStr;
	
	/**
	 * 性别
	 */
	private int sex;
	
	/**
	 * 富德：本地车标志  1	本地车2	外地车 
	 */
	private String localVehicleInd;
	
	/**
	 * 富德：使用年限
	 */
	private String useYears;
	
	/**
	 * 核定载质量
	 */
	private String tonCount;
	
	/**
	 * 重复投保信息
	 */
	private String reInsureInfo;
	/**
	 * 年款
	 */
	private String marketDate;
	/**
	 * 车型精友库标志，A套，B套
	 */
	private String modelCodeType;
	
	private AttestationDTO attestationBase;
	
	private String realPrice;
	
	private VoInquiryCustomerDTO voInquiryCustomerDTO;
	
	/**
	 * 报价单信息
	 */
	private List<OrderDTO> orderList;
	
	/**
	 * 业务类型
	 */
	private String usageCode;
	
	
	public VoInquiryCustomerDTO getVoInquiryCustomerDTO() {
		return voInquiryCustomerDTO;
	}


	public void setVoInquiryCustomerDTO(VoInquiryCustomerDTO voInquiryCustomerDTO) {
		this.voInquiryCustomerDTO = voInquiryCustomerDTO;
	}


	public Date getBirth() {
		return birth;
	}

	
	public String getTonCount() {
		return tonCount;
	}


	public void setTonCount(String tonCount) {
		this.tonCount = tonCount;
	}


	public String getLocalVehicleInd() {
		return localVehicleInd;
	}


	public String getRealPrice() {
		if(StringUtils.isBlank(this.realPrice)){
			realPrice = this.vehiclePrice;
		}
		return realPrice;
	}


	public void setRealPrice(String realPrice) {
		this.realPrice = realPrice;
	}


	public void setLocalVehicleInd(String localVehicleInd) {
		this.localVehicleInd = localVehicleInd;
	}


	public String getUseYears() {
		return useYears;
	}


	public void setUseYears(String useYears) {
		this.useYears = useYears;
	}


	public void setBirth(Date birth) {
		if(null != birth){
			this.birthStr = DateUtil.dateToString(DateUtil.YYYY_MM_DD, birth);
		}
		this.birth = birth;
	}

	public String getBirthStr() {
		return birthStr;
	}

	public void setBirthStr(String birthStr) {
		this.birthStr = birthStr;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public Date getCrtTm() {
		return crtTm;
	}

	public String getEjinTaivehiclePrice() {
		return ejinTaivehiclePrice;
	}


	public void setEjinTaivehiclePrice(String ejinTaivehiclePrice) {
		this.ejinTaivehiclePrice = ejinTaivehiclePrice;
	}


	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}

	public String getOwnerCertNo() {
		return ownerCertNo;
	}

	public void setOwnerCertNo(String ownerCertNo) {
		this.ownerCertNo = ownerCertNo;
	}

	public String getOwnerBirthday() {
		return ownerBirthday;
	}

	public void setOwnerBirthday(String ownerBirthday) {
		this.ownerBirthday = ownerBirthday;
	}

	public String getOwnerSex() {
		return ownerSex;
	}

	public void setOwnerSex(String ownerSex) {
		this.ownerSex = ownerSex;
	}

	public String getOwnerAge() {
		return ownerAge;
	}

	public void setOwnerAge(String ownerAge) {
		this.ownerAge = ownerAge;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public InquiryDTO() {
		super();
	}

	public String getCertfCde() {
		return certfCde;
	}

	public void setCertfCde(String certfCde) {
		this.certfCde = certfCde;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public InquiryDTO(String inquiryId) {
		super();
		this.inquiryId = inquiryId;
	}

	public String getDrivingReg() {
		return drivingReg;
	}

	public void setDrivingReg(String drivingReg) {
		this.drivingReg = drivingReg;
	}

	public void setTransferDateStr(String transferDateStr) {
		this.transferDateStr = transferDateStr;
	}

	public String getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	/**
	 * 车牌号
	 */
	public String getPlateNo() {
		return plateNo;
	}

	/**
	 * 车牌号
	 */
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * 发动机号
	 */
	public String getEngNo() {
		return engNo;
	}

	/**
	 * 发动机号
	 */
	public void setEngNo(String engNo) {
		this.engNo = engNo;
	}

	/**
	 * 车架号
	 */
	public String getFrmNo() {
		return frmNo;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * 车架号
	 */
	public void setFrmNo(String frmNo) {
		this.frmNo = frmNo;
	}

	/**
	 * 初登年月:年月日(20130201)
	 */
	public Date getFstRegNo() {
		return fstRegNo;
	}

	/**
	 * 初登年月:yyyy-MM-dd
	 */
	public String getFstRegNoStr() {
		return null != fstRegNo ? DateUtil.dateToString(DateUtil.YYYY_MM_DD,
				fstRegNo) : null;
	}

	public AttestationDTO getAttestationBase() {
		return attestationBase;
	}


	public void setAttestationBase(AttestationDTO attestationBase) {
		this.attestationBase = attestationBase;
	}


	/**
	 * 初登年月:年月日(20130201)
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setFstRegNo(Date fstRegNo) {
		this.fstRegNo = fstRegNo;
	}

	
	public void setFstRegNoStr(String fstRegNoStr) {
		this.fstRegNoStr = fstRegNoStr;
	}

	/**
	 * 座位数
	 */
	public int getSeatNum() {
		return seatNum;
	}

	/**
	 * 座位数
	 */
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	/**
	 * 排量
	 */
	public BigDecimal getDisplacement() {
		return displacement;
	}

	/**
	 * 排量
	 */
	public void setDisplacement(BigDecimal displacement) {
		this.displacement = displacement;
	}

	/**
	 * 车型编码
	 */
	public String getModelCode() {
		return modelCode;
	}

	/**
	 * 车型编码
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehiclePrice() {
		return vehiclePrice;
	}

	public void setVehiclePrice(String vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}

	public String getTransferDateStr() {
		return null!=transferDate?DateUtil.dateToString("yyyy-MM-dd", transferDate):null;
	}
	
	public Date getTransferDate() {
		return transferDate;
	}
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNewCarSign() {
		return newCarSign;
	}

	public void setNewCarSign(String newCarSign) {
		this.newCarSign = newCarSign;
	}

	public String getTransferSign() {
		return transferSign;
	}

	public void setTransferSign(String transferSign) {
		this.transferSign = transferSign;
	}

	public String getQueryStage() {
		return queryStage;
	}

	public void setQueryStage(String queryStage) {
		this.queryStage = queryStage;
	}

	public OrderDTO getOrder() {
		return order;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
	}

	public List<QuotaDTO> getQuotaList() {
		return quotaList;
	}
	
	public QuotaDTO getQuotaByQuotaId(String quotaId) {
		if(StringUtils.isNotBlank(quotaId) && getQuotaList() != null && getQuotaList().size() > 0){
			for(QuotaDTO item : getQuotaList()){
				if(quotaId.equals(item.getQuotaId())){
					item.getTotalAmount();
					return item;
				}
			}
		}
		return null;
	}

	public void setQuotaList(List<QuotaDTO> quotaList) {
		this.quotaList = quotaList;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}


	public String getDisplacementStr() {
		return displacementStr;
	}

	public void setDisplacementStr(String displacementStr) {
		this.displacementStr = displacementStr;
	}

	public Date getTciStartDate() {
		return tciStartDate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setTciStartDate(Date tciStartDate) {
		this.tciStartDate = tciStartDate;
	}

	public Date getTciEndDate() {
		return tciEndDate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setTciEndDate(Date tciEndDate) {
		this.tciEndDate = tciEndDate;
	}

	public Date getVciStartDate() {
		return vciStartDate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setVciStartDate(Date vciStartDate) {
		this.vciStartDate = vciStartDate;
	}

	public Date getVciEndDate() {
		return vciEndDate;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setVciEndDate(Date vciEndDate) {
		this.vciEndDate = vciEndDate;
	}

	public String getTciSign() {
		return tciSign;
	}

	public void setTciSign(String tciSign) {
		this.tciSign = tciSign;
	}

	public String getVciSign() {
		return vciSign;
	}

	public void setVciSign(String vciSign) {
		this.vciSign = vciSign;
	}
	
	public String getTciStartDateStr(){
		if(this.tciStartDate != null){
			try {
				this.tciStartDateStr = DateUtils.getDateToStr(this.tciStartDate, "yyyy-MM-dd");
				return this.tciStartDateStr;
			} catch (Exception e) {
				return "";
			}
		}else{
			return "";
		}
	}
	
	public void setTciEndDateStr(String tciEndDateStr) {
		this.tciEndDateStr = tciEndDateStr;
	}

	public void setVciStartDateStr(String vciStartDateStr) {
		this.vciStartDateStr = vciStartDateStr;
	}

	public void setVciEndDateStr(String vciEndDateStr) {
		this.vciEndDateStr = vciEndDateStr;
	}

	public void setTciStartDateStr(String tciStartDateStr) {
		this.tciStartDateStr = tciStartDateStr;
	}

	public String getTciEndDateStr(){
		if(this.tciEndDate != null){
			try {
				this.tciEndDateStr = DateUtils.getDateToStr(this.tciEndDate, "yyyy-MM-dd");
				return DateUtils.getDateToStr(this.tciEndDate, "yyyy-MM-dd");
			} catch (Exception e) {
				return "";
			}
		}else{
			return "";
		}
	}
	
	public String getVciStartDateStr(){
		if(this.vciStartDate != null){
			try {
				this.vciStartDateStr = DateUtils.getDateToStr(this.vciStartDate, "yyyy-MM-dd");
				return DateUtils.getDateToStr(this.vciStartDate, "yyyy-MM-dd");
			} catch (Exception e) {
				return "";
			}
		}else{
			return "";
		}
	}
	public String getVciEndDateStr(){
		if(this.vciEndDate != null){
			try {
				this.vciEndDateStr = DateUtils.getDateToStr(this.vciEndDate, "yyyy-MM-dd");
				return DateUtils.getDateToStr(this.vciEndDate, "yyyy-MM-dd");
			} catch (Exception e) {
				return "";
			}
		}else{
			return "";
		}
	}

	public String getCertfCdeType() {
		return certfCdeType;
	}

	public void setCertfCdeType(String certfCdeType) {
		this.certfCdeType = certfCdeType;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getInsQuotaFailCount() {
		return insQuotaFailCount;
	}

	public void setInsQuotaFailCount(String insQuotaFailCount) {
		this.insQuotaFailCount = insQuotaFailCount;
	}

	public String getInsQuotaFailInfo() {
		return insQuotaFailInfo;
	}

	public void setInsQuotaFailInfo(String insQuotaFailInfo) {
		this.insQuotaFailInfo = insQuotaFailInfo;
	}


	public String getReInsureInfo() {
		return reInsureInfo;
	}


	public void setReInsureInfo(String reInsureInfo) {
		this.reInsureInfo = reInsureInfo;
	}


	/**
	 * 富德：车辆种类
	 */
	private String carKindCodeShow;
	
	/**
	 * 富德：车辆类型描述
	 * 国产车 进口车 合资车。。。
	 */
	private String vehicleTypeDesc;
	
	/**
	 * 富德：国别性质代码
	 */
	private String countryNature;

	public String getCarKindCodeShow() {
		return carKindCodeShow;
	}


	public String getCountryNature() {
		return countryNature;
	}


	public void setCountryNature(String countryNature) {
		this.countryNature = countryNature;
	}


	public String getVehicleTypeDesc() {
		return vehicleTypeDesc;
	}


	public void setVehicleTypeDesc(String vehicleTypeDesc) {
		this.vehicleTypeDesc = vehicleTypeDesc;
	}


	public void setCarKindCodeShow(String carKindCodeShow) {
		this.carKindCodeShow = carKindCodeShow;
	}


	public String getMarketDate() {
		return marketDate;
	}


	public void setMarketDate(String marketDate) {
		this.marketDate = marketDate;
	}


	public String getModelCodeType() {
		return modelCodeType;
	}


	public void setModelCodeType(String modelCodeType) {
		this.modelCodeType = modelCodeType;
	}


	public List<OrderDTO> getOrderList() {
		return orderList;
	}


	public void setOrderList(List<OrderDTO> orderList) {
		this.orderList = orderList;
	}


	public String getUsageCode() {
		return usageCode;
	}


	public void setUsageCode(String usageCode) {
		this.usageCode = usageCode;
	}
	
	
}
