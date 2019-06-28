package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil.TypeLocator;

@SuppressWarnings({"static-access"}) 
/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Menús" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class SecMenusWrap {
    
    private final MenusUserWrapper secMenusUser;
    private final Channel channel;
    private final AppEcom app;
    private final WebDriver driver;
    
    public enum bloqueMenu {prendas, accesorios, colecciones}
    
    private SecMenusWrap(Channel channel, AppEcom app, WebDriver driver) {
    	this.channel = channel;
    	this.app = app;
    	this.driver = driver;
    	this.secMenusUser = MenusUserWrapper.getNew(channel, app, driver);
    }
    
    public static SecMenusWrap getNew(Channel channel, AppEcom app, WebDriver driver) {
    	return (new SecMenusWrap(channel, app, driver));
    }
    
    public MenusUserWrapper getMenusUser() {
    	return this.secMenusUser;
    }
    
	public boolean isLineaPresent(LineaType lineaType) {
        if (channel==Channel.movil_web) {
            return SecMenuLateralMobil.isLineaPresent(lineaType, app, driver);
        }
        return 
        	SecMenusDesktop.
        		secMenuSuperior.secLineas.isLineaPresent(lineaType, app, driver);
    }
    
    public boolean isLineaPresentUntil(LineaType lineaType, int maxSeconds) {
        if (channel==Channel.movil_web) {
            return SecMenuLateralMobil.isLineaPresent(lineaType, app, driver);
        }
        return 
        	SecMenusDesktop.
        		secMenuSuperior.secLineas.isLineaPresentUntil(lineaType, app, maxSeconds, driver);
    }    
    
    /**
     * @return la línea a la que se debería acceder cuando se selecciona el menú
     */
    public LineaType getLineaResultAfterClickMenu(LineaType lineaType, String nombre) {
        switch (nombre) {
        case "nuevo":
            if (lineaType==LineaType.she)
                return LineaType.nuevo;
            break;
        case "rebajas":
//        case "-rebajas":            
//            if (lineaType==LineaType.she)
//                return LineaType.rebajas;
//            break;
        default:
            break;
        }
        
        return lineaType;
    }

	public void closeSessionIfUserLogged() throws Exception {
		switch (channel) {
		case movil_web:
        	SecMenuLateralMobil.secMenusUser.clickCerrarSessionIfLinkExists(driver);
        	break;
		default:
		case desktop:
			SecMenusDesktop.secMenusUser.clickCerrarSessionIfLinkExists(driver);
		}
    }
    
    public List<String> getListDataLabelsMenus(Linea linea, SublineaNinosType sublineaType) throws Exception {
        if (channel==Channel.movil_web) {
            return SecMenuLateralMobil.getListDataLabelsMenus(linea, sublineaType, app, driver);
        }
        return 
        	SecMenusDesktop.
        		secMenuSuperior.secBlockMenus.getListDataLabelsMenus(linea.getType(), sublineaType, app, driver);        
    }
    
    /**
     * @return codificación que se acostumbra a utilizar para identificar la línea en el DOM
     */
    public String getLineaDOM(LineaType lineaType) {
        return (getIdLineaEnDOM(lineaType, channel, app));
    }
    
    /**
     * @return el id con el que se identifica la línea a nivel del DOM-HTML
     */
    public static String getIdLineaEnDOM(LineaType lineaShop, Channel channel, AppEcom app) {
        if (app==AppEcom.outlet) {
            return lineaShop.getSufixOutlet(channel);
        }
        return lineaShop.name();
    }
    
    public void selecLinea(Pais pais, LineaType lineaType) throws Exception {
        if (channel==Channel.movil_web) {
            SecMenuLateralMobil.selecLinea(pais.getShoponline().getLinea(lineaType), app, driver);
        } else {
            SecMenusDesktop.secMenuSuperior.secLineas.selecLinea(pais, lineaType, app, driver);
        }
    }
    
    public void selecSublinea(Pais pais, LineaType lineaType, SublineaNinosType sublineaType) throws Exception {
        if (channel==Channel.movil_web) {
            SecMenuLateralMobil.selectLinea(pais.getShoponline().getLinea(lineaType), sublineaType, app, driver);
        } else {
            SecMenusDesktop.secMenuSuperior.secLineas.selectSublinea(lineaType, sublineaType, app, driver);
        }
    }    
    
    /**
     * Selecciona una entrada de menú. Soporta Desktop y Móvil
     *  Desktop: selecciona una entrada del menú superior
     *  Móvil:   selecciona una entrada del menú lateral
     */
    public void clickMenu1erNivel(Pais pais, Menu1rstLevel menu1rstLevel) throws Exception {
        if (channel==Channel.desktop) {
            SecMenusDesktop.secMenuSuperior.secBlockMenus.clickMenuAndGetName(menu1rstLevel, app, driver);
        } else {
        	SecMenuLateralMobil.clickMenuLateral1rstLevel(TypeLocator.dataGaLabelPortion, menu1rstLevel, pais, driver);
        }
    }
    
    /**
     * Función que selecciona una determinada línea->menú (lo busca en el href como último elemento del path)
     */
    public void seleccionarMenuXHref(Menu1rstLevel menu1rstLevel, Pais pais) throws Exception {
        if (channel==Channel.movil_web) {
            SecMenuLateralMobil.clickMenuLateral1rstLevel(TypeLocator.hrefPortion, menu1rstLevel, pais, driver);
        } else {
        	SecMenusDesktop.secMenuSuperior.secBlockMenus.seleccionarMenuXHref(menu1rstLevel, driver);
        }
    }    
    
    /**
     * @param datagalabel_MenuSuperior: el data-ga-label asociado al menú superior
     * @return obtiene los menús laterales de 2o nivel asociados a un menú superior de 1er nivel
     */
    public List<WebElement> linkMenus2oLevel(String datagalabel_MenuSuperior) {
        String menuSupNorm = datagalabel_MenuSuperior.replace("-", ":");
        return (driver.findElements(By.xpath("//a[@data-ga-category='filtros' and @data-ga-label[contains(.,'" + menuSupNorm + "')]]")));
    }
    
    public boolean canClickMenuArticles(Pais paisI, Linea linea, Sublinea sublinea) {
        if (paisI.getShop_online().compareTo("true")==0) {
            if (sublinea==null) {
                return (linea.getMenusart().compareTo("s")==0);
            }
            return sublinea.getMenusart().compareTo("s")==0;
        }
        
        return false;
    }
}

