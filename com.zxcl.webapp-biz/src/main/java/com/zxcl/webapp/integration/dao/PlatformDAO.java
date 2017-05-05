package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;



import com.zxcl.webapp.dto.PlatformDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;

/**
 * 平台基础信息
 * 
 * @author 5555
 *
 */
public interface PlatformDAO {

	public List<PlatformDTO> getByCodeClass(String codeClass) throws Exception;

	public List<PlatformDTO> getByCode(@Param("codeClass") String codeClass,
			@Param("items") List<CoverageItemDTO> items) throws Exception;

	public PlatformDTO getPlatByCode(@Param("codeClass") String codeClass,
			@Param("code") String code) throws Exception;
	
	public void insert(PlatformDTO platformDTO) throws Exception;
	
	public void updAccessToken(@Param("codeClass") String accessToken, @Param("name")String token);
}
