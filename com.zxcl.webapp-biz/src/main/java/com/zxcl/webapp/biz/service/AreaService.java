package com.zxcl.webapp.biz.service;

import java.util.List;

import com.meyacom.fw.app.dto.PagingResult;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.AreaDTO;

/**
 * 地区Service
 * 
 * @author 5555
 *
 */
public interface AreaService {
	/**
	 * 获取所有的省级地区（包括北京）
	 * 
	 * @return
	 * @throws BusinessServiceException 
	 * @throws Exception
	 */
	public PagingResult<AreaDTO> getProvinces() throws BusinessServiceException;

	/**
	 * 根据传入的省份code获取省份下面所有的市
	 * 
	 * @return
	 * @throws BusinessServiceException 
	 */
	public List<AreaDTO> getCitysByProvinceCode(String provinceCode) throws BusinessServiceException;

	/**
	 * 根据名称返回省份信息
	 * 
	 * @return
	 * @throws BusinessServiceException 
	 * @throws Exception
	 */
	public AreaDTO getProvinceByIP(String ip) throws BusinessServiceException;

	/**
	 * 根据ip返回市的信息
	 * 
	 * @return
	 * @throws BusinessServiceException 
	 * @throws Exception
	 */
	public AreaDTO getCityByIP(String ip) throws BusinessServiceException;

	/**
	 * 根据code返回单条记录
	 * @throws BusinessServiceException 
	 * 
	 */
	public AreaDTO get(String code) throws BusinessServiceException;

	/**
	 * 根据Code返回最简单的城市信息
	 * 
	 * @param code
	 * @return
	 * @throws BusinessServiceException 
	 */
	public AreaDTO getCityByCode(String code) throws BusinessServiceException;

	/**
	 * 根据城市CODE，得到省份信息
	 * 
	 * @return
	 * @throws BusinessServiceException 
	 */
	public AreaDTO getProvinceByCityCode(String code) throws BusinessServiceException;

	/**
	 * 根据code得到省份编码
	 * 
	 * @param code
	 * @return
	 * @throws BusinessServiceException 
	 */
	public String getProvinceCity(String code) throws BusinessServiceException;

	/**
	 * 根据小微编码得到信息
	 * 
	 * @param microId
	 * @return
	 */
	// public AreaDTO getAreaByMicro(String microId);

	/**
	 * 根据小微编码得到地区信息
	 * 
	 * @param microId
	 * @return
	 */
	// public List<AreaDTO> getAreasByMicro(String microId);

	/**
	 * 修改城市名称
	 * 
	 * @param microId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public AreaDTO reviseName(AreaDTO area) throws BusinessServiceException;
}
