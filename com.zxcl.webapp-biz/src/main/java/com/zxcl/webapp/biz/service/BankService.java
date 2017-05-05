package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.dto.BankDTO;

/**
 * 
* @ClassName:  BankService 
* @Description: 银行
* @author 赵晋
* @date 2016年2月24日 上午11:29:32
*
 */
public interface BankService {

    /**
     * 
    * @Title: findBank
    * @Description: 查询所有银行
    * @param @return
    * @return List<BankDTO>
    * @throws
     */
    public List<BankDTO> queryBankService();
    /**
     * 
    * @Title: findCodeByName
    * @Description: 根据银行名字查询编码
    * @param @param bankName
    * @param @return
    * @return String
    * @throws
     */
    public BankDTO findCodeByName(String bankName);

}
