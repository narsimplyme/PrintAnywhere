package com.capstone.printanywhere;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.dto.Client;
import com.capstone.dto.Token;
import com.capstone.service.ClientService;
import com.capstone.service.UserService;
import com.capstone.util.AuthToken;
import com.capstone.util.Response;

@Controller
public class ClientController {
	Response res;
	JSONObject data;
	
	private ClientService clientService;
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}



	@RequestMapping(value = "selectNearClient.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response selectPayList (HttpServletRequest request, HttpServletResponse response, float latitude, float longitude) {
		res = new Response();
		data = new JSONObject();
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			List<Client> clientArray = clientService.selectNearClient(latitude, longitude);
			data.put("nearClientList", clientArray);
			res.setData(data);
		}
		return res;
	}
}
