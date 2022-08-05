package com.mng.robotest.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageDetalleCompraOld extends PageObjTM implements PageDetallePedido {
	
	private static final String XPATH_DIV_DETALLE = "//div[@id='myPurchasesPage']";
	private static final String XPATH_LINEA_PRENDA = "//div[@class[contains(.,'small-box-container')]]";
	private static final String XPATH_BACK_BUTTON = "//div[@class[contains(.,'shopping-breadcrumbs')]]";
	
	@Override
	public DetallePedido getTypeDetalle() {
		return DetallePedido.Old;
	}
	
	@Override
	public boolean isPage() {
		return (state(Present, By.xpath(XPATH_DIV_DETALLE)).check());
	}
	
	@Override
	public boolean isPresentImporteTotal(String importeTotal, String codPais) {
		return (ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver));
	}
	
	@Override
	public boolean isVisiblePrendaUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_LINEA_PRENDA)).wait(maxSeconds).check());
	}
	
	@Override
	public int getNumPrendas() {
		return (driver.findElements(By.xpath(XPATH_LINEA_PRENDA)).size());
	}
	
	@Override
	public void clickBackButton(Channel channel) {
		click(By.xpath(XPATH_BACK_BUTTON)).exec();
	}
}
