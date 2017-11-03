package com.jianma.fzkb.model;

public class ResultModel {

private int resultCode;
	
	private boolean success;
	private int error_code;
	
	private String message;
	private Object object;
	private String uptoken;
	
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getUptoken() {
		return uptoken;
	}
	public void setUptoken(String uptoken) {
		this.uptoken = uptoken;
	}
	
	
}
