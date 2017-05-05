package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.IdentityInfoService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.dto.MicroApproveDTO;
import com.zxcl.webapp.dto.MicroApproveFileDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.integration.dao.MicroApproveDAO;
import com.zxcl.webapp.integration.dao.MicroApproveFileDAO;
import com.zxcl.webapp.integration.dao.MicroDAO;
import com.zxcl.webapp.integration.dao.UserDAO;
import com.zxcl.webapp.util.IdcardValidator;


/**
 * 身份认证信息实现类
 * @author zxj
 * @date 2016年8月22日
 * @description 
 */
@Service
public class IdentityInfoServiceImpl implements IdentityInfoService {
	
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MicroApproveDAO microApproveDAO;
	
	@Autowired
	private MicroApproveFileDAO microApproveFileDAO;
	
	@Autowired
	private MicroDAO microDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private IdcardValidator idcardValidator;
	

	@Override
	public boolean isConfirmed(String userId) {
		boolean r = false;
		MicroApproveDTO ma = findConfirm(userId, false);
		if(null != ma && "2".equals(ma.getApproveState()+"")){
			r = true;
		}
		logger.info("r="+r);
		return r;
	}

	@Override
	public boolean isNeedApprove(String userId) {
		String need = microApproveDAO.isNeedApporve(userId);
		logger.info("need="+need);
		if("1".equals(need)){
			return true;
		}
		return false;
	}
	
	@Override
	public synchronized void confirmIndentityInfo(String userId, String fileIds, String iname, String icardid) throws Exception {
		logger.info("confirmIndentityInfo ==> userId="+userId+",fileIds="+fileIds+",iname="+iname+",icardid="+icardid);
		
		//校验
		if(StringUtils.isBlank(userId)){
			throw new BusinessServiceException("用户名必传");
		}
		if(StringUtils.isBlank(fileIds)){
			throw new BusinessServiceException("附件必传");
		}
		if(StringUtils.isBlank(iname)){
			throw new BusinessServiceException("真实姓名必传");
		}
		if(!iname.matches("[\\u4e00-\\u9fa5]+")){
			throw new BusinessServiceException("真实姓名为中文名称，不能包含字母数字等特殊字符");
		}
		if(StringUtils.isBlank(icardid)){
			throw new BusinessServiceException("身份证号码必传");
		}
		if(!idcardValidator.isValidatedAllIdcard(icardid)){
			throw new BusinessServiceException("身份证号码合法性校验不通过，请输入真实有效的身份证号");
		}
		//附件整理
		String ids[] = StringUtils.split(fileIds, ",");
		List<String> idList = Arrays.asList(ids);
		List<String> uploadIdList = new ArrayList<String>(idList.size());
		for(String id : idList){
			if(StringUtils.isNotBlank(id)){
				uploadIdList.add(id);
			}
		}
		if(idList.size() < 2){
			throw new BusinessServiceException("请上传身份证正面和反面");
		}
		
		MicroDTO m = microService.getMicroByUserId(userId);
		MicroApproveDTO ma = findConfirm(userId, false);
		if(null != ma){
			if("1".equals(ma.getApproveState()+"")){
				throw new BusinessServiceException("已提交认证，请勿重复提交认证");
			}
			if("2".equals(ma.getApproveState()+"")){
				throw new BusinessServiceException("已认证成功，请勿重复提交认证");
			}
			
			logger.info("使["+ma.getApproveId()+"]无效");
			int c = microApproveDAO.updApproveForInvalid(ma.getApproveId());
			logger.info("影响行数"+c);
		}
		
		
		//认证信息存库
		ma = new MicroApproveDTO();
		ma.setApproveState(1);
		ma.setCrtCde(userId);
		ma.setMicroCardId(icardid);
		ma.setMicroRealName(iname);
		ma.setMicroId(m.getMicro_id());
		ma.setStatus(1);
		ma.setUpdCde(userId);
		logger.info("ma="+ma);
		int c = microApproveDAO.insertSelective(ma);
		logger.info("影响行数"+c);
		
		//附件存库
		MicroApproveFileDTO mf = new MicroApproveFileDTO();
		mf.setStatus(1);
		mf.setCrtCde(userId);
		mf.setUpdCde(userId);
		mf.setApproveId(ma.getApproveId());
		for(String id : uploadIdList){
			mf.setFileId(id);
			logger.info("mf="+mf);
			c = microApproveFileDAO.insertSelective(mf);
			logger.info("影响行数"+c);
		}
		
		//姓名提交审核的时候就更新
		//不等到审核通过再更新
		//姚振东说：他要提现，乱写干啥
		logger.info("修改小微表用户表姓名为持卡人姓名");
		microDAO.updateMicroName(userId, iname);
		userDAO.updateUserName(userId, iname);
		
	}

	@Override
	public MicroApproveDTO findConfirm(String userId, boolean findFile) {
		logger.info("findConfirm ==> userId="+userId+",findFile="+findFile);
		MicroApproveDTO r = null;
		try {
			MicroDTO m = microService.getMicroByUserId(userId);
			r = microApproveDAO.findConfirm(m.getMicro_id());
			
			if(null != r && findFile){
				r.setFiles(microApproveFileDAO.findFileListByApproveId(r.getApproveId()));
			}
		} catch (BusinessServiceException e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("MicroApproveDTO <== "+r);
		return r;
	}
	
}
