package com.jianma.fzkb;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jianma.fzkb.cache.redis.MatchCache;
import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.MatchTableModel;
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
	@Qualifier("matchCacheImpl")
	private MatchCache matchCacheImpl;
	
	//@Test
	public void addMatch(){
		int Max=45, Min=16;
				
		Match match = new Match();
		match.setName("我的搭配");
		match.setUserId(2);
		match.setUsername("设计师A");
		match.setUwId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setUnderwear("");
		match.setGcId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setGreatcoat("");
		match.setTrId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setTrousers("");
		match.setCreateTime(new Date());
		matchServiceImpl.createMatch(match);
		
		match.setName("我的搭配2");
		match.setUserId(2);
		match.setUsername("设计师AM");
		match.setUwId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setUnderwear("http://www.");
		match.setGcId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setGreatcoat("http://www.");
		match.setTrId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setTrousers("http://www.");
		match.setCreateTime(new Date());
		matchServiceImpl.createMatch(match);
		
		match.setName("我的搭配3");
		match.setUserId(2);
		match.setUsername("设计师AE");
		match.setUwId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setUnderwear("http://www.");
		match.setGcId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setGreatcoat("http://www.");
		match.setTrId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setTrousers("http://www.");
		match.setCreateTime(new Date());
		matchServiceImpl.createMatch(match);
		
		match.setName("我的搭配4");
		match.setUserId(3);
		match.setUsername("设计师AD");
		match.setUwId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setUnderwear("http://www.");
		match.setGcId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setGreatcoat("http://www.");
		match.setTrId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setTrousers("http://www.");
		match.setCreateTime(new Date());
		matchServiceImpl.createMatch(match);
		
		match.setName("我的搭配5");
		match.setUserId(4);
		match.setUsername("设计师AC");
		match.setUwId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setUnderwear("http://www.");
		match.setGcId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setGreatcoat("http://www.");
		match.setTrId((int)Math.round(Math.random()*(Max-Min)+Min));
		match.setTrousers("http://www.");
		match.setCreateTime(new Date());
		matchServiceImpl.createMatch(match);
	}
	
	//@Test
	public void updateMatch(){
		Match match = new Match();
		match.setId(1);
		match.setName("我的搭配x");
		match.setUserId(2);
		match.setUsername("设计师A");
		match.setUwId(19);
		match.setUnderwear("http://www.");
		match.setGcId(22);
		match.setGreatcoat("http://www.");
		match.setTrId(33);
		match.setTrousers("http://www.");
		match.setCreateTime(new Date());
		matchServiceImpl.updateMatch(match);
	}
	
	//@Test
	public void deleteMatch(){
		matchServiceImpl.deleteMatch(2);
	}
	
	@Test
	public void loadMatch(){
		/*
		Map<Object,Object> map = redisTemplate.opsForHash().entries("match:data:1");
		map.forEach((k,v)->{
			System.out.println(k+"  "+v);
		});
		*/
		
		/*
		Map<String,String> map = new HashMap<>();
		map.put("category", "动物,风景");
		map.put("style1", "-1.0,1.0,3.0");
		map.put("style2", "1.0,2.0,1.5,3.0");
		map.put("style3", "-0.5,-1.5,-2.5");
		
		MatchTableModel matchTableModel = matchServiceImpl.getMatchPageByCondition(0, 10, map);
		System.out.println(matchTableModel.getCount());
		
		List<Match> list = matchTableModel.getList();
		System.out.println(list.size());
		
		list.stream().forEach((match)->{
			System.out.println(match.getId() + " " +match.getGreatcoat() + " " +match.getTrousers() + "  " + match.getUnderwear());
		});
		*/
		
		Map<String,String> map = new HashMap<>();
		map.put("category", "动物,风景");
		map.put("style1", "");
		map.put("style2", "");
		map.put("style3", "");
		
		MatchTableModel matchTableModel = matchServiceImpl.getMatchPageByCondition(10, 10, map);
		
		List<Match> list = matchTableModel.getList();
		
		list.stream().forEach((match)->{
			System.out.println(match.getId() + " " +match.getGreatcoat() + " " +match.getTrousers() + "  " + match.getUnderwear());
		});
		
	}
}
