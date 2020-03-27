package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.TiendaMantoEnum.TiendaManto;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Implementa la "API" de interacción con la página de "Selección de la tienda en Manto" (la posterior al login) 
 * @author jorge.munoz
 *
 */
public class PageSelTda {

    static String XPathCeldaTextSelectEntorno = "//td[text()[contains(.,'Seleccion de Entorno')]]";
    
    /**
     * @param tienda
     * @return el xpath correspondiente al link de una tienda/almacén concreto (Alemania, Europa Palau...)
     */
    public static String getXpath_linkTienda(TiendaManto tienda) {
        return ("//a[text()[contains(.,'" + tienda.litPantManto + "')]]");
    }

	public static boolean isPage(WebDriver driver) {
		return (state(Present, By.xpath(XPathCeldaTextSelectEntorno), driver).check());
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
