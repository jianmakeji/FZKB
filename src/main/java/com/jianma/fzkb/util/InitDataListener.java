package com.jianma.fzkb.util;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ServletContextAware;

import com.jianma.fzkb.cache.redis.InitCache;

@Repository
public class InitDataListener implements InitializingBean, ServletContextAware {
	
	@Autowired
	@Qualifier(value = "initCacheImpl")
	private InitCache initCacheImpl;
	
	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initCacheImpl.clearAllCache();
		
		initCacheImpl.initMaterial();
		
		initCacheImpl.initMatch();
		
	}

}
