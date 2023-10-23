package com.mng.robotest.tests.domains.galeria.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;
import com.mng.robotest.testslegacy.utils.UtilsTest;

public class PageGaleriaDeviceNormal extends PageGaleriaDevice {

	//TODO adaptar React (pendiente petición a Jesús Bermúdez 3-Marzo-2021)
	private static final String ARTICULO_ELEMENT = "li[@data-testid[contains(.,'plp.product')]]";
	private static final String XPATH_ARTICULO = "//" + ARTICULO_ELEMENT;
	private static final String XPATH_ANCESTOR_ARTICLE = "//ancestor::" + ARTICULO_ELEMENT;
	private static final String XPATH_NOMBRE_RELATIVE_TO_ARTICLE = "//*[@class[contains(.,'product-name')]]";
	private static final String XPATH_COLOR_ARTICLE_BUTTON = "//div[@class[contains(.,'product-colors')]]/button";
	
	private static final String XPATH_IMG_RELATIVE_ARTICLE = 
		"//img[@src and " + 
			 "(@class[contains(.,'productListImg')] or " + 
			  "@class[contains(.,'product-list-image')] or " + 
			  "@class[contains(.,'product-list-img')] or " +
			  "@class[contains(.,'product-image')] or " +
			  "@id[contains(.,'product-image')])]";	
	
	public PageGaleriaDeviceNormal() {
		super();
	}
	
	public PageGaleriaDeviceNormal(From from) {
		super(from);
	}	
	
	@Override
	protected String getXPathArticulo() {
		return XPATH_ARTICULO;
	}
	
	@Override
	protected String getXPathArticuloAncestor() {
		return XPATH_ANCESTOR_ARTICLE;
	}
	
	@Override
	protected String getXPathNombreRelativeToArticle() {
		return XPATH_NOMBRE_RELATIVE_TO_ARTICLE;
	}
	
	private String getXPathSliderRelativeToArticle(TypeSlider typeSlider) {
		return "//button[@aria-label='" + typeSlider.getKondo() + "']";
	}	
	
	@Override
	public String getRefArticulo(WebElement articulo) {
		int lengthReferencia = 8;
		String id = getRefFromId(articulo);
		if ("".compareTo(id)!=0) {
			if (id.length()>lengthReferencia) {
				return (id.substring(0, lengthReferencia));
			}
			return id;
		}

		//Para el caso TestAB-1 se ejecutará este caso para conseguir los atributos del artículo
		String href = articulo.findElement(By.xpath(XPATH_LINK_RELATIVE_TO_ARTICLE)).getAttribute("href");
		return UtilsTest.getReferenciaFromHref(href);
	}
	
	private String getRefFromId(WebElement articulo) {
		String id = articulo.getAttribute("id");
		return (id.replace("product-key-id-", ""));
	}	

	@Override
	public String getNombreArticulo(WebElement articulo) {
		String xpath = getXPathNombreRelativeToArticle();
		return articulo.findElement(By.xpath("." + xpath)).getText();
	}
	
	@Override
	public void showColors(WebElement articulo) {
		By byColorButton = By.xpath("." + XPATH_COLOR_ARTICLE_BUTTON);
		click(articulo).by(byColorButton).exec();
	}
	
	/**
	 * @param numArticulo: posición en la galería del artículo
	 * @return la referencia de un artículo
	 */
	@Override
	public String getRefColorArticulo(WebElement articulo) {
		int lengthReferencia = 11;
		String refWithColor = getRefColorArticuloMethod1(articulo);
		if ("".compareTo(refWithColor)==0) {
			refWithColor = getRefColorArticuloMethod2(articulo);
		}
			
		if (refWithColor.length()>lengthReferencia) {
			return (refWithColor.substring(0, lengthReferencia));
		}
		return refWithColor;
	}
	
	private String getRefColorArticuloMethod1(WebElement articulo) {
		String xpathDivRelativeArticle = "//div[@id and @class='product-container-image']";
		if (state(Present, articulo).by(By.xpath("." + xpathDivRelativeArticle)).check()) {
			return (articulo.findElement(By.xpath("." + xpathDivRelativeArticle)).getAttribute("id"));
		}
		return "";
	}

	private String getRefColorArticuloMethod2(WebElement articulo) {
		WebElement ancorArticle = getElementVisible(articulo, By.xpath(".//a"));
		if (ancorArticle!=null) {
			String hrefArticle = ancorArticle.getAttribute("href");
			return (UtilsPageGaleria.getReferenciaAndCodColorFromURLficha(hrefArticle));
		}
		return "";
	}
	
	@Override
	public WebElement getImagenElementArticulo(WebElement articulo) {
		moveToElement(articulo);
		By byImg = By.xpath("." + XPATH_IMG_RELATIVE_ARTICLE);
		if (state(Present, articulo).by(byImg).wait(3).check()) {
			return getElement(articulo, "." + XPATH_IMG_RELATIVE_ARTICLE);
		}
		return null;
	}
	
	@Override
	public void clickSlider(WebElement articulo, TypeSlider typeSlider) {
		String xpathSlider = getXPathSliderRelativeToArticle(typeSlider);
		waitMillis(500);
		click(articulo).by(By.xpath("." + xpathSlider)).exec();		
	}	
	
}
