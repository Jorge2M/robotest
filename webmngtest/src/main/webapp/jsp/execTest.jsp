<%response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);%>
<%@ page language="java" contentType="text/html" %>
<%@page session="false"%>
<html>
<head><title>WebDriver scripts Menu</title>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
</head>
<body>
	<%@ page import="com.mng.robotest.test80.Test80mng" %>
	<%@ page import="com.mng.robotest.test80.InputParamsMango" %>
	<%@ page import="com.mng.testmaker.domain.InputParamsTM" %>
	<%@ page import="com.mng.testmaker.boundary.access.CmdLineMaker"%>
	<%@ page import="java.io.BufferedReader" %>
	<%@ page import="javax.servlet.ServletContext" %>
	<%@ page import="java.io.InputStreamReader" %>
	<%@ page import="java.util.Arrays" %>
	<%@ page import="org.pruebasws.thread.TSuiteThreadsManager" %>
	<%@ page import="com.mng.robotest.test80.CallBack" %>
	<%@ page import="com.mng.robotest.test80.mango.conftestmaker.Suites" %>
	<%@ page import="com.mng.robotest.test80.mango.conftestmaker.AppEcom" %>
	<%@ page import="com.mng.testmaker.service.TestMaker" %>
	<%@ page import="com.mng.testmaker.domain.SuiteTM" %>

	<style>
	body {
		font-family:Verdana,sans-serif;
	}
	
	#dataTestSuite {
		margin-bottom:20px;
	}
	
	#testSuiteName {
		font-size:18px;
		margin-bottom:8px;
	}
	
	.testSuiteAttribute {
		font-size:12px;
		margin:0px;
	}
	
	.message {
		font-size:14px;
		margin-top: 10px;
		color: blue;
	}
	
	.errorMessage {
		font-size:14px;
		margin-top: 10px;
		color: red;
	}
	</style>

  	<%
  		ServletContext ctx = getServletContext();
  	  	  	  	System.setProperty("user.dir", getServletContext().getRealPath(""));
  	  	  	  	
  	  	  	  	//Store the request params
  	  	  	  	InputParamsMango paramsTSuite = storeParamsFromHttpRequest(request);
  	  	  		
  	  	  		//Specific parameter from index.jsp
  	  	  	  	String forceStart = "off"; 
  	  	  	  	if (request.getParameter("forceStart")!=null) {
  	  	  	  		forceStart = request.getParameter("forceStart");
  	  	  	  	}
  	  	  	  	SuiteTM suite = null;
  	%>

	<div id="dataTestSuite"">
		<p id="testSuiteName">TestSuite: <b><%=paramsTSuite.getSuiteName()%></b></p>
		<p class="testSuiteAttribute">Channel: <b><%=paramsTSuite.getChannel()%></b></p>
		<p class="testSuiteAttribute">Browser: <b><%=paramsTSuite.getWebDriverType()%></b></p>
		<%
		if (paramsTSuite.getVersion()!=null && "".compareTo(paramsTSuite.getVersion())!=0) {
		%>
		<p class="testSuiteAttribute">Version: <b><%=paramsTSuite.getVersion()%></b></p>
		<%
		}
		if (paramsTSuite.getUrlBase()!=null && "".compareTo(paramsTSuite.getUrlBase())!=0) {
		%>
		<p class="testSuiteAttribute">URL Base: <b><%=paramsTSuite.getUrlBase()%></b></p>
		<%
	    }
	    if (paramsTSuite.getListaPaises()!=null) {
		%>
		<p class="testSuiteAttribute">Countries: <b><%=paramsTSuite.getListaPaisesStr()%></b></p>
		<%
	    }
		%>
	</div>

	<%
	boolean browserTasksRunning;
	if(System.getProperty("os.name").contains("Windows")){
		browserTasksRunning = browserTasksRunning(paramsTSuite.getWebDriverType().toString(), "tasklist");
	} else {
		browserTasksRunning = browserTasksRunning(paramsTSuite.getWebDriverType().toString(), "ps");
	}
	if (forceStart.toLowerCase().compareTo("on")!=0 && browserTasksRunning) {
	%>
		<div id="contenidoAjax"><p style="color:red;">Test no iniciado! </p>
			<ul>
				<li><b>Existe un <%=paramsTSuite.getWebDriverType().toString().toUpperCase()%> arrancado en la mï¿½quina de Test.</b> Puede tratarse de un test activo. Espere a que finalice el test en la mï¿½quina remota o cierre las pantallas de <%=paramsTSuite.getWebDriverType().toString().toUpperCase()%>.</li>
			</ul>
		</div>
		<%
	}
	else {
	    //Obtenemos el id de la ejecuciï¿½n + el output directory correspondiente a dicha ejecuciï¿½n y ejecutamos la TestSuite
	    //String idExecutedSuite = Test80mng.getIdForSuiteToExecute();
		//paramsTSuite.setIdExecutedSuite(idExecutedSuite);
	    String idExecSuite = TSuiteThreadsManager.startSuiteInThread(paramsTSuite);

	    //Esperamos a que se arranque el test
		int maxSecondsToWait = Suites.valueOf(paramsTSuite.getSuiteName()).getMaxSecondsToWaitStart();
		boolean testExists = waitToTestSuiteExists(maxSecondsToWait, idExecSuite);
		if (!testExists) {
			out.print("<div class=\"errorMessage\"><b>Problema en el inicio la TestSuite!</b>. Superado Timeout " + maxSecondsToWait + " segundos</div>");
		}
		else {
		  	//Construímos la ruta del report HTML
		  	//suite = SuitesDAO.getSuite(idExecSuite, paramsTSuite.getSuiteName());
		  	suite = TestMaker.getSuite(idExecSuite);
		  	
		  	//Escribimos la funciï¿½n JavaScript que iterarï¿½ la llamada Ajax que espera la finalizaciï¿½n de los tests (existencia del fichero)
			out.println("<script>");
			out.println("var interval = null;");
			out.println("$(document).ready(function() {");
			out.println("    interval = setInterval(\"ajaxd()\",5000);");
			out.println("});");
			out.println("</script>");
			out.println("<div id=\"contenidoAjax\" class=\"message\"><b>Test en ejecuciï¿½n!</b>. Esperando su finalizaciï¿½n...</div>");
			
			//Forzamos un stop
			//testNG.setThreadCount(0);
		}
	}
	%>
	
	<%!public static InputParamsMango storeParamsFromHttpRequest(HttpServletRequest request) {
		//Parameters that come from index.jsp
		String app = request.getParameter(InputParamsTM.AppNameParam);
    	String suite = request.getParameter(InputParamsTM.SuiteNameParam);
    	InputParamsMango paramsTSuite = InputParamsMango.getNew(Suites.class, AppEcom.class);
    	paramsTSuite.setApp(AppEcom.valueOf(app));
    	paramsTSuite.setSuite(Suites.valueOf(suite));
	    paramsTSuite.setChannel(request.getParameter(InputParamsTM.ChannelNameParam));
	    paramsTSuite.setBrowser(request.getParameter(InputParamsTM.BrowserNameParam));
	    paramsTSuite.setVersion(request.getParameter(InputParamsTM.VersionNameParam));
	    paramsTSuite.setUrlBase(request.getParameter(InputParamsTM.URLNameParam));
	    paramsTSuite.setNetAnalysis(request.getParameter(InputParamsTM.NetAnalysisParam));
	    paramsTSuite.setListaPaises(request.getParameter(InputParamsMango.CountrysNameParam));
	    paramsTSuite.setListaLineas(request.getParameterValues(InputParamsMango.LineasNameParam));
	    paramsTSuite.setListaPayments(request.getParameterValues(InputParamsMango.PaymentsNameParam));
	    String[] listTCases = request.getParameterValues(InputParamsTM.TCaseNameParam);
	    if (listTCases!=null) {
	    	paramsTSuite.setTestCasesFilter(Arrays.asList(listTCases));
	    }

	    //Parameters that don't come from index.jsp (for exemple, the call from Jenkin's CI Task)
	    paramsTSuite.setStoreResult(true); 
	    paramsTSuite.setUrlManto(request.getParameter(InputParamsMango.UrlManto)); 
	    paramsTSuite.setRecicleWD(request.getParameter(InputParamsTM.RecicleWDParam)); 
	    paramsTSuite.setNetAnalysis(request.getParameter(InputParamsTM.NetAnalysisParam));
	    String[] listMails = request.getParameterValues(InputParamsTM.MailsParam);
	    if (listMails!=null) {
	    	paramsTSuite.setMails(Arrays.asList(listMails)); 
	    }
	    paramsTSuite.setWebAppDNS(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()); 
	    if (request.getParameter(InputParamsMango.CallBackResource)!=null) {
			CallBack callBack = new CallBack();
	        callBack.setCallBackResource(request.getParameter(InputParamsMango.CallBackResource));
	        callBack.setCallBackMethod(request.getParameter(InputParamsMango.CallBackMethod));
	        callBack.setCallBackUser(request.getParameter(InputParamsMango.CallBackUser));
	        callBack.setCallBackPassword(request.getParameter(InputParamsMango.CallBackPassword));
	        callBack.setCallBackSchema(request.getParameter(InputParamsMango.CallBackSchema));
	        callBack.setCallBackParams(request.getParameter(InputParamsMango.CallBackParams));
	        paramsTSuite.setCallBack(callBack);
	    }
	    
	    return paramsTSuite;
	}
	
	public boolean waitToTestSuiteExists(int maxSecondsToWait, String idExecSuite) throws Exception {
		int i=0;
		//boolean existsTSuite = SuitesDAO.existsSuite(idExecSuite);
		boolean existsTSuite = (TestMaker.getSuite(idExecSuite)!=null);
		while (!existsTSuite && i<=maxSecondsToWait) {
			i+=1;
			Thread.sleep(1000);
			existsTSuite = (TestMaker.getSuite(idExecSuite)!=null);
		}
		
		return (existsTSuite); 
	}
	
	public boolean browserTasksRunning(String browser, String executionProtocol) throws Exception {
		Process p = Runtime.getRuntime().exec(executionProtocol);
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";
		while ((line = reader.readLine()) != null) {
		if ((line.toLowerCase().contains(browser + ".exe")) || (line.toLowerCase().contains(browser + ".app")))
			return true;
		}

		return false;
	}%>

	<br>
	<form action="../index.jsp">
	    <input type="submit" value="Volver">
	</form>
  	
	<script>
	<%
	if (suite!=null && suite.getIdExecution()!=null) {
	%>
	function ajaxd() {
		$.ajax({
			url:'./suiteFinished.jsp', 
			data: { idExecSuite: '<%=suite.getIdExecution()%>' },		
			timeout: 2000,
			success: 
				function(datos) {
					if (datos.indexOf("false") != -1) {
						document.getElementById("contenidoAjax").innerHTML += ".";
					}
					else {
						window.clearInterval(interval);
						window.location.href="./getListSuites.jsp?idExecSuite=<%=suite.getIdExecution()%>";
					}
				},
			error:
				function() {
					window.clearInterval(interval);
					document.getElementById("contenidoAjax").innerHTML="<b style=\"color:red\">Problemas tï¿½cnicos en la ejecuciï¿½n del test!</b>";
				}
			});
	}
	<%
	}
	%>
	</script>
</body>
</html>