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

import com.jianma.fzkb.dao.DesignerDao;
import com.jianma.fzkb.model.Designer;


@Repository
@Component
@Qualifier(value = "designerDaoImpl")
public class DesignerDaoImpl implements DesignerDao {

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
	public void createDesigner(Designer designer) {
		sessionFactory.getCurrentSession().save(designer);
	}

	@Override
	public void updateDesigner(Designer designer) {
		sessionFactory.getCurrentSession().update(designer);
	}

	@Override
	public void deleteDesigner(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " delete from Designer d where d.id = ? ";
		Query query = session.createQuery(hql);
        query.setParameter(0, id); 
		query.executeUpdate();
	}

	@Override
	public Optional<Designer> getDesignerById(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " from Designer where id = ?";
		Query query = session.createQuery(hql);
        query.setParameter(0, id); 
        @SuppressWarnings("unchecked")
		List<Designer> list = query.list();
        if (list.size() > 0){
        	Optional<Designer> designer = Optional.ofNullable(list.get(0));
     		return designer;
        }
        else{
        	return Optional.empty();
        }
	}

	@Override
	public List<Designer> getDesignerByPage(int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		String hql  = " from Designer order by createtime desc";
		Query query = session.createQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.list();
	}

	@Override
	public int getCountDesigner() {
		Session session = this.getSessionFactory().getCurrentSession();
		final String hql = " select count(d) from Designer d"; 
        final Query query = session.createQuery(hql); 
        long count = (Long)query.uniqueResult();
        return (int)count;
	}

	@Override
	public List<Designer> getDesignerByRealname(String realname, int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		String hql  = " from Designer where realname like ? order by createtime desc";
		Query query = session.createQuery(hql);
		 query.setParameter(0, "%"+realname+"%");
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.list();
	}

	@Override
	public int getCountDesignerByRealname(String realname) {
		Session session = this.getSessionFactory().getCurrentSession();
		final String hql = " select count(d) from Designer d where realname like ? "; 
        final Query query = session.createQuery(hql); 
        query.setParameter(0, "%"+realname+"%");
        long count = (Long)query.uniqueResult();
        return (int)count;
	}

	@Override
	public Optional<Designer> authorityCheck(String username, String password) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " from Designer where username = ? and password = ? ";
		Query query = session.createQuery(hql);
        query.setParameter(0, username); 
        query.setParameter(1, password);
        @SuppressWarnings("unchecked")
        List<Designer> list = query.list();   
        if (list.size() > 0){
        	Optional<Designer> user = Optional.ofNullable(list.get(0));
     		return user;
        }
        else{
        	return Optional.empty();
        }
	}

	@Override
	public Optional<Designer> findDesignerByUsername(String username) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " from Designer where username = ?";
		Query query = session.createQuery(hql);
        query.setParameter(0, username); 
        @SuppressWarnings("unchecked")
		List<Designer> list = query.list();
        if (list.size() > 0){
        	Optional<Designer> designer = Optional.ofNullable(list.get(0));
     		return designer;
        }
        else{
        	return Optional.empty();
        }
	}

}
