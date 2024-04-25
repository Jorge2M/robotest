package com.mng.robotest.tests.domains.galeria.pageobjects.genesis;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.commons.PageGaleriaDevice;
import com.mng.robotest.tests.domains.galeria.pageobjects.commons.entity.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.device.SecMultiFiltrosDeviceNormal;
import com.mng.robotest.testslegacy.data.Color;

public class PageGaleriaDeviceGenesis extends PageGaleriaDevice {

	private final CommonGaleriaGenesis commonGenesis = new CommonGaleriaGenesis();
	
	private static final String XP_COLOR_ARTICLE_BUTTON = "//*[@data-testid='productCard.colorSheet']/../button";
	private static final String XP_COLOR_ARTICLE_OPTION = "//*[@data-testid='productCard-color-sheet']//button";
	
	public PageGaleriaDeviceGenesis() {
		super();
	}
	
	@Override
	protected String getXPathArticulo() {
		return commonGenesis.getXPathArticulo();
	}
	
	@Override
	protected String getXPathArticuloAncestor() {
		return commonGenesis.getXPathArticuloAncestor();
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
	public void showColors(WebElement articulo) {
		By byColorButton = By.xpath("." + XP_COLOR_ARTICLE_BUTTON);
		click(articulo).by(byColorButton).exec();
	}
	
	@Override
	public boolean backTo1erArticulo() throws InterruptedException {
		String xpathUpButton = commonGenesis.getXPathIconUpGalery();
		return backTo1erArticulo(xpathUpButton);
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
		return new SecMultiFiltrosDeviceNormal().isVisibleColorTags(colors);
	}
}
