package com.zxcl.webapp.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;

import com.zxcl.webapp.biz.service.AreaService;
import com.zxcl.webapp.biz.service.IManageDispatchService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.biz.util.model.BaseParam;
import com.zxcl.webapp.dto.AreaDTO;
import com.zxcl.webapp.dto.ManageDispatchDTO;
import com.zxcl.webapp.integration.dao.ManageDispatchDAO;

@Service
public class ManageDispatchServiceImpl implements IManageDispatchService {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ManageDispatchDAO manageDao;
	
	@Autowired
	private AreaService areaService;
	
	private static final String MANAGE_DISPATCH_PREFIXNAME="EA";
	
	@Override
	@Log("新增配送信息")
	public void insertManageDispatch(ManageDispatchDTO manageDispatchDTO,BaseParam baseParam) throws BusinessServiceException {
		logger.info("新增配送信息 入参    ManageDispatchDTO："+manageDispatchDTO+"  BaseParam："+baseParam);
		//check
		if(StringUtils.isBlank(manageDispatchDTO.getRecName())){
			throw new BusinessServiceException("收件人不能为空");
		}else{
			String reg = "^[\u4e00-\u9fa5_a-zA-Z]+$";
			Pattern pattern = Pattern.compile(reg);  
	        Matcher matcher=pattern.matcher(manageDispatchDTO.getRecName());
	        if(!matcher.matches()){
	        	throw new BusinessServiceException("收件人姓名不能含有特殊字符或数字");
	        }
		}
		if(StringUtils.isBlank(manageDispatchDTO.getPhone())){
			throw new BusinessServiceException("手机号不能为空");
		}
		if(!manageDispatchDTO.getPhone().matches("^(13|14|15|17|18)\\d{9}$")){
			throw new BusinessServiceException("手机号格式不正确");
		}
		if(StringUtils.isBlank(manageDispatchDTO.getAreaCode()) || manageDispatchDTO.getAreaCode().equals("0")){
			throw new BusinessServiceException("省不能为空");
		}
		if(StringUtils.isBlank(manageDispatchDTO.getAddress())){
			throw new BusinessServiceException("地址不能为空");
		}
		//insert
		Date date = new Date();
		manageDispatchDTO.setCrtCde(baseParam.getOperateUser());
		manageDispatchDTO.setUpdCde(baseParam.getOperateUser());
		manageDispatchDTO.setStatus(1);
		manageDispatchDTO.setCrtTm(date);
		manageDispatchDTO.setUpdTm(date);
		if(StringUtils.isNotBlank(manageDispatchDTO.getId())){
			try {
				manageDao.updateById(manageDispatchDTO);
			} catch (Exception e) {
				logger.error("编辑失败",e);
			}
		}else{			
			manageDispatchDTO.setId(MANAGE_DISPATCH_PREFIXNAME+System.currentTimeMillis());
			try {
				manageDao.insertSelective(manageDispatchDTO);
			} catch (Exception e) {
				logger.error("新增配送信息失败",e);
				throw new BusinessServiceException("新增配送信息失败");
			}
		}
	}
	
	@Override
	public void delManageDispatch(ManageDispatchDTO manageDispatchDTO,BaseParam baseParam) throws BusinessServiceException {
		logger.info("删除配送信息 入参    ManageDispatchDTO："+manageDispatchDTO+"  BaseParam："+baseParam);
		try {
			manageDispatchDTO = manageDao.selectByPrimaryKey(manageDispatchDTO.getId());
		} catch (Exception e) {
			logger.error("根据配送ID查询配送信息失败",e);
			throw new BusinessServiceException("根据配送ID查询配送信息失败");
		}
		if(null != manageDispatchDTO && manageDispatchDTO.getCrtCde().equals(baseParam.getOperateUser())){
			try {
				manageDao.deleteByPrimaryKey(manageDispatchDTO.getId());
			} catch (Exception e) {
				logger.error("删除配送信息失败",e);
				throw new BusinessServiceException("删除配送信息失败");
			}
		}
	}

	@Override
	public List<ManageDispatchDTO> selectManageDispatch(BaseParam baseParam) throws BusinessServiceException {
		logger.info("获取配送信息列表 入参    BaseParam："+baseParam);
		List<ManageDispatchDTO> manageDispatchDTOList = null;
		try {
			manageDispatchDTOList = manageDao.selectManageDispatch(baseParam);
		} catch (Exception e) {
			logger.error("获取配送信息列表失败",e);
			throw new BusinessServiceException("获取配送信息列表失败");
		}
		if(CollectionUtils.isNotEmpty(manageDispatchDTOList)){
			manageDispatchDTOList.get(0).setMoren(1);
			for(ManageDispatchDTO dto : manageDispatchDTOList){
				//AreaDTO areaDTO = areaService.get(dto.getAreaCode());
//				if(areaDTO != null){
//					dto.setAreaCodeStr(areaService.get(dto.getAreaCode()).getName());
//				}
				AreaDTO areaDTO = areaService.get(dto.getAreaChildCode());
				if(areaDTO != null){
					dto.setAreaChildCodeStr(areaDTO.getName());
				}
						
			}
		}
		
		return manageDispatchDTOList;
	}

	private void fullCityCodeStr(ManageDispatchDTO manageDispatchDTO){
		AreaDTO areaDTO = null;
		String code = null;
		try {
			code = manageDispatchDTO.getAreaChildCode();
			if(null != code){
				areaDTO = areaService.getCityByCode(code);
				if(null != areaDTO){
					manageDispatchDTO.setAreaChildCodeStr(areaDTO.getName());
				}
			}else{
				manageDispatchDTO.setAreaChildCodeStr("不详");
			}
			code = manageDispatchDTO.getAreaCode();
			if(null != code){
				areaDTO = areaService.getCityByCode(code);
				manageDispatchDTO.setAreaCodeStr(areaDTO.getName());
			}else{
				manageDispatchDTO.setAreaCodeStr("不详");
			}
			
			if(StringUtils.isBlank(manageDispatchDTO.getAddress())){
				manageDispatchDTO.setAddress("不详");
			}
		} catch (Exception e) {
		}
	}
	@Override
	public ManageDispatchDTO selectManageDispatchById(BaseParam baseParam,ManageDispatchDTO manageDispatchDTO) throws BusinessServiceException {
		logger.info("获取单条配送信息 入参    BaseParam："+baseParam+"  ManageDispatchDTO："+manageDispatchDTO);
		try {
			manageDispatchDTO = manageDao.selectByPrimaryKey(manageDispatchDTO.getId());
		} catch (Exception e) {
			logger.error("获取单条配送信息失败",e);
			throw new BusinessServiceException("获取单条配送信息失败");
		}
		if(null != manageDispatchDTO && manageDispatchDTO.getCrtCde().equals(baseParam.getOperateUser())){
			fullCityCodeStr(manageDispatchDTO);
			return manageDispatchDTO;
		}
		return null;
	}

	@Override
	public ManageDispatchDTO selectDefaultManageDispatch(BaseParam baseParam) throws BusinessServiceException {
		logger.info("获取默认配送信息 入参    BaseParam："+baseParam);
		ManageDispatchDTO manageDispatchDTO = null;
		try {
			manageDispatchDTO = manageDao.selectDefaultManageDispatch(baseParam);
		} catch (Exception e) {
			logger.error("获取默认配送信息失败",e);
			throw new BusinessServiceException("获取默认配送信息失败");
		}
		return manageDispatchDTO;
	}

	@Override
	public void setDefaultManageDispatch(ManageDispatchDTO manageDispatchDTO,BaseParam baseParam) throws BusinessServiceException {
		logger.info("设置默认配送信息 入参    ManageDispatchDTO："+manageDispatchDTO+"  BaseParam："+baseParam);
		try {
			manageDispatchDTO = manageDao.selectByPrimaryKey(manageDispatchDTO.getId());
		} catch (Exception e) {
			logger.error("根据ID查询配送信息失败",e);
			throw new BusinessServiceException("根据ID查询配送信息失败");
		}
		if(null != manageDispatchDTO && manageDispatchDTO.getCrtCde().equals(baseParam.getOperateUser())){
			ManageDispatchDTO tmp = new ManageDispatchDTO();
			tmp.setId(manageDispatchDTO.getId());
			tmp.setSort(System.currentTimeMillis());
			try {
				manageDao.updateByPrimaryKeySelective(tmp);
			} catch (Exception e) {
				logger.error("设置默认配送信息失败",e);
				throw new BusinessServiceException("设置默认配送信息失败");
			}
		}
	}

	@Override
	public ManageDispatchDTO selectById(String id) throws BusinessServiceException {
		logger.info("根据ID查询配送信息 入参    Id："+id);
		ManageDispatchDTO manageDispatchDTO = null;
		try {
			manageDispatchDTO = manageDao.selectById(id);
		} catch (Exception e) {
			logger.error("根据id查询配送信息失败",e);
			throw new BusinessServiceException("根据id查询配送信息失败");
		}
		return manageDispatchDTO;
	}

	@Override
	public void updateById(ManageDispatchDTO manageDispatchDTO)
			throws BusinessServiceException {
		logger.info("更新配送信息 入参    ManageDispatchDTO："+manageDispatchDTO);
		try {
			manageDao.updateById(manageDispatchDTO);
		} catch (Exception e) {
			logger.error("更新配送失败",e);
			throw new BusinessServiceException("更新配送信息失败");
		}
	}

	
	 public boolean startCheck(String reg,String string)  
	    {  
	        boolean tem=false;  
	          
	        Pattern pattern = Pattern.compile(reg);  
	        Matcher matcher=pattern.matcher(string);  
	          
	        tem=matcher.matches();  
	        return tem;  
	    } 
	 
}
