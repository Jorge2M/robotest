package com.mng.robotest.test.pageobject.shop.menus.desktop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusWrap.GroupMenu;
import com.mng.robotest.test.utils.checkmenus.DataScreenMenu;

public abstract class SecBloquesMenuDesktop extends PageObjTM {

	public abstract boolean goToMenuAndCheckIsVisible(Menu1rstLevel menu1rstLevel) throws Exception;
	public abstract void clickMenu(Menu1rstLevel menu1rstLevel);
	public abstract void makeMenusGroupVisible(LineaType lineaType, SublineaType sublineaType, GroupMenu bloque);
	public abstract String getXPathCapaMenusLinea(String idLinea);
	public abstract String getXPathCapaMenusSublinea(SublineaType sublineaType);
	public abstract String getXPathLinkMenuSuperiorRelativeToCapa(TypeMenuDesktop typeMenu);
	public abstract List<DataScreenMenu> getListDataScreenMenus(LineaType lineaType, SublineaType sublineaType) throws Exception;
	public abstract void seleccionarMenuXHref(Menu1rstLevel menu1rstLevel) throws Exception;
	public abstract String getXPathMenuSuperiorLinkVisible(Menu1rstLevel menu1rstLevel);
	public abstract String getXPathCapaMenusLinea(LineaType lineaId);
	public abstract String getXPathMenusSuperiorLinkVisibles(LineaType lineaType, SublineaType sublineaType, TypeMenuDesktop typeMenu);
	
	/**
	 * @param linea she, he, kids, home, teen
	 * @param bloque prendas, accesorios, colecciones...
	 * @return los menús asociados a una línea/bloque concretos (por bloque entendemos prendas, accesorios, colecciones...)
	 */
	public abstract List<WebElement> getListMenusLineaBloque(LineaType lineaType, SublineaType sublineaType, GroupMenu bloque) throws Exception;
	
	protected final AppEcom app;
	protected final Channel channel;
	
	protected final SecLineasMenuDesktop secLineasMenu;
	
	public enum TypeMenuDesktop {Link, Banner}
	
	protected static final String TagIdLinea = "@LineaId"; //she, he, nina...
	protected static final String TagIdBloque = "@BloqueId"; //Prendas, Accesorios...
	protected static final String TagIdTypeMenu = "@TypeMenu";



	protected SecBloquesMenuDesktop(AppEcom app, Channel channel, WebDriver driver) {
		super(driver);
		this.app = app;
		this.channel = channel;
		this.secLineasMenu = SecLineasMenuDesktop.factory(app, channel, driver);
	}
	
	public static SecBloquesMenuDesktop factory(AppEcom app, Channel channel, WebDriver driver) {
		return new SecBloquesMenuDesktopNew(app, channel, driver);
	}
		
	private String getXPathMenuVisibleByDataInHref(Menu1rstLevel menu1rstLevel) {
		LineaType lineaMenu = menu1rstLevel.getLinea();
		SublineaType sublineaMenu = menu1rstLevel.getSublinea();
		String nombreMenuInLower = menu1rstLevel.getNombre().toLowerCase();
		return (
			getXPathMenusSuperiorLinkVisibles(lineaMenu, sublineaMenu, TypeMenuDesktop.Link) + 
			"[(@href[contains(.,'/" + nombreMenuInLower +"')] or @href[contains(.,'/rebajas-" + nombreMenuInLower + "')]) and " + 
			 "@href[not(contains(.,'/" + nombreMenuInLower + "/'))]]");
	}

	public boolean isCapaMenusLineaVisibleUntil(LineaType lineaId, int maxSeconds) {
		String xpathCapa = getXPathCapaMenusLinea(lineaId);
		return (state(Visible, By.xpath(xpathCapa)).wait(maxSeconds).check());
	}

	protected void clickMenuInHref(Menu1rstLevel menu1rstLevel) throws Exception {
		String xpathLinkMenu = getXPathMenuVisibleByDataInHref(menu1rstLevel);
		driver.findElement(By.xpath(xpathLinkMenu)).click();
		waitForPageLoaded(driver);
	}

	protected List<DataScreenMenu> getDataListMenus(List<WebElement> listMenus, Channel channel) {
		List<DataScreenMenu> listDataMenus = new ArrayList<>();
		for (int i=0; i<listMenus.size(); i++) {
			DataScreenMenu dataMenu = DataScreenMenu.from(listMenus.get(i), channel);
			listDataMenus.add(dataMenu);
		}
		return listDataMenus;
	}

	protected void hoverLineaMenu(Menu1rstLevel menu1rstLevel) {
		LineaType lineaMenu = menu1rstLevel.getLinea();
		SublineaType sublineaMenu = menu1rstLevel.getSublinea();
		secLineasMenu.hoverLineaAndWaitForMenus(lineaMenu, sublineaMenu);
	}

	public void gotoAndClickMenu(Menu1rstLevel menu1rstLevel) throws Exception {
		goToMenuAndCheckIsVisible(menu1rstLevel);
		clickMenu(menu1rstLevel);
	}
	
	protected void clickEstandarMenu(Menu1rstLevel menu1rstLevel) {
		String xpathMenu = getXPathMenuSuperiorLinkVisible(menu1rstLevel);
		moveToElement(By.xpath(xpathMenu), driver);
		boolean menuVisible;
		int i=0;
		do {
			click(By.xpath(xpathMenu)).exec();
			menuVisible = !state(State.Invisible, By.xpath(xpathMenu)).wait(1).check();
			i+=1;
		} 
		while (menuVisible && i<5);
	}

	public boolean isPresentMenuFirstLevel(Menu1rstLevel menu1rstLevel) throws Exception {
		LineaType lineaMenu = menu1rstLevel.getLinea();
		SublineaType sublineaMenu = menu1rstLevel.getSublinea();
		secLineasMenu.hoverLineaAndWaitForMenus(lineaMenu, sublineaMenu);
		String xpathMenu = getXPathMenuSuperiorLinkVisible(menu1rstLevel);
		return (state(Visible, By.xpath(xpathMenu)).wait(2).check());
	}

	public boolean isPresentRightBanner(LineaType lineaType, SublineaType sublineaType) throws Exception {
		secLineasMenu.hoverLineaAndWaitForMenus(lineaType, null); 
		String xpathMenuLinea = getXPathMenusSuperiorLinkVisibles(lineaType, sublineaType, TypeMenuDesktop.Banner);
		return (state(Present, By.xpath(xpathMenuLinea)).check());
	}

	public void clickRightBanner(LineaType lineaType, SublineaType sublineaType) {
		secLineasMenu.hoverLineaAndWaitForMenus(lineaType, sublineaType);
		String xpathMenuLinea = getXPathMenusSuperiorLinkVisibles(lineaType, sublineaType, TypeMenuDesktop.Banner);
		click(By.xpath(xpathMenuLinea)).exec();
	}
}
