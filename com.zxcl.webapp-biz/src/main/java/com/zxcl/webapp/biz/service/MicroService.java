package com.zxcl.webapp.biz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;






import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.biz.util.resultEntity.PageBean;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

public interface MicroService {

	/**
	 * 通过登录的userId获取小薇账号
	 * 
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	public MicroDTO getMicroByUserId(String userId) throws BusinessServiceException;

	/**
	 * 通过登录的tel获取小薇账号
	 * 
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	public MicroDTO getMicroByTel(String tel) throws BusinessServiceException;

	/**
	 * 获取tel绑定小薇的个数
	 * 
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getMicroCountByTel(String tel) throws BusinessServiceException;

	/**
	 * 更新小微用户信息
	 * 
	 * @param microDTO
	 * @throws BusinessServiceException
	 */
	public void update(MicroDTO microDTO) throws BusinessServiceException;

	/**
	 * 获取费率相关信息
	 * 
	 * @param micro_id
	 * @throws BusinessServiceException
	 */
	public List<MicroDTO> getRoteByMicId(String micro_id) throws BusinessServiceException;

	/**
	 * 根据小微ID得到账号
	 * 
	 * @param microId
	 * @return
	 */
	public String getUserIdByMicId(String microId) throws BusinessServiceException;

	/**
	 * 根据传入的报价类型获取小薇绑定的保险公司
	 */
	List<InsuranceDTO> getInsByQuotaType(String userId, String quotaType) throws BusinessServiceException;

	/**
	 * 修改密码
	 * 
	 * @param authenticatedUserId
	 * @param password
	 * @throws BusinessServiceException
	 */
	public void editPwd(String userId, String oldpwd, String pwd) throws BusinessServiceException;

	/**
	 * 更新密码
	 * 
	 * @param authenticatedUserId
	 * @param password
	 * @throws BusinessServiceException
	 */
	public void updatePwd(String userId, String password) throws BusinessServiceException;

	/**
	 * 手机端通过邀请码添加用户信息
	 * 
	 * @param invitation
	 * @return
	 */

	public void addMicroInvitation(String invitation, MicroDTO microDTO) throws BusinessServiceException;

	public Integer selectAllMicro(String tel) throws BusinessServiceException;

	public MicroDTO getMicroInvitation(String invitation) throws BusinessServiceException;

	/**
	 * 通过当前登录名的到自己的邀请码
	 * 
	 * @param authenticatedUserId
	 * @return
	 */
	public UserDTO getUserIDyInvitation(String authenticatedUserId) throws BusinessServiceException;

	/**
	 * 通过邀请码得到 我邀请的朋友的个数
	 * 
	 * @param invitation
	 * @return
	 */
	public Integer getMyfrend(String invitation) throws BusinessServiceException;

//	/**
//	 * 根据小微获取相关联的保险公司ID集合
//	 * 
//	 * @param microId
//	 *            小微ID
//	 * @return
//	 * @throws BusinessServiceException
//	 */
//	public String[] getInsIdsByMicroId(String microId) throws BusinessServiceException;

	/**
	 * 查找有没有注册过用户名
	 * 
	 * @param user_id
	 * @return
	 */
	public Integer selectAllMicrobyUser(String user_id) throws BusinessServiceException;

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
	public String getConfigIdByInsIdAndMicroId(String insId, String microId) throws BusinessServiceException;

	/**
	 * 根据agent_id查询个性化数据
	 * 
	 * @param agent_id
	 * @return
	 * @throws BusinessServiceException
	 */
	public Map getPersonalityByMicId(MicroDTO micro) throws BusinessServiceException;
	
	
	
	/**
	 * 分页查询团队成员
	 * @param teamId
	 * @param currentPage
	 * @return
	 * @throws BusinessServiceException
	 */
	public PageBean<HashMap<String, String>> findTeamPeopleByPage(PageParam pageParam) throws BusinessServiceException;


	/**
	 * t_agent_info  t_agt_team  t_micro_info  这三个表都有status状态字段 ,其中任意一个为失效状态，当前用户就不能登录
	 * 小微是否能登录
	 * @param userId
	 * @param openId
	 * @return
	 */
	public boolean microIsAllowLogon(String userId, String openId);
	
	/**
	 * 根据保险公司ID或小微ID得到保险公司status
	 * @param insId
	 * @param microId
	 * @return
	 * @throws Exception
	 */
	public String getStatusByInsIdAndMicroId(String insId, String microId,String userId,String serviceType) throws BusinessServiceException;
}
