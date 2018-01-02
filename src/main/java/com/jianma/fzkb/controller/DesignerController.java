package com.jianma.fzkb.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianma.fzkb.exception.FZKBException;
import com.jianma.fzkb.model.Designer;
import com.jianma.fzkb.model.DesignerTableModel;
import com.jianma.fzkb.model.ListResultModel;
import com.jianma.fzkb.model.ResultModel;
import com.jianma.fzkb.service.DesignerService;
import com.jianma.fzkb.util.ResponseCodeUtil;
import com.jianma.fzkb.util.WebRequestUtil;

@Controller
@RequestMapping(value = "/designer")
public class DesignerController{

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
	public ResultModel createDesigner(HttpServletRequest request, HttpServletResponse response,@RequestBody Designer designer) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		
		resultModel = new ResultModel();
		designer.setCreateTime(new Date());
		
		int result = designerServiceImpl.createDesigner(designer);
		if (result == ResponseCodeUtil.DB_OPERATION_SUCCESS) {
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			return resultModel;
		} else {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/updateDesigner", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel updateDesigner(HttpServletRequest request, HttpServletResponse response, @RequestBody Designer designer) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
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

	@RequestMapping(value = "/deleteDesigner/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResultModel deleteDesigner(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
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
			@RequestParam int limit, @RequestParam int offset) {

		WebRequestUtil.AccrossAreaRequestSet(request, response);
		
		ListResultModel listResultModel = new ListResultModel();
		try {
			
			DesignerTableModel designerTableModel = designerServiceImpl.getDesignerByPage(offset, limit);
			listResultModel.setAaData(designerTableModel.getList());
			listResultModel.setiTotalRecords((int) designerTableModel.getCount());
			listResultModel.setiTotalDisplayRecords((int) designerTableModel.getCount());
			listResultModel.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}

	@RequestMapping(value = "/getDesignerByRealname", method = RequestMethod.GET)
	@ResponseBody
	public ListResultModel getDesignerByRealname(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int limit, @RequestParam int offset,@RequestParam String realname) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ListResultModel listResultModel = new ListResultModel();
		try {
			
			DesignerTableModel designerTableModel = designerServiceImpl.getDesignerByRealname(realname,offset, limit);
			listResultModel.setAaData(designerTableModel.getList());
			listResultModel.setiTotalRecords((int) designerTableModel.getCount());
			listResultModel.setiTotalDisplayRecords((int) designerTableModel.getCount());
			listResultModel.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}
	
	@RequestMapping(value = "/getDesigner/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultModel getDesignerByRealname(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int id) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		try {
			Designer designer = designerServiceImpl.getDesignerById(id).get();
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			resultModel.setObject(designer);
			return resultModel;
		}catch (Exception e) {
			e.printStackTrace();
			throw new FZKBException(500, "操作失败！");
		}	
		
	}
}
