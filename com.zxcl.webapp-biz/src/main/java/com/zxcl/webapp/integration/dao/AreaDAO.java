package com.zxcl.webapp.integration.dao;

import java.util.List;

import com.meyacom.fw.app.dto.PagingResult;

import com.zxcl.webapp.dto.AreaDTO;

/**
 * 
 * @author 5555
 *
 */
public interface AreaDAO {
	/**
	 * 获取所有的省级地区（包括北京）
	 * 
	 * @return
	 */
	public PagingResult<AreaDTO> getProvinceAreas() throws Exception;;

	/**
	 * 根据传入的省份code获取省份下面所有的市
	 * 
	 * @return
	 */
	public PagingResult<AreaDTO> getCitysByProvinceCode(String provinceCode) throws Exception;;

	/**
	 * 根据名称返回省份信息
	 * 
	 * @return
	 */
	public AreaDTO getProvinceByName(String name) throws Exception;;

	/**
	 * 根据code查询信息
	 * 
	 * @return
	 */
	public AreaDTO getAreaByCode(String code) throws Exception;

	/**
	 * 根据城市CODE，得到省份信息
	 * 
	 * @return
	 */
	public AreaDTO getProvinceByCityCode(String code) throws Exception;

	/**
	 * 根据小微编码得到信息
	 * 
	 * @param microId
	 * @return
	 */
	public AreaDTO getAreaByMicro(String microId) throws Exception;

	/**
	 * 根据小微编码得到地区信息
	 * 
	 * @param microId
	 * @return
	 */
	public List<AreaDTO> getAreasByMicro(String microId) throws Exception;

	public List<AreaDTO> getAll() throws Exception;
}
