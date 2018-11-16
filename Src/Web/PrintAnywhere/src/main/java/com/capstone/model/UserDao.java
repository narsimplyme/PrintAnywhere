package com.capstone.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	
	/*private FactoryDao factoryDao;
	
	@Autowired
	public void setFactoryDao(FactoryDao factoryDao) {
		this.factoryDao = factoryDao;
	}*/

	public int selectToken(String token) {
		return 3;
	}

	public int signIn(String userId, String userPw) {
		return 3;
	}
}
