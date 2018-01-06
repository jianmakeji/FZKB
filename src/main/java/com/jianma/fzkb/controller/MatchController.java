package com.jianma.fzkb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.jianma.fzkb.model.ListResultModel;
import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.MatchTableModel;
import com.jianma.fzkb.model.ResultModel;
import com.jianma.fzkb.service.MatchService;
import com.jianma.fzkb.util.ResponseCodeUtil;
import com.jianma.fzkb.util.WebRequestUtil;

@Controller
@RequestMapping(value = "/match")
public class MatchController{

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
	public ResultModel createMatch(HttpServletRequest request, HttpServletResponse response,@RequestBody Match match) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		match.setCreateTime(new Date());
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
	public ResultModel updateMatch(HttpServletRequest request, HttpServletResponse response, @RequestBody Match match) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		match.setCreateTime(new Date());
		
		int result = matchServiceImpl.updateMatch(match);
		if (result == ResponseCodeUtil.DB_OPERATION_SUCCESS) {
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			return resultModel;
		} else {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/deleteMatch/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResultModel deleteMatch(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
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
	
	@RequestMapping(value = "/getDataByPage", method = RequestMethod.GET)
	@ResponseBody
	public ListResultModel getDataByPage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int limit, @RequestParam int offset,@RequestParam int userId) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ListResultModel listResultModel = new ListResultModel();
		try {
			MatchTableModel brandTableModel = matchServiceImpl.getMatchPageByUserId(offset, limit, userId);
			listResultModel.setAaData(brandTableModel.getList());
			listResultModel.setiTotalRecords((int) brandTableModel.getCount());
			listResultModel.setiTotalDisplayRecords((int) brandTableModel.getCount());
			listResultModel.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}
	
	@RequestMapping(value = "/getDataPageByTag", method = RequestMethod.GET)
	@ResponseBody
	public ListResultModel getDataPageByTag(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int limit, @RequestParam int offset, @RequestParam String category, 
			@RequestParam String style1, @RequestParam String style2, @RequestParam String style3) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ListResultModel listResultModel = new ListResultModel();
		try {
			Map<String,String> map = new HashMap<>();
			map.put("category", category);
			map.put("style1", style1);
			map.put("style2", style2);
			map.put("style3", style3);
			MatchTableModel matchTableModel = matchServiceImpl.getMatchPageByCondition(offset, limit, map);
			listResultModel.setAaData(matchTableModel.getList());
			listResultModel.setiTotalRecords((int) matchTableModel.getCount());
			listResultModel.setiTotalDisplayRecords((int) matchTableModel.getCount());
			listResultModel.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}
	
	@RequestMapping(value = "/getMatch/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultModel getBrandById(HttpServletRequest request, HttpServletResponse response,@PathVariable int id) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
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
	
	@RequestMapping(value = "/getDataBySearchKeyword", method = RequestMethod.GET)
	@ResponseBody
	public ListResultModel getDataBySearchKeyword(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int limit, @RequestParam int offset,@RequestParam int userId,@RequestParam String keyword) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ListResultModel listResultModel = new ListResultModel();
		try {
			
			MatchTableModel brandTableModel = matchServiceImpl.getMatchBySearchKeyword(offset, limit, userId, keyword);
			listResultModel.setAaData(brandTableModel.getList());
			listResultModel.setiTotalRecords((int) brandTableModel.getCount());
			listResultModel.setiTotalDisplayRecords((int) brandTableModel.getCount());
			listResultModel.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}
}
