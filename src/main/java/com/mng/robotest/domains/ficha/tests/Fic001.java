package com.mng.robotest.domains.ficha.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.domains.ficha.steps.ModalBuscadorTiendasSteps;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;

import javassist.NotFoundException;

public class Fic001 extends TestBase {

	private final Optional<GarmentCatalog> articleOnline;
	private final List<FilterType> filterOnline = Arrays.asList(FilterType.Online);
	private final List<FilterType> filterNoOnlineWithColors = Arrays.asList(FilterType.NoOnline, FilterType.ManyColors);
	private final Optional<GarmentCatalog> articleNoOnlineWithColors;
	
	private final PageFichaSteps pageFichaSteps = new PageFichaSteps();
	
	public Fic001() throws Exception {
		super();
		dataTest.setUserRegistered(true);
		GetterProducts getterProducts = new GetterProducts.Builder(dataTest.getPais().getCodigo_alf(), app, driver)
				.numProducts(80)
				.build();

		articleOnline = getterProducts.getOneFiltered(filterOnline);
		articleNoOnlineWithColors = getterProducts.getOneFiltered(filterNoOnlineWithColors);
	}
	
	@Override
	public void execute() throws Exception {
		accessAndClearData();
		if (articleOnline.isPresent()) {
			articleOnlineTest();
		}
		stopIfNoPresentArticleNoOnlineWithColors();
		articleNoOnlineTest();
	}

	private void articleOnlineTest() throws Exception {
		new SecBuscadorSteps().searchArticulo(articleOnline.get(), filterOnline);
		pageFichaSteps.checkLinkDispTiendaInvisible();
	}
	
	private void articleNoOnlineTest() throws Exception {
		new SecBuscadorSteps().searchArticulo(articleNoOnlineWithColors.get(), filterNoOnlineWithColors);
		boolean isTallaUnica = pageFichaSteps.selectAnadirALaBolsaTallaPrevNoSelected();
		ArticuloScreen articulo = new ArticuloScreen(articleNoOnlineWithColors.get());
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
		if (!articleNoOnlineWithColors.isPresent()) {
			List<String> filtersLabels = 
					filterNoOnlineWithColors
						.stream()
						.map(Object::toString)
						.collect(Collectors.toList());
			
			throw new NotFoundException("Not found article with filters " + String.join(",", filtersLabels));
		}
	}
	
	private void ifTallaUnicaClearBolsa(boolean isTallaUnica) throws Exception {
		//Si es talla única -> Significa que lo dimos de alta en la bolsa cuando seleccionamos el click "Añadir a la bolsa"
		//-> Lo damos de baja
		if (isTallaUnica) {
			SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
			secBolsaSteps.clear();
		}
	}

}
