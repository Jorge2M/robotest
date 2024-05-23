package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.commons.entity.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.desktop.SecFiltrosDesktopNoGenesis;
import com.mng.robotest.testslegacy.data.Color;

public class PageGaleriaDesktopNoGenesis extends PageGaleriaDesktop {

	private final CommonGaleriaNoGenesis commonNormal = new CommonGaleriaNoGenesis();
	private static final String XP_ICONO_UP_GALERY = "//button[@aria-label='Scroll up']";
	private static final String XP_HEADER_ARTICLES = "//header[@id='catalogTitle']/h1";
	
	@Override
	public String getXPathIconUpGalery() {
		return XP_ICONO_UP_GALERY;
	}
	
	public PageGaleriaDesktopNoGenesis() {
		super();
	}
	
	@Override
	public String getXPathArticulo() {
		return commonNormal.getXPathArticulo();
	}
	
	@Override
	public String getXPathNombreRelativeToArticle() {
		return commonNormal.getXPathNombreRelativeToArticle();
	}
	
	private String getXPathLinkNumColumnas(NumColumnas numColumnas) {
		return ("//*[@data-testid='plp.columns" + getNumColumnas(numColumnas) + ".button']");
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
	public void clickLinkColumnas(NumColumnas numColumnas) {
		String xpathLink = getXPathLinkNumColumnas(numColumnas);
		click(xpathLink).exec();
	}	

	@Override
	public int getLayoutNumColumnas() {
		return commonNormal.getLayoutNumColumnas();
	}
	
	@Override
	public List<String> searchForArticlesNoValid(List<String> articleNames) {
		scrollToPageFromFirst(2);
		backTo1erArticulo();
		return getArticlesNoValid(articleNames);
	}
	
	@Override
	public boolean isHeaderArticlesVisible(String textHeader) {
		if (state(VISIBLE, XP_HEADER_ARTICLES).check()) {
			return getElement(XP_HEADER_ARTICLES).getText().toLowerCase().contains(textHeader.toLowerCase());
		}
		return false;
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
	
	public boolean isVisibleColorTags(List<Color> colors) {
		return new SecFiltrosDesktopNoGenesis().isVisibleColorTags(colors);
	}

}
