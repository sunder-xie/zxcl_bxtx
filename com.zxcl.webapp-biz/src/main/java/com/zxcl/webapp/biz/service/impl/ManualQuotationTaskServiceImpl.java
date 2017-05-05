package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.ManualQuotationTaskService;
import com.zxcl.webapp.biz.service.ManualQuoterService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.ManualQuotationTaskDTO;
import com.zxcl.webapp.dto.QuoteTrackDTO;
import com.zxcl.webapp.integration.dao.InquiryDAO;
import com.zxcl.webapp.integration.dao.ManualQuotationTaskDAO;
import com.zxcl.webapp.integration.dao.OrderDAO;
import com.zxcl.webapp.integration.dao.QuotaDAO;
import com.zxcl.webapp.integration.dao.QuoteTrackDAO;
import com.zxcl.webapp.integration.dao.TaskFileDAO;
import com.zxcl.webapp.util.Common;

@Service
public class ManualQuotationTaskServiceImpl implements ManualQuotationTaskService{

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ManualQuotationTaskDAO manualQuotationTaskDAO;
	
	@Autowired
	private QuoteTrackDAO quoteTrackDAO;
	
	@Autowired
	private InquiryDAO inquiryDao;
	
	@Autowired
	private QuotaDAO quotaDao;
	
	@Autowired
	private OrderDAO orderDao;
	
	@Autowired
	private TaskFileDAO taskFileDao;
	
	@Value("${fileserverUrl}")
	private String FILESERVER_URL;
	
	@Autowired
	private ManualQuoterService manualQuoterService;
	
	@Override
	public void insert(ManualQuotationTaskDTO manualQuotationTaskDTO)
			throws BusinessServiceException {
		logger.info("insert  manualQuotationTaskDTO："+manualQuotationTaskDTO);
		try {
			manualQuotationTaskDAO.insert(manualQuotationTaskDTO);
			QuoteTrackDTO quoteTrackDTO = new QuoteTrackDTO();
			quoteTrackDTO.setOperatId(Common.getUUID());
			quoteTrackDTO.setOperatStatus("1");
			quoteTrackDTO.setOperatTime(DateUtil.dateToString("yyyy-MM-dd HH:mm:ss", new Date()));
			quoteTrackDTO.setOperatUser(manualQuotationTaskDTO.getUserId());
			quoteTrackDTO.setTaskId(manualQuotationTaskDTO.getTaskId());
			quoteTrackDAO.insert(quoteTrackDTO);
			// 插入报价单信息
			inquiryDao.updateInquiryStatusByInquiryId("2", manualQuotationTaskDTO.getInquiryId(),manualQuotationTaskDTO.getUserId());
		} catch (Exception e) {
			logger.error("保存人工报价信息失败",e);
			throw new BusinessServiceException("保存人工报价信息失败");
		}
	}

	@Override
	public ManualQuotationTaskDTO createTaskDataAssembly(String inquiryId,
			String userId, String quoteId, String insId, String teamId)
			throws BusinessServiceException {
		ManualQuotationTaskDTO manualQuotationTaskDTO = new ManualQuotationTaskDTO();
		manualQuotationTaskDTO.setTaskId(Common.getUUID());
		manualQuotationTaskDTO.setInquiryId(inquiryId);
		manualQuotationTaskDTO.setInsId(insId);
		manualQuotationTaskDTO.setQuoteId(quoteId);
		manualQuotationTaskDTO.setRecordStatus("1");
		manualQuotationTaskDTO.setTaskStatus("1");
		manualQuotationTaskDTO.setTeamId(teamId);
		manualQuotationTaskDTO.setUserId(userId);
		return manualQuotationTaskDTO;
	}

	@Override
	public void withdrawQuotn(String inquiryId,String userId) throws BusinessServiceException {
		logger.info("withdrawQuotn  inquiryId:"+inquiryId);
		try {
			manualQuotationTaskDAO.withdrawQuotn(inquiryId,userId);
			List<ManualQuotationTaskDTO> manualQuotationTaskList = manualQuotationTaskDAO.queryInfoByInquiryId(inquiryId);
			for (ManualQuotationTaskDTO manualQuotationTaskDTO2 : manualQuotationTaskList) {				
				QuoteTrackDTO quoteTrackDTO = new QuoteTrackDTO();
				quoteTrackDTO.setOperatId(Common.getUUID());
				quoteTrackDTO.setOperatStatus("5");
				quoteTrackDTO.setOperatTime(DateUtil.dateToString("yyyy-MM-dd HH:mm:ss", new Date()));
				quoteTrackDTO.setOperatUser(userId);
				quoteTrackDTO.setTaskId(manualQuotationTaskDTO2.getTaskId());
				quoteTrackDTO.setOnOperatId(quoteTrackDAO.queryOnOperatId(manualQuotationTaskDTO2.getTaskId()));
				quoteTrackDAO.insert(quoteTrackDTO);
			}
			inquiryDao.updateInquiryStatusByInquiryId("1", inquiryId,userId);
			quotaDao.updateQuotaStatusByInquiryId("0", inquiryId,userId);
			orderDao.updateOrderStatusByInquiryId("0", inquiryId);
		} catch (Exception e) {
			logger.error("撤回人工报价任务失败",e);
			throw new BusinessServiceException("撤回人工报价任务失败");
		}
	}

	@Override
	public ManualQuotationTaskDTO queryByQuoteId(String quoteId)
			throws BusinessServiceException {
		logger.info("queryByQuoteId  quoteId:"+quoteId);
		ManualQuotationTaskDTO manualQuotationTaskDTO = new ManualQuotationTaskDTO();
		try {
			manualQuotationTaskDTO = manualQuotationTaskDAO.queryByQuoteId(quoteId);
			List<QuoteTrackDTO> quoteTrackList = quoteTrackDAO.queryByTaskIdAndStatus(manualQuotationTaskDTO.getTaskId(), null);
			manualQuotationTaskDTO.setQuoteTrackList(quoteTrackList);
			//测试
			String [] fileIds = taskFileDao.queryByTaskId(manualQuotationTaskDTO.getTaskId());
			List<String> fileIds2 = new ArrayList<String>();
			for (String fileId : fileIds) {
				fileIds2.add(FILESERVER_URL+fileId);
			}
			manualQuotationTaskDTO.setFileIds(fileIds2);
		} catch (Exception e) {
			logger.error("根据报价单号获取人工报价信息失败",e);
			throw new BusinessServiceException("根据报价单号获取人工报价信息失败");
		}
		return manualQuotationTaskDTO;
	}

	@Override
	public void underwrite(String quoteId,String userId) throws BusinessServiceException {
		logger.info("underwrite  quoteId:"+quoteId);
		try {
			//任务状态修改为确认投保
			manualQuotationTaskDAO.underwriteTypeUpdate("6", quoteId,userId);
			//报价轨迹表添加
			ManualQuotationTaskDTO manualQuotationTaskDTO = manualQuotationTaskDAO.queryByQuoteId(quoteId);
			QuoteTrackDTO quoteTrackDTO = new QuoteTrackDTO();
			quoteTrackDTO.setOperatId(Common.getUUID());
			quoteTrackDTO.setOperatStatus("6");
			quoteTrackDTO.setOperatTime(DateUtil.dateToString("yyyy-MM-dd HH:mm:ss", new Date()));
			quoteTrackDTO.setOperatUser(userId);
			quoteTrackDTO.setTaskId(manualQuotationTaskDTO.getTaskId());
			quoteTrackDTO.setOnOperatId(quoteTrackDAO.queryOnOperatId(manualQuotationTaskDTO.getTaskId()));
			quoteTrackDAO.insert(quoteTrackDTO);
			
			manualQuoterService.notifyQuoter(manualQuotationTaskDTO.getTeamId(), manualQuotationTaskDTO.getInsId());
		} catch (Exception e) {
			logger.error("核保任务信息相关修改失败",e);
			throw new BusinessServiceException("核保任务信息相关修改失败");
		}
	}

}
