package com.mng.robotest.domains.micuenta.pageobjects;

import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras.TypeTicket;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDetalleCompraDesktop extends PageDetalleCompra {
	
	private final SectionPrendas sectionPrendas = new SectionPrendas();
	
	private static final String XPATH_ID_TICKET = "//h2[@class[contains(.,'text-title-xl')]]/span";
	private static final String XPATH_IMPORTE = "//*[@data-testid='myPurchases.price']";
	
	//TODO necesitaría un data-testid
	private static final String XPATH_DIRECCION_ENVIO_ONLINE = XPATH_ID_TICKET + "/../..//h2[@class[contains(.,'text-title-l')]]/..";
	
	@Override
	public boolean isPage(int seconds) {
		return isVisibleDataTicket(seconds);
	}
	@Override
	public boolean isPresentImporteTotal(String importeTotal, String codPais) {
		String importe = getImporte();
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
		return state(Visible, getXPathTicket()).wait(seconds).check();
	}
	@Override
	public String getIdTicket(TypeTicket typeTicket) {
		String dataNumTicket = getElement(getXPathTicket()).getText();
		return (getDataRightFrom(":", dataNumTicket));
	}
	@Override
	public boolean isVisibleIdTicket(int seconds) {
		return state(Visible, getXPathTicket()).wait(seconds).check();
	}
	@Override
	public String getImporte() {
		state(Visible, XPATH_IMPORTE).wait(2).check();
		String importe = getElement(XPATH_IMPORTE).getText();
		return importe.replaceAll("[^\\d.,]", "");  //Eliminamos la divisa;
	}
	@Override
	public String getDireccionEnvioOnline() {
		return getElement(XPATH_DIRECCION_ENVIO_ONLINE).getText();
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
	
	private String getXPathTicket() {
		return XPATH_ID_TICKET;
	}
}
