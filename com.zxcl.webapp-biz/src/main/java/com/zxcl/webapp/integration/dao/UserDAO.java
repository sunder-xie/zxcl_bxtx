package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meyacom.fw.um.dto.Role;
import com.meyacom.fw.um.dto.UserInfo;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.UserDTO;

public interface UserDAO {

	public UserDTO queryUser(@Param("weChatId") String weChatId) throws Exception;
	
	public UserInfo queryUser2(String userId);

	/**
	 * 根据登录的id，查找对应的角色
	 * 
	 * @param userId
	 * @return
	 */
	public Role getRoleByUserId(String userId) throws Exception;

	/**
	 * 根据userId 查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserDTO queryUserByUserId(@Param("userId") String userId) throws Exception;
	
	public List<UserDTO> queryUserByUserIds(@Param("userIds") List<String> userIds) throws Exception;

	/**
	 * 根据userId去绑定微信（将weChatId 插入到对应的数据中）
	 */
	public void updateWeChatId(@Param("weChatId") String weChatId, @Param("nickName") String nickName, @Param("userId") String userId) throws Exception;

	/**
	 * 根据userId去解除绑定微信（将weChatId设为空字符串）
	 */
	public void deleteWeChatId(String userId) throws Exception;

	/**
	 * 修改密码
	 * @param userId
	 * @param password
	 */
	public void updatePassword(@Param("userId") String userId, @Param("password") String password) throws Exception;

	public void insertUser(MicroDTO microDTO) throws Exception;
	/*
	 * 获取最大的邀请码
	 */
	public Integer getMaxinvitation() throws Exception;
	
	public UserDTO getUserIDyInvitation(String authenticatedUserId)throws Exception;

	public Integer getMyfrend(String invitation)throws Exception;

	public void addSuggest(@Param("userId")String userId, @Param("suggest")String suggest) throws Exception;

	public int updateUserName(@Param("userId")String userId, @Param("userName")String microName);

	public int updateAgtByUserId(@Param("agtId")String agtId, @Param("userId")String userId);

	public int updateRelevanceByUserId(@Param("relevanceId") String relevanceId, @Param("userId") String userId);

}
