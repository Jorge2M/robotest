package com.mng.robotest.test.pageobject.shop.navigations;

import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;

public class ArticuloNavigations extends StepBase {

	public ArticuloScreen selectArticuloTallaColorByRef(Article articleStock) {
		ArticuloScreen articulo = new ArticuloScreen();
		articulo.setReferencia(articleStock.getGarmentId());
		if (articleStock.getUrlFicha()==null || "".compareTo(articleStock.getUrlFicha())==0) {
			buscarArticulo(articleStock);
		} else {
			driver.get(articleStock.getUrlFicha());
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

	public void buscarArticulo(Article article) {
		SecCabecera.buscarTexto(article.getGarmentId(), channel, app);
		if (article.getColor()!=null) {
			selectColorIfExists(article.getColor().getId());
		}
	}

	private void selectColorIfExists(String colourCode) {
		if (colourCode!=null && "".compareTo(colourCode)!=0) {
			PageFicha pageFicha = PageFicha.of(channel);
			if (pageFicha.getSecDataProduct().isClickableColor(colourCode) &&
				pageFicha.isPageUntil(5)) {
				pageFicha.getSecDataProduct().selectColorWaitingForAvailability(colourCode);
			}
		}
	}
}
