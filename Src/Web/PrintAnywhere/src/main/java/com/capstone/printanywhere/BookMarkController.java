package com.capstone.printanywhere;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.dto.BookMark;
import com.capstone.dto.Token;
import com.capstone.service.BookMarkService;
import com.capstone.service.UserService;
import com.capstone.util.AuthToken;
import com.capstone.util.Constants;
import com.capstone.util.Response;

@Controller
public class BookMarkController {

	Response res;
	JSONObject data;
	private BookMarkService bookMarkService;
	private UserService userService;
	
	@Autowired
	public void setBookMarkService(BookMarkService bookMarkService) {
		this.bookMarkService = bookMarkService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "insertBookMark.do",  method = RequestMethod.POST)
	@ResponseBody
	public Response insertBookMark(HttpServletRequest request, @RequestBody BookMark bookMark) {
		res = new Response();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			int resCode = bookMarkService.insertBookMark(authResult.getUserId(), bookMark.getClientId());
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
	
	@RequestMapping(value = "selectBookMarkList.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response selectBookMark(HttpServletRequest request) {
		res = new Response();
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			List<BookMark> bookMarkArray = bookMarkService.selectBookMarkList(authResult.getUserId());
			if(bookMarkArray == null) {
				bookMarkArray = new ArrayList<BookMark>();
			}
			data.put("bookMarkList", bookMarkArray);
			res.setData(data);
			res.setMessage(Constants.MSG_CODE_200);
		}
		
		return res;
	}
	
	@RequestMapping(value = "deleteBookMark.do",  method = RequestMethod.DELETE)
	@ResponseBody
	public Response deleteBookMark(HttpServletRequest request, @RequestBody BookMark bookMark) {
		res = new Response();
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			int resCode = bookMarkService.deleteBookMark(authResult.getUserId(), bookMark.getClientId());
		}
		
		return res;
	}
	
	
}
