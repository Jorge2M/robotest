package com.mng.testmaker.service.testreports;

import java.io.File;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.mail.internet.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.utils;
import com.mng.testmaker.utils.controlTest.FmwkTest;
import com.mng.testmaker.utils.mail.MailClient;
import com.mng.testmaker.utils.mail.beans.AttachMail;


public class CorreoReport {
    static Logger pLogger = LogManager.getLogger(FmwkTest.log4jLogger); 
    
    /**
     * @param args[0] - del tipo http://robottest.mangodev.net (+:port si fuera preciso)
     * 
     */
    public static void main(String[] args) throws Exception { 
        String serverDNS = args[0];
        String html = construirHTMLmail(serverDNS);
        sendMailResult(html);
    }
	
    //Construye el HTML del correo con la lista de tests ejecutados
    private static String construirHTMLmail(String serverDNS) {
        //Fecha actual - 13 horas
        //Date fechaDesde = new Date(System.currentTimeMillis() - 3600000 /*1 horas*/);
        Date fechaDesde = new Date(System.currentTimeMillis() - 50400000 /*14 horas*/);	    
        //Date fechaDesde = new Date(System.currentTimeMillis() - 46800000 /*13 horas*/);
        Date fechaHasta = new Date(System.currentTimeMillis());
		
        //Obtenemos las suites ejecutadas en las últimas 13 horas
        ArrayList<Suite> suiteBD = SuitesDAO.listSuitesDesde(fechaDesde);
		
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");

        String html =
            "<p style=\"font:12pt Arial;\">" +
            "Hola, <br><br>" +
    	    "en las últimas 13 horas [" + sdf.format(fechaDesde) + " - " + sdf.format(fechaHasta) + "] se han ejecutado los siguientes scripts automáticos contra PRO:" +
    	    "</p>";

        html+=constuctTableMail(suiteBD, serverDNS);
        return html;
    }
	
    //Construye la tabla de resultados de ejecución de los tests
    public static String constuctTableMail(ArrayList<Suite> listSuites, String serverDNS) {
        float totalOKs = 0;
        float totalIFs = 0;
        float totalNKs = 0;
        float totalWRs = 0;
        float totalDEs = 0;
        float totalSKs = 0;
        float totalDisp = 0;
        double totalMINs = 0;
        DecimalFormat df1 = new DecimalFormat("0.00");
        DecimalFormat df2 = new DecimalFormat("0");
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
    	
        for (int i=0; i<listSuites.size(); i++) {
            String pathReport = listSuites.get(i).getPathReport();
            String fechaSuite = listSuites.get(i).getIdExecution();
            String nombreSuite = listSuites.get(i).getSuiteName(); 
            File fileReport = new File(pathReport);
    		
            //Contamos los OKs, WARNINGs, NOKs y SKIPs
            String numOKs = String.valueOf(MethodsDAO.numMethdsInState(fechaSuite, nombreSuite, State.Ok));
            String numDEs = String.valueOf(MethodsDAO.numMethdsInState(fechaSuite, nombreSuite, State.Defect));    		
            String numWRs = String.valueOf(MethodsDAO.numMethdsInState(fechaSuite, nombreSuite, State.Warn));
            String numIFs = String.valueOf(MethodsDAO.numMethdsInState(fechaSuite, nombreSuite, State.Info));
            String numNKs = String.valueOf(MethodsDAO.numMethdsInState(fechaSuite, nombreSuite, State.Nok));
            String numSKs = String.valueOf(MethodsDAO.numMethdsInState(fechaSuite, nombreSuite, State.Skip));
    		
            String numDisps = String.valueOf(Integer.valueOf(numOKs) + Integer.valueOf(numIFs) + Integer.valueOf(numWRs) + Integer.valueOf(numDEs));
            String tiempoSegs = ""; 
            if (listSuites.get(i).getTimeMs()!=null) {
                tiempoSegs = df1.format(Double.valueOf(listSuites.get(i).getTimeMs()).doubleValue() / 1000 / 60);
            }
    		
            //Acumulamos los datos
            totalOKs += Integer.valueOf(numOKs);
            totalIFs += Integer.valueOf(numIFs);
            totalNKs += Integer.valueOf(numNKs);
            totalWRs += Integer.valueOf(numWRs);
            totalDEs += Integer.valueOf(numDEs);
            totalSKs += Integer.valueOf(numSKs);
            totalDisp += Integer.valueOf(numDisps);
            if (listSuites.get(i).getTimeMs()!=null) {
                totalMINs += Double.valueOf(listSuites.get(i).getTimeMs()) / 1000 / 60;
            }
    		
            if (numOKs.compareTo("0")==0) {
            	numOKs = "";
            }
            if (numIFs.compareTo("0")==0) {
            	numIFs = "";
            }
            if (numWRs.compareTo("0")==0) {
            	numWRs = "";
            }
            if (numDEs.compareTo("0")==0) {
            	numDEs = "";
            }
            if (numNKs.compareTo("0")==0) {
            	numNKs = "";
            }
            if (numSKs.compareTo("0")==0) {
            	numSKs = "";
            }
            if (numDisps.compareTo("0")==0) {
            	numDisps = "";
            }
    		
            html+= 
    		"<tr>" + 
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + listSuites.get(i).getIdExecution() + "</td>" + 
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + listSuites.get(i).getSuiteName() + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + listSuites.get(i).getApplication() + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + listSuites.get(i).getChannel() + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + listSuites.get(i).getBrowser() + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + listSuites.get(i).getVersion() + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + "darkGreen" + ";\">" + numDisps + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Ok.getColorCss() + ";\">" + numOKs + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Nok.getColorCss() + ";\">" + numNKs + "</td>" +
                "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Defect.getColorCss() + ";\">" + numDEs + "</td>" +    		
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Warn.getColorCss() + ";\">" + numWRs + "</td>" +
                "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Info.getColorCss() + ";\">" + numIFs + "</td>" +    		
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Skip.getColorCss() + ";\">" + numSKs + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:right;\">" + tiempoSegs + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + listSuites.get(i).getStateSuite() + "</td>" +
    		"<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\"><a href='" + utils.obtainDNSFromFile(fileReport.getAbsolutePath(), serverDNS).replace("\\", "/") + "'>Report HTML</a></td>" +
    		"</tr>";
        }
    
        html+="</tbody>";
    	
        //Totales
        float totalCasos = totalOKs + totalNKs + totalDEs + totalWRs + totalIFs + totalSKs;
        html+=
            "<tfoot style=\"font-weight: bold;\">" +
    	    "<tr style=\"border:none;\">" +
    	    "<td colspan=\"6\"></td>" +
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + "darkGreen" + ";\">" + df2.format(totalDisp) + "</td>" +
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Ok.getColorCss() + ";\">" + df2.format(totalOKs) + "</td>" +
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Nok.getColorCss() + ";\">" + df2.format(totalNKs) + "</td>" +
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Defect.getColorCss() + ";\">" + df2.format(totalDEs) + "</td>" +    	    
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Warn.getColorCss() + ";\">" + df2.format(totalWRs) + "</td>" +
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Info.getColorCss() + ";\">" + df2.format(totalIFs) + "</td>" +    	    
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Skip.getColorCss() + ";\">" + df2.format(totalSKs) + "</td>" +
    	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:right;\">" + df1.format(totalMINs) + "</td>" +
    	    "<td colspan=\"2\"></td>" +
    	    "</tr>" +
    		
   	    "<tr style=\"border:none;\">" +
   	    "<td colspan=\"6\"></td>" +
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + "darkGreen" + ";\">" + df1.format(((totalDisp / totalCasos) * 100)) + "%</td>" +
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Ok.getColorCss() + ";\">" + df1.format(((totalOKs / totalCasos) * 100)) + "%</td>" +
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Nok.getColorCss() + ";\">" + df1.format(((totalNKs / totalCasos) * 100)) + "%</td>" +
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Defect.getColorCss() + ";\">" + df1.format(((totalDEs / totalCasos) * 100)) + "%</td>" +    	    
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Warn.getColorCss() + ";\">" + df1.format(((totalWRs / totalCasos) * 100)) + "%</td>" +
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Info.getColorCss() + ";\">" + df1.format(((totalIFs / totalCasos) * 100)) + "%</td>" +    	    
   	    "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:" + State.Skip.getColorCss() + ";\">" + df1.format(((totalSKs / totalCasos) * 100)) + "%</td>" +
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
        pLogger.info("Fin Test... procedemos a envío del correo!");
        new MailClient().mail(from, myToList, myCcList, subject, message, listaAttachImages);
        pLogger.info("Correo enviado!");
    }	
}
