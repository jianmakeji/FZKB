package com.jianma.fzkb.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Match generated by hbm2java
 */
@Entity
@Table(name = "match", catalog = "sdx_fzkb")
public class Match implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int userId;
	private String name;
	private int uwId;
	private String underwear;
	private int gcId;
	private String greatcoat;
	private int trId;
	private String trousers;
	private Date createTime;
	
	private String username;

	public Match() {
	}

	public Match(int userId, String name, String underwear, String greatcoat, String trousers, Date createTime) {
		this.userId = userId;
		this.name = name;
		this.underwear = underwear;
		this.greatcoat = greatcoat;
		this.trousers = trousers;
		this.createTime = createTime;
	}
	
	public Match(int id,int userId, String name, String underwear, String greatcoat, String trousers, Date createTime,String username) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.underwear = underwear;
		this.greatcoat = greatcoat;
		this.trousers = trousers;
		this.createTime = createTime;
		this.username = username;
	}

	public Match(int userId, String name, String underwear, String greatcoat, String trousers, int uwId, int gcId, int trId) {
		this.userId = userId;
		this.name = name;
		this.underwear = underwear;
		this.greatcoat = greatcoat;
		this.trousers = trousers;
		this.uwId = uwId;
		this.gcId = gcId;
		this.trId = trId;
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

	@Column(name = "userId", nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "name", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "underwear", nullable = false)
	public String getUnderwear() {
		return this.underwear;
	}

	public void setUnderwear(String underwear) {
		this.underwear = underwear;
	}

	@Column(name = "greatcoat", nullable = false)
	public String getGreatcoat() {
		return this.greatcoat;
	}

	public void setGreatcoat(String greatcoat) {
		this.greatcoat = greatcoat;
	}

	@Column(name = "trousers", nullable = false)
	public String getTrousers() {
		return this.trousers;
	}

	public void setTrousers(String trousers) {
		this.trousers = trousers;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "uwId", nullable = false)
	public int getUwId() {
		return uwId;
	}

	public void setUwId(int uwId) {
		this.uwId = uwId;
	}

	@Column(name = "gcId", nullable = false)
	public int getGcId() {
		return gcId;
	}

	public void setGcId(int gcId) {
		this.gcId = gcId;
	}

	public int getTrId() {
		return trId;
	}

	@Column(name = "trId", nullable = false)
	public void setTrId(int trId) {
		this.trId = trId;
	}

	@Transient
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
