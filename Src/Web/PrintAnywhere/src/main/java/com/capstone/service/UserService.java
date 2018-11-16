package com.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public boolean isAuth(String token) {
		int resCode = userDao.selectToken(token);
		if(resCode == Constants.DB_RES_CODE_3)
			return true;
		else
			return false;
	}

	public int insertUser(User user) {
		return 0;
	}

	public User selectUser(String token) {
		return null;
	}

	public boolean signIn(String userId, String userPw) {
		int resCode = userDao.signIn(userId, userPw);
		if(resCode == Constants.DB_RES_CODE_3)
			return true;
		else
			return false;
	}

}
