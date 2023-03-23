package com.mng.robotest.domains.footer.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.pageobject.shop.modales.ModalClubMangoLikes;

public class SecFooter extends PageBase {

	private static final String XPATH_CAPA_SHOP = "//div[@id='nav-footer']";
	private static final String XPATH_CAPA_OUTLET = "//footer[@id='footerMNG']";
	
	private final SecNewsLetter secNewsLetter = new SecNewsLetter();
	
	private static final List<AppEcom> FOOTER_SHOP = Arrays.asList(AppEcom.shop);
	private static final List<AppEcom> FOOTER_OUTLET = Arrays.asList(AppEcom.outlet);
	private static final List<AppEcom> FOOTER_ALL = Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf);
	
	private static final List<Channel> CHANNEL_DESKTOP = Arrays.asList(Channel.desktop);
	private static final List<Channel> CHANNEL_MOBILE = Arrays.asList(Channel.mobile, Channel.tablet);
	private static final List<Channel> CHANNEL_ALL = Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet);	
	
	public enum FooterLink {
		AYUDA(FOOTER_SHOP, CHANNEL_ALL, "//a[@data-ga-label='ayuda' and text()[contains(.,'Ayuda')]]", false), 
		MIS_COMPRAS(FOOTER_SHOP, CHANNEL_ALL, "//a[@data-ga-label='miscompras']", false), 
		DEVOLUCIONES(FOOTER_ALL, CHANNEL_ALL, "//a[@data-ga-label='devoluciones' and text()[contains(.,'Devoluciones')]]", false), 
		TIENDAS(FOOTER_SHOP, CHANNEL_ALL, "//a[@data-ga-label='buscarTiendas']", false),
		MANGO_CARD(FOOTER_SHOP, CHANNEL_ALL, "//a[@data-ga-label='tarjetaMango' and text()[contains(.,'Mango Card')]]", false),
		CHEQUE_REGALO(FOOTER_SHOP, CHANNEL_DESKTOP, "//a[@data-ga-label='cheques' and text()[contains(.,'egalo')]]", false),
		CHEQUE_REGALO_OLD(FOOTER_SHOP, CHANNEL_DESKTOP, "//a[@data-ga-label='cheques']", false),
		APPS(FOOTER_SHOP, CHANNEL_ALL, "//a[@data-ga-label='apps' and text()[contains(.,'Apps')]]", false), 
		EMPRESA(FOOTER_SHOP, CHANNEL_DESKTOP, "//a[@data-ga-label='pieEmpresa' and text()[contains(.,'Empresa')]]", true), 
		FRANQUICIAS(FOOTER_SHOP, CHANNEL_DESKTOP, "//a[@data-ga-label='pieFranquicias' and text()[contains(.,'Franquicias')]]", true), 
		TRABAJA_CON_NOSOTROS(FOOTER_ALL, CHANNEL_ALL, "//a[@data-ga-label[contains(.,'pieTrabajar')] and text()[contains(.,'Trabaja')]]", true), 
		PRENSA(FOOTER_SHOP, CHANNEL_DESKTOP, "//a[@data-ga-label='pressroom' and text()[contains(.,'Prensa')]]", true), 
		MANGO_OUTLET(FOOTER_SHOP, CHANNEL_ALL, "//a[@data-ga-label='outlet' and text()[contains(.,'Mango Outlet')]]", true),
		
		PREGUNTAS_FRECUENTES(FOOTER_OUTLET, CHANNEL_ALL, "//a[@data-ga-label='ayuda' and text()[contains(.,'Preguntas Frecuentes')]]", false), 
		//PEDIDOS(FOOTER_OUTLET, CHANNEL_ALL, "//a[@data-ga-label='pedidos' and text()[contains(.,'Pedidos')]]", false), 
		ENVIO(FOOTER_OUTLET, CHANNEL_ALL, "//a[@data-ga-label='envio' and text()[contains(.,'Envío')]]", false), 
		FORMAS_DE_PAGO(FOOTER_OUTLET, CHANNEL_ALL, "//a[@data-ga-label='pago' and text()[contains(.,'Formas de pago')]]", false), 
		GUIA_DE_TALLAS(FOOTER_OUTLET, CHANNEL_ALL, "//a[@data-ga-label='guiaTallas']", true),
		MANGO(FOOTER_OUTLET, CHANNEL_ALL, "//a[@data-ga-label='shop' and (text()[contains(.,'MANGO')] or text()[contains(.,'Mango')])]", true); 
		//TRABAJA_CON_NOSOTROS_OUTLET(FOOTER_OUTLET, CHANNEL_ALL, "//a[@data-ga-label='pieTrabajarNew' and text()[contains(.,'Trabaja')]]", true);
		
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
		return state(Present, getXPathCapaFooter()).check();
	}
	
	public boolean isVisible() {
		waitLoadPage();
		return state(Visible, getXPathCapaFooter()).check();
	}	
	
	public void clickLink(FooterLink footerType) {
		new ModalClubMangoLikes().closeModalIfVisible();
		moveToElement(footerType.getXPathRelativeCapa());
		
		String xpathLink = footerType.getXPathRelativeCapa();
		state(Visible, xpathLink).wait(2).check();
		click(xpathLink).exec();
	}
	
	public String clickLinkAndGetWindowFatherHandle(FooterLink footerType) {
		clickLink(footerType);
		String windowFatherHandle = driver.getWindowHandle();
		if (footerType.pageInNewTab) {
			switchToAnotherWindow(driver, windowFatherHandle);
			waitForPageLoaded(driver, 10);
		}
		return windowFatherHandle;
	}

	public void clickLinkCambioPais() {
		click(getXPathLinkCambioPais()).exec();
	}

	public boolean checkFooter(FooterLink footerLink) {
		return state(Present, getXPathLink(footerLink)).check();
	}
	
	public boolean isTextoLegalRGPDPresent() {
		return state(Present, XPATH_LEGA_LRGPD).check();
	}
	
	public void moveTo() {
		String xpathFooter = getXPathCapaFooter();
		if (state(Visible, xpathFooter).check()) {
			moveToElement(xpathFooter);
		}
	}
	
	public void clickFooterSuscripcion() {
		secNewsLetter.clickFooterSuscripcion();
	}
	
	public boolean newsLetterMsgContains(String literal) {
		return secNewsLetter.newsLetterMsgContains(literal);
	}
}
