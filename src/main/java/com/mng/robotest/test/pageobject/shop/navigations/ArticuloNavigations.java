package com.mng.robotest.test.pageobject.shop.navigations;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog.Article;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;

public class ArticuloNavigations {

	/**
	 * Selecciona un artículo disponible a partir de su referencia (selecciona una talla/color que esté disponible)
	 */
	public static ArticuloScreen selectArticuloTallaColorByRef(GarmentCatalog productStock, AppEcom app, Channel channel, WebDriver driver) {
		ArticuloScreen articulo = new ArticuloScreen();

		Article articleStock = productStock.getArticleWithMoreStock();
		articulo.setReferencia(articleStock.getGarmentId());
		if (productStock.getUrlFicha()==null || "".compareTo(productStock.getUrlFicha())==0) {
			buscarArticulo(articleStock, channel, app);
		} else {
			driver.get(productStock.getUrlFicha());
		}

		//Esperamos un máximo de 10 segundos a que aparezca la ficha del artículo
		PageFicha pageFicha = PageFicha.of(channel);
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
			String size = articleStock.getSize().getId2Digits();
			if (articleStock.getSize().getLabel().matches("\\d+")) {
				size = articleStock.getSize().getLabel();
				pageFicha.selectTallaByValue(Talla.fromLabel(size));
			} else {
				pageFicha.selectTallaByValue(Talla.fromValue(size));
			}
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

	public static void buscarArticulo(Article article, Channel channel, AppEcom app) {
		SecCabecera.buscarTexto(article.getGarmentId(), channel, app);
		if (article.getColor()!=null) {
			selectColorIfExists(article.getColor().getId(), channel, app);
		}
	}

	private static void selectColorIfExists(String colourCode, Channel channel, AppEcom app) {
		if (colourCode!=null && "".compareTo(colourCode)!=0) {
			PageFicha pageFicha = PageFicha.of(channel);
			if (pageFicha.getSecDataProduct().isClickableColor(colourCode)) {
				if (pageFicha.isPageUntil(5)) {
					pageFicha.getSecDataProduct().selectColorWaitingForAvailability(colourCode);
				}
			}
		}
	}
}
