package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.javascript;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;

public abstract class SecLineasMobil extends PageObjTM {

	public abstract String getXPathLineaLink(LineaType lineaType) throws IllegalArgumentException;
	public abstract String getXPathSublineaNinosLink(SublineaNinosType sublineaType);
	
	protected final AppEcom app;

	protected static String[] tagsRebajasMobil = {"mujer", "hombre", "ninos", "violeta"};

	protected static String XPathHeaderMobile = "//div[@id='headerMobile']";
	protected static String XPathCapaLevelLinea = "//div[@class[contains(.,'menu-section')]]";
	protected static String XPathCapa2onLevelMenu = "//div[@class[contains(.,'section-detail-list')]]";
	
	public static SecLineasMobil getNew(AppEcom app, WebDriver driver) {
		switch (app) {
		case outlet:
			return new SecLineasMobilOutlet(driver);
		default:
			return new SecLineasMobilShop(driver);
		}
	}
	
	public SecLineasMobil(AppEcom app, WebDriver driver) {
		super(driver);
		this.app = app;
	}
	
	public String getXPathCapa2onLevelMenu() {
		return XPathCapa2onLevelMenu;
	}
	
	public String getXPathHeaderMobile() {
		return XPathHeaderMobile;
	}
	
	private String getXPathLiSublineaNinos(SublineaNinosType sublineaType) {
		String xpathLinkSublinea = getXPathSublineaNinosLink(sublineaType);        
		return (xpathLinkSublinea + "/..");
	}

	private String getXPathBlockSublineasNinos(SublineaNinosType sublineaType) {
		String xpathSublineaLi = getXPathLiSublineaNinos(sublineaType);        
		return (xpathSublineaLi + "/..");
	}
	
	public void selectLinea(Linea linea, SublineaNinosType sublineaType) {
		if (sublineaType==null) {
			selectLinea(linea);
		} else {
			selecSublineaNinosIfNotSelected(linea, sublineaType);
		}
	}
	
	public void selectLinea(Linea linea) {
		boolean toOpenMenus = true;
		SecCabecera secCabecera = SecCabecera.getNew(Channel.mobile, app, driver);
		secCabecera.clickIconoMenuHamburguerMobil(toOpenMenus);
		if ("n".compareTo(linea.getExtended())==0) {
			By byElem = By.xpath(getXPathLineaLink(linea.getType()));
			click(byElem).type(javascript).exec();
		}
 	}

	public boolean isLineaPresent(LineaType lineaType) {
		String xpathLinea = getXPathLineaLink(lineaType);
		return (state(Present, By.xpath(xpathLinea)).check());
	}

	public boolean isLineaPresentUntil(LineaType lineaType, int maxSeconds) {
		String xpathLinea = getXPathLineaLink(lineaType);
		return (state(Present, By.xpath(xpathLinea)).wait(maxSeconds).check());
	}

	public boolean isSelectedLinea(LineaType lineaType) {
		String xpathLineaWithFlagSelected = getXPathLineaLink(lineaType);
		if (app==AppEcom.outlet) {
			xpathLineaWithFlagSelected+="/..";
		}
		if (state(Present, By.xpath(xpathLineaWithFlagSelected)).check()) {
			return (driver.findElement(By.xpath(xpathLineaWithFlagSelected)).getAttribute("class").contains("selected"));
		}
		return false;
	}
	
	public void selecSublineaNinosIfNotSelected(Linea linea, SublineaNinosType sublineaType) {
		selectLinea(linea);
		if (!isSelectedSublineaNinos(sublineaType)) {
			By byElem = By.xpath(getXPathSublineaNinosLink(sublineaType));
			click(byElem).type(javascript).exec();
		}
	}
	
	private boolean isSelectedSublineaNinos(SublineaNinosType sublineaNinosType) {
		String xpathSublineaWithFlagOpen = getXPathSublineaNinosLink(sublineaNinosType);
		if (app==AppEcom.outlet) {
			xpathSublineaWithFlagOpen+="/..";
		}
		if (state(Present, By.xpath(xpathSublineaWithFlagOpen)).check()) {
			return (driver.findElement(By.xpath(xpathSublineaWithFlagOpen)).getAttribute("class").contains("open"));
		}
		return false;
	}

	public boolean isVisibleBlockSublineasNinos(LineaType lineaNinosType) {
		String xpathBlockSublineas = "";
		switch (lineaNinosType) {
		case nina: 
			xpathBlockSublineas = getXPathBlockSublineasNinos(SublineaNinosType.nina);
			break;
		default:
		case nino:
			xpathBlockSublineas = getXPathBlockSublineasNinos(SublineaNinosType.nino);
			break;
		}
		return (state(Visible, By.xpath(xpathBlockSublineas)).check());
	}
}
