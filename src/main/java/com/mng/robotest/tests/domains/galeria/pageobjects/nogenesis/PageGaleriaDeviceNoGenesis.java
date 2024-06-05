package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.commons.entity.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaDesktopBaseNoGenesis.NumColumnas;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.mobil.SecFiltrosMobilNoGenesis;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.menus.SecBannerHeadGallery.TypeLinkInfo;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.data.Color;

public class PageGaleriaDeviceNoGenesis extends PageGaleriaDevice {

	private final CommonGaleriaNoGenesis commonNormal = new CommonGaleriaNoGenesis();
	
	private static final String XP_COLOR_ARTICLE_BUTTON = "//*[@data-testid='productCard.showColors.button']";
	private static final String XP_COLOR_ARTICLE_OPTION = "//button[@data-testid='plp.color.selector']";
	private static final String XP_UP_BUTTON = "//button[@aria-label='Scroll up']/*[@data-testid='button-icon']";
	
	public PageGaleriaDeviceNoGenesis() {
		super();
	}
	
	@Override
	public String getXPathArticulo() {
		return commonNormal.getXPathArticulo();
	}

	@Override
	protected String getXPathArticuloAncestor() {
		return commonNormal.getXPathArticuloAncestor();
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
		return commonNormal.getXPathNombreRelativeToArticle();
	}
	
	@Override
	public boolean isVisibleAnyArticle() {
		return commonNormal.isVisibleAnyArticle();
	}
	
	@Override
	public String getRefArticulo(WebElement articulo) {
		return commonNormal.getRefArticulo(articulo);
	}	
	
	@Override
	public String getNombreArticulo(WebElement articulo) {
		return commonNormal.getNombreArticulo(articulo);
	}
	
	@Override
	public String getRefColorArticulo(WebElement articulo) {
		return commonNormal.getRefColorArticulo(articulo);
	}	
	
	@Override
	public WebElement getImagenElementArticulo(WebElement articulo) {
		return commonNormal.getImagenElementArticulo(articulo);
	}
	
	@Override
	public void clickSlider(WebElement articulo, TypeSlider typeSlider) {
		commonNormal.clickSlider(articulo, typeSlider);
	}
	
	@Override
	public void showColors(WebElement articulo) {
		By byColorButton = By.xpath("." + XP_COLOR_ARTICLE_BUTTON);
		click(articulo).by(byColorButton).exec();
	}
	
	@Override
	public boolean backTo1erArticulo() throws InterruptedException {
		return backTo1erArticulo(XP_UP_BUTTON);
	}	
	
	@Override
	public StateFavorito getStateHearthIcon(int iconNumber) {
		return commonNormal.getStateHearthIcon(iconNumber);
	}
	
	@Override
	public String getXPathArticleHearthIcon(int posArticulo) {
		return commonNormal.getXPathArticleHearthIcon(posArticulo);
	}
	
	@Override
	public int getNumFavoritoIcons() {
		return commonNormal.getNumFavoritoIcons();
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
	
	public boolean isVisibleColorTags(List<Color> colors) {
		return new SecFiltrosMobilNoGenesis().isVisibleColorTags(colors);
	}

}
