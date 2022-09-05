package com.mng.robotest.domains.ficha.tests;

import java.util.Arrays;
import java.util.Optional;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.domains.ficha.steps.PageFichaArtSteps;
import com.mng.robotest.domains.ficha.steps.SecModalPersonalizacionSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.Menu;
import com.mng.robotest.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.steps.shop.AccesoSteps;

import javassist.NotFoundException;


public class Fic005 extends TestBase {

	final GarmentCatalog articlePersonalizable;
	
	final SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps();
	final PageFichaArtSteps pageFichaSteps = new PageFichaArtSteps();
	final SecModalPersonalizacionSteps modalPersonalizacionSteps = new SecModalPersonalizacionSteps();
	
	public Fic005() throws Exception {
		super();
		articlePersonalizable = getArticlePersonalizable(dataTest.pais.getCodigo_alf(), app, driver);
	}
	
	@Override
	public void execute() throws Exception {
		new AccesoSteps().oneStep(false);
 		searchAndCheckArticlePersonalizable();
		
		pageFichaSteps.selectFirstTallaAvailable();
		modalPersonalizacionSteps.selectLinkPersonalizacion();
		modalPersonalizacionSteps.selectIconCustomization();
		modalPersonalizacionSteps.selectFirstIcon();
		modalPersonalizacionSteps.validateIconSelectedDesktop();
		modalPersonalizacionSteps.selectConfirmarButton();
		modalPersonalizacionSteps.validateCabeceraStep(2);
		modalPersonalizacionSteps.validateWhereDesktop();
		modalPersonalizacionSteps.selectConfirmarButton();
		modalPersonalizacionSteps.validateCabeceraStep(3);
		modalPersonalizacionSteps.validateSelectionColor();
 
		modalPersonalizacionSteps.selectSize();
		modalPersonalizacionSteps.confirmCustomization();
		modalPersonalizacionSteps.checkCustomizationProof();
	}

	private void searchAndCheckArticlePersonalizable() throws Exception {
		secBuscadorSteps.searchArticulo(articlePersonalizable);
		int numColors = pageFichaSteps.getFicha().getNumColors();
		for (int i=1; i<=numColors; i++) {
			pageFichaSteps.selectColor(i);
			State levelError = (i==numColors) ? State.Defect : State.Info;
			if (modalPersonalizacionSteps.checkArticleCustomizable(levelError)) {
				break;
			}
		}
	}
	
	private GarmentCatalog getArticlePersonalizable(String codigoPais, AppEcom app, WebDriver driver) 
			throws Exception {
		
		GetterProducts getterProducts = new GetterProducts.Builder(codigoPais, app, driver)
				.linea(LineaType.he)
				.menu(Menu.CamisasHE)
				.numProducts(5)
				.build();
		
		Optional<GarmentCatalog> articlePersonalizable = getterProducts.getOneFiltered(
				Arrays.asList(FilterType.Personalizable));
		
		if (!articlePersonalizable.isPresent()) {
			throw new NotFoundException("Not found article with filter " + FilterType.Personalizable);
		}
		return articlePersonalizable.get();
	}	

}
