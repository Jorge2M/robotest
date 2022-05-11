package com.mng.robotest.access;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.mail.internet.*;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.defaultmail.AttachMail;
import com.github.jorge2m.testmaker.conf.defaultmail.MailClient;
import com.github.jorge2m.testmaker.domain.RepositoryI;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.service.TestMaker;


public class CorreoReport {

	public static void main(String[] args) throws Exception { 
		System.out.println("Inicio...");
		String htmlBody = buildHtmlBody();
		sendMailResult(htmlBody);
	}
	
	private static String buildHtmlBody() throws Exception {
		//Fecha actual - 13 horas
		//Date fechaDesde = new Date(System.currentTimeMillis() - 3600000 /*1 horas*/);
		//Date fechaDesde = new Date(System.currentTimeMillis() - 50400000 /*14 horas*/);
		//Date fechaDesde = new Date(System.currentTimeMillis() - 54000000 /*15 horas*/);
		Date fechaDesde = new Date(System.currentTimeMillis() - 46800000 /*13 horas*/);
		//Date fechaDesde = new Date(System.currentTimeMillis() - 84800000 /*24 horas*/);
		//Date fechaDesde = new Date(System.currentTimeMillis() - 168000000 /*48 horas*/);
		Date fechaHasta = new Date(System.currentTimeMillis());
		RepositoryI repository = TestMaker.getRepository();
		List<SuiteBean> listSuites = repository.getListSuitesBetween(fechaDesde, null);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");

		return
			"<p style=\"font:12pt Arial;\">" +
			"Hola, <br><br>" +
			"en las últimas 13 horas [" + sdf.format(fechaDesde) + " - " + sdf.format(fechaHasta) + "] se han ejecutado los siguientes scripts automáticos contra PRO:" +
			"</p>" +
			TestMaker.getHtmlStatsSuites(listSuites, null);
	}
	
	private static void sendMailResult(String message) throws Exception {
		ArrayList<AttachMail> listaAttachImages = new ArrayList<>(); 
		  
		//Se realiza el envío del correo
		//String from = "Robotest QA<jorge.munoz@mango.com>";
		//String from = "jorge.munoz@mango.com";

		InternetAddress[] myToList = InternetAddress.parse("eqp.ecommerce.qamango@mango.com,");
		InternetAddress[] myCcList = InternetAddress.parse(
			"eqp.ecommerce.payments@mango.com," +
			"eqp.ebusiness.test@mango.com");
//			InternetAddress[] myToList = InternetAddress.parse("jorge.munoz.sge@mango.com");
//			InternetAddress[] myCcList = InternetAddress.parse("jorge.munoz.sge@mango.com");

		String subject="Resultado tests últimas 13 horas";  

		//Envío del correo
		Log4jTM.getGlobal().info("Procedemos a envío del correo!");
		new MailClient().mail("robottestmango@gmail.com", "sirrobot", myToList, myCcList, subject, message, listaAttachImages);
		Log4jTM.getGlobal().info("Correo enviado!");
	}	
}
