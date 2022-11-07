package com.mng.robotest.domains.ficha.tests;

import java.util.Arrays;
import java.util.Optional;

import org.openqa.selenium.NoSuchElementException;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.domains.ficha.pageobjects.PageFichaDevice;
import com.mng.robotest.domains.ficha.pageobjects.Slider;
import com.mng.robotest.domains.ficha.pageobjects.SecProductDescrOld.TypePanel;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.getdata.productlist.GetterProducts;
import com.mng.robotest.getdata.productlist.ProductFilter.FilterType;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;

public class Fic002 extends TestBase {

	private final Article article;
	private final boolean isTotalLook;

	private final SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps();
	private final PageFichaSteps pageFichaSteps = new PageFichaSteps();

	public Fic002() throws Exception {
		super();

		GetterProducts getterProducts = new GetterProducts
				.Builder(dataTest.getPais().getCodigo_alf(), app, driver)
				.build();

		Optional<GarmentCatalog> articleWithTotalLook = getterProducts
				.getOne(Arrays.asList(FilterType.TOTAL_LOOK));
		
		GarmentCatalog garment;
		if (articleWithTotalLook.isPresent()) {
			this.isTotalLook = true;
			garment = articleWithTotalLook.get();
		} else {
			this.isTotalLook = false;
			Optional<GarmentCatalog> garmentOpt = 	getterProducts.getOne();
			if (garmentOpt.isPresent()) {
				garment = garmentOpt.get();
			} else {
				throw new NoSuchElementException("Article not retrieved from GetterProducts service");
			}
		}
		article = Article.getArticleCandidateForTest(garment);
	}

	@Override
	public void execute() throws Exception {
		access();
		secBuscadorSteps.searchArticulo(article);
		if (channel.isDevice()) {
			pageFichaDeviceTest();
		} else {
			pageFichaDesktopTest();

		}

		pageFichaSteps.selectGuiaDeTallas(app);
		if (app==AppEcom.shop) {
			pageFichaSteps.validateSliderIfExists(Slider.ELEGIDO_PARA_TI);
		}

		if (app!=AppEcom.outlet && isTotalLook) {
			pageFichaSteps.validateSliderIfExists(Slider.COMPLETA_TU_LOOK);
		}
	}

	private void pageFichaDesktopTest() {
		boolean isFichaAccesorio = pageFichaSteps.getFicha().isFichaAccesorio();
		pageFichaSteps.getSecFotosNewSteps().validaLayoutFotosNew(isFichaAccesorio);
		if (isTotalLook) {
			pageFichaSteps.getSecTotalLookSteps().checkIsVisible();
		}

		if (app==AppEcom.shop) {
			pageFichaSteps.getSecBolsaButtonAndLinksNewSteps().selectEnvioYDevoluciones();
			pageFichaSteps.getModEnvioYdevolSteps().clickAspaForClose();
		}

		pageFichaSteps.getSecBolsaButtonAndLinksNewSteps().selectDetalleDelProducto(LineaType.she);
		pageFichaSteps.getSecBolsaButtonAndLinksNewSteps().selectLinkCompartir(dataTest.getCodigoPais());
	}

	private void pageFichaDeviceTest() {
		if (app==AppEcom.outlet && channel!=Channel.mobile) {
			pageFichaSteps.validaExistsImgsCarruselIzqFichaOld();
		}
		pageFichaSteps.getSecProductDescOldSteps().validateAreInStateInitial(app);
		PageFicha pageFicha = PageFicha.of(channel);
		if (((PageFichaDevice)pageFicha).getNumImgsCarruselIzq() > 2) {
			pageFichaSteps.selectImgCarruselIzqFichaOld(2);
		}

		if (channel!=Channel.tablet) {
			pageFichaSteps.selectImagenCentralFichaOld();
			if (channel.isDevice()) {
				pageFichaSteps.closeZoomImageCentralDevice();
			}
		}
		if (TypePanel.DESCRIPTION.getListApps().contains(app) &&
			!channel.isDevice()) {
			pageFichaSteps.getSecProductDescOldSteps().selectPanel(TypePanel.DESCRIPTION);
		}
		if (TypePanel.COMPOSITION.getListApps().contains(app)) {
			pageFichaSteps.getSecProductDescOldSteps().selectPanel(TypePanel.COMPOSITION);
		}
		if (TypePanel.RETURNS.getListApps().contains(app)) {
			pageFichaSteps.getSecProductDescOldSteps().selectPanel(TypePanel.RETURNS);
		}
		if (TypePanel.SHIPMENT.getListApps().contains(app) &&
			!channel.isDevice()) {
			pageFichaSteps.getSecProductDescOldSteps().selectPanel(TypePanel.SHIPMENT);  
		}
	}

}
