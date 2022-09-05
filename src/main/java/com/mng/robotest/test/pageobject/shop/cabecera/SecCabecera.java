package com.mng.robotest.test.pageobject.shop.cabecera;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.buscador.pageobjects.SecSearch;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu;
import com.mng.robotest.test.pageobject.shop.menus.mobil.SecMenuLateralDevice;

public abstract class SecCabecera extends PageBase {
	
	protected final SecSearch secSearch = SecSearch.getNew(channel, app, driver);

	private static final String XPATH_HEADER = "//header";
	private static final String XPATH_LINK_LOGO_MANGO = 
			"//a[@class='logo-link' or " + 
			"@class[contains(.,'logo_')] or" + 
			"@title[contains(.,'MANGO Shop Online')]]";

	abstract String getXPathNumberArtIcono();
	public abstract boolean isInStateIconoBolsa(State state, int maxSeconds);
	public abstract void clickIconoBolsa();
	public abstract void clickIconoBolsaWhenDisp(int maxSecondsToWait);
	public abstract void hoverIconoBolsa();
	
	public static SecCabecera getNew(Channel channel, AppEcom app) {
		if (channel==Channel.mobile && app==AppEcom.outlet) {
			return new SecCabeceraOutlet_Mobil();
		}
		
		switch (channel) {
		case tablet:
			//return SecCabeceraShop_Tablet.getNew(driver);
		case desktop:
		case mobile:
		default:
			return SecCabecera_MostFrequent.getNew(channel, app);
		}
	}
	
	public SecCabecera_MostFrequent getShop_DesktopMobile() {
		return (SecCabecera_MostFrequent)this;
	}
	
	public SecCabeceraOutlet_Mobil getOutletMobil() {
		return (SecCabeceraOutlet_Mobil)this;
	}
	
	public static void buscarTexto(String referencia, Channel channel, AppEcom app, WebDriver driver) {
		MenusUserWrapper menusUser = MenusUserWrapper.getNew(channel, app);
		menusUser.isMenuInStateUntil(UserMenu.lupa, State.Visible, 1);
		menusUser.clickMenuAndWait(UserMenu.lupa);
		SecSearch secSearch = SecSearch.getNew(channel, app, driver);
		secSearch.search(referencia);
	}
	
	public boolean clickLogoMango() {
		if (isPresentLogoMango(2)) {
			click(XPATH_LINK_LOGO_MANGO).exec();
			return true;
		}
		return false;
	}
	
	public boolean isPresentLogoMango(int maxSeconds) {
		return state(Present, XPATH_LINK_LOGO_MANGO).wait(maxSeconds).check();
	}

	public void hoverLogoMango() throws Exception {
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
			numArticulos = Integer.valueOf(numArtStr).intValue();
		}
		return numArticulos;
	}	
	
	public boolean hayArticulosBolsa() throws Exception {
		return (getNumArticulosBolsa() > 0);
	}

	public String getNumberArtIcono() throws Exception {
		String articulos = "0";
		waitForPageLoaded(driver); //Para evitar staleElement en la línea posterior
		String xpathNumberArtIcono = getXPathNumberArtIcono();
		if (state(Visible, xpathNumberArtIcono).check()) {
			articulos = getElement(xpathNumberArtIcono).getText();
		}
		return articulos;
	}

	//-- Específic functions for movil (Shop & Outlet)
	

	private static final String XPATH_SMART_BANNER = XPATH_HEADER + "/div[@id='smartbanner']";
	private static final String XPATH_LINK_CLOSE_SMART_BANNER = XPATH_SMART_BANNER + "//a[@class='sb-close']";	
	private static final String XPATH_ICONO_MENU_HAMBURGUESA = XPATH_HEADER + "//div[@class[contains(.,'menu-open-button')]]";

	
	/**
	 * Si existe, cierra el banner de aviso en móvil (p.e. el que sale proponiendo la descarga de la App)
	 */
	public void closeSmartBannerIfExistsMobil() {
		if (state(Visible, XPATH_LINK_CLOSE_SMART_BANNER).check()) {
			click(XPATH_LINK_CLOSE_SMART_BANNER).exec();
		}
	}

	/**
	 * Función que abre/cierra el menú lateral de móvil según le indiquemos en el parámetro 'open'
	 * @param open: 'true'  queremos que el menú lateral de móvil se abra
	 *			  'false' queremos que el menú lateral de móvil se cierre
	 */
	public void clickIconoMenuHamburguerMobil(boolean toOpenMenus) {
		//SecMenuLateralMobil secMenuLateral = new SecMenuLateralMobil(app, driver);
		SecMenuLateralDevice secMenuLateral = new SecMenuLateralDevice(channel, app);
		boolean menuVisible = secMenuLateral.isMenuInStateUntil(toOpenMenus, 1);
		int i=0;
		TypeClick typeClick = TypeClick.webdriver;
		while ((menuVisible!=toOpenMenus) && i<5) {
			try {
				isVisibleIconoMenuHamburguesaUntil(5);
				clickIconoMenuHamburguesaWhenReady(typeClick);
				typeClick = TypeClick.next(typeClick);
				menuVisible = secMenuLateral.isMenuInStateUntil(toOpenMenus, 2);
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Exception in click icono Hamburguer", e);
			}
			
			i+=1;
		}
	}

	public boolean isVisibleIconoMenuHamburguesaUntil(int maxSeconds) {
		return state(Visible, XPATH_ICONO_MENU_HAMBURGUESA).wait(maxSeconds).check();
	}

	public void clickIconoMenuHamburguesaWhenReady(TypeClick typeOfClick) {
		click(XPATH_ICONO_MENU_HAMBURGUESA).exec();
	}
}
