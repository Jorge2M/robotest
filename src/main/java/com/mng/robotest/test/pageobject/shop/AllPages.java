package com.mng.robotest.test.pageobject.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.ITestContext;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Pais;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class AllPages extends PageBase {
	
	public static final String XPATH_TAG_CANONICAL = "//link[@rel='canonical']";
	public static final String XPATH_TAG_ROBOTS = "//meta[@name='robots' and @content[contains(.,'noindex')]]";

	public String getXPathMainContent() {
		return ("//div[@class[contains(.,'main-content')] and @data-pais='" + dataTest.getCodigoPais() + "']");
	}

	public boolean isPresentTagCanonical() {
		return state(Present, XPATH_TAG_CANONICAL).check();
	}

	public boolean isPresentTagRobots() {
		return state(Present, XPATH_TAG_ROBOTS).check();
	}

	public WebElement getTagCanonincal() {
		return getElement(XPATH_TAG_CANONICAL);
	}
	
	public String getURLTagCanonical() {
		String urlTagCanonical = "";
		if (isPresentTagCanonical()) {
			urlTagCanonical = getTagCanonincal().getAttribute("href");
		}
		return urlTagCanonical;
	}

	public boolean isPresentElementWithTextUntil(String text, int seconds) {
		String xpath = "//*[text()[contains(.,'" + text + "')]]";
		return state(Present, xpath).wait(seconds).check();
	}

	public boolean isCodLiteralSinTraducir() {
		String xpath = "//*[text()[contains(.,'???')]]";
		return state(Present, xpath).check();
	}

	public boolean validateUrlNotMatchUntil(String url, int maxSeconds) throws Exception {
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
	
	public boolean validateElementsNotEqualsUntil(int elementosPagina, int margin, int maxSeconds) 
			throws Exception {
		int seconds = 0;
		do {
			if (Math.abs(elementosPagina - getElements("//*").size()) > margin) {
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
	public List<String> httpMalicious(ITestContext context, Pais pais) {
		boolean malicious = true;
		if (pais != null) {
			List<String> paisesOK = new ArrayList<>();
			paisesOK.add("006"); // United Kingdom
			paisesOK.add("003"); // Netherlands

			for (int i = 0; i < paisesOK.size(); i++) {
				if (pais.getCodigo_pais().contains(paisesOK.get(i))) {
					malicious = false;
					break;
				}
			}
		}

		List<String> listaHttpMalicious = new ArrayList<>();
		if (malicious) {
			List<WebElement> allHttp = getElements("//*[contains(@src, \"http\")]");
			for (WebElement tagHttp : allHttp) {
				if (isMaliciousHttp(context, tagHttp)) {
					String src = tagHttp.getAttribute("src");
					listaHttpMalicious.add(driver.getCurrentUrl() + ". <br><b>Http malicious!</b> " + ".id:" + tagHttp.getAttribute("id") + ",src:" + src);
					Log4jTM.getLogger().warn(() ->
							driver.getCurrentUrl() + ". Http malicious! " + 
							", id:" + tagHttp.getAttribute("id") + 
							", src:" + src);
				}
			}
		}
		return listaHttpMalicious;
	}

	private boolean isMaliciousHttp(final ITestContext context, final WebElement tagHttp) {
		boolean malicious = true;
		String src = tagHttp.getAttribute("src");
		String id = tagHttp.getAttribute("id");

		// Si ya habíamos registrado el warning no lo vamos a mostrar de nuevo (1 warning de este tipo x test)
		if (context.getAttribute("httpMalicious." + src) != null) {
			malicious = false;
		} else {
			List<String> dominiosOK = new ArrayList<>();
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

	public boolean isPresentMainContent() {
		String xpathMainContent = getXPathMainContent();
		return state(Present, xpathMainContent).check();
	}

	public boolean isTitleAssociatedToMenu(String menuName) {
		String titlePage = driver.getTitle().toLowerCase();
		if (titlePage.contains(menuName.toLowerCase())) {
			return true;
		}
		if (menuName.contains(" ")) {
			return Arrays.stream(titlePage.split(" "))
				.filter(s -> !titlePage.contains(s))
				.findAny()
				.isEmpty();
		}
		return false;
	}
}
