package com.zxcl.webapp.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bxtx.intf.weixin.biz.service.WeiXinService;

import com.zxcl.webapp.biz.service.ShareWeixinDataService;
import com.zxcl.webapp.util.WeiXinUtils;

@Service("shareWeixinDataServiceImpl")
public class ShareWeixinDataServiceImpl implements ShareWeixinDataService {

	@Autowired
	private WeiXinService weiXinService;
	
	@Override
	public String getTOKEN() {
		return weiXinService.getAccessToken();
	}

}
