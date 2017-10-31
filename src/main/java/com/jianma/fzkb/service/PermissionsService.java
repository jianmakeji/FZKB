package com.jianma.fzkb.service;

import com.jianma.fzkb.model.Permission;

public interface PermissionsService {

	public int createPermission(Permission permission);

	public int deletePermission(Long permissionId);
}
