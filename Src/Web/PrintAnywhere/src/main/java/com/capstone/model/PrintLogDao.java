package com.capstone.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capstone.dto.Pay;
import com.capstone.dto.PrintLog;
import com.capstone.util.Constants;

@Repository
public class PrintLogDao {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public int insertPrintLog(PrintLog printLog) {
		try {
			int count = sqlSession.insert("printLog.insertPrintLog", printLog);
			if(count > 0) {
				return Constants.DB_RES_CODE_1;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_2;
	}

	public List<PrintLog> selectPrintLogList(String userId, int startPoint, int endPoint) {
		try {
			PrintLog printLog = new PrintLog();
			printLog.setUserId(userId);
			printLog.setPrintStartPoint(startPoint);
			printLog.setPrintEndPoint(endPoint-startPoint);
			List<PrintLog> list = sqlSession.selectList("printLog.selectPrintLogList", printLog);
			return list;
		} catch (PersistenceException e) {
			return new ArrayList<PrintLog>();
		}
	}
}
