package com.mng.robotest.tests.domains.galeria.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.device.SecMultiFiltrosDeviceKondo;
import com.mng.robotest.testslegacy.data.Color;

public class PageGaleriaDeviceKondo extends PageGaleriaDevice {

	private final CommonGaleriaKondo commonKondo = new CommonGaleriaKondo();
	
	private static final String XP_COLOR_ARTICLE_BUTTON = "//*[@data-testid='productCard.showColors.button']";
	private static final String XP_COLOR_ARTICLE_OPTION = "//button[@data-testid='plp.color.selector']";
	private static final String XP_UP_BUTTON = "//*[@data-testid='button-icon']";
	
	public PageGaleriaDeviceKondo() {
		super();
	}
	
	public PageGaleriaDeviceKondo(From from) {
		super(from);
	}

	@Override
	protected String getXPathArticulo() {
		return commonKondo.getXPathArticulo();
	}
	
	@Override
	protected String getXPathArticuloAncestor() {
		return commonKondo.getXPathArticuloAncestor();
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
		return commonKondo.getXPathNombreRelativeToArticle();
	}
	
	@Override
	public String getRefArticulo(WebElement articulo) {
		return commonKondo.getRefArticulo(articulo);
	}	
	
	@Override
	public String getNombreArticulo(WebElement articulo) {
		return commonKondo.getNombreArticulo(articulo);
	}
	
	@Override
	public String getRefColorArticulo(WebElement articulo) {
		return commonKondo.getRefColorArticulo(articulo);
	}	
	
	@Override
	public WebElement getImagenElementArticulo(WebElement articulo) {
		return commonKondo.getImagenElementArticulo(articulo);
	}
	
	@Override
	public void clickSlider(WebElement articulo, TypeSlider typeSlider) {
		commonKondo.clickSlider(articulo, typeSlider);
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
		return new SecMultiFiltrosDeviceKondo().isVisibleColorTags(colors);
	}
}
