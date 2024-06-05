package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.commons.entity.TypeSlider;
import com.mng.robotest.testslegacy.utils.UtilsTest;

public class PageGaleriaDesktopOld extends PageGaleriaDesktopBaseNoGenesis {

	public static final String XP_ARTICULO = "//li[@id[contains(.,'product-key-id')]]";
	private static final String XP_NOMBRE_RELATIVE_TO_ARTICLE = "//*[@class[contains(.,'product-name')]]";
	private static final String XP_ICONO_UP_GALERY = "//div[@id='scroll-top-step' or @id='iconFillUp']";
	
	public PageGaleriaDesktopOld() {
		super();
	}
	
	private String getXPathSliderRelativeToArticle(TypeSlider typeSlider) {
		return "//*[@data-testid='" + typeSlider.getOld() + "']";
	}
	
	@Override
	public String getXPathArticulo() {
		return XP_ARTICULO;
	}
	
	@Override
	public String getXPathNombreRelativeToArticle() {
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
	public List<String> searchForArticlesNoValid(List<String> articleNames) {
		return getArticlesNoValid(articleNames);
	}
	
	@Override
	public String getXPathIconUpGalery() {
		return XP_ICONO_UP_GALERY;
	}
	
	@Override
	public StateFavorito getStateHearthIcon(int iconNumber) {
		return new CommonGaleriaNoGenesis().getStateHearthIcon(iconNumber);
	}
	
	@Override
	public String getXPathArticleHearthIcon(int posArticulo) {
		return new CommonGaleriaNoGenesis().getXPathArticleHearthIcon(posArticulo);
	}

	@Override
	public int getNumFavoritoIcons() {
		return new CommonGaleriaNoGenesis().getNumFavoritoIcons();
	}	
	
	private String getRefFromId(WebElement articulo) {
		String id = articulo.getAttribute("id");
		return (id.replace("product-key-id-", ""));
	}

}
