package com.mng.robotest.testslegacy.pageobject.shop.navigations;

import org.apache.commons.lang3.tuple.Pair;

import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.testslegacy.data.Talla;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

public class ArticuloNavigations extends StepBase {

	private final PageFicha pgFicha = PageFicha.of(channel);
	
	public ArticuloScreen selectArticuloTallaColorByRef(Article articleStock) {
		var articulo = new ArticuloScreen();
		articulo.setReferencia(articleStock.getGarmentId());
		
		searchArticle(articleStock);
		pgFicha.isFichaArticuloUntil(articulo.getReferencia(), 10);

		var color = selectColor(articleStock);
		articulo.setCodigoColor(color.getLeft());
		articulo.setColorName(color.getRight());

		var talla = selectTalla(articleStock);
		articulo.setTalla(talla);

		articulo.setPrecio(pgFicha.getSecDataProduct().getPrecioFinalArticulo());
		articulo.setPrecioSinDesc(getPrecioOriginal(articulo));
		articulo.setNombre(pgFicha.getSecDataProduct().getTituloArt());
		
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
		if (pgFicha.getSecDataProduct().isClickableColor(idColor)) {
			pgFicha.getSecDataProduct().selectColorWaitingForAvailability(idColor);
		}
		String nameColor = pgFicha.getSecDataProduct().getNombreColorSelected();
		return Pair.of(idColor, nameColor);
	}
	
	private Talla selectTalla(Article articleStock) {
		if (articleStock.getSize()!=null) {
			String size = articleStock.getSize().getId2Digits();
			if (articleStock.getSize().getLabel().matches("\\d+")) {
				size = articleStock.getSize().getLabel();
				pgFicha.selectTallaByValue(Talla.fromLabel(size));
			} else {
				pgFicha.selectTallaByValue(Talla.fromValue(size));
			}
		} else {
			pgFicha.selectTallaByIndex(1);
		}
		return pgFicha.getTallaSelected();
	}	
	
	private String getPrecioOriginal(ArticuloScreen articulo) {
		String precioSinDesc = pgFicha.getSecDataProduct().getPrecioTachadoFromFichaArt();
		if (precioSinDesc!=null && "".compareTo(precioSinDesc)!=0) {
			return precioSinDesc;
		} else {
			return articulo.getPrecio();
		}
	}

	private void selectColorIfExists(String colourCode) {
		if (colourCode!=null && "".compareTo(colourCode)!=0 &&
			pgFicha.getSecDataProduct().isClickableColor(colourCode) &&
			pgFicha.isPage(5)) {
			pgFicha.getSecDataProduct().selectColorWaitingForAvailability(colourCode);
		}
	}
	
}
