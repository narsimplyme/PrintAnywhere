package com.capstone.printanywhere;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.dto.Pay;
import com.capstone.dto.Token;
import com.capstone.service.PayService;
import com.capstone.service.UserService;
import com.capstone.util.AuthToken;
import com.capstone.util.Constants;
import com.capstone.util.Response;

@Controller
public class PayController {
	Response res;
	JSONObject data;
	
	private PayService payService;
	private UserService userService;

	@Autowired
	public void setPayService(PayService payService) {
		this.payService = payService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
	@RequestMapping(value = "selectPayList.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response selectPayList (HttpServletRequest request, HttpServletResponse response, int startPoint, int endPoint) {
		res = new Response();
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			List<Pay> payArray = payService.selectPayList(authResult.getUserId(), startPoint, endPoint);
			data.put("payList", payArray);
			res.setData(data);
		}
		return res;
	}
	
	@RequestMapping(value = "insertPay.do",  method = RequestMethod.POST)
	@ResponseBody
	public Response insertPay (HttpServletRequest request, HttpServletResponse response, @RequestBody Pay pay) {
		res = new Response();
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			pay.setUserId(authResult.getUserId());
			int resCode = payService.insertPay(pay);
			if(resCode == Constants.DB_RES_CODE_1) {
				resCode = userService.updatePoint(authResult.getUserId(), pay.getPayCash());
				if(resCode == Constants.DB_RES_CODE_5) {
					res.setMessage(Constants.MSG_CODE_200);
				}else {
					res.setSuccess(false);
					res.setMessage(Constants.MSG_CODE_112);
				}			
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
	
}
