package com.capstone.printanywhere;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.capstone.dto.File;
import com.capstone.service.FileService;
import com.capstone.service.UserService;
import com.capstone.util.Response;
import com.capstone.util.UploadFile;

@Controller
public class FileController {
	
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
		Response res = new Response();
		String token = request.getHeader("x-access-token");
		boolean authResult = userService.isAuth(token);
		if(authResult) {
			res.setSuccess(true);
			ArrayList<File> fileArray = new ArrayList<>();
			String userId = "userId값 구하자";
			fileArray = fileService.selectFileList(userId , sizeOfList);
			res.setFileArray(fileArray);	
		}else {
			res.setSuccess(false);
		}
		return res;
	}
	
	@RequestMapping(value = "fileDelete.do",  method = RequestMethod.GET)
	@ResponseBody
	public Response fileDelete(HttpServletRequest request) {
		String token = request.getHeader("x-access-token");
		boolean authResult = userService.isAuth(token);
		Response res = new Response();
		if(authResult) {
			//인증 성공했을경우 파일 삭제
		}else {
			res.setErrors("잘못된 접근입니다.");
		}
		return res;
	}
	
	@RequestMapping(value = "fileUpload.do",  method = RequestMethod.POST)
	@ResponseBody
	public int fileUpload(HttpSession session, MultipartHttpServletRequest request,
	        MultipartFile uploadFile) {
		if(uploadFile == null) {
			//null일 경우 처리
		}else {
			String token = request.getHeader("x-access-token");
			boolean authResult = userService.isAuth(token);
			if(authResult) {
				UploadFile uf = new UploadFile();
				File file = uf.uploadFile(session, request, uploadFile);
				fileService.insertFile(file);
			}else {
				//인증이 안됐을 경우
			}
		}
		
		return 101;
	}

}
