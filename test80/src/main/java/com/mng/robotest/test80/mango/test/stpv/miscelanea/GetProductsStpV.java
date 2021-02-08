package com.mng.robotest.test80.mango.test.stpv.miscelanea;

import org.openqa.selenium.WebDriver;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.getdata.products.GetterProducts;
import com.mng.robotest.test80.mango.test.getdata.products.GetterProducts.MethodGetter;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.utils.Test80;

public class GetProductsStpV {

	@Step (
		description=
			"Invocar desde navegador a productlist/products (<b>#{linea.name()}, #{seccion}, #{galeria}, #{familia}</b>) " + 
			"con personalización", 
		expected=
			"Aparece el flag de personalización en mínimo 2 y máximo 4 productos")
	public static void callProductListService(LineaType linea, String seccion, String galeria, String familia, WebDriver driver) 
	throws Exception {
    	DataCtxShop dCtxSh = Test80.getDefaultDataShop(); 
		GetterProducts getterProducts = new GetterProducts.Builder(dCtxSh, driver)
			.method(MethodGetter.WebDriver)
			.linea(LineaType.she)
			.seccion("prendas")
			.galeria("vaqueros")
			.familia("28")
			.build();
		
		isPersonalization(2, 4, getterProducts);
	}
	
	@Validation
	public static ChecksTM isPersonalization(int minArticles, int maxArticles, GetterProducts getterProducts) {
		ChecksTM validations = ChecksTM.getNew();
		int numArticlesPersonalized = getNumArticlesPersonalized(getterProducts);
	 	validations.add(
	 		"El número de artículos personalizados está entre " + minArticles + " y " + maxArticles + 
	 		" (se acaban localizando <b>" + numArticlesPersonalized + " artículos</b>)",
	 		numArticlesPersonalized >= minArticles && numArticlesPersonalized <= maxArticles, 
	 		State.Defect);
	 	return validations;
	}
	
	private static int getNumArticlesPersonalized(GetterProducts getterProducts) {
		int numArticlesPersonalized = 0;
		for (Garment garment : getterProducts.getAll()) {
			if (garment.getAnalyticsEventsData().isPersonalized()) {
				numArticlesPersonalized+=1;
			}
		}
		return numArticlesPersonalized;
	}
	
}