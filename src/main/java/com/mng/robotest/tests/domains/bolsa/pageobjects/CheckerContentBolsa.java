package com.mng.robotest.tests.domains.bolsa.pageobjects;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.bolsa.pageobjects.LineasArticuloBolsa.DataArtBolsa;
import com.mng.robotest.testslegacy.data.Constantes;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

import static com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsaBase.StateBolsa.*;

public class CheckerContentBolsa extends PageBase {
	
	private final SecBolsaBase secBolsa = new SecBolsa();
	
	private final List<ArticuloScreen> linesArticlesExpected;
	private final List<ArticuloDataBolsaScreen> linesArticlesInScreen = new ArrayList<>();

	public CheckerContentBolsa() throws Exception {
		super();
		this.linesArticlesExpected = dataTest.getDataBag().getListArticlesTypeViewInBolsa();
		storeArticlesFromScreen();
	}
	
	private void storeArticlesFromScreen() {
		linesArticlesInScreen.clear();
		secBolsa.setBolsaToStateIfNotYet(OPEN);
		int numArticles = secBolsa.getLineasArtBolsa().getNumLinesArticles();
		for (int i=1; i<=numArticles; i++) {
			var dataArtScreen = secBolsa.getLineasArtBolsa().getArticuloDataByPosicion(i);
			linesArticlesInScreen.add(dataArtScreen);
		}	
	}
	
	public boolean articlesMatch() {
		if (!numArticlesIsCorrect()) {
			return false;
		}
		var allDataToMatch = DataArtBolsa.getValues();
		return (allArticlesExpectedDataAreInScreen(allDataToMatch));
	}
	
	public boolean numArticlesIsCorrect() {
		return (linesArticlesInScreen.size() == linesArticlesExpected.size());
	}
	
	public boolean allArticlesExpectedDataAreInScreen(List<DataArtBolsa> listDataToMatch) {
		for (ArticuloScreen articleExpected : linesArticlesExpected) {
			boolean articleMatches = anyArticleInScreenMatchesArticleData(articleExpected, listDataToMatch);
			if (articleMatches) {
				return true;
			}
		}
		return false;
	}
	
	private boolean anyArticleInScreenMatchesArticleData(ArticuloScreen articleExpected, List<DataArtBolsa> listDataToMatch) {
		var articuloScreen = getArticleInScreenByReference(articleExpected.getReferencia());
		if (articuloScreen==null) {
			return false;
		}
		
		for (DataArtBolsa typeDataToMatch : listDataToMatch) {
			if (DataArtBolsa.getValues().contains(typeDataToMatch) &&
			    articleInScreenMatchArticleData(articleExpected, articuloScreen, typeDataToMatch)) {
				return true;
			}
		}
		return false;
	}

	private boolean articleInScreenMatchArticleData(ArticuloScreen articleExpected, ArticuloDataBolsaScreen articuloScreen, DataArtBolsa typeDataToMatch) {
		switch (typeDataToMatch) {
		case REFERENCIA:
			break;
		case NOMBRE:
			if (!articleExpected.getNombre().contains(articuloScreen.getNombre())) {
				return false;
			}
			break;
		case COLOR:
			if (articleExpected.getColorName().toLowerCase().compareTo(articuloScreen.getColor().toLowerCase())!=0 &&
				articleExpected.getColorName().compareTo(Constantes.COLOR_DESCONOCIDO)!=0) {
				return false;
			}
			break;
		case TALLA:
			if (articleExpected.getTalla()!=articuloScreen.getTalla()) {
				return false;
			}
			break;					
		case CANTIDAD:
			if (articleExpected.getNumero()!=Integer.valueOf(articuloScreen.getCantidad())) {
				return false;
			}
			break;
		case PRECIO_TOTAL:
		default:
			float precioArticulosExpected = articleExpected.getPrecioDescontado() * articleExpected.getNumero();
			if (precioArticulosExpected!=articuloScreen.getPrecio()) {
				return false;
			}
			break;
		}
		return true;
	}
	
	private ArticuloDataBolsaScreen getArticleInScreenByReference(String reference) {
		for (var articuloScreen : linesArticlesInScreen) {
			if (articuloScreen.getReferencia().equals(reference)) {
				return articuloScreen;
			}
		}
		return null;
	}
}
