package com.mng.robotest.tests.domains.personalizacion.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.repository.featureflags.GetterFeatureFlags;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.Menu;
import com.mng.robotest.tests.repository.productlist.GetterProducts.MethodGetter;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;

public class GetProductsSteps extends StepBase {

	public void callProductListService(LineaType linea, String seccion, String galeria, String familia) throws Exception {
		var getterProducts = new GetterProducts.Builder(dataTest.getPais().getCodigo_alf(), app, driver)
				.method(MethodGetter.WEBDRIVER)
				.linea(LineaType.SHE)
				.menu(Menu.VAQUEROS)
				.build();
		
		if (GetterFeatureFlags.isEnabledPersonalization(dataTest.getCodigoPais())) {
			callProductListServiceSiPerso(getterProducts);
		} else {
			callProductListServiceNoPerso(getterProducts);
		}
	}
	
	@Step (
		description=
			"Invocar desde navegador a productlist/products (<b>#{linea.name()}, #{seccion}, #{galeria}, #{familia}</b>) " + 
			"con personalización", 
		expected=
			"Aparece el flag de personalización en mínimo 2 y máximo 4 productos")
	private void callProductListServiceSiPerso(GetterProducts getterProducts) throws Exception {
		isPersonalization(2, 4, getterProducts);
	}
	
	@Step (
		description=
			"Invocar desde navegador a productlist/products (<b>#{linea.name()}, #{seccion}, #{galeria}, #{familia}</b>) " + 
			"con personalización", 
		expected=
			"Está desactivada la personalización -> No aparece el falg de personalización en ningún producto")
	private void callProductListServiceNoPerso(GetterProducts getterProducts) throws Exception {
		isPersonalization(0, 0, getterProducts);
	}	
	
	@Validation
	public ChecksTM isPersonalization(int minArticles, int maxArticles, GetterProducts getterProducts) 
			throws Exception {
		var checks = ChecksTM.getNew();
		int numArticlesPersonalized = getNumArticlesPersonalized(getterProducts);
	 	checks.add(
	 		"El número de artículos personalizados está entre " + minArticles + " y " + maxArticles + 
	 		" (se acaban localizando <b>" + numArticlesPersonalized + " artículos</b>)",
	 		numArticlesPersonalized >= minArticles && numArticlesPersonalized <= maxArticles);
	 	
	 	return checks;
	}
	
	private int getNumArticlesPersonalized(GetterProducts getterProducts) throws Exception {
		int numArticlesPersonalized = 0;
		for (GarmentCatalog garment : getterProducts.getAll()) {
			if (garment.getAnalyticsEventsData().isPersonalized()) {
				numArticlesPersonalized+=1;
			}
		}
		return numArticlesPersonalized;
	}
	
}
