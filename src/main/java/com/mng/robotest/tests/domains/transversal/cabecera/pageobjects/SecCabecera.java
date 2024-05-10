package com.mng.robotest.tests.domains.transversal.cabecera.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.buscador.pageobjects.SecSearch;
import com.mng.robotest.tests.domains.menus.pageobjects.MenusWebAll;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class SecCabecera extends PageBase {
	
	protected final SecSearch secSearch = SecSearch.getNew(channel);

	private static final String XP_HEADER = "//header";
	private static final String XP_LINK_LOGO_MANGO_V1 = 
			"//a[@class='logo-link' or " + 
			"@class[contains(.,'logo_')] or " + 
			"@title[contains(.,'MANGO Shop Online')]]";
	private static final String XP_LINK_LOGO_MANGO_V2 = 
			"//a/*[@data-test-id[contains(.,'logo.mango')]]/..";
	
	private static final String XP_HAMBURGUER_DEVICE_ICON = "//*[@data-testid='menu.burger']";

	abstract String getXPathNumberArtIcono();
	public abstract boolean isInStateIconoBolsa(State state, int seconds);
	public abstract void clickIconoBolsa();
	public abstract void clickIconoBolsaWhenDisp(int seconds);
	public abstract void hoverIconoBolsa();

	public static SecCabeceraCommon make() {
		return new SecCabeceraCommon();
	}
	
	private String getXPathLinkLogoMango() {
		return "(" + XP_LINK_LOGO_MANGO_V1 + " | " + XP_LINK_LOGO_MANGO_V2 + ")"; 
	}
	
	public void bring(BringTo bringTo) {
		bringElement(getElement(XP_HEADER), bringTo);
	}
	
	public static void buscarTexto(String referencia, Channel channel) {
		var menusUser = new MenusUserWrapper();
		menusUser.isMenuInStateUntil(UserMenu.LUPA, VISIBLE, 1);
		menusUser.clickMenuAndWait(UserMenu.LUPA);
		var secSearch = SecSearch.getNew(channel);
		secSearch.search(referencia);
	}
	
	public boolean clickLogoMango() {
		if (isPresentLogoMango(2)) {
			click(getXPathLinkLogoMango()).exec();
			return true;
		}
		return false;
	}
	
	public boolean isPresentLogoMango(int seconds) {
		return state(PRESENT, getXPathLinkLogoMango()).wait(seconds).check();
	}

	public void hoverLogoMango() {
		if (state(PRESENT, getXPathLinkLogoMango()).check()) {
			moveToElement(getXPathLinkLogoMango());
		}
	}

	public boolean validaLogoMangoGoesToIdioma(IdiomaPais idioma) {
		String xpathLogoIdiom = getXPathLinkLogoMango() + "[@href[contains(.,'/" + idioma.getAcceso() + "')]]";
		return state(PRESENT, xpathLogoIdiom).check();
	}

	public int getNumArticulosBolsa() throws Exception {
		int numArticulos = 0;
		String numArtStr = getNumberArtIcono();
		if (numArtStr.matches("\\d+$")) {
			numArticulos = Integer.parseInt(numArtStr);
		}
		return numArticulos;
	}	
	
	public boolean hayArticulosBolsa() throws Exception {
		return (getNumArticulosBolsa() > 0);
	}

	public String getNumberArtIcono() {
		waitLoadPage(); //Para evitar staleElement en la línea posterior
		String xpathNumberArtIcono = getXPathNumberArtIcono();
		int seconds = (isOutlet()) ? 1 : 0; 
		if (state(VISIBLE, xpathNumberArtIcono).wait(seconds).check()) {
			return getElement(xpathNumberArtIcono).getText();
		}
		return "0";
	}

	//-- Específic functions for movil (Shop & Outlet)
	

	private static final String XP_SMART_BANNER = XP_HEADER + "/div[@id='smartbanner']";
	private static final String XP_LINK_CLOSE_SMART_BANNER = XP_SMART_BANNER + "//a[@class='sb-close']";	

	
	/**
	 * Si existe, cierra el banner de aviso en móvil (p.e. el que sale proponiendo la descarga de la App)
	 */
	public void closeSmartBannerIfExistsMobil() {
		if (state(VISIBLE, XP_LINK_CLOSE_SMART_BANNER).check()) {
			click(XP_LINK_CLOSE_SMART_BANNER).exec();
		}
	}

	/**
	 * Función que abre/cierra el menú lateral de móvil según le indiquemos en el parámetro 'open'
	 * @param open: 'true'  queremos que el menú lateral de móvil se abra
	 *			  'false' queremos que el menú lateral de móvil se cierre
	 */
	public void clickIconoMenuHamburguerMobil(boolean toOpenMenus) {
		var secMenus = MenusWebAll.make(channel);
		boolean menuVisible = secMenus.isMenuInState(toOpenMenus, 1);
		int i=0;
		var typeClick = WEBDRIVER;
		while ((menuVisible!=toOpenMenus) && i<5) {
			try {
				state(VISIBLE, XP_HAMBURGUER_DEVICE_ICON).wait(5).check();
				click(XP_HAMBURGUER_DEVICE_ICON).type(typeClick).exec();
				typeClick = TypeClick.next(typeClick);
				menuVisible = secMenus.isMenuInState(toOpenMenus, 2);
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Exception in click icono Hamburguer", e);
			}
			i+=1;
		}
	}
}
