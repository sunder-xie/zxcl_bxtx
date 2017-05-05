package com.zxcl.webapp.integration.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;







import com.zxcl.huahai.dto.port.huahai.resp.seria.ApplyDetailCarDTO;
import com.zxcl.webapp.biz.util.model.BaseParam;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.InquiryFailMsgDTO;
import com.zxcl.webapp.dto.MicroDTO;

/**
 * 询价的车辆的基本信息
 * 
 * @author 5555
 *
 */
public interface InquiryDAO {

	/**
	 * 添加
	 * 
	 * @param inquiry
	 */
	public void insert(InquiryDTO inquiry) throws Exception;

	/**
	 * 获取单个的询价的车辆信息
	 * 
	 * @param id
	 * @return
	 */
	public InquiryDTO getInquiryVehicleByInquiryId(String inquiryId)
			throws Exception;

	/**
	 * 添加中间表的询价信息
	 * 
	 * @param inquiry
	 * @param mciro
	 */
	public void insertBasic(@Param("inquiry") InquiryDTO inquiry,
			@Param("micro") MicroDTO mciro) throws Exception;

	/**
	 * 获取总条数
	 */
	public int getCount() throws Exception;

	public List<InquiryDTO> getInquiryListByMicroId(
			Map<String, Object> contidion) throws Exception;

	/**
	 * 查询询价单信息
	 * 
	 * @param inquiryId
	 * @param microId
	 * @return
	 */
	public InquiryDTO get(@Param("inquiryId") String inquiryId,
			@Param("microId") String microId) throws Exception;

	/**
	 * 更新询价单信息
	 */
	public void updateInquiryByInquiryId(InquiryDTO inquiry)
			throws Exception;
	/**
	 * 
	* @Title: infoQueryDAO
	* @Description: 综合查询
	* @param  microID
	* @param @throws BusinessServiceException
	* @return List<InquiryDTO>
	* @throws
	 */
	public List<InquiryDTO> infoQueryDAO(@Param("microId") String microId,@Param("queryParameter") String queryParameter)throws Exception;
	/**
	 * 综合查询翻页
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	public int infoQueryCount(PageParam pageParam) throws Exception;
	public List<InquiryDTO> infoQueryPage(PageParam pageParam) throws Exception;
	/**
	 * 
	* @Title: infoTempQueryService
	* @Description: 查询暂存数据
	* @param  microID
	* @param @throws BusinessServiceException
	* @return List<InquiryDTO>
	* @throws
	 */
	public List<InquiryDTO> infoTempQueryDAO(String microId)throws Exception;
	

	/**
	 * 更新询价单险种信息
	 */
	public void updateInquiryBasicByInquiryId(InquiryDTO inquiry)
			throws Exception;

	/**
	 * 更新询价单状态
	 * 
	 * @param inquiry
	 */
	public void updateInquiryStatusByInquiryId(@Param("state") String state,
			@Param("inquiryId") String inquiryId,@Param("updCde")String updCde) throws Exception;

	
	/**
	 * 近期录入的车牌信息
	 * wd:车牌
	 * operateUser：用户id
	 * @return
	 * @throws Exception
	 */
	public List<InquiryDTO> getClosetInquiry(BaseParam baseParam) throws Exception;
	
	/**
	 * 更新报价失败保险公司数和失败信息
	 * @param inquiryFailMsgDTO 
	 * @throws Exception
	 */
	public void insertInsQuotaFailInfo(InquiryFailMsgDTO inquiryFailMsgDTO) throws Exception;
	
	/**
	 * 得到报价失败保险公司数
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public InquiryDTO getInsQuotaFailInfo(String inquiryId) throws Exception;
	
	/**
	 * 更新重复投保信息
	 * @param reInsureInfo 重复投保信息
	 * @param inquiryId 询价单号
	 * @param updCde 修改人
	 * @throws Exception
	 */
	public void updateReInsureInfo(@Param("reInsureInfo")String reInsureInfo,@Param("inquiryId")String inquiryId,@Param("updCde")String updCde) throws Exception;
	
	/**
	 * 根据询价单号查询信息
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public InquiryDTO selectByInquiryId(String inquiryId) throws Exception;
	
	/**
	 * 根据询价单号获取车牌号
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public String getPlateNoByInquiryId(String inquiryId) throws Exception;
	
	/**
	 * 根据询价单号修改起保日期
	 * @param inquiryId 询价单号
	 * @param ticStartQuotaDate 交强起保日期
	 * @param ticEndQuotaDate   交强结束日期
	 * @param vicStartQuotaDate 商业起保日期
	 * @param vicEndQuotaDate 商业结束日期
	 * @throws Exception
	 */
	public void updateStartQuotaDate(@Param("inquiryId")String inquiryId,@Param("ticStartQuotaDate")String ticStartQuotaDate,@Param("ticEndQuotaDate")String ticEndQuotaDate,
			@Param("vicStartQuotaDate")String vicStartQuotaDate,@Param("vicEndQuotaDate")String vicEndQuotaDate) throws Exception;

	/**
	 * @param orderId
	 * @return
	 */
	public String getInquiryIdByOrderId(String orderId);

	
	/**
	 * 更新车辆信息-华海
	 * @param car
	 * @param quotaId
	 * @return
	 */
	public int updateCarWithHHBX(@Param("car") ApplyDetailCarDTO car, @Param("inquiryId")String inquiryId);
}
