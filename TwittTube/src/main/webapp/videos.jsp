<%@page import="edu.columbia.vo.Video"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ page import="edu.columbia.vo.User" %>
 <%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%! 
	List<Video> replyList =	null;
	Video mainVideo = null;
	String userId = null;
//	List<Video> videoList = (List<Video>) getServletContext().getAttribute("videoList");
    String abc = "Tesgin";
%>
<% mainVideo = (Video) request.getAttribute("mainVideo");
	replyList= (List<Video>) request.getAttribute("replyVideos"); 
	userId = (String) request.getAttribute("userid");
	%>
</head>
<body>
<table>
	<tr>
		<td><h3>Video Uploaded by <%=mainVideo.getUploadedBy()%></h3></td>
	</tr>
	<tr>
		<td> 
			<video width="320" height="240" controls>
			  <source src="<%=mainVideo.getVideoLoc()%>" type="video/mp4">
			  <source src="<%=mainVideo.getVideoLoc().replaceAll("mp4", "ogg")%>" type="video/ogg">
			  <object data="<%=mainVideo.getVideoLoc()%>" width="320" height="240">
			    <embed src="<%=mainVideo.getVideoLoc().replaceAll("mp4", "swf")%>" width="320" height="240">
			  </object>
			</video>
		</td>
		<td>	
			<form action="UploadVideo.do" method="post" enctype="multipart/form-data">
		      <input type="hidden" name="key" value="uploads/${filename}">
		      <!--<input type="hidden" name="Content-Type" value="video/mp4">-->
		      	<input type="hidden" name="replyVideoId" value="<%=mainVideo.getVideoId()%>">
		      	<input type="hidden" name="userid" value="<%=userId%>">
		      File to upload to S3: 
		      <input name="file" type="file"> 
		      <br> 
		      <input type="submit" value="Upload File to S3"> 
		    </form> 
		</td>
	</tr>
</table>
<table>
	<tr>
		<td> <h3>Replies to the Video</h3></td>
	</tr>
	<% for(int i=0;i<replyList.size();i++) { %>
	<tr><td> Replied by <%=replyList.get(i).getUploadedBy()%></td></tr>
	<tr>
		<td> 
			<video width="320" height="240" controls>
			  <source src="<%=replyList.get(i).getVideoLoc()%>" type="video/mp4">
			  <source src="<%=replyList.get(i).getVideoLoc().replaceAll("mp4", "ogg")%>" type="video/ogg">
			  <object data="<%=replyList.get(i).getVideoLoc()%>" width="320" height="240">
			    <embed src="<%=replyList.get(i).getVideoLoc().replaceAll("mp4", "swf")%>" width="320" height="240">
			  </object>
			</video>
		</td>
		
	</tr>
<% } %>
</table>
<object width="500" height="300"> 
<!-- 
<param name="moviename" value="http://fpdownload.adobe.com/strobe/FlashMediaPlayback_101.swf"> </param> 
<param name="flashvars" value="src=http://d2vpjum3aigw6y.cloudfront.net/videos/sample_mpeg4.mp4"></param> 
<param name="allowFullScreen" value="true"></param> <param name="allowscriptaccess" value="always"></param> 
<embed src="http://fpdownload.adobe.com/strobe/FlashMediaPlayback_101.swf" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="500" height="300" flashvars="src=http://d2vpjum3aigw6y.cloudfront.net/videos/sample_mpeg4.mp4"></embed> </object>

<object width="500" height="300"> 
<param name="moviename" value="http://fpdownload.adobe.com/strobe/FlashMediaPlayback_101.swf"> </param> 
<param name="flashvars" value="src=rtmp://s49djqz462l9.cloudfront.net/videos/sample_mpeg4.mp4"></param> 
<param name="allowFullScreen" value="true"></param> <param name="allowscriptaccess" value="always"></param> 
<embed src="http://fpdownload.adobe.com/strobe/FlashMediaPlayback_101.swf" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="500" height="300" flashvars="src=rtmp://s49djqz462l9.cloudfront.net/videos/sample_mpeg4.mp4"></embed> </object>
  -->
</body>
</html>