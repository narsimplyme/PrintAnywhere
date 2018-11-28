package com.capstone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.dto.PrintLog;
import com.capstone.model.PrintLogDao;

@Service
public class PrintLogService {
	
	private PrintLogDao printLogDao;

	@Autowired
	public void setPrintLogDao(PrintLogDao printLogDao) {
		this.printLogDao = printLogDao;
	}

	public int insertPrintLog(PrintLog printLog) {
		return printLogDao.insertPrintLog(printLog);
	}

	public List<PrintLog> selectPrintLogList(String userId, int startPoint, int endPoint) {
		return printLogDao.selectPrintLogList(userId, startPoint, endPoint);
	}
	
	
}
