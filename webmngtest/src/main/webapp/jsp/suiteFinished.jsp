<%response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);%>
<%@ page import="com.mng.testmaker.service.TestMaker" %>
<%@ page import="com.mng.testmaker.domain.StateExecution" %>
<%@ page language="java" contentType="text/html" %>
<%@page session="false"%>
<%
	String idExecSuite = request.getParameter("idExecSuite");
out.print(TestMaker.getSuiteExecuted(idExecSuite).getStateExecution()==StateExecution.Finished_Normally);
%>