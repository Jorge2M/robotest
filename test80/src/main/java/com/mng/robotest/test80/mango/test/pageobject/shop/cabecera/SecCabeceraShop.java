package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.ModalUserSesionShopDesktop;

/**
 * Cabecera Shop compatible con desktop y movil
 *
 */
public class SecCabeceraShop extends SecCabecera {
	
	private final ModalUserSesionShopDesktop modalUserSesionShopDesktop;
	
    private final static String XPathDivNavTools = "//div[@id='navTools']";
    public final static String XPathNumArticlesBolsa = "//span[@class='icon-button-items']";
	
	public enum IconoCabeceraShop implements ElementPage {
		lupa("//span[@class[contains(.,'-search')]]/.."),
		iniciarsesion("//self::*[@id='login_any' or @id='login_mobile_any']/span[@class[contains(.,'-account')]]/.."),
		micuenta("//self::*[@id='login' or @id='login_mobile']/span[@class[contains(.,'-account')]]/.."),
		favoritos("//span[@class[contains(.,'-favorites')]]/.."),
		bolsa("//span[@class[contains(.,'-bag')]]/..");

		private By by;
		private String xpath;
		final static String XPathIcon = "//div[@class[contains(.,'user-icon-button')]]";
		IconoCabeceraShop(String xPath) {
			xpath = XPathIcon + xPath;
			by = By.xpath(XPathIcon + xPath);
		}

		@Override
		public By getBy() {
			return by;
		}
		public String getXPath() {
			return xpath;
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
    public boolean isInStateIconoBolsa(State state, int maxSeconds) {
    	return (isIconoInState(IconoCabeceraShop.bolsa, state, maxSeconds));
    }
    
    @Override
    public void clickIconoBolsa() {
    	clickIconoAndWait(IconoCabeceraShop.bolsa);
    }

    @Override
    public void clickIconoBolsaWhenDisp(int maxSeconds) {
    	boolean isIconoClickable = state(Clickable, IconoCabeceraShop.bolsa.getBy()).wait(maxSeconds).check();
        if (isIconoClickable) {
        	clickIconoBolsa(); 
        }
    }

    public void clickIconoAndWait(IconoCabeceraShop icono) {
    	click(icono.getBy()).exec();
    }
    
    public boolean isIconoInState(IconoCabeceraShop icono, State state) {
    	return isIconoInState(icono, state, 0);
    }
    
    public boolean isIconoInState(IconoCabeceraShop icono, State state, int maxSeconds) {
    	return (state(state, icono.getBy()).wait(maxSeconds).check());
    }
    
    public boolean isIconoInStateUntil(IconoCabeceraShop icono, State state, int maxSeconds) {
    	return (state(state, icono.getBy()).wait(maxSeconds).check());
    }
    
    public void hoverIcono(IconoCabeceraShop icono) {
    	moveToElement(By.xpath(icono.getXPath() + "/*"), driver); //Workaround problema hover en Firefox
        moveToElement(icono.getBy(), driver);
    }
    
    public void focusAwayBolsa(WebDriver driver) {
    	//The moveElement doens't works properly for hide the Bolsa-Modal
    	driver.findElement(By.xpath(XPathDivNavTools)).click();
    }
    
	public void hoverIconForShowUserMenuDesktop() {
		int i=0;
		while (!modalUserSesionShopDesktop.isVisible() && i<3) {
			if (isIconoInState(IconoCabeceraShop.iniciarsesion, State.Visible)) {
				hoverIcono(IconoCabeceraShop.iniciarsesion); 
			} else {
				hoverIcono(IconoCabeceraShop.micuenta);
			}
			if (modalUserSesionShopDesktop.isVisible()) {
				break;
			}
			waitMillis(1000);
			i+=1;
		}
	}
}
