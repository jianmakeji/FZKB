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

import com.jianma.fzkb.dao.CorporationDao;
import com.jianma.fzkb.model.Corporation;
import com.jianma.fzkb.model.Designer;

@Repository
@Component
@Qualifier(value = "corporationDaoImpl")
public class CorporationDaoImpl implements CorporationDao {

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
	public void createCorporation(Corporation corporation) {
		sessionFactory.getCurrentSession().save(corporation);
	}

	@Override
	public void updateCorporation(Corporation corporation) {
		sessionFactory.getCurrentSession().update(corporation);
	}

	@Override
	public void deleteCorporation(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " delete from Corporation c where c.id = ? ";
		Query query = session.createQuery(hql);
        query.setParameter(0, id); 
		query.executeUpdate();
	}

	@Override
	public Optional<Corporation> getCorporationById(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " from Corporation where id = ?";
		Query query = session.createQuery(hql);
        query.setParameter(0, id); 
        @SuppressWarnings("unchecked")
		List<Corporation> list = query.list();
        if (list.size() > 0){
        	Optional<Corporation> designer = Optional.ofNullable(list.get(0));
     		return designer;
        }
        else{
        	return Optional.empty();
        }
	}


	@Override
	public List<Corporation> getCorporationByPage(int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		String hql  = " from Corporation ";
		Query query = session.createQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.list();
	}

	@Override
	public int getCountCorporation() {
		Session session = this.getSessionFactory().getCurrentSession();
		final String hql = " select count(c) from Corporation c "; 
        final Query query = session.createQuery(hql); 
        long count = (Long)query.uniqueResult();
        return (int)count;
	}

	@Override
	public List<Corporation> getCorporationByName(String name, int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		String hql  = " from Corporation where name like ? ";
		Query query = session.createQuery(hql);
		query.setParameter(0, "%"+name+"%");
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.list();
	}

	@Override
	public int getCountCorporationByName(String name) {
		Session session = this.getSessionFactory().getCurrentSession();
		final String hql = " select count(c) from Corporation c where name like ? "; 
        final Query query = session.createQuery(hql); 
        query.setParameter(0, "%"+name+"%");
        long count = (Long)query.uniqueResult();
        return (int)count;
	}

}
