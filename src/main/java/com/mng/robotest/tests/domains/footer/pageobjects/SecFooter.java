package com.mng.robotest.tests.domains.footer.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalsSubscriptions;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts.SUSCRIPCION_EN_FOOTER_Y_NON_MODAL_LEGAL_TEXTS;

public class SecFooter extends PageBase {

	private final SecNewsLetter secNewsLetter = new SecNewsLetter();
	
	private static final List<AppEcom> FOOTER_SHOP = Arrays.asList(AppEcom.shop);
	private static final List<AppEcom> FOOTER_OUTLET = Arrays.asList(AppEcom.outlet);
	private static final List<AppEcom> FOOTER_ALL = Arrays.asList(AppEcom.shop, AppEcom.outlet);
	
	private static final List<Channel> CHANNEL_DESKTOP = Arrays.asList(Channel.desktop);
	private static final List<Channel> CHANNEL_ALL = Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet);	
	
	//TODO pendiente data-testids de Outlet (Genesis) solicitados a GPS mediante tícket ?????
	public enum FooterLink {
		AYUDA(FOOTER_ALL, CHANNEL_ALL,
			"//a[@href[contains(.,'/help')]]", 
			"//a[@href[contains(.,'/help')]]", false, true), 
		MIS_COMPRAS(FOOTER_ALL, CHANNEL_ALL, 
			"//a[@data-ga-label='miscompras']", 
			"//a[text()='Mis compras']", false, true), 
		DEVOLUCIONES(FOOTER_SHOP, CHANNEL_ALL,
			"//a[@data-ga-label='devoluciones' and text()[contains(.,'Devoluciones')]]", 
			"//a[text()='Devoluciones']", false, true), 
		TIENDAS(FOOTER_SHOP, CHANNEL_ALL, 
			"//a[@data-ga-label='buscarTiendas']", 
			"//a[text()='Tiendas']", false, false),
		CHEQUE_REGALO(FOOTER_SHOP, CHANNEL_DESKTOP, 
			"//a[@data-ga-label='cheques' and text()[contains(.,'egalo')]]", 
			"//a[text()='Tarjeta regalo']", false, true),
		CHEQUE_REGALO_OLD(FOOTER_SHOP, CHANNEL_DESKTOP, 
			"//a[@data-ga-label='cheques']", 
			"//a[text()='Carte cadeau' or @href[contains(.,'/chequeRegalo.faces')]]", false, true),
		APPS(FOOTER_SHOP, CHANNEL_ALL, 
			"//a[@data-ga-label='apps' and text()[contains(.,'Apps')]]", 
			"//a[text()='Apps']", false, false), 
		EMPRESA(FOOTER_SHOP, CHANNEL_DESKTOP,
			"//a[@data-ga-label='pieEmpresa' and text()[contains(.,'Empresa')]]", 
			"//a[text()='Empresa']", true, true), 
		FRANQUICIAS(FOOTER_SHOP, CHANNEL_DESKTOP, 
			"//a[@data-ga-label='pieFranquicias' and text()[contains(.,'Franquicias')]]", 
			"//a[text()='Franquicias']", true, false), 
		TRABAJA_CON_NOSOTROS(FOOTER_ALL, CHANNEL_ALL, 
			"//a[@data-ga-label[contains(.,'pieTrabajar')] and text()[contains(.,'Trabaja')]]", 
			"//a[text()='Trabaja con nosotros']", true, true), 
		PRENSA(FOOTER_ALL, CHANNEL_DESKTOP,
			"//a[@data-ga-label='pressroom' and text()[contains(.,'Prensa')]]", 
			"//a[text()='Prensa']", true, true), 
		MANGO_OUTLET(FOOTER_SHOP, CHANNEL_ALL, 
			"//a[@data-ga-label='outlet' and text()[contains(.,'Mango Outlet')]]", 
			"//a[text()='Mango Outlet']", true, true),
		ENVIO(FOOTER_OUTLET, CHANNEL_ALL, 
			"//a[@data-ga-label='envio' and text()[contains(.,'Envío')]]", 
			"//a[text()='Envíos']", false, false), 
		GUIA_DE_TALLAS(FOOTER_OUTLET, CHANNEL_ALL, 
			"//a[@data-ga-label='guiaTallas']", 
			"//a[text()='Guía de tallas']", true, false);
		
		List<AppEcom> appList;
		List<Channel> channel;
		String xpath;
		String xpathGenesis;
		boolean pageInNewTab;
		private String xpCapa = "//div[@id='nav-footer']";
		private String xpCapaGenesis = "//footer";
		private boolean isInGenesis;
		
		private FooterLink(
				List<AppEcom> appList, List<Channel> channel, String xpath, String xpathGenesis, 
				boolean pageInNewTab, boolean isInGenesis) {
			this.appList = appList;
			this.channel = channel;
			this.xpath = xpath;
			this.xpathGenesis = xpathGenesis;
			this.pageInNewTab = pageInNewTab;
			this.isInGenesis = isInGenesis;
		}
		
		public String getXPath() {
			String xpathFull = xpCapa + xpath;
			String xpathGenesisFull = xpCapaGenesis + xpathGenesis;
			return "(" + xpathFull + " | " + xpathGenesisFull + ")";
		}
		
		public boolean pageInNewTab(AppEcom app, Pais pais) {
			return false;
		}
		
		public boolean isInGenesis() {
			return isInGenesis;
		}
		
		public static List<FooterLink> getFooterLinksFiltered(AppEcom app, Channel channel) {
			List<FooterLink> listLinksToReturn = new ArrayList<>();
			for (var footerLink : FooterLink.values()) {
				if (footerLink.appList.contains(app) && 
					footerLink.channel.contains(channel) &&
					footerLink.isInGenesis() &&
					footerLink!=FooterLink.CHEQUE_REGALO_OLD) {
					listLinksToReturn.add(footerLink);
				}
			}
			return listLinksToReturn;
		}
	}
	
//	private static final String XP_CAMBIO_PAIS_SHOP = "//div[@class[contains(.,'modalCambioPaisShow')]]";
	private static final String XP_CAMBIO_PAIS_GENESIS = "//*[@data-testid='changeButton']";
	
	public SecFooter() {
		super(SUSCRIPCION_EN_FOOTER_Y_NON_MODAL_LEGAL_TEXTS);
	}
	
	private String getXPathLink(FooterLink footerType) {
		return footerType.getXPath();
	}
	
	private String getXPathLinkCambioPais() {
//		if (isOutlet()) {
			return XP_CAMBIO_PAIS_GENESIS;
//		}
//		return XP_CAMBIO_PAIS_SHOP;
	}
	
	public boolean isPresent() {
		String xpathAyuda = FooterLink.AYUDA.getXPath();
		return state(PRESENT, xpathAyuda).check();
	}
	
	public boolean isVisible() {
		waitLoadPage();
		String xpathAyuda = FooterLink.AYUDA.getXPath();
		return state(VISIBLE, xpathAyuda).check();
	}	
	
	public void clickLink(FooterLink footerType) {
		new ModalsSubscriptions().closeAllIfVisible();
		String xpathLink = footerType.getXPath(); 
		moveToElement(xpathLink);
		state(VISIBLE, xpathLink).wait(2).check();
		waitMillis(500);
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
		return state(PRESENT, getXPathLink(footerLink)).check();
	}
	
	public void moveTo() {
		String xpathAyuda = FooterLink.AYUDA.getXPath();
		if (state(VISIBLE, xpathAyuda).check()) {
			moveToElement(xpathAyuda);
		}
	}
	
	public void clickFooterSuscripcion() {
		secNewsLetter.clickFooterSuscripcion();
	}
	
	public boolean newsLetterMsgContains(String literal) {
		return secNewsLetter.newsLetterMsgContains(literal);
	}
	
}
