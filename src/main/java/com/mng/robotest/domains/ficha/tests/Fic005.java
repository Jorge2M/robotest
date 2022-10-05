package com.mng.robotest.domains.ficha.tests;

import java.util.Optional;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.domains.ficha.steps.SecModalPersonalizacionSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.Menu;
import com.mng.robotest.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;

import javassist.NotFoundException;

public class Fic005 extends TestBase {

	final GarmentCatalog articlePersonalizable;
	
	final SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps();
	final PageFichaSteps pageFichaSteps = new PageFichaSteps();
	final SecModalPersonalizacionSteps modalPersonalizacionSteps = new SecModalPersonalizacionSteps();
	
	public Fic005() throws Exception {
		super();
		articlePersonalizable = getArticlePersonalizable(dataTest.getPais().getCodigo_alf(), app, driver);
	}
	
	@Override
	public void execute() throws Exception {
		access();
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
		
		Optional<GarmentCatalog> articlePersonalizable = new GetterProducts
				.Builder(codigoPais, app, driver)
				.linea(LineaType.he)
				.menu(Menu.CamisasHE)
				.numProducts(5)
				.filter(FilterType.PERSONALIZABLE)
				.build()
				.getOne();
		
		if (!articlePersonalizable.isPresent()) {
			throw new NotFoundException("Not found article with filter " + FilterType.PERSONALIZABLE);
		}
		return articlePersonalizable.get();
	}	

}
