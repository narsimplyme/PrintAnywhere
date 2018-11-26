package com.capstone.model;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capstone.dto.BookMark;
import com.capstone.dto.File;
import com.capstone.util.Constants;

@Repository
public class BookMarkDao {

	private SqlSession sqlSession;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int insertBookMark(String userId, int clientId) {
		try {
			BookMark bookMark = new BookMark(userId, clientId);
			int count = sqlSession.insert("bookmark.insertBookMark", bookMark);
			if(count > 0) {
				return Constants.DB_RES_CODE_1;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_2;
	}

	public List<BookMark> selectBookMarkList(String userId) {
		try {
			List<BookMark> list = sqlSession.selectList("bookmark.selectBookMarkList", userId);
			return list;
		} catch (PersistenceException e) {
			return null;
		}
	}

	public int deleteBookMark(String userId, int clientId) {
		try {
			BookMark bookMark = new BookMark(userId, clientId);
			int count = sqlSession.delete("bookMark.deleteBookMark", bookMark);
			if(count > 0) {
				return Constants.DB_RES_CODE_7;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_8;
	}

}
