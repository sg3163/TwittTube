package edu.columbia.dao;

import java.util.List;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoder;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.CreatePipelineRequest;
import com.amazonaws.services.elastictranscoder.model.CreatePipelineResult;
import com.amazonaws.services.elastictranscoder.model.DeletePipelineRequest;
import com.amazonaws.services.elastictranscoder.model.Job;
import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.amazonaws.services.elastictranscoder.model.JobOutput;
import com.amazonaws.services.elastictranscoder.model.ListJobsByPipelineRequest;
import com.amazonaws.services.elastictranscoder.model.ListJobsByPipelineResult;
import com.amazonaws.services.elastictranscoder.model.ListPipelinesRequest;
import com.amazonaws.services.elastictranscoder.model.ListPipelinesResult;
import com.amazonaws.services.elastictranscoder.model.Pipeline;
import com.amazonaws.services.elastictranscoder.model.Preset;
import com.amazonaws.services.elastictranscoder.model.ReadPipelineRequest;
import com.amazonaws.services.elastictranscoder.model.TestRoleRequest;

public class ElasticTranscoderTestingDao {
	
	public static void main(String args[]) {
		// Create AWS job to upload transcode uploaded video in iphone/ipad format
		AmazonElasticTranscoderClient etc = new AmazonElasticTranscoderClient(new ClasspathPropertiesFileCredentialsProvider());
		
		
		// Get the pipeline Created in AWS with name iphonevideoconverter
		ListPipelinesRequest listPipelineRequest = new ListPipelinesRequest();
		ListPipelinesResult listPipelineResult = etc.listPipelines(listPipelineRequest);
		
		Pipeline pipeline = null;
		
		for (Pipeline pip : listPipelineResult.getPipelines()) {
			if(pip.getName().equalsIgnoreCase("iphonevideoconverter")) {
				pipeline = pip;
				break;
			}
		}
		
		
		CreateJobRequest jobReq = new CreateJobRequest();
		
		jobReq.setPipelineId(pipeline.getId());
		
		JobInput input = new JobInput();
		input.setKey("small.mp4");
		jobReq.setInput(input);
		
		
		CreateJobOutput output = new CreateJobOutput();
		output.setKey("small.mp4");
		output.setPresetId("1351620000001-100020");
		
		jobReq.setOutput(output);
		etc.createJob(jobReq);
		
	}
	
/*	public static void main(String args[]) {
		// Create AWS job to upload transcode uploaded video in iphone/ipad format
		AmazonElasticTranscoderClient etc = new AmazonElasticTranscoderClient(new ClasspathPropertiesFileCredentialsProvider());
		dispPiplineList(etc);
	}*/
	
	public static void dispPiplineList(AmazonElasticTranscoder transCoder) {
		ListPipelinesRequest listPipelineRequest = new ListPipelinesRequest();
		ListPipelinesResult listPipelineResult = transCoder.listPipelines(listPipelineRequest);
		List<Pipeline> pipelines = listPipelineResult.getPipelines();
		
		System.out.println("----------Pipeline List----------");
		for (Pipeline pipeline : pipelines) {
			System.out.println("Id : " + pipeline.getId());
			System.out.println("  Name : " + pipeline.getName());
			System.out.println("  Role : " + pipeline.getRole());
			ListJobsByPipelineRequest listJobsByPipelineRequest = new ListJobsByPipelineRequest();
			listJobsByPipelineRequest.setPipelineId(pipeline.getId());
			ListJobsByPipelineResult listJobsByPipelineResult = transCoder
					.listJobsByPipeline(listJobsByPipelineRequest);
			List<Job> jobs = listJobsByPipelineResult.getJobs();
			for (Job job : jobs) {
				System.out.println("  Id : " + job.getId());
				JobInput jobInput = job.getInput();
				System.out.println("    JobInput");
				System.out.println("      AspectRatio : "
						+ jobInput.getAspectRatio());
				System.out.println("      Container : "
						+ jobInput.getContainer());
				System.out.println("      FrameRate : "
						+ jobInput.getFrameRate());
				System.out.println("      Interlaced : "
						+ jobInput.getInterlaced());
				System.out.println("      Key : " + jobInput.getKey());
				System.out.println("      Resolution : "
						+ jobInput.getResolution());
				JobOutput jobOutput = job.getOutput();
				System.out.println("    JobOutput");
				System.out.println("      Key : " + jobOutput.getKey());
				System.out.println("      PresetId : "
						+ jobOutput.getPresetId());
				System.out.println("      Rotate : " + jobOutput.getRotate());
				System.out.println("      Status : " + jobOutput.getStatus());
				System.out.println("      StatusDetail : "
						+ jobOutput.getStatusDetail());
				System.out.println("      ThumbnailPattern : "
						+ jobOutput.getThumbnailPattern());
			}
		}
	}
}
