package com.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.model.PrintLogDao;

@Service
public class PrintLogService {
	
	private PrintLogDao printLogDao;

	@Autowired
	public void setPrintLogDao(PrintLogDao printLogDao) {
		this.printLogDao = printLogDao;
	}
	
	
}
