package edu.columbia.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.columbia.dao.VideosDao;
import edu.columbia.util.UAgentInfo;
import edu.columbia.vo.Video;

/**
 * Servlet implementation class UserServlet
 */
public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VideoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String videoId = request.getParameter("videoId");
		String userId = request.getParameter("userid");
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
				request.getSession().getServletContext());
			VideosDao dao = ctx.getBean(VideosDao.class);
			List<Video> allvideos = dao.getVideoAndReplies(videoId);
			Video mainVideo = null;
			List<Video> replyVidoes = new ArrayList<Video>();
			
			System.out.println("Client IP ----------------- " +getClientIpAddr(request));
			System.out.println("Is Mobile ----------------- " +isThisRequestCommingFromAMobileDevice(request));
			
			boolean isMobileRequest = isThisRequestCommingFromAMobileDevice(request);
			
			for(int i=0;i<allvideos.size();i++) {
				if(allvideos.get(i).getReplyVideoId() == null) {
					mainVideo = allvideos.get(i);
					if(isMobileRequest) {
						mainVideo.setVideoLoc(mainVideo.getVideoLoc().replaceAll("d2vpjum3aigw6y", "d1s8tdepuogpuo"));
					}
					break;
				}
			}
			
			for(int i=0;i<allvideos.size();i++) {
				if(allvideos.get(i) != mainVideo) {
					Video vid = allvideos.get(i);
					vid.setVideoLoc(vid.getVideoLoc().replaceAll("twittest", "twittestiphone"));
					replyVidoes.add(vid);
				}
			}
			
			request.setAttribute("mainVideo", mainVideo);
			request.setAttribute("replyVideos", replyVidoes);
			request.setAttribute("userid", userId);
			request.getRequestDispatcher("/videos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
	
	private boolean isThisRequestCommingFromAMobileDevice(HttpServletRequest request){

	    String userAgent = request.getHeader("User-Agent");
	    String httpAccept = request.getHeader("Accept");

	    UAgentInfo detector = new UAgentInfo(userAgent, httpAccept);

	    if (detector.detectMobileQuick()) {
	        return true;
	    }

	    return false;
	}
	
	public static String getClientIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  

}
