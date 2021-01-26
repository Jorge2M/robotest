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

public abstract class SecLineasDevice extends PageObjTM {

	abstract public String getXPathLineaLink(LineaType lineaType) throws IllegalArgumentException;
	
	protected final Channel channel;
	protected final AppEcom app;
	
	static String XPathCapaMenuLineasTablet = "//div[@class='menu-section-brands']";
	static String XPathCapaMenuLineasMobil = "//div[@class='section-detail-list']";
	
	static String IniXPathLinkSublinea = "//div[@data-label[contains(.,'interior-"; 
	static String XPathLinkSublineaNina =  IniXPathLinkSublinea + "nina')]]";
	static String XPathLinkSublineaBebeNina = IniXPathLinkSublinea + "bebe_nina')]]";
	static String XPathLinkSublineaTeenNina = IniXPathLinkSublinea + "teen')]]";
	static String XPathLinkSublineaNino = IniXPathLinkSublinea + "nino')]]";
	static String XPathLinkSublineaBebeNino = IniXPathLinkSublinea + "bebe_nino')]]";
	static String XPathLinkSublineaTeenNino = IniXPathLinkSublinea + "teen')]]";
	
	public SecLineasDevice(Channel channel, AppEcom app, WebDriver driver) {
		super(driver);
		this.channel = channel;
		this.app = app;
	}
	
	public static SecLineasDevice make(Channel channel, AppEcom app, WebDriver driver) {
		switch (channel) {
		case tablet:
			return SecLineasTablet.getNew(app, driver); 
		case mobile:
		default:
			return SecLineasMobil.getNew(app, driver);
		}
	}
	
	public String getXPathSublineaNinosLink(SublineaNinosType sublineaType) {
		switch (sublineaType) {
		case nina:
			return getXPathCapaMenus() + XPathLinkSublineaNina;
		case teen_nina:
			return getXPathCapaMenus() + XPathLinkSublineaTeenNina;
		case bebe_nina:
			return getXPathCapaMenus() + XPathLinkSublineaBebeNina;
		case nino:
			return getXPathCapaMenus() + XPathLinkSublineaNino;
		case teen_nino:
			return getXPathCapaMenus() + XPathLinkSublineaTeenNino;
		case bebe_nino:
		default:
			return getXPathCapaMenus() + XPathLinkSublineaBebeNino;
		}
	}
	
	private String getXPathCapaMenus() {
		if (channel==Channel.tablet) {
			return XPathCapaMenuLineasTablet;
		}
		return XPathCapaMenuLineasMobil;
	}
	
	public void selectLinea(Linea linea, SublineaNinosType sublineaType) {
		if (sublineaType==null) {
			selectLinea(linea);
		} else {
			selecSublineaNinosIfNotSelected(linea, sublineaType);
		}
	}
	
	public void selecSublineaNinosIfNotSelected(Linea linea, SublineaNinosType sublineaType) {
		selectLinea(linea);
		if (!isSelectedSublineaNinos(sublineaType)) {
			By byElem = By.xpath(getXPathSublineaNinosLink(sublineaType));
			click(byElem).type(javascript).exec();
		}
	}

	public void selectLinea(Linea linea) {
		boolean toOpenMenus = true;
		SecCabecera secCabecera = SecCabecera.getNew(channel, app, driver);
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
	
	public boolean isSelectedLinea(LineaType lineaType) {
		String xpathLineaWithFlagSelected = getXPathLineaLink(lineaType);
		if (app==AppEcom.outlet || channel==Channel.tablet) {
			xpathLineaWithFlagSelected+="/..";
		}
		if (state(Present, By.xpath(xpathLineaWithFlagSelected)).check()) {
			return (driver.findElement(By.xpath(xpathLineaWithFlagSelected)).getAttribute("class").contains("selected"));
		}
		return false;
	}
	
	public boolean isSelectedSublineaNinos(SublineaNinosType sublineaNinosType) {
		String xpathSublineaWithFlagOpen = getXPathSublineaNinosLink(sublineaNinosType);
		if (app==AppEcom.outlet || channel==Channel.tablet) {
			xpathSublineaWithFlagOpen+="/..";
		}
		if (state(Present, By.xpath(xpathSublineaWithFlagOpen)).check()) {
			String classDropdown = driver.findElement(By.xpath(xpathSublineaWithFlagOpen)).getAttribute("class");
			return (classDropdown.contains("open") || classDropdown.contains("-up"));
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
	
	private String getXPathLiSublineaNinos(SublineaNinosType sublineaType) {
		String xpathLinkSublinea = getXPathSublineaNinosLink(sublineaType);        
		return (xpathLinkSublinea + "/..");
	}
	
	private String getXPathBlockSublineasNinos(SublineaNinosType sublineaType) {
		String xpathSublineaLi = getXPathLiSublineaNinos(sublineaType);        
		return (xpathSublineaLi + "/..");
	}
	
}
