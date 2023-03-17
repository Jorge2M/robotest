package com.mng.robotest.domains.micuenta.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDetalleCompraOld extends PageBase implements PageDetallePedido {
	
	private static final String XPATH_DIV_DETALLE = "//div[@id='myPurchasesPage']";
	private static final String XPATH_LINEA_PRENDA = "//div[@class[contains(.,'small-box-container')]]";
	private static final String XPATH_BACK_BUTTON = "//div[@class[contains(.,'shopping-breadcrumbs')]]";
	
	@Override
	public DetallePedido getTypeDetalle() {
		return DetallePedido.OLD;
	}
	
	@Override
	public boolean isPage() {
		return state(Present, XPATH_DIV_DETALLE).check();
	}
	
	@Override
	public boolean isPresentImporteTotal(String importeTotal, String codPais) {
		return (ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver));
	}
	
	@Override
	public boolean isVisiblePrendaUntil(int seconds) {
		return state(Visible, XPATH_LINEA_PRENDA).wait(seconds).check();
	}
	
	@Override
	public int getNumPrendas() {
		return getElements(XPATH_LINEA_PRENDA).size();
	}
	
	@Override
	public void clickBackButton(Channel channel) {
		click(XPATH_BACK_BUTTON).exec();
	}
}
