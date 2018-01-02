package com.jianma.fzkb.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.jianma.fzkb.model.Designer;
import com.jianma.fzkb.model.ResponseData;
import com.jianma.fzkb.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public class TokenInterceptor extends HandlerInterceptorAdapter {

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception arg3) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model)
			throws Exception {
	}

	// 拦截每个请求
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		String token = request.getHeader("Authorization");
		ResponseData responseData = ResponseData.ok();
		
		if (null != token) {
			String loginId = request.getHeader("userId");
			try{
				Claims claims = JwtUtil.parseJWT(token);
				
				JSONObject jsonObject = JSONObject.parseObject(claims.getSubject());
				if (null != loginId && null != jsonObject) {
					if (Integer.parseInt(loginId) == jsonObject.getIntValue("userId")) {
						return true;
					} else {
						responseData = ResponseData.forbidden();
						responseMessage(response, response.getWriter(), responseData);
						return false;
					}
				} else {
					responseData = ResponseData.forbidden();
					responseMessage(response, response.getWriter(), responseData);
					return false;
				}
				
			}
			catch(ExpiredJwtException ex){
				responseData = ResponseData.authorizeOverTime();
				responseMessage(response, response.getWriter(), responseData);
				return false;
			}
			
		} else {
			responseData = ResponseData.forbidden();
			responseMessage(response, response.getWriter(), responseData);
			return false;
		}
	}

	// 请求不通过，返回错误信息给客户端
	private void responseMessage(HttpServletResponse response, PrintWriter out, ResponseData responseData) {
		
		response.setContentType("application/json; charset=utf-8");
		String json = JSONObject.toJSONString(responseData);
		out.print(json);
		out.flush();
		out.close();
	}

}
