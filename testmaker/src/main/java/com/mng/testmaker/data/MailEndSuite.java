package com.mng.testmaker.data;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;

import com.mng.testmaker.domain.InputParamsTestMaker;
import com.mng.testmaker.domain.SuiteContextTestMaker;
import com.mng.testmaker.jdbc.dao.SuitesDAO;
import com.mng.testmaker.jdbc.to.Suite;
import com.mng.testmaker.listeners.CorreoReport;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.controlTest.FmwkTest;
import com.mng.testmaker.utils.mail.MailClient;
import com.mng.testmaker.utils.mail.beans.AttachMail;

public class MailEndSuite {
	
    static Logger pLogger = LogManager.getLogger(FmwkTest.log4jLogger);
	
    private final String from = "Robotest QA<jorge.munoz.sge@mango.com>";
	private final List<String> toMail;
	private final List<String> ccMail;
	private final String subjectMail;

	private MailEndSuite(InputParamsTestMaker inputData) {
		toMail = inputData.getMails();
		ccMail = new ArrayList<>();
		ccMail.add("jorge.munoz.sge@mango.com");
		subjectMail = "Result TestSuite " + inputData.getSuiteName() + " (" + inputData.getApp() + " / " + inputData.getUrlBase() + ")";
	}
	
	public static MailEndSuite getNew(InputParamsTestMaker inputData) {
		if (inputData.isSendMailInEndSuite()) {
			return (new MailEndSuite(inputData));
		}
		return null;
	}
	
	private String getToMailCommaSeparated() {
		return String.join(",", toMail);
	}
	
	private String getCcMailCommaSeparated() {
		return String.join(",", ccMail);
	}
	
	private String getSubjectDependingResult(boolean resultSuiteOK) {
        if (resultSuiteOK) {
            return (subjectMail + " (OK)");
        } else {
            return (subjectMail + " (With Problems)");
        }
	}
	
    public void sendMail(ITestContext context) {
        sendMail(null, context);
    }
    
    public void sendMail(HttpURLConnection httpUrlCallBack, ITestContext context) {
        try {
            InternetAddress[] myToList = InternetAddress.parse(getToMailCommaSeparated());
            InternetAddress[] myCcList = InternetAddress.parse(getCcMailCommaSeparated());
            ArrayList<AttachMail> listaAttachImages = new ArrayList<>();
            
        	SuiteContextTestMaker testMakerCtx = SuiteContextTestMaker.getTestMakerContext(context);
        	InputParamsTestMaker inputData = testMakerCtx.getInputData();
            Suite suiteObj = SuitesDAO.getSuite(testMakerCtx.getIdSuiteExecution(), context.getSuite().getName());
            if (suiteObj!=null) {
                //Revisamos si alguna de las suites no es OK
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
                
                if (httpUrlCallBack!=null) {
                    mensajeHTML+=
                        "<p style=\"font:12pt Arial;\">" +
                        "<b>Nota</b>: se ha lanzado la URL de CallBack " + httpUrlCallBack.getURL() + " (con resultado <b>" + httpUrlCallBack.getResponseCode() + "</b>)" + 
                        "</p>";
                }
                
                pLogger.info(". Procedemos a enviar correo!");
                new MailClient().mail(this.from, myToList, myCcList, getSubjectDependingResult(suiteOK), mensajeHTML, listaAttachImages);
                pLogger.info("Correo enviado!");
            }
        }
        catch (Exception e) {
            pLogger.fatal("Problem sending mail", e);
        }
    }    
}
