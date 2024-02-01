package com.mng.robotest.tests.domains.footer.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalsSubscriptions;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts.SUSCRIPCION_EN_FOOTER_Y_NON_MODAL_LEGAL_TEXTS;

public class SecFooter extends PageBase {

	private final SecNewsLetter secNewsLetter = new SecNewsLetter();
	
	private static final List<AppEcom> FOOTER_SHOP = Arrays.asList(AppEcom.shop);
	private static final List<AppEcom> FOOTER_OUTLET = Arrays.asList(AppEcom.outlet);
	private static final List<AppEcom> FOOTER_ALL = Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf);
	
	private static final List<Channel> CHANNEL_DESKTOP = Arrays.asList(Channel.desktop);
	private static final List<Channel> CHANNEL_ALL = Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet);	
	
	//TODO pendiente data-testids de Outlet (Genesis) solicitados a GPS mediante tícket ?????
	public enum FooterLink {
		AYUDA(FOOTER_ALL, CHANNEL_ALL, 
				"//a[@data-ga-label='ayuda' and text()[contains(.,'Ayuda')]]", 
				"//a[text()='Ayuda']", false), 
		MIS_COMPRAS(FOOTER_ALL, CHANNEL_ALL, 
				"//a[@data-ga-label='miscompras']", 
				"//a[text()='Mis compras']", false), 
		DEVOLUCIONES(FOOTER_SHOP, CHANNEL_ALL, 
				"//a[@data-ga-label='devoluciones' and text()[contains(.,'Devoluciones')]]", 
				"//a[text()='Devoluciones']", false), 
		TIENDAS(FOOTER_SHOP, CHANNEL_ALL, 
				"//a[@data-ga-label='buscarTiendas']", 
				"//a[text()='Tiendas']", false),
		CHEQUE_REGALO(FOOTER_SHOP, CHANNEL_DESKTOP, 
				"//a[@data-ga-label='cheques' and text()[contains(.,'egalo')]]", 
				"//a[text()='Tarjeta Regalo']", false),
		CHEQUE_REGALO_OLD(FOOTER_SHOP, CHANNEL_DESKTOP, 
				"//a[@data-ga-label='cheques']", 
				"//a[text()='Tarjeta Regalo']", false),
		APPS(FOOTER_SHOP, CHANNEL_ALL, 
				"//a[@data-ga-label='apps' and text()[contains(.,'Apps')]]", 
				"//a[text()='Apps']", false), 
		EMPRESA(FOOTER_SHOP, CHANNEL_DESKTOP, 
				"//a[@data-ga-label='pieEmpresa' and text()[contains(.,'Empresa')]]", 
				"//a[text()='Empresa']", true), 
		FRANQUICIAS(FOOTER_SHOP, CHANNEL_DESKTOP, 
				"//a[@data-ga-label='pieFranquicias' and text()[contains(.,'Franquicias')]]", 
				"//a[text()='Franquicias']", true), 
		TRABAJA_CON_NOSOTROS(FOOTER_ALL, CHANNEL_ALL, 
				"//a[@data-ga-label[contains(.,'pieTrabajar')] and text()[contains(.,'Trabaja')]]", 
				"//a[text()='Trabaja con nosotros']", true), 
		PRENSA(FOOTER_ALL, CHANNEL_DESKTOP, 
				"//a[@data-ga-label='pressroom' and text()[contains(.,'Prensa')]]", 
				"//a[text()='Prensa']", true), 
		MANGO_OUTLET(FOOTER_SHOP, CHANNEL_ALL, 
				"//a[@data-ga-label='outlet' and text()[contains(.,'Mango Outlet')]]", 
				"//a[text()='Mango Outlet']", true),
		ENVIO(FOOTER_OUTLET, CHANNEL_ALL, 
				"//a[@data-ga-label='envio' and text()[contains(.,'Envío')]]", 
				"//a[text()='Envíos']", false), 
		GUIA_DE_TALLAS(FOOTER_OUTLET, CHANNEL_ALL, 
				"//a[@data-ga-label='guiaTallas']", 
				"//a[text()='Guía de tallas']", true);
		
		List<AppEcom> appList;
		List<Channel> channel;
		String xpath;
		String xpathGenesis;
		boolean pageInNewTab;
		private String xpCapa = "//div[@id='nav-footer']";
		private String xpCapaGenesis = "//footer";
		
		private FooterLink(
				List<AppEcom> appList, List<Channel> channel, String xpath, String xpathGenesis, 
				boolean pageInNewTab) {
			this.appList = appList;
			this.channel = channel;
			this.xpath = xpath;
			this.xpathGenesis = xpathGenesis;
			this.pageInNewTab = pageInNewTab;
		}
		
		public String getXPathCapa(AppEcom app) {
			if (app==AppEcom.outlet) {
				return xpCapaGenesis;
			}
			return xpCapa;
		}
		
		private String getXPathLink(AppEcom app) {
			if (app==AppEcom.outlet) {
				return xpathGenesis;
			}
			return xpath;			
		}
		
		public String getXPath(AppEcom app) {
			return getXPathCapa(app) + getXPathLink(app);
		}
		
		public boolean pageInNewTab(AppEcom app) {
			if (app==AppEcom.outlet) {
				return false;
			}
			return this.pageInNewTab;
		}
		
		public static List<FooterLink> getFooterLinksFiltered(AppEcom app, Channel channel) {
			List<FooterLink> listLinksToReturn = new ArrayList<>();
			for (var footerLink : FooterLink.values()) {
				if (footerLink.appList.contains(app) && footerLink.channel.contains(channel)) {
					listLinksToReturn.add(footerLink);
				}
			}
			return listLinksToReturn;
		}

	}
	
	private static final String XP_CAMBIO_PAIS_SHOP = "//div[@class[contains(.,'modalCambioPaisShow')]]";
	private static final String XP_CAMBIO_PAIS_GENESIS = "//*[@data-testid='changeButton']";
	
	public SecFooter() {
		super(SUSCRIPCION_EN_FOOTER_Y_NON_MODAL_LEGAL_TEXTS);
	}
	
	private String getXPathLink(FooterLink footerType) {
		return footerType.getXPath(app);
	}
	
	private String getXPathLinkCambioPais() {
		if (isOutlet()) {
			return XP_CAMBIO_PAIS_GENESIS;
		}
		return XP_CAMBIO_PAIS_SHOP;
	}
	
	public boolean isPresent() {
		return state(PRESENT, FooterLink.AYUDA.getXPathCapa(app)).check();
	}
	
	public boolean isVisible() {
		waitLoadPage();
		return state(VISIBLE, FooterLink.AYUDA.getXPathCapa(app)).check();
	}	
	
	public void clickLink(FooterLink footerType) {
		new ModalsSubscriptions().closeAllIfVisible();
		moveToElement(footerType.getXPath(app));
		
		String xpathLink = footerType.getXPath(app);
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
		String xpathFooter = FooterLink.AYUDA.getXPathCapa(app);
		if (state(VISIBLE, xpathFooter).check()) {
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
