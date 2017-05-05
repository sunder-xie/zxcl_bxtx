package com.zxcl.webapp.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.BankService;
import com.zxcl.webapp.dto.BankDTO;
import com.zxcl.webapp.integration.dao.BankDAO;

/**
 * 
* @ClassName:  BankService 
* @Description: 银行
* @author 赵晋
* @date 2016年2月24日 上午11:29:32
*
 */
@Service
public class BankServiceImpl implements BankService{
    @Autowired
    private BankDAO bankDAO;
    /**
     * 
    * @Title: findBank
    * @Description: 查询所有银行
    * @param @return
    * @return List<BankDTO>
    * @throws
     */
    public List<BankDTO> queryBankService() {
        return bankDAO.findBankDAO();
    }
    /**
     * 
    * @Title: findCodeByName
    * @Description: 根据银行名字查询编码
    * @param @param bankName
    * @param @return
    * @return String
    * @throws
     */
    public BankDTO findCodeByName(String bankName) {
        return bankDAO.findCodeByNameDAO(bankName);
    }

}
