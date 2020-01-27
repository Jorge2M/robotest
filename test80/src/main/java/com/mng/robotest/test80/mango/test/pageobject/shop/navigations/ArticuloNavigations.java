package com.mng.robotest.test80.mango.test.pageobject.shop.navigations;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Talla;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getproducts.data.Garment;
import com.mng.robotest.test80.mango.test.getproducts.data.Garment.Article;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;

@SuppressWarnings({"static-access"})
public class ArticuloNavigations {

	/**
	 * Selecciona un artículo disponible a partir de su referencia (selecciona una talla/color que esté disponible)
	 */
	public static ArticuloScreen selectArticuloTallaColorByRef(Garment productStock, AppEcom app, Channel channel, WebDriver driver) 
	throws Exception {
		ArticuloScreen articulo = new ArticuloScreen(app);
		
		Article articleStock = productStock.getArticleWithMoreStock();
		articulo.setReferencia(articleStock.getGarmentId());
		buscarArticulo(articleStock, channel, app, driver);

		//Esperamos un máximo de 10 segundos a que aparezca la ficha del artículo
		PageFicha pageFicha = PageFicha.newInstance(channel, app, driver);
		int maxSecondsToWait = 10;
		pageFicha.isFichaArticuloUntil(articulo.getReferencia(), maxSecondsToWait);

		String idColor = null;
		if (articleStock.getColor()!=null) {
			idColor = articleStock.getColor().getId();
		}
		if (pageFicha.secDataProduct.isClickableColor(idColor, driver)) {
			pageFicha.secDataProduct.selectColorWaitingForAvailability(idColor, driver);
		}
		articulo.setCodigoColor(idColor);

		articulo.setColorName(pageFicha.secDataProduct.getNombreColorSelected(channel, driver));
		if (articleStock.getSize()!=null) {
			pageFicha.selectTallaByValue(Talla.getTalla(articleStock.getSize().getId()));
		} else {
			pageFicha.selectTallaByIndex(1);
		}

		//Almacenamos los 2 valores de la talla seleccionada
		articulo.setTalla(pageFicha.getTallaSelected());

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

	public static void buscarArticulo(Article article, Channel channel, AppEcom app, WebDriver driver) 
	throws Exception {
		SecCabecera.buscarTexto(article.getGarmentId(), channel, app, driver);
		if (article.getColor()!=null) {
			selectColorIfExists(article.getColor().getId(), app, driver);
		}
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
