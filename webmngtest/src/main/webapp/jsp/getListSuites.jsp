<%response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);%>
<%@ page language="java" contentType="text/html"%>
<%@page session="false"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="com.github.jorge2m.testmaker.service.TestMaker"%>
<%@ page import="com.github.jorge2m.testmaker.conf.Channel"%>
<%@ page import="com.github.jorge2m.testmaker.domain.RepositoryI"%>
<%@ page import="com.github.jorge2m.testmaker.conf.defaultstorer.RepositorySQLite"%>
<%@ page import="com.github.jorge2m.testmaker.repository.jdbc.dao.SuitesDAO"%>
<%@ page import="com.github.jorge2m.testmaker.domain.suitetree.SuiteBean"%>

<%
	String idExecSuite = request.getParameter("idExecSuite");
String suiteName = request.getParameter("suite");
String channel = request.getParameter("channel");
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
		<th>Id Execution</th>
		<th>State</th>
		<th>Suite Name</th>
		<th>Versi�n</th>
		<th>Channel</th>
		<th>Application</th>
		<th>Browser</th>
		<th>#Test Cases</th>
		<th>Aditional Data</th>
		<th>URL Base</th>
		<th>Report HTML</th>
	</tr>
</thead>
<tbody>

<%
	List<SuiteBean> listSuitesToDisplay = getSuitesToDisplay(idExecSuite, suiteName, channel);
int displayedSuites = 0;
for (SuiteBean suite : listSuitesToDisplay) {
%>
	<tr id="scriptData">
		<td id="idExecution"><%=suite.getIdExecSuite()%></td>
		<td id="state"><%=suite.getStateExecution()%></td>
		<td id="suiteName"><%=suite.getName()%></td>
		<td id="version"><%=suite.getVersion()%></td>
		<td id="channel"><%=suite.getChannel()%></td>
		<td id="application"><%=suite.getApp()%></td>
		<td id="browser"><%=suite.getDriver()%></td>
		<td id="numTCases"><%=suite.getNumberTestCases()%></td>
		<td id="countrys"><%=suite.getMoreInfo()%></td>
		<td id="urlBase">
			<a href="<%=suite.getUrlBase()%>"><%=suite.getUrlBase()%></a>
		</td>		
		<td id="reportHTML">
			<a href="<%=suite.getUrlReportHtml()%>">Report HTML</a>
		</td>
	</tr>
<%	displayedSuites+=1;
	if (displayedSuites >=100) {
		break;
	}
}%>	
</table>
<br>
<form action="../index.jsp">
	<input type="submit" value="Volver">
</form>
</body>
</html>

<%!private List<SuiteBean> getSuitesToDisplay(String idExecSuite, String suiteName, String channel) throws Exception {
	if (idExecSuite!=null) {
		return Arrays.asList(
				TestMaker.getSuite(idExecSuite));
	} else {
		List<SuiteBean> listSuites = TestMaker.getListSuites();
		List<SuiteBean> listSuitesToReturn = new ArrayList<>();
		for (SuiteBean suite : listSuites) {
			if (suite.getName().compareTo(suiteName)==0 &&
				suite.getChannel().toString().compareTo(channel)==0) {
				listSuitesToReturn.add(suite);
			}
		}
		return listSuitesToReturn;
	}
}%>