package com.mng.sapfiori.test.testcase.stpv;

import com.mng.sapfiori.test.testcase.webobject.PageReclassifProducts;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.controlTest.DatosStep.SaveWhen;

public class PageReclassifProductsStpV {

	private final PageReclassifProducts pageReclassifProducts;
	
	private PageReclassifProductsStpV(PageReclassifProducts pageReclassifProducts) {
		this.pageReclassifProducts = pageReclassifProducts;
	}
	
	public static PageReclassifProductsStpV getNew(PageReclassifProducts pageReclassifProducts) {
		return new PageReclassifProductsStpV(pageReclassifProducts);
	}
	
	@Validation (
		description = "Estamos en la página de <b>Reclasificación de productos</b>",
		level = State.Defect)
	public boolean checkIsPage() {
		return pageReclassifProducts.checkIsPage();
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
