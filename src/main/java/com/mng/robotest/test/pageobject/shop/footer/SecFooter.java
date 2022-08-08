package com.mng.robotest.test.pageobject.shop.footer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.modales.ModalClubMangoLikes;


public class SecFooter extends PageBase {

	private final AppEcom app;
	
	private static final String XPATH_CAPA_SHOP = "//div[@id='nav-footer']";
	private static final String XPATH_CAPA_OUTLET = "//footer[@id='footerMNG']";
	
	private final SecNewsLetter secNewsLetter;
	
	static List<AppEcom> footerShop = Arrays.asList(AppEcom.shop);
	static List<AppEcom> footerOutlet = Arrays.asList(AppEcom.outlet);
	static List<AppEcom> footerAll = Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf);
	
	static List<Channel> channelDesktop = Arrays.asList(Channel.desktop);
	static List<Channel> channelMobile = Arrays.asList(Channel.mobile, Channel.tablet);
	static List<Channel> channelAll = Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet);	
	
	public static enum FooterLink {
		AYUDA(footerShop, channelAll, "//a[@data-ga-label='ayuda' and text()[contains(.,'Ayuda')]]", false), 
		MIS_COMPRAS(footerShop, channelAll, "//a[@data-ga-label='miscompras']", false), 
		DEVOLUCIONES(footerAll, channelAll, "//a[@data-ga-label='devoluciones' and text()[contains(.,'Devoluciones')]]", false), 
		TIENDAS(footerShop, channelAll, "//a[@data-ga-label='buscarTiendas']", false),
		MANGO_CARD(footerShop, channelAll, "//a[@data-ga-label='tarjetaMango' and text()[contains(.,'Mango Card')]]", false),
		CHEQUE_REGALO(footerShop, channelDesktop, "//a[@data-ga-label='cheques' and text()[contains(.,'egalo')]]", false),
		CHEQUE_REGALO_OLD(footerShop, channelDesktop, "//a[@data-ga-label='cheques']", false),
		APPS(footerShop, channelAll, "//a[@data-ga-label='apps' and text()[contains(.,'Apps')]]", false), 
		EMPRESA(footerAll, channelDesktop, "//a[@data-ga-label='pieEmpresa' and text()[contains(.,'Empresa')]]", true), 
		FRANQUICIAS(footerShop, channelDesktop, "//a[@data-ga-label='pieFranquicias' and text()[contains(.,'Franquicias')]]", true), 
		TRABAJA_CON_NOSOTROS_SHOP(footerShop, channelAll, "//a[@data-ga-label[contains(.,'pieTrabajar')] and text()[contains(.,'Trabaja')]]", true), 
		PRENSA(footerShop, channelDesktop, "//a[@data-ga-label='pressroom' and text()[contains(.,'Prensa')]]", true), 
		MANGO_OUTLET(footerShop, channelAll, "//a[@data-ga-label='outlet' and text()[contains(.,'Mango Outlet')]]", true),
		
		PREGUNTAS_FRECUENTES(footerOutlet, channelAll, "//a[@data-ga-label='ayuda' and text()[contains(.,'Preguntas Frecuentes')]]", false), 
		PEDIDOS(footerOutlet, channelAll, "//a[@data-ga-label='pedidos' and text()[contains(.,'Pedidos')]]", false), 
		ENVIO(footerOutlet, channelAll, "//a[@data-ga-label='envio' and text()[contains(.,'Envío')]]", false), 
		FORMAS_DE_PAGO(footerOutlet, channelAll, "//a[@data-ga-label='pago' and text()[contains(.,'Formas de pago')]]", false), 
		GUIA_DE_TALLAS(footerOutlet, channelAll, "//a[@data-ga-label='guiaTallas']", true),
		MANGO(footerOutlet, channelAll, "//a[@data-ga-label='shop' and (text()[contains(.,'MANGO')] or text()[contains(.,'Mango')])]", true), 
		TRABAJA_CON_NOSOTROS_OUTLET(footerOutlet, channelAll, "//a[@data-ga-label='pieTrabajarNew' and text()[contains(.,'Trabaja')]]", true);
		
		List<AppEcom> appList;
		List<Channel> channel;
		String xpathRelativeCapa;
		boolean pageInNewTab;
		
		private FooterLink(List<AppEcom> appList, List<Channel> channel, String xpathRelativeCapa, boolean pageInNewTab) {
			this.appList = appList;
			this.channel = channel;
			this.xpathRelativeCapa = xpathRelativeCapa;
			this.pageInNewTab = pageInNewTab;
		}
		
		public String getXPathRelativeCapa() {
			return this.xpathRelativeCapa;
		}
		
		public boolean pageInNewTab() {
			return this.pageInNewTab;
		}
		
		public static List<FooterLink> getFooterLinksFiltered(AppEcom app, Channel channel) {
			List<FooterLink> listLinksToReturn = new ArrayList<>();
			for (FooterLink footerLink : FooterLink.values()) {
				if (footerLink.appList.contains(app) && footerLink.channel.contains(channel)) {
					listLinksToReturn.add(footerLink);
				}
			}

			return listLinksToReturn;
		}

	}
	
	
	private static final String XPATH_LEGA_LRGPD = "//p[@class='gdpr-text gdpr-data-protection']";
	private static final String XPATH_CAMBIO_PAIS_SHOP = "//div[@class[contains(.,'modalCambioPaisShow')]]";
	private static final String XPATH_CAMBIO_PAIS_OUTLET = "//span[@class[contains(.,'countrySelector')]]";
	
	public SecFooter(AppEcom app) {
		this.app = app;
		this.secNewsLetter = new SecNewsLetter(app);
	}
	
	private String getXPathCapaFooter() {
		switch (app) {
		case outlet:
			return XPATH_CAPA_OUTLET;
		case shop:
		default:
			return XPATH_CAPA_SHOP;
		}
	}
	
	private String getXPathLink(FooterLink footerType) {
		return (getXPathCapaFooter() + footerType.getXPathRelativeCapa());
	}
	
	private String getXPathLinkCambioPais() {
		if (app==AppEcom.outlet) {
			return XPATH_CAMBIO_PAIS_OUTLET;
		}
		return XPATH_CAMBIO_PAIS_SHOP;
	}
	
	public boolean isPresent() {
		String xpath = getXPathCapaFooter();
		return (state(Present, By.xpath(xpath)).check());
	}
	
	public boolean isVisible() throws Exception {
		String xpath = getXPathCapaFooter();
		waitForPageLoaded(driver);
		return (state(Visible, By.xpath(xpath)).check());
	}	
	
	public void clickLink(FooterLink footerType) {
		ModalClubMangoLikes.closeModalIfVisible(driver);
		moveToElement(By.xpath(footerType.getXPathRelativeCapa()), driver);
		
		By byLink = By.xpath(footerType.getXPathRelativeCapa());
		state(State.Visible, byLink).wait(2).check();
		click(byLink).exec();
	}
	
	public String clickLinkAndGetWindowFatherHandle(FooterLink footerType) throws Exception {
		clickLink(footerType);
		String windowFatherHandle = driver.getWindowHandle();
		if (footerType.pageInNewTab) {
			switchToAnotherWindow(driver, windowFatherHandle);
			waitForPageLoaded(driver, 10);
		}
		return windowFatherHandle;
	}

	public void clickLinkCambioPais() {
		String xpathLink = getXPathLinkCambioPais();
		click(By.xpath(xpathLink)).exec();
	}

	public boolean checkFooters(List<FooterLink> listFooterLinksToValidate) {
		for (FooterLink footerLink : listFooterLinksToValidate) {
			String xpathLink = getXPathLink(footerLink);
			if (!state(Present, By.xpath(xpathLink)).check()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isTextoLegalRGPDPresent() {
		return (state(Present, By.xpath(XPATH_LEGA_LRGPD)).check());
	}
	
	public void moveTo() {
		String xpath = getXPathCapaFooter();
		By footer = By.xpath(xpath);
		if (state(Visible, footer, driver).check()) {
			moveToElement(footer, driver);
		}
	}
	
	public void clickFooterSuscripcion() throws Exception {
		secNewsLetter.clickFooterSuscripcion();
	}
	
	public boolean newsLetterMsgContains(String literal) {
		return secNewsLetter.newsLetterMsgContains(literal);
	}
}
