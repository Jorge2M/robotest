package com.mng.robotest.domains.micuenta.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras.TypeTicket;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDetalleCompraDesktop extends PageDetalleCompra {
	
	private final SectionPrendas sectionPrendas = new SectionPrendas();
	
	private static final String XPATH_ID_TICKET = "//h2[@class[contains(.,'text-title-xl')]]/span";
	private static final String XPATH_IMPORTE = "//*[@data-testid='price']";
	
	//TODO necesitaría un data-testid
	private static final String XPATH_DIRECCION_ENVIO_ONLINE = XPATH_ID_TICKET + "/../../../div[2]//div[@class[contains(.,'sg-body-small')]]";
	private static final String XPATH_LINK_TO_MIS_COMPRAS = "//*[@data-testid[contains(.,'detail.goBack')]]";
	
	@Override
	public boolean isPage() {
		return isVisibleDataTicket(2);
	}
	@Override
	public boolean isPresentImporteTotal(String importeTotal, String codPais) {
		String importe = getImporte();
		return (importe.compareTo(importeTotal)==0);
	}
	@Override
	public boolean isVisiblePrendaUntil(int maxSeconds) {
		return getNumPrendas()>0;
	}
	@Override
	public void clickBackButton(Channel channel) {
		gotoListaMisCompras();
	}
	@Override
	public int getNumPrendas() {
		return sectionPrendas.getNumPrendas();
	}
	@Override
	public boolean isVisibleDataTicket(int maxSeconds) {
		return state(Visible, getXPathTicket()).wait(maxSeconds).check();
	}
	@Override
	public String getIdTicket(TypeTicket typeTicket) {
		String dataNumTicket = getElement(getXPathTicket()).getText();
		return (getDataRightFrom(":", dataNumTicket));
	}
	@Override
	public boolean isVisibleIdTicket(int maxSeconds) {
		return state(State.Visible, getXPathTicket()).wait(maxSeconds).check();
	}
	@Override
	public String getImporte() {
		state(State.Visible, XPATH_IMPORTE).wait(2).check();
		String importe = getElement(XPATH_IMPORTE).getText();
		return importe.replaceAll("[^\\d.,]", "");  //Eliminamos la divisa;
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
	@Override
	public void gotoListaMisCompras() {
		ModalDetalleArticuloDesktop modalArticulo = new ModalDetalleArticuloDesktop();
		if (modalArticulo.isVisible(0)) {
			modalArticulo.clickAspaForClose();
			modalArticulo.isInvisible(2);
		}
		click(XPATH_LINK_TO_MIS_COMPRAS).exec();
	}
	
	private String getXPathTicket() {
		return XPATH_ID_TICKET;
	}

	public String getDireccionEnvioOnline() {
		return getElement(XPATH_DIRECCION_ENVIO_ONLINE).getText();
	}
}
