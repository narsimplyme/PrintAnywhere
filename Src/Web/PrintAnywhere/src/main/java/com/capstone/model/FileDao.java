package com.capstone.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capstone.dto.File;
import com.capstone.dto.User;

@Repository
public class FileDao {
	
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
			con=factoryDao.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,userId);
			rs = pstmt.executeQuery();
			while(rs.next() && sizeOfList > 0) {
				fileArray.add(new File(rs.getInt("file_id"), 
						rs.getString("file_hash"), rs.getString("file_name"),
						rs.getString("file_type"), rs.getString("file_date"), 
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
}
