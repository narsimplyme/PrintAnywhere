package com.capstone.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.dto.File;
import com.capstone.model.FileDao;

@Service
public class FileService {
	
	private FileDao fileDao;
	
	@Autowired
	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public ArrayList<File> selectFileList(String userId, int sizeOfList) {
		return fileDao.selectFileList(userId, sizeOfList);
	}

	public void insertFile(File file) {
		// TODO Auto-generated method stub
		
	}

}
