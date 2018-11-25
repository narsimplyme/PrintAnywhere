package com.capstone.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<File> selectFileList(String userId, int endFilePoint) {
		try {
			File file = new File();
			file.setUserId(userId);
			file.setEndFilePoint(endFilePoint);
			List<File> list = sqlSession.selectList("file.selectFileList", file);
			return list;
		} catch (PersistenceException e) {
			return null;
		}
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

	public int deleteFile(int fileId) {
		try {
			int count = sqlSession.delete("file.deleteFile", fileId);
			if(count > 0) {
				return Constants.DB_RES_CODE_7;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_8;
	}

	public String fileDownload(int fileId) {
		try {
			return sqlSession.selectOne("file.fileDownload", fileId);
		} catch (PersistenceException e) {
			return null;
		}
	}
}
