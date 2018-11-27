package com.capstone.printanywhere;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.capstone.service.PrintLogService;
import com.capstone.service.UserService;
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
	
	
}
