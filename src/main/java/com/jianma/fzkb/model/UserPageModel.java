package com.jianma.fzkb.model;

import java.util.List;

public class UserPageModel {

	public List<User> list;
	public int count;
	
	public List<User> getList() {
		return list;
	}
	public void setList(List<User> list) {
		this.list = list;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
