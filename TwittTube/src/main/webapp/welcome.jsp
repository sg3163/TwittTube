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
	List<Video> videoList =	null;
//	List<Video> videoList = (List<Video>) getServletContext().getAttribute("videoList");
    String abc = "Tesgin";
%>
<% videoList= (List<Video>) request.getAttribute("videoList"); %>
</head>
<body>
<table>
	<% for(int i=0;i<videoList.size();i++) { %>
	<tr><td> <param name="moviename" value="http://fpdownload.adobe.com/strobe/FlashMediaPlayback_101.swf"> </param> 
			<param name="flashvars" value="src=<%=videoList.get(i).getVideoLoc()%>"></param> 
			<param name="allowFullScreen" value="true"></param> <param name="allowscriptaccess" value="always"></param> 
			<embed src="http://fpdownload.adobe.com/strobe/FlashMediaPlayback_101.swf" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="500" height="300" flashvars="src=<%=videoList.get(i).getVideoLoc()%>"></embed> </object>
	</td></tr>
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