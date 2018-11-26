package com.capstone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.dto.BookMark;
import com.capstone.model.BookMarkDao;

@Service
public class BookMarkService {
	
	private BookMarkDao bookMarkDao;
	
	@Autowired
	public void setBookMarkDao(BookMarkDao bookMarkDao) {
		this.bookMarkDao = bookMarkDao;
	}



	public int insertBookMark(String userId, int client) {
		return bookMarkDao.insertBookMark(userId, client);
	}



	public List<BookMark> selectBookMarkList(String userId) {
		return bookMarkDao.selectBookMarkList(userId);
	}



	public int deleteBookMark(String userId, int clientId) {
		return bookMarkDao.deleteBookMark(userId, clientId);
	}

}
