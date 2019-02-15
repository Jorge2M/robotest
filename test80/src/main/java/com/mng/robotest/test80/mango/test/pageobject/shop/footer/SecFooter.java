package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class SecFooter extends WebdrvWrapp {

    public static String XPathCapaShop = "//div[@id='nav-footer']";
    public static String XPathCapaOutlet = "//div[@class[contains(.,'footer__column footer__column')]]";
    static List<AppEcom> footerShop = Arrays.asList(AppEcom.shop);
    static List<AppEcom> footerOutlet = Arrays.asList(AppEcom.outlet);
    static List<AppEcom> footerAll = Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf);
    
    static List<Channel> channelDesktop = Arrays.asList(Channel.desktop);
    static List<Channel> channelMobile = Arrays.asList(Channel.movil_web);
    static List<Channel> channelAll = Arrays.asList(Channel.desktop, Channel.movil_web);    
    
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
    	mango(footerOutlet, channelAll, "//a[@data-ga-label='shop' and text()[contains(.,'MANGO')]]", true), 
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
    			if (footerLink.appList.contains(app) && footerLink.channel.contains(channel))
    				listLinksToReturn.add(footerLink);
    		}

    		return listLinksToReturn;
    	}

    }
    
    final static String XPathNewsLetterMsg = "//div[@class='newsletter-label']";
    final static String XPathTextAreaMailSuscripcion = "//input[@id[contains(.,'FooterNewsletter')]]";
    final static String XPathTextRGPD = "//p[@class='gdpr-text gdpr-profiling']";
    final static String XPathLegalRGPD = "//p[@class='gdpr-text gdpr-data-protection']";
    final static String XPathCambioPaisShop = "//li[@class[contains(.,'modalCambioPaisShow')]]/a";
    final static String XPathCambioPaisOutlet = "//span[@class[contains(.,'countrySelector')]]";
    
    /**
     * @return xpath correspondiente a la capa del footer
     */
    static String getXPathCapaFooter(AppEcom app) {
    	switch (app) {
    	case outlet:
            return XPathCapaOutlet;
    	case shop:
    	default:
    		return XPathCapaShop;
    	}
    }
    
    static String getXPathLink(FooterLink footerType, AppEcom app) {
    	return (getXPathCapaFooter(app) + footerType.getXPathRelativeCapa());
    }
    
    public static String getXPathLinkCambioPais(AppEcom app) {
        if (app==AppEcom.outlet)
            return XPathCambioPaisOutlet;
        
        return XPathCambioPaisShop;
    }
    
    public static boolean isPresent(AppEcom app, WebDriver driver) {
        String xpath = getXPathCapaFooter(app);
        return (isElementPresent(driver, By.xpath(xpath)));
    }
    
    public static boolean isVisible(AppEcom app, WebDriver driver) {
        String xpath = getXPathCapaFooter(app);
        return (isElementVisible(driver, By.xpath(xpath)));
    }    
    
    public static void clickLink(FooterLink footerType, WebDriver driver) throws Exception {
    	clickAndWaitLoad(driver, By.xpath(footerType.getXPathRelativeCapa()));
    }
    
    public static String clickLinkAndGetWindowFatherHandle(FooterLink footerType, WebDriver driver) throws Exception {
    	clickLink(footerType, driver);
    	String windowFatherHandle = driver.getWindowHandle();
	    if (footerType.pageInNewTab) {
	        switchToAnotherWindow(driver, windowFatherHandle);
	        waitForPageLoaded(driver, 10);
	    }
	    
	    return windowFatherHandle;
    }
    
    public static void clickLinkCambioPais(WebDriver driver, AppEcom app) throws Exception {
        String xpathLink = getXPathLinkCambioPais(app);
        clickAndWaitLoad(driver, By.xpath(xpathLink));
    }
    
    public static boolean checkFooters(List<FooterLink> listFooterLinksToValidate, AppEcom app, WebDriver driver) {
        for (FooterLink footerLink : listFooterLinksToValidate) {
        	String xpathLink = getXPathLink(footerLink, app);
            if (!isElementPresent(driver, By.xpath(xpathLink)))
                return false;
        }
        
        return true;
    }
    
    public static String getNewsLetterMsgText(WebDriver driver) {
        try {
            WebElement titleNws = driver.findElement(By.xpath(XPathNewsLetterMsg));
            if (titleNws!=null)
                return driver.findElement(By.xpath(XPathNewsLetterMsg)).getText();
        }
        catch (Exception e) {
            //Retornamos ""
        }
        
        return "";
    }
    
    public static boolean newsLetterMsgContains(String literal, WebDriver driver) {
        return (getNewsLetterMsgText(driver).contains(literal));
    }

	public static void clickFooterSuscripcion(WebDriver driver) {
		driver.findElement(By.xpath(XPathTextAreaMailSuscripcion)).click();
	}

	public static boolean isTextoRGPDPresent(WebDriver driver) {
		return isElementPresent(driver, By.xpath(XPathTextRGPD));
	}

	public static boolean isTextoLegalRGPDPresent(WebDriver driver) {
		return isElementPresent(driver, By.xpath(XPathLegalRGPD));
	}
}
