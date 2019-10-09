package com.mng.testmaker.service.testreports;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;

import com.mng.testmaker.domain.InputParamsTestMaker;
import com.mng.testmaker.domain.SuiteTestMaker;
import com.mng.testmaker.jdbc.dao.SuitesDAO;
import com.mng.testmaker.jdbc.to.Suite;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.controlTest.FmwkTest;
import com.mng.testmaker.utils.mail.MailClient;
import com.mng.testmaker.utils.mail.beans.AttachMail;

public class DefaultMailEndSuite implements SenderMailEndSuite {
	
    static Logger pLogger = LogManager.getLogger(FmwkTest.log4jLogger);
	
    private final String from = "Robotest QA<jorge.munoz.sge@mango.com>";
    
	private String getSubjectDependingResult(boolean resultSuiteOK) {
        if (resultSuiteOK) {
            return (subjectMail + " (OK)");
        } else {
            return (subjectMail + " (With Problems)");
        }
	}
    
	@Override
    public void sendMail(SuiteTestMaker suite) {
		InputParamsTestMaker inputParams = suite.getInputData();
		List<String> toMails = inputParams.getMails();
		List<String> ccMails = new ArrayList<>();
		String subjectMail = 
			"Result TestSuite " + inputParams.getSuiteName() + 
			" (" + inputParams.getApp() + " / " + inputParams.getUrlBase() + ")";
        try {
            InternetAddress[] myToList = InternetAddress.parse(String.join(",", toMails));
            InternetAddress[] myCcList = InternetAddress.parse(String.join(",", ccMails));
            ArrayList<AttachMail> listaAttachImages = new ArrayList<>();
            boolean suiteOK = true;
            if (Integer.valueOf(suiteObj.getResultScript()).intValue() > State.Warn.getCriticity()) {
                suiteOK = false;
            }
                     
            String mensajeHTML =
                "<p style=\"font:12pt Arial;\">" +
                "Hola, <br><br>" +
                "se ha ejecutado el siguiente script:" +
                "</p>";
              
            ArrayList<Suite>list1Suite = new ArrayList<>();
            list1Suite.add(suiteObj);
            mensajeHTML+=CorreoReport.constuctTableMail(list1Suite, inputData.getWebAppDNS());
            
            pLogger.info(". Procedemos a enviar correo!");
            new MailClient().mail(this.from, myToList, myCcList, getSubjectDependingResult(suiteOK), mensajeHTML, listaAttachImages);
            pLogger.info("Correo enviado!");
        }
        catch (Exception e) {
            pLogger.fatal("Problem sending mail", e);
        }
    }    
}
