package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WalletActiveService;
import com.zxcl.webapp.biz.service.WalletCoreService;
import com.zxcl.webapp.biz.util.Encoding;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.biz.util.resultEntity.PageBean;
import com.zxcl.webapp.dto.CompanyAccInfoDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.integration.dao.AgencyDAO;
import com.zxcl.webapp.integration.dao.IntfMicroInsConfigDAO;
import com.zxcl.webapp.integration.dao.MicroDAO;
import com.zxcl.webapp.integration.dao.RoleDAO;
import com.zxcl.webapp.integration.dao.TeamParamMappingDAO;
import com.zxcl.webapp.integration.dao.UserDAO;

/**
 * 小薇账户
 */
@Service
public class MicroServiceImpl implements MicroService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private MicroDAO microDao;

	@Autowired
	private WalletActiveService walletActiveService;

	@Autowired
	private WalletCoreService walletCoreService;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private UserService userService;

	@Autowired
	private AgencyDAO agencyDAO;

	@Autowired
	private IntfMicroInsConfigDAO intfMicroInsConfigDAO;
	
	@Autowired
	private TeamParamMappingDAO teamParamMappingDAO;

	@Override
	public MicroDTO getMicroByUserId(String userId) throws BusinessServiceException {
		logger.info("getMicroByUserId 根据用户ID获取小微信息 入参    用户ID：" + userId);
		MicroDTO microDTO = null;
		try {
			microDTO = microDao.getMicroByUserId(userId);
		} catch (Exception e) {
			logger.error("获取登录用户:" + userId + "的小薇信息.", e);
			throw new BusinessServiceException("根据用户ID获取小微信息失败");
		}
		return microDTO;
	}

	@Override
	public Integer getMicroCountByTel(String tel) throws BusinessServiceException {
		Integer count = 0;
		logger.info("根据用户手机号绑定的小薇个数 入参    用户手机号：" + tel);
		try {
			count = microDao.getMicroCountByTel(tel);
		} catch (Exception e) {
			logger.error("根据用户手机号" + tel + "获取绑定的小薇个数:", e);
			throw new BusinessServiceException("根据用户手机号获取小微信息");
		}
		logger.info(count);
		return 0;
	}

	@Override
	public MicroDTO getMicroByTel(String tel) throws BusinessServiceException {
		logger.info("根据用户手机号获取小微信息 入参    用户手机号：" + tel);
		MicroDTO microDTO = null;
		try {
			microDTO = microDao.getMicroByTel(tel);
		} catch (Exception e) {
			logger.error("根据用户手机号" + tel + "获取小微信息:", e);
			throw new BusinessServiceException("根据用户手机号获取小微信息");
		}
		return microDTO;
	}

	@Override
	public void update(MicroDTO microDTO) throws BusinessServiceException {
		logger.info("更新小微信息 入参    MicroDTO：" + microDTO);
		try {
			// if (StringUtils.isNotBlank(password)) {
			// userDAO.updatePassword(microDTO.getUser().getUserId(),
			// Encoding.encode(password));
			// }
			microDao.update(microDTO);
		} catch (Exception e) {
			logger.error("修改小薇:" + microDTO.getMicro_id() + "密码失败:", e);
			throw new BusinessServiceException("更新小微信息失败");
		}

	}

	@Override
	public void updatePwd(String userId, String password) throws BusinessServiceException {
		logger.info("更新密码 入参    用户ID：" + userId );

		// update
		try {
			userDAO.updatePassword(userId, Encoding.encode(password));
		} catch (Exception e) {
			logger.error("修改密码失败IOP[ ", e);
			throw new BusinessServiceException("密码修改失败", e);
		}
	}

	@Override
	public void editPwd(String userId, String oldpwd, String pwd) throws BusinessServiceException {
		logger.info("修改密码 入参    用户ID：" + userId );
		// check
		if (StringUtils.isBlank(oldpwd)) {
			throw new BusinessServiceException("原密码不能为空");
		}
		if (StringUtils.isBlank(pwd)) {
			throw new BusinessServiceException("新密码不能为空");
		}
		UserDTO user = userService.queryUserByUserId(userId);
		if (!Encoding.matches(oldpwd, user.getLoginPassword())) {
			throw new BusinessServiceException("原密码输入错误");
		}

		// update
		try {
			userDAO.updatePassword(userId, Encoding.encode(pwd));
		} catch (Exception e) {
			logger.error("修改密码失败IOP[ ", e);
			throw new BusinessServiceException("密码修改失败", e);
		}

	}

	@Override
	public List<MicroDTO> getRoteByMicId(String microId) throws BusinessServiceException {
		logger.info("获取费率相关信息 入参    小微ID：" + microId);
		List<MicroDTO> list = new ArrayList<MicroDTO>();
		try {
			list = microDao.getRoteByMicId(microId, new Date());
		} catch (Exception e) {
			logger.error("获取小薇编码为:" + microId + "费率信息失败:" + e, e);
			throw new BusinessServiceException("获取费率相关信息");
		}
		return list;
	}

	@Override
	public String getUserIdByMicId(String microId) throws BusinessServiceException {
		logger.info("跟据小微ID得到账号 入参    小微ID：" + microId);
		String userId = "";
		try {
			userId = microDao.getUserIdByMicId(microId);
		} catch (Exception e) {
			logger.error("获取小薇编码为:" + microId + "费率信息失败:" + e, e);
			throw new BusinessServiceException("跟据小微ID得到账号失败");
		}
		return userId;
	}

	@Override
	public List<InsuranceDTO> getInsByQuotaType(String userId, String quotaType) throws BusinessServiceException {
		logger.info("根据传入的报价类型获取小薇绑定的保险公司 入参    用户ID：" + userId + "  quotaType：" + quotaType);
		List<InsuranceDTO> list = new ArrayList<InsuranceDTO>();
		try {
			list = microDao.getInsByQuotaType(userId, quotaType);
		} catch (Exception e) {
			logger.error("用户" + userId + "获取报价类型:" + quotaType + "的保险公司信息失败:" + e, e);
			throw new BusinessServiceException("根据传入的报价类型获取小薇绑定的保险公司失败");
		}
		return list;
	}

	/**
	 * 通过邀请码获取邀请码主人
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public synchronized void addMicroInvitation(String invitation, MicroDTO microDTO) throws BusinessServiceException {
		logger.info("通过邀请码获取邀请码主人 ,入参    邀请码：" + invitation + " MicroDTO ：" + microDTO);
		
		/**
		 * 通过邀请码获取邀请主人的参数
		 */
		MicroDTO parentmicro = new MicroDTO();
		try {
			logger.info("通过邀请码获取邀请主人的参数");
			parentmicro = microDao.getMicroInvitation(invitation);
		} catch (Exception e) {
			logger.error("通过邀请码获取邀请主人的参数失败", e);
			throw new BusinessServiceException("通过邀请码获取邀请主人的参数失败");
		}
		if (StringUtils.isNotBlank(parentmicro.getAgt_team_id())) {
			String password = microDTO.getPassword();
			String encode = Encoding.encode(password);
			
			// 查找最大的邀请号
			Integer num = 0;
			try {
				logger.info("获取最大验证码");
				num = userDAO.getMaxinvitation();
			} catch (Exception e) {
				logger.error("获取最大验证码失败", e);
				throw new BusinessServiceException("获取最大验证码失败");
			}

			if (null == num || "".equals(num)) {
				num = 100000;
			}

			/**
			 * 把邀请主人的信息封装给信注册的用户
			 */
			microDTO.setPassword(encode);
			microDTO.setMicro_id(null);

			/**
			 * 添加邀请码
			 */

			microDTO.setInvitation(Integer.toString(num + 3));
			
			/**
			 * 添加关系码
			 */
			if (parentmicro.getAgency() != null) {
				microDTO.setRelevance(invitation);
				microDTO.setCrt_cde(parentmicro.getAgency().getAgent_id());
				microDTO.setUpd_cde(parentmicro.getAgency().getAgent_id());
				microDTO.setWeChatType(parentmicro.getWeChatType());
				microDTO.setAgent_id(parentmicro.getAgency().getAgent_id());
				microDTO.setAgt_team_id(parentmicro.getAgt_team_id());
			} else {
				logger.error("没有中介信息");

			}
			microDTO.setStatus(1);
			microDTO.setCrtTime(new Date());
			microDTO.setUpdTime(new Date());
			/**
			 * 新增用户
			 */
			try {
				logger.info("新增用户");
				userDAO.insertUser(microDTO);
			} catch (Exception e) {
				logger.error("新增用户失败", e);
				throw new BusinessServiceException("新增用户失败");
			}
			/**
			 * 新增小薇账户
			 */
			try {
				logger.info("新增小微账号");
				microDao.insertMicroDTO(microDTO);
			} catch (Exception e) {
				logger.error("新增小微账号失败", e);
				throw new BusinessServiceException("新增小微账号失败");
			}

			logger.info("增加一条小微是信息数据END");

			logger.info("初始化小微钱包");
			try {
				walletCoreService.getWalletByUserId(microDTO.getUser_id());
			} catch (Exception e) {
				logger.error("初始化小微钱包失败", e);
			}

//			logger.info("注册活动开始....");
//			try {
//				walletActiveService.activeWithRegist(microDTO.getUser_id());
//			} catch (Exception e) {
//				logger.error("注册活动:" + e.getMessage(), e);
//			}
//			logger.info("注册活动结束....");
		}

	}

	@Override
	public Integer selectAllMicro(String userId) throws BusinessServiceException {
		logger.info("查找有没有注册过的电话 入参    用户ID：" + userId);
		Integer i = 0;
		try {
			i = microDao.selectAllMicro(userId);
		} catch (Exception e) {
			logger.error("查找有没有注册过的电话失败", e);
			throw new BusinessServiceException("查找有没有注册过的电话失败");
		}
		return i;
	}

	@Override
	public MicroDTO getMicroInvitation(String invitation) throws BusinessServiceException {
		logger.info("通过邀请码获取邀请码主人 入参    邀请码：" + invitation);
		MicroDTO microDTO = null;
		try {
			microDTO = microDao.getMicroInvitation(invitation);
		} catch (Exception e) {
			logger.error("通过邀请码获取邀请码主人失败", e);
			throw new BusinessServiceException("通过邀请码获取邀请码主人失败");
		}
		return microDTO;
	}

	@Override
	public UserDTO getUserIDyInvitation(String authenticatedUserId) throws BusinessServiceException {
		logger.info("通过当前登录名的到自己的邀请码 入参    authenticatedUserId：" + authenticatedUserId);
		UserDTO userDTO = null;
		try {
			userDTO = userDAO.getUserIDyInvitation(authenticatedUserId);
		} catch (Exception e) {
			logger.error("getUserIDyInvitation查询异常" + e.getStackTrace(), e);
			throw new BusinessServiceException("通过当前登录名的到自己的邀请码失败");
		}
		return userDTO;
	}

	@Override
	public Integer getMyfrend(String invitation) throws BusinessServiceException {
		logger.info("通过邀请码得到 我邀请的朋友的个数 入参    invitation：" + invitation);
		Integer myfriend = null;
		try {
			myfriend = userDAO.getMyfrend(invitation);
		} catch (Exception e) {
			logger.error("getMyfrend查询异常" + e.getStackTrace(), e);
			throw new BusinessServiceException("通过邀请码得到 我邀请的朋友的个数失败ss");
		}
		return myfriend;
	}

//	public String[] getInsIdsByMicroId(String microId) throws BusinessServiceException {
//		logger.info("MicroServiceImpl  getInsIdsByMicroId");
//		logger.info("根据小微获取相关联的保险公司ID集合");
//		logger.info("入参    小微ID：" + microId);
//		String[] insIds = null;
//		try {
//			insIds = microDao.getInsIdsByMicroId(microId);
//		} catch (Exception e) {
//			logger.error("查询异常改账号相关联的保险公司ID失败" + e.getStackTrace());
//			throw new BusinessServiceException("根据小微获取相关联的保险公司ID集合失败");
//		}
//		return insIds;
//	}

	@Override
	public Integer selectAllMicrobyUser(String user_id) throws BusinessServiceException {
		logger.info("查找有没有注册过用户名 入参    用户ID：" + user_id);
		Integer i = 0;
		try {
			i = microDao.selectAllMicrobyUser(user_id);
		} catch (Exception e) {
			logger.error("查找有没有注册过用户名失败", e);
			throw new BusinessServiceException("查找有没有注册过用户名失败");
		}
		return i;
	}

	@Override
	public String getConfigIdByInsIdAndMicroId(String insId, String microId) throws BusinessServiceException {
		logger.info("根据保险公司ID和小微ID得到保险公司配置信息ID 入参    保险公司ID：" + insId + "  小微ID：" + microId);
		String configId = "";
		try {
			configId = microDao.getConfigIdByInsIdAndMicroId(insId, microId);
		} catch (Exception e) {
			logger.error("根据保险公司ID和小微ID得到保险公司配置信息ID失败", e);
			throw new BusinessServiceException("根据保险公司ID和小微ID得到保险公司配置信息ID失败");
		}
		return configId;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getPersonalityByMicId(MicroDTO micro) throws BusinessServiceException {
		logger.info("根据 代理机构编码或者中介公司团队编码    获取代理机构名称 电话 入参   MicroDTO：" + micro);
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			resultMap = microDao.selectAgtTeam(micro.getAgt_team_id());
			resultMap.putAll(microDao.selectAgent(micro.getAgency().getAgent_id()));
		} catch (Exception e) {
			logger.error("根据 代理机构编码或者中介公司团队编码    获取代理机构名称 电话", e);
			throw new BusinessServiceException("根据 代理机构编码或者中介公司团队编码    获取代理机构名称 电话");
		}
		return resultMap;
	}

	@Override
	public PageBean<HashMap<String, String>> findTeamPeopleByPage(PageParam pageParam) throws BusinessServiceException {
		PageBean<HashMap<String, String>> page = new PageBean<HashMap<String, String>>();
		if(null == pageParam.getPageSize()){
			pageParam.setPageSize(30);
		}
		if(null == pageParam.getCurrentPage()){
			pageParam.setCurrentPage(1);
		}
		int recordCount = 0;
		List<HashMap<String, String>> dataList = null;
		pageParam.setStart(pageParam.getCurrentPage()==1?0:(pageParam.getCurrentPage()-1)*pageParam.getPageSize());
		try {
			
			recordCount = microDao.findTeamPeopleCount(pageParam.getWd());
			dataList = microDao.findTeamPeopleList(pageParam);
		} catch (Exception e) {
			logger.error("获取团队成员列表失败", e);
		}
		
		page.setPageSize(pageParam.getPageSize());
		page.setCurrentPage(pageParam.getCurrentPage());
		page.setRecordCount(recordCount);
		page.setPageCount(recordCount);
		page.setDataList(dataList);
	    return page;
	}

	/** 
	 * t_agent_info  t_agt_team  t_micro_info  这三个表都有status状态字段 (及t_user.userStatus的状态必须为A有效)其中任意一个为失效状态，当前用户就不能登录
	 */
	public static final ThreadLocal<String> tl = new ThreadLocal<>();
	
	@Override
	public boolean microIsAllowLogon(String userId, String openId) {
		MicroDTO microDTO = null;
		UserDTO userDTO = null;
		try {
			
			//小微是否存在并且有效
			if(StringUtils.isNotBlank(userId)){
				microDTO = microDao.getMicroByUserId(userId);
				if(null == microDTO){
					tl.set("用户不存在或已失效");
					logger.info("小微用户不存在或已失效"+userId);
					return false;
				}
				userDTO = userDAO.queryUserByUserId(userId);
				
				if(null == userDTO){
					tl.set("用户不存在或已失效");
					logger.info("账户不存在"+userId);
					return false;
				}
				
				//判断协议是否有效
				CompanyAccInfoDTO companyAccInfoDTO = microDao.getCompanyAccinfoByCompCode(userId);
				if(companyAccInfoDTO != null && new Date().getTime() > companyAccInfoDTO.getEndDate().getTime()){
					tl.set("尊敬的用户，您所在代理公司的保行天下使用协议已到期，我们已停止保行天下的服务。");
					logger.info("尊敬的用户，您所在代理公司的保行天下使用协议已到期，我们已停止保行天下的服务。userId:"+userId);
					return false;
				}
				
				if("L".equals(userDTO.getUserStatus())){
					tl.set("账户已锁定");
					logger.info("账户已锁定"+userId);
					return false;
				}
			}
			else if(StringUtils.isNotBlank(openId)){
				userDTO = userDAO.queryUser(openId);
				if(null == userDTO){
					tl.set("用户不存在或已失效");
					logger.info("账户不存在,微信OPENID:"+openId);
					return false;
				}
				userId = userDTO.getUserId();

				microDTO = microDao.getMicroByUserId(userId);
				
				//判断协议是否有效
				CompanyAccInfoDTO companyAccInfoDTO = microDao.getCompanyAccinfoByCompCode(userId);
				if(companyAccInfoDTO != null && new Date().getTime() > companyAccInfoDTO.getEndDate().getTime()){
					tl.set("尊敬的用户，您所在代理公司的保行天下使用协议已到期，我们已停止保行天下的服务。");
					logger.info("尊敬的用户，您所在代理公司的保行天下使用协议已到期，我们已停止保行天下的服务。userId:"+userId);
					return false;
				}
				
				if("L".equals(userDTO.getUserStatus())){
					tl.set("账户已锁定");
					logger.info("账户已锁定"+userId);
					return false;
				}
				
				if(null == microDTO){
					tl.set("用户不存在或已失效");
					logger.info("小微用户不存在或已失效"+userId);
					return false;
				}
			}
			
			//校验团队及代理是否有效
			HashMap<String, String> agtTeamDTO = agencyDAO.getAgtTeamByTeamId(microDTO.getAgt_team_id());
			if(null == agtTeamDTO || agtTeamDTO.isEmpty()){
				tl.set("当前用户所属团队不存在或者团队已失效");
				logger.info("当前用户所属团队不存在或者团队已失效"+microDTO.getAgt_team_id());
				return false;
			}
			
			HashMap<String, String> agtDTO = agencyDAO.getAgtByAgtId(microDTO.getAgency().getAgent_id());
			if(null == agtDTO || agtDTO.isEmpty()){
				tl.set("当前用户所属代理不存在或者代理已失效");
				logger.info("当前用户所属代理不存在或者代理已失效"+microDTO.getAgency().getAgent_id());
				return false;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			tl.set("认证失败:"+e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public String getStatusByInsIdAndMicroId(String insId, String microId,String userId,String serviceType) throws BusinessServiceException {
		String status = "";
		try {
			Integer i = teamParamMappingDAO.getCountAutoQuote(userId, serviceType);
			String quotnType = "";
			if(i > 0){
				quotnType = "A";
			}else{
				quotnType = "M";
			}
			status = microDao.getStatusByInsIdAndMicroId(insId, microId, quotnType,serviceType);
		} catch (Exception e) {
			logger.error("根据保险公司ID或小微ID得到保险公司status失败",e);
			throw new BusinessServiceException("根据保险公司ID或小微ID得到保险公司status失败");
		}
		return status;
	}

}
