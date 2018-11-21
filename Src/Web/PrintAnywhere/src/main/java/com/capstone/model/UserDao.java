package com.capstone.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capstone.dto.Token;
import com.capstone.dto.User;
import com.capstone.util.Constants;

@Repository
public class UserDao {
	
	@Autowired
	private DataSource dataSource;
	
	private SqlSession sqlSession;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public Token selectToken(String tokenId) {
		try {
			return sqlSession.selectOne("token.selectToken", tokenId);
		} catch (PersistenceException e) {
			return null;
		}
	}

	public int signIn(String userId, String userPw) {
		try {
			String result = sqlSession.selectOne("user.signIn", userId);
			if(userPw.equals(result)) {
				return Constants.DB_RES_CODE_3;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_4;
	}

	public int insertUser(User user) {
		try {
			int count = sqlSession.insert("user.insertUser", user);
			if(count > 0) {
				return Constants.DB_RES_CODE_1;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_2;
	}

	public User selectUser(String userId) {
		try {
			return (User)sqlSession.selectOne("user.selectUser", userId);
		} catch (PersistenceException e) {
			return null;
		}
	}

	public int updateUser(User user, String userNewPw) {
		try {
			int count = sqlSession.update("user.updateUser", user);
			if(count > 0) {
				return Constants.DB_RES_CODE_5;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_6;
	}

	public int updateToken(String newToken, String tokenId) {
		try {
			Map<String, String> tokenMap = new HashMap<String, String>();
			tokenMap.put("tokenId", tokenId);
			tokenMap.put("newToken", newToken);
			Date date = new Date();
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			tokenMap.put("ttl", f.format(date));
			int count = sqlSession.update("user.updateToken", tokenMap);
			if(count > 0) {
				return Constants.DB_RES_CODE_5;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_6;
	}

	public int insertToken(Token token) {
		try {
			int count = sqlSession.insert("user.insertToken", token);
			if(count > 0) {
				return Constants.DB_RES_CODE_1;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_2;
	}
}
