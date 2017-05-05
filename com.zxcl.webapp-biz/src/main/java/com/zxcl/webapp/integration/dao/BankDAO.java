package com.zxcl.webapp.integration.dao;

import java.util.List;

import com.zxcl.webapp.dto.BankDTO;

/**
 * 
 * @ClassName: BankDAO
 * @Description: TODO
 * @author 赵晋
 * @date 2016年2月24日 上午11:14:16
 *
 */
public interface BankDAO {
    /**
     * 
    * @Title: findBank
    * @Description: 查询所有银行
    * @param @return
    * @return List<BankDTO>
    * @throws
     */
    public List<BankDTO> findBankDAO();
    /**
     * 
    * @Title: findCodeByName
    * @Description: 根据银行名字查询编码
    * @param @param BankDTO
    * @param @return
    * @return String
    * @throws
     */
    public BankDTO findCodeByNameDAO(String bankName);
    
    /**
     * findBankByCode
     * @param code
     * @return
     */
    public BankDTO findBankByCode(String code);
}
