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

import com.capstone.dto.PrintLog;
import com.capstone.dto.Token;
import com.capstone.service.PrintLogService;
import com.capstone.service.UserService;
import com.capstone.util.AuthToken;
import com.capstone.util.Constants;
import com.capstone.util.Response;

@Controller
public class PrintLogController {
	Response res;
	JSONObject data;
	private PrintLogService printLogService;
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	public void setPrintLogService(PrintLogService printLogService) {
		this.printLogService = printLogService;
	}
	
	@RequestMapping(value = "insertPrintLog.do",  method = RequestMethod.POST)
	@ResponseBody
	public Response insertPrintLog(HttpServletRequest request, @RequestBody PrintLog printLog) {
		res = new Response();
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			int resCode = printLogService.insertPrintLog(printLog);
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
	
	@RequestMapping(value = "selectPrintLogList.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response selectPrintLogList(HttpServletRequest request, int startPoint, int endPoint) {
		res = new Response();
		data = new JSONObject();
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			List<PrintLog> logArray = printLogService.selectPrintLogList(authResult.getUserId(), startPoint, endPoint);
			data.put("PrintLogList", logArray);
			res.setData(data);
		}
		return res;
	}
	
}
