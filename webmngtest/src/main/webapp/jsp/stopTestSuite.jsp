
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);
%>
<%@ page language="java" contentType="text/html"%>
<%@page session="false"%>
<%@ page import="org.pruebasws.Launch"%>
<%
String idThreadGroupTestSuite = request.getParameter("idThreadGroupTestSuite");
Launch.stopTSuite(idThreadGroupTestSuite);
%>
<p>TestSuite Stopped!<p>
<br>
<form action="../index.jsp">
	<input type="submit" value="Volver">
</form>

