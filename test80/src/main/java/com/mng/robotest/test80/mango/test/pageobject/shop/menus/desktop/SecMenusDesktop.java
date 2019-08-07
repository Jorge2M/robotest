package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusFiltroDiscount;


public class SecMenusDesktop {
	
	public final SecBloquesMenuDesktop secBloquesMenu;
	public final SecMenuSuperiorDesktop secMenuSuperior;
    public final ModalUserSesionShopDesktop secMenusUser;
    public final SecMenuLateralDesktop secMenuLateral;
    //public final SecMenusFiltroCollection secMenusFitroCollection;
    public final SecMenusFiltroDiscount secMenusFiltroDiscount;
	
	private SecMenusDesktop(AppEcom app, WebDriver driver) {
		secBloquesMenu = SecBloquesMenuDesktop.getNew(app, driver);
		secMenuSuperior = SecMenuSuperiorDesktop.getNew(app, driver);
	    secMenusUser = ModalUserSesionShopDesktop.getNew(driver);
	    secMenuLateral = SecMenuLateralDesktop.getNew(driver);
	    secMenusFiltroDiscount = SecMenusFiltroDiscount.getNew(driver);
	}
	
	public static SecMenusDesktop getNew(AppEcom app, WebDriver driver) {
		return (new SecMenusDesktop(app, driver));
	}
}
