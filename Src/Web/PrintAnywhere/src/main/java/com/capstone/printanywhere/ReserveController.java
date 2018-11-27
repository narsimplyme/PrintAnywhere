package com.capstone.printanywhere;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.capstone.service.ReserveService;
import com.capstone.service.UserService;
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
	
	
}
