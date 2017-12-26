package com.jianma.fzkb.util;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

public class SessionManager extends DefaultWebSessionManager {
	private String authorization = "Authorization";  
	  
    /** 
     * 重写获取sessionId的方法调用当前Manager的获取方法 
     * 
     * @param request 
     * @param response 
     * @return 
     */  
    @Override  
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {  
        return this.getReferencedSessionId(request, response);  
    }  
  
    /** 
     * 获取sessionId从请求中 
     * 
     * @param request 
     * @param response 
     * @return 
     */  
    private Serializable getReferencedSessionId(ServletRequest request, ServletResponse response) {  
        String id = this.getSessionIdCookieValue(request, response);  
        if (id != null) {  
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "cookie");  
        } else {  
            id = this.getUriPathSegmentParamValue(request, "JSESSIONID");  
            if (id == null) {  
                // 获取请求头中的session  
                id = WebUtils.toHttp(request).getHeader(this.authorization);  
                if (id == null) {  
                    String name = this.getSessionIdName();  
                    id = request.getParameter(name);  
                    if (id == null) {  
                        id = request.getParameter(name.toLowerCase());  
                    }  
                }  
            }  
            if (id != null) {  
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "url");  
            }  
        }  
  
        if (id != null) {  
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);  
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);  
        }  
  
        return id;  
    }  
  
    // copy super  
    private String getSessionIdCookieValue(ServletRequest request, ServletResponse response) {  
        if (!this.isSessionIdCookieEnabled()) {   
            return null;  
        } else if (!(request instanceof HttpServletRequest)) {   
            return null;  
        } else {  
            HttpServletRequest httpRequest = (HttpServletRequest) request;  
            return this.getSessionIdCookie().readValue(httpRequest, WebUtils.toHttp(response));  
        }  
    }  
  
    // copy super  
    private String getUriPathSegmentParamValue(ServletRequest servletRequest, String paramName) {  
        if (!(servletRequest instanceof HttpServletRequest)) {  
            return null;  
        } else {  
            HttpServletRequest request = (HttpServletRequest) servletRequest;  
            String uri = request.getRequestURI();  
            if (uri == null) {  
                return null;  
            } else {  
                int queryStartIndex = uri.indexOf(63);  
                if (queryStartIndex >= 0) {  
                    uri = uri.substring(0, queryStartIndex);  
                }  
  
                int index = uri.indexOf(59);  
                if (index < 0) {  
                    return null;  
                } else {  
                    String TOKEN = paramName + "=";  
                    uri = uri.substring(index + 1);  
                    index = uri.lastIndexOf(TOKEN);  
                    if (index < 0) {  
                        return null;  
                    } else {  
                        uri = uri.substring(index + TOKEN.length());  
                        index = uri.indexOf(59);  
                        if (index >= 0) {  
                            uri = uri.substring(0, index);  
                        }  
  
                        return uri;  
                    }  
                }  
            }  
        }  
    }  
  
    // copy super  
    private String getSessionIdName() {  
        String name = this.getSessionIdCookie() != null ? this.getSessionIdCookie().getName() : null;  
        if (name == null) {  
            name = "JSESSIONID";  
        }  
  
        return name;  
    }  
}
