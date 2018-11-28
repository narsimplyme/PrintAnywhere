package com.capstone.printanywhere;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.dto.Client;
import com.capstone.dto.Reserve;
import com.capstone.dto.Token;
import com.capstone.service.ClientService;
import com.capstone.service.ReserveService;
import com.capstone.service.UserService;
import com.capstone.util.AuthToken;
import com.capstone.util.Constants;
import com.capstone.util.Response;

@Controller
public class ClientController {
	Response res;
	JSONObject data;
	
	private ClientService clientService;
	private UserService userService;
	private ReserveService reserveService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}
	@Autowired
	public void setReserveService(ReserveService reserveService) {
		this.reserveService = reserveService;
	}



	@RequestMapping(value = "selectNearClient.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response selectPayList (HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="latitude", defaultValue="-1") float latitude, 
			@RequestParam(value="longitude", defaultValue="-1") float longitude) {
		res = new Response();
		data = new JSONObject();
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			if(latitude == -1 || longitude == -1) {
				res.setSuccess(false);
				res.setErrors(Constants.MSG_CODE_113);
				return res;
			}
			List<Client> clientArray = clientService.selectNearClient(latitude, longitude);
			data.put("nearClientList", clientArray);
			res.setData(data);
		}
		return res;
	}
	
	@RequestMapping(value = "reserveFileForClient.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response reserveFileForClient(HttpServletRequest request, int clientId) {
		res = new Response();
		data = new JSONObject();
		
		int resCode = clientService.updateClient(clientId);
		if(resCode == Constants.DB_RES_CODE_5) {
			List<Reserve> reserveList = reserveService.reserveFileForClient(clientId);
			data.put("reserveList", reserveList);
			res.setData(data);
			res.setMessage(Constants.MSG_CODE_200);
			res.setSuccess(true);	
		}else {
			res.setSuccess(false);
			if(resCode == Constants.DB_RES_CODE_9) 
				res.setMessage(Constants.MSG_CODE_106);
			else 
				res.setMessage(Constants.MSG_CODE_304);
		}
		return res;
	}
	
	
}
