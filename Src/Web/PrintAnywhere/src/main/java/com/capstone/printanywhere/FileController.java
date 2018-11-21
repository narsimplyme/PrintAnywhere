package com.capstone.printanywhere;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.capstone.dto.File;
import com.capstone.dto.Token;
import com.capstone.service.FileService;
import com.capstone.service.UserService;
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
		res = new Response();
		data = new JSONObject();
		
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		if(authResult != null) {
			if(authResult.isToken()) {
				if(authResult.isTTL()) {
					res.setSuccess(true);
					ArrayList<File> fileArray = new ArrayList<>();
					String userId = "admin";
					fileArray = fileService.selectFileList(userId , sizeOfList);
					data.put("fileList", fileArray);
					res.setData(data);
				}else {
					//ttl이 없을경우
					res.setSuccess(false);
					res.setErrors(Constants.ERROR_CODE_5);
					res.setMessage(Constants.MSG_CODE_108);
				}
			}else {
				res.setSuccess(false);
				res.setErrors(Constants.ERROR_CODE_5);
				res.setMessage(Constants.MSG_CODE_107);
				//token이 없을경우
			}
		}else {
			res.setSuccess(false);
			res.setErrors(Constants.ERROR_CODE_7);
			res.setMessage(Constants.MSG_CODE_110);
		}
		return res;
	}
	
	@RequestMapping(value = "fileDelete.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response fileDelete(HttpServletRequest request) {
		res = new Response();
		String tokenId = request.getHeader("x-access-token");
		Token authResult = userService.isAuth(tokenId);
		if(authResult != null) {
			//인증 성공했을경우 파일 삭제
		}else {
			res.setSuccess(false);
			res.setErrors(Constants.ERROR_CODE_7);
			res.setMessage(Constants.MSG_CODE_110);
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
			if(true) {
			/*Token authResult = userService.isAuth(tokenId);
			if(authResult != null) {*/
				for (int i = 0; i < fileList.size(); i++) {
					UploadFile uf = new UploadFile();
					File file = uf.uploadFile(session, request, fileList.get(i));
					if(file == null) {
						res.setSuccess(false);
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

}
