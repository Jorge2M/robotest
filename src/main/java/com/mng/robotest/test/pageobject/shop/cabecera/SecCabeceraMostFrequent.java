package com.mng.robotest.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import javax.ws.rs.NotAllowedException;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.pageobject.shop.menus.desktop.ModalUserSesionShopDesktop;

public class SecCabeceraMostFrequent extends SecCabecera {
	
	private final ModalUserSesionShopDesktop modalUserSesionShopDesktop = new ModalUserSesionShopDesktop(); 
	
	private static final String XPATH_DIV_NAV_TOOLS = "//div[@id='navTools']";
	private static final String XPATH_NUM_ARTICLES_MOBIL_OUTLET = "//*[@class='icon-button-items']";
	private static final String XPATH_NUM_ARTICLES_MANY_LOCATIONS = "//span[@data-testid[contains(.,'numItems')] or @data-testid[contains(.,'totalItems')]]";
	
	public enum IconoCabecera implements ElementPage {
		lupa(
			"//*[@data-testid='header.userMenu.search.button']",
			//TODO eliminar el 1o cuando suba la actual versión a pro (24-enero)			
			"//*[@data-testid[contains(.,'header.userMenu.searchIconButton')] or @data-testid='header.userMenu.search.button']"),
		iniciarsesion(
			"//*[@data-testid='header.userMenu.login_mobile_any']",
			"//*[@data-testid='header.userMenu.login_any']"),
		micuenta(
			"//*[@data-testid='header.userMenu.login_mobile']",
			"//*[@data-testid='header.userMenu.login']"),
		favoritos(
			"//*[@data-testid='header.userMenu.favorites_mobile_any']",
			"//*[@data-testid[contains(.,'header.userMenu.favorites')]]"),
		bolsa(
			"//*[@data-testid='header-user-menu-bag']",
			"//*[@data-testid[contains(.,'header.userMenu.bolsa')] or " + //TODO eliminar cuando todos los países vayan por la nueva bolsa 
			    "@data-testid[contains(.,'header-user-menu-bag')]]");

		private By byDevice;
		private String xpathDevice;
		private By byDesktop;
		private String xpathDesktop;
		
		IconoCabecera(String xpathDevice, String xPathDesktop) {
			this.xpathDevice = xpathDevice;
			this.byDevice = By.xpath(xpathDevice);
			
			this.xpathDesktop = xPathDesktop;
			this.byDesktop = By.xpath(xPathDesktop);
		}

		@Override
		public By getBy(Channel channel) {
			if (channel.isDevice()) {
			    return byDevice;
			} 
		    return byDesktop;
		}

		public String getXPath(Channel channel) {
			if (channel.isDevice()) {
				return xpathDevice;
			} 
			return xpathDesktop;
		}
		
		@Override
		public By getBy() {
			throw new NotAllowedException("Method not allowed because Channel is needed");
		}
	}
	
	public ModalUserSesionShopDesktop getModalUserSesionDesktop() {
		return modalUserSesionShopDesktop;
	}

	@Override
	public String getXPathNumberArtIcono() {
		if (channel==Channel.desktop || app==AppEcom.shop) {
			return XPATH_NUM_ARTICLES_MANY_LOCATIONS;
		}
		return XPATH_NUM_ARTICLES_MOBIL_OUTLET;
	}
	
	@Override
	public void hoverIconoBolsa() {
		hoverIcono(IconoCabecera.bolsa);
	}
	
	@Override
	public boolean isInStateIconoBolsa(State state, int seconds) {
		return (isIconoInState(IconoCabecera.bolsa, state, seconds));
	}
	
	@Override
	public void clickIconoBolsa() {
		clickIconoAndWait(IconoCabecera.bolsa);
	}

	@Override
	public void clickIconoBolsaWhenDisp(int seconds) {
		boolean isIconoClickable = state(Clickable, IconoCabecera.bolsa.getBy(channel)).wait(seconds).check();
		if (isIconoClickable) {
			clickIconoBolsa(); 
		}
	}

	public void clickIconoAndWait(IconoCabecera icono) {
		isInStateIconoBolsa(State.Visible, 3); //Con los nuevos menús ahora tardan bastante en aparecer los iconos
		click(icono.getBy(channel)).type(TypeClick.javascript).exec(); //TODO
	}
	
	public boolean isIconoInState(IconoCabecera icono, State state) {
		return isIconoInState(icono, state, 0);
	}
	
	public boolean isIconoInState(IconoCabecera icono, State state, int seconds) {
		return state(state, icono.getBy(channel)).wait(seconds).check();
	}
	
	public boolean isIconoInStateUntil(IconoCabecera icono, State state, int seconds) {
		return state(state, icono.getBy(channel)).wait(seconds).check();
	}
	
	public void hoverIcono(IconoCabecera icono) {
		isInStateIconoBolsa(State.Visible, 5); //Con los nuevos menús ahora tardan bastante en aparecer los iconos
		waitForPageLoaded(driver);
		moveToElement(icono.getXPath(channel) + "/*"); //Workaround problema hover en Firefox
		moveToElement(icono.getBy(channel));
	}
	
	public void focusAwayBolsa() {
		//The moveElement doens't works properly for hide the Bolsa-Modal
		click(XPATH_DIV_NAV_TOOLS).exec();
	}
	
	public void hoverIconForShowUserMenuDesktop() {
		int i=0;
		while (!modalUserSesionShopDesktop.isVisible() && i<3) {
			hoverVisibleUserIcon();
			if (modalUserSesionShopDesktop.isVisible()) {
				break;
			}
			waitMillis(1000);
			i+=1;
		}
	}

	private void hoverVisibleUserIcon() {
		try {
			if (isIconoInState(IconoCabecera.iniciarsesion, State.Visible)) {
				hoverIcono(IconoCabecera.iniciarsesion); 
			} else {
				hoverIcono(IconoCabecera.micuenta);
			}
		}
		catch (NoSuchElementException e) {
			Log4jTM.getLogger().warn("Problem hover user icon", e);
		}
	}
}
