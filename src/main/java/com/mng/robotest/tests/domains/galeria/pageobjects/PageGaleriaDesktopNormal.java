package com.mng.robotest.tests.domains.galeria.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;
import com.mng.robotest.testslegacy.utils.UtilsTest;

public class PageGaleriaDesktopNormal extends PageGaleriaDesktop {

	public static final String XP_ARTICULO = "//li[@id[contains(.,'product-key-id')]]";
	private static final String XP_NOMBRE_RELATIVE_TO_ARTICLE = "//*[@class[contains(.,'product-name')]]";
	private static final String XP_LIST_ARTICLES = "//div[@class[contains(.,'columns')] and @id='list']";
	private static final String XP_ICONO_UP_GALERY = "//div[@id='scroll-top-step' or @id='iconFillUp']";
	
	public PageGaleriaDesktopNormal() {
		super();
	}
	
	public PageGaleriaDesktopNormal(From from) {
		super(from);
	}
	
	private String getXPathSliderRelativeToArticle(TypeSlider typeSlider) {
		return "//*[@data-testid='" + typeSlider.getNormal() + "']";
	}
	
	@Override
	protected String getXPathArticulo() {
		return XP_ARTICULO;
	}
	
	@Override
	protected String getXPathNombreRelativeToArticle() {
		return XP_NOMBRE_RELATIVE_TO_ARTICLE;
	}
	
	private String getXPathLinkNumColumnas(NumColumnas numColumnas) {
		return ("//button[@id='navColumns" + getNumColumnas(numColumnas) + "']");
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
		String href = articulo.findElement(By.xpath(XP_LINK_RELATIVE_TO_ARTICLE)).getAttribute("href");
		return UtilsTest.getReferenciaFromHref(href);
	}	
	
	@Override
	public String getNombreArticulo(WebElement articulo) {
		String xpathNameArticle = "." + getXPathNombreRelativeToArticle();
		if (state(VISIBLE, articulo).by(By.xpath(xpathNameArticle)).check()) {
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
		if (state(VISIBLE, articulo).by(byImg).wait(1).check()) {
			return getElement(byImg);
		}
		return null;
	}
	
	@Override
	public void clickSlider(WebElement articulo, TypeSlider typeSlider) {
		String xpathSlider = getXPathSliderRelativeToArticle(typeSlider);
		waitMillis(500);
		click(articulo).by(By.xpath(xpathSlider)).exec();		
	}
	
	@Override
	public void clickLinkColumnas(NumColumnas numColumnas) {
		String xpathLink = getXPathLinkNumColumnas(numColumnas);
		click(xpathLink).exec();
	}
	
	@Override
	public int getLayoutNumColumnas() {
		var listArt = getElement(XP_LIST_ARTICLES);
		if (listArt.getAttribute("class").contains("columns3")) {
			return 3;
		}
		if (listArt.getAttribute("class").contains("columns4")) {
			return 4;
		}
		return 2; 
	}	
	
	@Override
	public List<String> searchForArticlesNoValid(List<String> articleNames) {
		return getArticlesNoValid(articleNames);
	}
	
	@Override
	public String getXPathIconUpGalery() {
		return XP_ICONO_UP_GALERY;
	}
	
	private String getRefFromId(WebElement articulo) {
		String id = articulo.getAttribute("id");
		return (id.replace("product-key-id-", ""));
	}

}
