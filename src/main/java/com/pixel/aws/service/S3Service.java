package com.pixel.aws.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.pixel.model.S3ObjectModel;

public interface S3Service {

	public ArrayList<S3ObjectModel> getBucketObjectURLList(String bucketName, String firstFolder);
	
	public String uploadS3File(MultipartFile file, String contentType) throws IOException ;
		
}
