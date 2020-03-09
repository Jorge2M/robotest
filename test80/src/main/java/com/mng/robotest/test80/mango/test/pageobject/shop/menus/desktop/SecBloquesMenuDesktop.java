package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.bloqueMenu;
import com.mng.robotest.test80.mango.test.utils.checkmenus.DataScreenMenu;

public class SecBloquesMenuDesktop extends WebdrvWrapp {
	
	private final WebDriver driver;
	private final AppEcom app;
	private final SecLineasMenuDesktop secLineasMenu;
	
	public enum TypeMenuDesktop {Link, Banner}
	
	static String TagIdLinea = "@LineaId"; //she, he, nina...
	static String TagIdBloque = "@BloqueId"; //Prendas, Accesorios...
	static String TagIdTypeMenu = "@TypeMenu";
    static String XPathContainerMenus = "//div[@class[contains(.,'section-detail-container')]]";
    static String XPathCapaMenus = XPathContainerMenus + "//div[@class[contains(.,'section-detail-list')]]";
    static String XPathCapaMenusLineaWithTag = XPathCapaMenus + "//self::*[@data-brand='" + TagIdLinea + "']";
    static String XPathEntradaMenuLineaRelativeToCapaWithTag = 
    	"//ul[@class[contains(.,'" + TagIdTypeMenu + "')]]" +
    	"/li[@class[contains(.,'menu-item')] and not(@class[contains(.,'desktop-label-hidden')])]/a"; 
    static String XPathEntradaMenuBloqueRelativeWithTag = "//ul/li/a[@data-label[contains(.,'" + TagIdBloque + "-')]]";
    
    private SecBloquesMenuDesktop(AppEcom app, WebDriver driver) {
    	this.driver = driver;
    	this.app = app;
    	this.secLineasMenu = SecLineasMenuDesktop.getNew(app, driver);
    }
    
    public static SecBloquesMenuDesktop getNew(AppEcom app, WebDriver driver) {
    	return (new SecBloquesMenuDesktop(app, driver));
    }
    
    public String getXPathCapaMenusLinea(LineaType lineaId) {
    	String idLineaDom = SecMenusWrap.getIdLineaEnDOM(Channel.desktop, app, lineaId);
    	if (lineaId==LineaType.nuevo) {
    		idLineaDom = "sections_nuevo";
    	}
    	if (lineaId==LineaType.rebajas) {
    		idLineaDom = "sections_rebajas_step1";
    	}
        
        return XPathCapaMenusLineaWithTag.replace(TagIdLinea, idLineaDom);
    }

    String getXPathCapaMenusSublinea(SublineaNinosType sublineaType) {
    	LineaType parentLine = sublineaType.getParentLine();
    	return (getXPathCapaMenusLinea(parentLine));
    }
    
    String getXPathLinkMenuSuperiorRelativeToCapa(TypeMenuDesktop typeMenu) {
    	switch (typeMenu) {
    	case Link:
    		return (XPathEntradaMenuLineaRelativeToCapaWithTag.replace(TagIdTypeMenu, "section-detail"));
    	case Banner:
    	default:
    		return (XPathEntradaMenuLineaRelativeToCapaWithTag.replace(TagIdTypeMenu, "section-image--single"));
    	}
    }
    
    String getXPathMenusSuperiorLinkVisibles(LineaType lineaType, SublineaNinosType sublineaType, TypeMenuDesktop typeMenu) {
        String xpathCapaMenuLinea = "";
        if (sublineaType==null) {
            xpathCapaMenuLinea = getXPathCapaMenusLinea(lineaType);
        } else {
        	xpathCapaMenuLinea = getXPathCapaMenusSublinea(sublineaType);
        }
        
        String xpathMenu = getXPathLinkMenuSuperiorRelativeToCapa(typeMenu);
        return (xpathCapaMenuLinea + xpathMenu);
    }    
    
    String getXPathMenuVisibleByDataInHref(Menu1rstLevel menu1rstLevel) {
    	LineaType lineaMenu = menu1rstLevel.getLinea();
    	SublineaNinosType sublineaMenu = menu1rstLevel.getSublinea();
    	String nombreMenuInLower = menu1rstLevel.getNombre().toLowerCase();
        return (
        	getXPathMenusSuperiorLinkVisibles(lineaMenu, sublineaMenu, TypeMenuDesktop.Link) + 
        	"[@href[contains(.,'/" + nombreMenuInLower + 
        	"')] and @href[not(contains(.,'/" + nombreMenuInLower + "/'))]]");
    }
    
    String getXPathMenuSuperiorLinkVisible(Menu1rstLevel menu1rstLevel) {
    	LineaType lineaMenu = menu1rstLevel.getLinea();
    	SublineaNinosType sublineaMenu = menu1rstLevel.getSublinea();
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
    
    public boolean isCapaMenusLineaVisibleUntil(LineaType lineaId, int maxSecondsToWait) {
    	String xpathCapa = getXPathCapaMenusLinea(lineaId);
    	return (isElementVisibleUntil(driver, By.xpath(xpathCapa), maxSecondsToWait));
    }
    
    public void clickMenuInHrefAndGetName(Menu1rstLevel menu1rstLevel) throws Exception {
        String xpathLinkMenu = getXPathMenuVisibleByDataInHref(menu1rstLevel);
        //menu1rstLevel.setNombre(driver.findElement(By.xpath(xpathLinkMenu)).getText());
        driver.findElement(By.xpath(xpathLinkMenu)).click();
        waitForPageLoaded(driver);
    }

    public List<WebElement> getListMenusLinea(LineaType lineaType, SublineaNinosType sublineaType) throws Exception {
    	secLineasMenu.hoverLineaAndWaitForMenus(lineaType, sublineaType);
        String XPathMenusVisibles = getXPathMenusSuperiorLinkVisibles(lineaType, sublineaType, TypeMenuDesktop.Link);
        return (driver.findElements(By.xpath(XPathMenusVisibles)));
    }

    public List<DataScreenMenu> getListDataScreenMenus(LineaType lineaType, SublineaNinosType sublineaType) 
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
     * @param linea she, he, kids, violeta
     * @param bloque prendas, accesorios, colecciones...
     * @return los menús asociados a una línea/bloque concretos (por bloque entendemos prendas, accesorios, colecciones...)
     */
    public List<WebElement> getListMenusLineaBloque(LineaType lineaType, bloqueMenu bloque) throws Exception {
    	secLineasMenu.hoverLineaAndWaitForMenus(lineaType, null);
        String xpathMenuLinea = getXPathCapaMenusLinea(lineaType);
        String xpathEntradaMenu = XPathEntradaMenuBloqueRelativeWithTag.replace(TagIdBloque, bloque.toString());
        return (driver.findElements(By.xpath(xpathMenuLinea + xpathEntradaMenu)));
    }

    public void clickMenuAndGetName(Menu1rstLevel menu1rstLevel) throws Exception {
    	LineaType lineaMenu = menu1rstLevel.getLinea();
    	SublineaNinosType sublineaMenu = menu1rstLevel.getSublinea();
    	secLineasMenu.hoverLineaAndWaitForMenus(lineaMenu, sublineaMenu);
        String xpathMenu = getXPathMenuSuperiorLinkVisible(menu1rstLevel);
        isElementVisibleUntil(driver, By.xpath(xpathMenu), 1);
        //menu1rstLevel.setNombre(driver.findElement(By.xpath(xpathMenu)).getText().replace("New!", "").trim());
        moveToElement(By.xpath(xpathMenu), driver);
        clickAndWaitLoad(driver, By.xpath(xpathMenu));
    }    
    
    public boolean isPresentMenuFirstLevel(Menu1rstLevel menu1rstLevel) throws Exception {
    	LineaType lineaMenu = menu1rstLevel.getLinea();
    	SublineaNinosType sublineaMenu = menu1rstLevel.getSublinea();
    	secLineasMenu.hoverLineaAndWaitForMenus(lineaMenu, sublineaMenu);
        String xpathMenu = getXPathMenuSuperiorLinkVisible(menu1rstLevel);
        return (isElementVisibleUntil(driver, By.xpath(xpathMenu), 2));
    }
    
    public void seleccionarMenuXHref(Menu1rstLevel menu1rstLevel) throws Exception {
    	secLineasMenu.hoverLineaAndWaitForMenus(menu1rstLevel.getLinea(), menu1rstLevel.getSublinea());
        clickMenuInHrefAndGetName(menu1rstLevel);
    }
    
    public boolean isPresentRightBanner(LineaType lineaType, SublineaNinosType sublineaType) throws Exception {
    	secLineasMenu.hoverLineaAndWaitForMenus(lineaType, null); 
    	String xpathMenuLinea = getXPathMenusSuperiorLinkVisibles(lineaType, sublineaType, TypeMenuDesktop.Banner);
        return (isElementPresent(driver, By.xpath(xpathMenuLinea)));    	
    }
    
    public void clickRightBanner(LineaType lineaType, SublineaNinosType sublineaType) throws Exception {
    	secLineasMenu.hoverLineaAndWaitForMenus(lineaType, sublineaType);
        String xpathMenuLinea = getXPathMenusSuperiorLinkVisibles(lineaType, sublineaType, TypeMenuDesktop.Banner);
        clickAndWaitLoad(driver, By.xpath(xpathMenuLinea));
    }
}
