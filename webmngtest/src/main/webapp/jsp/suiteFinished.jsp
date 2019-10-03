<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);
%>
<%@ page import="com.mng.testmaker.jdbc.dao.SuitesDAO" %>
<%@ page import="com.mng.testmaker.utils.StateSuite" %>
<%@ page language="java" contentType="text/html" %>
<%@page session="false"%>
<%
String idExecSuite = request.getParameter("idExecSuite");
out.print(SuitesDAO.getStateSuite(idExecSuite)==StateSuite.FINISHED);
%>