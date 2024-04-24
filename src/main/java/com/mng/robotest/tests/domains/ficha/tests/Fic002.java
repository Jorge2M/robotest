package com.mng.robotest.tests.domains.ficha.tests;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.NoSuchElementException;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.SecSliders.Slider;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.PageFichaDeviceNoGenesis;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;

import static com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecProductDescrDevice.TypePanel.*;
import static com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType.*;
import static com.mng.robotest.tests.repository.productlist.ProductFilter.FilterType.*;

public class Fic002 extends TestBase {

	private final Article article;
	private final boolean isTotalLook;

	private final SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps();
	private final FichaSteps fichaSteps = new FichaSteps();

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
		fichaSteps.selectGuiaDeTallas(app);		
	}
	
	private void checkSliders() {
		Stream.of(Slider.values())
			.filter(this::isSliderChequeable)
			.forEach(fichaSteps::checkSliderIfExists);
	}
	private boolean isSliderChequeable(Slider slider) {
		return 
			slider.active(app) &&
			slider!=Slider.COMBINA_PERFECTO || isTotalLook;
	}

	private void pageFichaDesktopTest() {
		boolean isFichaAccesorio = fichaSteps.getFicha().isFichaAccesorio();
		fichaSteps.getSecFotosNewSteps().validaLayoutFotosNew(isFichaAccesorio);
		if (isShop()) {
			fichaSteps.getSecBolsaButtonAndLinksNewSteps().selectEnvioYDevoluciones();
			fichaSteps.getModEnvioYdevolSteps().clickAspaForClose();
		}

		fichaSteps.getSecBolsaButtonAndLinksNewSteps().selectDetalleDelProducto(SHE);
		fichaSteps.getSecBolsaButtonAndLinksNewSteps().selectLinkCompartir(dataTest.getCodigoPais());
	}

	private void pageFichaDeviceTest() {
		if (isOutlet() && !isMobile()) {
			fichaSteps.validaExistsImgsCarruselIzqFichaOld();
		}
		fichaSteps.getSecProductDescDeviceSteps().checkAreInStateInitial();
		var pageFicha = new PageFichaDeviceNoGenesis();
		if (pageFicha.getNumImgsCarruselIzq() > 2) {
			fichaSteps.selectImgCarruselIzqFichaOld(2);
		}

		if (channel!=Channel.tablet) {
			fichaSteps.selectImagenCentralFichaOld();
			if (channel.isDevice()) {
				fichaSteps.closeZoomImageCentralDevice();
			}
		}
		if (DESCRIPTION.getListApps().contains(app) &&
			!channel.isDevice()) {
			fichaSteps.getSecProductDescDeviceSteps().selectPanel(DESCRIPTION);
		}
		if (COMPOSITION.getListApps().contains(app)) {
			fichaSteps.getSecProductDescDeviceSteps().selectPanel(COMPOSITION);
		}
		if (RETURNS.getListApps().contains(app)) {
			fichaSteps.getSecProductDescDeviceSteps().selectPanel(RETURNS);
		}
		if (SHIPMENT.getListApps().contains(app) &&
			!channel.isDevice()) {
			fichaSteps.getSecProductDescDeviceSteps().selectPanel(SHIPMENT);  
		}
	}
	
	private Pair<Article, Boolean> getArticle() throws Exception {
		var articlePair = getArticleTLook();
		boolean isTLook = articlePair.getRight();
		var garmentOpt = articlePair.getLeft(); 
		if (garmentOpt.isEmpty()) {
			throw new NoSuchElementException("Article not retrieved from GetterProducts service");
		}
		
		return Pair.of(
				Article.getArticleForTest(garmentOpt.get()), 
				isTLook);
	}
	
	private Pair<Optional<GarmentCatalog>, Boolean> getArticleTLook() 
			throws Exception {
		var getterProducts = new GetterProducts
				.Builder(dataTest.getPais().getCodigoAlf(), app, driver)
				.build();
	
		var articleGet = getterProducts.getOne(Arrays.asList(TOTAL_LOOK));
		if (articleGet.isPresent()) {
			return Pair.of(articleGet, true);
		}
		return Pair.of(getterProducts.getOne(), false);
	}

}
