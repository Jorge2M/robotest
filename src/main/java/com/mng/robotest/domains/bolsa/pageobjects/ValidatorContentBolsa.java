package com.mng.robotest.domains.bolsa.pageobjects;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.domains.bolsa.pageobjects.LineasArticuloBolsaCommon.DataArtBolsa;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsaCommon.StateBolsa;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.generic.beans.ArticuloScreen;

public class ValidatorContentBolsa extends PageBase {
	
	private final SecBolsaCommon secBolsa = new SecBolsa();
	
	private final List<ArticuloScreen> linesArticlesExpected;
	private final List<ArticuloDataBolsaScreen> linesArticlesInScreen = new ArrayList<>();

	public ValidatorContentBolsa() throws Exception {
		super();
		this.linesArticlesExpected = dataTest.getDataBag().getListArticlesTypeViewInBolsa();
		storeArticlesFromScreen();
	}
	
	private void storeArticlesFromScreen() throws Exception {
		linesArticlesInScreen.clear();
		secBolsa.setBolsaToStateIfNotYet(StateBolsa.OPEN);
		int numArticles = secBolsa.getLineasArtBolsa().getNumLinesArticles();
		for (int i=1; i<=numArticles; i++) {
			ArticuloDataBolsaScreen dataArtScreen = secBolsa.getLineasArtBolsa().getArticuloDataByPosicion(i);
			linesArticlesInScreen.add(dataArtScreen);
		}	
	}
	
	public boolean articlesMatch() {
		if (!numArticlesIsCorrect()) {
			return false;
		}
		List<DataArtBolsa> allDataToMatch = DataArtBolsa.getValuesValidForChannel(channel);
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
		ArticuloDataBolsaScreen articuloScreen = getArticleInScreenByReference(articleExpected.getReferencia());
		if (articuloScreen==null) {
			return false;
		}
		
		for (DataArtBolsa typeDataToMatch : listDataToMatch) {
			if (DataArtBolsa.getValuesValidForChannel(channel).contains(typeDataToMatch))
				if (articleInScreenMatchArticleData(articleExpected, articuloScreen, typeDataToMatch)) {
					return true;
				}
		}
		
		return true;
	}

	@SuppressWarnings("incomplete-switch")
	private boolean articleInScreenMatchArticleData(ArticuloScreen articleExpected, ArticuloDataBolsaScreen articuloScreen, DataArtBolsa typeDataToMatch) {
		switch (typeDataToMatch) {
		case REFERENCIA:
			break;
		case NOMBRE:
			if (!articleExpected.getNombre().contains(articuloScreen.nombre)) {
				return false;
			}
			break;
		case COLOR:
			if (articleExpected.getColorName().toLowerCase().compareTo(articuloScreen.color.toLowerCase())!=0 &&
				articleExpected.getColorName().compareTo(Constantes.COLOR_DESCONOCIDO)!=0) {
				return false;
			}
			break;
		case TALLA:
			if (articleExpected.getTalla()!=articuloScreen.talla) {
				return false;
			}
			break;					
		case CANTIDAD:
			if (articleExpected.getNumero()!=Integer.valueOf(articuloScreen.cantidad)) {
				return false;
			}
			break;
		case PRECIO_TOTAL:
			float precioArticulosExpected = articleExpected.getPrecioDescontado() * articleExpected.getNumero();
			if (precioArticulosExpected!=articuloScreen.precio) {
				return false;
			}
			break;
		}
		
		return true;
	}
	
	private ArticuloDataBolsaScreen getArticleInScreenByReference(String reference) {
		for (ArticuloDataBolsaScreen articuloScreen : linesArticlesInScreen) {
			if (articuloScreen.referencia.equals(reference)) {
				return articuloScreen;
			}
		}
		
		return null;
	}
}
