package com.zxcl.webapp.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

/**
 * 订单
 * 
 * @author 5555
 *
 */
public class OrderDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	public OrderDTO() {
		super();
	}

	public OrderDTO(String orderId, InsuranceDTO insurance) {
		super();
		this.orderId = orderId;
		this.insurance = insurance;
	}

	/**
	 * 主键
	 */
	private String orderId;

	/**
	 * 询价
	 */
	private InquiryDTO inquiry;

	/**
	 * 报价
	 */
	private QuotaDTO quota;

	/**
	 * 保险公司
	 */
	private InsuranceDTO insurance;

	/**
	 * 小薇账户
	 */
	private MicroDTO micro;
	
	/**
	 * 订单号
	 */
	private String noticeNo;
	
	/**
	 * 联合投保单号
	 */
	private String carApplyNo;

	/**
	 * 交强险投保单号
	 */
	private String tciApplyNo;

	/**
	 * 商业险投保单号
	 */
	private String vciApplyNo;

	/**
	 * 核保时间
	 */
	private Date underTime;

	/**
	 * 订单创建时间
	 */
	private Date creatTime;

	/**
	 * 商业险保单号
	 */
	private String vciPlyNo;

	/**
	 * 交强险保单号
	 */
	private String tciPlyNo;

	/**
	 * 保单号创建时间
	 */
	private Date plyCrtTime;

	/**
	 * 状态:有效或者无效
	 */
	private String status;

	/**
	 * 订单进行中的状态
	 */
	private String forStatus;

	/**
	 * 支付单号
	 */
	private String payId;

	/**
	 * 支付方式
	 */
	private String payWay;

	private Date payTime;

	/*
	 * 查询增加的 query_stage
	 */

	private String queryStage;

	// 被保险人姓名

	private String name;

	// 付款方姓名
	private String payName;

	// 总金额
	private BigDecimal totMoney;

	private String microId;

	/**
	 * 主键
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 主键
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	/**
	 * 询价
	 */
	public InquiryDTO getInquiry() {
		return inquiry;
	}

	/**
	 * 询价
	 */
	public void setInquiry(InquiryDTO inquiry) {
		this.inquiry = inquiry;
	}

	/**
	 * 报价
	 */
	public QuotaDTO getQuota() {
		return quota;
	}

	/**
	 * 报价
	 */
	public void setQuota(QuotaDTO quota) {
		this.quota = quota;
	}

	/**
	 * 保险公司
	 */
	public InsuranceDTO getInsurance() {
		return insurance;
	}

	/**
	 * 保险公司
	 */
	public void setInsurance(InsuranceDTO insurance) {
		this.insurance = insurance;
	}

	/**
	 * 小薇账户
	 */
	public MicroDTO getMicro() {
		return micro;
	}

	/**
	 * 小薇账户
	 */

	public void setMicro(MicroDTO micro) {
		this.micro = micro;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	/**
	 * 联合投保单号
	 */
	public String getCarApplyNo() {
		return carApplyNo;
	}

	/**
	 * 联合投保单号
	 */
	public void setCarApplyNo(String carApplyNo) {
		this.carApplyNo = carApplyNo;
	}

	/**
	 * 交强险投保单号
	 */

	public String getTciApplyNo() {
		return tciApplyNo;
	}

	/**
	 * 交强险投保单号
	 */

	public void setTciApplyNo(String tciApplyNo) {
		this.tciApplyNo = tciApplyNo;
	}

	/**
	 * 商业险投保单号
	 */
	public String getVciApplyNo() {
		return vciApplyNo;
	}

	/**
	 * 商业险投保单号
	 */
	public void setVciApplyNo(String vciApplyNo) {
		this.vciApplyNo = vciApplyNo;
	}

	/**
	 * 核保时间
	 */
	public Date getUnderTime() {
		return underTime;
	}

	/**
	 * 核保时间
	 */
	public void setUnderTime(Date underTime) {
		this.underTime = underTime;
	}

	/**
	 * 订单创建时间
	 */
	public Date getCreatTime() {
		return creatTime;
	}

	/**
	 * 订单创建时间
	 */
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	/**
	 * 商业险保单号
	 */
	public String getVciPlyNo() {
		return vciPlyNo;
	}

	/**
	 * 商业险保单号
	 */
	public void setVciPlyNo(String vciPlyNo) {
		this.vciPlyNo = vciPlyNo;
	}

	/**
	 * 交强险保单号
	 */
	public String getTciPlyNo() {
		return tciPlyNo;
	}

	/**
	 * 交强险保单号
	 */
	public void setTciPlyNo(String tciPlyNo) {
		this.tciPlyNo = tciPlyNo;
	}

	/**
	 * 保单号创建时间
	 */
	public Date getPlyCrtTime() {
		return plyCrtTime;
	}

	/**
	 * 保单号创建时间
	 */
	public void setPlyCrtTime(Date plyCrtTime) {
		this.plyCrtTime = plyCrtTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 订单进行中的状态
	 */
	public String getForStatus() {
		return forStatus;
	}

	/**
	 * 订单进行中的状态
	 */

	public void setForStatus(String forStatus) {
		this.forStatus = forStatus;
	}

	public String getQueryStage() {
		return queryStage;
	}

	public void setQueryStage(String queryStage) {
		this.queryStage = queryStage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public BigDecimal getTotMoney() {
		return totMoney;
	}

	public void setTotMoney(BigDecimal totMoney) {
		this.totMoney = totMoney;
	}

	public String getMicroId() {
		return microId;
	}

	public void setMicroId(String microId) {
		this.microId = microId;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	@Override
	public String toString() {
		return "OrderDTO [orderId=" + orderId + ", inquiry=" + inquiry
				+ ", quota=" + quota + ", insurance=" + insurance + ", micro="
				+ micro + ", noticeNo=" + noticeNo + ", carApplyNo="
				+ carApplyNo + ", tciApplyNo=" + tciApplyNo + ", vciApplyNo="
				+ vciApplyNo + ", underTime=" + underTime + ", creatTime="
				+ creatTime + ", vciPlyNo=" + vciPlyNo + ", tciPlyNo="
				+ tciPlyNo + ", plyCrtTime=" + plyCrtTime + ", status="
				+ status + ", forStatus=" + forStatus + ", payId=" + payId
				+ ", payWay=" + payWay + ", payTime=" + payTime
				+ ", queryStage=" + queryStage + ", name=" + name
				+ ", payName=" + payName + ", totMoney=" + totMoney
				+ ", microId=" + microId + "]";
	}
}
