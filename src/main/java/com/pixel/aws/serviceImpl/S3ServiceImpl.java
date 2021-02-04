package com.pixel.aws.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.pixel.aws.service.AWSService;
import com.pixel.aws.service.S3Service;
import com.pixel.model.S3ObjectModel;
import com.pixel.utils.CommonUtils;

@Service
public class S3ServiceImpl implements S3Service {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(S3ServiceImpl.class);

	@Autowired
	AWSService awsService;

	@Autowired
	Environment env;

	@SuppressWarnings("deprecation")
	public String uploadS3File(MultipartFile file, String contentType) throws IOException {

		String bucketImageFolderName = env.getProperty("bucketImageFolderName");
		String bucketVideoFolderName = env.getProperty("bucketVideoFolderName");
		String bucketFileFolderName = env.getProperty("bucketFileFolderName");
		String bucketName = env.getProperty("bucketName");
		boolean proxySet = Boolean.parseBoolean(env.getProperty("proxySet"));
		
		String fileName = file.getOriginalFilename();

		fileName = CommonUtils.generateFileNameForS3(fileName);
		String awsEndPoint = env.getProperty("awsEndPoint");
		String fullPath = "";
		String fullBucketPath = "";

		try {

			AmazonS3 s3client = new AmazonS3Client(awsService.getConnection(), awsService.getClientConfiguration(proxySet));
			s3client.setEndpoint(awsEndPoint);

			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(file.getContentType());
			meta.setContentLength(file.getSize());
			meta.setHeader("filename", fileName);

			ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());

			TransferManager transferManager = new TransferManager(s3client);
			// transferManager.upload(bucketName, fileName, bis, meta);

			if ("Image".equalsIgnoreCase(contentType)) {

				fullBucketPath = bucketName + bucketImageFolderName;

				Upload upload = transferManager.upload(new PutObjectRequest(fullBucketPath, fileName, bis, meta)
						.withCannedAcl(CannedAccessControlList.PublicRead));
				upload.waitForCompletion();
				
				LOGGER.info("Method : uploadS3File : Image"+" Upload Completed");

			} else if ("Video".equalsIgnoreCase(contentType)) {

				fullBucketPath = bucketName + bucketVideoFolderName;

				Upload upload = transferManager.upload(new PutObjectRequest(fullBucketPath, fileName, bis, meta)
						.withCannedAcl(CannedAccessControlList.PublicRead));
				
				UploadResult result = upload.waitForUploadResult();
				
				LOGGER.info("Method : uploadS3File : Video"+" Upload Completed  "+result);
				

			} else if ("File".equalsIgnoreCase(contentType)) {

				fullBucketPath = bucketName + bucketFileFolderName;

				Upload upload = transferManager.upload(new PutObjectRequest(fullBucketPath, fileName, bis, meta)
						.withCannedAcl(CannedAccessControlList.PublicRead));
				
				UploadResult result = upload.waitForUploadResult();
				
				LOGGER.info("Method : uploadS3File : File"+" Upload Completed "+result);
				
			}

			fullPath = "/" + fullBucketPath + "/" +fileName;
			
			LOGGER.info("Method : uploadS3File : "+fullPath);

		} catch (AmazonS3Exception e) {
			LOGGER.error("In uploadS3File Function::",e);
		} catch (AmazonServiceException e) {
			LOGGER.error("In uploadS3File Function::",e);
		} catch (AmazonClientException e) {
			LOGGER.error("In uploadS3File Function::",e);
		} catch (InterruptedException e) {
			LOGGER.error("In uploadS3File Function::",e);
		}

		return fullPath;
	}

	@SuppressWarnings("deprecation")
	public ArrayList<S3ObjectModel> getBucketObjectURLList(String bucketName, String firstFolder) {

		ObjectListing objectListing = null;
		S3ObjectModel objS3 = null;
		ArrayList<S3ObjectModel> arr = null;

		try {

			AmazonS3 s3client = new AmazonS3Client(awsService.getConnection(), awsService.getClientConfiguration(true));
			s3client.setEndpoint("s3-ap-southeast-1.amazonaws.com");

			ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName);
			arr = new ArrayList<S3ObjectModel>();

			do {
				objectListing = s3client.listObjects(listObjectsRequest);
				for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
					if (objectSummary.getSize() != 0 && objectSummary.getKey().startsWith(firstFolder)) {
						objS3 = new S3ObjectModel();
						objS3.setBucketName(bucketName);
						objS3.setKey(objectSummary.getKey());
						String thumbUrl = CommonUtils.thumbUrl(objectSummary.getKey(), "TH");
						objS3.setThumbkey(thumbUrl);
						objS3.setSize((int) objectSummary.getSize());

						GeneratePresignedUrlRequest request1 = new GeneratePresignedUrlRequest(bucketName, thumbUrl);
						request1.withMethod(HttpMethod.GET);
						objS3.setGeneratedSignedURLSmall(s3client.generatePresignedUrl(request1));

						GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName,
								objectSummary.getKey());
						request.withMethod(HttpMethod.GET);
						objS3.setGeneratedSignedURL(s3client.generatePresignedUrl(request));

						arr.add(objS3);
					} else {
					}
				}
				listObjectsRequest.setMarker(objectListing.getNextMarker());
			} while (objectListing.isTruncated());

		} catch (AmazonS3Exception e) {
			LOGGER.error("In getBucketObjectURLList Function::",e);
		}

		return arr;
	}

}
