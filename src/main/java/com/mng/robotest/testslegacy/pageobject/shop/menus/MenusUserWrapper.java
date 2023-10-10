package com.mng.robotest.testslegacy.pageobject.shop.menus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabeceraMostFrequent;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabeceraMostFrequent.IconoCabecera;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu;
import com.mng.robotest.testslegacy.pageobject.shop.menus.desktop.ModalUserSesionShopDesktop.MenuUserDesktop;
import com.mng.robotest.testslegacy.pageobject.shop.menus.device.SecMenusUserDevice;
import com.mng.robotest.testslegacy.pageobject.shop.menus.device.SecMenusUserDevice.MenuUserDevice;
import com.mng.robotest.testslegacy.utils.ImporteScreen;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class MenusUserWrapper extends PageBase {
	
	private final SecCabecera secCabecera = new SecCabeceraMostFrequent();
	
	public boolean isMenuInState(UserMenu menu, State state) {
		return (isMenuInStateUntil(menu, state, 0));
	}
	
	public boolean isMenuInStateUntil(UserMenu menu, State state, int seconds) {
		if (menu==UserMenu.BOLSA) {
			return (secCabecera.isInStateIconoBolsa(state, seconds));
		} else {
			var menuUserItem = new MenuUserItem(menu, channel, app);
			return (isMenuInStateUntil(menuUserItem, state, seconds));
		}
	}
	
	public void clickMenuAndWait(UserMenu menu) {
		checkAppSupported(app, menu);
		if (menu==UserMenu.BOLSA) {
			secCabecera.clickIconoBolsa();
		} else {
			var menuUserItem = new MenuUserItem(menu, channel, app);
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
		if (menu==UserMenu.BOLSA) {
			secCabecera.hoverIconoBolsa();
		} else {
			isMenuInStateUntil(menu, State.Visible, 2);
			var menuUserItem = new MenuUserItem(menu, channel, app);
			moveToElement(menuUserItem.getLink().getBy(channel));
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

	private boolean isMenuInStateUntil(MenuUserItem menu, State state, int seconds) {
		ElementPage menuLink = menu.getLink();
		switch (menu.getType()) {
			case ICONO_CABECERA_SHOP_DESKTOP_MOBILE:
				return (secCabecera.getShop_DesktopMobile().isIconoInStateUntil((IconoCabecera)menuLink, state, seconds));
			case MENU_USER_DESKTOP:
				return (secCabecera.getShop_DesktopMobile().getModalUserSesionDesktop().isMenuInStateUntil((MenuUserDesktop)menuLink, state, seconds));
			case MENU_USER_DEVICE:
				return (new SecMenusUserDevice().isMenuInStateUntil((MenuUserDevice)menuLink, state, seconds));
			default:
				return false;
		}
	}
	
	private void clickMenuAndWait(MenuUserItem menu) {
		ElementPage menuLink = menu.getLink();
		switch (menu.getType()) {
			case ICONO_CABECERA_SHOP_DESKTOP_MOBILE:
				secCabecera.getShop_DesktopMobile().clickIconoAndWait((IconoCabecera)menuLink);
				break;
			case MENU_USER_DESKTOP:
				secCabecera.getShop_DesktopMobile().hoverIconForShowUserMenuDesktop();
				secCabecera.getShop_DesktopMobile().getModalUserSesionDesktop().wait1sForItAndclickMenu((MenuUserDesktop)menuLink);
				break;
			case MENU_USER_DEVICE:
				new SecMenusUserDevice().clickMenu((MenuUserDevice)menuLink);
				break;
		}
	}
	
	public LoyaltyData checkAndGetLoyaltyPointsUntil(int seconds) {
		//TODO Workarround for manage shadow-dom Elements. Remove when WebDriver supports shadow-dom
		var loyaltyData = new LoyaltyData(false, 0);
		By byLoyaltyUserMenu = By.tagName("loyalty-user-menu");
		for (int i=0; i<seconds; i++) {
			var blockLoyalty = getElementVisible(driver, byLoyaltyUserMenu);
			if (blockLoyalty!=null) {
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
				Log4jTM.getLogger().info(() -> "Contenido del elemento HTML loyalty-user-menu  " + innerHTML);
				Log4jTM.getLogger().info(() -> "Número de bloques de Loyalty " + numberBlocksLoyalty);
				Pattern pattern = Pattern.compile("tienes (.*?) Likes");
				Matcher matcher = pattern.matcher(innerHTML);
				if (matcher.find()) {
					loyaltyData.isPresent = true;
					float pointsFloat = ImporteScreen.getFloatFromImporteMangoScreen(matcher.group(1));
					loyaltyData.numberPoints = (int)pointsFloat;
					break;
				}
			}
			waitMillis(1000);
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