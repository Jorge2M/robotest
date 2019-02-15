package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.bloqueMenu;

public class SecBloquesMenuDesktop extends WebdrvWrapp {
	
	public enum TypeMenuDesktop {Link, Banner}
	
	static String TagIdLinea = "@LineaId"; //she, he, nina...
	static String TagIdBloque = "@BloqueId"; //Prendas, Accesorios...
	static String TagIdTypeMenu = "@TypeMenu";
    static String XPathContainerMenus = "//div[@class[contains(.,'section-detail-container')]]";
    static String XPathCapaMenus = XPathContainerMenus + "//div[@class[contains(.,'section-detail-list')]]";
    static String XPathCapaMenusLineaNoNuevoWithTag = XPathCapaMenus + "/ul/li[@id[contains(.,'" + TagIdLinea + "')]]/../..";
    static String XPathCapaMenusLineaNuevoWithTag = XPathCapaMenus + "//self::*[@data-brand[contains(.,'" + TagIdLinea + "')]]";
    static String XPathEntradaMenuLineaRelativeToCapaWithTag = 
    	"//ul[@class='" + TagIdTypeMenu + "']/li[@class[contains(.,'menu-item')] and not(@class[contains(.,'desktop-label-hidden')])]/a"; 
    static String XPathEntradaMenuBloqueRelativeWithTag = "//ul/li/a[@data-label[contains(.,'" + TagIdBloque + "-')]]";
    
    public static String getXPathCapaMenusLinea(LineaType lineaId) {
        String idLineaDom = SecMenusWrap.getIdLineaEnDOM(lineaId, AppEcom.shop, Channel.desktop);
        if (lineaId==LineaType.nuevo)
        	return XPathCapaMenusLineaNuevoWithTag.replace(TagIdLinea, idLineaDom);
        return XPathCapaMenusLineaNoNuevoWithTag.replace(TagIdLinea, idLineaDom);
    }

    static String getXPathCapaMenusSublinea(SublineaNinosType sublineaType) {
        String idSublineaEnDom = sublineaType.getId(AppEcom.shop);
        return XPathCapaMenusLineaNoNuevoWithTag.replace(TagIdLinea, idSublineaEnDom);
    }
    
    static String getXPathLinkMenuSuperiorRelativeToCapa(TypeMenuDesktop typeMenu) {
    	switch (typeMenu) {
    	case Link:
    		return (XPathEntradaMenuLineaRelativeToCapaWithTag.replace(TagIdTypeMenu, "section-detail"));
    	case Banner:
    	default:
    		return (XPathEntradaMenuLineaRelativeToCapaWithTag.replace(TagIdTypeMenu, "section-image--single"));
    	}
    }
    
    static String getXPathMenusSuperiorLinkVisibles(LineaType lineaType, SublineaNinosType sublineaType, TypeMenuDesktop typeMenu) {
        String xpathCapaMenuLinea = "";
        if (sublineaType==null) {
            xpathCapaMenuLinea = getXPathCapaMenusLinea(lineaType);
        }
        else {
        	xpathCapaMenuLinea = getXPathCapaMenusSublinea(sublineaType);
        }
        
        String xpathMenu = getXPathLinkMenuSuperiorRelativeToCapa(typeMenu);
        return (xpathCapaMenuLinea + xpathMenu);
    }    
    
    static String getXPathMenuVisibleByDataInHref(Menu1rstLevel menu1rstLevel) {
    	LineaType lineaMenu = menu1rstLevel.getLinea();
    	SublineaNinosType sublineaMenu = menu1rstLevel.getSublinea();
    	String nombreMenuInLower = menu1rstLevel.getNombre().toLowerCase();
        return (getXPathMenusSuperiorLinkVisibles(lineaMenu, sublineaMenu, TypeMenuDesktop.Link) + 
        		"[@href[contains(.,'/" + nombreMenuInLower + "')] and @href[not(contains(.,'/" + nombreMenuInLower + "/'))]]");
    }
    
    static String getXPathMenuSuperiorLinkVisible(Menu1rstLevel menu1rstLevel) {
    	LineaType lineaMenu = menu1rstLevel.getLinea();
    	SublineaNinosType sublineaMenu = menu1rstLevel.getSublinea();
    	String dataGaLabelMenu = menu1rstLevel.getDataGaLabelMenuSuperiorDesktop();
        String xpathMenuVisible = getXPathMenusSuperiorLinkVisibles(lineaMenu, sublineaMenu, TypeMenuDesktop.Link);
        if (dataGaLabelMenu.contains("'")) {
            //En el caso de que el data_ga_label contenga ' 
            //no parece existir carácter de escape, así que hemos de desglosar en 2 bloques y aplicar el 'contains' en cada uno de ellos
            int posApostrophe = dataGaLabelMenu.indexOf("'");
            String block1 = dataGaLabelMenu.substring(0, posApostrophe);
            String block2 = dataGaLabelMenu.substring(posApostrophe + 1);
            return xpathMenuVisible + "[@data-label[contains(.,'" + block1 + "')] and @data-label[contains(.,'" + block2 + "')]]";
        }

        return xpathMenuVisible + "[@data-label[contains(.,'" + dataGaLabelMenu + "')] or @data-label[contains(.,'" + dataGaLabelMenu.toLowerCase() + "')]]";
    }
    
    public static boolean isCapaMenusLineaVisibleUntil(LineaType lineaId, int maxSecondsToWait, WebDriver driver) {
    	String xpathCapa = getXPathCapaMenusLinea(lineaId);
    	return (isElementVisibleUntil(driver, By.xpath(xpathCapa), maxSecondsToWait));
    }
    
    public static void clickMenuInHrefAndGetName(Menu1rstLevel menu1rstLevel, WebDriver driver) 
    throws Exception {
        String xpathLinkMenu = getXPathMenuVisibleByDataInHref(menu1rstLevel);
        menu1rstLevel.setNombre(driver.findElement(By.xpath(xpathLinkMenu)).getText());
        driver.findElement(By.xpath(xpathLinkMenu)).click();
        waitForPageLoaded(driver);
    }

    public static List<WebElement> getListMenusLinea(LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, WebDriver driver) 
    throws Exception {
        SecLineasMenuDesktop.hoverLineaAndWaitForMenus(lineaType, sublineaType, app, driver);
        String XPathMenusVisibles = getXPathMenusSuperiorLinkVisibles(lineaType, sublineaType, TypeMenuDesktop.Link);
        return (driver.findElements(By.xpath(XPathMenusVisibles)));
    }

    public static List<String> getListDataLabelsMenus(LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, WebDriver driver) 
    throws Exception {
        List<WebElement> listMenus = getListMenusLinea(lineaType, sublineaType, app, driver);
        List<String> listMenusLabel = new ArrayList<>();
        for (int i=0; i<listMenus.size(); i++) {
            String data_ga_label = listMenus.get(i).getAttribute("data-label");
            if (data_ga_label!=null && data_ga_label.compareTo("")!=0)
                listMenusLabel.add(data_ga_label);
        }
        
        return listMenusLabel;
    }
    
    /**
     * @param linea she, he, kids, violeta
     * @param bloque prendas, accesorios, colecciones...
     * @return los menús asociados a una línea/bloque concretos (por bloque entendemos prendas, accesorios, colecciones...)
     */
    public static List<WebElement> getListMenusLineaBloque(LineaType lineaType, bloqueMenu bloque, AppEcom app, WebDriver driver) 
    throws Exception {
    	SecLineasMenuDesktop.hoverLineaAndWaitForMenus(lineaType, null, app, driver);
        String xpathMenuLinea = getXPathCapaMenusLinea(lineaType);
        String xpathEntradaMenu = XPathEntradaMenuBloqueRelativeWithTag.replace(TagIdBloque, bloque.toString());
        return (driver.findElements(By.xpath(xpathMenuLinea + xpathEntradaMenu)));
    }

    public static void clickMenuAndGetName(Menu1rstLevel menu1rstLevel, AppEcom app, WebDriver driver) 
    throws Exception {
    	LineaType lineaMenu = menu1rstLevel.getLinea();
    	SublineaNinosType sublineaMenu = menu1rstLevel.getSublinea();
        SecLineasMenuDesktop.hoverLineaAndWaitForMenus(lineaMenu, sublineaMenu, app, driver);
        String xpathMenu = getXPathMenuSuperiorLinkVisible(menu1rstLevel);
        isElementVisibleUntil(driver, By.xpath(xpathMenu), 1);
        menu1rstLevel.setNombre(driver.findElement(By.xpath(xpathMenu)).getText().replace("New!", "").trim());
        moveToElement(By.xpath(xpathMenu), driver);
        clickAndWaitLoad(driver, By.xpath(xpathMenu));
    }    
    
    public static void seleccionarMenuXHref(Menu1rstLevel menu1rstLevel, WebDriver driver) 
    throws Exception {
        SecLineasMenuDesktop.hoverLineaAndWaitForMenus(menu1rstLevel.getLinea(), menu1rstLevel.getSublinea(), menu1rstLevel.getApp(), driver);
        clickMenuInHrefAndGetName(menu1rstLevel, driver);
    }
    
    public static boolean isPresentRightBanner(LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, WebDriver driver) 
    throws Exception {
    	SecLineasMenuDesktop.hoverLineaAndWaitForMenus(lineaType, null/*sublineaType*/, app, driver); 
    	String xpathMenuLinea = getXPathMenusSuperiorLinkVisibles(lineaType, sublineaType, TypeMenuDesktop.Banner);
        return (isElementPresent(driver, By.xpath(xpathMenuLinea)));    	
    }
    
    public static void clickRightBanner(LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, WebDriver driver) throws Exception {
        SecLineasMenuDesktop.hoverLineaAndWaitForMenus(lineaType, sublineaType, app, driver);
        String xpathMenuLinea = getXPathMenusSuperiorLinkVisibles(lineaType, sublineaType, TypeMenuDesktop.Banner);
        clickAndWaitLoad(driver, By.xpath(xpathMenuLinea));
    }
}
