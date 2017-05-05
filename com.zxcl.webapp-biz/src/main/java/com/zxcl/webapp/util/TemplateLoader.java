package com.zxcl.webapp.util;

import java.io.InputStream;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
/**
 * Velocityģ模板文件加载器
 * @author wenchang001
 */
public class TemplateLoader extends ResourceLoader{

	@Override
	public long getLastModified(Resource arg0) {
		return 0;
	}

	@Override
	public InputStream getResourceStream(String path)
			throws ResourceNotFoundException {
		return this.getClass().getResourceAsStream(path);
	}

	@Override
	public void init(ExtendedProperties arg0) {
	}

	@Override
	public boolean isSourceModified(Resource arg0) {
		return false;
	}

}
