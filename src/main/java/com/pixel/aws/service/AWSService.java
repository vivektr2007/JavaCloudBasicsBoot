package com.pixel.aws.service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;

public interface AWSService {
	
	public AWSCredentials getConnection();
	
	public ClientConfiguration getClientConfiguration(boolean isProxy);

}
