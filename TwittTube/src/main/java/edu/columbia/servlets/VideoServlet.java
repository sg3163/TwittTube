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
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
				request.getSession().getServletContext());
			VideosDao dao = ctx.getBean(VideosDao.class);
			List<Video> allvideos = dao.getVideoAndReplies(videoId);
			Video mainVideo = null;
			List<Video> replyVidoes = new ArrayList<Video>();
			
			for(int i=0;i<allvideos.size();i++) {
				if(allvideos.get(i).getReplyVideoId() == null) {
					mainVideo = allvideos.get(i);
					break;
				}
			}
			
			for(int i=0;i<allvideos.size();i++) {
				if(allvideos.get(i) != mainVideo) {
					replyVidoes.add(allvideos.get(i));
				}
			}
			
			request.setAttribute("mainVideo", mainVideo);
			request.setAttribute("replyVideos", replyVidoes);
			request.getRequestDispatcher("/videos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
