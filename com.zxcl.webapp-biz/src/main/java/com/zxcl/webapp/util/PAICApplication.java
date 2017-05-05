package com.zxcl.webapp.util;

import org.springframework.stereotype.Service;

/**
 * 
* @ClassName:  PAICApplication 
* @Description: 平安变量
* @author 赵晋
* @date 2015年12月10日 下午6:02:41
*
 */
@Service
public class PAICApplication {
	/**
	 * 获取token
	 */
//	public static  String GET_TOKEN="/oauth/oauth2/access_token?client_id=P_SCJIACHENG_AUTO&grant_type=client_credentials&client_secret=29pnV4Wm";
//	public static  String GET_TOKEN="/oauth/oauth2/access_token?client_id=P_HUI_ZE_AUTO&grant_type=client_credentials&client_secret=v2Vf4zQ6";
	/**
	 * 标准申请投保
	 */
	public static  String GET_APPLYPOLICYBYIBCS = "/appsvr/property/applyPolicyByIBCS?request_id=applyPolicyByIBCS";
	
	
	/**
	 * applyCustToFinance
	 * h5移动支付
	 */
	public static  String GET_APPLYCUSTTOFINANCE = "/appsvr/property/applyCustToFinance?request_id=applyCustToFinance";
	
	/**
	 * 标准取特别约定
	 */
	public static  String GET_SPECIALPROMISEBYIBCS = "/appsvr/property/specialPromiseByIBCS?request_id=specialPromiseByIBCS";
	/**
	 * 标准承保
	 */
	public static  String GET_ACCEPTBYIBCS = "/appsvr/property/acceptByIbcs?request_id=acceptByIbcs";
	/**
	 * 标准财务开单
	 */
	public static  String GET_NOTICEDOBYNPS = "/appsvr/property/noticeDoByNps?request_id=noticeDoByNps";
	/**
	 * 支付
	 */
	public static  String GET_QRCODE = "/appsvr/property/getQRcode?request_id=getQRcode001";
	
	/**
	 * 标准财务通知到账
	 */
	public static  String GET_NOTICEDONEBYNPS = "/appsvr/property/noticeDoneByNps?request_id=noticeDoneByNps";
	/**
	 * 标准保费计算
	 */
	public static  String GET_STANDARDCALCULATEPREMIUM = "/appsvr/property/standardCalculatePremium?request_id=standardCalculatePremium";
	/**
	 * 标准平台车型查询
	 */
	public static  String GET_VEHICLEQUERYBYIBCS = "/appsvr/property/vehicleQueryByIbcs?request_id=vehicleQueryByIbcs";

	/**
	 * 标准平台投保查询
	 */
	public static  String GET_CIRCQUERYBYIBCS = "/appsvr/property/circQueryByIbcs?request_id=circQueryByIbcs";
	
}
