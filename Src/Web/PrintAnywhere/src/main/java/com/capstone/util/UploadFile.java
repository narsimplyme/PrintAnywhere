package com.capstone.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadFile {
	
	public com.capstone.dto.File uploadFile(HttpSession session, MultipartHttpServletRequest request, MultipartFile uploadFile) {
		com.capstone.dto.File f;
		String path = "";
        String fileName = "";
        String fileOriginName = uploadFile.getOriginalFilename();
        OutputStream out = null;
        PrintWriter printWriter = null;
        System.out.println("fileOriginName: "+fileOriginName+" / "
        +"getContentType: "+uploadFile.getContentType()
        +" / "+"getSize: "+uploadFile.getSize());
        fileName = "";
        try {
            String filePath = "";
			//File file = new File(filePath);  
            //out = new FileOutputStream(file);  
            //byte[] bytes = uploadFile.getBytes();
            //out.write(bytes);
        }catch (Exception e) {
                e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //(int fileId, String fileHash, String fileName, String fileType, String fileDate, String fileSize, String userId)
        f = new com.capstone.dto.File(0, AuthToken.getToken(fileOriginName), fileOriginName, uploadFile.getContentType(), 
        		dateFormat.format(date), ""+uploadFile.getSize(), "");
		return f;
	}
}
