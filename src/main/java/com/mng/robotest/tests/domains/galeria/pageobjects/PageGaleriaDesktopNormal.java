package com.mng.robotest.tests.domains.galeria.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.testslegacy.utils.UtilsTest;

public class PageGaleriaDesktopNormal extends PageGaleriaDesktop {

	private static final String XPATH_ARTICULO = "//li[@id[contains(.,'product-key-id')]]";
	private static final String XPATH_NOMBRE_RELATIVE_TO_ARTICLE = "//*[@class[contains(.,'product-name')]]";
	
	public PageGaleriaDesktopNormal() {
		super();
	}
	
	public PageGaleriaDesktopNormal(From from) {
		super(from);
	}
	
	@Override
	protected String getXPathArticulo() {
		return XPATH_ARTICULO;
	}
	
	@Override
	protected String getXPathNombreRelativeToArticle() {
		return XPATH_NOMBRE_RELATIVE_TO_ARTICLE;
	}
	
	private String getXPathImgArticulo(WebElement article) {
		String id = article.getAttribute("id");
		Pattern pattern = Pattern.compile("product-key-id-(.*)");
		Matcher matcher = pattern.matcher(id);
		if (matcher.find()) {
			return ".//img[@id='product-" + matcher.group(1) + "' or @src[contains(.,'/" + matcher.group(1) + "')]]" ;
		}
		return ".//img[contains(.,'product-')]";
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
	
	@Override
	public String getNombreArticulo(WebElement articulo) {
		String xpathNameArticle = "." + getXPathNombreRelativeToArticle();
		if (state(Visible, articulo).by(By.xpath(xpathNameArticle)).check()) {
			return articulo.findElement(By.xpath(xpathNameArticle)).getText();
		} else {
			return "Not Found"; 
		}
	}
	
	@Override
	public String getRefColorArticulo(WebElement articulo) {
		int lengthReferencia = 10;
		String id = getRefFromId(articulo);
		if (id.length()>lengthReferencia) {
			return (id.substring(0, lengthReferencia));
		}
		return id;
	}
	
	@Override
	public WebElement getImagenElementArticulo(WebElement articulo) {
		moveToElement(articulo);
		By byImg = By.xpath(getXPathImgArticulo(articulo));
		if (state(Visible, articulo).by(byImg).wait(1).check()) {
			return getElement(byImg);
		}
		return null;
	}
	
	private String getRefFromId(WebElement articulo) {
		String id = articulo.getAttribute("id");
		return (id.replace("product-key-id-", ""));
	}

}
