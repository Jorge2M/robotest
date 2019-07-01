package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraShop.IconoShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusUserDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenusUserMobil;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;
import static com.mng.robotest.test80.mango.conftestmaker.AppEcom.shop;
import static com.mng.robotest.test80.mango.conftestmaker.AppEcom.outlet;
import static com.mng.robotest.test80.mango.conftestmaker.AppEcom.votf;


public class MenusUserWrapper {

	final Channel channel;
	final AppEcom app;
	final WebDriver driver;
	final SecCabecera secCabecera;
	
	public enum UserMenu {
		iniciarSesion(Arrays.asList(shop, outlet)),
		cerrarSesion(Arrays.asList(shop, outlet)),
		registrate(Arrays.asList(shop, outlet)),
		miCuenta(Arrays.asList(shop, outlet)),
		favoritos(Arrays.asList(shop, votf)),
		bolsa(Arrays.asList(shop, outlet)),
		misCompras(Arrays.asList(shop)),
		pedidos(Arrays.asList(outlet)),
		mangoLikesYou(Arrays.asList(shop)),
		ayuda(Arrays.asList(shop, outlet, votf));

		List<AppEcom> apps;
		private UserMenu(List<AppEcom> apps) {
			this.apps = apps;
		}
		
		public List<AppEcom> getAppsSupported() {
			return this.apps;
		}
	}
	
	private MenusUserWrapper(Channel channel, AppEcom app, WebDriver driver) {
		this.channel = channel;
		this.app = app;
		this.driver = driver;
		this.secCabecera = SecCabecera.getNew(channel, app, driver);
	}
	
	public static MenusUserWrapper getNew(Channel channel, AppEcom app, WebDriver driver) {
		return (new MenusUserWrapper(channel, app, driver));
	}
	
	private void checkAppSupported(AppEcom app, UserMenu userMenu) {
		if (!userMenu.getAppsSupported().contains(app)) {
			throw new IllegalArgumentException("The application " + app + " doesn't include the user menu " + userMenu);
		}
	}
	
	public boolean isMenuInStateUntil(UserMenu menu, StateElem state, int maxSecondsWait) {
		checkAppSupported(app, menu);
		
		switch (menu) {
		case iniciarSesion:
		case cerrarSesion:
		case registrate:
		case miCuenta:
		case favoritos:
			return (secCabecera.getShop().isIconoInState(IconoShop.favoritos, state));
		case bolsa:
		case misCompras:
		case pedidos:
		case mangoLikesYou:
		case ayuda:
			
		}
		
		return false;
	}
	
	public void clickMenuAndWait(UserMenu menu) throws Exception {
		checkAppSupported(app, menu);
		
		switch (menu) {
		case iniciarSesion:
		case cerrarSesion:
		case registrate:
		case miCuenta:
		case favoritos:
			secCabecera.getShop().clickIconoAndWait(IconoShop.favoritos);
		case bolsa:
		case misCompras:
		case pedidos:
		case mangoLikesYou:
		case ayuda:
			
		}
	}
	
	public boolean isPresentIniciarSesionUntil(int maxSecondsToWait) {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentIniciarSesionUntil(maxSecondsToWait, driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentIniciarSesionUntil(maxSecondsToWait, driver));
		}
	}
	
	public void MoveAndclickIniciarSesion() throws Exception {
		switch (channel) {
		case desktop:
			SecMenusUserDesktop.MoveAndclickIniciarSesion(driver);
		break;
		case movil_web:
			SecMenusUserMobil.MoveAndclickIniciarSesion(driver);
		}		
	}


	public boolean isPresentCerrarSesion() {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentCerrarSesion(driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentCerrarSesion(driver));
		}		
	}
	
	public boolean clickCerrarSessionIfLinkExists() throws Exception {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.clickCerrarSessionIfLinkExists(driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.clickCerrarSessionIfLinkExists(driver));
		}		
	}
	
	public void clickCerrarSesion() throws Exception {
		switch (channel) {
		case desktop:
			SecMenusUserDesktop.clickCerrarSesion(driver);
		break;
		case movil_web:
			SecMenusUserMobil.clickCerrarSesion(driver);
		}
	}	

	
	public boolean isPresentMiCuentaUntil(int maxSecondsToWait) {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentMiCuentaUntil(maxSecondsToWait, driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentMiCuentaUntil(maxSecondsToWait, driver));
		}		
	}
	
	public void clickMiCuenta() throws Exception {
		switch (channel) {
		case desktop:
			SecMenusUserDesktop.clickMiCuenta(driver);
		break;
		case movil_web:
			SecMenusUserMobil.clickMiCuenta(app, driver);
		}
	}
	
	public boolean isPresentPedidos() {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentPedidos(driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentPedidos(driver));
		}		
	}	
	
	public boolean isPresentMisCompras() {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentMisCompras(driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentMisCompras(driver));
		}		
	}	
	
	public boolean isPresentAyuda() {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentAyuda(driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentAyuda(driver));
		}		
	}	
	
	public void clickRegistrate() throws Exception {
		switch (channel) {
		case desktop:
			SecMenusUserDesktop.clickRegistrate(driver);
		break;
		case movil_web:
			SecMenusUserMobil.clickRegistrate(driver);
		}
	}	
	
	public boolean isPresentMangoLikesYou() {
		switch (channel) {
		case movil_web:
			return (SecMenusUserMobil.isPresentMangoLikesYou(driver));
		case desktop:
		default:
			return (SecMenusUserDesktop.isPresentMangoLikesYou(driver));
		}
	}

    public void clickMangoLikesYou() throws Exception {
    	switch (channel) {
    	case movil_web:
    		SecMenusUserMobil.clickMangoLikesYou(driver);
	    	break;
    	case desktop:
    		SecMenusUserDesktop.clickMangoLikesYou(driver);
	        break;
    	}
    }
    
	public LoyaltyData checkAndGetLoyaltyPointsUntil(int maxSecondsWait) throws Exception {
    	//TODO Workarround for manage shadow-dom Elements. Remove when WebDriver supports shadow-dom
		LoyaltyData loyaltyData = new LoyaltyData(false, 0);
		By byLoyaltyUserMenu = By.tagName("loyalty-user-menu");
		for (int i=0; i<maxSecondsWait; i++) {
			if (WebdrvWrapp.isElementVisible(driver, byLoyaltyUserMenu)) {
		    	WebElement shadowHost = driver.findElement(byLoyaltyUserMenu);
		    	if (shadowHost!=null) {
			    	Object shadowLoyaltyPoints = ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot", shadowHost);
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
			    	
			        Pattern pattern = Pattern.compile("tienes (.*?) Likes");
			        Matcher matcher = pattern.matcher(innerHTML);
			    	if (matcher.find()) {
			    		loyaltyData.isPresent = true;
			    		float pointsFloat = ImporteScreen.getFloatFromImporteMangoScreen(matcher.group(1));
			    		loyaltyData.numberPoints = (int)pointsFloat;
			    		break;
			    	}
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
