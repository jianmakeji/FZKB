package com.jianma.fzkb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianma.fzkb.exception.FZKBException;
import com.jianma.fzkb.model.ListResultModel;
import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.model.MaterialTableModel;
import com.jianma.fzkb.model.ResultModel;
import com.jianma.fzkb.service.MaterialService;
import com.jianma.fzkb.util.ResponseCodeUtil;
import com.jianma.fzkb.util.WebRequestUtil;

@Controller
@RequestMapping(value = "/material")
public class MaterialController {

	@Autowired
	@Qualifier(value = "materialServiceImpl")
	private MaterialService materialServiceImpl;
	
	private ResultModel resultModel = null;
	
	@ExceptionHandler(FZKBException.class)
	public @ResponseBody ResultModel handleCustomException(FZKBException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		resultModel.setSuccess(false);
		return resultModel;
	}
	
	@RequestMapping(value = "/createMaterial", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel createMaterial(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String name, @RequestParam String thumb, @RequestParam String masterImage) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		Material material = new Material();
		material.setName(name);
		material.setMasterImage(masterImage);
		material.setThumb(thumb);
		material.setCreateTime(new Date());
		
		int result = materialServiceImpl.createMaterial(material);
		if (result == ResponseCodeUtil.DB_OPERATION_SUCCESS) {
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			return resultModel;
		} else {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/updateMaterial", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel updateMaterial(HttpServletRequest request, HttpServletResponse response, @RequestParam int id,
			@RequestParam String name, @RequestParam String thumb, @RequestParam String masterImage) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		Material material = new Material();
		material.setCreateTime(new Date());
		material.setName(name);
		material.setMasterImage(masterImage);
		material.setThumb(thumb);
		material.setId(id);
		
		int result = materialServiceImpl.updateMaterial(material);
		if (result == ResponseCodeUtil.DB_OPERATION_SUCCESS) {
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			return resultModel;
		} else {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/deleteMaterial/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResultModel deleteMaterial(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		int result = materialServiceImpl.deleteMaterial(id);
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
			
			MaterialTableModel brandTableModel = materialServiceImpl.getMaterialByPage(iDisplayStart, iDisplayLength);
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
	
	@RequestMapping(value = "/getRandomMaterial", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel getRandomMaterial(HttpServletRequest request, HttpServletResponse response, @RequestParam int count) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		try {
			List<Material> list = materialServiceImpl.getMaterialByCount(count);
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			resultModel.setObject(list);
			return resultModel;
		}catch (Exception e) {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/getMaterial/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultModel getMaterialById(HttpServletRequest request, HttpServletResponse response,@PathVariable int id) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		resultModel = new ResultModel();
		try {
			Material material = materialServiceImpl.getDataByMaterialId(id).get();
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			resultModel.setObject(material);
			return resultModel;
		}catch (Exception e) {
			throw new FZKBException(500, "操作失败！");
		}
	}
	
	@RequestMapping(value = "/getDataPageByTag", method = RequestMethod.GET)
	@ResponseBody
	public ListResultModel getDataPageByTag(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int iDisplayLength, @RequestParam int iDisplayStart, @RequestParam String sEcho,
			@RequestParam String category, @RequestParam String style1, @RequestParam String style2, @RequestParam String style3) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ListResultModel listResultModel = new ListResultModel();
		try {
			Map<String,String> map = new HashMap<>();
			map.put("category", category);
			map.put("style1", style1);
			map.put("style2", style2);
			map.put("style3", style3);
			MaterialTableModel brandTableModel = materialServiceImpl.getMaterialPageByCondition(iDisplayStart, iDisplayLength, map);
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
}
