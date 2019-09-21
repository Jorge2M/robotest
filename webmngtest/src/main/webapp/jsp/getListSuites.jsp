<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);
%>
<%@ page language="java" contentType="text/html"%>
<%@page session="false"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.mng.robotest.test80.arq.jdbc.dao.SuitesDAO"%>
<%@ page import="com.mng.robotest.test80.arq.jdbc.to.Suite"%>
<%
String suiteName = request.getParameter("suite");
String channel = request.getParameter("channel");
String idExecSuite = request.getParameter("idExecSuite");
ArrayList<Suite> listSuites = new ArrayList<>(); 
if (idExecSuite==null)
    listSuites = SuitesDAO.getSuitesByChannel(suiteName, channel);
else
    listSuites.add(SuitesDAO.getSuite(idExecSuite, suiteName));
%>

<html>
<head>
<title>TestSuites Ejecutadas</title>
<style>
table th, td {
	padding: 0 5pt;
}

table#tablaScripts {
	font: 10pt Arial;
	background-color: #F0FFF0;
}

table#tablaScripts td.nombreTestSuite {
	background-color: #C0FFC0;
	font-weight: bold;
	"
}
</style>
</head>
<body>
<table id="tablaScripts" border="1">
<thead id="theadScripts">
	<tr>
		<th>Id Execution</th><th>State</th><th>Suite Name</th><th>Versión</th><th>Channel</th><th>Application</th><th>Browser</th><th>#Test Cases</th><th>Countries</th><th>URL Base</th><th>Report HTML</th>
	</tr>
</thead>
<tbody>

<%
int i=0;
for (Suite suite : listSuites) {
%>
	<tr id="scriptData">
		<td id="idExecution"><%=suite.getIdExecution()%></td>
		<td id="state"><%=suite.getStateSuite()%></td>
		<td id="suiteName"><%=suite.getSuiteName()%></td>
		<td id="version"><%=suite.getVersion()%></td>
		<td id="channel"><%=suite.getChannel()%></td>
		<td id="application"><%=suite.getApplication()%></td>
		<td id="browser"><%=suite.getBrowser()%></td>
		<td id="numTCases"><%=suite.getNumberMethods()%></td>
		<td id="countrys"><%=suite.getMoreInfo()%></td>
		<td id="urlBase">
			<a href="<%=suite.getUrlBase()%>"><%=suite.getUrlBase()%></a>
		</td>		
		<td id="reportHTML">
			<a href="<%=suite.getUrlReport()%>">Report HTML</a>
		</td>
	</tr>
<%}%>	
</table>
<br>
<form action="../index.jsp">
	<input type="submit" value="Volver">
</form>
</body>
</html>