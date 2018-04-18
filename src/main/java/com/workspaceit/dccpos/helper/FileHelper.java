package com.workspaceit.dccpos.helper;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileHelper {
	
	
	public static String getMimeType(MultipartFile multipartFile){
        String mimeType = multipartFile.getContentType();

        if(mimeType==null || mimeType.equals("")){
            mimeType = FileHelper.getMimeType(multipartFile.getOriginalFilename());
        }
        return mimeType;
    }
	public static String getMimeType(String originalFilename){
		String mimeType =  URLConnection.guessContentTypeFromName(originalFilename);

		if(mimeType==null || mimeType.equals("")){
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			mimeType = fileNameMap.getContentTypeFor(originalFilename);
		}


		return mimeType;
	}
    public static String getMimeType(File file){
        String mimeType = "";
        try {
            mimeType =  Files.probeContentType(file.toPath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return mimeType;
    }
    public static File getFile(String filePath){
        return new File(filePath);
    }
	public static String getExtension(MultipartFile multipartFile) {
		String oiginalFilename = multipartFile.getOriginalFilename();
		String fileExtensions= "";
		if(oiginalFilename!=null) {
			try {
				System.out.println(Arrays.toString(oiginalFilename.split(".")));
				fileExtensions = oiginalFilename.substring(oiginalFilename.indexOf(".")+1);
			}catch(StringIndexOutOfBoundsException ex) {
				System.out.println("Can't retrive extention from "+oiginalFilename);
				fileExtensions= "";
			}
			
		}
		return fileExtensions;
	}
	public static long getMBtoByte(int mb) {
		return mb *(1024*1000);
	}
	public static long getByteToMb(long bytes) {
		return bytes / (long)(1024*1000);
	}

	public static void main(String[] args) {
		String e = FileHelper.getMimeType(FileHelper.getFile("/home/mi_rafi/Videos/a.mp4"));
        try {
            System.out.println(Files.probeContentType(Paths.get("/home/mi_rafi/Videos/big_buck_bunny_720p_1mb.flv")));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}