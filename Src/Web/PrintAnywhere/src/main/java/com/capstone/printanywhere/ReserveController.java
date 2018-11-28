package com.capstone.printanywhere;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.dto.Reserve;
import com.capstone.dto.Token;
import com.capstone.service.ReserveService;
import com.capstone.service.UserService;
import com.capstone.util.AuthToken;
import com.capstone.util.Constants;
import com.capstone.util.Response;

@Controller
public class ReserveController {
	
	Response res;
	JSONObject data;
	
	private ReserveService reserveService;
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	public void setReserveService(ReserveService reserveService) {
		this.reserveService = reserveService;
	}
	
	@RequestMapping(value = "insertReserve.do",  method = RequestMethod.POST)
	@ResponseBody
	public Response insertReserve(HttpServletRequest request, @RequestBody Reserve reserve) {
		res = new Response();
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			reserve.setUserId(authResult.getUserId());
			int resCode = reserveService.insertReserve(reserve);
			if(resCode == Constants.DB_RES_CODE_1) {
				res.setMessage(Constants.MSG_CODE_200);
			}else {
				if(resCode == Constants.DB_RES_CODE_9) {
					res.setSuccess(false);
					res.setErrors(Constants.ERROR_CODE_1);
					res.setMessage(Constants.MSG_CODE_106);
				}else {
					res.setSuccess(false);
					res.setMessage(Constants.MSG_CODE_301);
				}
			}
		}
		return res;
	}
	
	@RequestMapping(value = "selectReserveList.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response selectReserveList(HttpServletRequest request) {
		res = new Response();
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			List<Reserve> reserveList = reserveService.selectReserveList(authResult.getUserId());
			data.put("reserveList", reserveList);
			res.setMessage(Constants.MSG_CODE_200);
			res.setData(data);
		}
		return res;
	}
	
	@RequestMapping(value = "selectReserve.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response selectReserve(HttpServletRequest request) {
		res = new Response();
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			
		}
		return res;
	}
	
	@RequestMapping(value = "updateReserve.do",  method = RequestMethod.PUT)
	@ResponseBody
	public Response updateReserve(HttpServletRequest request, @RequestBody Reserve reserve) {
		res = new Response();
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			int resCode = reserveService.updateReserve(reserve);
			if(resCode == Constants.DB_RES_CODE_5) {
				res.setMessage(Constants.MSG_CODE_200);
			}else {
				res.setSuccess(false);
				if(resCode == Constants.DB_RES_CODE_9) 
					res.setMessage(Constants.MSG_CODE_106);
				else 
					res.setMessage(Constants.MSG_CODE_304);
			}
		}
		return res;
	}
	
	@RequestMapping(value = "deleteReserve.do",  method = RequestMethod.DELETE)
	@ResponseBody
	public Response deleteReserve(HttpServletRequest request, @RequestBody Reserve reserve) {
		res = new Response();
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			int resCode = reserveService.deleteReserve(reserve.getReserveId());
		}
		return res;
	}
	
}
