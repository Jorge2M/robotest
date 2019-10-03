package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.webdriverwrapper.ElementPage;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.ModalUserSesionShopDesktop;

/**
 * Cabecera Shop compatible con desktop y movil_web
 *
 */
public class SecCabeceraShop extends SecCabecera {
	
	private final ModalUserSesionShopDesktop modalUserSesionShopDesktop;
	
    private final static String XPathDivNavTools = "//div[@id='navTools']";
    private final static String XPathNumArticlesBolsa = "//span[@class='icon-button-items']";
	
    public enum IconoCabeceraShop implements ElementPage {
    	lupa("//span[@class[contains(.,'-search')]]/.."),
    	//lupa("//self::*[@id='search_icon_button']"),
		iniciarsesion("//self::*[@id='login_any' or @id='login_mobile_any']/span[@class[contains(.,'-account')]]/.."),
    	micuenta("//self::*[@id='login' or @id='login_mobile']/span[@class[contains(.,'-account')]]/.."),
		favoritos("//span[@class[contains(.,'-favorites')]]/.."),
		bolsa("//span[@class[contains(.,'-bag')]]/..");

		private String xPath;
		final static String XPathIcon = "//div[@class[contains(.,'user-icon-button')]]";
		IconoCabeceraShop(String xPath) {
			this.xPath = XPathIcon + xPath;
		}

		public String getXPath() {
			return this.xPath;
		}
	}
    
	protected SecCabeceraShop(Channel channel, AppEcom app, WebDriver driver) {
		super(channel, app, driver);
		this.modalUserSesionShopDesktop = ModalUserSesionShopDesktop.getNew(driver);
	}
	
	public static SecCabeceraShop getNew(Channel channel, AppEcom app, WebDriver driver) {
		return (new SecCabeceraShop(channel, app, driver));
	}
	
	public ModalUserSesionShopDesktop getModalUserSesionDesktop() {
		return modalUserSesionShopDesktop;
	}

    @Override
    String getXPathNumberArtIcono() {
    	return (XPathNumArticlesBolsa);
    }
    
    @Override
    public void hoverIconoBolsa() {
    	hoverIcono(IconoCabeceraShop.bolsa);
    }
    
    @Override
    public boolean isInStateIconoBolsa(StateElem state) {
    	return (isIconoInState(IconoCabeceraShop.bolsa, state));
    }
    
    @Override
    public void clickIconoBolsa() throws Exception {
    	clickIconoAndWait(IconoCabeceraShop.bolsa);
    }

    @Override
    public void clickIconoBolsaWhenDisp(int maxSecondsWait) throws Exception {
    	boolean isIconoClickable = isElementInStateUntil(IconoCabeceraShop.bolsa, StateElem.Clickable, maxSecondsWait, driver);
        if (isIconoClickable) {
        	clickIconoBolsa(); 
        }
    }

    public void clickIconoAndWait(IconoCabeceraShop icono) throws Exception {
    	clickAndWait(icono, driver);
    }
    
    public boolean isIconoInState(IconoCabeceraShop icono, StateElem state) {
    	return (isElementInState(icono, state, driver));
    }
    
    public boolean isIconoInStateUntil(IconoCabeceraShop icono, StateElem state, int maxSecondsWait) {
    	return (isElementInStateUntil(icono, state, maxSecondsWait, driver));
    }
    
    public void hoverIcono(IconoCabeceraShop icono) {
    	moveToElement(By.xpath(icono.getXPath() + "/*"), driver); //Workaround problema hover en Firefox
        moveToElement(icono, driver);
    }
    
    public void focusAwayBolsa(WebDriver driver) {
    	//The moveElement doens't works properly for hide the Bolsa-Modal
    	driver.findElement(By.xpath(XPathDivNavTools)).click();
    }
    
	public void hoverIconForShowUserMenuDesktop() throws Exception {
		int i=0;
		while (!modalUserSesionShopDesktop.isVisible() && i<3) {
			if (isIconoInState(IconoCabeceraShop.iniciarsesion, StateElem.Visible)) {
				hoverIcono(IconoCabeceraShop.iniciarsesion); 
			} else {
				hoverIcono(IconoCabeceraShop.micuenta);
			}
			if (modalUserSesionShopDesktop.isVisible()) {
				break;
			}
			Thread.sleep(1000);
			i+=1;
		}
	}
}
