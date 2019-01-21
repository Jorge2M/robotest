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
	<%@ page import="com.mng.robotest.test80.ParamsBean" %>
	<%@ page import="java.io.BufferedReader" %>
	<%@ page import="javax.servlet.ServletContext" %>
	<%@ page import="java.io.InputStreamReader" %>
	<%@ page import="org.pruebasws.utils.TSuiteThreadsManager" %>
	<%@ page import="com.mng.robotest.test80.arq.jdbc.to.Suite" %>
	<%@ page import="com.mng.robotest.test80.arq.jdbc.dao.SuitesDAO" %>
	<%@ page import="com.mng.robotest.test80.arq.listeners.CallBack" %>
	<%@ page import="com.mng.robotest.test80.mango.test.data.Suites" %>

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
  	ParamsBean paramsTSuite = storeParamsFromHttpRequest(request);
	
	//Specific parameter from index.jsp
  	String forceStart = "off"; 
  	if (request.getParameter("forceStart")!=null)
  		forceStart = request.getParameter("forceStart");
  	
  	Suite suite = null;
  	%>

	<div id="dataTestSuite"">
		<p id="testSuiteName">TestSuite: <b><%=paramsTSuite.getSuiteName()%></b></p>
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
	boolean browserTasksRunning = browserTasksRunning(paramsTSuite.getBrowser());
	if (forceStart.toLowerCase().compareTo("on")!=0 && browserTasksRunning) {
	%>
		<div id="contenidoAjax"><p style="color:red;">Test no iniciado! </p>
			<ul>
				<li><b>Existe un <%=paramsTSuite.getBrowser().toUpperCase()%> arrancado en la máquina de Test.</b> Puede tratarse de un test activo. Espere a que finalice el test en la máquina remota o cierre las pantallas de <%=paramsTSuite.getBrowser().toUpperCase()%>.</li>
			</ul>
		</div>
		<%
	}
	else {
	    //Obtenemos el id de la ejecución + el output directory correspondiente a dicha ejecución y ejecutamos la TestSuite
	    String idExecutedSuite = Test80mng.getIdForSuiteToExecute();
		paramsTSuite.setIdExecutedSuite(idExecutedSuite);
	    launchTestSuiteInThread(paramsTSuite, ctx);

	    //Esperamos a que se arranque el test
		int maxSecondsToWait = Suites.valueOf(paramsTSuite.getSuiteName()).getMaxSecondsToWaitStart();
		boolean testExists = waitToTestSuiteExists(maxSecondsToWait, idExecutedSuite);
		if (!testExists)
			out.print("<div class=\"errorMessage\"><b>Problema en el inicio la TestSuite!</b>. Superado Timeout " + maxSecondsToWait + " segundos</div>");			
		else {
		  	//Construímos la ruta del report HTML
		  	suite = SuitesDAO.getSuite(idExecutedSuite, paramsTSuite.getSuiteName());
		  	
		  	//Escribimos la función JavaScript que iterará la llamada Ajax que espera la finalización de los tests (existencia del fichero)
			out.println("<script>");
			out.println("var interval = null;");
			out.println("$(document).ready(function() {");
			out.println("    interval = setInterval(\"ajaxd()\",5000);");
			out.println("});");
			out.println("</script>");
			out.println("<div id=\"contenidoAjax\" class=\"message\"><b>Test en ejecución!</b>. Esperando su finalización...</div>");
			
			//Forzamos un stop
			//testNG.setThreadCount(0);
		}
	}
	%>
	
	<%!
	public static long launchTestSuiteInThread(final ParamsBean paramsTSuite, final ServletContext ctx) {  	
	  	//Definimos el manejador de excepciones
	  	Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
		    public void uncaughtException(Thread th, Throwable ex) {
			System.out.println("Uncaught exception: " + ex);
			}
		};
	  	
		//Nombreamos el group con los datos de la TSuite
	  	ThreadGroup tg1 = new ThreadGroup(TSuiteThreadsManager.getLocatorThreadTestSuite(paramsTSuite));
	    Thread test = new Thread(tg1, new Runnable() {
	        public void run(){
	          	try {
	        	    Test80mng.execSuiteByProgramaticXML(paramsTSuite);
	          	}
	          	catch (Throwable e) {
	          		e.printStackTrace();
	          	}
	        }
	    });
	  	
	  	test.start();
	  	
	  	//Nombramos el Thread con los datos de la TSuite
		test.setName(TSuiteThreadsManager.getLocatorThreadTestSuite(paramsTSuite));
	  	return test.getId();
	}
  	
	
	public static ParamsBean storeParamsFromHttpRequest(HttpServletRequest request) {
		//Parameters that come from index.jsp
	    ParamsBean paramsTSuite = new ParamsBean();
	    paramsTSuite.setSuiteName(request.getParameter(Test80mng.SuiteNameParam));
	    paramsTSuite.setChannel(request.getParameter(Test80mng.ChannelNameParam));
	    paramsTSuite.setBrowser(request.getParameter(Test80mng.BrowserNameParam));
	    paramsTSuite.setAppE(request.getParameter(Test80mng.AppNameParam));
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
	    paramsTSuite.setEnvioCorreo(request.getParameter(Test80mng.EnvioCorreo));
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
	    
	    //Other
	    paramsTSuite.setApplicationDNS(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()); 
	    
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
	
	public boolean browserTasksRunning(String browser) throws Exception {
		Process p = Runtime.getRuntime().exec("tasklist");
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";
		while ((line = reader.readLine()) != null) {
			if (line.toLowerCase().contains(browser + ".exe"))
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
					document.getElementById("contenidoAjax").innerHTML="<b style=\"color:red\">Problemas técnicos en la ejecución del test!</b>";
				}
			});
	}
	<%
	}
	%>
	</script>
</body>
</html>