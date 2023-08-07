package com.mng.robotest.domains.micuenta.pageobjects;

import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras.TypeTicket;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDetalleCompraMobil extends PageDetalleCompra {
	
	private final SectionPrendas sectionPrendas = new SectionPrendas();
	
	private static final String XPATH_ID_TICKET = "//*[@data-testid[contains(.,'purchaseDetail.purchaseNumber')]]";
	private static final String XPATH_LINEA_IMPORTE = "//div[@class[contains(.,'text-body-s')]]//*[@data-testid='myPurchases.price']";
	private static final String XPATH_DESPLEGABLE_DATOS_ENVIO = "//button[@id='accordion-purchase-0-title']";
	private static final String XPATH_DATOS_ENVIO = "//dd[@id='accordion-purchase-0']/div";
	
	@Override
	public boolean isPage(int seconds) {
		return isVisibleDataTicket(seconds);
	}
	@Override
	public boolean isPresentImporteTotal(String importeTotal, String codPais) {
		String importe = getImporte().replaceAll("[^\\d.,]", "");  //Eliminamos la divisa;
		return (importe.compareTo(importeTotal)==0);
	}
	@Override
	public boolean isVisiblePrendaUntil(int seconds) {
		return getNumPrendas()>0;
	}
	@Override
	public int getNumPrendas() {
		return sectionPrendas.getNumPrendas();
	}
	@Override
	public boolean isVisibleDataTicket(int seconds) {
		return state(Visible, XPATH_ID_TICKET).wait(seconds).check();
	}
	@Override
	public boolean isVisibleIdTicket(int seconds) {
		return state(Visible, XPATH_ID_TICKET).wait(seconds).check();
	}
	@Override
	public String getIdTicket(TypeTicket typeTicket) {
		return getElementWeb(XPATH_ID_TICKET).getText();
	}
	@Override
	public String getImporte() {
		state(Visible, XPATH_LINEA_IMPORTE).wait(2).check();
		return getElementWeb(XPATH_LINEA_IMPORTE).getText();
	}
	@Override
	public String getDireccionEnvioOnline() {
		makeVisibleDatosEnvio();
		return getElement(XPATH_DATOS_ENVIO).getText(); 
	}
	@Override
	public String getReferenciaArticulo(int posArticulo) {
		return sectionPrendas.getReferenciaArticulo(posArticulo);
	}
	@Override
	public String getNombreArticulo(int posArticulo) {
		return sectionPrendas.getNombreArticulo(posArticulo);
	}
	@Override
	public String getPrecioArticulo(int posArticulo) {
		return sectionPrendas.getPrecioArticulo(posArticulo);
	}
	@Override
	public void selectArticulo(int posArticulo) {
		sectionPrendas.selectArticulo(posArticulo);
	}
	
	private void makeVisibleDatosEnvio() {
		if (state(Invisible, XPATH_DATOS_ENVIO).check()) {
			click(XPATH_DESPLEGABLE_DATOS_ENVIO).exec();
			state(Visible, XPATH_DATOS_ENVIO).wait(1).check();
		}
	}
}
