package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraOutlet_Mobil.IconoCabOutletMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera_MostFrequent.IconoCabeceraShop_DesktopMobile;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraShop_Tablet.IconoCabeceraShop_Tablet;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuUserItem.UserMenu;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.ModalUserSesionShopDesktop.MenuUserDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralDevice;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenusUserDevice.MenuUserDevice;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class MenusUserWrapper extends PageObjTM {
	
	final Channel channel;
	final AppEcom app;
	final SecCabecera secCabecera;
	final SecMenuLateralDevice secMenuLateralMobil;
	
	private MenusUserWrapper(Channel channel, AppEcom app, WebDriver driver) {
		super(driver);
		this.channel = channel;
		this.app = app;
		this.secCabecera = SecCabecera.getNew(channel, app, driver);
		this.secMenuLateralMobil = new SecMenuLateralDevice(channel, app, driver);
	}
	
	public static MenusUserWrapper getNew(Channel channel, AppEcom app, WebDriver driver) {
		return (new MenusUserWrapper(channel, app, driver));
	}
	
	public boolean isMenuInState(UserMenu menu, State state) throws Exception {
		return (isMenuInStateUntil(menu, state, 0));
	}
	
	public boolean isMenuInStateUntil(UserMenu menu, State state, int maxSeconds) {
		if (menu==UserMenu.bolsa) {
			return (secCabecera.isInStateIconoBolsa(state, maxSeconds));
		} else {
			MenuUserItem menuUserItem = new MenuUserItem(menu, channel, app);
			return (isMenuInStateUntil(menuUserItem, state, maxSeconds));
		}
	}
	
	public void clickMenuAndWait(UserMenu menu) {
		checkAppSupported(app, menu);
		if (menu==UserMenu.bolsa) {
			secCabecera.clickIconoBolsa();
		} else {
			MenuUserItem menuUserItem = new MenuUserItem(menu, channel, app);
			clickMenuAndWait(menuUserItem);
		}
	}
	
	public boolean clickMenuIfInState(UserMenu menu, State state) {
		if (isMenuInStateUntil(menu, state, 0)) {
			clickMenuAndWait(menu);
			return true;
		}
		return false;
	}
	
	public void moveToMenu(UserMenu menu) {
		if (menu==UserMenu.bolsa) {
			secCabecera.hoverIconoBolsa();
		} else {
			MenuUserItem menuUserItem = new MenuUserItem(menu, channel, app);
			moveToElement(menuUserItem.getLink().getBy(channel, app), driver);
		}
	}
	
	public void moveAndClick(UserMenu menu) {
		moveToMenu(menu);
		clickMenuAndWait(menu);
	}
	
	public void hoverIconForShowUserMenuDesktopShop() {
		if (channel!=Channel.desktop && app!=AppEcom.shop) {
			throw new IllegalArgumentException(
				"This function doesn't support the channel " + channel + " and the application " + app);
		}
		secCabecera.getShop_DesktopMobile().hoverIconForShowUserMenuDesktop();
	}
	
	private void checkAppSupported(AppEcom app, UserMenu userMenu) {
		if (!userMenu.getAppsSupported().contains(app)) {
			throw new IllegalArgumentException("The application " + app + " doesn't include the user menu " + userMenu);
		}
	}

	private boolean isMenuInStateUntil(MenuUserItem menu, State state, int maxSeconds) {
		ElementPage menuLink = menu.getLink();
		switch (menu.getType()) {
			case IconoCabeceraShop_DesktopMobile:
				return (secCabecera.getShop_DesktopMobile().isIconoInStateUntil((IconoCabeceraShop_DesktopMobile)menuLink, state, maxSeconds));
			case IconoCabeceraShop_Tablet:
				return (secCabecera.getShop_Tablet().isIconoInStateUntil((IconoCabeceraShop_Tablet)menuLink, state, maxSeconds));
			case MenuUserDesktop:
				return (secCabecera.getShop_DesktopMobile().getModalUserSesionDesktop().isMenuInStateUntil((MenuUserDesktop)menuLink, state, maxSeconds));
			case MenuUserDevice:
				return (secMenuLateralMobil.getUserMenu().isMenuInStateUntil((MenuUserDevice)menuLink, state, maxSeconds));
			case IconoCabOutletMobil:
				return (secCabecera.getOutletMobil().isElementInStateUntil((IconoCabOutletMobil)menuLink, state, maxSeconds));
			default:
				return false;
		}
	}
	
	private void clickMenuAndWait(MenuUserItem menu) {
		ElementPage menuLink = menu.getLink();
		switch (menu.getType()) {
			case IconoCabeceraShop_DesktopMobile:
				secCabecera.getShop_DesktopMobile().clickIconoAndWait((IconoCabeceraShop_DesktopMobile)menuLink);
				break;
			case IconoCabeceraShop_Tablet:
				secCabecera.getShop_Tablet().clickIconoAndWait((IconoCabeceraShop_Tablet)menuLink);
				break;
			case MenuUserDesktop:
				secCabecera.getShop_DesktopMobile().hoverIconForShowUserMenuDesktop();
				secCabecera.getShop_DesktopMobile().getModalUserSesionDesktop().wait1sForItAndclickMenu((MenuUserDesktop)menuLink);
				break;
			case MenuUserDevice:
				secMenuLateralMobil.getUserMenu().clickMenu((MenuUserDevice)menuLink);
				break;
			case IconoCabOutletMobil:
				secCabecera.getOutletMobil().click((IconoCabOutletMobil)menuLink);
				break;
		}
	}
	
	public LoyaltyData checkAndGetLoyaltyPointsUntil(int maxSeconds) throws Exception {
    	//TODO Workarround for manage shadow-dom Elements. Remove when WebDriver supports shadow-dom
		LoyaltyData loyaltyData = new LoyaltyData(false, 0);
		By byLoyaltyUserMenu = By.tagName("loyalty-user-menu");
		for (int i=0; i<maxSeconds; i++) {
			WebElement blockLoyalty = getElementVisible(driver, byLoyaltyUserMenu);
			if (blockLoyalty!=null) {
				//if (WebdrvWrapp.isElementPresent(driver, byLoyaltyUserMenu)) {
				//WebElement shadowHost = driver.findElement(byLoyaltyUserMenu);
		    	Object shadowLoyaltyPoints = ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot", blockLoyalty);
		    	//TODO pendiente el grupo de Loyalty nos proporcione un id
		    	String innerHTML;
		    	if (shadowLoyaltyPoints instanceof WebElement) {
		    		//Caso de Chrome
			    	WebElement loyaltyPoints = (WebElement)shadowLoyaltyPoints;
			    	innerHTML = loyaltyPoints.getAttribute("innerHTML");
		    	}
		    	else {
			    	//Caso de Firefox
		    		innerHTML = shadowLoyaltyPoints.toString();
		    	}
		    	int numberBlocksLoyalty = driver.findElements(By.tagName("loyalty-user-menu")).size();
		    	Log4jTM.getLogger().info("Contenido del elemento HTML loyalty-user-menu  " + innerHTML);
		    	Log4jTM.getLogger().info("Número de bloques de Loyalty " + numberBlocksLoyalty);
		        Pattern pattern = Pattern.compile("tienes (.*?) Likes");
		        Matcher matcher = pattern.matcher(innerHTML);
		    	if (matcher.find()) {
		    		loyaltyData.isPresent = true;
		    		float pointsFloat = ImporteScreen.getFloatFromImporteMangoScreen(matcher.group(1));
		    		loyaltyData.numberPoints = (int)pointsFloat;
		    		break;
		    	}
	    	}
			
	    	Thread.sleep(1000);
		}
    	
    	return loyaltyData;
	}
	
	public class LoyaltyData {
		public boolean isPresent=false;
		public int numberPoints=0;
		
		public LoyaltyData(boolean isPresent, int numberPoints) {
			this.isPresent = isPresent;
			this.numberPoints = numberPoints;
		}
	}
}
