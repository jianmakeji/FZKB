package com.jianma.fzkb.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianma.fzkb.exception.FZKBException;
import com.jianma.fzkb.model.ListResultModel;
import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.MatchTableModel;
import com.jianma.fzkb.model.ResultModel;
import com.jianma.fzkb.service.MatchService;
import com.jianma.fzkb.util.ResponseCodeUtil;

@Controller
@RequestMapping(value = "/match")
public class MatchController {

	@Autowired
	@Qualifier(value = "matchServiceImpl")
	private MatchService matchServiceImpl;
	
	private ResultModel resultModel = null;
	
	@ExceptionHandler(FZKBException.class)
	public @ResponseBody ResultModel handleCustomException(FZKBException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		resultModel.setSuccess(false);
		return resultModel;
	}
	
	@RequestMapping(value = "/createMatch", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel createMatch(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String name, @RequestParam String icon, @RequestParam String description) {
		
		resultModel = new ResultModel();
		Match match = new Match();
		match.setCreateTime(new Date());
		match.setName(name);
		
		
		int result = matchServiceImpl.createMatch(match);
		if (result == ResponseCodeUtil.DB_OPERATION_SUCCESS) {
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			return resultModel;
		} else {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/updateMatch", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel updateMatch(HttpServletRequest request, HttpServletResponse response, @RequestParam int id,
			@RequestParam String name, @RequestParam String icon, @RequestParam String description) {
		
		resultModel = new ResultModel();
		Match match = new Match();
		match.setCreateTime(new Date());
		match.setName(name);
		match.setId(id);
		
		int result = matchServiceImpl.updateMatch(match);
		if (result == ResponseCodeUtil.DB_OPERATION_SUCCESS) {
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			return resultModel;
		} else {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/deleteMatch", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel deleteMatch(HttpServletRequest request, HttpServletResponse response, @RequestParam int id) {
		
		resultModel = new ResultModel();
		int result = matchServiceImpl.deleteMatch(id);
		if (result == ResponseCodeUtil.DB_OPERATION_SUCCESS) {
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			return resultModel;
		} else {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/getDataByCondition", method = RequestMethod.GET)
	@ResponseBody
	public ListResultModel getDataByCondition(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int iDisplayLength, @RequestParam int iDisplayStart, @RequestParam String sEcho) {
		
		ListResultModel listResultModel = new ListResultModel();
		try {
			
			MatchTableModel brandTableModel = matchServiceImpl.getMatchByPage(iDisplayStart, iDisplayLength);
			listResultModel.setAaData(brandTableModel.getList());
			listResultModel.setsEcho(sEcho);
			listResultModel.setiTotalRecords((int) brandTableModel.getCount());
			listResultModel.setiTotalDisplayRecords((int) brandTableModel.getCount());
			listResultModel.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}
	

	
	@RequestMapping(value = "/getBrandById", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel getBrandById(HttpServletRequest request, HttpServletResponse response,@RequestParam int id) {
		
		resultModel = new ResultModel();
		try {
			Match match = matchServiceImpl.getDataByMatchId(id).get();
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			resultModel.setObject(match);
			return resultModel;
		}catch (Exception e) {
			throw new FZKBException(500, "操作失败！");
		}
	}
}
