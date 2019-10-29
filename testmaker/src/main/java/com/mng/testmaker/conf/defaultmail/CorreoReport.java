package com.mng.testmaker.conf.defaultmail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.DecimalFormat;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.mail.internet.*;

import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.conf.defaultstorer.StorerResultSQLite;
import com.mng.testmaker.domain.PersistorDataI;
import com.mng.testmaker.domain.data.SuiteData;
import com.mng.testmaker.domain.data.TestCaseData;
import com.mng.testmaker.repository.jdbc.dao.SuitesDAO;
import com.mng.testmaker.repository.jdbc.dao.TestCasesDAO;


public class CorreoReport {
    
    /**
     * @param args[0] - del tipo http://robottest.mangodev.net (+:port si fuera preciso)
     * 
     */
    public static void main(String[] args) throws Exception { 
        String serverDNS = args[0];
        PersistorDataI persistor = new StorerResultSQLite();
        String html = construirHTMLmail(serverDNS, persistor);
        sendMailResult(html);
    }
	
    //Construye el HTML del correo con la lista de tests ejecutados
    private static String construirHTMLmail(String serverDNS, PersistorDataI persistor) throws Exception {
        //Fecha actual - 13 horas
        //Date fechaDesde = new Date(System.currentTimeMillis() - 3600000 /*1 horas*/);
        Date fechaDesde = new Date(System.currentTimeMillis() - 50400000 /*14 horas*/);	    
        //Date fechaDesde = new Date(System.currentTimeMillis() - 46800000 /*13 horas*/);
        Date fechaHasta = new Date(System.currentTimeMillis());
		
        SuitesDAO suitesDAO = new SuitesDAO(persistor);
        List<SuiteData> listSuites = suitesDAO.getListSuitesAfter(fechaDesde);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");

        String html =
            "<p style=\"font:12pt Arial;\">" +
            "Hola, <br><br>" +
    	    "en las últimas 13 horas [" + sdf.format(fechaDesde) + " - " + sdf.format(fechaHasta) + "] se han ejecutado los siguientes scripts automáticos contra PRO:" +
    	    "</p>";

        html+=constuctTableMail(listSuites, serverDNS, persistor);
        return html;
    }
	
    public static String constuctTableMail(List<SuiteData> listSuites, String serverDNS, PersistorDataI persistor) 
    throws Exception {

        String html = 	
            "<table border=\"0\" style=\"font:12pt Arial; margin:-8px 0 0; border-collapse:collapse; text-align:left;\"><thead>" +
            "<tr style=\"background-color:#11F411;border:1px solid #000000;border-collapse:collapse;\">" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">ID TEST</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">TIPO TEST</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">APPLICATION</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">CHANNEL</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">BROWSER</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">VERSIÓN</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">#AVAILABILITY</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">#OKs</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">#NOKs</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">#DEFECTs</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">#WARNs</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">#INFOs</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">#SKIPs</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">MINUTOS</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">STATE</th>" +
            "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">REPORT</th>" +
            "</tr>" +
            "</thead>" +
            "<tbody>";
    	
        DecimalFormat df1 = new DecimalFormat("0.00");
        DecimalFormat df2 = new DecimalFormat("0");
        Map<State,Integer> testCasesStateAcc = getInitZeroValues();
        float totalDisp = 0;
        double totalMINs = 0;
        for (SuiteData suite : listSuites) {
        	String nameSuite = suite.getName();
        	String idSuite = suite.getIdExecSuite();
        	TestCasesDAO testCasesDAO = new TestCasesDAO(persistor);
        	List<TestCaseData> listTestCases = testCasesDAO.getListTestCases(idSuite);
        	Map<State,Integer> testCasesState = mapNumberTestCasesInState(listTestCases);
            Integer numDisps = 
            	testCasesState.get(State.Ok) + 
            	testCasesState.get(State.Info) + 
            	testCasesState.get(State.Warn) + 
            	testCasesState.get(State.Defect);
            
            String tiempoSegs = df1.format(Double.valueOf(suite.getDurationMillis()).doubleValue() / 1000 / 60);
            accumulateData(testCasesStateAcc, testCasesState);
            totalDisp+=Integer.valueOf(numDisps);
            totalMINs += Double.valueOf(suite.getDurationMillis()) / 1000 / 60;
    		
            html+= 
    		"<tr>" + 
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + idSuite + "</td>" + 
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + nameSuite + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + suite.getApp() + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + suite.getChannel() + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + suite.getWebDriverType() + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + suite.getVersion() + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + "darkGreen" + ";\">" + numDisps + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Ok.getColorCss() + ";\">" + getNumTestCasesStr(testCasesState.get(State.Ok)) + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Nok.getColorCss() + ";\">" + getNumTestCasesStr(testCasesState.get(State.Nok)) + "</td>" +
            "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Defect.getColorCss() + ";\">" + getNumTestCasesStr(testCasesState.get(State.Defect)) + "</td>" +    		
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Warn.getColorCss() + ";\">" + getNumTestCasesStr(testCasesState.get(State.Warn)) + "</td>" +
            "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Info.getColorCss() + ";\">" + getNumTestCasesStr(testCasesState.get(State.Info)) + "</td>" +    		
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Skip.getColorCss() + ";\">" + getNumTestCasesStr(testCasesState.get(State.Skip)) + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:right;\">" + tiempoSegs + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + suite.getStateExecution() + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\"><a href='" + suite.getUrlReportHtml().replace("\\", "/") + "'>Report HTML</a></td>" +
    		"</tr>";
        }
    
        html+="</tbody>";
        Integer totalCasos =             	
        	testCasesStateAcc.get(State.Ok) + 
            testCasesStateAcc.get(State.Nok) + 
            testCasesStateAcc.get(State.Warn) + 
            testCasesStateAcc.get(State.Defect) +
        	testCasesStateAcc.get(State.Info) + 
        	testCasesStateAcc.get(State.Skip);
        
        html+=
            "<tfoot style=\"font-weight: bold;\">" +
    	    "<tr style=\"border:none;\">" +
    	    "<td colspan=\"6\"></td>" +
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + "darkGreen" + ";\">" + df2.format(totalDisp) + "</td>" +
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Ok.getColorCss() + ";\">" + getNumTestCasesStr(testCasesStateAcc.get(State.Ok)) + "</td>" +
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Nok.getColorCss() + ";\">" + getNumTestCasesStr(testCasesStateAcc.get(State.Nok)) + "</td>" +
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Defect.getColorCss() + ";\">" + getNumTestCasesStr(testCasesStateAcc.get(State.Defect)) + "</td>" +    	    
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Warn.getColorCss() + ";\">" + getNumTestCasesStr(testCasesStateAcc.get(State.Warn)) + "</td>" +
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Info.getColorCss() + ";\">" + getNumTestCasesStr(testCasesStateAcc.get(State.Info)) + "</td>" +    	    
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Skip.getColorCss() + ";\">" + getNumTestCasesStr(testCasesStateAcc.get(State.Skip)) + "</td>" +
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:right;\">" + df1.format(totalMINs) + "</td>" +
    	    "<td colspan=\"2\"></td>" +
    	    "</tr>" +
    		
   	    "<tr style=\"border:none;\">" +
   	    "<td colspan=\"6\"></td>" +
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + "darkGreen" + ";\">" + df1.format(((totalDisp / totalCasos) * 100)) + "%</td>" +
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Ok.getColorCss() + ";\">" + df1.format(((testCasesStateAcc.get(State.Ok) / totalCasos) * 100)) + "%</td>" +
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Nok.getColorCss() + ";\">" + df1.format(((testCasesStateAcc.get(State.Nok) / totalCasos) * 100)) + "%</td>" +
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Defect.getColorCss() + ";\">" + df1.format(((testCasesStateAcc.get(State.Defect) / totalCasos) * 100)) + "%</td>" +    	    
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Warn.getColorCss() + ";\">" + df1.format(((testCasesStateAcc.get(State.Warn) / totalCasos) * 100)) + "%</td>" +
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Info.getColorCss() + ";\">" + df1.format(((testCasesStateAcc.get(State.Info) / totalCasos) * 100)) + "%</td>" +    	    
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Skip.getColorCss() + ";\">" + df1.format(((testCasesStateAcc.get(State.Skip) / totalCasos) * 100)) + "%</td>" +
   	    "<td colspan=\"3\"></td>" +
   	    "</tr>" +
   	    "</tfoot>";				
    			
        html+="</table>";
        html+="<br>";		
        html+="<p style=\"font:12pt Arial;\">";
        html+="Un saludo,<br>";
        html+="El Robot de Test";
        html+="</p>";
    	    
        return html;
    }
    
    private static String getNumTestCasesStr(int numTestCases) {
    	if (numTestCases!=0) {
    		return String.valueOf(numTestCases);
    	}
    	return "";
    }
    
    private static Map<State,Integer> getInitZeroValues() {
    	Map<State,Integer> mapReturn = new HashMap<>();
    	for (State state : State.values()) {
    		mapReturn.put(state, 0);
    	}
    	return mapReturn;
    }
    
    private static Map<State,Integer> mapNumberTestCasesInState(List<TestCaseData> listTestCases) {
    	Map<State,Integer> mapReturn = getInitZeroValues();
    	for (TestCaseData testCase : listTestCases) {
    		mapReturn.put(
    			testCase.getResult(), 
    			mapReturn.get(testCase.getResult())+1);
    	}
    	return mapReturn;
    }
    
    private static void accumulateData(Map<State,Integer> mapToAccumulate, Map<State,Integer> mapToAdd) {
    	for (State state : State.values()) {
    		Integer numToAdd = mapToAdd.get(state);
    		Integer numAcc = mapToAccumulate.get(state);
    		mapToAccumulate.put(state, numAcc + numToAdd);
    	}
    }
	
    private static void sendMailResult(String message) throws Exception {
        ArrayList<AttachMail> listaAttachImages = new ArrayList<>(); 
		  
        //Se realiza el envío del correo
        String from = "robottestmango@gmail.com";
        //String from = "Robotest QA<jorge.munoz@mango.com>";
        //String from = "jorge.munoz@mango.com";

        InternetAddress[] myToList = InternetAddress.parse("eqp.ecommerce.qamango@mango.com,");
        InternetAddress[] myCcList = InternetAddress.parse(
        	"jordi.pereta@mango.com," + 
        	"antonio.hernandez@mango.com," +
        	"eqp.ecommerce.payments@mango.com," +
        	"eqp.ebusiness.test@mango.com");
        //InternetAddress[] myToList = InternetAddress.parse("jorge.munoz@mango.com");
        //InternetAddress[] myCcList = InternetAddress.parse("jorge.munoz@mango.com");
	      
        String subject="Resultado tests últimas 12 horas";  
		  
        //Envío del correo
        Log4jConfig.pLogger.info("Fin Test... procedemos a envío del correo!");
        new MailClient().mail(from, myToList, myCcList, subject, message, listaAttachImages);
        Log4jConfig.pLogger.info("Correo enviado!");
    }	
}
