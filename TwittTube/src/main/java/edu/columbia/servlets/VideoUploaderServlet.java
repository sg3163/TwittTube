package edu.columbia.servlets;

import java.io.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.columbia.dao.*;
import edu.columbia.util.UAgentInfo;
import edu.columbia.vo.User;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobPlaylist;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.amazonaws.services.elastictranscoder.model.JobOutput;
import com.amazonaws.services.elastictranscoder.model.ListPipelinesRequest;
import com.amazonaws.services.elastictranscoder.model.ListPipelinesResult;
import com.amazonaws.services.elastictranscoder.model.Pipeline;
import com.amazonaws.services.elastictranscoder.model.Preset;
import com.oreilly.servlet.MultipartRequest;

public class VideoUploaderServlet extends HttpServlet {
			   
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VideoUploaderServlet() {
        super();
        System.out.println("Creating video uploader instance");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		S3BucketManager m = new S3BucketManager();
		
		MultipartRequest mp = new MultipartRequest(request, ".", 1024 * 1024 * 1024);
		Enumeration files = mp.getFileNames();
		while (files.hasMoreElements())
		{
			String name = (String)files.nextElement();
			   String filename = mp.getFilesystemName(name);
			   String type = mp.getContentType(name);
			   File f = mp.getFile(name);
			   
			   String videoId = "";
			   String userId = mp.getParameter("userid");
			   String videoLoc = "http://d2vpjum3aigw6y.cloudfront.net/videos/" + filename;
			   String videoReplyTo = mp.getParameter("replyVideoId");
			   
			   ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
						request.getSession().getServletContext());
			   VideosDao dao = ctx.getBean(VideosDao.class);
		   
			   videoId = dao.getNextVideoSequence();
			   SnsManager sns = new SnsManager();
			   
			   videoId = dao.getNextVideoSequence();
			
			   User user = dao.getUserDetails(userId);
			
			   String groupID = user.getGroupNumber();
			   String phoneNumber = user.getPhoneNumber();
			   String email = user.getEmail();
			   
			   videoId = dao.getNextVideoSequence();
			   
				if (!sns.isTopicExisting(groupID))
					sns.createTopic(groupID);
				
				sns.subscribe(groupID, phoneNumber, email);
				sns.sendMessage(groupID, user.getFirstName(), user.getLastName());
				
				m.putObject("videos/" + filename, f);
				dao.saveVideoMetadata(videoId, userId, videoLoc, videoReplyTo);
			  
				
				// Create AWS job to upload transcode uploaded video in iphone/ipad format
				try {
					
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
					input.setKey(filename);
					jobReq.setInput(input);
					
					
					CreateJobOutput output = new CreateJobOutput();
					output.setKey(filename);
					output.setPresetId("1351620000001-100020");
					
					jobReq.setOutput(output);
					etc.createJob(jobReq);
					
				} catch(Exception e) {
					System.out.println("Unable to Elastically Transcode uploaded file");
				}
				
				
			   f.delete();
		}
		
		// request.getRequestDispatcher("/videos.jsp").forward(request, response);
		
				/*
				ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
				
				try
				{
					List fileItems = upload.parseRequest(request);
					Iterator i = fileItems.iterator();
					
					while ( i.hasNext () ) 
				      {
				         FileItem fi = (FileItem)i.next();
				         
				         if ( !fi.isFormField() )	
				         {
				            String fieldName = fi.getFieldName();
				            String fileName = fi.getName();
				            String contentType = fi.getContentType();
				            boolean isInMemory = fi.isInMemory();
				            long sizeInBytes = fi.getSize();
				            File file;
				            
				            // Write the file
				            if( fileName.lastIndexOf("\\") >= 0 )
				            {
				               file = new File(fileName.substring( fileName.lastIndexOf("\\"))) ;
				            }
				            else
				            {
				               file = new File(fileName.substring(fileName.lastIndexOf("\\")+1)) ;
				            }
				            
				            fi.write( file ) ;
				         
				            m.putObject("video file", file);
				            file.delete();
				            
				            out.println("Uploaded Filename: " + file.getName() + "<br>");
				         }
				         
				      }
				}
				catch (FileUploadException e)
				{
					out.println(e.getMessage() + "<br>");
				}
				catch (Exception e)
				{
					out.println("exception");
				}
				*/
		
	}
	
}
