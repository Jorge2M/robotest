package com.mng.robotest.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusFiltroDiscount;


public class SecMenusDesktop extends PageObjTM {
	
	public final SecBloquesMenuDesktop secBloquesMenu;
	public final SecMenuSuperiorDesktop secMenuSuperior;
	public final ModalUserSesionShopDesktop secMenusUser;
	public final SecMenuLateralDesktop secMenuLateral;
	//public final SecMenusFiltroCollection secMenusFitroCollection;
	public final SecMenusFiltroDiscount secMenusFiltroDiscount;
	
	private SecMenusDesktop(AppEcom app, WebDriver driver) {
		super(driver);
		//TODO normalizar
		secBloquesMenu = SecBloquesMenuDesktop.factory(app, driver);
		secMenuSuperior = SecMenuSuperiorDesktop.getNew(app, driver);
		secMenusUser = ModalUserSesionShopDesktop.getNew(driver);
		secMenuLateral = SecMenuLateralDesktop.getNew(app, driver);
		secMenusFiltroDiscount = SecMenusFiltroDiscount.getNew(driver);
	}
	
	public static SecMenusDesktop getNew(AppEcom app, WebDriver driver) {
		return (new SecMenusDesktop(app, driver));
	}
	
	public void hideMenus() {
    	Actions actions = new Actions(driver);
    	try {
			while (isMenusVisible()) {
				actions.moveByOffset(0, 50).build().perform();
			}
    	} catch (MoveTargetOutOfBoundsException e) {
    		if (isMenusVisible()) {
    			String idSubMenu = driver.findElement(By.xpath("//div[@id='headerPortalContainer']//div[@id[contains(.,'SubMenu')]]")).getAttribute("id");
    	    	((JavascriptExecutor) driver).executeScript(
    	            	"document.getElementById('" + idSubMenu + "').style.zIndex=99; " + 
    	            	"document.getElementById('" + idSubMenu + "').style.display='none'"); 
    		}
    	}
		waitMillis(1000);
	}
	
	private boolean isMenusVisible() {
		return secMenuSuperior.secBlockMenus.isCapaMenusLineaVisibleUntil(LineaType.she, 1);
	}
}
