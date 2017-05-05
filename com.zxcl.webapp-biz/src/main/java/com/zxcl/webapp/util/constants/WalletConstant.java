package com.zxcl.webapp.util.constants;

import com.zxcl.webapp.biz.util.Remark;

/**
 * @ClassName: 
 * @Description:钱包常量池
 * @author zxj
 * @date 
 */
public class WalletConstant {
	
	/**
	 * 钱包能否使用名义代理人KEY
	 */
	public static final String KEY_WALLET_CAN_USE_AGENT = "BXTX_WALLET_CAN_USE_AGENT";
	
	/**
	 * 账单交易类型-提现
	 */
	public static final String TRANS_TYPE_TIXIAN = "1";
	
	/**
	 * 账单交易类型-报价活动
	 */
	public static final String TRANS_TYPE_ACT_QUOTA = "2";//针对被邀请人
	public static final String TRANS_TYPE_ACT_QUOTA_1 = "2_1";//针对邀请人
	
	/**
	 * 账单交易类型-出单活动
	 */
	public static final String TRANS_TYPE_ACT_VOTE = "3";//针对被邀请人
	public static final String TRANS_TYPE_ACT_VOTE_1 = "3_1";//针对邀请人
	
	
	
	/**
	 * 账单交易类型-注册活动
	 */
	public static final String TRANS_TYPE_ACT_REG = "4";//针对被邀请人
	public static final String TRANS_TYPE_ACT_REG_1 = "4_1";//针对被邀请人
	
	/**
	 * 账单交易类型-出单活动
	 */
	public static final String TRANS_TYPE_ACT_VOTE5 = "5";//针对被邀请人
	public static final String TRANS_TYPE_ACT_VOTE5_1 = "5_1";//针对邀请人
	
	/**
	 * 账单交易类型-拥金结算
	 */
	public static final String TRANS_TYPE_WALLET_POLICY_FEE = "8";
	
	/**
	 * 嘉诚商业险出单奖励交易类型-奖励结算
	 */
	public static final String TRANS_TYPE_POLICY_SALE_ACTIVITY_FEE = "9";
	
	/**
	 * 保行天下活动A，红包发放
	 */
	public static final String TRANS_TYPE_ACTIVITY_BXTXA_REDPACKET = "10";
	
	/**
	 * 自营出单奖励
	 */
	public static final String TRANS_TYPE_POLICY_SELF_ACTIVITY_FEE = "11";
	
	/**
	 * 嘉诚太平出单奖励(待激活)
	 */
	public static final String TRANS_TYPE_JIACHENGTAIPINGCHUDAN_ACTIVITY_FEE = "12";
	
	/**
	 * 嘉诚太平出单奖励(已到账)
	 */
	public static final String TRANS_TYPE_JIACHENGTAIPINGCHUDAN_1_ACTIVITY_FEE = "12_1";
	
	
	
	/*******************************************账单交易类型-报价活动(被邀请人)-状态**************************************************/
	/**
	 * 账单交易类型-报价活动(被邀请人)-状态-无效(未连续报价)
	 */
	@Remark("未连续")
	public static final String TRANS_TYPE_ACT_QUOTA_STATUS_DEL= "0";
	
	/**
	 * 账单交易类型-报价活动(被邀请人)-状态-待激活
	 */
	@Remark("待激活")
	public static final String TRANS_TYPE_ACT_QUOTA_STATUS_DJH= "1";
	
	/**
	 * 账单交易类型-出单活动(被邀请人)-状态-已到账
	 */
	@Remark("已到账")
	public static final String TRANS_TYPE_ACT_QUOTA_STATUS_YDZ= "6";
	
	/**
	 * 账单交易类型-出单活动(被邀请人)-状态-已结束
	 */
	@Remark("已结束")
	public static final String TRANS_TYPE_ACT_QUOTA_STATUS_DONE= "4";
	
	/*******************************************账单交易类型-报价活动(邀请人)-状态**************************************************/
	/**
	 * 账单交易类型-报价活动(邀请人)-状态-无效
	 */
	@Remark("无效")
	public static final String TRANS_TYPE_ACT_QUOTA_1_STATUS_DEL= "0";
	
	
	/**
	 * 账单交易类型-出单活动(邀请人)-状态-已到账
	 */
	@Remark("已到账")
	public static final String TRANS_TYPE_ACT_QUOTA_1_STATUS_YDZ= "6";
	
	/**
	 * 账单交易类型-出单活动(邀请人)-状态-已结算
	 */
	@Remark("已结算")
	public static final String TRANS_TYPE_WALLET_POLICY_FEE_STATUS6 = "6";
	
	/**
	 * 账单交易类型-注册活动(邀请人)-状态-已到账
	 */
	@Remark("已到账")
	public static final String TRANS_TYPE_ACT_REG_STATUS_YDZ= "6";
	
	/**
	 * 账单交易类型-注册活动(被邀请人)-状态-已到账
	 */
	@Remark("已到账")
	public static final String TRANS_TYPE_ACT_REG_1_STATUS_YDZ= "6";
	
	/**
	 * 账单交易类型-出单活动(被邀请人)-状态-无效
	 */
	@Remark("无效")
	public static final String TRANS_TYPE_ACT_VOTE_STATUS_DEL= "0";
	
	/**
	 * 账单交易类型-出单活动(被邀请人)-状态-已到账
	 */
	@Remark("已到账")
	public static final String TRANS_TYPE_ACT_VOTE_STATUS_YDZ= "6";
	
	/**
	 * 账单交易类型-出单活动(被邀请人)-状态-无效
	 */
	@Remark("无效")
	public static final String TRANS_TYPE_ACT_VOTE5_STATUS_DEL= "0";
	
	/**
	 * 账单交易类型-出单活动(被邀请人)-状态-已到账
	 */
	@Remark("已到账")
	public static final String TRANS_TYPE_ACT_VOTE5_STATUS_YDZ= "6";
	
	/**
	 * 账单交易类型-出单活动(邀请人)-状态-无效
	 */
	@Remark("无效")
	public static final String TRANS_TYPE_ACT_VOTE_1_STATUS_DEL= "0";
	
	/**
	 * 账单交易类型-出单活动(邀请人)-状态-已到账
	 */
	@Remark("已到账")
	public static final String TRANS_TYPE_ACT_VOTE_1_STATUS_YDZ= "6";
	
	/**
	 * 账单交易类型-出单活动(邀请人)-状态-无效
	 */
	@Remark("无效")
	public static final String TRANS_TYPE_ACT_VOTE5_1_STATUS_DEL= "0";
	
	/**
	 * 账单交易类型-出单活动(邀请人)-状态-已到账
	 */
	@Remark("已到账")
	public static final String TRANS_TYPE_ACT_VOTE5_1_STATUS_YDZ= "6";
	
	/**
	 * 账单交易类型-提现-状态-无效账单
	 */
	@Remark("无效")
	public static final String TRANS_TYPE_TIXIAN_STATUS_DEL= "0";
	
	/**
	 * 账单交易类型-提现-状态-审核中
	 */
	@Remark("审核中")
	public static final String TRANS_TYPE_TIXIAN_STATUS_CHECKING = "1";
	
	/**
	 * 账单交易类型-提现-状态-审核成功,已受理
	 */
	@Remark("已受理")
	public static final String TRANS_TYPE_TIXIAN_STATUS_CHECKSUCC = "2";
	
	/**
	 * 账单交易类型-提现-状态-审核失败
	 */
	@Remark("审核失败")
	public static final String TRANS_TYPE_TIXIAN_STATUS_CHECKFAIL = "3";
	
	/**
	 * 账单交易类型-提现-失败
	 */
	@Remark("提现失败")
	public static final String TRANS_TYPE_TIXIAN_STATUS_PAYFAIL = "4";
	
	/**
	 * 账单交易类型-提现-成功
	 */
	@Remark("提现成功")
	public static final String TRANS_TYPE_TIXIAN_STATUS_PAYSUCC = "6";
	
	/**
	 * 支付渠道:通联支付
	 */
	public static final String TRANS_TYPE_PAY_CHANNEL_TL = "1";
	
	/**
	 * 收支类型,收入I
	 */
	public static final String WALLET_BILL_TYPE_IN = "I";
	
	/**
	 * 收支类型,待激活收入WI
	 */
	public static final String WALLET_BILL_TYPE_WIN = "WI";
	
	
	/**
	 * 收支类型,支出O
	 */
	public static final String WALLET_BILL_TYPE_OUT = "O";
	
	/**
	 * 收支类型,待激活支出WO
	 */
	public static final String WALLET_BILL_TYPE_WOUT = "WO";
	
	/**
	 * 嘉诚出单活动 账单奖励
	 */
	@Remark("已结算")
	public static final String TRANS_TYPE_WALLET_POLICY_SALE_ACTIVITY_FEE_STATUS6 = "0";
	
	/**
	 * 保行天下红包活动A
	 */
	@Remark("已结算")
	public static final String TRANS_TYPE_WALLET_ACTIVITY_BXTXA_STATUS6 = "0";
	
	public static final String TRANS_TYPE_WALLET_POLICY_SEAL_ACTIVITY_FEE_STATUS0 = "0";
	
	public static final String TRANS_TYPE_WALLET_POLICY_SEAL_ACTIVITY_FEE_STATUS1 = "1";
	
	public static final String TRANS_TYPE_WALLET_POLICY_SEAL_ACTIVITY_FEE_STATUS6 = "6";

	/**
	 * 嘉诚太平出单奖励(已到账)
	 */
	public static final String TRANS_TYPE_JIACHENGTAIPINGCHUDAN_1_ACTIVITY_FEE_STATUS6 = "6";
	
}
