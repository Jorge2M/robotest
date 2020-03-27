package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.utils.checkmenus.DataScreenMenu;

public class SecMenuLateralMobil extends PageObjTM {
	
	private final AppEcom app;
	private final SecMenusUserMobil secUserMenu;
    
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
    
    private SecMenuLateralMobil(AppEcom app, WebDriver driver) {
    	super(driver);
    	this.app = app;
    	this.secUserMenu = SecMenusUserMobil.getNew(app, driver);
    }
    
    public static SecMenuLateralMobil getNew(AppEcom app, WebDriver driver) {
    	return (new SecMenuLateralMobil(app, driver));
    }
    
    public SecMenusUserMobil getUserMenu() {
    	return secUserMenu;
    }
    
    /**
     * @return retornamos el XPATH correspondiente al link de cada una de las líneas (she, he, kids...)
     */
    private String getXPathLineaLink(LineaType lineaType) {
        switch (lineaType) {
        case rebajas:
            return XPathLinkLineaRebajas;
        case nuevo:
            return XPathLinkLineaNuevo + " | " + XPathLinkLineaNewFor388and743;           
        case she: 
            if (app==AppEcom.outlet) {
                return XPathLinkLineaMujerOutlet;
            }
            return XPathLinkLineaMujerShop;
        case he: 
            if (app==AppEcom.outlet) {
                return XPathLinkLineaHombreOutlet;
            }
            return XPathLinkLineaHombreShop;
        case nina:
            if (app==AppEcom.outlet) {
                return XPathLinkLineaNinaOutlet;
            }
            return XPathLinkLineaNinaShop;
        case nino: 
            if (app==AppEcom.outlet) {
                return XPathLinkLineaNinoOutlet;
            }
            return XPathLinkLineaNinoShop;
        case kids: 
            return XPathLinkLineaKids;
        case violeta: 
            if (app==AppEcom.outlet) {
                return XPathLinkLineaVioletaOutlet;
            }
            return XPathLinkLineaVioletaShop;
        case edits:
        default:
            return XPathLinkLineaEdits;
        }
    }
    
    private String getXPathLiLinea(LineaType lineaType) {
        String xpathLinkLinea = getXPathLineaLink(lineaType);        
        return (xpathLinkLinea + "/..");
    }
    
    private String getXPathSublineaNinosLink(SublineaNinosType sublineaType) {
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
    
    private String getXPathLiSublineaNinos(SublineaNinosType sublineaType) {
        String xpathLinkSublinea = getXPathSublineaNinosLink(sublineaType);        
        return (xpathLinkSublinea + "/..");
    }
    
    private String getXPathBlockSublineasNinos(SublineaNinosType sublineaType) {
        String xpathSublineaLi = getXPathLiSublineaNinos(sublineaType);        
        return (xpathSublineaLi + "/..");
    }
    
    private String getXPathSublineaRebajasLink(LineaType lineaType) {
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
    
    private String getXPathBlockMenusSublineaRebajas(LineaType lineaType) {
        String xpathLinkSublinea = getXPathSublineaRebajasLink(lineaType);        
        return (xpathLinkSublinea + "/..");
    }
    
    public boolean isSublineaRebajasAssociated(LineaType lineaType) {
        return (getXPathSublineaRebajasLink(lineaType)!=null);
    }    
    
    private String getXPathCarruselNuevo(LineaType lineaType) {
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
    
    public boolean isCarruselNuevoAssociated(LineaType lineaType) {
        return (getXPathCarruselNuevo(lineaType)!=null);
    }
    
    private String getXPathLinksMenus(SublineaNinosType sublineaType) {
        if (sublineaType==null) {
            return XPathCapa2onLevelMenu + XPathLinkMenuVisibleFromLi;
        }
        
        String divSublineaNinos = getXPathSublineaNinosLink(sublineaType); 
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
    
    public void selectLinea(Linea linea, SublineaNinosType sublineaType) throws Exception {
        if (sublineaType==null) {
            selecLinea(linea);
        } else {
            selecSublineaNinosIfNotSelected(linea, sublineaType);
        }
    }
    
    public void selecLinea(Linea linea) throws Exception {
    	boolean toOpenMenus = true;
    	SecCabecera secCabecera = SecCabecera.getNew(Channel.movil_web, app, driver);
        secCabecera.clickIconoMenuHamburguerMobil(toOpenMenus);        
        if ("n".compareTo(linea.getExtended())==0) { 
            //if (!isSelectedLinea(linea.getType(), appE, driver)) {
                clickAndWaitLoad(driver, By.xpath(getXPathLineaLink(linea.getType())), TypeOfClick.javascript);
            //}
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
        String xpathLinea = getXPathLiLinea(lineaType);
        if (state(Present, By.xpath(xpathLinea)).check()) {
            return (driver.findElement(By.xpath(xpathLinea)).getAttribute("class").contains("selected"));
        }
        return false;
    }
    
    public boolean isMenus2onLevelDisplayed(SublineaNinosType sublineaType) {
    	List<WebElement> listMenus = getListMenusDisplayed(sublineaType);
    	return (listMenus!=null && listMenus.size()>0);
    }
    
    public void selecSublineaNinosIfNotSelected(Linea linea, SublineaNinosType sublineaType) throws Exception {
        selecLinea(linea);
        if (!isSelectedSublineaNinos(sublineaType)) {
            clickAndWaitLoad(driver, By.xpath(getXPathSublineaNinosLink(sublineaType)), TypeOfClick.javascript);
        }
    }
    
    public boolean isSelectedSublineaNinos(SublineaNinosType sublineaNinosType) {
        String xpathSublinea = getXPathLiSublineaNinos(sublineaNinosType);
        if (state(Present, By.xpath(xpathSublinea)).check()) {
            return (driver.findElement(By.xpath(xpathSublinea)).getAttribute("class").contains("open"));
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

    public boolean isCarruselNuevoVisible(LineaType lineaType) {
        String xpathCarrusel = getXPathCarruselNuevo(lineaType);
        return (state(Visible, By.xpath(xpathCarrusel)).check());
    }
    
    public void clickCarruselNuevo(Linea lineaNuevo, LineaType lineaType) throws Exception {
        selecLinea(lineaNuevo);
        String xpathCarrusel = getXPathCarruselNuevo(lineaType);
        if (xpathCarrusel!=null) {
            clickAndWaitLoad(driver, By.xpath(xpathCarrusel), TypeOfClick.javascript);
        }
    }
    
    public boolean isSublineaRebajasVisible(LineaType lineaType) {
        String xpathSublinea = getXPathSublineaRebajasLink(lineaType);
        return (state(Visible, By.xpath(xpathSublinea)).check());
    }
    
    public void clickSublineaRebajas(Linea lineaRebajas, LineaType lineaType) throws Exception {
        selecLinea(lineaRebajas);
        String xpathSublinea = getXPathSublineaRebajasLink(lineaType);
        if (xpathSublinea!=null) {
            clickAndWaitLoad(driver, By.xpath(xpathSublinea));
        }
    }    
    
    public boolean isVisibleMenuSublineaRebajas(LineaType lineaType) {
        String xpathMenus = getXPathBlockMenusSublineaRebajas(lineaType);
        if (state(Visible, By.xpath(xpathMenus)).check()) {
            return (driver.findElement(By.xpath(xpathMenus)).getAttribute("class").contains("open"));
        }
        return false;
    }
    
    /**
     * Obtiene la lista con las entradas de menú de una línea concreta
     */
    public List<WebElement> getListMenusAfterSelectLinea(Linea linea, SublineaNinosType sublineaType) throws Exception {
        selectLinea(linea, sublineaType);
        return (getListMenusDisplayed(sublineaType));
    }
    
    private List<WebElement> getListMenusDisplayed(SublineaNinosType sublineaType) {
	    String xpath2oLevelMenuLink = getXPathLinksMenus(sublineaType);
	    return (getElementsVisible(driver, By.xpath(xpath2oLevelMenuLink)));
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
    
    public void clickMenuYetDisplayed(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) throws Exception {
        String xpathMenu = getXPathMenuByTypeLocator(typeLocator, menu1rstLevel);
        clickElementVisibleAndWaitLoad(driver, By.xpath(xpathMenu), 0, TypeOfClick.javascript);
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
    
    /**
     * @return indicador de si el menú lateral de móvil está visible
     */
    public boolean isMenuInStateUntil(boolean open, int maxSeconds) {
        try {
            String xpathLineaShe = getXPathLineaLink(LineaType.she);
            String xpathLineaNuevo = getXPathLineaLink(LineaType.nuevo);
            String xpathValidation = xpathLineaShe + " | " + xpathLineaNuevo;
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
    public void clickMenuLateral1rstLevel(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel, Pais pais) throws Exception {
        Linea linea = pais.getShoponline().getLinea(menu1rstLevel.getLinea());
        selectLinea(linea, menu1rstLevel.getSublinea());
        //menu1rstLevel.setNombre(getLiteralMenuVisible(typeLocator, menu1rstLevel));
        clickMenuYetDisplayed(typeLocator, menu1rstLevel);
    }
    
    public void bringHeaderMobileBackground() throws Exception {
    	WebElement menuWrapp = driver.findElement(By.xpath(XPathHeaderMobile));
    	((JavascriptExecutor) driver).executeScript("arguments[0].style.zIndex=1;", menuWrapp);
    	Thread.sleep(500);
    }
}