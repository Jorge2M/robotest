package com.mng.robotest.test80.arq.listeners;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import javax.mail.internet.InternetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;

import com.mng.robotest.test80.CorreoReport;
import com.mng.robotest.test80.arq.jdbc.dao.CorreosDAO;
import com.mng.robotest.test80.arq.jdbc.dao.SuitesDAO;
import com.mng.robotest.test80.arq.jdbc.to.Suite;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.mail.MailClient;
import com.mng.robotest.test80.arq.utils.mail.beans.AttachMail;
import com.mng.robotest.test80.arq.utils.otras.Constantes;

@SuppressWarnings("javadoc")
public class EmailEndSuite {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    final String from = "Robotest QA<jorge.munoz.sge@mango.com>";
    String toList = null;
    String CcList = null;
    String Asunto = null;
    String envioCorreo = null; //group of recipients
    String siempreMail = null; //"true" o "false". Indicamos que el mail sólo se envíe cuando se produce un NOK en el test
    
    public String getToList() {
        return this.toList;
    }
    
    public void setToList(String toList) {
        this.toList = toList;
    }
    
    public String getCcList() {
        return this.CcList;
    }
    
    public void setCcList(String CcList) { 
        this.CcList = CcList;
    }
    
    public String getAsunto() {
        return this.Asunto;
    }
    
    public void setAsunto(String asunto) {
        this.Asunto = asunto;
    }
    
    public String getEnvioCorreo() {
        return this.envioCorreo;
    }
    
    public void setEnvioCorreo(String envioCorreo) {
        this.envioCorreo = envioCorreo;
    }
    
    public String getSiempreMail() {
        return this.siempreMail;
    }
    
    public boolean isSiempreMail() {
        return ("true".compareTo(getSiempreMail())==0);
    }
    
    public void setSiempreMail(String siempreMail) {
        this.siempreMail = siempreMail;
    }
    
    /**
     * Envía un correo con el report pero sólo en caso que: se haya producido algún error + no se haya superado el máximo de correos x intervalo
     * @param maxCorreos: número máximo de correos por periodo de tiempo
     * @param periodo: periodo de tiempo (en minutos) sobre el que aplica maxCorreos
     */
    public void sendMail(int maxCorreos, int periodoMin, ITestContext context) {
        sendMail(maxCorreos, periodoMin, null/*httpUrlCallBack*/, context);
    }
    
    public void sendMail(int maxCorreos, int periodoMin, HttpURLConnection httpUrlCallBack, ITestContext context) {
        try {
            InternetAddress[] myToList = InternetAddress.parse(this.toList);
            InternetAddress[] myCcList = InternetAddress.parse(getCcList());
            ArrayList<AttachMail> listaAttachImages = new ArrayList<>();
            int numCorreos = CorreosDAO.getCorreosEnviados(periodoMin, context);
            if (numCorreos < maxCorreos) {
                Suite suiteObj = SuitesDAO.getSuite(context.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx), context.getSuite().getName());
                if (suiteObj!=null) {
                    //Revisamos si alguna de las suites no es OK
                    boolean suiteOK = true;
                    if (Integer.valueOf(suiteObj.getResultScript()).intValue() > State.Warn.getCriticity())
                        suiteOK = false;
                                
                    if (suiteOK)
                        setAsunto(getAsunto() + " (OK)");
                    else
                        setAsunto(getAsunto() + " (Con problemas)");
                                
                    if (isSiempreMail() || (!suiteOK)) {
                        //Construimos el mensaje HTML contenido en el correo
                        String mensajeHTML =
                            "<p style=\"font:12pt Arial;\">" +
                            "Hola, <br><br>" +
                            "se ha ejecutado el siguiente script:" +
                            "</p>";
                          
                        ArrayList<Suite>list1Suite = new ArrayList<>();
                        list1Suite.add(suiteObj);
                        mensajeHTML+=CorreoReport.constuctTableMail(list1Suite, context.getCurrentXmlTest().getParameter(Constantes.paramApplicationDNS));
                        
                        if (httpUrlCallBack!=null) {
                            mensajeHTML+=
                                "<p style=\"font:12pt Arial;\">" +
                                "<b>Nota</b>: se ha lanzado la URL de CallBack " + httpUrlCallBack.getURL() + " (con resultado <b>" + httpUrlCallBack.getResponseCode() + "</b>)" + 
                                "</p>";
                        }
                        
                        pLogger.info(". Procedemos a enviar correo! {} en la última hora", Integer.valueOf(numCorreos + 1));
                        new MailClient().mail(this.from, myToList, myCcList, getAsunto() , mensajeHTML, listaAttachImages);
                        pLogger.info("Correo enviado!");
                        CorreosDAO.grabarCorreoEnviado(context);
                    }
                }
            }
            else
                pLogger.warn("{}. No enviamos correo! {} en la última hora", getAsunto(), Integer.valueOf(numCorreos));
        }
        catch (Exception e) {
            pLogger.fatal("Problem sending mail", e);
        }
    }    
}