package com.mng.robotest.tests.domains.ficha.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.exceptions.NotFoundException;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.tests.domains.ficha.steps.ModalBuscarEnTiendaSteps;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.ProductFilter.FilterType;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

public class Fic001 extends TestBase {

	private final Optional<GarmentCatalog> productOnline;
	private final Optional<GarmentCatalog> produtNoOnlineWithColors;
	private final List<FilterType> filterOnline = Arrays.asList(FilterType.ONLINE);
	private final List<FilterType> filterNoOnlineWithColors = Arrays.asList(FilterType.NO_ONLINE, FilterType.MANY_COLORS);
	
	private final FichaSteps fichaSteps = new FichaSteps();
	
	public Fic001() throws Exception {
		super();
		dataTest.setUserRegistered(true);
		var getterProducts = new GetterProducts.Builder(dataTest.getPais().getCodigoAlf(), app, driver)
				.minProducts(200)
				.build();

		productOnline = getterProducts.getOne(filterOnline);
		produtNoOnlineWithColors = getterProducts.getOne(filterNoOnlineWithColors);
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
		new SecBuscadorSteps().searchArticulo(articleOnline, filterOnline);
		fichaSteps.checkLinkDispTiendaInvisible();
	}
	
	private void articleNoOnlineTest() throws Exception {
		var articleNoOnlineWithColors = Article.getArticleForTest(produtNoOnlineWithColors.get());
		new SecBuscadorSteps().searchArticulo(articleNoOnlineWithColors, filterNoOnlineWithColors);
		if (isShop() && channel!=Channel.tablet) {
			fichaSteps.selectBuscarEnTienda();
			new ModalBuscarEnTiendaSteps().close();
		}

		var articulo = selectColorAndTalla();
		if (isShop()) {
			checkFavorites();
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
