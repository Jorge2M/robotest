package com.mng.robotest.domains.ficha.tests;

import java.util.Optional;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.domains.ficha.pageobjects.PageFichaArtOld;
import com.mng.robotest.domains.ficha.pageobjects.Slider;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha.TypeFicha;
import com.mng.robotest.domains.ficha.pageobjects.SecProductDescrOld.TypePanel;
import com.mng.robotest.domains.ficha.steps.PageFichaArtSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.steps.shop.AccesoSteps;


public class Fic002 extends TestBase {

	final GarmentCatalog garment;
	final boolean isTotalLook;
	
	final SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps();
	final PageFichaArtSteps pageFichaSteps = new PageFichaArtSteps(dataTest.pais);
	
	public Fic002() throws Exception {
		super();
		
		GetterProducts getterProducts = new GetterProducts.Builder(dataTest.pais.getCodigo_alf(), app, driver)
				.build();
		
		Optional<GarmentCatalog> articleWithTotalLook = getterProducts.getOneFiltered(FilterType.TotalLook);
		isTotalLook = articleWithTotalLook.isPresent();
		if (isTotalLook) {
			garment = articleWithTotalLook.get();
		} else {
			garment = getterProducts.getAll().get(0);			
		}
	}
	
	@Override
	public void execute() throws Exception {

		AccesoSteps.oneStep(dataTest, false, driver);
		secBuscadorSteps.searchArticulo(garment, dataTest.pais);
		
		if (pageFichaSteps.getFicha().getTypeFicha()==TypeFicha.OLD) {
			pageFichaOldTest();
		} else {
			pageFichaNewTest();

		}
			
		pageFichaSteps.selectGuiaDeTallas(app);
		if (app==AppEcom.shop) {
			pageFichaSteps.validateSliderIfExists(Slider.ELEGIDO_PARA_TI);
		}
		
		if (app!=AppEcom.outlet && isTotalLook) {
			pageFichaSteps.validateSliderIfExists(Slider.COMPLETA_TU_LOOK);
		}
	}

	private void pageFichaNewTest() throws Exception {
		boolean isFichaAccesorio = pageFichaSteps.getFicha().isFichaAccesorio(); 
		pageFichaSteps.getSecFotosNewSteps().validaLayoutFotosNew(isFichaAccesorio);
		if (isTotalLook) {
			pageFichaSteps.getSecTotalLookSteps().checkIsVisible();
		}

		pageFichaSteps.getSecBolsaButtonAndLinksNewSteps().selectEnvioYDevoluciones();
		pageFichaSteps.getModEnvioYdevolSteps().clickAspaForClose();
		
		pageFichaSteps.getSecBolsaButtonAndLinksNewSteps().selectDetalleDelProducto(LineaType.she);
		pageFichaSteps.getSecBolsaButtonAndLinksNewSteps().selectLinkCompartir(dataTest.pais.getCodigo_pais());
	}

	private void pageFichaOldTest() throws Exception {
		if (app==AppEcom.outlet && channel!=Channel.mobile) {
			pageFichaSteps.validaExistsImgsCarruselIzqFichaOld();
		}
		pageFichaSteps.getSecProductDescOldSteps().validateAreInStateInitial(app);
		PageFicha pageFicha = PageFicha.newInstance(channel, app);
		if (((PageFichaArtOld)pageFicha).getNumImgsCarruselIzq() > 2) {
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
