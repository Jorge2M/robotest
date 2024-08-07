package com.mng.robotest.tests.domains.ficha.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.jorge2m.testmaker.service.exceptions.NotFoundException;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.buscador.steps.BuscadorSteps;
import com.mng.robotest.tests.domains.ficha.steps.ModalBuscarEnTiendaSteps;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.ProductFilter.FilterType;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

import com.github.jorge2m.testmaker.conf.State;

public class Fic001 extends TestBase {

	private final Optional<GarmentCatalog> productOnline;
	private final List<GarmentCatalog> produtsNoOnlineWithColors;
	private final List<FilterType> filterOnline = Arrays.asList(FilterType.ONLINE);
	private final List<FilterType> filterNoOnlineWithColors = Arrays.asList(FilterType.NO_ONLINE, FilterType.MANY_COLORS);
	
	private final FichaSteps fichaSteps = new FichaSteps();
	
	public Fic001() throws Exception {
		super();
		dataTest.setUserRegistered(true);
		var codPais = dataTest.getPais().getCodigoAlf();
		var getterProducts = new GetterProducts.Builder(codPais, app, driver)
				.minProducts(200)
				.build();

		productOnline = getterProducts.getOne(filterOnline);
		produtsNoOnlineWithColors = getterProducts.getAll(filterNoOnlineWithColors);
	}
	
	@Override
	public void execute() throws Exception {
		accessAndClearData();
		if (productOnline.isPresent()) {
			articleOnlineTest();
		}
		stopIfNoPresentArticleNoOnlineWithColors();
		articleNoOnlineTest();
	}

	private void articleOnlineTest() {
		var articleOnline = Article.getArticleForTest(productOnline.get());
		new BuscadorSteps().searchArticulo(articleOnline, filterOnline);
		fichaSteps.checkLinkDispTiendaInvisible();
	}
	
	private void articleNoOnlineTest() throws Exception {
		var garmentCatalog = searchArticleNoOnline();
		var articulo = selectColorAndTalla(garmentCatalog);
		if (isShop()) {
			checkFavorites();
		}
		fichaSteps.selectAnadirALaBolsaTallaPrevSiSelected(articulo);
	}

	private GarmentCatalog searchArticleNoOnline() {
		if (isOutlet()) {
			return searchArticleNoOnlineOutlet();
		} 
		return searchArticleNoOnlineShop();
	}
	
	private GarmentCatalog searchArticleNoOnlineOutlet() {
		var garmentNoOnline = produtsNoOnlineWithColors.get(0);
		var articleNoOnlineWithColors = Article.getArticleForTest(garmentNoOnline);
		new BuscadorSteps().searchArticulo(articleNoOnlineWithColors, filterNoOnlineWithColors);
		return garmentNoOnline;
	}
	
	private GarmentCatalog searchArticleNoOnlineShop() {
		GarmentCatalog garmentNoOnline = produtsNoOnlineWithColors.get(0);
		for (int i=0; i<3; i++) {
			garmentNoOnline = produtsNoOnlineWithColors.get(i);
			var articleNoOnlineWithColors = Article.getArticleForTest(garmentNoOnline);
			new BuscadorSteps().searchArticulo(articleNoOnlineWithColors, filterNoOnlineWithColors);
			var state = (i==3) ? State.DEFECT : State.WARN;
			var foundTiendas = fichaSteps.selectBuscarEnTienda(state);
			new ModalBuscarEnTiendaSteps().close();
			if (foundTiendas) {
				break;
			}
		}
		return garmentNoOnline;
	}	

	private ArticuloScreen selectColorAndTalla(GarmentCatalog produtNoOnlineWithColors) 
			throws Exception {
		boolean isTallaUnica = checkClickAddBolsaWithoutSelectTalla();
		var articulo = new ArticuloScreen(produtNoOnlineWithColors);
		fichaSteps.selectColorAndSaveData(articulo);
		fichaSteps.selectTallaAndSaveData(articulo);
		ifTallaUnicaClearBolsa(isTallaUnica);
		return articulo;
	}	
	
	private boolean checkClickAddBolsaWithoutSelectTalla() {
		boolean isTallaUnica = fichaSteps.selectAnadirALaBolsaTallaPrevNoSelected();
		if (channel.isDevice()) {
			fichaSteps.closeTallas();
		}
		return isTallaUnica;
	}
	
	private void checkFavorites() {
		fichaSteps.selectAnadirAFavoritos();
		fichaSteps.changeColorGarment();
		fichaSteps.selectRemoveFromFavoritos();		
	}

	private void stopIfNoPresentArticleNoOnlineWithColors() throws NotFoundException {
		if (produtsNoOnlineWithColors.isEmpty()) {
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
