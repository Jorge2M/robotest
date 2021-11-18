package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.javascript;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Linea;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.beans.Sublinea.SublineaType;
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
	static String XPathLinkSublineaTeenNina = IniXPathLinkSublinea + "chica')]]";
	static String XPathLinkSublineaNino = IniXPathLinkSublinea + "nino')]]";
	static String XPathLinkSublineaBebeNino = IniXPathLinkSublinea + "bebe_nino')]]";
	static String XPathLinkSublineaTeenNino = IniXPathLinkSublinea + "chico')]]";
	
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
	
	public String getXPathSublineaNinosLink(SublineaType sublineaType) {
		switch (sublineaType) {
		case nina_nina:
			return getXPathCapaMenus() + XPathLinkSublineaNina;
		case teen_nina:
			return getXPathCapaMenus() + XPathLinkSublineaTeenNina;
		case nina_bebe:
			return getXPathCapaMenus() + XPathLinkSublineaBebeNina;
		case nino_nino:
			return getXPathCapaMenus() + XPathLinkSublineaNino;
		case teen_nino:
			return getXPathCapaMenus() + XPathLinkSublineaTeenNino;
		case nino_bebe:
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
	
	public void selectLinea(Linea linea, SublineaType sublineaType) {
		if (sublineaType==null) {
			selectLinea(linea);
		} else {
			selecSublineaNinosIfNotSelected(linea, sublineaType);
		}
	}
	
	public void selecSublineaNinosIfNotSelected(Linea linea, SublineaType sublineaType) {
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
	
	public boolean isSelectedSublineaNinos(SublineaType sublineaNinosType) {
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
			xpathBlockSublineas = getXPathBlockSublineasNinos(SublineaType.nina_nina);
			break;
		case teen:
			xpathBlockSublineas = getXPathBlockSublineasNinos(SublineaType.teen_nina);
			break;
		default:
		case nino:
			xpathBlockSublineas = getXPathBlockSublineasNinos(SublineaType.nino_nino);
			break;
		}
		return (state(Visible, By.xpath(xpathBlockSublineas)).check());
	}
	
	private String getXPathLiSublineaNinos(SublineaType sublineaType) {
		String xpathLinkSublinea = getXPathSublineaNinosLink(sublineaType);		
		return (xpathLinkSublinea + "/..");
	}
	
	private String getXPathBlockSublineasNinos(SublineaType sublineaType) {
		String xpathSublineaLi = getXPathLiSublineaNinos(sublineaType);		
		return (xpathSublineaLi + "/..");
	}
	
}
