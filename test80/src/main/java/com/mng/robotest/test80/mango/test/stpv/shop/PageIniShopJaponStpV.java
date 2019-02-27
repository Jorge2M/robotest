package com.mng.robotest.test80.mango.test.stpv.shop;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageIniShopJapon;

public class PageIniShopJaponStpV {

	@Validation 
    public static ListResultValidation validaPageIniJapon(int maxSecondsWait, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
    	validations.add(
    		"Estamos en la página inicial de la shop de Japón (la esperamos hasta " + maxSecondsWait + " segundos):<br>" +
            "   - El título es \"" + PageIniShopJapon.Title + "\"<br>" +        
            "   - La URL contiene \"" + PageIniShopJapon.URL + "\"",
			PageIniShopJapon.isPageUntil(maxSecondsWait, driver), State.Warn);
    	
    	return validations;
    }
}
