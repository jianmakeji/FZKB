package com.jianma.fzkb.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Qualifier(value = "configInfo")
@PropertySource(value="classpath:config.properties")
public class ConfigInfo {

	@Value("${endpoint}")
	public String endpoint;
	
	@Value("${accessId}")
	public String accessId;
	
	@Value("${accessKey}")
	public String accessKey;
	
	@Value("${bucket}")
	public String bucket;
			
}
