package com.capstone.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.dto.Reserve;
import com.capstone.model.ReserveDao;
import com.capstone.util.Response;

@Service
public class ReserveService {
	Response res;
	JSONObject data;
	
	private ReserveDao reserveDao;

	@Autowired
	public void setReserveDao(ReserveDao reserveDao) {
		this.reserveDao = reserveDao;
	}

	public int insertReserve(Reserve reserve) {
		return reserveDao.insertReserve(reserve);
	}

	public List<Reserve> selectReserveList(String userId) {
		return reserveDao.selectReserveList(userId);
	}

	public int updateReserve(Reserve reserve) {
		return reserveDao.updateReserve(reserve);
	}

	public int deleteReserve(int reserveId) {
		return reserveDao.deleteReserve(reserveId);
	}

	public List<Reserve> reserveFileForClient(int clientId) {
		return reserveDao.reserveFileForClient(clientId);
	}
	
	
}
