package com.pixel.aws.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.pixel.aws.service.AWSService;

@PropertySource("classpath:/config.properties")
@Service
public class AWSServiceImpl implements AWSService {

	// private final static Logger log = Logger.getLogger(AWSServiceImpl.class);

	@Autowired
	Environment env;

	public AWSCredentials getConnection() {

		String tmpAccessKey = env.getProperty("awsAccessKey");
		String tmpSecretKey = env.getProperty("awsSecretKey");


		return new BasicAWSCredentials(tmpAccessKey, tmpSecretKey);

	}

	public ClientConfiguration getClientConfiguration(boolean isProxy) {

		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);

		if (isProxy) {
			clientConfig.setProxyHost(env.getProperty("proxyIp"));
			clientConfig.setProxyPort((Integer.parseInt(env.getProperty("proxyPort"))));
		}
		return clientConfig;

	}

}
