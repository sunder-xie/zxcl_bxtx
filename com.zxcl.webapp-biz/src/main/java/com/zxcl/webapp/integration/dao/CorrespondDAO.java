package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;



import com.zxcl.webapp.dto.CorrespondDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;

/**
 * 系统的基本信息查询保险公司的相关信息
 * 
 * @author 5555
 *
 */
public interface CorrespondDAO {

	/**
	 * 根据自定义的险种code查询出保险公司的险种信息
	 * 
	 * @param items
	 * @return
	 */
	public List<CorrespondDTO> getInsInfo(@Param("insId") String insId,
			@Param("items") List<CoverageItemDTO> items) throws Exception;

	public CorrespondDTO get(CorrespondDTO corr) throws Exception;
	public CorrespondDTO getTwo(CorrespondDTO corr) throws Exception;

	/**
	 * 根据保险公司ID获取
	 * 
	 * @param insId
	 * @return
	 */
	public List<CorrespondDTO> getCorrespondByInsId(String insId) throws Exception;
}
