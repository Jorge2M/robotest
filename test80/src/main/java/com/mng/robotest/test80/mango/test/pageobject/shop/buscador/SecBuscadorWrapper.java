package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;


public class SecBuscadorWrapper {
	
	public static void buscarArticulo(ArticleStock articulo, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
    	buscarTexto(articulo.getReference(), channel, app, driver);
    	selectColorIfExists(articulo.getColourCode(), app, driver);
    }
    
    @SuppressWarnings("static-access")
    private static void selectColorIfExists(String colourCode, AppEcom app, WebDriver driver) throws Exception {
    	if (colourCode!=null && "".compareTo(colourCode)!=0) {
    		PageFicha pageFicha = PageFicha.newInstance(app, Channel.desktop, driver);
		    if (pageFicha.secDataProduct.isClickableColor(colourCode, driver)) {
		        int maxSecondsToWait = 5;
		        if (pageFicha.isPageUntil(maxSecondsToWait)) {
		        	pageFicha.secDataProduct.selectColorWaitingForAvailability(colourCode, driver);
		        }
	        }
    	}
    }
    
    public static void buscarTexto(String texto, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
    	SecCabecera.getNew(channel, app, driver).buscarReferenciaNoWait(texto);
    }
}
