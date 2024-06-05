package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaNoGenesis.StateFavorito.*;

import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.commons.entity.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaDesktopBaseNoGenesis.NumColumnas;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaNoGenesis.StateFavorito;

public class CommonGaleriaNoGenesis extends PageBase {

	private static final String XP_LISTA_ARTICULOS = "//ul[@data-testid[contains(.,'plp.grid.')]]";
	private static final String XP_CAPA_ARTICULO = "//div[@data-testid='plp.product.figure']";
	public static final String XP_ARTICULO = XP_CAPA_ARTICULO + "/..";
	private static final String XP_ANCESTOR_ARTICLE = "//ancestor::div[@data-testid='plp.product.figure']/..";
	
	//TODO Galería Kondo -> Solicitar React (11-10-2023)
	private static final String XP_NOMBRE_RELATIVE_TO_ARTICLE_DESKTOP = "//div[@class[contains(.,'md12')]]";
	private static final String XP_NOMBRE_RELATIVE_TO_ARTICLE_DEVICE = "//p[@class[contains(.,'hYlwk')]]/span[2]";
	
	protected static final String XP_HEARTH_ICON_RELATIVE_ARTICLE = "//*[@data-testid='button-icon']";	

	public String getXPathArticulo() {
		return XP_ARTICULO;
	}
	
	public String getXPathArticuloAncestor() {
		return XP_ANCESTOR_ARTICLE;
	}	
	
	public String getXPathNombreRelativeToArticle() {
		if (channel.isDevice()) {
			return XP_NOMBRE_RELATIVE_TO_ARTICLE_DEVICE;
		}
		return XP_NOMBRE_RELATIVE_TO_ARTICLE_DESKTOP;
	}
	
	public boolean isVisibleAnyArticle() {
		return state(VISIBLE, getXPathArticulo()).check();
	}
	
	private String getXPathSliderRelativeToArticle(TypeSlider typeSlider) {
		return "//button[@aria-label='" + typeSlider.getNormal() + "']";
	}	
	
	public String getRefArticulo(WebElement articulo) {
		return getReference(articulo).substring(0, 8); 
	}	
	
	public String getNombreArticulo(WebElement articulo) {
		String xpathNameArticle = "." + getXPathNombreRelativeToArticle();
		if (state(VISIBLE, articulo).by(By.xpath(xpathNameArticle)).check()) {
			return getElement(articulo, xpathNameArticle).getText();
		} else {
			return "Not Found"; 
		}
	}
	
	public String getRefColorArticulo(WebElement articulo) {
		return getReference(articulo).replace(":", "");
	}	
	
	public WebElement getImagenElementArticulo(WebElement articulo) {
		moveToElement(articulo);
		String xpathImg = getXPathImgArticulo(); 
		if (state(PRESENT, articulo).by(By.xpath("." + xpathImg)).wait(1).check()) {
			return getElement(articulo, "." + xpathImg);
		}
		return null;
	}
	
	public void clickSlider(WebElement articulo, TypeSlider typeSlider) {
		String xpathSlider = getXPathSliderRelativeToArticle(typeSlider);
		waitMillis(500);
		click(articulo).by(By.xpath("." + xpathSlider)).exec();		
	}	
	
	public NumColumnas getLayoutNumColumnas() {
		String dataTestId = getElement(XP_LISTA_ARTICULOS).getAttribute("data-testid");
		var pattern = Pattern.compile("\\d+$");
		var matcher = pattern.matcher(dataTestId);
        if (matcher.find()) {
            String match = matcher.group();
            return NumColumnas.getValue(Integer.parseInt(match));
        } else {
        	return null;
        }
	}
	
	//TODO Galería Kondo React (13-10)
	private String getXPathImgArticulo() {
		return "//img[@class[contains(.,'tWlwm')] or @class[contains(.,'Lcm5m')]]"; //Lcm5m artículos dobles
	}	
	
	private String getReference(WebElement articulo) {
		return getElement(articulo, "." + XP_CAPA_ARTICULO).getAttribute("reference");
	}
	
	String getXPathArticleHearthIcon(int posArticulo) {
		String xpathArticulo = "(" + getXPathArticulo() + ")[" + posArticulo + "]";
		return (xpathArticulo + XP_HEARTH_ICON_RELATIVE_ARTICLE);
	}

	public StateFavorito getStateHearthIcon(int iconNumber) {
		var icon = getElement(getXPathArticleHearthIcon(iconNumber));
		if (icon.getAttribute("class").contains("icon-fill")) {
			return MARCADO;
		}
		return DESMARCADO;
	}	
	
	public int getNumFavoritoIcons() {
		return getElements(XP_HEARTH_ICON_RELATIVE_ARTICLE).size();
	}
	
}
