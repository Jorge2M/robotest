package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.TiendaMantoEnum.TiendaManto;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
/**
 * Implementa la "API" de interacción con la página de "Selección de la tienda en Manto" (la posterior al login) 
 * @author jorge.munoz
 *
 */
public class PageSelTda extends WebdrvWrapp {

    static String XPathCeldaTextSelectEntorno = "//td[text()[contains(.,'Seleccion de Entorno')]]";
    
    /**
     * @param tienda
     * @return el xpath correspondiente al link de una tienda/almacén concreto (Alemania, Europa Palau...)
     */
    public static String getXpath_linkTienda(TiendaManto tienda) {
        return ("//a[text()[contains(.,'" + tienda.litPantManto + "')]]");
    }
    
    /**
     * @return si realmente estamos en la página
     */
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathCeldaTextSelectEntorno)));
    }
    
    /**
     * Seleccionamos una tienda/almacén concreta de entre las disponibles (Alemania, Europa Palau...)
     * @param tienda
     */
    public static void selectTienda(TiendaManto tienda, WebDriver driver) throws Exception {
        String xpath = getXpath_linkTienda(tienda);
        clickAndWaitLoad(driver, By.xpath(xpath));
    }
}
