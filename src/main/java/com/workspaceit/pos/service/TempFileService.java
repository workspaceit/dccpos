package com.workspaceit.pos.service;

import com.workspaceit.pos.config.Environment;

import com.workspaceit.pos.constant.FILE;
import com.workspaceit.pos.dao.TempFileDao;
import com.workspaceit.pos.entity.TempFile;
import com.workspaceit.pos.helper.FileHelper;
import com.workspaceit.pos.helper.TokenGenerator;
import com.workspaceit.pos.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TempFileService {
	private FileUtil fileUtil;
	private TempFileDao tempFileDao;
	private TempFileService tempFileService;
	private Environment env;



	@Autowired
    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Autowired
    public void setTempFileDao(TempFileDao tempFileDao) {
        this.tempFileDao = tempFileDao;
    }

	@Autowired
	public void setTempFileService(TempFileService tempFileService) {
		this.tempFileService = tempFileService;
	}

	@Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }




	@Transactional(rollbackFor = Exception.class)
	public TempFile saveTempFile(MultipartFile multipartFile) throws IOException{

		byte[] fileByte = multipartFile.getBytes();
		String fileExtension = FileHelper.getExtension(multipartFile);
		Map<FILE,String> fileInf =  this.fileUtil.saveFileInFolder(fileByte, fileExtension);
		String fileName = fileInf.get(FILE.NAME);
		String filePath = fileInf.get(FILE.PATH);

		int token = TokenGenerator.generateTempFileToken();

		TempFile tempFile = new TempFile();
		tempFile.setFileName(fileName);
		tempFile.setPath(filePath);
		tempFile.setToken(token);

		tempFileDao.insert(tempFile);
		return tempFile;
	}

	@Transactional(rollbackFor = Exception.class)
	public void removeTempFile(Integer token){
		TempFile tempFile = tempFileDao.getByToken(token);
		if(tempFile==null){
			return;
		}
		this.tempFileDao.delete(tempFile);
		this.fileUtil.deleteFile(tempFile.getPath());
	}


	@Transactional(rollbackFor = Exception.class)
	public String copyFileToCommonFolder(Integer token){
		String fileName = "";
		if(token==null){
			return "";
		}
		TempFile tempFile = tempFileDao.getByToken(token);
		if(tempFile==null){
			return fileName;
		}
		try {
			fileName = this.fileUtil.copyFile(env.getCommonFilePath(),tempFile.getPath());
		} catch (IOException e) {
			return fileName;
		}
		return fileName;
	}

	@Transactional(rollbackFor = Exception.class)
	public String copyFileToCommonFolder(String fileAbsPath){
		String fileName = "";

		try {
			fileName = this.fileUtil.copyFile(env.getCommonFilePath()+System.nanoTime(),fileAbsPath);
		} catch (IOException e) {
			return fileName;
		}
		return fileName;
	}
	@Transactional
	public String getMimeTypeByToken(Integer token){
		String fileMimeType = "";
		if(token==null){
			return fileMimeType;
		}

		TempFile tempFile = tempFileDao.getByToken(token);

		if(tempFile==null){
			return fileMimeType;
		}
		try {
			File file = this.fileUtil.getTempFile(env.getTmpFilePath()+"/"+tempFile.getPath());
			fileMimeType =  FileHelper.getMimeType(file);
		} catch (IOException e) {
			return fileMimeType;
		}
		return fileMimeType;

	}
}