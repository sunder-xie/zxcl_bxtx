package com.zxcl.webapp.biz.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.zxcl.webapp.dto.PolicySalesActivityFeeDTO;
import com.zxcl.webapp.dto.wallet.response.BillBaseRspDTO;

/**
 * @ClassName:
 * @Description:更新账单状态 
 * @author zxj
 * @date 
 */
public interface WalletRmiService {
	
	/**
	 * 更新结算单状态
	 * @param billBaseRspDTO
	 * @throws Exception
	 */
//	public void updBillStatus(BillBaseRspDTO billBaseRspDTO) throws Exception;
	
	
	//我就把这个对象传给你PolicySalesActivityFeeDTO
	//	list
	//PolicySelfActivityWallet
	
	/**
	 * 自营出单奖励
	 * @param billBaseRspDTO
	 * @return
	 * @throws Exception
	 * @param PolicySalesActivityFeeDTO
	 * @description
	 * HashMap=
	 * status={0:成功,4:失败}
	 * msg=状态说明
	 */
//	public HashMap<String, String> policySelfActivityWallet(ArrayList<PolicySalesActivityFeeDTO> policySalesActivityFeeDTOList) throws Exception;
}
