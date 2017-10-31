package com.jianma.fzkb.util;

public class ResponseCodeUtil {
	
	public static final int DB_OPERATION_SUCCESS = 1;
	public static final int DB_OPERATION_FAILURE = 0;

	public static final int PERMISSION_OPERATION_SUCESS = 1;
	public static final int PERMISSION_OPERATION_FAILURE = 0;

	public static final int USER_FINDPWD_SUCESS = 1;
	public static final int USER_FINDPWD_LINK_OUT_TIME = 2; //链接已经过期
	public static final int USER_FINDPWD_LINK_VALID_ERROR = 3;//链接加密密码不正�?
	
	public static final int UESR_CREATE_EXIST = 2;
	public static final int UESR_OPERATION_SUCESS = 1;
	public static final int UESR_OPERATION_FAILURE = 0;
	public static final int UESR_OPERATION_USER_IS_NOT_EXISTS = 0;
}
