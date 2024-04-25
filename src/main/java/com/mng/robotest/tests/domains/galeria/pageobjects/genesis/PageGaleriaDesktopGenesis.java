package com.mng.robotest.tests.domains.galeria.pageobjects.genesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.commons.PageGaleriaDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.commons.entity.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.CommonGaleriaNormal;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.desktop.SecFiltrosDesktopNormal;
import com.mng.robotest.testslegacy.data.Color;

public class PageGaleriaDesktopGenesis extends PageGaleriaDesktop {

	private final CommonGaleriaGenesis commonGenesis = new CommonGaleriaGenesis();
	private static final String XP_HEADER_ARTICLES = "//h1[@data-testid[contains(.,'plp.products.list')]]";
	
	@Override
	public String getXPathIconUpGalery() {
		return commonGenesis.getXPathIconUpGalery();
	}
	
	public PageGaleriaDesktopGenesis() {
		super();
	}
	
	@Override
	protected String getXPathArticulo() {
		return commonGenesis.getXPathArticulo();
	}
	
	@Override
	protected String getXPathNombreRelativeToArticle() {
		return commonGenesis.getXPathNombreRelativeToArticle();
	}
	
	@Override
	public String getRefArticulo(WebElement articulo) {
		return commonGenesis.getRefArticulo(articulo);
	}	
	
	@Override
	public String getNombreArticulo(WebElement articulo) {
		return commonGenesis.getNombreArticulo(articulo);
	}
	
	@Override
	public String getRefColorArticulo(WebElement articulo) {
		return commonGenesis.getRefColorArticulo(articulo);
	}	
	
	@Override
	public WebElement getImagenElementArticulo(WebElement articulo) {
		return commonGenesis.getImagenElementArticulo(articulo);
	}
	
	@Override
	public void clickSlider(WebElement articulo, TypeSlider typeSlider) {
		commonGenesis.clickSlider(articulo, typeSlider);
	}	

	@Override
	public void clickLinkColumnas(NumColumnas numColumnas) {
		commonGenesis.clickLinkColumnas(numColumnas);
	}	

	@Override
	public int getLayoutNumColumnas() {
		return commonGenesis.getLayoutNumColumnas();
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
	protected String getXPathArticleHearthIcon(int posArticulo) {
		return commonGenesis.getXPathArticleHearthIcon(posArticulo);
	}

	@Override
	public StateFavorito getStateHearthIcon(int iconNumber) {
		return commonGenesis.getStateHearthIcon(iconNumber);
	}	
	
	@Override
	public int getNumFavoritoIcons() {
		return commonGenesis.getNumFavoritoIcons();
	}
	
	public boolean isVisibleColorTags(List<Color> colors) {
		return new SecFiltrosDesktopNormal().isVisibleColorTags(colors);
	}

}
