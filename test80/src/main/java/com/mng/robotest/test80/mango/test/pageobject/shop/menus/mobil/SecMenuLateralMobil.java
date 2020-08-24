package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.utils.checkmenus.DataScreenMenu;

public class SecMenuLateralMobil extends PageObjTM {

	public enum TypeLocator {dataGaLabelPortion, hrefPortion}
	
	private final SecLineasMobil secLineasMobil;
	private final SecMenusUserMobil secUserMenu;
	private final AppEcom app;
	
	static String XPathLinkMenuVisibleFromLi = 
		"//ul[@class='section-detail' or @class[contains(.,'dropdown-menu')]]" +
		"/li[not(@class[contains(.,'mobile-label-hidden')] or @class[contains(.,' gap ')])]" +
		"/a[@class='menu-item-label' and @href]";

	public SecMenuLateralMobil(AppEcom app, WebDriver driver) {
		super(driver);
		this.secLineasMobil = SecLineasMobil.getNew(app, driver);
		this.secUserMenu = SecMenusUserMobil.getNew(app, driver);
		this.app = app;
	}

	public SecLineasMobil getSecLineasMobil() {
		return secLineasMobil;
	}
	
	public SecMenusUserMobil getUserMenu() {
		return secUserMenu;
	}
	
	private String getXPathLinksMenus(SublineaNinosType sublineaType) {
		if (sublineaType==null) {
			return secLineasMobil.getXPathCapa2onLevelMenu() + XPathLinkMenuVisibleFromLi;
		}
		String divSublineaNinos = secLineasMobil.getXPathSublineaNinosLink(sublineaType); 
		return divSublineaNinos + "/.." + XPathLinkMenuVisibleFromLi;
	}

	private String getXPathMenuByTypeLocator(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) {
		String xpath2oLevelMenuLink = getXPathLinksMenus(menu1rstLevel.getSublinea());
		switch (typeLocator) {
		case dataGaLabelPortion:
			return xpath2oLevelMenuLink.replace("@href", "@data-label[contains(.,'" + menu1rstLevel.getDataGaLabelMenuSuperiorDesktop().toLowerCase() + "')]");            
		case hrefPortion:
		default:
			return xpath2oLevelMenuLink.replace("@href", "@href[contains(.,'" + menu1rstLevel.getDataGaLabelMenuSuperiorDesktop().toLowerCase() + "')]");
		}
	}

	public boolean isMenus2onLevelDisplayed(SublineaNinosType sublineaType) {
		List<WebElement> listMenus = getListMenusDisplayed(sublineaType);
		return (listMenus!=null && listMenus.size()>0);
	}

	public List<WebElement> getListMenusAfterSelectLinea(Linea linea, SublineaNinosType sublineaType) throws Exception {
		secLineasMobil.selectLinea(linea, sublineaType);
		return (getListMenusDisplayed(sublineaType));
	}

	private List<WebElement> getListMenusDisplayed(SublineaNinosType sublineaType) {
		String xpath2oLevelMenuLink = getXPathLinksMenus(sublineaType);
		if (app==AppEcom.outlet) {
			return (getElementsVisible(driver, By.xpath(xpath2oLevelMenuLink)));
		} else {
			return driver.findElements(By.xpath(xpath2oLevelMenuLink));
		}
	}

	public List<DataScreenMenu> getListDataScreenMenus(Linea linea, SublineaNinosType sublineaType) throws Exception {
		List<DataScreenMenu> listDataMenus = new ArrayList<>();
		List<WebElement> listMenus = getListMenusAfterSelectLinea(linea, sublineaType);
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

	public void clickMenuYetDisplayed(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) {
		String xpathMenu = getXPathMenuByTypeLocator(typeLocator, menu1rstLevel);
		click(By.xpath(xpathMenu))
			.state(Visible)
			.type(javascript)
			.waitLoadPage(0).exec();
	}

	public String getLiteralMenuVisible(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) throws Exception {
		By byMenu = By.xpath(getXPathMenuByTypeLocator(typeLocator, menu1rstLevel));
		state(Visible, byMenu).wait(1).check();
		moveToElement(byMenu, driver);
		if (state(Visible, byMenu).check()) {
			return driver.findElement(byMenu).getAttribute("innerHTML");
		}
		return "";
	}

	public boolean isMenuInStateUntil(boolean open, int maxSeconds) {
		try {
			String xpathLineaShe = secLineasMobil.getXPathLineaLink(LineaType.she);
			String xpathLineaNina = secLineasMobil.getXPathLineaLink(LineaType.nina);
			String xpathValidation = xpathLineaShe + " | " + xpathLineaNina;
			if (open) {
				return (state(Visible, By.xpath(xpathValidation)).wait(maxSeconds).check());
			}
			return (state(Invisible, By.xpath(xpathValidation)).wait(maxSeconds).check());
		}
		catch (Exception e) {
			//Si se produce una excepción continuamos porque existe la posibilidad que el menú se encuentre en el estado que necesitamos
		}
		return true;
	}

	/**
	 * Selecciona un menú lateral de 2o nivel de la aplicación en Móbil
	 * @param linea: línea al que pertenece el menú
	 * @param menu: nombre del menú a nivel del data-ga-label
	 */
	public void clickMenuLateral1rstLevel(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel, Pais pais) {
		Linea linea = pais.getShoponline().getLinea(menu1rstLevel.getLinea());
		secLineasMobil.selectLinea(linea, menu1rstLevel.getSublinea());
		if (app==AppEcom.shop) {
			unfoldMenuGroup(typeLocator, menu1rstLevel);
			SeleniumUtils.waitMillis(500);
		}
		clickMenuYetDisplayed(typeLocator, menu1rstLevel);
	}
	
	public void unfoldMenuGroup(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) {
		String xpathLinkMenu = getXPathMenuByTypeLocator(typeLocator, menu1rstLevel);
		for (int i=0; i<5; i++) {
			if (!state(State.Clickable, By.xpath(xpathLinkMenu)).check()) {
				String xpathGroupMenu = xpathLinkMenu + "/../../preceding-sibling::div[@class[contains(.,'dropdown')]]";
				click(By.xpath(xpathGroupMenu)).exec();
			} else {
				break;
			}
		}
	}
	
	public boolean existsMenuLateral1rstLevel(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel, Pais pais) {
		Linea linea = pais.getShoponline().getLinea(menu1rstLevel.getLinea());
		secLineasMobil.selectLinea(linea, menu1rstLevel.getSublinea());
		String xpathMenu = getXPathMenuByTypeLocator(typeLocator, menu1rstLevel);
		return (state(Visible, By.xpath(xpathMenu)).check());
	}

	public void bringHeaderMobileBackground() throws Exception {
		WebElement menuWrapp = driver.findElement(By.xpath(secLineasMobil.getXPathHeaderMobile()));
		((JavascriptExecutor) driver).executeScript("arguments[0].style.zIndex=1;", menuWrapp);
		Thread.sleep(500);
	}
}