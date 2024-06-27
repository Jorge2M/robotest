package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.commons.entity.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaDesktopBaseNoGenesis.NumColumnas;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.menus.SecBannerHeadGallery.TypeLinkInfo;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.utils.UtilsTest;

public class PageGaleriaDeviceOld extends PageGaleriaDevice {

	//TODO adaptar React (pendiente petición a Jesús Bermúdez 3-Marzo-2021)
	private static final String ARTICULO_ELEMENT = "li[@data-testid[contains(.,'plp.product')]]";
	private static final String XP_ARTICULO = "//" + ARTICULO_ELEMENT;
	private static final String XP_ANCESTOR_ARTICLE = "//ancestor::" + ARTICULO_ELEMENT;
	private static final String XP_NOMBRE_RELATIVE_TO_ARTICLE = "//*[@class[contains(.,'product-name')]]";
	private static final String XP_COLOR_ARTICLE_BUTTON = "//div[@class[contains(.,'product-colors')]]/button";
	private static final String XP_COLOR_ARTICLE_OPTION = "//button[@class='product-color']";	
	private static final String XP_ICONO_GALERY_MOBILE = "//div[@class[contains(.,'scroll-container--visible')]]";
	private static final String XP_ICONO_UP_GALERY_TABLET = "//div[@class='scroll-top-step']";
	
	private static final String XP_IMG_RELATIVE_ARTICLE = 
		"//img[@src and " + 
			 "(@class[contains(.,'productListImg')] or " + 
			  "@class[contains(.,'product-list-image')] or " + 
			  "@class[contains(.,'product-list-img')] or " +
			  "@class[contains(.,'product-image')] or " +
			  "@id[contains(.,'product-image')])]";	
	
	public PageGaleriaDeviceOld() {
		super();
	}
	
	@Override
	public String getXPathArticulo() {
		return XP_ARTICULO;
	}
	
	@Override
	protected String getXPathArticuloAncestor() {
		return XP_ANCESTOR_ARTICLE;
	}
	
	@Override
	protected String getXPathArticuloConColores() {
		return XP_COLOR_ARTICLE_BUTTON + getXPathArticuloAncestor();
	}
	
	@Override
	protected String getXPathColorArticleOption() {
		return XP_COLOR_ARTICLE_OPTION;
	}	
	
	@Override
	public String getXPathNombreRelativeToArticle() {
		return XP_NOMBRE_RELATIVE_TO_ARTICLE;
	}
	
	private String getXPathSliderRelativeToArticle(TypeSlider typeSlider) {
		return "//button[@aria-label='" + typeSlider.getNormal() + "']";
	}	

	private String getXPpathIconoUpGalery() {
		switch (channel) {
		case mobile:
			return XP_ICONO_GALERY_MOBILE;
		case tablet:
		default:
			return XP_ICONO_UP_GALERY_TABLET;
		}
	}
	
	@Override
	public boolean isVisibleAnyArticle() {
		return state(VISIBLE, XP_ARTICULO).check();
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
		By byColorButton = By.xpath("." + XP_COLOR_ARTICLE_BUTTON);
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
		if (state(PRESENT, articulo).by(By.xpath("." + xpathDivRelativeArticle)).check()) {
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
		By byImg = By.xpath("." + XP_IMG_RELATIVE_ARTICLE);
		if (state(PRESENT, articulo).by(byImg).wait(3).check()) {
			return getElement(articulo, "." + XP_IMG_RELATIVE_ARTICLE);
		}
		return null;
	}
	
	@Override
	public void clickSlider(WebElement articulo, TypeSlider typeSlider) {
		String xpathSlider = getXPathSliderRelativeToArticle(typeSlider);
		waitMillis(500);
		click(articulo).by(By.xpath("." + xpathSlider)).exec();		
	}
	
	@Override
	public void clickSliders(WebElement articulo, TypeSlider... typeSliderList) {
		throw new UnsupportedOperationException();
	}	

	@Override
	public boolean backTo1erArticulo() throws InterruptedException {
		return backTo1erArticulo(getXPpathIconoUpGalery());
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
	
	@Override
	public void clickSubMenuDesktop(String submenu) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean isVisibleSubMenuDesktop(String submenu) {
		throw new UnsupportedOperationException();
	}	
	
	@Override
    public boolean isVisibleBannerHead() {
    	throw new UnsupportedOperationException();
    }
	@Override
    public boolean isBannerHeadLinkable() {
    	throw new UnsupportedOperationException();
    }
	@Override
    public void clickBannerHeadIfClickable() {
    	throw new UnsupportedOperationException();
    }
	@Override
    public boolean isBannerHeadWithoutTextAccesible() {
    	throw new UnsupportedOperationException();
    }
	@Override
    public String getTextBannerHead() {
    	throw new UnsupportedOperationException();
    }
	@Override
    public boolean isBannerHeadSalesBanner(IdiomaPais idioma) {
    	throw new UnsupportedOperationException();
    }	

	@Override
    public boolean isVisibleLinkInfoRebajasBannerHead() {
		throw new UnsupportedOperationException();
    }
	@Override
    public boolean isVisibleLinkInfoRebajasBannerHead(TypeLinkInfo typeLinkInfo) {
		throw new UnsupportedOperationException();
    }

	@Override
	public boolean isVisibleSelectorPrecios() {
		throw new UnsupportedOperationException();
	}
	@Override
	public int getMinImportFilter() {
		throw new UnsupportedOperationException();
	}
	@Override
	public int getMaxImportFilter() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void clickIntervalImportFilter(int margenPixelsLeft, int margenPixelsRight) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void showFilters() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void acceptFilters() {
		throw new UnsupportedOperationException();
	}
	@Override
	public List<String> searchForArticlesNoValid(List<String> articleNames) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void clickLinkColumnas(NumColumnas numColumnas) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean isVisibleLabelFiltroPrecioApplied(int minim, int maxim) {
		throw new UnsupportedOperationException();
	}
	
}
