package com.jianma.fzkb.model;

import java.util.HashMap;
import java.util.Map;

public class ResponseData {
	
	private final String message;
    private final int code;
    private final Map<String, Object> data = new HashMap<String, Object>();
    
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public ResponseData putDataValue(String key, Object value) {
        data.put(key, value);
        return this;
    }

    private ResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseData ok() {
        return new ResponseData(200, "Ok");
    }

    public static ResponseData notFound() {
        return new ResponseData(404, "Not Found");
    }

    public static ResponseData badRequest() {
        return new ResponseData(400, "Bad Request");
    }

    public static ResponseData forbidden() {
        return new ResponseData(403, "禁止访问");
    }

    public static ResponseData unauthorized() {
        return new ResponseData(401, "没有进行身份认证，请登录");
    }

    public static ResponseData authorizeOverTime() {
        return new ResponseData(405, "身份认证超时，请重新登录");
    }
    
    public static ResponseData serverInternalError() {
        return new ResponseData(500, "服务器内部错误");
    }

    public static ResponseData customerError() {
        return new ResponseData(1001, "customer Error");
    }
}
