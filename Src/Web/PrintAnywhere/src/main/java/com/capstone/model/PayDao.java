package com.capstone.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capstone.dto.Pay;
import com.capstone.util.Constants;

@Repository
public class PayDao {
	@Autowired
	private DataSource dataSource;
	private SqlSession sqlSession;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List<Pay> selectPayList(String userId, int startPoint, int endPoint) {
		try {
			Pay pay = new Pay();
			pay.setUserId(userId);
			pay.setPayStartPoint(startPoint);
			pay.setPayEndPoint(endPoint-startPoint);
			List<Pay> list = sqlSession.selectList("pay.selectPayList", pay);
			return list;
		} catch (PersistenceException e) {
			return new ArrayList<Pay>();
		}
	}

	public int insertPay(Pay pay) {
		try {
			int count = sqlSession.insert("pay.insertPay", pay);
			if(count > 0) {
				return Constants.DB_RES_CODE_1;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_2;
	}
	
	

}
