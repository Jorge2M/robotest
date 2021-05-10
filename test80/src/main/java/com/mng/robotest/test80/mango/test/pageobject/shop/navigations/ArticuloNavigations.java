package com.mng.robotest.test80.mango.test.pageobject.shop.navigations;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Talla;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment.Article;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;

@SuppressWarnings({"static-access"})
public class ArticuloNavigations {

	/**
	 * Selecciona un artículo disponible a partir de su referencia (selecciona una talla/color que esté disponible)
	 */
	public static ArticuloScreen selectArticuloTallaColorByRef(Garment productStock, AppEcom app, Channel channel, WebDriver driver) {
		ArticuloScreen articulo = new ArticuloScreen();
		
		Article articleStock = productStock.getArticleWithMoreStock();
		articulo.setReferencia(articleStock.getGarmentId());
		if (productStock.getUrlFicha()==null || "".compareTo(productStock.getUrlFicha())==0) {
			buscarArticulo(articleStock, channel, app, driver);
		} else {
			driver.get(productStock.getUrlFicha());
		}

		//Esperamos un máximo de 10 segundos a que aparezca la ficha del artículo
		PageFicha pageFicha = PageFicha.newInstance(channel, app, driver);
		pageFicha.isFichaArticuloUntil(articulo.getReferencia(), 10);

		String idColor = null;
		if (articleStock.getColor()!=null) {
			idColor = articleStock.getColor().getId();
		}
		if (pageFicha.getSecDataProduct().isClickableColor(idColor)) {
			pageFicha.getSecDataProduct().selectColorWaitingForAvailability(idColor);
		}
		articulo.setCodigoColor(idColor);

		articulo.setColorName(pageFicha.getSecDataProduct().getNombreColorSelected());
		if (articleStock.getSize()!=null) {
			pageFicha.selectTallaByValue(Talla.getTalla(articleStock.getSize().getId2Digits()));
		} else {
			pageFicha.selectTallaByIndex(1);
		}

		//Almacenamos los 2 valores de la talla seleccionada
		articulo.setTalla(pageFicha.getTallaSelected());

		// Almacenamos el precio para futuras validaciones (hemos de contemplar todos los posibles formatos según el país)
		String precio = "";
		precio = pageFicha.getSecDataProduct().getPrecioFinalArticulo();
		articulo.setPrecio(precio);

		// Extraemos el precio original tachado (si existe)
		String precioSinDesc = pageFicha.getSecDataProduct().getPrecioTachadoFromFichaArt();

		if (precioSinDesc!=null && "".compareTo(precioSinDesc)!=0) {
			articulo.setPrecioSinDesc(precioSinDesc);
		} else {
			articulo.setPrecioSinDesc(articulo.getPrecio());
		}

		// Almacenamos el nombre de artículo para futuras validaciones
		articulo.setNombre(pageFicha.getSecDataProduct().getTituloArt());

		return articulo;
	}

	public static void buscarArticulo(Article article, Channel channel, AppEcom app, WebDriver driver) {
		SecCabecera.buscarTexto(article.getGarmentId(), channel, app, driver);
		if (article.getColor()!=null) {
			selectColorIfExists(article.getColor().getId(), channel, app, driver);
		}
	}

	@SuppressWarnings("static-access")
	private static void selectColorIfExists(String colourCode, Channel channel, AppEcom app, WebDriver driver) {
		if (colourCode!=null && "".compareTo(colourCode)!=0) {
			PageFicha pageFicha = PageFicha.newInstance(channel, app, driver);
			if (pageFicha.getSecDataProduct().isClickableColor(colourCode)) {
				int maxSecondsToWait = 5;
				if (pageFicha.isPageUntil(maxSecondsToWait)) {
					pageFicha.getSecDataProduct().selectColorWaitingForAvailability(colourCode);
				}
			}
		}
	}
}
