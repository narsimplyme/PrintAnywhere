package com.capstone.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

//@Repository
public class FactoryDao {

	private String driver;
	private static String url;
	private static String username;
	private static String password;
	private static Map dbserver = null;
	private static FactoryDao factoryDao;
	private static Connection conn = null;
	
	/**
	 * 객체 생성시 DB연결을 위한 초기값을 셋팅해주는 생성자
	 * @param dbserver
	 * @throws SQLException 
	 */
	private FactoryDao(Map dbserver) throws SQLException {
		this.dbserver = dbserver;		
		driver = dbserver.get("driver").toString();
		url = dbserver.get("url").toString();
		username = dbserver.get("username").toString();
		password = dbserver.get("password").toString();
		try {			
			Class.forName(dbserver.get("driver").toString() );	
		} catch (ClassNotFoundException e) {		
			e.printStackTrace();
		}
		//conn = DriverManager.getConnection(url, username, password);
	}


	public void reStartConnection() throws SQLException {
		conn = DriverManager.getConnection(url, username, password);
	}
	/**
	 * DB사용후 자원을 해제하는 메소드
	 * @param con
	 * @param pstmt
	 * @param rs
	 * @throws SQLException
	 */
	public void close(Connection con, PreparedStatement pstmt, ResultSet rs ) throws SQLException {
		if(rs != null){	rs.close(); }
		if(pstmt != null){ pstmt.close(); }
		if(con != null){ con.close(); }
	}
	
	public static FactoryDao getInstance() {
		return factoryDao;
	}
	
	public static FactoryDao getInstance(Map map) throws SQLException {
		if(factoryDao == null) {
			factoryDao = new FactoryDao(map);
		}
		return factoryDao;
	}
}