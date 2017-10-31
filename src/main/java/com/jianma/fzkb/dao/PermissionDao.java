package com.jianma.fzkb.dao;

import com.jianma.fzkb.model.Permission;

public interface PermissionDao {

	 public void createPermission(Permission permission);

	 public void deletePermission(Long permissionId);
	    
}
