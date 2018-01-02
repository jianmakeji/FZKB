package com.jianma.fzkb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jianma.fzkb.util.WebRequestUtil;

import net.sf.json.JSONObject;

public abstract class BaseController {
	/**
	 * 登录认证异常
	 */
	public String authenticationException(HttpServletRequest request, HttpServletResponse response) {
		// 输出JSON
		Map<String, Object> map = new HashMap<>();
		map.put("code", "-999");
		map.put("message", "未登录或会话超时！");
		writeJson(map, response);
		return null;
	}

	/**
	 * 权限异常
	 */
	public String authorizationException(HttpServletRequest request, HttpServletResponse response) {

		// 输出JSON
		Map<String, Object> map = new HashMap<>();
		map.put("code", "-998");
		map.put("message", "无权限");
		writeJson(map, response);
		return null;

	}

	/**
	 * 输出JSON
	 *
	 * @param response
	 * @author SHANHY
	 * @create 2017年4月4日
	 */
	private void writeJson(Map<String, Object> map, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			out = response.getWriter();
			JSONObject jsonObject = JSONObject.fromObject(map);
			out.write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
