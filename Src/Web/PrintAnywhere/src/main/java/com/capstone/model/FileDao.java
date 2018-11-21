package com.capstone.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capstone.dto.File;
import com.capstone.dto.User;
import com.capstone.util.Constants;

@Repository
public class FileDao {
	
	@Autowired
	private DataSource dataSource;
	//DataSource 객체는 데이터베이스의 모든 리소스를 지칭하는 포괄적인 개념으로 JDBC 2.0 표준의 확장 API로 처음 소개되었다.
	//여기서 설명하는 DataSource는 하나의 데이터베이스에 대응된다.
	private SqlSession sqlSession;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	private FactoryDao factoryDao;
	
	@Autowired
	public void setFactoryDao(FactoryDao factoryDao) {
		this.factoryDao = factoryDao;
	}

	public ArrayList<File> selectFileList(String userId, int sizeOfList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<File> fileArray = new ArrayList<File>();
		String sql="select * from user where file = ?";
		try {
			con = dataSource.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,userId);
			rs = pstmt.executeQuery();
			while(rs.next() && sizeOfList > 0) {
				fileArray.add(new File(rs.getInt("file_id"), 
						rs.getString("file_hash"), rs.getString("file_name"),
						rs.getInt("file_type"), rs.getString("file_date"), 
						rs.getString("file_date"), rs.getString("user_id")));
				sizeOfList--;
			}
		} catch (SQLException e) {
			return fileArray;
		}finally {
			try {
				factoryDao.close(con, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fileArray;
	}

	public int insertFile(File file) {
		try {
			int count = sqlSession.insert("file.insertFile", file);
			if(count > 0) {
				return Constants.DB_RES_CODE_1;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_2;
	}
}
