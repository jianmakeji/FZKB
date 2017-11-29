package com.jianma.fzkb.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "role", catalog = "sdx_fzkb")
public class Role implements java.io.Serializable {

	private Integer id;
	private String rolename;
	private Date createtime;
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	private Set<PermissionRole> permissionRoles = new HashSet<PermissionRole>(0);

	public Role() {
	}

	public Role(String rolename) {
		this.rolename = rolename;
	}

	public Role(String rolename, Date createtime, Set<UserRole> userRoles, Set<PermissionRole> permissionRoles) {
		this.rolename = rolename;
		this.createtime = createtime;
		this.userRoles = userRoles;
		this.permissionRoles = permissionRoles;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "rolename", nullable = false, length = 10)
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", length = 19)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<PermissionRole> getPermissionRoles() {
		return this.permissionRoles;
	}

	public void setPermissionRoles(Set<PermissionRole> permissionRoles) {
		this.permissionRoles = permissionRoles;
	}

}
