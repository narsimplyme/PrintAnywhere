package com.capstone.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capstone.dto.PrintLog;
import com.capstone.dto.Reserve;
import com.capstone.util.Constants;

@Repository
public class ReserveDao {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public int insertReserve(Reserve reserve) {
		try {
			int count = sqlSession.insert("reserve.insertReserve", reserve);
			if(count > 0) {
				return Constants.DB_RES_CODE_1;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_2;
	}

	public List<Reserve> selectReserveList(String userId) {
		try {
			List<Reserve> list = sqlSession.selectList("reserve.selectReserveList", userId);
			return list;
		} catch (PersistenceException e) {
			return new ArrayList<Reserve>();
		}
	}

	public int updateReserve(Reserve reserve) {
		try {
			int count = sqlSession.update("reserve.updateReserve", reserve);
			if(count > 0) {
				return Constants.DB_RES_CODE_5;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_6;
	}

	public int deleteReserve(int reserveId) {
		try {
			int count = sqlSession.delete("reserve.deleteReserve", reserveId);
			if(count > 0) {
				return Constants.DB_RES_CODE_7;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_8;
	}

	public List<Reserve> reserveFileForClient(int clientId) {
		try {
			List<Reserve> list = sqlSession.selectList("reserve.reserveFileForClient", clientId);
			return list;
		} catch (PersistenceException e) {
			return new ArrayList<Reserve>();
		}
	}
}
