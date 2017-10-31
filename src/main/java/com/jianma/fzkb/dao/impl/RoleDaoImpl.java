package com.jianma.fzkb.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jianma.fzkb.dao.RoleDao;
import com.jianma.fzkb.model.Role;


@Repository
@Component
@Qualifier(value = "roleDaoImpl")
public class RoleDaoImpl implements RoleDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public void createRole(Role role) {
		Session session = sessionFactory.getCurrentSession();
		session.save(role);
	}

	@Override
	public void deleteRole(Long roleId) {


	}

	@Override
	public void correlationPermissions(Long roleId, Long... permissionIds) {


	}

	@Override
	public void uncorrelationPermissions(Long roleId, Long... permissionIds) {


	}

}
