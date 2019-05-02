package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusUserDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenusUserMobil;

public class SecMenusUserWrap {

	public static boolean isPresentFavoritos(Channel channel, WebDriver driver) {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentFavoritos(driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentFavoritos(driver));
		}		
	}
	
	public static void clickFavoritosAndWait(Channel channel, AppEcom appE, WebDriver driver) throws Exception {
		switch (channel) {
		case desktop:
			SecMenusUserDesktop.clickFavoritosAndWait(driver);
			break;
		case movil_web:
			SecMenusUserMobil.clickFavoritosAndWait(appE, driver);
		}
	}

	public static boolean isPresentCerrarSesion(Channel channel, WebDriver driver) {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentCerrarSesion(driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentCerrarSesion(driver));
		}		
	}
	
	public static boolean clickCerrarSessionIfLinkExists(Channel channel, WebDriver driver) throws Exception {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.clickCerrarSessionIfLinkExists(driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.clickCerrarSessionIfLinkExists(driver));
		}		
	}
	
	public static void clickCerrarSesion(Channel channel, WebDriver driver) throws Exception {
		switch (channel) {
		case desktop:
			SecMenusUserDesktop.clickCerrarSesion(driver);
		break;
		case movil_web:
			SecMenusUserMobil.clickCerrarSesion(driver);
		}
	}	
	
	public static boolean isPresentIniciarSesionUntil(Channel channel, int maxSecondsToWait, WebDriver driver) {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentIniciarSesionUntil(maxSecondsToWait, driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentIniciarSesionUntil(maxSecondsToWait, driver));
		}
	}
	
	public static void MoveAndclickIniciarSesion(Channel channel, WebDriver driver) throws Exception {
		switch (channel) {
		case desktop:
			SecMenusUserDesktop.MoveAndclickIniciarSesion(driver);
		break;
		case movil_web:
			SecMenusUserMobil.MoveAndclickIniciarSesion(driver);
		}		
	}
	
	public static boolean isPresentMiCuentaUntil(Channel channel, int maxSecondsToWait, WebDriver driver) {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentMiCuentaUntil(maxSecondsToWait, driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentMiCuentaUntil(maxSecondsToWait, driver));
		}		
	}
	
	public static void clickMiCuenta(AppEcom app, Channel channel, WebDriver driver) throws Exception {
		switch (channel) {
		case desktop:
			SecMenusUserDesktop.clickMiCuenta(driver);
		break;
		case movil_web:
			SecMenusUserMobil.clickMiCuenta(app, driver);
		}
	}
	
	public static boolean isPresentPedidos(Channel channel, WebDriver driver) {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentPedidos(driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentPedidos(driver));
		}		
	}	
	
	public static boolean isPresentMisCompras(Channel channel, WebDriver driver) {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentMisCompras(driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentMisCompras(driver));
		}		
	}	
	
	public static boolean isPresentAyuda(Channel channel, WebDriver driver) {
		switch (channel) {
		case desktop:
			return (SecMenusUserDesktop.isPresentAyuda(driver));
		case movil_web:
		default:
			return (SecMenusUserMobil.isPresentAyuda(driver));
		}		
	}	
	
	public static void clickRegistrate(Channel channel, WebDriver driver) throws Exception {
		switch (channel) {
		case desktop:
			SecMenusUserDesktop.clickRegistrate(driver);
		break;
		case movil_web:
			SecMenusUserMobil.clickRegistrate(driver);
		}
	}	
	
	public static boolean isPresentMangoLikesYou(Channel channel, WebDriver driver) {
		switch (channel) {
		case movil_web:
			return (SecMenusUserMobil.isPresentMangoLikesYou(driver));
		case desktop:
		default:
			return (SecMenusUserDesktop.isPresentMangoLikesYou(driver));
		}
	}
	
    public static void clickMangoLikesYou(Channel channel, WebDriver driver) throws Exception {
    	switch (channel) {
    	case movil_web:
    		SecMenusUserMobil.clickMangoLikesYou(driver);
	    	break;
    	case desktop:
    		SecMenusUserDesktop.clickMangoLikesYou(driver);
	        break;
    	}
    }
}
