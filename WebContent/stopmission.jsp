<%@page import="com.cycrypt.mission.DoneCallBack"%>
<%@page import="com.cycrypt.mission.CrackerMissionController"%>
<%@page import="com.cycrypt.mission.MHIDMaker"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String stopCheck = request.getParameter("stopCheck");
	String msg = "stop success.";
	if("1".equals(stopCheck))
	{
		CrackerMissionController controllerRunning =  (CrackerMissionController)application.getAttribute("missionController");
		if(controllerRunning!=null)
		{
			CrackerMissionController.runningFlag=false;
			application.removeAttribute("missionController");
		}
		
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%=msg %>
</body>
</html>