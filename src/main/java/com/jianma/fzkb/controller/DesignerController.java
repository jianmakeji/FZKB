package com.jianma.fzkb.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianma.fzkb.exception.FZKBException;
import com.jianma.fzkb.model.Designer;
import com.jianma.fzkb.model.DesignerTableModel;
import com.jianma.fzkb.model.ListResultModel;
import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.MatchTableModel;
import com.jianma.fzkb.model.ResultModel;
import com.jianma.fzkb.realm.DesignerRealm;
import com.jianma.fzkb.service.DesignerService;
import com.jianma.fzkb.service.MatchService;
import com.jianma.fzkb.util.ResponseCodeUtil;
import com.jianma.fzkb.util.WebRequestUtil;

@Controller
@RequestMapping(value = "/designer")
public class DesignerController {

	@Autowired
	@Qualifier(value = "designerServiceImpl")
	private DesignerService designerServiceImpl;
	
	private ResultModel resultModel = null;
	
	@ExceptionHandler(FZKBException.class)
	public @ResponseBody ResultModel handleCustomException(FZKBException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		resultModel.setSuccess(false);
		return resultModel;
	}
	
	@RequestMapping(value = "/createDesigner", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel createDesigner(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String name, @RequestParam String underwear, @RequestParam String greatcoat, @RequestParam String trousers) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		Designer designer = new Designer();
		
		
		int result = designerServiceImpl.createDesigner(designer);
		if (result == ResponseCodeUtil.DB_OPERATION_SUCCESS) {
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			return resultModel;
		} else {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/authorityCheck", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel authorityCheck(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String username, @RequestParam String password) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		String msg = "";
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			if (subject.isAuthenticated()) {
				msg = "鐧诲綍鎴愬姛锛�";
				DefaultWebSecurityManager dw = (DefaultWebSecurityManager)SecurityUtils.getSecurityManager();
				for (Realm realm : dw.getRealms()){
					resultModel.setObject(((DesignerRealm)realm).getSuccessDesigner());
				}
				resultModel.setResultCode(200);
				resultModel.setSuccess(true);
			} else {
				resultModel.setResultCode(500);
				resultModel.setSuccess(false);
			}
		}catch (IncorrectCredentialsException e) {
			msg = "登录密码错误.";
		} catch (ExcessiveAttemptsException e) {
			msg = "登录失败次数过多";
		} catch (LockedAccountException e) {
			msg = "帐号已被锁定.";
		} catch (DisabledAccountException e) {
			msg = "帐号已被禁用. ";
		} catch (ExpiredCredentialsException e) {
			msg = "帐号已过期.";
		} catch (UnknownAccountException e) {
			msg = "帐号不存在.或者未激活";
		} catch (UnauthorizedException e) {
			msg = "您没有得到相应的授权！";
		}
		resultModel.setMessage(msg);
		return resultModel;
	}
	
	@RequestMapping(value = "/updateDesigner", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel updateDesigner(HttpServletRequest request, HttpServletResponse response, @RequestParam int id,
			@RequestParam String name, @RequestParam String underwear, @RequestParam String greatcoat, @RequestParam String trousers) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		Designer designer = new Designer();
		designer.setCreateTime(new Date());
		
		
		int result = designerServiceImpl.updateDesigner(designer);
		if (result == ResponseCodeUtil.DB_OPERATION_SUCCESS) {
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			return resultModel;
		} else {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/deleteDesigner", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel deleteDesigner(HttpServletRequest request, HttpServletResponse response, @RequestParam int id) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		int result = designerServiceImpl.deleteDesigner(id);
		if (result == ResponseCodeUtil.DB_OPERATION_SUCCESS) {
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			return resultModel;
		} else {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/getDataByPage", method = RequestMethod.GET)
	@ResponseBody
	public ListResultModel getDataByPage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int iDisplayLength, @RequestParam int iDisplayStart, @RequestParam String sEcho) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ListResultModel listResultModel = new ListResultModel();
		try {
			
			DesignerTableModel designerTableModel = designerServiceImpl.getDesignerByPage(iDisplayStart, iDisplayLength);
			listResultModel.setAaData(designerTableModel.getList());
			listResultModel.setsEcho(sEcho);
			listResultModel.setiTotalRecords((int) designerTableModel.getCount());
			listResultModel.setiTotalDisplayRecords((int) designerTableModel.getCount());
			listResultModel.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}
}
