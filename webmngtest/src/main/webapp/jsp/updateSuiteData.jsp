
<%response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);%>
<%@ page language="java" contentType="text/html"%>
<%@page session="false"%>
<%@ page import="org.pruebasws.jdbc.dao.TestsDAO"%>
<%@ page import="org.pruebasws.jdbc.dao.TestsDAO.DataTSuiteChangeable"%>
<%
String suite = request.getParameter("suite");
String channel = request.getParameter("channel");
String dataToChange = request.getParameter("dataToChange");
String newData = request.getParameter("newData");
String[] newDataMOption = request.getParameterValues("newData[]");

String retorno = "false";
DataTSuiteChangeable dataToChangeEnum = DataTSuiteChangeable.valueOf(dataToChange);
int result = 0;
try {
	result = TestsDAO.updateDataTestSuite(suite, channel, dataToChangeEnum, newData, newDataMOption);
}
catch (Exception e) {
    e.printStackTrace();
}
	
if (result > 0) 
    retorno = "true";
  
out.print(retorno);
%>