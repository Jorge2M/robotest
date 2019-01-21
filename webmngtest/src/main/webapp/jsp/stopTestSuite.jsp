
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
boolean stoppedNormally = Launch.stopTSuite(idThreadGroupTestSuite);
if (stoppedNormally) {
%>
<p>TestSuite Stopped Correctamente!<p>
<%
}
else {
%>
<p>Problemas en el Stop de la TestSuite, se ha procedido al stop vía KillThread.<p>
<%}%>
<br>
<form action="../index.jsp">
	<input type="submit" value="Volver">
</form>

