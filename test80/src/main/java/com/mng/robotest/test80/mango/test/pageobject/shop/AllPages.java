package com.mng.robotest.test80.mango.test.pageobject.shop;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.test80.mango.test.beans.Pais;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class AllPages {
	
	public static final String XPath_tagCanonical = "//link[@rel='canonical']";
	public static final String XPath_tagRobots = "//meta[@name='robots' and @content[contains(.,'noindex')]]";

	public static String getXPathMainContent(Pais pais) {
		return ("//div[@class[contains(.,'main-content')] and @data-pais='" + pais.getCodigo_pais() + "']");
	}

	public static boolean isPresentTagCanonical(WebDriver driver) {
		return (state(Present, By.xpath(XPath_tagCanonical), driver).check());
	}

	public static boolean isPresentTagRobots(WebDriver driver) {
		return (state(Present, By.xpath(XPath_tagRobots), driver).check());
	}

	public static WebElement getTagCanonincal(WebDriver driver) {
		return (driver.findElement(By.xpath(XPath_tagCanonical)));
	}
	
	public static String getURLTagCanonical(WebDriver driver) {
		String urlTagCanonical = "";
		if (isPresentTagCanonical(driver)) {
			urlTagCanonical = getTagCanonincal(driver).getAttribute("href");
		}
		
		return urlTagCanonical;
	}

	public static boolean isPresentElementWithTextUntil(String text, int maxSeconds, WebDriver driver) {
		String xpath = "//*[text()[contains(.,'" + text + "')]]";
		return (state(Present, By.xpath(xpath), driver).wait(maxSeconds).check());
	}

	public static boolean isCodLiteralSinTraducir(WebDriver driver) {
		String xpath = "//*[text()[contains(.,'???')]]";
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean validateUrlNotMatchUntil(String url, int maxSeconds, WebDriver driver) throws Exception {
		int seconds = 0;
		do {
			if (url.compareTo(driver.getCurrentUrl())!=0) {
				return true;
			}
			Thread.sleep(1000);
			seconds+=1;
		}
		while (seconds<maxSeconds);
		return false;
	}
	
	public static boolean validateElementsNotEqualsUntil(int elementosPagina, int margin, int maxSeconds, WebDriver driver) 
	throws Exception {
		int seconds = 0;
		do {
			if (Math.abs(elementosPagina - driver.findElements(By.xpath("//*")).size()) > margin) {
				return true;
			}
			Thread.sleep(1000);
			seconds+=1;
		}
		while (seconds<maxSeconds);
		return false;
	}

	/**
	 * Función para detectar elementos http maliciosos incrustados en el atributo 'src'
	 */
	public static ArrayList<String> httpMalicious(final WebDriver webdriver, final ITestContext context, final Pais pais) throws Exception {
		boolean malicious = true;
		if (pais != null) {
			ArrayList<String> paisesOK = new ArrayList<>();
			paisesOK.add("006"); // United Kingdom
			paisesOK.add("003"); // Netherlands

			for (int i = 0; i < paisesOK.size(); i++) {
				if (pais.getCodigo_pais().contains(paisesOK.get(i))) {
					malicious = false;
					break;
				}
			}
		}

		ArrayList<String> listaHttpMalicious = new ArrayList<>();
		if (malicious) {
			List<WebElement> allHttp = webdriver.findElements(By.xpath("//*[contains(@src, \"http\")]"));
			for (WebElement tagHttp : allHttp) {
				if (isMaliciousHttp(context, tagHttp)) {
					String src = tagHttp.getAttribute("src");
					listaHttpMalicious.add(webdriver.getCurrentUrl() + ". <br><b>Http malicious!</b> " + ".id:" + tagHttp.getAttribute("id") + ",src:" + src);
					Log4jTM.getLogger().warn(
							webdriver.getCurrentUrl() + ". Http malicious! " + 
							", id:" + tagHttp.getAttribute("id") + 
							", src:" + src);
				}
			}
		}

		return listaHttpMalicious;
	}

	/**
	 * Decide si un elemento HTML es o no malicioso (revisa el src y el id)
	 */
	private static boolean isMaliciousHttp(final ITestContext context, final WebElement tagHttp) {
		boolean malicious = true;
		String src = tagHttp.getAttribute("src");
		String id = tagHttp.getAttribute("id");

		// Si ya habíamos registrado el warning no lo vamos a mostrar de nuevo (1 warning de este tipo x test)
		if (context.getAttribute("httpMalicious." + src) != null) {
			malicious = false;
		} else {
			ArrayList<String> dominiosOK = new ArrayList<>();
			dominiosOK.add("mngbcn.com");
			dominiosOK.add("mango.com");
			dominiosOK.add("addthis.com");
			dominiosOK.add("google-analytics.com");
			dominiosOK.add("google-analytics.com");
			dominiosOK.add("baidu.com");
			dominiosOK.add("googleads.g.doubleclick.net");
			dominiosOK.add("facebook.net");
			dominiosOK.add("googleadservices.com");
			dominiosOK.add("wcs.naver.net");
			dominiosOK.add("nxtck.com");
			dominiosOK.add("static.zanox.com");
			dominiosOK.add("eu-sonar.sociomantic.com");
			dominiosOK.add("avazudsp.net");
			dominiosOK.add("adserving.avazudsp.net");
			dominiosOK.add("criteo.net");
			dominiosOK.add("criteo.com");
			dominiosOK.add("app.salecycle.com");
			dominiosOK.add("cloudfront.net");
			dominiosOK.add("zanox.ws");
			dominiosOK.add("yandex.ru");
			dominiosOK.add("colbenson.es");
			dominiosOK.add("lengow.com");
			dominiosOK.add("doubleclick.net");
			dominiosOK.add("optimizely.com");
			dominiosOK.add("optimizely.com");
			dominiosOK.add("adnxs.com");
			dominiosOK.add("prfct.co");
			dominiosOK.add("marinsm.com");
			dominiosOK.add("perfectaudience.com");
			dominiosOK.add("cannedbanners.com");
			dominiosOK.add("mng-images.s3.amazonaws.com");
			dominiosOK.add("bat.r.msn.com");
			dominiosOK.add("bat.bing.com");
			dominiosOK.add("ds-aksb-a.akamaihd.net");

			for (int i = 0; i < dominiosOK.size(); i++) {
				if (src.contains(dominiosOK.get(i))) {
					malicious = false;
					break;
				}
			}

			if (id.contains("sonar-tracking")) {
				malicious = false;
			}
		}

		if (malicious) {
			context.setAttribute("httpMalicious." + src, "");
		}
		return malicious;
	}

	public static boolean isPresentMainContent(Pais pais, WebDriver driver) {
		String xpathMainContent = getXPathMainContent(pais);
		return (state(Present, By.xpath(xpathMainContent), driver).check());
	}

	public static boolean isTitleAssociatedToMenu(String menuName, WebDriver driver) {
		String titlePage = driver.getTitle();
		if (titlePage.toLowerCase().contains(menuName.toLowerCase())) {
			return true;
		}
		return false;
	}
}
