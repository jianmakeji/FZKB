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
	
	/**
	 * 数据请求返回码
	 */
	public static final int RESCODE_SUCCESS = 1000;				//成功
	public static final int RESCODE_SUCCESS_MSG = 1001;			//成功(有返回信息)
	public static final int RESCODE_EXCEPTION = 1002;			//请求抛出异常
	public static final int RESCODE_NOLOGIN = 1003;				//未登陆状态
	public static final int RESCODE_NOEXIST = 1004;				//查询结果为空
	public static final int RESCODE_NOAUTH = 1005;				//无操作权限
	
	/**
	 * jwt
	 */
	public static final String JWT_ID = "jianma_jwt";
	public static final String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";
	public static final int JWT_TTL = 60*60*1000;  //millisecond
	public static final int JWT_REFRESH_INTERVAL = 55*60*1000;  //millisecond
	public static final int JWT_REFRESH_TTL = 10*365*24*60*60*1000;  //millisecond
}
