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

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.sitemap.Sitemapindex;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.sitemap.Sitemapindex.Sitemap;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;

/**
 * Implementa steps/validaciones más propias del browser que de una página concreta (como p.e. la introducción de una URL concreta)
 * @author jorge.munoz
 *
 */
public class BrowserStpV {

	final static String tagUrlRobots = "@TagUrlRobots";
	@Step (
		description="Ejecutamos la URL del robots: " + tagUrlRobots, 
        expected="Se carga el contenido correcto")
    public static void inputRobotsURLandValidate(String urlBaseTest, AppEcom app, WebDriver driver) throws Exception {
        URI uriBase = new URI(urlBaseTest);
        String urlRobots = urlBaseTest.replace(uriBase.getPath(), "") + "/" + "robots.txt";
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagUrlRobots, urlRobots);
        String urlSitemap = urlBaseTest.replace(uriBase.getPath(), "") + "/" + "sitemap.xml";
        
        driver.get(urlRobots);
        checkPageRobotsTxt(urlSitemap, app, driver);
	}
    
	@Validation
	private static ChecksResult checkPageRobotsTxt(String urlSitemap, AppEcom app, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
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
    		
    	validations.add(
            "Figura el siguiente contenido: <br>" + contRobots1.replace("\n", "<br>"),
            driver.getPageSource().toLowerCase().contains(contRobots1.toLowerCase()), State.Defect);
    	validations.add(
            "Figura el siguiente contenido: <br>" + contRobots2.replace("\n", "<br>"),
            driver.getPageSource().contains(contRobots2), State.Defect);
    	validations.add(
    		"Figura el siguiente contenido: <br>" + contRobots3.replace("\n", "<br>"),
    		driver.getPageSource().contains(contRobots3), State.Defect);
    	validations.add(
    		"Figura el siguiente contenido: <br>" + contRobots4.replace("\n", "<br>"),
    		driver.getPageSource().contains(contRobots4), State.Defect);
    	return validations;
    }
    
    @Step (
    	description="Ejecutamos la URL del sitemap: #{urlSitemap}", 
        expected="Se carga el contenido correcto")
    public static void inputSitemapURLandValidate(String urlSitemap, WebDriver driver) throws Exception {
        driver.get(urlSitemap);
        checkResultUrlSitemal(driver);
    }
    
    @Validation
    private static ChecksResult checkResultUrlSitemal(WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	
    	Sitemapindex sitemapIndex = null;
    	try {
	        JAXBContext jaxbContext = JAXBContext.newInstance(Sitemapindex.class);
	        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	        StringReader reader = new StringReader(UtilsMangoTest.getPageSource(driver));
	        sitemapIndex = (Sitemapindex) jaxbUnmarshaller.unmarshal(reader);
    	}
    	catch (Exception e) {}
    	validations.add(
            "Obtenemos un XML con formato de sitemap",
            sitemapIndex!=null, State.Defect);
    	
    	if (sitemapIndex!=null) {
            Date currDateDayPrecision = removeTime(new Date());
            String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(currDateDayPrecision);
            Iterator<Sitemap> itSites = sitemapIndex.getSitemap().iterator();
            boolean lastModsContainsCurrentDay = true;
            while (itSites.hasNext()) {
                Sitemap sitemap = itSites.next();
                Date lastmodDate = removeTime(sitemap.getLastmod().toGregorianCalendar().getTime());
                if (!lastmodDate.equals(currDateDayPrecision)) {
                	lastModsContainsCurrentDay = false;
                	break;
                }
            }
        	validations.add(
                "Todos los tags <b>lastmod</b> contienen la fecha del día: " + currentDay,
                lastModsContainsCurrentDay, State.Defect);
    	}
    	
    	return validations;
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
