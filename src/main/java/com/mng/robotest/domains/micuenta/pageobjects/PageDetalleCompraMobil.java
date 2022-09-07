package com.mng.robotest.domains.micuenta.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.footer.pageobjects.SecFooter;
import com.mng.robotest.domains.footer.pageobjects.SecFooter.FooterLink;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras.TypeTicket;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDetalleCompraMobil extends PageDetalleCompra {
	
	private final SectionPrendas sectionPrendas = new SectionPrendas();
	
	private static final String XPATH_ID_TICKET = "//*[@data-testid[contains(.,'purchaseDetail.purchaseNumber')]]";
	private static final String XPATH_LINEA_IMPORTE = "//*[@data-testid[contains(.,'detail.totalPrice')]]";
	
	@Override
	public boolean isPage() {
		return isVisibleDataTicket(2);
	}
	@Override
	public boolean isPresentImporteTotal(String importeTotal, String codPais) {
		String importe = getImporte().replaceAll("[^\\d.,]", "");  //Eliminamos la divisa;
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
		//return (driver.findElements(By.xpath(XPathArticulo)).size());
		return sectionPrendas.getNumPrendas();
	}
	@Override
	public boolean isVisibleDataTicket(int maxSeconds) {
		return state(Visible, XPATH_ID_TICKET).wait(maxSeconds).check();
	}
	@Override
	public boolean isVisibleIdTicket(int maxSeconds) {
		return state(State.Visible, XPATH_ID_TICKET).wait(maxSeconds).check();
	}
	@Override
	public String getIdTicket(TypeTicket typeTicket) {
		return getElementWeb(XPATH_ID_TICKET).getText();
	}
	@Override
	public String getImporte() {
		state(State.Visible, XPATH_LINEA_IMPORTE).wait(2).check();
		return getElementWeb(XPATH_LINEA_IMPORTE).getText();
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
		SecFooter secFooter = new SecFooter();
		secFooter.clickLink(FooterLink.MIS_COMPRAS);
	}
}
