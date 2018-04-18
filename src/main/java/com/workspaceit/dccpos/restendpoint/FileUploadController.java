package com.workspaceit.dccpos.restendpoint;


import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.TempFile;
import com.workspaceit.dccpos.helper.FileHelper;
import com.workspaceit.dccpos.service.TempFileService;
import com.workspaceit.dccpos.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/upload/temp-file")
public class FileUploadController {
	private TempFileService tempFileService;
    private Set<String> imgAllowedMimeType;

    @Autowired
    public void setTempFileService(TempFileService tempFileService) {
        this.tempFileService = tempFileService;
    }

    @PostConstruct
    private void initConfiguration(){
        imgAllowedMimeType = new HashSet<>();

        /**
         * Image mime type
         * */
        imgAllowedMimeType.add("image/jpeg");
        imgAllowedMimeType.add("image/pjpeg");
        imgAllowedMimeType.add("image/jpeg");
        imgAllowedMimeType.add("image/png");


    }

    public FileUploadController(){}

    @RequestMapping(value="/product-image",headers="Content-Type=multipart/form-data",method=RequestMethod.POST)
    public ResponseEntity<?> uploadPicture(@RequestParam("file") MultipartFile multipartFile) {
        long fileSizeLimit;
        Set<String> imgContentType;
        fileSizeLimit = FileHelper.getMBtoByte(1) ;// 1 MB
        imgContentType = this.imgAllowedMimeType;


        return validateAndProcessMultiPart(  "file",fileSizeLimit,multipartFile,imgContentType);
    }


    private ResponseEntity<?>   validateAndProcessMultiPart(  String param,
                                                            long fileSizeLimit,
                                                            MultipartFile multipartFile,
                                                            Set<String> imgContentType
                                                         ){
        String mimeType = FileHelper.getMimeType(multipartFile);


        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        if(!imgContentType.contains(mimeType)) {
            serviceResponse.setValidationError(param," Mime Type "+ mimeType+" not allowed");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        if(multipartFile==null || multipartFile.getSize()==0){
            serviceResponse.setValidationError(param,"No file receive");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }


        if(multipartFile.getSize()>fileSizeLimit){
            serviceResponse.setValidationError(param,"File size exceeds. Max size "+FileHelper.getByteToMb(fileSizeLimit));
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        TempFile tempfile;
        try {
            tempfile  = this.tempFileService.saveTempFile(multipartFile);

        } catch(IOException e) {
            serviceResponse.setValidationError(param,"Internal server error : "+e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }


        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tempfile);
    }

    @RequestMapping(value="/remove",method=RequestMethod.POST)
	public ResponseEntity<?> removePicture(@RequestParam("token") Integer token) {


		ServiceResponse serviceResponse = ServiceResponse.getInstance();
		if(token==null) {
			serviceResponse.setValidationError("token","Token Required");
			ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
		}
		this.tempFileService.removeTempFile(token);
        serviceResponse.setMsg("token",token);
        serviceResponse.setMsg("msg","Successfully removed");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(serviceResponse.getMsg());
	}
    
}