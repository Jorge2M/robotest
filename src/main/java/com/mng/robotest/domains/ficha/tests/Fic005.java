package com.mng.robotest.domains.ficha.tests;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.domains.ficha.steps.SecModalPersonalizacionSteps;
import com.mng.robotest.repository.productlist.GetterProducts;
import com.mng.robotest.repository.productlist.entity.GarmentCatalog.Article;

import java.util.Optional;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType.*;
import static com.mng.robotest.repository.productlist.ProductFilter.FilterType.*;
import static com.mng.robotest.repository.productlist.Menu.*;

public class Fic005 extends TestBase {

	private final Optional<Article> articlePersonalizable;
	
	private final SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps();
	private final PageFichaSteps pageFichaSteps = new PageFichaSteps();
	private final SecModalPersonalizacionSteps modalPersonalizacionSteps = new SecModalPersonalizacionSteps();
	
	public Fic005() throws Exception {
		super();
		articlePersonalizable = getArticlePersonalizable(dataTest.getPais().getCodigo_alf(), app, driver);
	}
	
	@Override
	public void execute() throws Exception {
		if (articlePersonalizable.isEmpty()) {
			return;
		}
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
		secBuscadorSteps.searchArticulo(articlePersonalizable.get());
		int numColors = pageFichaSteps.getFicha().getNumColors();
		for (int i=1; i<=numColors; i++) {
			pageFichaSteps.selectColor(i);
			State levelError = (i==numColors) ? Defect : Info;
			if (modalPersonalizacionSteps.checkArticleCustomizable(levelError)) {
				break;
			}
		}
	}
	
	private Optional<Article> getArticlePersonalizable(String codigoPais, AppEcom app, WebDriver driver) 
			throws Exception {
		
		var articlePers = new GetterProducts
				.Builder(codigoPais, app, driver)
				.linea(HE)
				.menu(CAMISAS_HE)
				.numProducts(40)
				.filter(PERSONALIZABLE)
				.build()
				.getOne();
		
		if (!articlePers.isPresent()) {
			Log4jTM.getLogger().info("Not found article with filter " + PERSONALIZABLE);
			return Optional.empty();
		}
		return Optional.of(Article.getArticleCandidateForTest(articlePers.get()));
	}	

}
