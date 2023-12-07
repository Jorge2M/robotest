package com.mng.robotest.tests.domains.galeria.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.desktop.SecFiltrosDesktopKondo;
import com.mng.robotest.testslegacy.data.Color;

public class PageGaleriaDesktopKondo extends PageGaleriaDesktop {

	private final CommonGaleriaKondo commonKondo = new CommonGaleriaKondo();
	private static final String XP_ICONO_UP_GALERY = "//button[@aria-label='Scroll up']";
	
	@Override
	public String getXPathIconUpGalery() {
		return XP_ICONO_UP_GALERY;
	}
	
	public PageGaleriaDesktopKondo() {
		super();
	}
	
	public PageGaleriaDesktopKondo(From from) {
		super(from);
	}

	@Override
	protected String getXPathArticulo() {
		return commonKondo.getXPathArticulo();
	}
	
	@Override
	protected String getXPathNombreRelativeToArticle() {
		return commonKondo.getXPathNombreRelativeToArticle();
	}
	
	private String getXPathLinkNumColumnas(NumColumnas numColumnas) {
		return ("//*[@data-testid='plp.columns" + getNumColumnas(numColumnas) + ".button']");
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
	public void clickLinkColumnas(NumColumnas numColumnas) {
		String xpathLink = getXPathLinkNumColumnas(numColumnas);
		click(xpathLink).exec();
	}	

	@Override
	public int getLayoutNumColumnas() {
		return commonKondo.getLayoutNumColumnas();
	}
	
	@Override
	public List<String> searchForArticlesNoValid(List<String> articleNames) {
		scrollToPageFromFirst(2);
		backTo1erArticulo();
		return getArticlesNoValid(articleNames);
	}
	
	public boolean isVisibleColorTags(List<Color> colors) {
		return new SecFiltrosDesktopKondo().isVisibleColorTags(colors);
	}

}
