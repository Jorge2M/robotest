package com.mng.robotest.domains.ficha.tests;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.NoSuchElementException;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.domains.ficha.pageobjects.PageFichaDevice;
import com.mng.robotest.domains.ficha.pageobjects.SecSliders.Slider;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.getdata.productlist.GetterProducts;
import com.mng.robotest.getdata.productlist.ProductFilter.FilterType;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;

import static com.mng.robotest.domains.ficha.pageobjects.SecProductDescrOld.TypePanel.*;

public class Fic002 extends TestBase {

	private final Article article;
	private final boolean isTotalLook;

	private final SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps();
	private final PageFichaSteps pageFichaSteps = new PageFichaSteps();

	public Fic002() throws Exception {
		super();
		var pair = getArticle();
		this.article = pair.getLeft();
		this.isTotalLook = pair.getRight();
	}

	@Override
	public void execute() throws Exception {
		access();
		searchArticle();
		searchArticle();
		checkSliders();
		checkFicha();
		checkGuiaDeTallas();
	}
	
	private void searchArticle() {
		secBuscadorSteps.searchArticulo(article);
	}
	private void checkFicha() {
		if (channel.isDevice()) {
			pageFichaDeviceTest();
		} else {
			pageFichaDesktopTest();

		}
	}
	
	private void checkGuiaDeTallas() {
		pageFichaSteps.selectGuiaDeTallas(app);		
	}
	
	private void checkSliders() {
		Stream.of(Slider.values())
			.filter(s -> isSliderChequeable(s))
			.forEach(s -> pageFichaSteps.validateSliderIfExists(s));
	}
	private boolean isSliderChequeable(Slider slider) {
		return 
			slider.active(app) &&
			slider!=Slider.COMBINA_PERFECTO || isTotalLook;
	}

	private void pageFichaDesktopTest() {
		boolean isFichaAccesorio = pageFichaSteps.getFicha().isFichaAccesorio();
		pageFichaSteps.getSecFotosNewSteps().validaLayoutFotosNew(isFichaAccesorio);
		if (app==AppEcom.shop) {
			pageFichaSteps.getSecBolsaButtonAndLinksNewSteps().selectEnvioYDevoluciones();
			pageFichaSteps.getModEnvioYdevolSteps().clickAspaForClose();
		}

		pageFichaSteps.getSecBolsaButtonAndLinksNewSteps().selectDetalleDelProducto(LineaType.SHE);
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
		if (DESCRIPTION.getListApps().contains(app) &&
			!channel.isDevice()) {
			pageFichaSteps.getSecProductDescOldSteps().selectPanel(DESCRIPTION);
		}
		if (COMPOSITION.getListApps().contains(app)) {
			pageFichaSteps.getSecProductDescOldSteps().selectPanel(COMPOSITION);
		}
		if (RETURNS.getListApps().contains(app)) {
			pageFichaSteps.getSecProductDescOldSteps().selectPanel(RETURNS);
		}
		if (SHIPMENT.getListApps().contains(app) &&
			!channel.isDevice()) {
			pageFichaSteps.getSecProductDescOldSteps().selectPanel(SHIPMENT);  
		}
	}
	
	private Pair<Article, Boolean> getArticle() throws Exception {
		var getterProducts = new GetterProducts
				.Builder(dataTest.getPais().getCodigo_alf(), app, driver)
				.build();

		var articleWithTotalLook = getterProducts
				.getOne(Arrays.asList(FilterType.TOTAL_LOOK));
		
		GarmentCatalog garment;
		boolean isTotalLook;
		if (articleWithTotalLook.isPresent()) {
			isTotalLook = true;
			garment = articleWithTotalLook.get();
		} else {
			isTotalLook = false;
			Optional<GarmentCatalog> garmentOpt = 	getterProducts.getOne();
			if (garmentOpt.isPresent()) {
				garment = garmentOpt.get();
			} else {
				throw new NoSuchElementException("Article not retrieved from GetterProducts service");
			}
		}
		return Pair.of(Article.getArticleCandidateForTest(garment), isTotalLook);
	}

}
