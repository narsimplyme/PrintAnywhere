package com.capstone.printanywhere;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
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

import com.capstone.dto.User;
import com.capstone.service.UserService;
import com.capstone.util.AuthToken;
import com.capstone.util.Response;

@Controller
public class UserController {
	
	private UserService userService;
	Response res;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "signUp.do",  method = RequestMethod.POST)
	@ResponseBody
	public Response signUp (@RequestBody User user, HttpServletResponse response) {
		res = new Response();
		if(userService.insertUser(user)) {
			res.setSuccess(true);
			String token = AuthToken.getToken(user.getUserId());
			res.setToken(token);
			//회원가입 성공

		}else {
			res.setSuccess(false);
			//회원가입 실패
		}
		return res;
	}
	
	@RequestMapping(value = "signIn.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response signIn(String userId, String userPw) {
		res = new Response();
		if(userService.signIn(userId, userPw)) {
			//로그인 성공
			res.setSuccess(true);
			res.setToken(AuthToken.getToken(userId));
		}else {
			//로그인 실패
			res.setSuccess(false);
		}
		return res;
	}
	
	@RequestMapping(value = "authMe.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response authMe(HttpServletRequest request) {
		res = new Response();
		String token = request.getHeader("x-access-token");
		boolean authResult = userService.isAuth(token);
		if(authResult) {
			//인증 성공
			res.setSuccess(true);
			User user = userService.selectUserToToken(token);
			res.setData(user);	
		}else {
			//인증 실패
			res.setSuccess(false);
		}
		return res;
	}
	
	@RequestMapping(value = "profile.do",  method = RequestMethod.PUT)
	@ResponseBody
	public Response profile(HttpServletRequest request, @RequestBody Map<String, String> map ) {
		res = new Response();
		String token = request.getHeader("x-access-token");
		boolean authResult = userService.isAuth(token);
		if(authResult) {
			//인증 성공
			ObjectMapper mapper = new ObjectMapper();
			User user = mapper.convertValue(map, User.class);
			
			String userNewPw = map.get("userNewPw");			
			//업데이트 처리 
			res.setSuccess(true);
		}else {
			//인증 실패
			res.setSuccess(false);
		}
		return res;
	}
	
	@RequestMapping(value = "show.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response show(HttpServletRequest request) {
		res = new Response();
		String token = request.getHeader("x-access-token");
		boolean authResult = userService.isAuth(token);
		if(authResult) {
			res.setSuccess(true);
			//토큰 기반 유저 아이디 검색
			String userId = "test";
			User user = userService.selectUser(userId);
			if(user != null) {
				res.setData(user);
				
			}else {
				res.setSuccess(false);

			}
		}else {
			res.setSuccess(false);
		}
		return res;
	}
		
}
