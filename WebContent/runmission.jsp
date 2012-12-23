<%@page import="com.cycrypt.mission.DoneCallBack"%>
<%@page import="com.cycrypt.mission.CrackerMissionController"%>
<%@page import="com.cycrypt.mission.MHIDMaker"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String runCheck = request.getParameter("runCheck");
	String amount = request.getParameter("amount");
	if(amount==null||"".equals(amount.trim()))
		amount="20";
	String threadCount = request.getParameter("threadCount");
	if(threadCount==null||"".equals(threadCount.trim()))
		threadCount="5";
	String msg = "start success,amount:"+amount;
	if("1".equals(runCheck))
	{
		CrackerMissionController controllerRunning =  (CrackerMissionController)application.getAttribute("missionController");
		if(controllerRunning!=null&&controllerRunning.getRunningThreadCount()>0)
		{
				msg="still running go back and stop controller..";				
		}
		else
		{
			MHIDMaker idMaker = new MHIDMaker(Integer.valueOf(amount));//new MHIDMaker();//new MHIDMaker(2001000138, 3);
			CrackerMissionController controller = new CrackerMissionController(idMaker, new DoneCallBack() {
				
				public void done() {
					// TODO Auto-generated method stub
					System.out.println("All missions done, checkout~!");
				}
			},Integer.parseInt(threadCount));
			controller.runMissions();
			application.removeAttribute("missionController");
			application.setAttribute("missionController", controller);
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