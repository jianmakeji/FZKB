package com.jianma.fzkb.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ResourceBundle;

import com.aliyun.oss.OSSClient;

public class UploadOSSUtil {
	
	public void uploadImgAliyun(InputStream inputStream, String fileName) throws FileNotFoundException {
		ResourceBundle resource = ResourceBundle.getBundle("config");
		String accesskeyId = resource.getString("accessId");
		String accessKeySecret = resource.getString("accessKey");
		String endpoint = resource.getString("endpoint");
		String bucketName = resource.getString("bucket");
		OSSClient client = new OSSClient(endpoint, accesskeyId, accessKeySecret);
		client.putObject(bucketName, "article/" + fileName, inputStream);
		client.shutdown();
	}
}
