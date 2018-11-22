package com.capstone.printanywhere;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.capstone.dto.File;
import com.capstone.dto.Token;
import com.capstone.service.FileService;
import com.capstone.service.UserService;
import com.capstone.util.AuthToken;
import com.capstone.util.Constants;
import com.capstone.util.Response;
import com.capstone.util.UploadFile;

@Controller
public class FileController {
	
	Response res;
	JSONObject data;

	private FileService fileService;
	private UserService userService;
		
	@Autowired
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	@RequestMapping(value = "fileList.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response fileList(int sizeOfList, HttpServletRequest request) {
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			List<File> fileArray = new ArrayList<>();
			fileArray = fileService.selectFileList(authResult.getUserId() , sizeOfList);
			data.put("fileList", fileArray);
			res.setData(data);
		}
		return res;
	}
	
	@RequestMapping(value = "fileDelete.do",  method = RequestMethod.DELETE)
	@ResponseBody
	public Response fileDelete(HttpServletRequest request, @RequestBody File file) {
		res = new Response();
		int fileId = file.getFileId();
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		res = AuthToken.isOk(authResult);
		if(res.isSuccess()) {
			int resCode = fileService.deleteFile(fileId);
			if(resCode == Constants.DB_RES_CODE_7) {
				res.setSuccess(true);
				res.setMessage(Constants.MSG_CODE_200);
			}else {
				res.setSuccess(false);
				if(resCode == Constants.DB_RES_CODE_9) {
					res.setMessage(Constants.MSG_CODE_106);
					res.setErrors(Constants.ERROR_CODE_4);
				}else { 
					res.setMessage(Constants.MSG_CODE_305);				
				}
			}
		}
		return res;
	}
	
	@RequestMapping(value = "fileUpload.do",  method = RequestMethod.POST)
	@ResponseBody
	public Response fileUpload(HttpSession session, MultipartHttpServletRequest request) {
		List<MultipartFile> fileList = request.getFiles("uploadFile");
		if(fileList.size() == 0) {
			res.setSuccess(false);
			res.setMessage(Constants.MSG_CODE_306);
			//null일 경우 처리
		}else {
			res = new Response();
			String tokenId = request.getHeader("x-access-token");
			Token authResult = userService.isAuth(tokenId);
			res = AuthToken.isOk(authResult);
			if(res.isSuccess()) {
				for (int i = 0; i < fileList.size(); i++) {
					UploadFile uf = new UploadFile();
					File file = uf.uploadFile(session, request, fileList.get(i));
					if(file == null) {
						res.setSuccess(false);
						res.setMessage(Constants.MSG_CODE_111);
					}else {
						res.setSuccess(true);
						file.setUserId(authResult.getUserId());
						int resCode = fileService.insertFile(file);
					}	
				}				
			}
		}
		return res;
	}
		
	
	@RequestMapping(value = "fileDownload.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response fileDownload(HttpSession session) {
		String uploadPath = session.getServletContext().getRealPath("/");
	    String attachPath = "resources/file/";
		String savePath = uploadPath+attachPath+"test.pdf";
		data = new JSONObject();
		res = new Response();
		data.put("fileUrl", savePath);
        res.setData(data);
		return res;
	}

	private String getSaveLocation(MultipartHttpServletRequest request) {
        String uploadPath = request.getSession().getServletContext().getRealPath("/");
        String attachPath = "resources/file/";  
        return uploadPath + attachPath;
	}
	
	
	
	
	
	
	
	// 테스트
	
	@RequestMapping(value = "fileUploadTest.do",  method = RequestMethod.POST)
	@ResponseBody
	public Response fileUploadTest(HttpSession session, MultipartHttpServletRequest request) {
		List<MultipartFile> fileList = request.getFiles("uploadFile");
		if(fileList.size() == 0) {
			res.setSuccess(false);
			res.setMessage(Constants.MSG_CODE_306);
			//null일 경우 처리
		}else {
			res = new Response();
			String tokenId = request.getHeader("x-access-token");
			if(true) {
			/*Token authResult = userService.isAuth(tokenId);
			if(authResult != null) {*/
				for (int i = 0; i < fileList.size(); i++) {
					UploadFile uf = new UploadFile();
					File file = uf.uploadFile(session, request, fileList.get(i));
					if(file == null) {
						res.setSuccess(false);
						res.setMessage(Constants.MSG_CODE_306);
					}else {
						res.setSuccess(true);
						//file.setUserId(authResult.getUserId());
						//int resCode = fileService.insertFile(file);
					}	
				}				
			}else {
				//인증이 안됐을 경우
				res.setSuccess(false);
				res.setErrors(Constants.ERROR_CODE_7);
				res.setMessage(Constants.MSG_CODE_110);
			}
		}
		return res;
	}

}
