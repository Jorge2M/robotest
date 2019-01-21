package com.mng.robotest.test80.mango.test.pageobject.shop;

@SuppressWarnings("javadoc")
public class Mensajes {

    /**
     * @return el xpath correspondiente a la capa de "Cargando"
     */
    public static String getXPathCapaCargando() {
        return ("//*[@id='Form:SVLoading:popupLoading' or @class[contains(.,'container-full-centered-loading')]]");
    }
    
}
