package com.mng.robotest.test80.mango.test.pageobject.shop.bolsa;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.LineasArticuloBolsa.DataArtBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;

public class ValidatorContentBolsa {
	
	ArrayList<ArticuloScreen> linesArticlesExpected;
	ArrayList<ArticuloDataBolsaScreen> linesArticlesInScreen = new ArrayList<>();
	AppEcom app;
	Channel channel;
	WebDriver driver;
	
	public ValidatorContentBolsa(DataBag contentBagExpected, AppEcom app, Channel channel, WebDriver driver) throws Exception {
		this.linesArticlesExpected = contentBagExpected.getListArticlesTypeViewInBolsa();
		this.app = app;
		this.channel = channel;
		this.driver = driver;
		storeArticlesFromScreen(driver);
	}
	
	@SuppressWarnings("static-access")
	private void storeArticlesFromScreen(WebDriver driver) throws Exception {
		linesArticlesInScreen.clear();
		SecBolsa.setBolsaToState(StateBolsa.Open, channel, app, driver);
		int numArticles = SecBolsa.lineasArticuloBolsa.getNumLinesArticles(channel, driver);
		for (int i=1; i<=numArticles; i++) {
			ArticuloDataBolsaScreen dataArtScreen = SecBolsa.lineasArticuloBolsa.getArticuloDataByPosicion(i, channel, driver);
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
		case Referencia:
			break;
		case Nombre:
			if (!articleExpected.getNombre().contains(articuloScreen.nombre)) {
				return false;
			}
			break;
		case Color:
			if (articleExpected.getColorName().toLowerCase().compareTo(articuloScreen.color.toLowerCase())!=0 &&
				articleExpected.getColorName().compareTo(Constantes.colorDesconocido)!=0) {
				return false;
			}
			break;
		case Talla:
			if (articleExpected.getTalla()!=articuloScreen.talla) {
				return false;
			}
			break;					
		case Cantidad:
			if (articleExpected.getNumero()!=Integer.valueOf(articuloScreen.cantidad)) {
				return false;
			}
			break;
		case PrecioTotal:
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
