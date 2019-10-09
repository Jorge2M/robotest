package com.mng.sapfiori.test.testcase.stpv;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.utils.State;
import com.mng.sapfiori.test.testcase.pageobject.PageInitial;

public class PageInitialStpV {

	private final PageInitial pageInitial;
	
	private PageInitialStpV(WebDriver driver) {
		this.pageInitial = PageInitial.getNew(driver);
	}
	
	public static PageInitialStpV getNew(WebDriver driver) {
		return new PageInitialStpV(driver);
	}
    
    @Validation (
    	description="Aparece la página inicial de la aplicación cuando el acceso en Español",
    	level=State.Defect)
    public boolean checkIsInitialPageSpanish() {
    	return (pageInitial.checkIsInitialPageSpanish());
    }
}
