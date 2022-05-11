package com.mng.robotest.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDetalleCompraMobil extends PageDetalleCompra {
	
	private final SectionPrendas sectionPrendas;
	private final AppEcom app;
	
	private static String XPathIdTicket = "//*[@data-testid[contains(.,'purchaseDetail.purchaseNumber')]]";
	private static String XPathLineaImporte = "//*[@data-testid[contains(.,'detail.totalPrice')]]";
	//private static String XPathLinkToMisCompras = "//button/*[@class[contains(.,'icon-fill-prev')]]/.."; 
	
	public PageDetalleCompraMobil(Channel channel, AppEcom app, WebDriver driver) {
		super(channel, driver);
		this.sectionPrendas = new SectionPrendas(driver);
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
		return PageObjTM.getElementWeb(By.xpath(XPathIdTicket), driver).getText();
	}
	@Override
	public String getImporte() {
		state(State.Visible, By.xpath(XPathLineaImporte)).wait(2).check();
		return PageObjTM.getElementWeb(By.xpath(XPathLineaImporte), driver).getText();
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
		SecFooter secFooter = new SecFooter(app, driver);
		secFooter.clickLink(FooterLink.miscompras);
		//click(By.xpath(XPathLinkToMisCompras)).exec();
	}
}
