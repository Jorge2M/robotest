package com.mng.sapfiori.access.test.testcase.stpv.reclassifprods;

import com.mng.sapfiori.access.test.testcase.webobject.reclassifprods.PageReclassifProducts;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;

public class PageReclassifProductsStpV {

	private final PageReclassifProducts pageReclassifProducts;
	
	private PageReclassifProductsStpV(PageReclassifProducts pageReclassifProducts) {
		this.pageReclassifProducts = pageReclassifProducts;
	}
	
	public static PageReclassifProductsStpV getNew(PageReclassifProducts pageReclassifProducts) {
		return new PageReclassifProductsStpV(pageReclassifProducts);
	}
	
	@Validation (
		description = 
			"Estamos en la página de <b>Reclasificación de productos</b> " +
			"(la esperamos hasta #{maxSeconds} segundos)",
		level = State.Defect)
	public boolean checkIsPageUntil(int maxSeconds) {
		return pageReclassifProducts.checkIsPageUntil(3);
	}
	
	@Step (
		description = 
			"Introducir el nuevo \"Cód.estad.mercancías\" con valor <b>#{newCodEstadMerc}</b> " +
			"y pulsar el botón <b>Grabar</b>",
		expected = 
			"Se modifica correctamente el dato",
		saveImagePage=SaveWhen.Always)
	public void writeInputCodEstadMercAndSave(String newCodEstadMerc) throws Exception {
		pageReclassifProducts.writeInputCodEstadMerc(newCodEstadMerc);
		pageReclassifProducts.clickGrabarButton();
		
//		PageSelProdsToReclassifyStpV pageSelProductsToReclassify = PageSelProdsToReclassifyStpV.getNew(
//			pageReclassifProducts.clickGrabarButton());
//		pageSelProductsToReclassify.checkIsVisiblePage(3);
	}
}
