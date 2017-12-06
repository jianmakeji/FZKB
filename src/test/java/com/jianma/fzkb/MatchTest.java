package com.jianma.fzkb;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jianma.fzkb.cache.redis.MatchCache;
import com.jianma.fzkb.service.MatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class MatchTest {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	@Qualifier(value="matchServiceImpl")
	private MatchService matchServiceImpl;
	
	@Autowired
	@Qualifier("materialCacheImpl")
	private MatchCache matchCacheImpl;
	
	public void addMatch(){
		
	}
}
