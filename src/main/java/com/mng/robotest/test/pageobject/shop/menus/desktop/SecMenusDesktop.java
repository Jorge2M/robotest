package com.mng.robotest.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusFiltroDiscount;

public class SecMenusDesktop extends PageBase {
	
	public final SecBloquesMenuDesktop secBloquesMenu = new SecBloquesMenuDesktopNew();
	public final SecMenuSuperiorDesktop secMenuSuperior = new SecMenuSuperiorDesktop();
	public final ModalUserSesionShopDesktop secMenusUser = new ModalUserSesionShopDesktop();
	public final SecMenuLateralDesktop secMenuLateral = new SecMenuLateralDesktop();
	public final SecMenusFiltroDiscount secMenusFiltroDiscount = new SecMenusFiltroDiscount();
	
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
