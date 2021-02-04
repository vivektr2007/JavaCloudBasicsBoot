package com.pixel.model;

import java.net.URL;

public class S3ObjectModel {

	private String bucketName;
	private String key;
	private String thumbkey;
	private URL generatedSignedURL;
	private URL generatedSignedURLSmall;
	private int size;
	
	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public URL getGeneratedSignedURL() {
		return generatedSignedURL;
	}

	public void setGeneratedSignedURL(URL url) {
		this.generatedSignedURL = url;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public URL getGeneratedSignedURLSmall() {
		return generatedSignedURLSmall;
	}

	public void setGeneratedSignedURLSmall(URL generatedSignedURLSmall) {
		this.generatedSignedURLSmall = generatedSignedURLSmall;
	}

	public String getThumbkey() {
		return thumbkey;
	}

	public void setThumbkey(String thumbkey) {
		this.thumbkey = thumbkey;
	}

}
