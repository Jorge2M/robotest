package com.mng.robotest.tests.domains.ficha.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.exceptions.NotFoundException;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.tests.domains.ficha.steps.ModalBuscadorTiendasSteps;
import com.mng.robotest.tests.domains.ficha.steps.PageFichaSteps;
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
	
	private final PageFichaSteps pageFichaSteps = new PageFichaSteps();
	
	public Fic001() throws Exception {
		super();
		dataTest.setUserRegistered(true);
		var getterProducts = new GetterProducts.Builder(dataTest.getPais().getCodigo_alf(), app, driver)
				.numProducts(80)
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
		Article articleOnline = Article.getArticleForTest(productOnline.get());
		new SecBuscadorSteps().searchArticulo(articleOnline, filterOnline);
		pageFichaSteps.checkLinkDispTiendaInvisible();
	}
	
	private void articleNoOnlineTest() throws Exception {
		var articleNoOnlineWithColors = Article.getArticleForTest(produtNoOnlineWithColors.get());
		new SecBuscadorSteps().searchArticulo(articleNoOnlineWithColors, filterNoOnlineWithColors);
		boolean isTallaUnica = pageFichaSteps.selectAnadirALaBolsaTallaPrevNoSelected();
		if (channel.isDevice()) {
			pageFichaSteps.closeTallas();
		}

		var articulo = new ArticuloScreen(produtNoOnlineWithColors.get());
		pageFichaSteps.selectColorAndSaveData(articulo);
		pageFichaSteps.selectTallaAndSaveData(articulo);
		ifTallaUnicaClearBolsa(isTallaUnica);
		if (app==AppEcom.shop) {
			if (channel!=Channel.tablet) {
				pageFichaSteps.selectBuscarEnTiendaButton();
				new ModalBuscadorTiendasSteps().close();
			}
			pageFichaSteps.selectAnadirAFavoritos();
			pageFichaSteps.changeColorGarment();
			pageFichaSteps.selectRemoveFromFavoritos();
		}

		pageFichaSteps.selectAnadirALaBolsaTallaPrevSiSelected(articulo);
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
