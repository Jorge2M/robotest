package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.pageobject.TypeOfClick;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;


public class SecMenuLateralMobil extends WebdrvWrapp {
    
    public static SecMenusUserMobil secMenusUser;
    
    public enum TypeLocator {dataGaLabelPortion, hrefPortion}
    
    public static String[] tagsRebajasMobil = {"mujer", "hombre", "ninos", "violeta"};
    
    //Capa superior que contiene el menú
    static String XPathHeaderMobile = "//div[@id='headerMobile']";
    
    //New
    //Capa líneas shop y outlet
    static String XPathCapaLevelLinea = "//div[@class[contains(.,'menu-section')]]";
    static String IniXPathLinkLinea = XPathCapaLevelLinea + "//li/div[@class[contains(.,'menu-item-label')] and @id"; 
    static String XPathLinkLineaRebajas = IniXPathLinkLinea + "[contains(.,'sections_rebajas')]]"; 
    static String XPathLinkLineaNuevo = IniXPathLinkLinea + "[contains(.,'sections_nuevo')]]";
    static String XPathLinkLineaMujerShop = IniXPathLinkLinea + "='she']";
    static String XPathLinkLineaMujerOutlet = IniXPathLinkLinea + "='outlet']";
    static String XPathLinkLineaHombreShop = IniXPathLinkLinea + "='he']";
    static String XPathLinkLineaHombreOutlet = IniXPathLinkLinea + "='outletH']";
    static String XPathLinkLineaNinaShop = IniXPathLinkLinea + "='nina']";
    static String XPathLinkLineaNinaOutlet = IniXPathLinkLinea + "='outletA']";
    static String XPathLinkLineaNinoShop =IniXPathLinkLinea + "='nino']";
    static String XPathLinkLineaNinoOutlet =IniXPathLinkLinea + "='outletO']";
    static String XPathLinkLineaKids =IniXPathLinkLinea + "='kids']"; //p.e. Bolivia
    static String XPathLinkLineaVioletaShop = IniXPathLinkLinea + "='violeta']";
    static String XPathLinkLineaVioletaOutlet = IniXPathLinkLinea + "='outletV']";
    static String XPathLinkLineaEdits = IniXPathLinkLinea + "[contains(.,'sections_edits')]]";
    
    //Link line New specific for countrys 388,743
    static String XPathLinkLineaNewFor388and743 = XPathCapaLevelLinea + "//li/a[@class[contains(.,'menu-item-label')] and @data-label[contains(.,'nuevo')]]";
    
    //Sublíneas niños
    static String XPathCapa2onLevelMenu = "//div[@class[contains(.,'section-detail-list')]]";
    static String IniXPathLinkSublinea = XPathCapa2onLevelMenu + "//div[@data-label[contains(.,'interior-"; 
    static String XPathLinkSublineaNina =  IniXPathLinkSublinea + "nina')]]";
    static String XPathLinkSublineaBebeNina = IniXPathLinkSublinea + "bebe_nina')]]";
    static String XPathLinkSublineaNino = IniXPathLinkSublinea + "nino')]]";
    static String XPathLinkSublineaBebeNino = IniXPathLinkSublinea + "bebe_nino')]]";
    
    //Sublíneas rebajas
    static String IniXPathLinkSublineaRebajas = XPathCapa2onLevelMenu + "//div[@id[contains(.,'rebajas_";
    static String XPathLinkSublineaRebajasMujer = IniXPathLinkSublineaRebajas + "she')]]";
    static String XPathLinkSublineaRebajasHombre = IniXPathLinkSublineaRebajas + "he')]]";
    static String XPathLinkSublineaRebajasNina = IniXPathLinkSublineaRebajas + "kidsA')]]";
    static String XPathLinkSublineaRebajasNino = IniXPathLinkSublineaRebajas + "kidsO')]]";
    static String XPathLinkSublineaRebajasVioleta = IniXPathLinkSublineaRebajas + "violeta')]]";
    
    //Capa submenús nuevo
    static String XPathCarruselNuevoMujerLink = 
    	XPathCapa2onLevelMenu + 
    	"//a[@class='menu-item-label' and @data-label[contains(.,'nuevo')] and @data-label[contains(.,'mujer')]]";   
    static String XPathCarruselNuevoHombreLink = 
    	XPathCapa2onLevelMenu + 
    	"//a[@class='menu-item-label' and @data-label[contains(.,'nuevo')] and @data-label[contains(.,'hombre')]]";
    static String XPathCarruselNuevoNinaLink = 
    	XPathCapa2onLevelMenu + 
    	"//a[@class='menu-item-label' and @data-label[contains(.,'nuevo')] and @data-label[contains(.,'nina')]]";
    static String XPathCarruselNuevoNinoLink = 
    	XPathCapa2onLevelMenu + 
    	"//a[@class='menu-item-label' and @data-label[contains(.,'nuevo')] and @data-label[contains(.,'nino')]]";
    
    static String XPathLinkMenuVisibleFromLi = 
    	"//ul[@class='section-detail' or @class='dropdown-menu']" +
    	"/li[not(@class[contains(.,'mobile-label-hidden')] or @class[contains(.,' gap ')])]" +
    	"/a[@class='menu-item-label' and @href]";
    
    /**
     * @return retornamos el XPATH correspondiente al link de cada una de las líneas (she, he, kids...)
     */
    public static String getXPathLineaLink(LineaType lineaType, AppEcom appE) {
        switch (lineaType) {
        case rebajas:
            return XPathLinkLineaRebajas;
        case nuevo:
            return XPathLinkLineaNuevo + " | " + XPathLinkLineaNewFor388and743;           
        case she: 
            if (appE==AppEcom.outlet) {
                return XPathLinkLineaMujerOutlet;
            }
            return XPathLinkLineaMujerShop;
        case he: 
            if (appE==AppEcom.outlet) {
                return XPathLinkLineaHombreOutlet;
            }
            return XPathLinkLineaHombreShop;
        case nina:
            if (appE==AppEcom.outlet) {
                return XPathLinkLineaNinaOutlet;
            }
            return XPathLinkLineaNinaShop;
        case nino: 
            if (appE==AppEcom.outlet) {
                return XPathLinkLineaNinoOutlet;
            }
            return XPathLinkLineaNinoShop;
        case kids: 
            return XPathLinkLineaKids;
        case violeta: 
            if (appE==AppEcom.outlet) {
                return XPathLinkLineaVioletaOutlet;
            }
            return XPathLinkLineaVioletaShop;
        case edits:
        default:
            return XPathLinkLineaEdits;
        }
    }
    
    public static String getXPathLiLinea(LineaType lineaType, AppEcom appE) {
        String xpathLinkLinea = getXPathLineaLink(lineaType, appE);        
        return (xpathLinkLinea + "/..");
    }
    
    public static String getXPathSublineaNinosLink(SublineaNinosType sublineaType) {
        switch (sublineaType) {
        case nina:
        	return XPathLinkSublineaNina;
        case bebe_nina:
            return XPathLinkSublineaBebeNina;
        case nino:
        	return XPathLinkSublineaNino;
        case bebe_nino:
        default:
            return XPathLinkSublineaBebeNino;
        }
    }
    
    public static String getXPathLiSublineaNinos(SublineaNinosType sublineaType) {
        String xpathLinkSublinea = getXPathSublineaNinosLink(sublineaType);        
        return (xpathLinkSublinea + "/..");
    }
    
    public static String getXPathBlockSublineasNinos(SublineaNinosType sublineaType) {
        String xpathSublineaLi = getXPathLiSublineaNinos(sublineaType);        
        return (xpathSublineaLi + "/..");
    }
    
    public static String getXPathSublineaRebajasLink(LineaType lineaType) {
        switch (lineaType) {
        case she:
            return XPathLinkSublineaRebajasMujer;
        case he:
            return XPathLinkSublineaRebajasHombre;            
        case nina:
            return XPathLinkSublineaRebajasNina;            
        case nino:
            return XPathLinkSublineaRebajasNino;            
        case violeta:
            return XPathLinkSublineaRebajasVioleta;            
        default:
            return null;
        }
    }
    
    public static String getXPathBlockMenusSublineaRebajas(LineaType lineaType) {
        String xpathLinkSublinea = getXPathSublineaRebajasLink(lineaType);        
        return (xpathLinkSublinea + "/..");
    }
    
    public static boolean isSublineaRebajasAssociated(LineaType lineaType) {
        return (getXPathSublineaRebajasLink(lineaType)!=null);
    }    
    
    public static String getXPathCarruselNuevo(LineaType lineaType) {
        switch (lineaType) {
        case she:
            return XPathCarruselNuevoMujerLink;
        case he:
            return XPathCarruselNuevoHombreLink;
        case nina:
            return XPathCarruselNuevoNinaLink;
        case nino:
            return XPathCarruselNuevoNinoLink;
        default:
            return null;
        }
    }
    
    public static boolean isCarruselNuevoAssociated(LineaType lineaType) {
        return (getXPathCarruselNuevo(lineaType)!=null);
    }
    
    public static String getXPathLinksMenus(SublineaNinosType sublineaType) {
        if (sublineaType==null) {
            return XPathCapa2onLevelMenu + XPathLinkMenuVisibleFromLi;
        }
        
        String divSublineaNinos = getXPathSublineaNinosLink(sublineaType); 
        return divSublineaNinos + "/.." + XPathLinkMenuVisibleFromLi;
    }
    
    public static String getXPathMenuByTypeLocator(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) {
        String xpath2oLevelMenuLink = getXPathLinksMenus(menu1rstLevel.getSublinea());
        switch (typeLocator) {
        case dataGaLabelPortion:
            return xpath2oLevelMenuLink.replace("@href", "@data-label[contains(.,\"" + menu1rstLevel.getDataGaLabelMenuSuperiorDesktop().toLowerCase() + "\")]");            
        case hrefPortion:
        default:
            return xpath2oLevelMenuLink.replace("@href", "@href[contains(.,'" + menu1rstLevel.getDataGaLabelMenuSuperiorDesktop().toLowerCase() + "')]");
        }
    }
    
    public static void selectLinea(Linea linea, SublineaNinosType sublineaType, AppEcom appE, WebDriver driver) 
    throws Exception {
        if (sublineaType==null) {
            selecLinea(linea, appE, driver);
        } else {
            selecSublineaNinosIfNotSelected(linea, sublineaType, appE, driver);
        }
    }
    
    public static void selecLinea(Linea linea, AppEcom appE, WebDriver driver) throws Exception {
    	boolean toOpenMenus = true;
    	SecCabeceraMobil secCabeceraMobil = (SecCabeceraMobil)SecCabecera.getNew(Channel.movil_web, appE, driver);
        secCabeceraMobil.clickIconoMenuHamburguer(toOpenMenus);        
        if ("n".compareTo(linea.getExtended())==0) { 
            //if (!isSelectedLinea(linea.getType(), appE, driver)) {
                clickAndWaitLoad(driver, By.xpath(getXPathLineaLink(linea.getType(), appE)), TypeOfClick.javascript);
            //}
        }
    }
    
    public static boolean isLineaPresent(LineaType lineaType, AppEcom appE, WebDriver driver) {
        String xpathLinea = getXPathLineaLink(lineaType, appE);
        return (isElementPresent(driver, By.xpath(xpathLinea)));
    }
    
    public static boolean isLineaPresentUntil(LineaType lineaType, AppEcom appE, int maxSeconds, WebDriver driver) {
        String xpathLinea = getXPathLineaLink(lineaType, appE);
        return (isElementPresentUntil(driver, By.xpath(xpathLinea), maxSeconds));
    }    
    
    public static boolean isSelectedLinea(LineaType lineaType, AppEcom appE, WebDriver driver) {
        String xpathLinea = getXPathLiLinea(lineaType, appE);
        if (isElementPresent(driver, By.xpath(xpathLinea))) {
            return (driver.findElement(By.xpath(xpathLinea)).getAttribute("class").contains("selected"));
        }
        return false;
    }
    
    public static boolean isMenus2onLevelDisplayed(SublineaNinosType sublineaType, WebDriver driver) {
        String xpath2oLevelMenuLink = getXPathLinksMenus(sublineaType);
        return (isElementVisible(driver, By.xpath(xpath2oLevelMenuLink)));
    }
    
    public static void selecSublineaNinosIfNotSelected(Linea linea, SublineaNinosType sublineaType, AppEcom appE, WebDriver driver) throws Exception {
        selecLinea(linea, appE, driver);
        if (!isSelectedSublineaNinos(sublineaType, driver)) {
            clickAndWaitLoad(driver, By.xpath(getXPathSublineaNinosLink(sublineaType)), TypeOfClick.javascript);
        }
    }
    
    public static boolean isSelectedSublineaNinos(SublineaNinosType sublineaNinosType, WebDriver driver) {
        String xpathSublinea = getXPathLiSublineaNinos(sublineaNinosType);
        if (isElementPresent(driver, By.xpath(xpathSublinea))) {
            return (driver.findElement(By.xpath(xpathSublinea)).getAttribute("class").contains("open"));
        }
        return false;
    }
    
    public static boolean isVisibleBlockSublineasNinos(LineaType lineaNinosType, WebDriver driver) {
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
        
        return (isElementVisible(driver, By.xpath(xpathBlockSublineas)));
    }

    public static boolean isCarruselNuevoVisible(LineaType lineaType, WebDriver driver) {
        String xpathCarrusel = getXPathCarruselNuevo(lineaType);
        return (isElementVisible(driver, By.xpath(xpathCarrusel)));
    }
    
    public static void clickCarruselNuevo(Linea lineaNuevo, LineaType lineaType, AppEcom appE, WebDriver driver) throws Exception {
        selecLinea(lineaNuevo, appE, driver);
        String xpathCarrusel = getXPathCarruselNuevo(lineaType);
        if (xpathCarrusel!=null) {
            clickAndWaitLoad(driver, By.xpath(xpathCarrusel), TypeOfClick.javascript);
        }
    }
    
    public static boolean isSublineaRebajasVisible(LineaType lineaType, WebDriver driver) {
        String xpathSublinea = getXPathSublineaRebajasLink(lineaType);
        return (isElementVisible(driver, By.xpath(xpathSublinea)));
    }
    
    public static void clickSublineaRebajas(Linea lineaRebajas, LineaType lineaType, AppEcom appE, WebDriver driver) 
    throws Exception {
        selecLinea(lineaRebajas, appE, driver);
        String xpathSublinea = getXPathSublineaRebajasLink(lineaType);
        if (xpathSublinea!=null) {
            clickAndWaitLoad(driver, By.xpath(xpathSublinea));
        }
    }    
    
    public static boolean isVisibleMenuSublineaRebajas(LineaType lineaType, WebDriver driver) {
        String xpathMenus = getXPathBlockMenusSublineaRebajas(lineaType);
        if (isElementVisible(driver, By.xpath(xpathMenus))) {
            return (driver.findElement(By.xpath(xpathMenus)).getAttribute("class").contains("open"));
        }
        return false;    
    }
    
    /**
     * Obtiene la lista con las entradas de menú de una línea concreta
     */
    public static List<WebElement> getListMenusAfterSelectLinea(Linea linea, SublineaNinosType sublineaType, AppEcom appE, WebDriver driver) 
    throws Exception {
        selectLinea(linea, sublineaType, appE, driver);
        String xpath2oLevelMenuLink = getXPathLinksMenus(sublineaType);
        return (getElementsVisible(driver, By.xpath(xpath2oLevelMenuLink)));
    }
    
    public static List<String> getListDataLabelsMenus(Linea linea, SublineaNinosType sublineaType, AppEcom appE, WebDriver driver) 
    throws Exception {
        List<WebElement> listMenus = getListMenusAfterSelectLinea(linea, sublineaType, appE, driver);
        List<String> listMenusLabel = new ArrayList<>();
        for (int i=0; i<listMenus.size(); i++) {
            String data_ga_label = listMenus.get(i).getAttribute("data-label");
            if (data_ga_label!=null && data_ga_label.compareTo("")!=0) {
                listMenusLabel.add(data_ga_label);
            }
        }
        
        return listMenusLabel;
    }
    
    public static void clickMenuYetDisplayed(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel, WebDriver driver) throws Exception {
        String xpathMenu = getXPathMenuByTypeLocator(typeLocator, menu1rstLevel);
        clickAndWaitLoad(driver, By.xpath(xpathMenu), TypeOfClick.javascript);
    }
    
    public static String getLiteralMenuVisible(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel, WebDriver driver) {
        By byMenu = By.xpath(getXPathMenuByTypeLocator(typeLocator, menu1rstLevel));
        moveToElement(byMenu, driver);
        if (isElementVisible(driver, byMenu)) {
            return driver.findElement(byMenu).getAttribute("innerHTML");
        }
        return "";
    }
    
    /**
     * @return indicador de si el menú lateral de móvil está visible
     */
    public static boolean isMenuInStateUntil(boolean open, int maxSecondsToWait, AppEcom appE, WebDriver driver) {
        try {
            String xpathLineaShe = getXPathLineaLink(LineaType.she, appE);
            String xpathLineaNuevo = getXPathLineaLink(LineaType.nuevo, appE);
            String xpathValidation = xpathLineaShe + " | " + xpathLineaNuevo;
            if (open) {
                return (isElementVisibleUntil(driver, By.xpath(xpathValidation), maxSecondsToWait));
            }
            return (isElementInvisibleUntil(driver, By.xpath(xpathValidation), maxSecondsToWait));
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
    public static void clickMenuLateral1rstLevel(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel, Pais pais, WebDriver driver) 
    throws Exception {
    	AppEcom appMenu = menu1rstLevel.getApp();
        Linea linea = pais.getShoponline().getLinea(menu1rstLevel.getLinea());
        selectLinea(linea, menu1rstLevel.getSublinea(), appMenu, driver);
        menu1rstLevel.setNombre(getLiteralMenuVisible(typeLocator, menu1rstLevel, driver));
        clickMenuYetDisplayed(typeLocator, menu1rstLevel, driver);
    }
    
    public static void bringHeaderMobileBackground(WebDriver driver) throws Exception {
    	WebElement menuWrapp = driver.findElement(By.xpath(XPathHeaderMobile));
    	((JavascriptExecutor) driver).executeScript("arguments[0].style.zIndex=1;", menuWrapp);
    	Thread.sleep(500);
    }
}