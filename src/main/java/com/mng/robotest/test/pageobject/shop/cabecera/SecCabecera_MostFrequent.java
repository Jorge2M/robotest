package com.mng.robotest.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import javax.ws.rs.NotAllowedException;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.NavigationBase;
import com.mng.robotest.test.pageobject.shop.menus.desktop.ModalUserSesionShopDesktop;

public class SecCabecera_MostFrequent extends SecCabecera {
	
	private final ModalUserSesionShopDesktop modalUserSesionShopDesktop = new ModalUserSesionShopDesktop(); 
	
	private static final String XPATH_DIV_NAV_TOOLS = "//div[@id='navTools']";
	private static final String XPATH_NUM_ARTICLES_MOBIL_OUTLET = "//*[@class='icon-button-items']";
	private static final String XPATH_NUM_ARTICLES_MANY_LOCATIONS = "//span[@data-testid[contains(.,'numItems')] or @data-testid[contains(.,'totalItems')]]";
	
	public enum IconoCabeceraShop_DesktopMobile implements ElementPage {
		lupa(
			"//*[@class[contains(.,'user-icon-button')]]//span[@class[contains(.,'-search')]]/..",
			"//*[@data-testid='header.userMenu.search.button']",
			"//*[@data-testid[contains(.,'header.userMenu.searchIconButton')]]"),
		iniciarsesion(
			"//*[@class[contains(.,'user-icon-button')] and @id='login_any' or @id='login_mobile_any' or @id='login_tablet_any']/span[@class[contains(.,'-account')]]/..",
			"//*[@data-testid='header.userMenu.login_mobile_any']",
			"//*[@data-testid='header.userMenu.login_any']"),
		micuenta(
			"//*[@class[contains(.,'user-icon-button')] and @id='login' or @id='login_mobile' or @id='login_tablet']/span[@class[contains(.,'-account')]]/..",
			"//*[@data-testid='header.userMenu.login_mobile']",
			"//*[@data-testid='header.userMenu.login']"),
		favoritos(
			"//*[@class[contains(.,'user-icon-button')]]//span[@class[contains(.,'-favorites')]]/..",
			"//*[@data-testid='header.userMenu.favorites_mobile_any']",
			"//*[@data-testid[contains(.,'header.userMenu.favorites')]]"),
		bolsa(
			"//*[@class[contains(.,'user-icon-button')]]//span[@class[contains(.,'-bag')]]/..",
			"//*[@data-testid='header-user-menu-bag']",
			"//*[@data-testid[contains(.,'header.userMenu.bolsa')] or " + //TODO eliminar cuando todos los países vayan por la nueva bolsa 
			    "@data-testid[contains(.,'header-user-menu-bag')]]");

		private By byMobileOutlet;
		private String xpathMobileOutlet;
		private By byMobileShop;
		private String xpathMobileShop;
		private By byDesktop;
		private String xpathDesktop;
		
		IconoCabeceraShop_DesktopMobile(String xPathMobileOutlet, String xpathMobileShop, String xPathDesktop) {
			this.xpathMobileOutlet = xPathMobileOutlet;
			this.byMobileOutlet = By.xpath(xPathMobileOutlet);
			
			this.xpathMobileShop = xpathMobileShop;
			this.byMobileShop = By.xpath(xpathMobileShop);
			
			this.xpathDesktop = xPathDesktop;
			this.byDesktop = By.xpath(xPathDesktop);
		}

		@Override
		public By getBy(Channel channel, Enum<?> app) {
			if (channel.isDevice()) {
//				if (app==AppEcom.shop && this==iniciarsesion) {
//					return By.xpath("//span[@data-testid='header.menuItem.userIcon']");
//				}
				if (app==AppEcom.outlet) { //||
				   //(new NavigationBase().isPRO() && UtilsTest.dateBeforeToday("2022-10-04"))) {
					return byMobileOutlet;
				} else {
				    return byMobileShop;
				}
			} else {
			    return byDesktop;
			}
		}

		public String getXPath(Channel channel, Enum<?> app) {
			if (channel.isDevice()) {
				if (app==AppEcom.outlet /*||
				   (new NavigationBase().isPRO() && UtilsTest.dateBeforeToday("2022-10-04"))*/) {
				    return xpathMobileOutlet;
				} else {
					return xpathMobileShop;
				}
			} else {
			    return xpathDesktop;
			}
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
		hoverIcono(IconoCabeceraShop_DesktopMobile.bolsa);
	}
	
	@Override
	public boolean isInStateIconoBolsa(State state, int seconds) {
		return (isIconoInState(IconoCabeceraShop_DesktopMobile.bolsa, state, seconds));
	}
	
	@Override
	public void clickIconoBolsa() {
		clickIconoAndWait(IconoCabeceraShop_DesktopMobile.bolsa);
	}

	@Override
	public void clickIconoBolsaWhenDisp(int seconds) {
		boolean isIconoClickable = state(Clickable, IconoCabeceraShop_DesktopMobile.bolsa.getBy(channel, app)).wait(seconds).check();
		if (isIconoClickable) {
			clickIconoBolsa(); 
		}
	}

	public void clickIconoAndWait(IconoCabeceraShop_DesktopMobile icono) {
		isInStateIconoBolsa(State.Visible, 3); //Con los nuevos menús ahora tardan bastante en aparecer los iconos
		click(icono.getBy(channel, app)).type(TypeClick.javascript).exec(); //TODO
	}
	
	public boolean isIconoInState(IconoCabeceraShop_DesktopMobile icono, State state) {
		return isIconoInState(icono, state, 0);
	}
	
	public boolean isIconoInState(IconoCabeceraShop_DesktopMobile icono, State state, int seconds) {
		return state(state, icono.getBy(channel, app)).wait(seconds).check();
	}
	
	public boolean isIconoInStateUntil(IconoCabeceraShop_DesktopMobile icono, State state, int seconds) {
		return state(state, icono.getBy(channel, app)).wait(seconds).check();
	}
	
	public void hoverIcono(IconoCabeceraShop_DesktopMobile icono) {
		isInStateIconoBolsa(State.Visible, 5); //Con los nuevos menús ahora tardan bastante en aparecer los iconos
		moveToElement(icono.getXPath(channel, app) + "/*"); //Workaround problema hover en Firefox
		moveToElement(icono.getBy(channel, app));
	}
	
	public void focusAwayBolsa() {
		//The moveElement doens't works properly for hide the Bolsa-Modal
		click(XPATH_DIV_NAV_TOOLS).exec();
	}
	
	public void hoverIconForShowUserMenuDesktop() {
		int i=0;
		while (!modalUserSesionShopDesktop.isVisible() && i<3) {
			if (isIconoInState(IconoCabeceraShop_DesktopMobile.iniciarsesion, State.Visible)) {
				hoverIcono(IconoCabeceraShop_DesktopMobile.iniciarsesion); 
			} else {
				hoverIcono(IconoCabeceraShop_DesktopMobile.micuenta);
			}
			if (modalUserSesionShopDesktop.isVisible()) {
				break;
			}
			waitMillis(1000);
			i+=1;
		}
	}
}
