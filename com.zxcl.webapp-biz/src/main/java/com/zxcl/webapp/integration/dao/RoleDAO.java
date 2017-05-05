package com.zxcl.webapp.integration.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;



@Primary
public interface RoleDAO {

	/**
	 * 删除用户的角色
	 * 
	 * @param userId
	 *            um账号
	 */
	public void deleteUserRole(@Param("userId") String userId) throws Exception;

	/**
	 * 为用户授予角色
	 * 
	 * @param userId
	 *            um账号
	 * @param roleId
	 *            角色
	 */
	public void insertUserRole(@Param("userId") String userId, @Param("roleId") String roleId) throws Exception;
	/**
	 * 为角色授权资源
	 * @param roleId
	 * @param resourceId
	 */
	public void insertRoleResources(@Param("roleId") String roleId,@Param("resourceId") String resourceId) throws Exception;
	/**
	 * 删除
	 * @param roleId
	 */
	public void deleteByResourcesId(@Param("roleId") String roleId) throws Exception;
}