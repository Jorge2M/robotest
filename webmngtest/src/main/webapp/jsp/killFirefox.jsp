
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);
%>
<%@ page language="java" contentType="text/html"%>
<%@page session="false"%>
<%
//Buscamos procesos Firefox
Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
%>
<p>
	<b>Procesos firefox.exe eliminados</b>
</p>
<br>
<form action="../index.jsp">
	<input type="submit" value="Volver">
</form>
