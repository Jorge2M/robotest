package com.mng.robotest.domains.micuenta.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDetalleCompraOldOld extends PageBase implements PageDetallePedido {
	
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
		return state(Present, XPATH_DIV_DETALLE).check();
	}
	
	@Override
	public boolean isPresentImporteTotal(String importeTotal, String codPais) {
		return (ImporteScreen.isPresentImporteInElements(importeTotal, codPais, "//td[@class='txt12ArialB']/../*", driver));
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
	
	public void clickIrATiendaButton() {
		click(XPATH_IR_A_TIENDA_BUTTON).exec();
	}	
}
