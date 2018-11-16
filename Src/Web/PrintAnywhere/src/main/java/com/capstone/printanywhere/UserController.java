package com.capstone.printanywhere;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping(value = "signUp.do",  method = RequestMethod.POST)
	@ResponseBody
	public Response signUp(User user, HttpServletResponse response) {
		int resCode = userService.insertUser(user);
		res = new Response();
		System.out.println(res.toString());
		response.setContentType("application/json");
		return res;
	}
	
	@RequestMapping(value = "authMe.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response authMe(HttpServletRequest request) {
		String token = request.getHeader("x-access-token");
		boolean authResult = userService.isAuth(token);
		res = new Response();
		if(authResult) {
			//인증 성공
			res.setSuccess(true);
			User user = userService.selectUser(token);
			res.setData(user);	
		}else {
			//인증 실패
			res.setSuccess(false);
		}
		return res;
	}
	
	@RequestMapping(value = "profile.do",  method = RequestMethod.PUT)
	@ResponseBody
	public Response profile(HttpServletRequest request, User user, String userNewPw, String userNewPwConfirm) {
		String token = request.getHeader("x-access-token");
		boolean authResult = userService.isAuth(token);
		res = new Response();
		if(authResult) {
			//인증 성공
			res.setSuccess(true);
		}else {
			//인증 실패
			res.setSuccess(false);
		}
		System.out.println(user.toString() + " | "+ userNewPw +" / " +userNewPwConfirm);
		return res;
	}
	
	@RequestMapping(value = "show.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response show(HttpServletRequest request) {
		String token = request.getHeader("x-access-token");
		boolean authResult = userService.isAuth(token);
		res = new Response();
		User user = new User("하하", "하하", "하하", "하하", "하하", "하하", "하하");
		res.setData(user);
		return res;
	}
		
}
