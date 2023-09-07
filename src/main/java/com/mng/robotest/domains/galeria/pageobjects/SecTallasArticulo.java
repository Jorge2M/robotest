package com.mng.robotest.domains.galeria.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.base.PageBase;


public class SecTallasArticulo extends PageBase {
	
	private final String xpathArticulo;
	private final String xpathCapaTallasArticuloShop = "//div[@class[contains(.,'sizes-container')]]";
	private static final String XPATH_TALLA_AVAILABLE = "//button[@data-testid[contains(.,'size.available')]]";
	private static final String XPATH_TALLA_UNAVAILABLE =	"//button[@data-testid[contains(.,'size.unavailable')]]";
	private static final String CLASS_CAPA_ACTIVE_SHOP = "@class[contains(.,'active')]";
	
	public SecTallasArticulo(String xpathArticulo) {
		this.xpathArticulo = xpathArticulo;
	}
	
	private String getXPathArticleCapaInferiorDesktop(int posArticulo) {
		String xpathArticuloX = "(" + xpathArticulo + ")[" + posArticulo + "]";
		return xpathArticuloX + xpathCapaTallasArticuloShop;
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

	public String getXPathArticleTallaAvailable(int posArticulo) {
		String xpathCapaTallas = getXPathCapaTallas(posArticulo, true);
		return xpathCapaTallas + XPATH_TALLA_AVAILABLE;
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
		WebElement sizes = articulo.findElement(By.xpath("." + xpathCapaTallasArticuloShop));
		bringElement(sizes, BringTo.BACKGROUND);
	}
}
