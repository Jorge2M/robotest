<%response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);%>
<%@ page language="java" contentType="text/html"%>
<%@page session="false"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.mng.testmaker.service.TestMaker"%>
<%@ page import="com.mng.testmaker.conf.Channel"%>
<%@ page import="com.mng.testmaker.domain.SuiteTM"%>
<%@ page import="com.mng.robotest.test80.InputParams"%>
<%
	String suiteName = request.getParameter("suite");
String channel = request.getParameter("channel");
String idExecSuite = request.getParameter("idExecSuite");
List<SuiteTM> listSuites = new ArrayList<>(); 
if (idExecSuite==null)
    listSuites = TestMaker.getListSuites(suiteName, Channel.valueOf(channel));
else
    listSuites.add(TestMaker.getSuite(idExecSuite));
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
for (SuiteTM suite : listSuites) {
	InputParams inputParams = (InputParams)suite.getInputParams();
%>
	<tr id="scriptData">
		<td id="idExecution"><%=suite.getIdExecution()%></td>
		<td id="state"><%=suite.getStateExecution()%></td>
		<td id="suiteName"><%=suite.getName()%></td>
		<td id="version"><%=inputParams.getVersionSuite()%></td>
		<td id="channel"><%=inputParams.getChannel()%></td>
		<td id="application"><%=inputParams.getApp()%></td>
		<td id="browser"><%=inputParams.getWebDriverType()%></td>
		<td id="numTCases"><%=suite.getNumberTestCases()%></td>
		<td id="countrys"><%=inputParams.getListaPaisesStr()%></td>
		<td id="urlBase">
			<a href="<%=inputParams.getUrlBase()%>"><%=inputParams.getUrlBase()%></a>
		</td>		
		<td id="reportHTML">
			<a href="<%=suite.getDnsReportHtml()%>">Report HTML</a>
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