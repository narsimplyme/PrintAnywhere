package com.capstone.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capstone.dto.User;
import com.capstone.util.Constants;

@Repository
public class UserDao {
	
	private FactoryDao factoryDao;
	
	@Autowired
	public void setFactoryDao(FactoryDao factoryDao) {
		this.factoryDao = factoryDao;
	}

	public int selectToken(String token) {
		return 3;
	}

	public int signIn(String userId, String userPw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="SELECT * FROM user where user_id = ? and user_pw = ?";
		try {
			con=factoryDao.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,userId);
			pstmt.setString(2,userPw);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				return Constants.DB_RES_CODE_3;
			}
		} catch (SQLException e) {
			return Constants.DB_RES_CODE_9;
		}finally {
			try {
				factoryDao.close(con, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Constants.DB_RES_CODE_4;
	}

	public int insertUser(User user) {
		Connection con =null;
		PreparedStatement pstmt =null;
		String sql=" insert into user (user_id, user_pw, user_name, user_mail, user_nickname, user_phoneNumber) "
				+ "values (?,?,?,?,?,?)";
		try {
			con=factoryDao.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserMail());
			pstmt.setString(5, user.getUserNickname());
			pstmt.setString(6, user.getUserPhoneNumber());

			if(pstmt.executeUpdate() != 0) {
				return Constants.DB_RES_CODE_1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return Constants.DB_RES_CODE_9;

		}finally {
			try {
				factoryDao.close(con, pstmt, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Constants.DB_RES_CODE_2;
	}

	public User selectUser(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="select * from user where user_id = ?";
		try {
			con=factoryDao.getConnection();
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
