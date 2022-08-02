package com.mng.robotest.domains.ficha.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.steps.ModalBuscadorTiendasSteps;
import com.mng.robotest.domains.ficha.steps.PageFichaArtSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test.stpv.shop.buscador.SecBuscadorStpV;

import javassist.NotFoundException;


public class Fic001 extends TestBase {

	final Optional<GarmentCatalog> articleOnline;
	final List<FilterType> filterOnline;
	final List<FilterType> filterNoOnlineWithColors;
	final Optional<GarmentCatalog> articleNoOnlineWithColors;
	
	final SecBuscadorStpV secBuscadorSteps = new SecBuscadorStpV(app, channel, driver);
	final PageFichaArtSteps pageFichaSteps = new PageFichaArtSteps(app, channel, dataTest.pais);
	
	public Fic001() throws Exception {
		super();
		
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userRegistered = true;
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		
		GetterProducts getterProducts = new GetterProducts.Builder(dataTest.pais.getCodigo_alf(), app, driver)
				.numProducts(80)
				.build();

		filterOnline = Arrays.asList(FilterType.Online);
		filterNoOnlineWithColors = Arrays.asList(
				FilterType.NoOnline, 
				FilterType.ManyColors);
		
		articleOnline = getterProducts.getOneFiltered(filterOnline);
		articleNoOnlineWithColors = getterProducts.getOneFiltered(filterNoOnlineWithColors);
	}
	
	@Override
	public void execute() throws Exception {
		AccesoStpV.oneStep(dataTest, true, driver);
		if (articleOnline.isPresent()) {
			articleOnlineTest();
		}
		
		stopIfNoPresentArticleNoOnlineWithColors();
		articleNoOnlineTest();
	}

	private void articleOnlineTest() throws Exception {
		secBuscadorSteps.searchArticulo(articleOnline.get(), dataTest.pais, filterOnline);
		pageFichaSteps.checkLinkDispTiendaInvisible();
	}
	
	private void articleNoOnlineTest() throws Exception {
		secBuscadorSteps.searchArticulo(articleNoOnlineWithColors.get(), dataTest.pais, filterNoOnlineWithColors);
		boolean isTallaUnica = pageFichaSteps.selectAnadirALaBolsaTallaPrevNoSelected();
		ArticuloScreen articulo = new ArticuloScreen(articleNoOnlineWithColors.get());
		pageFichaSteps.selectColorAndSaveData(articulo);
		pageFichaSteps.selectTallaAndSaveData(articulo);

		ifTallaUnicaClearBolsa(isTallaUnica);

		articulo = pageFichaSteps.getFicha().getArticuloObject();
		pageFichaSteps.selectBuscarEnTiendaButton();
		new ModalBuscadorTiendasSteps(channel, app, driver).close();
		if (app==AppEcom.shop) {
			pageFichaSteps.selectAnadirAFavoritos();
			pageFichaSteps.changeColorGarment();
			pageFichaSteps.selectRemoveFromFavoritos();
		}

		pageFichaSteps.selectAnadirALaBolsaTallaPrevSiSelected(articulo, dataTest);
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
			SecBolsaStpV secBolsaStpV = new SecBolsaStpV(dataTest, driver);
			secBolsaStpV.clear();
		}
	}

}
