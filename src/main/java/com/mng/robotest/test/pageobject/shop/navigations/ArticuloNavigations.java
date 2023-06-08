package com.mng.robotest.test.pageobject.shop.navigations;

import org.apache.commons.lang3.tuple.Pair;

import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;

public class ArticuloNavigations extends StepBase {

	private final PageFicha pageFicha = PageFicha.of(channel);
	
	public ArticuloScreen selectArticuloTallaColorByRef(Article articleStock) {
		var articulo = new ArticuloScreen();
		articulo.setReferencia(articleStock.getGarmentId());
		
		searchArticle(articleStock);
		pageFicha.isFichaArticuloUntil(articulo.getReferencia(), 10);

		var color = selectColor(articleStock);
		articulo.setCodigoColor(color.getLeft());
		articulo.setColorName(color.getRight());

		var talla = selectTalla(articleStock);
		articulo.setTalla(talla);

		articulo.setPrecio(pageFicha.getSecDataProduct().getPrecioFinalArticulo());
		articulo.setPrecioSinDesc(getPrecioOriginal(articulo));
		articulo.setNombre(pageFicha.getSecDataProduct().getTituloArt());
		
		return articulo;
	}
	
	public void buscarArticulo(Article article) {
		SecCabecera.buscarTexto(article.getGarmentId(), channel);
		if (article.getColor()!=null) {
			selectColorIfExists(article.getColor().getId());
		}
	}	

	private void searchArticle(Article articleStock) {
		if (articleStock.getUrlFicha()==null || "".compareTo(articleStock.getUrlFicha())==0) {
			buscarArticulo(articleStock);
		} else {
			driver.get(articleStock.getUrlFicha());
		}
	}

	private Pair<String, String> selectColor(Article articleStock) {
		String idColor = null;
		if (articleStock.getColor()!=null) {
			idColor = articleStock.getColor().getId();
		}
		if (pageFicha.getSecDataProduct().isClickableColor(idColor)) {
			pageFicha.getSecDataProduct().selectColorWaitingForAvailability(idColor);
		}
		String nameColor = pageFicha.getSecDataProduct().getNombreColorSelected();
		return Pair.of(idColor, nameColor);
	}
	
	private Talla selectTalla(Article articleStock) {
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
		return pageFicha.getTallaSelected();
	}	
	
	private String getPrecioOriginal(ArticuloScreen articulo) {
		String precioSinDesc = pageFicha.getSecDataProduct().getPrecioTachadoFromFichaArt();
		if (precioSinDesc!=null && "".compareTo(precioSinDesc)!=0) {
			return precioSinDesc;
		} else {
			return articulo.getPrecio();
		}
	}

	private void selectColorIfExists(String colourCode) {
		if (colourCode!=null && "".compareTo(colourCode)!=0) {
			if (pageFicha.getSecDataProduct().isClickableColor(colourCode) &&
				pageFicha.isPageUntil(5)) {
				pageFicha.getSecDataProduct().selectColorWaitingForAvailability(colourCode);
			}
		}
	}
}
