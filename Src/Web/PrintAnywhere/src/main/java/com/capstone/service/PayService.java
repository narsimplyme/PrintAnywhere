package com.capstone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.dto.Pay;
import com.capstone.model.PayDao;

@Service
public class PayService {

	private PayDao payDao;
	
	@Autowired
	public void setPayDao(PayDao payDao) {
		this.payDao = payDao;
	}


	public List<Pay> selectPayList(String userId, int startPoint, int endPoint) {
		return payDao.selectPayList(userId, startPoint, endPoint);
	}


	public int insertPay(Pay pay) {
		return payDao.insertPay(pay);
	}

}
