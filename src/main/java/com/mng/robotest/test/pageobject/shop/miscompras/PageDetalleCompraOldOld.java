package com.mng.robotest.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageDetalleCompraOldOld extends PageObjTM implements PageDetallePedido {
	
	private static final String XPATH_DIV_DETALLE = "//div[@class[contains(.,'detallePedido')]]";
	private static final String XPATH_LINEA_PRENDA = "//tr/td[@align='left' and @height='30']/..";
	private static final String XPATH_IR_A_TIENDA_BUTTON = "(//div[@id[contains(.,'ListaDetail')]])[1]";
	private static final String XPATH_BACK_BUTTON = "(//div[@id[contains(.,'ListaDetail')]])[2]";
	
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
		return (ImporteScreen.isPresentImporteInElements(importeTotal, codPais, "//td[@class='txt12ArialB']/../*", driver));
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
	
	public void clickIrATiendaButton() {
		click(By.xpath(XPATH_IR_A_TIENDA_BUTTON)).exec();
	}	
}
