package com.capstone.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capstone.dto.User;
import com.capstone.util.Constants;

@Repository
public class UserDao {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	private FactoryDao factoryDao;
	
	@Autowired
	public void setFactoryDao(FactoryDao factoryDao) {
		this.factoryDao = factoryDao;
	}

	public int selectToken(String token) {
		return 3;
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
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="select * from user where user_id = ?";
		try {
			con = dataSource.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,userId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				return new User(rs.getString("user_id"), " ",
						rs.getString("user_name"), rs.getString("user_mail"), 
						rs.getString("user_nickname"), rs.getString("user_phoneNumber"), rs.getString("user_point"));
			}
		} catch (SQLException e) {
			return null;
		}finally {
			try {
				factoryDao.close(con, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
