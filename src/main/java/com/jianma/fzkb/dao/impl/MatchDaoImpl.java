package com.jianma.fzkb.dao.impl;

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
	public void updateMaterial(Match match) {
		sessionFactory.getCurrentSession().update(match);
	}

	@Override
	public void deleteMaterial(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " delete from Match m where m.id = ? ";
		Query query = session.createQuery(hql);
        query.setParameter(0, id); 
		query.executeUpdate();
	}

	@Override
	public List<Match> getMatchByPage(int offset, int limit) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " from Match";
		final Query query = session.createQuery(hql); 
        query.setFirstResult(offset);    
        query.setMaxResults(limit); 
        @SuppressWarnings("unchecked")
		final List<Match> list = query.list(); 
		return list;
	}

	@Override
	public int getCountMatch() {
		Session session = this.getSessionFactory().getCurrentSession();
		final String hql = " select count(m) from Match m"; 
        final Query query = session.createQuery(hql); 
        long count = (Long)query.uniqueResult();
        return (int)count;
	}

	@Override
	public Optional<Match> getDataByMatchId(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " from Match where Id = ?";
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

}
