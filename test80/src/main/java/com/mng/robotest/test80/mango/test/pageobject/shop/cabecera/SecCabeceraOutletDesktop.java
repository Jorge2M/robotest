package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Cabecera" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class SecCabeceraOutletDesktop extends SecCabeceraOutlet {
	
	public enum LinkCabeceraOutletDesktop implements ElementPage {
		lupa("//span[@class='menu-search-icon']"),
		registrate("//a[@data-origin='register']"),
		iniciarsesion("//a[@data-origin='login']"),
		cerrarsesion("//span[@class[contains(.,'_logout')]]"),
		micuenta("//a[@href[contains(.,'/account')]]"),
		pedidos("//a[@class[contains(.,'_pedidos')]]"),
		ayuda("//a[@class[contains(.,'_pedidos')]]"),
		bolsa("//div[@class[contains(.,'shoppingCart')]]");
		
		private By by;
		private LinkCabeceraOutletDesktop(String xpath) {
			by = By.xpath(xpath);
		}
		
		@Override
		public By getBy() {
			return by;
		}
	}

	private final static String XPathNumArticles = "//span[@id[contains(.,'bolsa_articulosNum')]]";
    public final static String XPathLinkLogo = "//a[@class[contains(.,'headerMobile__logoLink')]]";
    
    private SecCabeceraOutletDesktop(Channel channel, AppEcom app, WebDriver driver) {
    	super(channel, app, driver);
    }
    
    public static SecCabeceraOutletDesktop getNew(Channel channel, AppEcom app, WebDriver driver) {
    	return (new SecCabeceraOutletDesktop(channel, app, driver));
    }
    
    @Override
    String getXPathNumberArtIcono() {
    	return XPathNumArticles;
    }
    
    @Override
    public boolean isInStateIconoBolsa(State state) {
    	return (isElementInState(LinkCabeceraOutletDesktop.bolsa, state));
    }

    @Override
    public void clickIconoBolsa() {
    	clickElement(LinkCabeceraOutletDesktop.bolsa);
    }

    
    @Override
    public void clickIconoBolsaWhenDisp(int maxSeconds) {
    	boolean isIconoClickable = state(Clickable, LinkCabeceraOutletDesktop.bolsa.getBy()).wait(maxSeconds).check();
        if (isIconoClickable) {
        	clickIconoBolsa();
        }
    }
    
    @Override
    public void hoverIconoBolsa() {
    	hoverElement(LinkCabeceraOutletDesktop.bolsa);
    }
    
    public boolean isElementInState(LinkCabeceraOutletDesktop element, State state) {
    	return (state(state, element.getBy()).check());
    }
    
    public boolean isElementInStateUntil(LinkCabeceraOutletDesktop element, State state, int maxSeconds) {
    	return (state(state, element.getBy()).wait(maxSeconds).check());
    }
    
    public void clickElement(LinkCabeceraOutletDesktop element) {
    	click(element.getBy()).exec();
    }
    
    public void hoverElement(LinkCabeceraOutletDesktop element) {
        moveToElement(element.getBy(), driver);
    }
}
