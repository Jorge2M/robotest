package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

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
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.GroupMenu;
import com.mng.robotest.test80.mango.test.utils.checkmenus.DataScreenMenu;

public abstract class SecBloquesMenuDesktop extends PageObjTM {

	public abstract boolean goToMenuAndCheckIsVisible(Menu1rstLevel menu1rstLevel) throws Exception;
	public abstract void clickMenu(Menu1rstLevel menu1rstLevel);
	public abstract void makeMenusGroupVisible(LineaType lineaType, GroupMenu bloque);
	
	protected final AppEcom app;
	protected final SecLineasMenuDesktop secLineasMenu;
	
	public enum TypeMenuDesktop {Link, Banner}
	
	protected final static String TagIdLinea = "@LineaId"; //she, he, nina...
	protected final static String TagIdBloque = "@BloqueId"; //Prendas, Accesorios...
	protected final static String TagIdTypeMenu = "@TypeMenu";
	protected final static String XPathContainerMenus = "//div[@class[contains(.,'section-detail-container')]]";
	protected final static String XPathCapaMenus = XPathContainerMenus + "//div[@class[contains(.,'section-detail-list')]]";
	protected final static String XPathCapaMenusLineaWithTag = XPathCapaMenus + "//self::*[@data-brand[contains(.,'" + TagIdLinea + "')]]";
	protected final static String XPathMenuItem = "/li[@class[contains(.,'menu-item')] and not(@class[contains(.,'desktop-label-hidden')] or @class[contains(.,' label-hidden')])]/a"; 
	protected final static String XPathEntradaMenuLineaRelativeToCapaWithTag = 
		"//ul[@class[contains(.,'" + TagIdTypeMenu + "')]]" +
		XPathMenuItem;
		
	protected final static String XPathEntradaMenuBloqueRelativeWithTag = "//ul/li/a[@data-label[contains(.,'" + TagIdBloque + "-')]]";

	protected SecBloquesMenuDesktop(AppEcom app, WebDriver driver) {
		super(driver);
		this.app = app;
		this.secLineasMenu = SecLineasMenuDesktop.factory(app, driver);
	}
	
	public static SecBloquesMenuDesktop factory(AppEcom app, WebDriver driver) {
		if (app==AppEcom.outlet) {
			return new SecBloquesMenuDesktopOld(app, driver);
		}
		return new SecBloquesMenuDesktopNew(app, driver);
	}

	String getXPathCapaMenusLinea(LineaType lineaId) {
		String idLineaDom = SecMenusWrap.getIdLineaEnDOM(Channel.desktop, app, lineaId);
		if (lineaId==LineaType.rebajas) {
			idLineaDom = "sections_rebajas_step1";
		}

		return XPathCapaMenusLineaWithTag.replace(TagIdLinea, idLineaDom);
	}

	private String getXPathCapaMenusSublinea(SublineaType sublineaType) {
		LineaType parentLine = sublineaType.getParentLine();
		return (getXPathCapaMenusLinea(parentLine));
	}

	private String getXPathLinkMenuSuperiorRelativeToCapa(TypeMenuDesktop typeMenu) {
		switch (typeMenu) {
		case Link:
			return (XPathEntradaMenuLineaRelativeToCapaWithTag.replace(TagIdTypeMenu, "section-detail"));
		case Banner:
		default:
			return (XPathEntradaMenuLineaRelativeToCapaWithTag.replace(TagIdTypeMenu, "section-image--single"));
		}
	}

	private String getXPathMenusSuperiorLinkVisibles(LineaType lineaType, SublineaType sublineaType, TypeMenuDesktop typeMenu) {
		String xpathCapaMenuLinea = "";
		if (sublineaType==null) {
			xpathCapaMenuLinea = getXPathCapaMenusLinea(lineaType);
		} else {
			xpathCapaMenuLinea = getXPathCapaMenusSublinea(sublineaType);
		}

		String xpathMenu = getXPathLinkMenuSuperiorRelativeToCapa(typeMenu);
		return (xpathCapaMenuLinea + xpathMenu);
	}

	private String getXPathMenuVisibleByDataInHref(Menu1rstLevel menu1rstLevel) {
		LineaType lineaMenu = menu1rstLevel.getLinea();
		SublineaType sublineaMenu = menu1rstLevel.getSublinea();
		String nombreMenuInLower = menu1rstLevel.getNombre().toLowerCase();
		return (
			getXPathMenusSuperiorLinkVisibles(lineaMenu, sublineaMenu, TypeMenuDesktop.Link) + 
			"[@href[contains(.,'/" + nombreMenuInLower + 
			"')] and @href[not(contains(.,'/" + nombreMenuInLower + "/'))]]");
	}

	protected String getXPathMenuSuperiorLinkVisible(Menu1rstLevel menu1rstLevel) {
		LineaType lineaMenu = menu1rstLevel.getLinea();
		SublineaType sublineaMenu = menu1rstLevel.getSublinea();
		String dataGaLabelMenu = menu1rstLevel.getDataGaLabelMenuSuperiorDesktop();
		String xpathMenuVisible = getXPathMenusSuperiorLinkVisibles(lineaMenu, sublineaMenu, TypeMenuDesktop.Link);
		if (dataGaLabelMenu.contains("'")) {
			//En el caso de que el data_ga_label contenga ' 
			//no parece existir carácter de escape, así que hemos de desglosar en 2 bloques y aplicar el 'contains' en cada uno
			int posApostrophe = dataGaLabelMenu.indexOf("'");
			String block1 = dataGaLabelMenu.substring(0, posApostrophe);
			String block2 = dataGaLabelMenu.substring(posApostrophe + 1);
			return (
				xpathMenuVisible + 
				"[@data-label[contains(.,'" + block1 + "')] and @data-label[contains(.,'" + 
				block2 + "')]]");
		}

		return (
			xpathMenuVisible + 
			"[@data-label[contains(.,'" + dataGaLabelMenu + "')] or " + 
			"@data-label[contains(.,'" + dataGaLabelMenu.toLowerCase() + "')]]");
	}

	public boolean isCapaMenusLineaVisibleUntil(LineaType lineaId, int maxSeconds) {
		String xpathCapa = getXPathCapaMenusLinea(lineaId);
		return (state(Visible, By.xpath(xpathCapa)).wait(maxSeconds).check());
	}

	public void clickMenuInHrefAndGetName(Menu1rstLevel menu1rstLevel) throws Exception {
		String xpathLinkMenu = getXPathMenuVisibleByDataInHref(menu1rstLevel);
		//menu1rstLevel.setNombre(driver.findElement(By.xpath(xpathLinkMenu)).getText());
		driver.findElement(By.xpath(xpathLinkMenu)).click();
		waitForPageLoaded(driver);
	}

	public List<WebElement> getListMenusLinea(LineaType lineaType, SublineaType sublineaType) throws Exception {
		secLineasMenu.hoverLineaAndWaitForMenus(lineaType, sublineaType);
		String XPathMenusVisibles = getXPathMenusSuperiorLinkVisibles(lineaType, sublineaType, TypeMenuDesktop.Link);
		return (driver.findElements(By.xpath(XPathMenusVisibles)));
	}

	public List<DataScreenMenu> getListDataScreenMenus(LineaType lineaType, SublineaType sublineaType) 
	throws Exception {
		List<DataScreenMenu> listDataMenus = new ArrayList<>();
		List<WebElement> listMenus = getListMenusLinea(lineaType, sublineaType);
		for (int i=0; i<listMenus.size(); i++) {
			DataScreenMenu dataMenu = DataScreenMenu.getNew();
			dataMenu.setDataGaLabel(listMenus.get(i).getAttribute("data-label"));
			if (dataMenu.isDataGaLabelValid()) {
				dataMenu.setLabel(listMenus.get(i).getText().replace("New!", "").trim());
				listDataMenus.add(dataMenu);
			}
		}

		return listDataMenus;
	}

	/**
	 * @param linea she, he, kids, home, teen
	 * @param bloque prendas, accesorios, colecciones...
	 * @return los menús asociados a una línea/bloque concretos (por bloque entendemos prendas, accesorios, colecciones...)
	 */
	public List<WebElement> getListMenusLineaBloque(LineaType lineaType, GroupMenu bloque) throws Exception {
		makeMenusGroupVisible(lineaType, bloque);
		String xpathMenuLinea = getXPathCapaMenusLinea(lineaType);
		String xpathEntradaMenu = XPathEntradaMenuBloqueRelativeWithTag.replace(TagIdBloque, bloque.toString());
		return (driver.findElements(By.xpath(xpathMenuLinea + xpathEntradaMenu)));
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

	public void seleccionarMenuXHref(Menu1rstLevel menu1rstLevel) throws Exception {
		secLineasMenu.hoverLineaAndWaitForMenus(menu1rstLevel.getLinea(), menu1rstLevel.getSublinea());
		clickMenuInHrefAndGetName(menu1rstLevel);
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
