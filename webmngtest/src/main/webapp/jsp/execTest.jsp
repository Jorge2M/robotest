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
	<%@ page import="com.mng.robotest.test80.arq.access.InputParamsTestMaker" %>
	<%@ page import="java.io.BufferedReader" %>
	<%@ page import="javax.servlet.ServletContext" %>
	<%@ page import="java.io.InputStreamReader" %>
	<%@ page import="org.pruebasws.thread.TSuiteThreadsManager" %>
	<%@ page import="com.mng.robotest.test80.arq.jdbc.to.Suite" %>
	<%@ page import="com.mng.robotest.test80.arq.jdbc.dao.SuitesDAO" %>
	<%@ page import="com.mng.robotest.test80.arq.listeners.CallBack" %>
	<%@ page import="com.mng.robotest.test80.mango.conftestmaker.Suites" %>
	<%@ page import="com.mng.robotest.test80.mango.conftestmaker.AppEcom" %>
	<%@ page import="com.mng.robotest.test80.arq.xmlprogram.SuiteMaker" %>

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
  	  	  	InputParamsTestMaker paramsTSuite = storeParamsFromHttpRequest(request);
  	  		
  	  		//Specific parameter from index.jsp
  	  	  	String forceStart = "off"; 
  	  	  	if (request.getParameter("forceStart")!=null)
  	  	  		forceStart = request.getParameter("forceStart");
  	  	  	
  	  	  	Suite suite = null;
  	%>

	<div id="dataTestSuite"">
		<p id="testSuiteName">TestSuite: <b><%=paramsTSuite.getNameSuite()%></b></p>
		<p class="testSuiteAttribute">Channel: <b><%=paramsTSuite.getChannel()%></b></p>
		<p class="testSuiteAttribute">Browser: <b><%=paramsTSuite.getBrowser()%></b></p>
		<%
		if (paramsTSuite.getVersion()!=null && "".compareTo(paramsTSuite.getVersion())!=0) {
		%>
		<p class="testSuiteAttribute">Version: <b><%=paramsTSuite.getVersion()%></b></p>
		<%
		}
		if (paramsTSuite.getURLBase()!=null && "".compareTo(paramsTSuite.getURLBase())!=0) {
		%>
		<p class="testSuiteAttribute">URL Base: <b><%=paramsTSuite.getURLBase()%></b></p>
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
		browserTasksRunning = browserTasksRunning(paramsTSuite.getBrowser(), "tasklist");
	} else {
		browserTasksRunning = browserTasksRunning(paramsTSuite.getBrowser(), "ps");
	}
	if (forceStart.toLowerCase().compareTo("on")!=0 && browserTasksRunning) {
	%>
		<div id="contenidoAjax"><p style="color:red;">Test no iniciado! </p>
			<ul>
				<li><b>Existe un <%=paramsTSuite.getBrowser().toUpperCase()%> arrancado en la m�quina de Test.</b> Puede tratarse de un test activo. Espere a que finalice el test en la m�quina remota o cierre las pantallas de <%=paramsTSuite.getBrowser().toUpperCase()%>.</li>
			</ul>
		</div>
		<%
	}
	else {
	    //Obtenemos el id de la ejecuci�n + el output directory correspondiente a dicha ejecuci�n y ejecutamos la TestSuite
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
		  	//Constru�mos la ruta del report HTML
		  	suite = SuitesDAO.getSuite(idExecSuite, paramsTSuite.getSuiteName());
		  	
		  	//Escribimos la funci�n JavaScript que iterar� la llamada Ajax que espera la finalizaci�n de los tests (existencia del fichero)
			out.println("<script>");
			out.println("var interval = null;");
			out.println("$(document).ready(function() {");
			out.println("    interval = setInterval(\"ajaxd()\",5000);");
			out.println("});");
			out.println("</script>");
			out.println("<div id=\"contenidoAjax\" class=\"message\"><b>Test en ejecuci�n!</b>. Esperando su finalizaci�n...</div>");
			
			//Forzamos un stop
			//testNG.setThreadCount(0);
		}
	}
	%>
	
	<%!public static InputParamsTestMaker storeParamsFromHttpRequest(HttpServletRequest request) {
		//Parameters that come from index.jsp
		String app = request.getParameter(Test80mng.AppNameParam);
    	String suite = request.getParameter(Test80mng.SuiteNameParam);
        InputParamsTestMaker paramsTSuite = new InputParamsTestMaker(AppEcom.valueOf(app), Suites.valueOf(suite));
	    paramsTSuite.setChannel(request.getParameter(Test80mng.ChannelNameParam));
	    paramsTSuite.setBrowser(request.getParameter(Test80mng.BrowserNameParam));
	    paramsTSuite.setVersion(request.getParameter(Test80mng.VersionNameParam));
	    paramsTSuite.setURLBase(request.getParameter(Test80mng.URLNameParam));
	    paramsTSuite.setNetAnalysis(request.getParameter(Test80mng.NetAnalysis));
	    paramsTSuite.setListaPaises(request.getParameter(Test80mng.CountrysNameParam));
	    paramsTSuite.setListaLineas(request.getParameterValues(Test80mng.LineasNameParam));
	    paramsTSuite.setListaPayments(request.getParameterValues(Test80mng.PaymentsNameParam));
	    paramsTSuite.setListaTestCases(request.getParameterValues(Test80mng.TCaseNameParam));

	    //Parameters that don't come from index.jsp (for exemple, the call from Jenkin's CI Task)
	    paramsTSuite.setUrlManto(request.getParameter(Test80mng.UrlManto));
	    paramsTSuite.setRecicleWD(request.getParameter(Test80mng.RecicleWD));
	    paramsTSuite.setNetAnalysis(request.getParameter(Test80mng.NetAnalysis));
	    paramsTSuite.setMails(request.getParameterValues(Test80mng.Mails));
	    paramsTSuite.setApplicationDNS(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()); 
	    if (request.getParameter(Test80mng.CallBackResource)!=null) {
			CallBack callBack = new CallBack();
	        callBack.setCallBackResource(request.getParameter(Test80mng.CallBackResource));
	        callBack.setCallBackMethod(request.getParameter(Test80mng.CallBackMethod));
	        callBack.setCallBackUser(request.getParameter(Test80mng.CallBackUser));
	        callBack.setCallBackPassword(request.getParameter(Test80mng.CallBackPassword));
	        callBack.setCallBackSchema(request.getParameter(Test80mng.CallBackSchema));
	        callBack.setCallBackParams(request.getParameter(Test80mng.CallBackParams));
	        paramsTSuite.setCallBack(callBack);
	    }
	    
	    return paramsTSuite;
	}
	
	public boolean waitToTestSuiteExists(int maxSecondsToWait, String idExecSuite) throws Exception {
		int i=0;
		boolean existsTSuite = SuitesDAO.existsSuite(idExecSuite);   		
		while (!existsTSuite && i<=maxSecondsToWait) {
			i+=1;
			Thread.sleep(1000);
			existsTSuite = SuitesDAO.existsSuite(idExecSuite);
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
						window.location.href="./getListSuites.jsp?idExecSuite=<%=suite.getIdExecution()%>&suite=<%=suite.getSuiteName()%>";
					}
				},
			error:
				function() {
					window.clearInterval(interval);
					document.getElementById("contenidoAjax").innerHTML="<b style=\"color:red\">Problemas t�cnicos en la ejecuci�n del test!</b>";
				}
			});
	}
	<%
	}
	%>
	</script>
</body>
</html>