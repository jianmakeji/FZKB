package com.jianma.fzkb;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.jianma.fzkb.exception.FZKBException;
import com.jianma.fzkb.model.Designer;
import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.model.ResponseData;
import com.jianma.fzkb.model.ResultModel;
import com.jianma.fzkb.service.DesignerService;
import com.jianma.fzkb.service.MaterialService;
import com.jianma.fzkb.util.ConfigInfo;
import com.jianma.fzkb.util.GraphicsUtil;
import com.jianma.fzkb.util.JwtUtil;
import com.jianma.fzkb.util.Md5SaltTool;
import com.jianma.fzkb.util.ResponseCodeUtil;
import com.jianma.fzkb.util.WebRequestUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	@Qualifier(value = "configInfo")
	private ConfigInfo configInfo;
	
	@Autowired
	@Qualifier(value = "designerServiceImpl")
	private DesignerService designerServiceImpl;
	
	@Autowired
	@Qualifier(value = "materialServiceImpl")
	private MaterialService materialServiceImpl;
	
	@ExceptionHandler(FZKBException.class)
	public @ResponseBody ResultModel handleCustomException(FZKBException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		resultModel.setSuccess(false);
		return resultModel;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/authorityCheck", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel authorityCheck(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String username, @RequestParam String password) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ResultModel resultModel = new ResultModel();

		Optional<Designer> designer = designerServiceImpl.findDesignerByUsername(username);
		if (designer.isPresent()){
			try {
				if(Md5SaltTool.validPassword(password, designer.get().getPassword())){
					String subject = JwtUtil.generalSubject(designer.get());
					String token = JwtUtil.createJWT(ResponseCodeUtil.JWT_ID, subject, ResponseCodeUtil.JWT_TTL);
					String refreshToken = JwtUtil.createJWT(ResponseCodeUtil.JWT_ID, subject, ResponseCodeUtil.JWT_REFRESH_TTL);
					JSONObject jo = new JSONObject();
					jo.put("token", token);
					jo.put("refreshToken", refreshToken);
					jo.put("userId", designer.get().getId());
					jo.put("roleId", designer.get().getRole());
					resultModel.setObject(jo);
					resultModel.setMessage("验证成功!");
					resultModel.setResultCode(200);
				}else{
					resultModel.setMessage("密码不正确!");
					resultModel.setResultCode(110);
				}
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				resultModel.setMessage("验证失败!");
				resultModel.setResultCode(110);
				e.printStackTrace();
			}
			
		}else{
			resultModel.setResultCode(110);
			resultModel.setMessage("用户不存在！");
		}
		
		return resultModel;
	}
	
	@RequestMapping(value = "/matchReview", method = RequestMethod.GET)
	public ModelAndView matchReview(HttpServletRequest request, HttpServletResponse response,
			Locale locale, Model model) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ModelAndView modelView = new ModelAndView();
		String uwId = request.getParameter("uwId");
		String gcId = request.getParameter("gcId");
		String trId = request.getParameter("trId");
		
		if (uwId != null && gcId != null && trId != null){
			Map<String,Material> map = materialServiceImpl.getMaterialByIds(Integer.parseInt(uwId), Integer.parseInt(gcId), Integer.parseInt(trId));
			
			modelView.addObject("uwUrl", map.get("underwear").getImageUrl());
			modelView.addObject("gcUrl", map.get("greatcoat").getImageUrl());
			modelView.addObject("trUrl", map.get("trouser").getImageUrl());
		}
		
		modelView.setViewName("/preview");
		
		return modelView;
	}
	
	@RequestMapping(value = "/matchPage", method = RequestMethod.GET)
	public ModelAndView matchPage(HttpServletRequest request, HttpServletResponse response,
			Locale locale, Model model) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ModelAndView modelView = new ModelAndView();
		String authCode = request.getParameter("authCode");
		String userId = request.getParameter("userId");
		
		if (null != authCode) {
			try{
				Claims claims = JwtUtil.parseJWT(authCode);
				
				JSONObject jsonObject = JSONObject.parseObject(claims.getSubject());
				if (null != userId && null != jsonObject) {
					if (Integer.parseInt(userId) == jsonObject.getIntValue("userId")) {
						modelView.addObject("authCode", authCode);
						modelView.addObject("userId", userId);
						modelView.setViewName("matchView");
					} else {
						modelView.setViewName("error");
					}
				} else {
					modelView.setViewName("error");
				}
				
			}
			catch(ExpiredJwtException ex){
				modelView.setViewName("error");
			}
			
		} else {
			modelView.setViewName("error");
		}
		
		return modelView;
	}
	
	@RequestMapping(value = "/matchMobilePage", method = RequestMethod.GET)
	public ModelAndView matchMobilePage(HttpServletRequest request, HttpServletResponse response,
			Locale locale, Model model) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ModelAndView modelView = new ModelAndView();
		
		String authCode = request.getParameter("authCode");
		String userId = request.getParameter("userId");
		
		if (null != authCode) {
			try{
				Claims claims = JwtUtil.parseJWT(authCode);
				
				JSONObject jsonObject = JSONObject.parseObject(claims.getSubject());
				if (null != userId && null != jsonObject) {
					if (Integer.parseInt(userId) == jsonObject.getIntValue("userId")) {
						modelView.addObject("authCode", authCode);
						modelView.addObject("userId", userId);
						modelView.setViewName("mobile");
					} else {
						modelView.setViewName("error");
					}
				} else {
					modelView.setViewName("error");
				}
				
			}
			catch(ExpiredJwtException ex){
				modelView.setViewName("error");
			}
			
		} else {
			modelView.setViewName("error");
		}
		
		return modelView;
	}
	
	@RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel refreshToken(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String token) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ResultModel resultModel = new ResultModel();

    	Claims claims = JwtUtil.parseJWT(token);
 		String json = claims.getSubject();
 		JSONObject jObject = JSONObject.parseObject(json);
 		Designer designer = new Designer();
 		designer.setId(jObject.getIntValue("userId"));
 		designer.setRole(jObject.getIntValue("roleId"));
 		String subject = JwtUtil.generalSubject(designer);
 		String refreshToken = JwtUtil.createJWT(ResponseCodeUtil.JWT_ID, subject, ResponseCodeUtil.JWT_TTL);
 		
		resultModel.setResultCode(200);
		resultModel.setMessage("更新token成功！");
		resultModel.setObject(refreshToken);
		
		return resultModel;
	}
	
	@RequestMapping(value = "/uploadKey/{type}", method = RequestMethod.GET)
	public @ResponseBody  Map<String, String> uploadKey(HttpServletRequest request,HttpServletResponse response,Locale locale, Model model, @PathVariable int type) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		String endpoint = configInfo.endpoint;
        String accessId = configInfo.accessId;
        String accessKey = configInfo.accessKey;
        String bucket = configInfo.bucket;
        String dir = "";
        if (type == 1){
        	dir = "kbxt/";
        }
        else if(type == 2){
        	dir = "/";
        }
        else if(type == 3){
        	dir = "/";
        }
        else{
        	dir = "/";
        }
        
        String host = "http://" + bucket + "." + endpoint;
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        try { 	
        	long expireTime = 30;
        	long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);
            
            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            //respMap.put("expire", formatISO8601Date(expiration));
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            return respMap;
            
        } catch (Exception e) {
            
            return null;
        }
        
	}
	
	@RequestMapping(value = "/getCode", method = RequestMethod.GET)
	public void getCode(HttpServletRequest request, HttpServletResponse response) {
		try {

			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			// 表明生成的响应是图片
			response.setContentType("image/jpeg");

			Map<String, Object> map = new GraphicsUtil().getGraphics();
			request.getSession().setAttribute("rand", map.get("rand"));
			ImageIO.write((RenderedImage) map.get("image"), "JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getRandomMaterial", method = RequestMethod.GET)
	@ResponseBody
	public ResultModel getRandomMaterial(HttpServletRequest request, HttpServletResponse response, @RequestParam int count) {
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ResultModel resultModel = new ResultModel();
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
}
