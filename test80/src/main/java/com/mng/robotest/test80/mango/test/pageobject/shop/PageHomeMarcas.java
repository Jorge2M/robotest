package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;


public class PageHomeMarcas {

    /**
     * Nos dice si está apareciendo la home de marcas o multimarcas correctamente en base al país/líenas
     */
    public static boolean isHomeMarcasMultimarcasDependingCountry(Pais pais, AppEcom app, WebDriver driver) {
        //Comprobamos el número de líneas e incluimos una excepción en Chile/Perú/Paraguay (códigos 512/504/520) el cual tiene 4 líneas pero se define como "home"
        int numLineas = pais.getShoponline().getNumLineasTiendas(app);
        if (numLineas < 3 || pais.getCodigo_pais().equals("512") || pais.getCodigo_pais().equals("504") || pais.getCodigo_pais().equals("520")) {
            if(!driver.getPageSource().contains("home")) {
                return false;
            }
        }  else {
            if (!driver.getPageSource().contains("homeMarcas")) {
                return false;
            }
        }
        
        return true;
    }
}
