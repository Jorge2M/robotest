
<%
	response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@ page import="org.pruebasws.thread.TSuiteThreadsManager"%>
<%@ page import="org.pruebasws.jdbc.dao.TestsDAO"%>
<%@ page import="org.pruebasws.jdbc.to.SuiteTestData"%>
<%@ page import="org.pruebasws.jdbc.to.BrowserSuite"%>
<%@ page import="org.pruebasws.jdbc.to.ApplicationSuite"%>
<%@ page import="org.pruebasws.jdbc.to.VersionSuite"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.TreeSet"%>
<%@ page import="com.mng.robotest.test80.Test80mng"%>
<%@ page import="com.mng.robotest.test80.InputParamsMango"%>
<%@ page import="com.mng.testmaker.boundary.access.CmdLineMaker"%>
<%@ page import="com.mng.testmaker.domain.InputParamsTM"%>
<%@ page import="com.mng.robotest.test80.InputParamsMango" %>
<%@ page import="com.mng.testmaker.domain.testfilter.TestMethod"%>
<%@ page import="com.mng.testmaker.domain.testfilter.FilterTestsSuiteXML"%>
<%@ page import="com.mng.robotest.test80.mango.conftestmaker.AppEcom" %>
<%@ page import="com.mng.robotest.test80.mango.conftestmaker.Suites" %>

<!-- Bootstrap styles -->
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
	
<style>
.fileinput-button {
	position: relative;
	overflow: hidden;
	font-size: 12px;
	padding: 1px;
}

.fileinput-button input {
	position: absolute;
	top: 0;
	right: 0;
	margin: 0;
	opacity: 0;
	-ms-filter: 'alpha(opacity=0)';
	font-size: 200px;
	direction: ltr;
	cursor: pointer;
}

/* Fixes for IE < 8 */
@media screen\9 {
	.fileinput-button input {
		filter: alpha(opacity = 0);
		font-size: 100%;
		height: 100%;
	}
}
</style>
<%
	ServletContext ctx = getServletContext();
System.setProperty("user.dir", getServletContext().getRealPath(""));

//Obtenemos la información de todos los scripts definidos para la aplicación
List<SuiteTestData> listTestSuites = TestsDAO.getListaTestSuites();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="refresh" content="60" >
<title>Menú tests disponibles</title>
<style>
table th, td {
	padding: 0 5pt;
}

table td {
	height: 50pt;
}

table#tablaScripts {
	font: 10pt Arial;
	background-color: #F0FFF0;
}

table#tablaScripts td.nombreTestSuite {
	background-color: #C0FFC0;
	font-weight: bold;
}

table#tablaScripts td.channel {
	font-weight: bold;
}

table select {
	margin-bottom: 10px;
}

thead#theadScripts {
	background-color: #A0FFA0;
	font: 12pt Arial;
}
</style>

</head>
<body>
	<div style="float:left;">
	<table id="tablaScripts" border="1">
		<thead id="theadScripts">
			<tr>
				<th>Test Suite</th>
				<th>Channel</th>
				<th>Start</th>
				<th>Force Start</th>
				<th>Browser</th>
				<th>Application</th>
				<th>NetTraffic</th>				
				<th>Versión</th>
				<th>URL Base</th>
				<th>TestCases</th>
				<th>Países</th>
				<th>Pagos</th>
				<th>Líneas</th>
				<th>Tests en curso</th>
				<th>Histórico Reports</th>
			</tr>
		</thead>
		<tbody>

<%
	int i=0;
for (SuiteTestData suiteTest : listTestSuites) {
%>
		<form id="testform_<%=i%>" action="./jsp/execTest.jsp" method="post">
			<tr id="scriptData-<%=i%>">
				<td id="test-<%=i%>" class="nombreTestSuite" title="<%=suiteTest.getDescription()%>"><%=suiteTest.getSuite()%></td>
				<input id="<%=InputParamsTM.SuiteNameParam%>" name="<%=InputParamsTM.SuiteNameParam%>" type="hidden" value='<%=suiteTest.getSuite()%>' />
				<td id="channel-<%=i%>" class="channel"><%=suiteTest.getChannel()%></td>
				<input id="<%=InputParamsTM.ChannelNameParam%>" name="<%=InputParamsTM.ChannelNameParam%>" type="hidden" value='<%=suiteTest.getChannel()%>' />
				<td id="link-<%=i%>">
					<input id="Start" type="Submit" value="Start" />
				</td>
				<td id="force-<%=i%>">
					<input id="force-<%=i%>" type="checkbox" name="forceStart" />
				</td>
				<td id="browser-<%=i%>">
					<select id="browser-select-<%=i%>" name="<%=InputParamsTM.BrowserNameParam%>" suite="<%=suiteTest.getSuite()%>" channel="<%=suiteTest.getChannel()%>" form="testform_<%=i%>">
						<%
							String actualBrowser = suiteTest.getIdBrowser();
											for (BrowserSuite browserSuite : suiteTest.getListBrowsersChannel()) {
												String selected = "";
												if (browserSuite.getBrowser().compareTo(actualBrowser)==0) {
													selected = "selected";
												}
						%>
								<option value="<%=browserSuite.getBrowser()%>" <%=selected%>><%=browserSuite.getBrowser()%></option>
								<%
									}
								%>
					</select>
				</td>
				<td id="application">
					<select id="application-select-<%=i%>" name="<%=InputParamsTM.AppNameParam%>" suite="<%=suiteTest.getSuite()%>" channel="<%=suiteTest.getChannel()%>" form="testform_<%=i%>">
						<%
							String actualApplication = suiteTest.getApplicationActual();
											for (ApplicationSuite applicationSuite : suiteTest.getListApplicationsChannel()) {
												String selected = "";
												if (applicationSuite.getApplication().compareTo(actualApplication)==0) {
													selected = "selected";
												}
						%>
								<option value="<%=applicationSuite.getApplication()%>" <%=selected%>><%=applicationSuite.getApplication()%></option>
								<%
									}
								%>
					</select></td>
				<td id="nettrafic">
					<select id="nettrafic-select-<%=i%>" name="<%=InputParamsTM.NetAnalysisParam%>" suite="<%=suiteTest.getSuite()%>" channel="<%=suiteTest.getChannel()%>" form="testform_<%=i%>">
						<%
							String actualNettrafic = suiteTest.getNettrafic();
											String selectedFalse = "selected";
											String selectedTrue = "";
											if ("true".compareTo(actualNettrafic)==0) {
												selectedFalse = "";
												selectedTrue = "selected";   
											}
						%>
						<option value="false" <%=selectedFalse%>>false</option>
						<option value="true" <%=selectedTrue%>>true</option>
					</select></td>					
				<td id="version">
					<select id="version-select-<%=i%>" name="<%=InputParamsTM.VersionNameParam%>" suite="<%=suiteTest.getSuite()%>" channel="<%=suiteTest.getChannel()%>" form="testform_<%=i%>">
						<%
							String actualVersion = suiteTest.getVersionActual();
											for (VersionSuite versionSuite : suiteTest.getVersionChannelList()) {
												String selected = "";
												if (versionSuite.getVersion().compareTo(actualVersion)==0) {
													selected = "selected";
												}
						%>
								<option value="<%=versionSuite.getVersion()%>" <%=selected%>><%=versionSuite.getDescription()%></option>
								<%
									}
								%>
					</select>
				</td>
				<td id="urlbase-<%=i%>" class="input">
					<input id="urlbase-input-<%=i%>" name="<%=InputParamsTM.URLNameParam%>" suite="<%=suiteTest.getSuite()%>" channel="<%=suiteTest.getChannel()%>" value='<%=suiteTest.getUrlBase()%>' />
				</td>
				<td id="tcasesselect-<%=i%>">
					<%
						if (suiteTest.getFiltroTCases()==1) {
					%>					
					<select multiple size=10 id="tcases-<%=i%>" name="<%=InputParamsTM.TCaseNameParam%>" suite="<%=suiteTest.getSuite()%>" channel="<%=suiteTest.getChannel()%>" form="testform_<%=i%>">
						<%
							for (TestMethod testMethod : Test80mng.getDataTestAnnotationsToExec(getParamsFromSuiteToGetTCases(suiteTest))) {
												String selected = "";
												if (FilterTestsSuiteXML.methodInTestCaseList(testMethod.getMethod().getName(), suiteTest.getListTCasesArray())) {
													selected = "selected";
												}
						%>
								<option value="<%=testMethod.getMethod().getName()%>" <%=selected%> title="<%=testMethod.getAnnotationTest().description()%>"><%=testMethod.getMethod().getName()%></option>
						<%
							}
						%>
 					<%
 						}
 					%>						
					</select>
				</td>				
				<td id="countrys-<%=i%>" class="input">
					<%
						if (suiteTest.getFiltroPaises()==1) {
					%> 
					<input id="countrys-input-<%=i%>" name="<%=InputParamsMango.CountrysNameParam%>" suite="<%=suiteTest.getSuite()%>" channel="<%=suiteTest.getChannel()%>" value='<%=suiteTest.getListPaises()%>' /> 
 					<%
  						}
  					%>
				</td>
				<td id="pagos-<%=i%>">
					<%
						if (suiteTest.getFiltroPagos()==1) {
					%>					
					<select multiple size=10 id="pagos-<%=i%>" name="<%=InputParamsMango.PaymentsNameParam%>" suite="<%=suiteTest.getSuite()%>" channel="<%=suiteTest.getChannel()%>" form="testform_<%=i%>">
						<%
							String listPaisesCommaSeparated = suiteTest.getListPaises();
										if (suiteTest.getFiltroPaises()==0)
										    listPaisesCommaSeparated = "001"; //España														
										
							            TreeSet<String> listAllPagosCountrys = Test80mng.getListPagoFilterNames(listPaisesCommaSeparated, suiteTest.getChannelType(), suiteTest.getAppType(), false/*isEmpl*/);
										for (String nombrePago : listAllPagosCountrys) {
										    String selected = "";
										    if (suiteTest.getListPagosArray().size()>0 && suiteTest.getListPagosArray().contains(nombrePago))
										        selected = "selected";
						%>
							<option value="<%=nombrePago%>" <%=selected%>><%=nombrePago%></option>
						<%
							}
						%>
 					<%
 						}
 					%>						
					</select>
				</td>				
				<td id="lineas-<%=i%>">
					<%
						if (suiteTest.getFiltroLineas()==1) {
					%>					
					<select multiple size=3 id="lineas-select-<%=i%>" name="<%=InputParamsMango.LineasNameParam%>" suite="<%=suiteTest.getSuite()%>" channel="<%=suiteTest.getChannel()%>" form="testform_<%=i%>">
						<%
							for (String linea : suiteTest.getListLineasArray()) {
						%>
						<option value="<%=linea%>"><%=linea%></option>
						<%
							}
						%>
 					<%
 						}
 					%>						
					</select>
				</td>				
				<td nowrap id="threads-<%=i%>">
					<%
						ArrayList<Thread> threadsTest = TSuiteThreadsManager.getThreadsTestSuiteGroup(suiteTest.getSuite(), suiteTest.getChannel());
								boolean threadsRun = false;
								if (threadsTest.size()>0) 
								    threadsRun = true;
								
								for (int j=0; j<threadsTest.size(); j++) {
					%><div style="white-space: nowrap;"><a href="./jsp/stopTestSuite.jsp?idThreadGroupTestSuite=<%=threadsTest.get(j).getName()%>">Stop</a><span> <%=threadsTest.get(j).getName()%></span></div>
					<%
						}
					%>
				</td>
				<td id="historico-<%=i%>">
					<a href="./jsp/getListSuites.jsp?suite=<%=suiteTest.getSuite()%>&channel=<%=suiteTest.getChannel()%>">Histórico</a>
				</td>
			</tr>
		</form>
<%
	i+=1;
}
%>

		</tbody>
	</table>
	</div>
	
	<div style="float:right;">
		<ul style="font: 9pt Arial; padding-left: 2px; margin-bottom: 0px;">
			<li style="list-style-type: none; display: inline; padding-right: 8px; float: right;"><a href="./jsp/killFirefox.jsp?">Kill firefox</a></li>
			<li style="list-style-type: none; display: inline; padding-right: 8px; float: right;"><a href="./jsp/killChrome.jsp?">Kill Chrome</a></li>
		</ul>
	</div>

	<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
	<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
	<script
		src="https://blueimp.github.io/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
	<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
	<script
		src="https://blueimp.github.io/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
	<!-- The basic File Upload plugin -->
	<script
		src="https://blueimp.github.io/jQuery-File-Upload/js/jquery.fileupload.js"></script>
	<!-- Bootstrap JS is not required, but included for the responsive demo navigation -->
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<script>
$(document).ready(function () {
    $('select[name=<%=InputParamsTM.BrowserNameParam%>]').change(function () {
    	updateData($(this).attr("suite"), $(this).attr("channel"), "browser", $(this).val());
    });
    
    $('select[name=<%=InputParamsTM.AppNameParam%>]').change(function () {
    	updateData($(this).attr("suite"), $(this).attr("channel"), "application", $(this).val());
    });
    
    $('select[name=<%=InputParamsTM.NetAnalysisParam%>]').change(function () {
    	updateData($(this).attr("suite"), $(this).attr("channel"), "net", $(this).val());
    });    
    
    $('select[name=<%=InputParamsTM.VersionNameParam%>]').change(function () {
    	updateData($(this).attr("suite"), $(this).attr("channel"), "version", $(this).val());
    });

    $('input[name=<%=InputParamsTM.URLNameParam%>]').change(function () {
    	updateData($(this).attr("suite"), $(this).attr("channel"), "url", $(this).val());
    });
    
    $('input[name=<%=InputParamsMango.CountrysNameParam%>]').change(function () {
    	updateData($(this).attr("suite"), $(this).attr("channel"), "countrys", $(this).val());
    });
    
    $('select[name=<%=InputParamsMango.PaymentsNameParam%>]').change(function () {
    	updateData($(this).attr("suite"), $(this).attr("channel"), "payments", $(this).val());
    });    
    
    $('select[name=<%=InputParamsTM.TCaseNameParam%>]').change(function () {
    	updateData($(this).attr("suite"), $(this).attr("channel"), "tcases", $(this).val());
    });
});

function updateData(suite, channel, dataToChange, newBrowser) {
	$.get('./jsp/updateSuiteData.jsp', { suite:suite, channel:channel, dataToChange:dataToChange, newData:newBrowser })
	.done(function(data) {
    	location.reload();
	});			
}
</script>

</body>
</html>
	
<%!public static InputParamsMango getParamsFromSuiteToGetTCases(SuiteTestData suiteTest) {
	String app = suiteTest.getApplicationActual();
	String suite = suiteTest.getSuite();
	InputParamsMango paramsTSuite = InputParamsMango.getNew(Suites.class, AppEcom.class);
	paramsTSuite.setApp(AppEcom.valueOf(app));
	paramsTSuite.setSuite(Suites.valueOf(suite));
    paramsTSuite.setChannel(suiteTest.getChannel());
    paramsTSuite.setBrowser(suiteTest.getIdBrowser());
    paramsTSuite.setVersion(suiteTest.getVersionActual());

    return paramsTSuite;
}%>
