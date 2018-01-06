package com.jianma.fzkb.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jianma.fzkb.dao.MatchDao;
import com.jianma.fzkb.model.Match;

@Repository
@Component
@Qualifier(value = "matchDaoImpl")
public class MatchDaoImpl implements MatchDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void createMatch(Match match) {
		sessionFactory.getCurrentSession().save(match);
	}

	@Override
	public void updateMatch(Match match) {
		sessionFactory.getCurrentSession().update(match);
	}

	@Override
	public void deleteMatch(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " delete from Match m where m.id = ? ";
		Query query = session.createQuery(hql);
        query.setParameter(0, id); 
		query.executeUpdate();
	}

	@Override
	public Optional<Match> getDataByMatchId(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " from Match where id = ?";
		Query query = session.createQuery(hql);
        query.setParameter(0, id); 
        @SuppressWarnings("unchecked")
		List<Match> list = query.list();
        if (list.size() > 0){
        	Optional<Match> match = Optional.ofNullable(list.get(0));
     		return match;
        }
        else{
        	return Optional.empty();
        }
	}

	@Override
	public List<Match> getMatchPageByUserId(int offset, int limit, int userId) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "";
		if (userId == 0){
			hql = "select m.id,m.userId,m.name,m.underwear,m.greatcoat,m.trousers,m.createTime,d.realname,"
					+ " m.uwId,m.gcId,m.trId from Match m,Designer d where m.userId = d.id ";
		}
		else{
			hql = "select m.id,m.userId,m.name,m.underwear,m.greatcoat,m.trousers,m.createTime,"
					+ " d.realname,m.uwId,m.gcId,m.trId from Match m,Designer d where m.userId = d.id and m.userId = ?";
		}
		
		final Query query = session.createQuery(hql); 
		if (userId != 0){
			query.setParameter(0, userId);
		}
        query.setFirstResult(offset);    
        query.setMaxResults(limit); 
        @SuppressWarnings("unchecked")
		final List list = query.list();
     	List<Match> mList = new  ArrayList<Match>(10);
		for(int i=0; i<list.size(); i++)
        {
			Match match = new Match();
            Object []o = (Object[])list.get(i);
            int id = ((Number)o[0]).intValue();
            int uId = ((Number)o[1]).intValue();
            String name = (String)o[2];
            String underwear = (String)o[3];
            String greatcoat = (String)o[4];
            String trousers = (String)o[5];
            Date createTime = (Date)o[6];
            String realname = (String)o[7];
            int uwId = ((Number)o[8]).intValue();
            int gcId = ((Number)o[9]).intValue();
            int trId = ((Number)o[10]).intValue();
            match.setId(id);
            match.setName(name);
            match.setUserId(uId);
            match.setCreateTime(createTime);
            match.setGreatcoat(greatcoat);
            match.setTrousers(trousers);
            match.setUnderwear(underwear);
            match.setUsername(realname);
            match.setUwId(uwId);
            match.setGcId(gcId);
            match.setTrId(trId);
            mList.add(match);
        }
		return mList;
	}

	@Override
	public int getCountMatchByUserId(int userId) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "";
		if (userId == 0){
			hql = " select count(m) from Match m"; 
		}
		else{
			hql = " select count(m) from Match m where m.userId = ?"; 
		}
        final Query query = session.createQuery(hql); 
        if (userId != 0){
			query.setParameter(0, userId);
		}
        long count = (Long)query.uniqueResult();
        return (int)count;
	}

	@Override
	public List<Match> getMatchBySearchKeyword(int offset, int limit, int userId, String keyword) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "";
		if (userId == 0){
			hql = "select m.id,m.userId,m.name,m.underwear,m.greatcoat,m.trousers,m.createTime,d.realname,m.uwId,m.gcId,m.trId from "
					+ " Match m,Designer d where m.userId = d.id and m.name like ? ";
		}
		else{
			hql = "select m.id,m.userId,m.name,m.underwear,m.greatcoat,m.trousers,m.createTime,d.realname,m.uwId,m.gcId,m.trId from "
					+ " Match m,Designer d where m.userId = d.id and m.userId = ? and m.name like ? ";
		}
		
		final Query query = session.createQuery(hql); 
		if (userId != 0){
			query.setParameter(0, userId);
			query.setParameter(1, "%" + keyword + "%");
		}
		else{
			query.setParameter(0, "%" + keyword + "%");
		}
        query.setFirstResult(offset);    
        query.setMaxResults(limit); 
        @SuppressWarnings("unchecked")
		final List list = query.list();
     	List<Match> mList = new  ArrayList<Match>(10);
		for(int i=0; i<list.size(); i++)
        {
			Match match = new Match();
            Object []o = (Object[])list.get(i);
            int id = ((Number)o[0]).intValue();
            int uId = ((Number)o[1]).intValue();
            String name = (String)o[2];
            String underwear = (String)o[3];
            String greatcoat = (String)o[4];
            String trousers = (String)o[5];
            Date createTime = (Date)o[6];
            String realname = (String)o[7];
            int uwId = ((Number)o[8]).intValue();
            int gcId = ((Number)o[9]).intValue();
            int trId = ((Number)o[10]).intValue();
            match.setId(id);
            match.setName(name);
            match.setUserId(uId);
            match.setCreateTime(createTime);
            match.setGreatcoat(greatcoat);
            match.setTrousers(trousers);
            match.setUnderwear(underwear);
            match.setUsername(realname);
            match.setUwId(uwId);
            match.setGcId(gcId);
            match.setTrId(trId);
            mList.add(match);
        }
		return mList;
	}

	@Override
	public int getCountMatchBySearchKeyword(int userId, String keyword) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "";
		if (userId == 0){
			hql = " select count(m) from Match m where m.name like ? "; 
		}
		else{
			hql = " select count(m) from Match m where m.userId = ? and m.name like ? "; 
		}
        final Query query = session.createQuery(hql); 
        if (userId != 0){
			query.setParameter(0, userId);
			query.setParameter(1, "%" + keyword + "%");
		}
        else{
        	query.setParameter(0, "%" + keyword + "%");
        }
        long count = (Long)query.uniqueResult();
        return (int)count;
	}

}
