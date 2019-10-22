package com.mng.sapfiori.test.testcase.stpv;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.webobject.PageInitial;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;

public class PageInitialStpV {

	private final PageInitial pageInitial;
	
	private PageInitialStpV(WebDriver driver) {
		this.pageInitial = PageInitial.getNew(driver);
	}
	private PageInitialStpV(PageInitial pageInitial) {
		this.pageInitial = pageInitial;
	}
	
	public static PageInitialStpV getNew(WebDriver driver) {
		return new PageInitialStpV(driver);
	}
	public static PageInitialStpV getNew(PageInitial pageInitial) {
		return new PageInitialStpV(pageInitial);
	}
    
    @Validation (
    	description=
    		"Aparece la página inicial de la aplicación cuando el acceso en Español " +
    		"(la esperamos hasta #{maxSeconds} segundos)",
    	level=State.Defect)
    public boolean checkIsInitialPageSpanish(int maxSeconds) {
    	//return (pageInitial.checkIsInitialPageSpanish(maxSeconds));
    	return false;
    }
    
	@Step (
		description="Seleccionar el option <b>Clasificar Productos</b>",
		expected="Aparece la página para clasificar los productos")
	public PageSelProdsToReclassifyStpV clickClasificarProductos() throws Exception {
		PageSelProdsToReclassifyStpV pageClassifProductosStpV = PageSelProdsToReclassifyStpV.getNew(
			pageInitial.clickClasificarProductos());
		
		int maxSeconds = 3;
		pageClassifProductosStpV.checkIsVisiblePage(maxSeconds);
		return pageClassifProductosStpV;
	}
}
