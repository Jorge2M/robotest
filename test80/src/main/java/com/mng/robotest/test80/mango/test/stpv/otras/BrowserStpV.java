package com.mng.robotest.test80.mango.test.stpv.otras;

import java.io.StringReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.sitemap.Sitemapindex;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.sitemap.Sitemapindex.Sitemap;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;

/**
 * Implementa steps/validaciones más propias del browser que de una página concreta (como p.e. la introducción de una URL concreta)
 * @author jorge.munoz
 *
 */

public class BrowserStpV {

    public static void inputRobotsURLandValidate(String urlBaseTest, AppEcom app, DataFmwkTest dFTest) throws Exception {
        URI uriBase = new URI(urlBaseTest);
        String urlRobots = urlBaseTest.replace(uriBase.getPath(), "") + "/" + "robots.txt";
        String urlSitemap = urlBaseTest.replace(uriBase.getPath(), "") + "/" + "sitemap.xml";
        
        //Step. Comprobamos la URL correspondiente a robots.txt
        DatosStep datosStep = new DatosStep(
            "Ejecutamos la URL del robots: " + urlRobots, 
            "Se carga el contenido correcto");
        try {
            dFTest.driver.get(urlRobots);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }
    
        //Validaciones.
        String contRobots1 = 
            "sitemap: " + urlSitemap;
                
        String contRobots2 = 
            "User-Agent: *\n" +
            "Disallow: /*size=*\n" +
            "Disallow: /*min=*\n" +
            "Disallow: /*max=*\n";

        switch (app) {
        case shop:
        	contRobots2+=
            "Disallow: /*?temporada=*\n" +
            "Disallow: /*=*,*\n" +
            "Disallow: /*sort=asc\n" +
            "Disallow: /*sort=desc\n" +
            "Disallow: /*/search*\n" +
            "Disallow: /*?descuento*\n" + 
            "Disallow: /shopurlhome*\n";
        	break;
        case outlet:
        default:
        	contRobots2+=
            "Disallow: /*temporada=*\n" +
            "Disallow: /*=*,*\n" +
            "Disallow: /*sort=asc\n" +
            "Disallow: /*sort=desc\n" +
            "Disallow: */search*\n";
        }

        contRobots2+=
            "Disallow: /*/helppopup/*\n" +
            "disallow: /es-es*\n" +
            "disallow: /gb-en*\n" +
            "disallow: /de-de*\n" +
            "disallow: /fr-fr*\n" +
            "disallow: /pl-pl*\n" +
            "disallow: /ru-ru*\n" +
            "disallow: /pt-pt*\n" +
            "disallow: /it-it*\n" +
            "disallow: /ir-ir*\n" +
            "disallow: /nl-nl*\n" +
            "disallow: /tr-tr*\n" +
            "disallow: /ro-ro*";
        
        String contRobots3 = 
            "User-Agent: expo9\n" +
            "Disallow: /\n" +
            " \n" +
            "User-agent: Orthogaffe\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: UbiCrawler\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: DOC\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: Zao\n" +
            "Disallow: /";
            
        String contRobots4 =            
            "User-agent: sitecheck.internetseer.com\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: Zealbot\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: MSIECrawler\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: SiteSnagger\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: WebStripper\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: WebCopier\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: Fetch\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: Offline Explorer\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: Teleport\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: TeleportPro\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: WebZIP\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: linko\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: HTTrack\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: Microsoft.URL.Control\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: Xenu\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: larbin\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: libwww\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: ZyBORG\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: Download Ninja\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: wget\n" +
            "Disallow: /\n" +
            "\n" +
            "User-agent: grub-client\n" +
            "Disallow: /";
        
        String descripValidac = 
            "1) Figura el siguiente contenido: <br>" + contRobots1.replace("\n", "<br>") + "<br>" +
            "2) Figura el siguiente contenido: <br>" + contRobots2.replace("\n", "<br>") + "<br>" +
            "3) Figura el siguiente contenido: <br>" + contRobots3.replace("\n", "<br>") + "<br>" +
            "4) Figura el siguiente contenido: <br>" + contRobots4.replace("\n", "<br>");
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!dFTest.driver.getPageSource().toLowerCase().contains(contRobots1.toLowerCase())) {
                listVals.add(1, State.Defect);                
            }
            if (!dFTest.driver.getPageSource().contains(contRobots2)) {
                listVals.add(2, State.Defect);
            }
            if (!dFTest.driver.getPageSource().contains(contRobots3)) {
                listVals.add(3, State.Defect);
            }
            if (!dFTest.driver.getPageSource().contains(contRobots4)) {
                listVals.add(4, State.Defect);
            }
    
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    @Step (
    	description="Ejecutamos la URL del sitemap: #{urlSitemap}", 
        expected="Se carga el contenido correcto")
    public static void inputSitemapURLandValidate(String urlSitemap, WebDriver driver) throws Exception {
        driver.get(urlSitemap);
    
        //Validaciones.
        checkResultUrlSitemal(driver);
    }
    
    private static void checkResultUrlSitemal(WebDriver driver) throws Exception {
    	DatosStep datosStep = TestCaseData.getDatosLastStep();
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        Date currDateDayPrecision = removeTime(new Date());
        String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(currDateDayPrecision);
    
        String descripValidac = 
            "1) Obtenemos un XML con formato de sitemap<br>" + 
            "2) Todos los tags <b>lastmod</b> contienen la fecha del día: " + currentDay;
        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Warn);
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            State resultado = State.Ok;
            //1)
            JAXBContext jaxbContext = JAXBContext.newInstance(Sitemapindex.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(UtilsMangoTest.getPageSource(dFTest.driver));
            Sitemapindex sitemapIndex = (Sitemapindex) jaxbUnmarshaller.unmarshal(reader);
            //2)
            Iterator<Sitemap> itSites = sitemapIndex.getSitemap().iterator();
            while (itSites.hasNext()) {
                Sitemap sitemap = itSites.next();
                Date lastmodDate = removeTime(sitemap.getLastmod().toGregorianCalendar().getTime());
                Assert.assertTrue(lastmodDate.equals(currDateDayPrecision));
            }
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(resultado);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static Date removeTime(Date date) {    
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        cal.set(Calendar.HOUR_OF_DAY, 0);  
        cal.set(Calendar.MINUTE, 0);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MILLISECOND, 0);  
        return cal.getTime(); 
    }
}
