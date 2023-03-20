package com.mng.robotest.test.pageobject.shop.cabecera;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.buscador.pageobjects.SecSearch;
import com.mng.robotest.domains.transversal.menus.pageobjects.MenusWebAll;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu;

public abstract class SecCabecera extends PageBase {
	
	protected final SecSearch secSearch = SecSearch.getNew(channel);

	private static final String XPATH_HEADER = "//header";
	private static final String XPATH_LINK_LOGO_MANGO = 
			"//a[@class='logo-link' or " + 
			"@class[contains(.,'logo_')] or" + 
			"@title[contains(.,'MANGO Shop Online')]]";

	abstract String getXPathNumberArtIcono();
	public abstract boolean isInStateIconoBolsa(State state, int seconds);
	public abstract void clickIconoBolsa();
	public abstract void clickIconoBolsaWhenDisp(int seconds);
	public abstract void hoverIconoBolsa();
	
	public SecCabeceraMostFrequent getShop_DesktopMobile() {
		return (SecCabeceraMostFrequent)this;
	}
	
	public void bring(BringTo bringTo) {
		bringElement(getElement(XPATH_HEADER), bringTo);
	}
	
	public static void buscarTexto(String referencia, Channel channel) {
		MenusUserWrapper menusUser = new MenusUserWrapper();
		menusUser.isMenuInStateUntil(UserMenu.LUPA, State.Visible, 1);
		menusUser.clickMenuAndWait(UserMenu.LUPA);
		SecSearch secSearch = SecSearch.getNew(channel);
		secSearch.search(referencia);
	}
	
	public boolean clickLogoMango() {
		if (isPresentLogoMango(2)) {
			click(XPATH_LINK_LOGO_MANGO).exec();
			return true;
		}
		return false;
	}
	
	public boolean isPresentLogoMango(int seconds) {
		return state(Present, XPATH_LINK_LOGO_MANGO).wait(seconds).check();
	}

	public void hoverLogoMango() {
		if (state(Present, XPATH_LINK_LOGO_MANGO).check()) {
			moveToElement(XPATH_LINK_LOGO_MANGO);
		}
	}

	public boolean validaLogoMangoGoesToIdioma(IdiomaPais idioma) {
		String xpathLogoIdiom = XPATH_LINK_LOGO_MANGO + "[@href[contains(.,'/" + idioma.getAcceso() + "')]]";
		return state(Present, xpathLogoIdiom).check();
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
		if (state(Visible, xpathNumberArtIcono).check()) {
			return getElement(xpathNumberArtIcono).getText();
		}
		return "0";
	}

	//-- Específic functions for movil (Shop & Outlet)
	

	private static final String XPATH_SMART_BANNER = XPATH_HEADER + "/div[@id='smartbanner']";
	private static final String XPATH_LINK_CLOSE_SMART_BANNER = XPATH_SMART_BANNER + "//a[@class='sb-close']";	
//	private static final String XPATH_ICONO_MENU_HAMBURGUESA = XPATH_HEADER + "//div[@class[contains(.,'menu-open-button')]]";

	
	/**
	 * Si existe, cierra el banner de aviso en móvil (p.e. el que sale proponiendo la descarga de la App)
	 */
	public void closeSmartBannerIfExistsMobil() {
		if (state(Visible, XPATH_LINK_CLOSE_SMART_BANNER).check()) {
			click(XPATH_LINK_CLOSE_SMART_BANNER).exec();
		}
	}

	private static final String XPATH_HAMBURGUESA_ICON = "//*[@data-testid='header.burger']";
	
	/**
	 * Función que abre/cierra el menú lateral de móvil según le indiquemos en el parámetro 'open'
	 * @param open: 'true'  queremos que el menú lateral de móvil se abra
	 *			  'false' queremos que el menú lateral de móvil se cierre
	 */
	public void clickIconoMenuHamburguerMobil(boolean toOpenMenus) {
		MenusWebAll secMenus = MenusWebAll.make(channel);
		boolean menuVisible = secMenus.isMenuInState(toOpenMenus, 1);
		int i=0;
		TypeClick typeClick = TypeClick.webdriver;
		while ((menuVisible!=toOpenMenus) && i<5) {
			try {
				state(Visible, XPATH_HAMBURGUESA_ICON).wait(5).check();
				click(XPATH_HAMBURGUESA_ICON).type(typeClick).exec();
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
