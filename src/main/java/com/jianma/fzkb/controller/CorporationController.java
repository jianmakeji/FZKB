package com.jianma.fzkb.controller;

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
import com.jianma.fzkb.model.Corporation;
import com.jianma.fzkb.model.CorporationTableModel;
import com.jianma.fzkb.model.ListResultModel;
import com.jianma.fzkb.model.ResultModel;
import com.jianma.fzkb.service.CorporationService;
import com.jianma.fzkb.util.ResponseCodeUtil;
import com.jianma.fzkb.util.WebRequestUtil;

@Controller
@RequestMapping(value = "/corporation")
public class CorporationController {

	@Autowired
	@Qualifier(value = "corporationServiceImpl")
	private CorporationService corporationServiceImpl;
	
	private ResultModel resultModel = null;
	
	@ExceptionHandler(FZKBException.class)
	public @ResponseBody ResultModel handleCustomException(FZKBException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		resultModel.setSuccess(false);
		return resultModel;
	}
	
	@RequestMapping(value = "/createCorporation", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel createCorporation(HttpServletRequest request, HttpServletResponse response,@RequestBody Corporation corporation) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		
		resultModel = new ResultModel();
		
		int result = corporationServiceImpl.createCorporation(corporation);
		if (result == ResponseCodeUtil.DB_OPERATION_SUCCESS) {
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			return resultModel;
		} else {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/updateCorporation", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel updateCorporation(HttpServletRequest request, HttpServletResponse response, @RequestBody Corporation corporation) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		
		int result = corporationServiceImpl.updateCorporation(corporation);
		if (result == ResponseCodeUtil.DB_OPERATION_SUCCESS) {
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			return resultModel;
		} else {
			throw new FZKBException(500, "操作失败！");
		}
	}

	@RequestMapping(value = "/deleteCorporation/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResultModel deleteCorporation(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		int result = corporationServiceImpl.deleteCorporation(id);
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
			
			CorporationTableModel corporationTableModel = corporationServiceImpl.getCorporationByPage(offset, limit);
			listResultModel.setAaData(corporationTableModel.getList());
			listResultModel.setiTotalRecords((int) corporationTableModel.getCount());
			listResultModel.setiTotalDisplayRecords((int) corporationTableModel.getCount());
			listResultModel.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}

	@RequestMapping(value = "/getCorporationByName", method = RequestMethod.GET)
	@ResponseBody
	public ListResultModel getCorporationByName(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int limit, @RequestParam int offset,@RequestParam String name) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ListResultModel listResultModel = new ListResultModel();
		try {
			
			CorporationTableModel corporationTableModel = corporationServiceImpl.getCorporationByName(name,offset, limit);
			listResultModel.setAaData(corporationTableModel.getList());
			listResultModel.setiTotalRecords((int) corporationTableModel.getCount());
			listResultModel.setiTotalDisplayRecords((int) corporationTableModel.getCount());
			listResultModel.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}
	
	@RequestMapping(value = "/getCorporation/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultModel getCorporationById(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int id) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		try {
			Corporation corporation = corporationServiceImpl.getCorporationById(id).get();
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			resultModel.setObject(corporation);
			return resultModel;
		}catch (Exception e) {
			e.printStackTrace();
			throw new FZKBException(500, "操作失败！");
		}	
		
	}
}
