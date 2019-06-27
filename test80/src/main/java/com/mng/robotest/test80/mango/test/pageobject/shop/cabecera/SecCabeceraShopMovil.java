package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.buscador.SecBuscadorDesktop;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Cabecera" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class SecCabeceraShopMovil extends SecCabeceraShop {
	
    private SecCabeceraShopMovil(AppEcom app, WebDriver driver) {
    	super(app, driver);
    }
    
    public static SecCabeceraShopMovil getNew(AppEcom app, WebDriver driver) {
    	return (new SecCabeceraShopMovil(app, driver));
    }
    


    @Override
    public void buscarReferenciaNoWait(String referencia) throws Exception {
    	SecBuscadorDesktop.buscarReferenciaNoWait(referencia, driver);
    }
    
    public void moveToLogo(WebDriver driver) {
    	WebElement logo = driver.findElement(By.xpath(XPathLinkLogoMango));
    	moveToElement(logo, driver);
    }
    


}
