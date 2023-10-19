package com.mng.robotest.tests.domains.galeria.pageobjects;

import org.openqa.selenium.WebElement;
import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;

public class PageGaleriaDeviceKondo extends PageGaleriaDevice {

	private final CommonGaleriaKondo commonKondo = new CommonGaleriaKondo();
	
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
	
}
