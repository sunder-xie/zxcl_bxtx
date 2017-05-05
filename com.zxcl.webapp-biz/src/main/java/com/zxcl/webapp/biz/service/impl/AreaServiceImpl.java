package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meyacom.fw.app.dto.PagingResult;
import com.zxcl.webapp.biz.exception.BusinessServiceException;

import com.zxcl.webapp.biz.service.AreaService;
import com.zxcl.webapp.biz.util.AddressUtils;
import com.zxcl.webapp.dto.AreaDTO;
import com.zxcl.webapp.integration.dao.AreaDAO;

/**
 * 地区的Service的实现
 * 
 * @author 5555
 *
 */
@Service
public class AreaServiceImpl implements AreaService {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private AreaDAO areaDAO;

	/**
	 * 获取所有省级地区(包括北京等)
	 * 
	 * @throws Exception
	 */
	@Override
	public PagingResult<AreaDTO> getProvinces() throws BusinessServiceException {
		PagingResult<AreaDTO> areaDTOs = null;
		try {
			areaDTOs = areaDAO.getProvinceAreas();
		} catch (Exception e) {
			logger.error("获取所有的省级地区失败:" + e,e);
			throw new BusinessServiceException("获取所有的省级地区失败");
		}
		return areaDTOs;
	}

	/**
	 * 根据传入的省份code获取省份下面所有的市
	 * 
	 * @returns
	 */
	@Override
	public List<AreaDTO> getCitysByProvinceCode(String provinceCode)
			throws BusinessServiceException {
		logger.info("根据传入的省份code获取省份下面所有的市入参    省份ID："+provinceCode);
		List<AreaDTO> citys = new ArrayList<AreaDTO>();
		try {
			PagingResult<AreaDTO> citysPaging = areaDAO.getCitysByProvinceCode(provinceCode);
			logger.info("将获取到的市名称截取掉省份");
			// 将获取到的市名称截取掉省份
			AreaDTO areaProvince = areaDAO.getAreaByCode(provinceCode);
			for (AreaDTO areaCity : citysPaging) {
				// 截取名称
				String cityName = areaCity.getName().substring(areaProvince.getName().length(),
						areaCity.getName().length());
				if (!cityName.equals(areaProvince.getName())) {
					areaCity.setName(cityName);
					citys.add(areaCity);
				}
			}
		} catch (Exception e) {
			logger.error("根据省级编码查找省级下面的所有市级信息失败:" + e,e);
			throw new BusinessServiceException("根据传入的省份code获取省份下面所有的市失败");
		}
		return citys;

	}

	/**
	 * 根据ip查询到的地址返回省份信息
	 * 
	 * @param ip
	 */
	@Override
	public AreaDTO getProvinceByIP(String ip) throws BusinessServiceException {
		logger.info("根据ip查询到的地址返回省份信息 入参    IP："+ip);
		AreaDTO province = null;
		try {
			String cityName = AddressUtils.getAddresses(ip);
			province = areaDAO.getProvinceByName(cityName);
			if (province == null) {
				// 定位到北京（北京的code:110000）
				province = areaDAO.getAreaByCode("110000");
			}
		} catch (Exception e) {
			logger.error("根据传入的IP地址获取省份信息失败:" + e,e);
			throw new BusinessServiceException("根据ip查询到的地址返回省份信息失败");
		}
		return province;
	}

	/**
	 * 根据ip查询到的地址返回市的信息
	 * 
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	@Override
	public AreaDTO getCityByIP(String ip) throws BusinessServiceException {
		logger.info("根据ip查询到的地址返回市的信息入参    IP："+ip);
		AreaDTO city = null;
		try {
			// 获取此地址的省份，进行截取
			String cityName = AddressUtils.getAddresses(ip);
			city = areaDAO.getProvinceByName(cityName);
			if (city != null) {
				city.setName(cityName.substring(city.getName().length(), cityName.length()));
			}
		} catch (Exception e) {
			logger.error("根据传入的IP地址获取市级信息失败:" + e,e);
			throw new BusinessServiceException("根据ip查询到的地址返回市的信息失败");
		}
		return city;
	}

	/**
	 * 根据code返回单条记录
	 */
	public AreaDTO get(String code) throws BusinessServiceException {
		logger.info("根据code返回单条记录 入参    code："+code);
		AreaDTO areaDTO = null;
		try {
			areaDTO = areaDAO.getAreaByCode(code);
		} catch (Exception e) {
			logger.error("查询城市信息失败:" + e,e);
			throw new BusinessServiceException("根据code返回单条记录失败");
		}
		return areaDTO;
	}

	/**
	 * 根据Code返回最简单的城市信息
	 * 
	 * @param code
	 * @return
	 */
	public AreaDTO getCityByCode(String code) throws BusinessServiceException {
		logger.info("根据Code返回最简单的城市信息 入参    code："+code);
		AreaDTO areaCity = null;
		try {
			areaCity = areaDAO.getAreaByCode(code);
			logger.info("根据名称返回省份信息");
			AreaDTO areaProvince = areaDAO.getProvinceByName(areaCity.getName());
			if (null != areaProvince) {
				areaCity.setName(areaCity.getName().substring(areaProvince.getName().length(),
						areaCity.getName().length()));
			}
		} catch (Exception e) {
			logger.error("查询城市:"+code+"信息失败:" + e,e);
			throw new BusinessServiceException("根据Code返回最简单的城市信息失败");
		}
		return areaCity;

	}

	@Override
	public AreaDTO getProvinceByCityCode(String code) throws BusinessServiceException {
		logger.info("根据城市CODE得到省份信息 入参    code："+code);
		AreaDTO areaDTO = null;
		try {
			areaDTO = areaDAO.getProvinceByCityCode(code);
		} catch (Exception e) {
			logger.error("获取传入市级编码:" + code + "的省级信息失败:" + e,e);
			throw new BusinessServiceException("根据城市CODE得到省份信息失败");
		}
		return areaDTO;
	}

	@Override
	public String getProvinceCity(String code) throws BusinessServiceException {
		logger.info("根据code得到省份编码 入参    code："+code);
		String provinceCode = "";
		try {
			provinceCode = areaDAO.getAreaByCode(code).getCode();
		} catch (Exception e) {
			logger.error("获取传入市级编码:" + code + "的省级编码失败:" + e,e);
			throw new BusinessServiceException("根据code得到省份编码失败");
		}
		return provinceCode;
	}

	// @Override
	// public AreaDTO getAreaByMicro(String microId) {
	// try {
	// return areaDAO.getAreaByMicro(microId);
	// } catch (Exception e) {
	// logger.error("参数.microId:" + microId + ".查询小薇");
	// }
	//
	// }

	// @Override
	// public List<AreaDTO> getAreasByMicro(String microId) {
	// try {
	// return areaDAO.getAreasByMicro(microId);
	// } catch (Exception e) {
	// logger.error("参数.microId："+microId+ "");
	// }
	//
	// }

	@Override
	public AreaDTO reviseName(AreaDTO area) throws BusinessServiceException {
		logger.info("修改城市名称 入参    AreaDTO："+area);
		try {
			logger.info("根据城市CODE得到省份信息");
			AreaDTO province = areaDAO.getProvinceByCityCode(area.getCode());
			area.setName(area.getName().substring(province.getName().length(),
					area.getName().length()));
		} catch (Exception e) {
			logger.error("修改城市名称失败:" + e,e);
			throw new BusinessServiceException("修改城市名称失败");
		}
		return area;
	}
}
