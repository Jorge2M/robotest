package com.mng.robotest.tests.domains.galeria.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.device.SecMultiFiltrosDeviceNormal;
import com.mng.robotest.testslegacy.data.Color;

public class PageGaleriaDeviceNormal extends PageGaleriaDevice {

	private final CommonGaleriaNormal commonNormal = new CommonGaleriaNormal();
	
	private static final String XP_COLOR_ARTICLE_BUTTON = "//*[@data-testid='productCard.showColors.button']";
	private static final String XP_COLOR_ARTICLE_OPTION = "//button[@data-testid='plp.color.selector']";
	private static final String XP_UP_BUTTON = "//button[@aria-label='Scroll up']/*[@data-testid='button-icon']";
	
	public PageGaleriaDeviceNormal() {
		super();
	}
	
	@Override
	protected String getXPathArticulo() {
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
	protected String getXPathNombreRelativeToArticle() {
		return commonNormal.getXPathNombreRelativeToArticle();
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
	
	public boolean isVisibleColorTags(List<Color> colors) {
		return new SecMultiFiltrosDeviceNormal().isVisibleColorTags(colors);
	}
}
