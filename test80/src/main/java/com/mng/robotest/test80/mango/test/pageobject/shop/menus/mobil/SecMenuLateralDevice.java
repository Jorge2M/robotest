package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.utils.checkmenus.DataScreenMenu;

public class SecMenuLateralDevice extends PageObjTM {

	public enum TypeLocator {dataGaLabelPortion, hrefPortion}
	
	private final Channel channel;
	private final AppEcom app;
	
	private final SecLineasDevice secLineasDevice;
	private final SecMenusUserDevice secUserMenu;
	
	private static String XPathCapa2onLevelMenu = "//div[@class[contains(.,'section-detail-list')]]";
	private String XPathLinkMenuVisibleFromLi = 
		"//ul[@class='section-detail' or @class[contains(.,'dropdown-menu')]]" +
		"/li[not(@class[contains(.,'mobile-label-hidden')] or @class[contains(.,' label-hidden')] or @class[contains(.,' gap ')])]" +
		"/a[@class[contains(.,'menu-item-label')] and @href]";

	public SecMenuLateralDevice(Channel channel, AppEcom app, WebDriver driver) {
		super(driver);
		this.channel = channel;
		this.app = app;
		this.secLineasDevice = SecLineasDevice.make(channel, app, driver);
		this.secUserMenu = SecMenusUserDevice.getNew(channel, app, driver);
	}

	public SecLineasDevice getSecLineasDevice() {
		return secLineasDevice;
	}
	
	public SecMenusUserDevice getUserMenu() {
		return secUserMenu;
	}
	
	private String getXPathLinksMenus(SublineaNinosType sublineaType) {
		if (sublineaType==null) {
			return XPathCapa2onLevelMenu + XPathLinkMenuVisibleFromLi;
		}
		String divSublineaNinos = secLineasDevice.getXPathSublineaNinosLink(sublineaType); 
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
		secLineasDevice.selectLinea(linea, sublineaType);
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
			//.type(javascript)
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
		switch (channel) {
		case tablet:
			return isMenuTabletInStateUntil(open, maxSeconds);
		case mobile:
		default:
			return isMenuMobilInStateUntil(open, maxSeconds);
		}
	}
	
	private final static String XPathLineaItem = "//ul[@class='menu-section-brands']/li[@class[contains(.,'menu-item')]]";
	private boolean isMenuTabletInStateUntil(boolean open, int maxSeconds) {
		if (open) {
			return (state(Visible, By.xpath(XPathLineaItem)).wait(maxSeconds).check());
		}
		return (state(Invisible, By.xpath(XPathLineaItem)).wait(maxSeconds).check());
	}
	
	private boolean isMenuMobilInStateUntil(boolean open, int maxSeconds) {
		try {
			String xpathLineaShe = secLineasDevice.getXPathLineaLink(LineaType.she);
			String xpathLineaNina = secLineasDevice.getXPathLineaLink(LineaType.nina);
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
		secLineasDevice.selectLinea(linea, menu1rstLevel.getSublinea());
		if (app==AppEcom.shop) {
			unfoldMenuGroup(typeLocator, menu1rstLevel);
			SeleniumUtils.waitMillis(500);
		}
		clickMenuYetDisplayed(typeLocator, menu1rstLevel);
	}
	
	private String getXPathMenuGroup(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) {
		String xpathLinkMenu = getXPathMenuByTypeLocator(typeLocator, menu1rstLevel);
		return xpathLinkMenu + "/../../preceding-sibling::div[@class[contains(.,'dropdown')]]";
	}
	
	private boolean isMenuGroupOpen(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel, int maxSeconds) {
		By groupBy = By.xpath(getXPathMenuGroup(typeLocator, menu1rstLevel));
		if (state(State.Present, groupBy).check()) {
			for (int i=0; i<maxSeconds; i++) {
				String classGroup = driver.findElement(groupBy).getAttribute("className");
				if (classGroup!=null && classGroup.contains("open")) {
					return true;
				}
				waitMillis(1000);
			}
		}
		return false;
	}
	
	public void unfoldMenuGroup(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) {
		String xpathLinkMenu = getXPathMenuByTypeLocator(typeLocator, menu1rstLevel);
		By byMenu = By.xpath(xpathLinkMenu);
		boolean menuVisible = state(State.Clickable, byMenu).check();
		String xpathGroupMenu = getXPathMenuGroup(typeLocator, menu1rstLevel);
		List<WebElement> listGroups = driver.findElements(By.xpath(xpathGroupMenu));
		Iterator<WebElement> itGroups = listGroups.iterator();
		while (!menuVisible && itGroups.hasNext()) {
			WebElement group = itGroups.next();
			int ii=0;
			while (!menuVisible && ii<2) {
				click(group).exec();
				isMenuGroupOpen(typeLocator, menu1rstLevel, 1);
				if (state(State.Present, byMenu).wait(1).check()) {
					moveToElement(byMenu, driver);
					menuVisible = state(State.Clickable, byMenu).wait(1).check();
				}
				ii+=1;
			}
		}
	}
	
	public boolean existsMenuLateral1rstLevel(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel, Pais pais) {
		Linea linea = pais.getShoponline().getLinea(menu1rstLevel.getLinea());
		secLineasDevice.selectLinea(linea, menu1rstLevel.getSublinea());
		String xpathMenu = getXPathMenuByTypeLocator(typeLocator, menu1rstLevel);
		return (state(Visible, By.xpath(xpathMenu)).check());
	}
}