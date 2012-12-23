<%@page import="com.cycrypt.mission.CrackerMissionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
  	CrackerMissionController controller = (CrackerMissionController)application.getAttribute("missionController");
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
hello
<br/>
<% 
	if(controller!=null)
	{
		String currentCYID = controller.getCurrentCYID();
		%>
		<%="currentCYID:"+currentCYID %><br/>
		<%="currentRunningThreadCount:"+controller.getRunningThreadCount() %>
		<form action="stopmission.jsp" method="get">
			<input type="hidden" name="stopCheck" value="1"/>
			<input type="submit" value="stop"/>
		</form>
		<%
	}else
	{
		%>
		<%="not running.." %>
		<br/>
		<form action="runmission.jsp" method="get">
			amount:<input type="text" name="amount"/><br/>
			threadCount:<input type="text" name="threadCount"/><br/>
			<input type="hidden" name="runCheck" value="1"/>
			<input type="submit" value="run"/>
		</form>
		<%
	}
%>
</body>
</html>