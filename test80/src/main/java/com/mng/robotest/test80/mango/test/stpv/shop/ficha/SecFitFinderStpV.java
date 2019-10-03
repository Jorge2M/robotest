package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecFitFinder;

public class SecFitFinderStpV {
    
	@Validation
    public static ChecksResult validateIsOkAndClose(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	int maxSecondsWait = 2;
      	validations.add(
    		"Es visible el Wrapper con la guía de tallas (lo esperamos hasta " + maxSecondsWait + " seconds)",
    		SecFitFinder.isVisibleUntil(maxSecondsWait, driver), State.Defect);
      	validations.add(
    		"Es visible el input para la introducción de la altura",
    		SecFitFinder.isVisibleInputAltura(driver), State.Warn);
      	validations.add(
    		"Es visible el input para la introducción del peso",
    		SecFitFinder.isVisibleInputPeso(driver), State.Warn);

    	SecFitFinder.clickAspaForCloseAndWait(driver);
    	return validations;
    }
}
