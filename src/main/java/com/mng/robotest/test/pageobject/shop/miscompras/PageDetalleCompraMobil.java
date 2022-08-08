package com.mng.robotest.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageDetalleCompraMobil extends PageDetalleCompra {
	
	private final SectionPrendas sectionPrendas = new SectionPrendas();
	private final AppEcom app;
	
	private static String XPathIdTicket = "//*[@data-testid[contains(.,'purchaseDetail.purchaseNumber')]]";
	private static String XPathLineaImporte = "//*[@data-testid[contains(.,'detail.totalPrice')]]";
	
	public PageDetalleCompraMobil(Channel channel, AppEcom app) {
		super(channel);
		this.app = app;
	}
	
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
		return (state(Visible, By.xpath(XPathIdTicket)).wait(maxSeconds).check());
	}
	@Override
	public boolean isVisibleIdTicket(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathIdTicket)).wait(maxSeconds).check();
	}
	@Override
	public String getIdTicket(TypeTicket typeTicket) {
		return getElementWeb(By.xpath(XPathIdTicket), driver).getText();
	}
	@Override
	public String getImporte() {
		state(State.Visible, By.xpath(XPathLineaImporte)).wait(2).check();
		return getElementWeb(By.xpath(XPathLineaImporte), driver).getText();
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
		SecFooter secFooter = new SecFooter(app);
		secFooter.clickLink(FooterLink.MIS_COMPRAS);
	}
}
