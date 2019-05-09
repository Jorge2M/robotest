package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraMobil.Icono;
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
    
    public static SecMenusUserWrap secMenusUser;
    
    public enum bloqueMenu {prendas, accesorios, colecciones}
    
	public static boolean isLineaPresent(LineaType lineaType, AppEcom app, Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            return SecMenuLateralMobil.isLineaPresent(lineaType, app, driver);
        }
        return 
        	SecMenusDesktop.
        		secMenuSuperior.secLineas.isLineaPresent(lineaType, app, driver);
    }
    
    public static boolean isLineaPresentUntil(LineaType lineaType, AppEcom app, Channel channel, int maxSeconds, WebDriver driver) {
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
    public static LineaType getLineaResultAfterClickMenu(LineaType lineaType, String nombre) {
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

    /**
     * Función que ejecuta la identificación del usuario. Introduce las credenciales del usuario y seleccióna el botón de submit
     */
	public static void closeSessionIfUserLogged(Channel channel, AppEcom app, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
        	SecCabeceraMobil secCabeceraMobil = (SecCabeceraMobil)SecCabecera.getNew(Channel.movil_web, app, driver);
        	if (secCabeceraMobil.isVisible(Icono.MiCuenta)) {
        		SecMenuLateralMobil.secMenusUser.clickCerrarSessionIfLinkExists(driver);
        	}
        } else {       
        	SecMenusDesktop.secMenusUser.clickCerrarSessionIfLinkExists(driver);
        }
    }
    
    public static List<String> getListDataLabelsMenus(Linea linea, SublineaNinosType sublineaType, Channel channel, AppEcom appE, WebDriver driver) 
    throws Exception {
        if (channel==Channel.movil_web) {
            return SecMenuLateralMobil.getListDataLabelsMenus(linea, sublineaType, appE, driver);
        }
        return 
        	SecMenusDesktop.
        		secMenuSuperior.secBlockMenus.getListDataLabelsMenus(linea.getType(), sublineaType, appE, driver);        
    }
    
    /**
     * @return codificación que se acostumbra a utilizar para identificar la línea en el DOM
     */
    public static String getLineaDOM(LineaType lineaType, AppEcom app, Channel channel) {
        return (getIdLineaEnDOM(lineaType, app, channel));
    }
    
    /**
     * @return el id con el que se identifica la línea a nivel del DOM-HTML
     */
    public static String getIdLineaEnDOM(LineaType lineaShop, AppEcom app, Channel channel) {
        if (app==AppEcom.outlet) {
            return lineaShop.getSufixOutlet(channel);
        }
        return lineaShop.name();
    }
    
    public static void selecLinea(Pais pais, LineaType lineaType, AppEcom app, Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            SecMenuLateralMobil.selecLinea(pais.getShoponline().getLinea(lineaType), app, driver);
        } else {
            SecMenusDesktop.secMenuSuperior.secLineas.selecLinea(pais, lineaType, app, driver);
        }
    }
    
    public static void selecSublinea(Pais pais, LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, Channel channel, WebDriver driver) throws Exception {
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
    public static void clickMenu1erNivel(Pais pais, Menu1rstLevel menu1rstLevel, AppEcom app, Channel channel, WebDriver driver) 
    throws Exception {
        if (channel==Channel.desktop) {
            SecMenusDesktop.secMenuSuperior.secBlockMenus.clickMenuAndGetName(menu1rstLevel, app, driver);
        } else {
        	SecMenuLateralMobil.clickMenuLateral1rstLevel(TypeLocator.dataGaLabelPortion, menu1rstLevel, pais, driver);
        }
    }
    
    /**
     * Función que selecciona una determinada línea->menú (lo busca en el href como último elemento del path)
     */
    public static void seleccionarMenuXHref(Menu1rstLevel menu1rstLevel, Pais pais, Channel channel, WebDriver driver) throws Exception {
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
    public static List<WebElement> linkMenus2oLevel(WebDriver driver, String datagalabel_MenuSuperior) {
        String menuSupNorm = datagalabel_MenuSuperior.replace("-", ":");
        return (driver.findElements(By.xpath("//a[@data-ga-category='filtros' and @data-ga-label[contains(.,'" + menuSupNorm + "')]]")));
    }
    
    public static boolean canClickMenuArticles(Pais paisI, Linea linea, Sublinea sublinea) {
        if (paisI.getShop_online().compareTo("true")==0) {
            if (sublinea==null) {
                return (linea.getMenusart().compareTo("s")==0);
            }
            return sublinea.getMenusart().compareTo("s")==0;
        }
        
        return false;
    }
}

