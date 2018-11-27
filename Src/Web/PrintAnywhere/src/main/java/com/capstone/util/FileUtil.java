package com.capstone.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUtil {
	
	public com.capstone.dto.File uploadFile(HttpSession session, MultipartHttpServletRequest request, MultipartFile uploadFile) {
		com.capstone.dto.File f;
		String path = "";
        String fileName = "";
        String fileOriginName = uploadFile.getOriginalFilename();
        int expender = fileOriginName.lastIndexOf(".");
        OutputStream out = null;
        PrintWriter printWriter = null;
        String hashFileName = AuthToken.getToken(fileOriginName)+fileOriginName.substring(expender, fileOriginName.length());
        System.out.println("fileOriginName: "+fileOriginName+" / "
        +"getContentType: "+uploadFile.getContentType()
        +" / "+"getSize: "+uploadFile.getSize()+"hashFileName: "+hashFileName+"??"+expender);
        fileName = "";
        try {
            String filePath = getSaveLocation(request)+hashFileName;
			File file = new File(filePath);  
            out = new FileOutputStream(file);  
            byte[] bytes = uploadFile.getBytes();
            out.write(bytes);
        }catch (Exception e) {
                e.printStackTrace();
                return null;
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
        f = new com.capstone.dto.File(0, hashFileName, fileOriginName, 0, dateFormat.format(date), ""+uploadFile.getSize(), "");
		return f;
	}
	
	public int deleteFile(HttpServletRequest request, com.capstone.dto.File f) {
        String filePath = getSaveLocationDelete(request) + f.getFileHash();
        System.out.println(filePath);
		File file = new File(filePath); 
		if( file.exists() ){ 
			if(file.delete()){ 
				System.out.println("파일삭제 성공"); 
			}else{ 
				System.out.println("파일삭제 실패"); 
				} 
			}else{ 
				System.out.println("파일이 존재하지 않습니다."); 
			}

		return 0;	
	}
	
	private String getSaveLocation(MultipartHttpServletRequest request) {
        String uploadPath = request.getSession().getServletContext().getRealPath("/");
        String attachPath = "resources/file/";        
        return uploadPath + attachPath;
	}
	
	private String getSaveLocationDelete(HttpServletRequest request) {
        String uploadPath = request.getSession().getServletContext().getRealPath("/");
        String attachPath = "resources/file/";        
        return uploadPath + attachPath;
	}
}
