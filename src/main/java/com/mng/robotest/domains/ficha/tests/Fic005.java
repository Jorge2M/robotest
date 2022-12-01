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
import com.mng.robotest.getdata.productlist.GetterProducts;
import com.mng.robotest.getdata.productlist.Menu;
import com.mng.robotest.getdata.productlist.ProductFilter.FilterType;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;

import javassist.NotFoundException;

public class Fic005 extends TestBase {

	private final Article articlePersonalizable;
	
	private final SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps();
	private final PageFichaSteps pageFichaSteps = new PageFichaSteps();
	private final SecModalPersonalizacionSteps modalPersonalizacionSteps = new SecModalPersonalizacionSteps();
	
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

	private void searchAndCheckArticlePersonalizable() {
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
	
	private Article getArticlePersonalizable(String codigoPais, AppEcom app, WebDriver driver) 
			throws Exception {
		
		Optional<GarmentCatalog> articlePersonalizable = new GetterProducts
				.Builder(codigoPais, app, driver)
				.linea(LineaType.HE)
				.menu(Menu.CAMISAS_HE)
				.numProducts(40)
				.filter(FilterType.PERSONALIZABLE)
				.build()
				.getOne();
		
		if (!articlePersonalizable.isPresent()) {
			throw new NotFoundException("Not found article with filter " + FilterType.PERSONALIZABLE);
		}
		return Article.getArticleCandidateForTest(articlePersonalizable.get());
	}	

}
