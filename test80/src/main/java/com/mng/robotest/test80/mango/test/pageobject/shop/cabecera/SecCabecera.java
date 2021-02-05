package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.pageobject.shop.buscador.SecSearch;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuUserItem.UserMenu;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralDevice;

public abstract class SecCabecera extends PageObjTM {
	
	protected final Channel channel;
	protected final AppEcom app;
	protected final SecSearch secSearch;

	private final static String XPathLinkLogoMango = "//a[@class='logo-link' or @class[contains(.,'logo_')]]";

	abstract String getXPathNumberArtIcono();
	public abstract boolean isInStateIconoBolsa(State state, int maxSeconds);
	public abstract void clickIconoBolsa();
	public abstract void clickIconoBolsaWhenDisp(int maxSecondsToWait);
	public abstract void hoverIconoBolsa();
	
	protected SecCabecera(Channel channel, AppEcom app, WebDriver driver) {
		super(driver);
		this.channel = channel;
		this.app = app;
		this.secSearch = SecSearch.getNew(channel, app, driver);
	}
	
	public static SecCabecera getNew(Channel channel, AppEcom app, WebDriver driver) {
		switch (app) {
		case shop:
		case votf:
			return SecCabeceraShop.getNew(channel, app, driver);
		default:
			return SecCabeceraOutlet.getNew(channel, app, driver);
		}
	}
	
	public SecCabeceraShop_DesktopMobile getShop_DesktopMobile() {
		return (SecCabeceraShop_DesktopMobile)this;
	}
	
	public SecCabeceraShop_Tablet getShop_Tablet() {
		return (SecCabeceraShop_Tablet)this;
	}
	
	public SecCabeceraOutlet_Mobil getOutletMobil() {
		return (SecCabeceraOutlet_Mobil)this;
	}
	
	public static void buscarTexto(String referencia, Channel channel, AppEcom app, WebDriver driver) {
		MenusUserWrapper menusUser = MenusUserWrapper.getNew(channel, app, driver);
		menusUser.clickMenuAndWait(UserMenu.lupa);
		SecSearch secSearch = SecSearch.getNew(channel, app, driver);
		secSearch.search(referencia);
	}
	
	public boolean clickLogoMango() {
		if (state(Present, By.xpath(XPathLinkLogoMango)).wait(2).check()) {
			click(By.xpath(XPathLinkLogoMango)).exec();
			return true;
		}
		return false;
	}

	public void hoverLogoMango() throws Exception {
		if (state(Present, By.xpath(XPathLinkLogoMango)).check()) {
			moveToElement(By.xpath(XPathLinkLogoMango), driver);
		}
	}

	public boolean validaLogoMangoGoesToIdioma(IdiomaPais idioma) {
		String xpathLogoIdiom = XPathLinkLogoMango + "[@href[contains(.,'/" + idioma.getAcceso() + "')]]";
		return (state(Present, By.xpath(xpathLogoIdiom)).check());
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
		if (state(Visible, By.xpath(xpathNumberArtIcono)).check()) {
			articulos = driver.findElement(By.xpath(xpathNumberArtIcono)).getText();
		}
		return articulos;
	}

    //-- Específic functions for movil (Shop & Outlet)
    
	private final static String XPathHeader = "//header";
    private final static String XPathSmartBanner = XPathHeader + "/div[@id='smartbanner']";
    private final static String XPathLinkCloseSmartBanner = XPathSmartBanner + "//a[@class='sb-close']";    
    private final static String XPathIconoMenuHamburguesa = XPathHeader + "//div[@class[contains(.,'menu-open-button')]]";

	/**
	 * Si existe, cierra el banner de aviso en móvil (p.e. el que sale proponiendo la descarga de la App)
	 */
	public void closeSmartBannerIfExistsMobil() {
		if (state(Visible, By.xpath(XPathLinkCloseSmartBanner)).check()) {
			click(By.xpath(XPathLinkCloseSmartBanner)).exec();
		}
	}

    /**
     * Función que abre/cierra el menú lateral de móvil según le indiquemos en el parámetro 'open'
     * @param open: 'true'  queremos que el menú lateral de móvil se abra
     *              'false' queremos que el menú lateral de móvil se cierre
     */
    public void clickIconoMenuHamburguerMobil(boolean toOpenMenus) {
    	//SecMenuLateralMobil secMenuLateral = new SecMenuLateralMobil(app, driver);
    	SecMenuLateralDevice secMenuLateral = new SecMenuLateralDevice(channel, app, driver);
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
		return (state(Visible, By.xpath(XPathIconoMenuHamburguesa))
				.wait(maxSeconds).check());
	}

	public void clickIconoMenuHamburguesaWhenReady(TypeClick typeOfClick) {
		click(By.xpath(XPathIconoMenuHamburguesa)).exec();
	}
}
