package com.mng.robotest.tests.domains.ficha.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.jorge2m.testmaker.service.exceptions.NotFoundException;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.buscador.steps.BuscadorSteps;
import com.mng.robotest.tests.domains.favoritos.pageobjects.PageFavoritos;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.ProductFilter.FilterType;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

public class Fic006 extends TestBase {

	private final Pais liechtenstein = LIECHTENSTEIN.getPais();
	private final FichaSteps fichaSteps;
	
	private final Optional<GarmentCatalog> productOnline;
	private final Optional<GarmentCatalog> produtNoOnlineWithColors;
	private final List<FilterType> filterOnline = Arrays.asList(FilterType.ONLINE);
	private final List<FilterType> filterNoOnlineWithColors = Arrays.asList(FilterType.NO_ONLINE, FilterType.MANY_COLORS);
	
	public Fic006() throws Exception {
		super();
		dataTest.setUserRegistered(true);
		dataTest.setPais(liechtenstein);
		
		var getterProducts = new GetterProducts.Builder(dataTest.getPais().getCodigoAlf(), app, driver)
				.minProducts(200)
				.build();

		fichaSteps = new FichaSteps();
		productOnline = getterProducts.getOne(filterOnline);
		produtNoOnlineWithColors = getterProducts.getOne(filterNoOnlineWithColors);
	}
	
	@Override
	public void execute() throws Exception {
		accessAndClear();
		if (productOnline.isPresent()) {
			articleOnlineTest();
		}
		stopIfNoPresentArticleNoOnlineWithColors();
		articleNoOnlineTest();
	}
	
	private void accessAndClear() throws Exception {
		accessAndClearData();
		PageFavoritos.make(dataTest.getPais(), app).clearAllArticulos();
	}

	private void articleOnlineTest() {
		var articleOnline = Article.getArticleForTest(productOnline.get());
		new BuscadorSteps().searchArticulo(articleOnline, filterOnline);
		fichaSteps.checkLinkDispTiendaInvisible();
	}
	
	private void articleNoOnlineTest() throws Exception {
		var articleNoOnlineWithColors = Article.getArticleForTest(produtNoOnlineWithColors.get());
		new BuscadorSteps().searchArticulo(articleNoOnlineWithColors, filterNoOnlineWithColors);
		var articulo = selectColorAndTalla();
		if (isShop()) {
			checkFavorites();
		}
		if (isDesktop()) {
			checkStickyContent(articulo);
		}
		
		fichaSteps.selectAnadirALaBolsaTallaPrevSiSelected(articulo);
	}

	private ArticuloScreen selectColorAndTalla() throws Exception {
		boolean isTallaUnica = checkClickAddBolsaWithoutSelectTalla();
		var articulo = new ArticuloScreen(produtNoOnlineWithColors.get());
		fichaSteps.selectColorAndSaveData(articulo);
		fichaSteps.selectTallaAndSaveData(articulo);
		ifTallaUnicaClearBolsa(isTallaUnica);
		return articulo;
	}	
	
	private boolean checkClickAddBolsaWithoutSelectTalla() {
		boolean isTallaUnica = fichaSteps.selectAnadirALaBolsaTallaPrevNoSelected();
		fichaSteps.closeTallas();
		return isTallaUnica;
	}
	
	private void checkFavorites() {
		fichaSteps.selectAnadirAFavoritos();
		fichaSteps.changeColorGarment();
		fichaSteps.selectRemoveFromFavoritos();		
	}
	
	private void checkStickyContent(ArticuloScreen articulo) {
		scrollVertical(4000);
		fichaSteps.checkStickyContentVisible(articulo);
		waitMillis(500);
		scrollVertical(-2000);
		waitMillis(500);
		scrollVertical(-2000);
		fichaSteps.checkStickyContentInvisible(2);
	}
	
	private void stopIfNoPresentArticleNoOnlineWithColors() throws NotFoundException {
		if (!produtNoOnlineWithColors.isPresent()) {
			var filtersLabels = 
				filterNoOnlineWithColors
					.stream()
					.map(Object::toString)
					.toList();
			
			throw new NotFoundException("Not found article with filters " + String.join(",", filtersLabels));
		}
	}	
	
	private void ifTallaUnicaClearBolsa(boolean isTallaUnica) throws Exception {
		//Si es talla única -> Significa que lo dimos de alta en la bolsa cuando seleccionamos el click "Añadir a la bolsa"
		//-> Lo damos de baja
		if (isTallaUnica) {
			new SecBolsaSteps().clear();
		}
	}

}
