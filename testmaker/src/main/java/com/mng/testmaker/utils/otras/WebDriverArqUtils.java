package com.mng.testmaker.utils.otras;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import javax.net.ssl.*;

import org.testng.*;

import com.mng.testmaker.utils.DataFmwkTest;
import com.mng.testmaker.utils.controlTest.fmwkTest;
import com.mng.testmaker.utils.controlTest.fmwkTest.TypeEvidencia;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que recopila la apliaci�n de funcionalidades para Selenium. En un futuro se podr�a estudiar c�mo a�adir directamente a la libreria de Selenium
 * para poder ejecutarla normamente desde el driverSelenium como cualquier funcionalidad nativa.
 * @author jorge.munoz
 *
 */

public class WebDriverArqUtils {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public static void captureEntirePageMultipleBrowsers (WebDriver driver, ITestContext contextTNG, String filename) 
    throws RuntimeException {
        boolean browserGUI = true;
        if (contextTNG.getAttribute("browserGUI")!=null) {
            browserGUI = ((Boolean)contextTNG.getAttribute("browserGUI")).booleanValue();
        }
        if (driver != null && browserGUI) {
            try {
                //Este código sólo se ejecuta si no se ha producido una excepción (x timeout) en waitForPageLoaded;
                WebDriver newWebDriver = null;
                if (driver.getClass() == RemoteWebDriver.class) {
                    newWebDriver = new Augmenter().augment(driver);
                } else {
                    newWebDriver = driver;
                }
                
                File screenshot = ((TakesScreenshot)newWebDriver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File(filename));
            }
            catch(Exception e) {
                //No lanzaremos excepción (para no enmascarar una posible excepción previa)
                //throw new RuntimeException(e);
                pLogger.error("Problem capturing Page and store in file {}", filename, e);
            }
        }
    }
	
    public static void acceptAllCertificates () {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                
                @Override
                public void checkClientTrusted (java.security.cert.X509Certificate[] certs, String authType) {
                    //
		}
                
                @Override
                public void checkServerTrusted (java.security.cert.X509Certificate[] certs, String authType) {
                    //
                }
            }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } 
        catch (Exception e) {
            //
        }
    }

    // Just add these two functions in your program 
    public static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
 
        @SuppressWarnings("unused")
        public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }
 
        @SuppressWarnings("unused")
        public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }
 
        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) 
        throws java.security.cert.CertificateException {
            return;
        }
 
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) 
        throws java.security.cert.CertificateException {
            return;
        }
    }

 
    @SuppressWarnings("unused")
    private static void trustAllHttpsCertificates() throws Exception {
        //  Create a trust manager that does not validate certificate chains:
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(
        sc.getSocketFactory());
    }
    
    /**
     * Se realiza una captura del HTML de la página
     */
    public static void capturaHTMLPage(DataFmwkTest dFTest, int stepNumber) throws Exception {
        try {
            String methodWithFactory = fmwkTest.getMethodWithFactory(dFTest.meth, dFTest.ctx);
            String nombreHTMLfile = fmwkTest.getPathFileEvidenciaStep(dFTest.ctx, methodWithFactory, stepNumber, TypeEvidencia.html);
            File htmlFile = new File(nombreHTMLfile);
            try (FileWriter fw = new FileWriter(htmlFile)) {
                fw.write(dFTest.driver.getPageSource());
            }
        } 
        catch (Exception e) {
            throw e;
        } 
    }    
    
    /**
     * Retorna el log de errores de WebDriver entre los que se encuentran los JavaScript
     * El nivel de errores de cada uno de los tipos se define en la creación del WebDriver
     * Detecta el caso en que no se había superado el máximo de errores en todos los errores detectados -> No mostraremos warning
     * Nota: No funciona con GeckoDriver porque no están implementados los servicios al no formar parte del protocolo W3C https://github.com/w3c/webdriver/issues/406
     */
    public static ResultadoErrores getLogErrors(Level levelFrom, WebDriver webdriver, int maxErrors, ITestContext context) 
    throws Exception {
        ResultadoErrores resultado = new ResultadoErrores();
        resultado.setResultado(ResultadoErrores.Resultado.OK);
        ArrayList<String> listaLogError = new ArrayList<>();
        List<Boolean> supMaximosList = new ArrayList<>();
        try {
            Logs logs = webdriver.manage().logs();
            Iterator<String> itTiposLog = logs.getAvailableLogTypes().iterator();
            while (itTiposLog.hasNext()) {
                String tipoLog = itTiposLog.next();
                LogEntries logEntries = logs.get(tipoLog);
                int j = 0;
                for (LogEntry logEntry : logEntries) {
                    if (logEntry.getLevel().intValue() >= levelFrom.intValue()) {
                        resultado.setResultado(ResultadoErrores.Resultado.ERRORES);
                        String descError = "<br><b>Error Log!</b> Tipo: " + tipoLog.toUpperCase() + " | Level: " + logEntry.getLevel() + " | Descripción: " + logEntry.getMessage();
                        listaLogError.add("<br>" + descError);
            
                        int numErrors = 0;
                        if (context.getAttribute(descError) != null) {
                            numErrors = ((Integer)context.getAttribute(descError)).intValue();
                        }
            
                        // Comprobamos si ya se había superado el máximo de errores
                        if (j == 0) {
                            Boolean supMaximo = Boolean.valueOf(numErrors >= maxErrors);
                            supMaximosList.add(supMaximo);
                        }
            
                        numErrors += 1;
                        context.setAttribute(descError, Integer.valueOf(numErrors));
                        j += 1;
                    }
                }
            }
        }
        catch (Exception e) {
            //Parece que en el caso de Safari se produce una excepción al cargar algunos logs como el de 'performance'
            pLogger.warn("Problem to load the WebDriver error Log", e);
        }

        // Retornamos la lista de errores y el estado
        resultado.setListaLogError(listaLogError);
        if (resultado.getResultado() == ResultadoErrores.Resultado.ERRORES && supMaximosList.indexOf(Boolean.valueOf(false)) < 0) {
            resultado.setResultado(ResultadoErrores.Resultado.MAX_ERRORES);
        }
        return resultado;
    }
}