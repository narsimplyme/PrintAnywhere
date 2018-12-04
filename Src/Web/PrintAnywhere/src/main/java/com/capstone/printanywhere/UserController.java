package com.capstone.printanywhere;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.dto.Token;
import com.capstone.dto.User;
import com.capstone.service.UserService;
import com.capstone.util.AuthToken;
import com.capstone.util.Constants;
import com.capstone.util.Response;

@Controller
public class UserController {

	Response res;
	JSONObject data;
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "signUp.do",  method = RequestMethod.POST)
	@ResponseBody
	public Response signUp (@RequestBody User user, HttpServletResponse response) {
		res = new Response();
		data = new JSONObject();

		if(userService.insertUser(user)) {
			res.setSuccess(true);
			res.setMessage(Constants.MSG_CODE_200);
			String token = AuthToken.getToken(user.getUserId());
			int resCode = userService.insertToken(token, user.getUserId());
			if(resCode == Constants.DB_RES_CODE_1) {
				res.setSuccess(true);
				res.setMessage(Constants.MSG_CODE_200);
				data.put("token", token);
				res.setData(data);
			}else {
				res.setSuccess(false);
				if(resCode == Constants.DB_RES_CODE_9) 
					res.setMessage(Constants.MSG_CODE_106);
				else 
					res.setMessage(Constants.MSG_CODE_307);
			}
			
			//회원가입 성공

		}else {
			res.setSuccess(false);
			res.setErrors(Constants.ERROR_CODE_1);
			res.setMessage(Constants.MSG_CODE_101);
			//회원가입 실패
		}
		return res;
	}
	
	@RequestMapping(value = "signIn.do",  method = RequestMethod.POST)
	@ResponseBody
	public Response signIn(@RequestBody User user) {
		res = new Response();
		data = new JSONObject();
		if(userService.signIn(user.getUserId(), user.getUserPw())) {
			//로그인 성공
			res.setSuccess(true);
			String newToken = AuthToken.getToken(user.getUserId());
			int resCode = userService.updateToken(newToken, user.getUserId());
			data.put("token", newToken);
			res.setSuccess(true);
			res.setMessage(Constants.MSG_CODE_200);
			res.setData(data);
		}else {
			//로그인 실패
			res.setSuccess(false);
			res.setErrors(Constants.ERROR_CODE_2);
			res.setMessage(Constants.MSG_CODE_103);
		}
		return res;
	}
	
	@RequestMapping(value = "authMe.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response authMe(HttpServletRequest request) {
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		
		if(res.isSuccess()) {
			data.put("token", tokenId);
			data.put("ttl", authResult.getTtl());
			res.setData(data);
		}
		return res;
	}
	
	@RequestMapping(value = "authMeFull.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response authMeFull(HttpServletRequest request) {
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		
		if(res.isSuccess()) {
			User user = userService.selectUser(authResult.getUserId());
			if(user != null) {
				res.setSuccess(true);
				res.setMessage(Constants.MSG_CODE_200);
				data.put("token", tokenId);
				data.put("ttl", authResult.getTtl());
				data.put("user", user);	
				res.setData(data);	
			}else {
				//user객체를 찾지못할경우
				res.setErrors(Constants.ERROR_CODE_2);
				res.setMessage(Constants.MSG_CODE_303);
				data.put("token", tokenId);
				data.put("ttl", authResult.getTtl());
			}
		}
		return res;
	}
	
	@RequestMapping(value = "profile.do",  method = RequestMethod.PUT)
	@ResponseBody
	public Response profile(HttpServletRequest request, @RequestBody Map<String, String> map ) {
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		
		if(res.isSuccess()) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				User user = mapper.convertValue(map, User.class);
				String userNewPw = map.get("userNewPw");
				if(userNewPw != null)
					user.setUserPw(userNewPw);
				int resCode = userService.updateUser(user);
				if(resCode == Constants.DB_RES_CODE_5) {
					res.setSuccess(true);
					res.setMessage(Constants.MSG_CODE_200);
					String newToken = AuthToken.getToken(user.getUserId());
					userService.updateToken(newToken, authResult.getTokenId());
					data.put("token", newToken);
					res.setData(data);
				}else {
					res.setSuccess(false);
					if(resCode == Constants.DB_RES_CODE_9) 
						res.setMessage(Constants.MSG_CODE_106);
					else 
						res.setMessage(Constants.MSG_CODE_304);
				}
			} catch (IllegalArgumentException e) {
				res.setSuccess(false);
				res.setErrors(Constants.ERROR_CODE_6);
				res.setMessage(Constants.MSG_CODE_109);
				return res;
			}
			
		}
		return res;
	}
	
	@RequestMapping(value = "show.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response show(HttpServletRequest request) {
		data = new JSONObject();

		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		
		if(res.isSuccess()) {
			//토큰 기반 유저 아이디 검색
			User user = userService.selectUser(authResult.getUserId());
			if(user != null) {
				res.setSuccess(true);
				res.setMessage(Constants.MSG_CODE_200);
				user.setUserPw("");
				data.put("user", user);
				res.setData(data);
				//res.setData(user);
			}else {
				res.setErrors(Constants.ERROR_CODE_2);
				res.setMessage(Constants.MSG_CODE_303);
				data.put("token", tokenId);
				data.put("ttl", authResult.getTtl());
			}
		}
		return res;
	}
	
	@RequestMapping(value = "refresh.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response refresh(HttpServletRequest request) {
		res = new Response();
		data = new JSONObject();

		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		System.out.println(res);
		if(res.getMessage() == Constants.MSG_CODE_108 || res.getMessage() == Constants.MSG_CODE_200) {
			String newToken = AuthToken.getToken(authResult.getUserId());
			userService.updateToken(newToken, authResult.getUserId());
			res.setSuccess(true);
			res.setErrors("");
			res.setMessage(Constants.MSG_CODE_200);
			data.put("token", newToken);
			res.setData(data);
		}
		return res;
	}
	
	@RequestMapping(value = "isId.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response isId(HttpServletRequest request, String userId) {
		res = new Response();
		data = new JSONObject();
		
		int resCode = userService.isId(userId);
		if(resCode == Constants.DB_RES_CODE_4) {
			res.setSuccess(true);
		}else {
			if(resCode == Constants.DB_RES_CODE_9) {
				res.setErrors(Constants.ERROR_CODE_2);
				res.setMessage(Constants.MSG_CODE_106);
			}
			res.setSuccess(false);
		}
		return res;
	}
	
	
}
