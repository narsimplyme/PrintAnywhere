package com.capstone.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.dto.Token;
import com.capstone.dto.User;
import com.capstone.model.UserDao;
import com.capstone.util.Constants;

@Service
public class UserService {
	
	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public Token isAuth(String tokenId) {
		return userDao.selectToken(tokenId);		
	}

	public boolean insertUser(User user) {
		int resCode = userDao.insertUser(user);
		if(resCode == Constants.DB_RES_CODE_1)
			return true;
		else
			return false;
	}

	public User selectUser(String userId) {
		return userDao.selectUser(userId);
	}

	public boolean signIn(String userId, String userPw) {
		if(userPw == null)
			return false;
		int resCode = userDao.signIn(userId, userPw);
		if(resCode == Constants.DB_RES_CODE_3)
			return true;
		else
			return false;
	}

	public User selectUserToToken(String token) {
		// TODO Auto-generated method stub
		return new User();
	}

	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	public int updateToken(String newToken, String userId) {
		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("userId", userId);
		tokenMap.put("newToken", newToken);
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		tokenMap.put("ttl", f.format(date));
		
		return userDao.updateToken(tokenMap);
	}

	public int insertToken(String tokenId, String userId) {
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Token token = new Token(tokenId, f.format(date), userId);

		return userDao.insertToken(token);
	}

	public int isId(String userId) {
		return userDao.isId(userId);
	}

	public int updatePoint(String userId, int point) {
		return userDao.updatePoint(userId, point); 
	}

}
