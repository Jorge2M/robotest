package com.mng.robotest.domains.galeria.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;


public class SecTallasArticuloDesktop extends PageBase {
	
	private final String XPATH_ARTICULO;
	private final String XPATH_CAPA_TALLAS_ARTICULO_SHOP = "//div[@class[contains(.,'sizes-container')]]";
	private static final String XPATH_TALLA_AVAILABLE = "//button[@data-testid='si-stock']";
	private static final String XPATH_TALLA_UNAVAILABLE =	"//button[@data-testid='no-stock']";
	private final static String CLASS_CAPA_ACTIVE_SHOP = "@class[contains(.,'active')]";
	
	public SecTallasArticuloDesktop(String xpathArticulo) {
		this.XPATH_ARTICULO = xpathArticulo;
	}
	
	private String getXPathArticleCapaInferiorDesktop(int posArticulo) {
		String xpathArticuloX = "(" + XPATH_ARTICULO + ")[" + posArticulo + "]";
		return xpathArticuloX + XPATH_CAPA_TALLAS_ARTICULO_SHOP;
	}

	public String getXPathFirstCapaAnadirOutlet(int posArticulo, boolean capaVisible) {
		String xpathCapaAdd = getXPathArticleCapaInferiorDesktop(posArticulo);
		String classSegunVisible = "not(@class[contains(.,'active')])";
		if (capaVisible) {
			classSegunVisible = "@class[contains(.,'active')]";
		}
		return (xpathCapaAdd + "//p[@class[contains(.,'first-step')] and " + classSegunVisible + "]");
	}
	
	public String getXPathCapaTallas(int posArticulo, boolean capaVisible) {
		String xpathCapaAdd = getXPathArticleCapaInferiorDesktop(posArticulo);
		String classCapaActive = CLASS_CAPA_ACTIVE_SHOP;
		if (capaVisible) {
			return xpathCapaAdd + "//self::div[" + classCapaActive + "]";
		}
		return xpathCapaAdd + "//self::div[not(" + classCapaActive + ")]"; 
	}

	public String getXPathArticleTallaAvailable(int posArticulo, int posTalla) {
		String xpathCapaTallas = getXPathCapaTallas(posArticulo, true);
		return "(" + xpathCapaTallas + XPATH_TALLA_AVAILABLE + ")[" + posTalla + "]";
	}

	public String getXPathArticleTallaNotAvailable() {
		return XPATH_TALLA_UNAVAILABLE;
	}
	
	public String getXPathArticleTallaNotAvailable(int posArticulo, int posTalla) {
		String xpathCapaTallas = getXPathCapaTallas(posArticulo, true);
		return "(" + xpathCapaTallas + XPATH_TALLA_UNAVAILABLE + ")[" + posTalla + "]";
	}	

	public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int seconds) {
		String xpathCapa = getXPathCapaTallas(posArticulo, true);
		return (state(Visible, xpathCapa).wait(seconds).check());
	}
	
	public void bringSizesBack(WebElement articulo) {
		WebElement sizes = articulo.findElement(By.xpath("." + XPATH_CAPA_TALLAS_ARTICULO_SHOP));
		bringElement(sizes, BringTo.BACKGROUND);
	}
}
