<%response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);%>
<%@ page import="com.github.jorge2m.testmaker.service.TestMaker" %>
<%@ page import="com.github.jorge2m.testmaker.domain.StateExecution" %>
<%@ page language="java" contentType="text/html" %>
<%@page session="false"%>
<%
	String idExecSuite = request.getParameter("idExecSuite");
out.print(TestMaker.getSuite(idExecSuite).getStateExecution()==StateExecution.Finished);
%>