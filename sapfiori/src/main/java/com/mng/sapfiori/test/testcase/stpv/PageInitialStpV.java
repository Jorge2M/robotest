package com.mng.sapfiori.test.testcase.stpv;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.State;
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
    	description="Aparece la página inicial de la aplicación",
    	level=State.Defect)
    public boolean checkIsInitialPage() {
    	return (pageInitial.checkIsInitialPage());
    }
}
