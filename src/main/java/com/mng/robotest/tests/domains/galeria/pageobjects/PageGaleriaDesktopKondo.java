package com.mng.robotest.tests.domains.galeria.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.desktop.SecFiltrosDesktopKondo;
import com.mng.robotest.testslegacy.data.Color;

public class PageGaleriaDesktopKondo extends PageGaleriaDesktop {

	private final CommonGaleriaKondo commonKondo = new CommonGaleriaKondo();
	
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
	
	public boolean isVisibleColorTags(List<Color> colors) {
		return new SecFiltrosDesktopKondo().isVisibleColorTags(colors);
	}
	
}
