package com.zxcl.webapp.integration.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.dto.CompanyAccInfoDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

/**
 * 小薇账号
 */
public interface MicroDAO {

	/**
	 * 通过登录的userId获取小薇账号
	 * 
	 * @param userId
	 * @return
	 */
	public MicroDTO getMicroByUserId(String userId) throws Exception;
	
	/**
	 * 通过登录的microId获取小薇账号
	 * 
	 * @param microId
	 * @return
	 */
	public MicroDTO getMicroByMicroId(String microId);

	/**
	 * 根据小微手机号得到账号
	 * 
	 * @param microId
	 * @return
	 */
	public MicroDTO getMicroByTel(String tel) throws Exception;

	/**
	 * 通过tel获取绑定的小薇个数
	 * 
	 * @param tel
	 * @return
	 * @throws Exception
	 */
	public Integer getMicroCountByTel(String tel) throws Exception;

	/**
	 * 根据小微ID得到账号
	 * 
	 * @param microId
	 * @return
	 */
	public String getUserIdByMicId(String microId) throws Exception;

	/**
	 * 更新小微用户信息
	 * 
	 * @param microDTO
	 */
	public void update(MicroDTO micro) throws Exception;

	/**
	 * 获取费率相关信息
	 * 
	 * @param date
	 *            当前时间
	 * @param micro_id
	 * @return
	 */
	public List<MicroDTO> getRoteByMicId(@Param("microId") String microId, @Param("date") Date date) throws Exception;

	/**
	 * 根据传入的报价类型获取小薇绑定的保险公司
	 */
	public List<InsuranceDTO> getInsByQuotaType(@Param("userId") String userId, @Param("quotaType") String quotaType) throws Exception;

	/**
	 * 通过邀请码获取邀请码主人
	 */
	public MicroDTO getMicroInvitation(String invitation) throws Exception;

	public void insertMicroDTO(MicroDTO microDTO) throws Exception;

	public Integer getMaxinvitation() throws Exception;

	/**
	 * 查找有没有注册过的电话
	 * 
	 * @param tel
	 * @return
	 */
	public Integer selectAllMicro(String tel) throws Exception;

	/**
	 * 查找有没有注册过用户名
	 * 
	 * @param authenticatedUserId
	 * @return
	 */
	public Integer selectAllMicrobyUser(String user_id) throws Exception;

//	/**
//	 * 根据小微获取相关联的保险公司ID集合
//	 * 
//	 * @param microId
//	 *            小微ID
//	 * @return
//	 * @throws BusinessServiceException
//	 */
//	public String[] getInsIdsByMicroId(String microId) throws Exception;

	/**
	 * 根据保险公司ID和小微ID得到保险公司配置信息ID
	 * 
	 * @param insId
	 *            保险公司ID
	 * @param microId
	 *            小微ID
	 * @return 保险公司配置信息ID
	 * @throws Exception
	 */
	public String getConfigIdByInsIdAndMicroId(@Param("insId") String insId, @Param("microId") String microId) throws Exception;
	
	
	/**
	 * getconfigIdByParam
	 * @param insId
	 * @param microId
	 * @param quotaType
	 * @return
	 */
	public String findConfigIdByParam(@Param("insId") String insId, @Param("microId") String microId, @Param("quotnType")String quotnType);
	
	
	/**
	 * 根据保险公司ID或小微ID得到保险公司status
	 * @param insId
	 * @param microId
	 * @return
	 * @throws Exception
	 */
	public String getStatusByInsIdAndMicroId(@Param("insId") String insId, @Param("microId") String microId,@Param("quotnType")String quotnType,
			@Param("serviceType")String serviceType) throws Exception;
	

	/**
	 * 根据团队编码 agt_team_id 获取团队名称 电话号码
	 * 
	 * @param agt_team_id
	 * @return
	 * @throws Exception
	 */
	public Map selectAgtTeam(String agt_team_id) throws Exception;

	/**
	 * 根据代理机构编码    agent_id 获取代理名称电话
	 * @param agent_id
	 * @return
	 * @throws Exception
	 */
	public Map selectAgent(String agent_id) throws Exception;
	
	/**
	 * 通过用户ID获取用户所属团队的地址
	 * @param userId
	 * @return
	 */
	public String selectMicroTeamAddressByUserId(String userId);

	/**
	 * 修改小微姓名
	 * @param userId
	 * @param cardOwner
	 */
	public int updateMicroName(@Param("userId")String userId, @Param("microName")String microName);

	/**
	 * 获取团队成员总人数
	 * @param teamId
	 * @return
	 */
	public int findTeamPeopleCount(String teamId);

	/**
	 * 分页获取团队成员
	 * @param pageParam
	 * @return
	 */
	public List<HashMap<String, String>> findTeamPeopleList(PageParam pageParam);

	/**
	 * updateAgtIdAndTeamIdByUserId
	 * @param changeTeamId
	 * @param changeAgtId
	 * @param userPhone
	 */
	public int updateTeamIdByUserId(@Param("teamId")String teamId, @Param("userId")String userId);
	
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	public CompanyAccInfoDTO getCompanyAccinfoByCompCode(String userId);

	
}
