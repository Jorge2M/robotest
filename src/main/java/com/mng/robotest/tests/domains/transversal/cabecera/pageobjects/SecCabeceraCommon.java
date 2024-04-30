package com.mng.robotest.tests.domains.transversal.cabecera.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import javax.ws.rs.NotAllowedException;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.robotest.testslegacy.pageobject.shop.menus.desktop.ModalUserSesionShopDesktop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabeceraCommon.IconoCabecera.*;

public class SecCabeceraCommon extends SecCabecera {
	
	private final ModalUserSesionShopDesktop modalUserSesionShopDesktop = new ModalUserSesionShopDesktop(); 
	
	private static final String XP_NUM_ARTICLES = "//span[@data-testid[contains(.,'numItems')] or @data-testid[contains(.,'totalItems')]]";
	
	public enum IconoCabecera implements ElementPage {
		LUPA(
			"//*[@data-testid='header.userMenu.search.button']",
			"//*[@data-testid='header.userMenu.search.button']",
			"//button[@class[contains(.,'SearchIcon')]]"),
		INICIAR_SESION(
			"//*[@data-testid='header.userMenu.login_mobile_any']",
			"//*[@data-testid='header.userMenu.login_any']",
			"//*[@data-testid[contains(.,header.userMenu.login)] and @href[contains(.,'login')]]"),
		MICUENTA(
			"//*[@data-testid='header.userMenu.login_mobile']",
			"//*[@data-testid='header.userMenu.login']",
			"//*[@data-testid[contains(.,'header.userMenu.login')] and @href[contains(.,'my-account')]]"),
		FAVORITOS(
			"//*[@data-testid='header.userMenu.favorites_mobile_any']",
			"//*[@data-testid[contains(.,'header.userMenu.favorites')]]",
			"//*[@data-testid[contains(.,'header.userMenu.favorites_any')]]"),
		BOLSA(
			"//*[@data-testid='header-user-menu-bag']",
			"//*[@data-testid[contains(.,'header.userMenu.cart.button')] or " + 
			    "@data-testid[contains(.,'header-user-menu-bag')]]",
			"//a[@href[contains(.,'/cart')]]");

		private By byDevice;
		private String xpathDevice;
		private By byDesktop;
		private String xpathDesktop;
		
		IconoCabecera(String xpathDevice, String xpathDesktop, String xpathGenesis) {
			this.xpathDevice = "( " + xpathDevice + " | " + xpathGenesis + ")";
			this.byDevice = By.xpath(this.xpathDevice);
			
			this.xpathDesktop = "( " + xpathDesktop + " | " + xpathGenesis + ")";
			this.byDesktop = By.xpath(this.xpathDesktop);
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
		return XP_NUM_ARTICLES;
	}
	
	@Override
	public void hoverIconoBolsa() {
		hoverIcono(BOLSA);
	}
	
	@Override
	public boolean isInStateIconoBolsa(State state, int seconds) {
		return (isIconoInState(BOLSA, state, seconds));
	}
	
	@Override
	public void clickIconoBolsa() {
		clickIconoAndWait(BOLSA);
	}

	@Override
	public void clickIconoBolsaWhenDisp(int seconds) {
		boolean isIconoClickable = state(CLICKABLE, BOLSA.getBy(channel)).wait(seconds).check();
		if (isIconoClickable) {
			clickIconoBolsa(); 
		}
	}

	public void clickIconoAndWait(IconoCabecera icono) {
		isInStateIconoBolsa(VISIBLE, 3); //Con los nuevos menús ahora tardan bastante en aparecer los iconos
		click(icono.getBy(channel)).type(JAVASCRIPT).exec(); //TODO
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
		isInStateIconoBolsa(VISIBLE, 5); //Con los nuevos menús ahora tardan bastante en aparecer los iconos
		waitForPageLoaded(driver);
		moveToElement(icono.getXPath(channel) + "/*"); //Workaround problema hover en Firefox
		moveToElement(icono.getBy(channel));
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
			if (isIconoInState(INICIAR_SESION, VISIBLE)) {
				hoverIcono(INICIAR_SESION); 
			} else {
				hoverIcono(MICUENTA);
			}
		}
		catch (NoSuchElementException e) {
			Log4jTM.getLogger().warn("Problem hover user icon", e);
		}
	}
}
