package com.zxcl.webapp.integration.dao;

import com.zxcl.webapp.dto.LoginLoggingDTO;

/**
 *  登录历史dao
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 
 */
public interface LoginLoggingDAO {

    /**
     * 记录登录历史
     * @param loglogin 登录日志对象
     * @return
     */
    public int insertLogLogin(LoginLoggingDTO loglogin);

}
