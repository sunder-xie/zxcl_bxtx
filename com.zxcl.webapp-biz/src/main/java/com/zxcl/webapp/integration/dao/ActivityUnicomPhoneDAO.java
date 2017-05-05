package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.dto.activity.unicome.ActivityUnicomPhoneDTO;

/**
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 
 */
public interface ActivityUnicomPhoneDAO {
    int deleteByPrimaryKey(String phoneId);

    int insertSelective(ActivityUnicomPhoneDTO record);

    ActivityUnicomPhoneDTO selectByPrimaryKey(String phoneId);

    int updateByPrimaryKeySelective(ActivityUnicomPhoneDTO record);

	int phoneListCount(PageParam pageParam);

	List<ActivityUnicomPhoneDTO> phoneList(PageParam pageParam);
	
	List<ActivityUnicomPhoneDTO> lianghaoPhoneList(@Param("pageSize")Integer pageSize, @Param("mealId")Integer mealId);
	
}