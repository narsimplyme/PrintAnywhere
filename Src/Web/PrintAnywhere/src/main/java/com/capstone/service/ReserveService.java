package com.capstone.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
}
