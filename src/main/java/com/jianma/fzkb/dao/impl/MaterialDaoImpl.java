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

import com.jianma.fzkb.dao.MaterialDao;
import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.model.MaterialTableModel;


@Repository
@Component
@Qualifier(value = "materialDaoImpl")
public class MaterialDaoImpl implements MaterialDao {

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
	public int createMaterial(Material material) {
		return (int)sessionFactory.getCurrentSession().save(material);
	}

	@Override
	public void updateMaterial(Material material) {
		sessionFactory.getCurrentSession().update(material);
	}

	@Override
	public void deleteMaterial(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " delete from Material m where m.id = ? ";
		Query query = session.createQuery(hql);
        query.setParameter(0, id); 
		query.executeUpdate();
	}

	@Override
	public List<Material> getMaterialByPage(int offset, int limit) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " from Material";
		final Query query = session.createQuery(hql); 
        query.setFirstResult(offset);    
        query.setMaxResults(limit); 
        @SuppressWarnings("unchecked")
		final List<Material> list = query.list(); 
		return list;
	}

	@Override
	public int getCountMaterial() {
		Session session = this.getSessionFactory().getCurrentSession();
		final String hql = " select count(m) from Material m"; 
        final Query query = session.createQuery(hql); 
        long count = (Long)query.uniqueResult();
        return (int)count;
	}

	@Override
	public Optional<Material> getDataByMaterialId(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " from Material where Id = ?";
		Query query = session.createQuery(hql);
        query.setParameter(0, id); 
        @SuppressWarnings("unchecked")
		List<Material> list = query.list();
        if (list.size() > 0){
        	Optional<Material> material = Optional.ofNullable(list.get(0));
     		return material;
        }
        else{
        	return Optional.empty();
        }
	}

	@Override
	public List<Material> getAllMaterial() {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " from Material";
		final Query query = session.createQuery(hql);  
        @SuppressWarnings("unchecked")
		final List<Material> list = query.list(); 
		return list;
	}

	@Override
	public List<Material> getMaterialPageByNumber(int offset, int limit, String number) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " from Material where number like ? ";
		final Query query = session.createQuery(hql);
		query.setParameter(0, "%" + number + "%");
        query.setFirstResult(offset);    
        query.setMaxResults(limit); 
        @SuppressWarnings("unchecked")
		final List<Material> list = query.list(); 
		return list;
	}

	@Override
	public int getMaterialCountByNumber(String number) {
		Session session = this.getSessionFactory().getCurrentSession();
		final String hql = " select count(m) from Material m where number like ? "; 
        final Query query = session.createQuery(hql);
        query.setParameter(0, "%" + number + "%");
        long count = (Long)query.uniqueResult();
        return (int)count;
	}

	@Override
	public List<Material> getMaterialPageByUserId(int offset, int limit, int userId) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = " from Material where userId = ? ";
		final Query query = session.createQuery(hql);
		query.setParameter(0, userId);
        query.setFirstResult(offset);    
        query.setMaxResults(limit); 
        @SuppressWarnings("unchecked")
		final List<Material> list = query.list(); 
		return list;
	}

	@Override
	public int getMaterialCountByUserId(int userId) {
		Session session = this.getSessionFactory().getCurrentSession();
		final String hql = " select count(m) from Material m where userId = ? "; 
        final Query query = session.createQuery(hql);
        query.setParameter(0, userId);
        long count = (Long)query.uniqueResult();
        return (int)count;
	}

}
