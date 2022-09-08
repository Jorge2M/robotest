package com.mng.robotest.domains.personalizacion.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.Menu;
import com.mng.robotest.test.getdata.products.GetterProducts.MethodGetter;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;

public class GetProductsSteps extends StepBase {

	@Step (
		description=
			"Invocar desde navegador a productlist/products (<b>#{linea.name()}, #{seccion}, #{galeria}, #{familia}</b>) " + 
			"con personalización", 
		expected=
			"Aparece el flag de personalización en mínimo 2 y máximo 4 productos")
	public void callProductListService(LineaType linea, String seccion, String galeria, String familia) throws Exception {
		GetterProducts getterProducts = new GetterProducts.Builder(dataTest.pais.getCodigo_alf(), app, driver)
			.method(MethodGetter.WebDriver)
			.linea(LineaType.she)
			.menu(Menu.Vaqueros)
			.build();
		
		isPersonalization(2, 4, getterProducts);
	}
	
	@Validation
	public ChecksTM isPersonalization(int minArticles, int maxArticles, GetterProducts getterProducts) {
		ChecksTM checks = ChecksTM.getNew();
		int numArticlesPersonalized = getNumArticlesPersonalized(getterProducts);
	 	checks.add(
	 		"El número de artículos personalizados está entre " + minArticles + " y " + maxArticles + 
	 		" (se acaban localizando <b>" + numArticlesPersonalized + " artículos</b>)",
	 		numArticlesPersonalized >= minArticles && numArticlesPersonalized <= maxArticles, 
	 		State.Defect);
	 	
	 	return checks;
	}
	
	private int getNumArticlesPersonalized(GetterProducts getterProducts) {
		int numArticlesPersonalized = 0;
		for (GarmentCatalog garment : getterProducts.getAll()) {
			if (garment.getAnalyticsEventsData().isPersonalized()) {
				numArticlesPersonalized+=1;
			}
		}
		return numArticlesPersonalized;
	}
	
}