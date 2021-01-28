package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalClubMangoLikes;


public class SecFooter extends PageObjTM {

	private final AppEcom app;
	
    private final static String XPathCapaShop = "//div[@id='nav-footer']";
    private final static String XPathCapaOutlet = "//div[@class[contains(.,'footer__column footer__column')]]";
    
    private final SecNewsLetter secNewsLetter;
    
    static List<AppEcom> footerShop = Arrays.asList(AppEcom.shop);
    static List<AppEcom> footerOutlet = Arrays.asList(AppEcom.outlet);
    static List<AppEcom> footerAll = Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf);
    
    static List<Channel> channelDesktop = Arrays.asList(Channel.desktop);
    static List<Channel> channelMobile = Arrays.asList(Channel.mobile, Channel.tablet);
    static List<Channel> channelAll = Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet);    
    
    public static enum FooterLink {
    	ayuda(footerShop, channelAll, "//a[@data-ga-label='ayuda' and text()[contains(.,'Ayuda')]]", false), 
    	miscompras(footerShop, channelAll, "//a[@data-ga-label='miscompras']", false), 
    	devoluciones(footerAll, channelAll, "//a[@data-ga-label='devoluciones' and text()[contains(.,'Devoluciones')]]", false), 
    	tiendas(footerShop, channelAll, "//a[@data-ga-label='buscarTiendas']", false),
		mango_card(footerShop, channelAll, "//a[@data-ga-label='tarjetaMango' and text()[contains(.,'Mango Card')]]", false),
    	cheque_regalo(footerShop, channelDesktop, "//a[@data-ga-label='cheques' and text()[contains(.,'egalo')]]", false),
    	apps(footerShop, channelAll, "//a[@data-ga-label='apps' and text()[contains(.,'Apps')]]", false), 
    	empresa(footerAll, channelDesktop, "//a[@data-ga-label='pieEmpresa' and text()[contains(.,'Empresa')]]", true), 
    	franquicias(footerShop, channelDesktop, "//a[@data-ga-label='pieFranquicias' and text()[contains(.,'Franquicias')]]", true), 
    	trabaja_con_nosotros_shop(footerShop, channelAll, "//a[@data-ga-label[contains(.,'pieTrabajar')] and text()[contains(.,'Trabaja')]]", true), 
    	prensa(footerShop, channelDesktop, "//a[@data-ga-label='pressroom' and text()[contains(.,'Prensa')]]", true), 
    	mango_outlet(footerShop, channelAll, "//a[@data-ga-label='outlet' and text()[contains(.,'Mango Outlet')]]", true),
    	
    	preguntas_frecuentes(footerOutlet, channelAll, "//a[@data-ga-label='ayuda' and text()[contains(.,'Preguntas Frecuentes')]]", false), 
    	pedidos(footerOutlet, channelAll, "//a[@data-ga-label='pedidos' and text()[contains(.,'Pedidos')]]", false), 
    	envio(footerOutlet, channelAll, "//a[@data-ga-label='envio' and text()[contains(.,'Envío')]]", false), 
    	formas_de_pago(footerOutlet, channelAll, "//a[@data-ga-label='pago' and text()[contains(.,'Formas de pago')]]", false), 
    	guia_de_tallas(footerOutlet, channelAll, "//a[@data-ga-label='guiaTallas']", true),
    	mango(footerOutlet, channelAll, "//a[@data-ga-label='shop' and (text()[contains(.,'MANGO')] or text()[contains(.,'Mango')])]", true), 
    	trabaja_con_nosotros_outlet(footerOutlet, channelAll, "//a[@data-ga-label='pieTrabajarNew' and text()[contains(.,'Trabaja')]]", true);
    	
    	List<AppEcom> appList;
    	List<Channel> channel;
    	String xpathRelativeCapa;
    	boolean pageInNewTab;
    	
    	private FooterLink(List<AppEcom> appList, List<Channel> channel, String xpathRelativeCapa, boolean pageInNewTab) {
    		this.appList = appList;
    		this.channel = channel;
    		this.xpathRelativeCapa = xpathRelativeCapa;
    		this.pageInNewTab = pageInNewTab;
    	}
    	
    	public String getXPathRelativeCapa() {
    		return this.xpathRelativeCapa;
    	}
    	
    	public boolean pageInNewTab() {
    		return this.pageInNewTab;
    	}
    	
    	public static List<FooterLink> getFooterLinksFiltered(AppEcom app, Channel channel) {
    		List<FooterLink> listLinksToReturn = new ArrayList<>();
    		for (FooterLink footerLink : FooterLink.values()) {
    			if (footerLink.appList.contains(app) && footerLink.channel.contains(channel)) {
    				listLinksToReturn.add(footerLink);
    			}
    		}

    		return listLinksToReturn;
    	}

    }
    
    
    private final static String XPathLegalRGPD = "//p[@class='gdpr-text gdpr-data-protection']";
    private final static String XPathCambioPaisShop = "//div[@class[contains(.,'modalCambioPaisShow')]]";
    private final static String XPathCambioPaisOutlet = "//span[@class[contains(.,'countrySelector')]]";
    
    public SecFooter(AppEcom app, WebDriver driver) {
    	super(driver);
    	this.app = app;
    	this.secNewsLetter = new SecNewsLetter(app, driver);
    }
    
    private String getXPathCapaFooter() {
    	switch (app) {
    	case outlet:
            return XPathCapaOutlet;
    	case shop:
    	default:
    		return XPathCapaShop;
    	}
    }
    
    private String getXPathLink(FooterLink footerType) {
    	return (getXPathCapaFooter() + footerType.getXPathRelativeCapa());
    }
    
    private String getXPathLinkCambioPais() {
        if (app==AppEcom.outlet) {
            return XPathCambioPaisOutlet;
        }
        return XPathCambioPaisShop;
    }
    
    public boolean isPresent() {
        String xpath = getXPathCapaFooter();
        return (state(Present, By.xpath(xpath)).check());
    }
    
    public boolean isVisible() throws Exception {
        String xpath = getXPathCapaFooter();
        waitForPageLoaded(driver);
        return (state(Visible, By.xpath(xpath)).check());
    }    
    
    public void clickLink(FooterLink footerType) {
    	ModalClubMangoLikes.closeModalIfVisible(driver);
    	moveToElement(By.xpath(footerType.getXPathRelativeCapa()), driver);
    	click(By.xpath(footerType.getXPathRelativeCapa())).exec();
    }
    
    public String clickLinkAndGetWindowFatherHandle(FooterLink footerType) throws Exception {
    	clickLink(footerType);
    	String windowFatherHandle = driver.getWindowHandle();
	    if (footerType.pageInNewTab) {
	        switchToAnotherWindow(driver, windowFatherHandle);
	        waitForPageLoaded(driver, 10);
	    }
	    return windowFatherHandle;
    }

	public void clickLinkCambioPais() {
		String xpathLink = getXPathLinkCambioPais();
		click(By.xpath(xpathLink)).exec();
	}

    public boolean checkFooters(List<FooterLink> listFooterLinksToValidate) {
        for (FooterLink footerLink : listFooterLinksToValidate) {
        	String xpathLink = getXPathLink(footerLink);
        	if (!state(Present, By.xpath(xpathLink)).check()) {
                return false;
            }
        }
        return true;
    }
    
	public boolean isTextoLegalRGPDPresent() {
		return (state(Present, By.xpath(XPathLegalRGPD)).check());
	}
	
	public void moveTo() {
		String xpath = getXPathCapaFooter();
		By footer = By.xpath(xpath);
		if (state(Visible, footer, driver).check()) {
			moveToElement(footer, driver);
		}
	}
	
	public void clickFooterSuscripcion() throws Exception {
		secNewsLetter.clickFooterSuscripcion();
	}
	
	public boolean newsLetterMsgContains(String literal) {
		return secNewsLetter.newsLetterMsgContains(literal);
	}
}
