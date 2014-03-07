package edu.columbia.dao;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.amazonaws.services.elastictranscoder.model.Preset;

public class ElasticTranscoderTestingDao {
	
	public static void main(String args[]) {
		// Create AWS job to upload transcode uploaded video in iphone/ipad format
		AmazonElasticTranscoderClient etc = new AmazonElasticTranscoderClient(new ClasspathPropertiesFileCredentialsProvider());
		
		CreateJobRequest jobReq = new CreateJobRequest();
		
		jobReq.setPipelineId("iphonevideoconverter");
		
		JobInput input = new JobInput();
		input.setKey("small.mp4");
		jobReq.setInput(input);
		
		CreateJobOutput output = new CreateJobOutput();
		output.setKey("small.mp4");
		output.setPresetId("iPhone4S");
		
		jobReq.setOutput(output);
		
		Preset preset = new Preset();
		preset.setType("iPhone4S");
		
		etc.createJob(jobReq);
	}
}
