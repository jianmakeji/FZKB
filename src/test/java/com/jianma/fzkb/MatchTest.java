package com.jianma.fzkb;

import java.util.Date;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jianma.fzkb.cache.redis.MatchCache;
import com.jianma.fzkb.model.Match;
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
		Match match = new Match();
		match.setName("我的搭配");
		match.setUserId(2);
		match.setUsername("设计师A");
		match.setUwId(15);
		match.setUnderwear("");
		match.setGcId(12);
		match.setGreatcoat("");
		match.setTrId(11);
		match.setTrousers("");
		match.setCreateTime(new Date());
		matchServiceImpl.createMatch(match);
		
		match.setName("我的搭配2");
		match.setUserId(2);
		match.setUsername("设计师AM");
		match.setUwId(16);
		match.setUnderwear("http://www.");
		match.setGcId(17);
		match.setGreatcoat("http://www.");
		match.setTrId(19);
		match.setTrousers("http://www.");
		match.setCreateTime(new Date());
		matchServiceImpl.createMatch(match);
		
		match.setName("我的搭配3");
		match.setUserId(2);
		match.setUsername("设计师AE");
		match.setUwId(16);
		match.setUnderwear("http://www.");
		match.setGcId(17);
		match.setGreatcoat("http://www.");
		match.setTrId(19);
		match.setTrousers("http://www.");
		match.setCreateTime(new Date());
		matchServiceImpl.createMatch(match);
		
		match.setName("我的搭配4");
		match.setUserId(2);
		match.setUsername("设计师AD");
		match.setUwId(16);
		match.setUnderwear("http://www.");
		match.setGcId(17);
		match.setGreatcoat("http://www.");
		match.setTrId(19);
		match.setTrousers("http://www.");
		match.setCreateTime(new Date());
		matchServiceImpl.createMatch(match);
		
		match.setName("我的搭配5");
		match.setUserId(2);
		match.setUsername("设计师AC");
		match.setUwId(16);
		match.setUnderwear("http://www.");
		match.setGcId(17);
		match.setGreatcoat("http://www.");
		match.setTrId(19);
		match.setTrousers("http://www.");
		match.setCreateTime(new Date());
		matchServiceImpl.createMatch(match);
	}
	
	public void updateMatch(){
		Match match = new Match();
		match.setId(1);
		match.setName("我的搭配x");
		match.setUserId(2);
		match.setUsername("设计师A");
		match.setUwId(15);
		match.setUnderwear("http://www.");
		match.setGcId(12);
		match.setGreatcoat("http://www.");
		match.setTrId(11);
		match.setTrousers("http://www.");
		match.setCreateTime(new Date());
		matchServiceImpl.updateMatch(match);
	}
	
	public void deleteMatch(){
		matchServiceImpl.deleteMatch(1);
	}
	
	public void loadMatch(){
		
	}
}
