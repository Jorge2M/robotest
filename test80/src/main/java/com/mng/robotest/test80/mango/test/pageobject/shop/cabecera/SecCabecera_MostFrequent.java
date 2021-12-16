package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import javax.ws.rs.NotAllowedException;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.ModalUserSesionShopDesktop;

/**
 * Cabecera Shop compatible con desktop y movil
 *
 */
public class SecCabecera_MostFrequent extends SecCabecera {
	
	private final ModalUserSesionShopDesktop modalUserSesionShopDesktop;
	
	private final static String XPathDivNavTools = "//div[@id='navTools']";
	private final static String XPathNumArticlesBolsaMobile = "//span[@class='icon-button-items']";
	
	//TODO React. 16-diciembre-2021: Solicitado a David Masa via teams
	private final static String XPathNumArticlesBolsaDesktop = "//span[@class[contains(.,'UxJ2P')]]";
	
	public enum IconoCabeceraShop_DesktopMobile implements ElementPage {
		lupa(
			"//span[@class[contains(.,'-search')]]/..",
			"//self::*[@data-testid[contains(.,'header-user-menu-searchIconButton')]]"),
		iniciarsesion(
			"//self::*[@id='login_any' or @id='login_mobile_any']/span[@class[contains(.,'-account')]]/..",
			"//self::*[@data-testid='header-user-menu-login_any']"),
		micuenta(
			"//self::*[@id='login' or @id='login_mobile']/span[@class[contains(.,'-account')]]/..",
			"//self::*[@data-testid='header-user-menu-login']"),
		favoritos(
			"//span[@class[contains(.,'-favorites')]]/..",
			"//self::*[@data-testid[contains(.,'header-user-menu-favorites')]]"),
		bolsa(
			"//span[@class[contains(.,'-bag')]]/..",
			"//self::*[@data-testid[contains(.,'header-user-menu-bolsa')]]");

		private By byMobile;
		private String xpathMobile;
		private By byDesktop;
		private String xpathDesktop;
		
		final static String XPathIconMobile = "//div[@class[contains(.,'user-icon-button')]]";
		IconoCabeceraShop_DesktopMobile(String xPathMobile, String xPathDesktop) {
			xpathMobile = XPathIconMobile + xPathMobile;
			byMobile = By.xpath(XPathIconMobile + xPathMobile);
			
			this.xpathDesktop = xPathDesktop;
			byDesktop = By.xpath(xPathDesktop);
		}

		@Override
		public By getBy(Channel channel) {
			if (channel.isDevice()) {
				return byMobile;
			}
			return byDesktop;
		}
		@Override
		public By getBy(Channel channel, Enum<?> app) {
			return getBy(channel);
		}
		public String getXPath(Channel channel) {
			if (channel.isDevice()) {
				return xpathMobile;
			}
			return xpathDesktop;
		}
		
		@Override
		public By getBy() {
			throw new NotAllowedException("Method not allowed because Channel is needed");
		}
	}
	
	protected SecCabecera_MostFrequent(Channel channel, WebDriver driver) {
		super(channel, AppEcom.shop, driver);
		this.modalUserSesionShopDesktop = ModalUserSesionShopDesktop.getNew(driver);
	}
	
	public static SecCabecera_MostFrequent getNew(Channel channel, WebDriver driver) {
		return (new SecCabecera_MostFrequent(channel, driver));
	}
	
	public ModalUserSesionShopDesktop getModalUserSesionDesktop() {
		return modalUserSesionShopDesktop;
	}

	@Override
	public String getXPathNumberArtIcono() {
		return getXPathNumberArtIcono(channel);
	}
	
	public static String getXPathNumberArtIcono(Channel channel) {
		if (channel==Channel.mobile) {
			return (XPathNumArticlesBolsaMobile);
		}
		return XPathNumArticlesBolsaDesktop;
	}
	
	@Override
	public void hoverIconoBolsa() {
		hoverIcono(IconoCabeceraShop_DesktopMobile.bolsa);
	}
	
	@Override
	public boolean isInStateIconoBolsa(State state, int maxSeconds) {
		return (isIconoInState(IconoCabeceraShop_DesktopMobile.bolsa, state, maxSeconds));
	}
	
	@Override
	public void clickIconoBolsa() {
		clickIconoAndWait(IconoCabeceraShop_DesktopMobile.bolsa);
	}

	@Override
	public void clickIconoBolsaWhenDisp(int maxSeconds) {
		boolean isIconoClickable = state(Clickable, IconoCabeceraShop_DesktopMobile.bolsa.getBy(channel)).wait(maxSeconds).check();
		if (isIconoClickable) {
			clickIconoBolsa(); 
		}
	}

	public void clickIconoAndWait(IconoCabeceraShop_DesktopMobile icono) {
		isInStateIconoBolsa(State.Visible, 3); //Con los nuevos menús ahora tardan bastante en aparecer los iconos
		click(icono.getBy(channel)).type(TypeClick.javascript).exec(); //TODO
	}
	
	public boolean isIconoInState(IconoCabeceraShop_DesktopMobile icono, State state) {
		return isIconoInState(icono, state, 0);
	}
	
	public boolean isIconoInState(IconoCabeceraShop_DesktopMobile icono, State state, int maxSeconds) {
		return (state(state, icono.getBy(channel)).wait(maxSeconds).check());
	}
	
	public boolean isIconoInStateUntil(IconoCabeceraShop_DesktopMobile icono, State state, int maxSeconds) {
		return (state(state, icono.getBy(channel)).wait(maxSeconds).check());
	}
	
	public void hoverIcono(IconoCabeceraShop_DesktopMobile icono) {
		isInStateIconoBolsa(State.Visible, 3); //Con los nuevos menús ahora tardan bastante en aparecer los iconos
		moveToElement(By.xpath(icono.getXPath(channel) + "/*"), driver); //Workaround problema hover en Firefox
		moveToElement(icono.getBy(channel), driver);
	}
	
	public void focusAwayBolsa(WebDriver driver) {
		//The moveElement doens't works properly for hide the Bolsa-Modal
		driver.findElement(By.xpath(XPathDivNavTools)).click();
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
