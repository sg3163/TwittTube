package edu.columbia.servlets;

import java.io.*;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.columbia.dao.*;

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
				
				m.putObject("videos/" + filename, f);
				dao.saveVideoMetadata(videoId, userId, videoLoc, videoReplyTo);
			  
			   f.delete();
		}
		
	}

}
