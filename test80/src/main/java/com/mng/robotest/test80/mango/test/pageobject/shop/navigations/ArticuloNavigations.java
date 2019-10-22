package com.mng.robotest.test80.mango.test.pageobject.shop.navigations;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecDataProduct.ColorType;

@SuppressWarnings({"static-access"})
public class ArticuloNavigations {

    /**
     * Selecciona un artículo disponible a partir de su referencia (selecciona una talla/color que esté disponible)
     */
    public static ArticuloScreen selectArticuloTallaColorByRef(ArticleStock articleStock, AppEcom app, Channel channel, WebDriver driver) throws Exception {
        ArticuloScreen articulo = new ArticuloScreen();
        articulo.setReferencia(articleStock.getReference());
        buscarArticulo(articleStock, channel, app, driver);

        //Esperamos un máximo de 10 segundos a que aparezca la ficha del artículo
        PageFicha pageFicha = PageFicha.newInstance(channel, app, driver);
        int maxSecondsToWait = 10;
        pageFicha.isFichaArticuloUntil(articulo.getReferencia(), maxSecondsToWait);

        //Selección del COLOR 
        if (articleStock.getColourCode()!=null && "".compareTo(articleStock.getColourCode())!=0) {
            //Si existe, seleccionamos la pastilla de color correspondiente al código de color
            if (pageFicha.secDataProduct.isClickableColor(articleStock.getColourCode(), driver)) {
                pageFicha.secDataProduct.selectColorWaitingForAvailability(articleStock.getColourCode(), driver);
            }
            
            articulo.setCodigoColor(articleStock.getColourCode());
        } else {
        	pageFicha.secDataProduct.clickIfPossibleAndWait(ColorType.Available, driver);
            articulo.setCodigoColor(pageFicha.secDataProduct.getCodeColor(ColorType.Selected, driver));
        }
        
        // Si existe, almacenamos el color para posteriores validaciones
        articulo.setColorName(pageFicha.secDataProduct.getNombreColorSelected(channel, driver));

        //Selección de la TALLA
        if (articleStock.getSize()!=null && "".compareTo(articleStock.getSize())!=0) {
            //Seleccionamos la talla que nos llega en selArticulo (en formato número)
            pageFicha.selectTallaByValue(articleStock.getSize());
        } else {
            // Seleccionamos la 1a talla disponible
            pageFicha.selectFirstTallaAvailable();
        }
        
        //Comprobamos si aparece el modal de stock o no
        if (pageFicha.isModalNoStockVisible(3/*seconds*/)) {
        	ManagerArticlesStock manager = new ManagerArticlesStock(app);
        	manager.deleteArticle(articleStock.getReference());
        }	

        //Almacenamos los 2 valores de la talla seleccionada
        articulo.setTallaAlf(pageFicha.getTallaAlfSelected());
        articulo.setTallaNum(pageFicha.getTallaNumSelected());
    
        // Almacenamos el precio para futuras validaciones (hemos de contemplar todos los posibles formatos según el país)
        String precio = "";
        precio = pageFicha.secDataProduct.getPrecioFinalArticulo(driver);
        articulo.setPrecio(precio);

        // Extraemos el precio original tachado (si existe)
        String precioSinDesc = pageFicha.secDataProduct.getPrecioTachadoFromFichaArt(driver);
        
        if (precioSinDesc!=null && "".compareTo(precioSinDesc)!=0) {
            articulo.setPrecioSinDesc(precioSinDesc);
        } else {
            articulo.setPrecioSinDesc(articulo.getPrecio());
        }

        // Almacenamos el nombre de artículo para futuras validaciones
        articulo.setNombre(pageFicha.secDataProduct.getTituloArt(channel, driver));

        return articulo;
    }
    
	public static void buscarArticulo(ArticleStock articulo, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
		SecCabecera.buscarTexto(articulo.getReference(), channel, app, driver);
    	selectColorIfExists(articulo.getColourCode(), app, driver);
    }
    
    @SuppressWarnings("static-access")
    private static void selectColorIfExists(String colourCode, AppEcom app, WebDriver driver) throws Exception {
    	if (colourCode!=null && "".compareTo(colourCode)!=0) {
    		PageFicha pageFicha = PageFicha.newInstance(Channel.desktop, app, driver);
		    if (pageFicha.secDataProduct.isClickableColor(colourCode, driver)) {
		        int maxSecondsToWait = 5;
		        if (pageFicha.isPageUntil(maxSecondsToWait)) {
		        	pageFicha.secDataProduct.selectColorWaitingForAvailability(colourCode, driver);
		        }
	        }
    	}
    }
}
