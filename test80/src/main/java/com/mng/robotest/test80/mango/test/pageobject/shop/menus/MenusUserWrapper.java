package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

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
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraOutletMobil.IconoCabOutletMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraShop.IconoCabeceraShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.ModalUserSesionShopDesktop.MenuUserDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralDevice;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenusUserDevice.MenuUserDevice;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;
import static com.mng.robotest.test80.mango.conftestmaker.AppEcom.shop;
import static com.mng.robotest.test80.mango.conftestmaker.AppEcom.outlet;
import static com.mng.robotest.test80.mango.conftestmaker.AppEcom.votf;

public class MenusUserWrapper extends PageObjTM {
	
	final Channel channel;
	final AppEcom app;
	final SecCabecera secCabecera;
	final SecMenuLateralDevice secMenuLateralMobil;
	
	public enum UserMenu {
		lupa(Arrays.asList(shop, outlet, votf)),
		iniciarSesion(Arrays.asList(shop, outlet)),
		cerrarSesion(Arrays.asList(shop, outlet)),
		registrate(Arrays.asList(shop, outlet)),
		miCuenta(Arrays.asList(shop, outlet)),
		favoritos(Arrays.asList(shop, votf)),
		bolsa(Arrays.asList(shop, outlet)),
		misCompras(Arrays.asList(shop)),
		pedidos(Arrays.asList(outlet)),
		mangoLikesYou(Arrays.asList(shop)),
		ayuda(Arrays.asList(shop, outlet, votf)),
		cambioPais(Arrays.asList(outlet));

		List<AppEcom> apps;
		private UserMenu(List<AppEcom> apps) {
			this.apps = apps;
		}
		
		public List<AppEcom> getAppsSupported() {
			return this.apps;
		}
	}
	
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
			ElementPage menuElement = getMenu(menu);
			return (isMenuInStateUntil(menuElement, state, maxSeconds));
		}
	}
	
	public void clickMenuAndWait(UserMenu menu) {
		checkAppSupported(app, menu);
		if (menu==UserMenu.bolsa) {
			secCabecera.clickIconoBolsa();
		} else {
			ElementPage menuElement = getMenu(menu);
			clickMenuAndWait(menuElement);
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
			ElementPage menuElement = getMenu(menu);
			moveToElement(menuElement.getBy(channel, app), driver);
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
		secCabecera.getShop().hoverIconForShowUserMenuDesktop();
	}
	
	private void checkAppSupported(AppEcom app, UserMenu userMenu) {
		if (!userMenu.getAppsSupported().contains(app)) {
			throw new IllegalArgumentException("The application " + app + " doesn't include the user menu " + userMenu);
		}
	}

	private ElementPage getMenu(UserMenu menu) {
		switch (menu) {
			case lupa:
				return getMenuLupa();
			case iniciarSesion:
				return getMenuIniciarSesion();
			case cerrarSesion:
				return getMenuCerrarSesion();
			case registrate:
				return getMenuRegistrate();
			case miCuenta:
				return getMenuMiCuenta();
			case favoritos:
				return getMenuFavoritos();
			case misCompras:
				return getMenuMisCompras();
			case pedidos:
				return getMenuPedidos();
			case mangoLikesYou:
				return getMenuMangoLikesYou();
			case ayuda:
				return getMenuAyuda();
			case cambioPais:
				return getMenuCambioPais();
			default:
				return null;
		}
	}

	private boolean isMenuInStateUntil(ElementPage menu, State state, int maxSeconds) {
		if (menu instanceof IconoCabeceraShop) {
			return (secCabecera.getShop().isIconoInStateUntil((IconoCabeceraShop)menu, state, maxSeconds));
		}
		if (menu instanceof MenuUserDesktop) {
			return (secCabecera.getShop().getModalUserSesionDesktop().isMenuInStateUntil((MenuUserDesktop)menu, state, maxSeconds));
		}
		if (menu instanceof MenuUserDevice) {
			return (secMenuLateralMobil.getUserMenu().isMenuInStateUntil((MenuUserDevice)menu, state, maxSeconds));
		}
		if (menu instanceof IconoCabOutletMobil) {
			return (secCabecera.getOutletMobil().isElementInStateUntil((IconoCabOutletMobil)menu, state, maxSeconds));
		}
		return false;
	}
	
	private void clickMenuAndWait(ElementPage menu) {
		if (menu instanceof IconoCabeceraShop) {
			secCabecera.getShop().clickIconoAndWait((IconoCabeceraShop)menu);
		}
		if (menu instanceof MenuUserDesktop) {
			secCabecera.getShop().hoverIconForShowUserMenuDesktop();
			secCabecera.getShop().getModalUserSesionDesktop().wait1sForItAndclickMenu((MenuUserDesktop)menu);
		}
		if (menu instanceof MenuUserDevice) {
			secMenuLateralMobil.getUserMenu().clickMenu((MenuUserDevice)menu);
		}
		if (menu instanceof IconoCabOutletMobil) {
			secCabecera.getOutletMobil().click((IconoCabOutletMobil)menu);
		}
	}
	
	private ElementPage getMenuLupa() {
		if (app==AppEcom.shop || app==AppEcom.votf) {
			return IconoCabeceraShop.lupa;
		}
		if (app==AppEcom.outlet) {
			if (channel==Channel.desktop) {
				return IconoCabeceraShop.lupa;
			}
			if (channel.isDevice()) {
				return IconoCabOutletMobil.lupa;
			}
		}
		return null;
	}
	
	private ElementPage getMenuIniciarSesion() {
		if (app==AppEcom.outlet || channel==Channel.tablet) {
			if (channel==Channel.desktop) {
				return IconoCabeceraShop.iniciarsesion;
			}
			if (channel.isDevice()) {
				return MenuUserDevice.iniciarsesion;
			}
		}
		if (app==AppEcom.shop || app==AppEcom.votf) {
			return IconoCabeceraShop.iniciarsesion;
		}
		return null;
	}
	
	private ElementPage getMenuCerrarSesion() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.cerrarSesion;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.cerrarsesion;
		}
		return null;
	}
	
	private ElementPage getMenuRegistrate() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.registrate;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.registrate;
		}
		return null;
	}
	
	private ElementPage getMenuMiCuenta() {
		if (app==AppEcom.outlet || channel==Channel.tablet) {
			if (channel==Channel.desktop) {
				return IconoCabeceraShop.micuenta;
			}
			if (channel.isDevice()) {
				return MenuUserDevice.micuenta;
			}
		}
		if (app==AppEcom.shop || app==AppEcom.votf) {
			return IconoCabeceraShop.micuenta;
		}
		return null;
	}
	
	private ElementPage getMenuFavoritos() {
		if (channel==Channel.tablet) {
			return MenuUserDevice.favoritos;
		}
		if (app==AppEcom.shop || app==AppEcom.votf) {
			return IconoCabeceraShop.favoritos;
		}
		return null;
	}
	
	private ElementPage getMenuMisCompras() {
		if (app==AppEcom.shop || app==AppEcom.votf) {
			if (channel==Channel.desktop) {
				return MenuUserDesktop.misCompras;
			}
			if (channel.isDevice()) {
				return MenuUserDevice.miscompras;
			}
		}
		return null;
	}
	
	private ElementPage getMenuPedidos() {
		if (app==AppEcom.outlet && channel.isDevice()) {
			return MenuUserDevice.pedidos;
		}
		return null;
	}
	
	private ElementPage getMenuMangoLikesYou() {
		if (app==AppEcom.shop || app==AppEcom.votf) {
			if (channel==Channel.desktop) {
				return MenuUserDesktop.mangoLikesYou;
			}
			if (channel.isDevice()) {
				return MenuUserDevice.mangolikesyou;
			}
		}
		return null;
	}
	
	private ElementPage getMenuAyuda() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.ayuda;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.ayuda;
		}
		return null;
	}
	
	private ElementPage getMenuCambioPais() {
		if (channel.isDevice()) {
			return MenuUserDevice.cambiopais;
		}
		return null;
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
