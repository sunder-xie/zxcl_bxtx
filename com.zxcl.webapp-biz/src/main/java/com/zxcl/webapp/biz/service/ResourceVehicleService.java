package com.zxcl.webapp.biz.service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.bizdto.ResourceVehicleDTO;
/**
 * 
* @ClassName:  ResourceVehicleService 
* @Description: 车辆信息业务处理
* @author 赵晋
* @date 2015年11月18日 上午10:58:22
*
 */
public interface ResourceVehicleService {

	/**
	 * 
	* @Title: getResVehicleByPlateNoService
	* @Description: 根据车牌号查询车辆信息
	* @param plateNo 车牌号
	* @throws BusinessServiceException
	* @return ResourceVehicleDTO
	* @throws
	 */
	public ResourceVehicleDTO getResVehicleByPlateNoService(String plateNo) throws BusinessServiceException;
	
	/**
	 * getVehicleByRMI
	 * @param areaCode
	 * @param plateNo
	 * @param totalSeconds 多少秒内必须返回无论有没有结果
	 * @return
	 * @throws BusinessServiceException
	 */
	public ResourceVehicleDTO getVehicleByRMI(String areaCode, String plateNo, int totalSeconds, String userId,String ownerName) throws Exception;
	
	/**
	 * getVehicleByRMI
	 * 与上一个方法不用的是,如果未带出保险期限,会去平安爬虫报价获取重复投保的保险期限
	 * @param plateNo
	 * @param frmNO
	 * @param enginNo
	 * @param ownerName
	 * @param certNo
	 * @return
	 * @throws Exception
	 */
	public ResourceVehicleDTO getVehicleByRMI(String plateNo, String frmNO, String enginNo, String ownerName, String certNo, String userId) throws Exception;
	
	/**
	 * @param modelName
	 * @return
	 * @throws BusinessServiceException
	 */
	public ResourceVehicleDTO getResVehicleByModelName(String modelName) throws BusinessServiceException;

	/**
	 * 添加资源信息
	 * @param inquiryId 询价单号
	 * @throws BusinessServiceException
	 */
	public void insert(String inquiryId) throws BusinessServiceException;
}
