package com.zxcl.webapp.biz.action.call;

import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;

public interface HttpAlltrustCallAction {
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId)
			throws ActionException;
}
