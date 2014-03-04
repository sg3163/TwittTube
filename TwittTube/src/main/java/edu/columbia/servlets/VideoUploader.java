package edu.columbia.servlets;

import java.io.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.columbia.dao.*;

/*
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
*/
import com.oreilly.servlet.MultipartRequest;

public class VideoUploader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VideoUploader() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		S3BucketManager m = new S3BucketManager();
		
		/*
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		*/
		
		MultipartRequest mp = new MultipartRequest(request, ".", 1024 * 1024 * 1024);
		Enumeration files = mp.getFileNames();
		while (files.hasMoreElements())
		{
			String name = (String)files.nextElement();
			   String filename = mp.getFilesystemName(name);
			   String type = mp.getContentType(name);
			   File f = mp.getFile(name);
			   
			   /*
			   out.println("name: " + name);
			   out.println("filename: " + filename);
			   out.println("type: " + type);
			   if (f != null) {
			     out.println("f.toString(): " + f.toString());
			     out.println("f.getName(): " + f.getName());
			     out.println("f.exists(): " + f.exists());
			     out.println("f.length(): " + f.length());
			     out.println("f.path(): " + f.getAbsolutePath());
			     out.println();
			   }
			   */
			   
			   String videoId = "";
			   String userId = "";
			   String videoLoc = "http://d2vpjum3aigw6y.cloudfront.net/videos/" + filename;
			   String videoReplyTo = request.getParameter("videoReplyTo");
			   
			   VideosDao vd = new VideosDao();
			   vd.saveVideoMetadata(videoId, userId, videoLoc, videoReplyTo);
			   m.putObject("videos/" + filename, f);
			  
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
