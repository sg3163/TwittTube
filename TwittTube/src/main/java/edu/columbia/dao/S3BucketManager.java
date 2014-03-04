package edu.columbia.dao;

import java.io.File;
import com.amazonaws.auth.*;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3BucketManager {
	AmazonS3Client s3;
	String bucket_name = null;

	public S3BucketManager()
	{
		this.s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
		this.bucket_name = "twitttubebucket";
	}

	public S3BucketManager(AmazonS3Client s3Instance, String bucketName)
	{
		this.s3 = s3Instance;
		this.bucket_name = bucketName;
	}

	public void createBucket()
	{
		//create bucket
		s3.createBucket(bucket_name);
	}
	
	public void putObject(String key, File file)
	{		
		try 
		{
			//put object - bucket, key, value(file)
			s3.putObject(new PutObjectRequest(bucket_name, key, file).withCannedAcl(CannedAccessControlList.PublicRead));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
