package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandexMoneyResult;

public class PageYandexMoneyResultStpV {
    
	@Validation
    public static ChecksResult validateIsResultOk(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece la página de resultado de Yandex Money",
			PageYandexMoneyResult.isPage(driver), State.Defect);
	 	validations.add(
			"Aparece un mensaje de transferencia con éxito",
			PageYandexMoneyResult.isVisibleMsgTransferOk(driver), State.Defect);
		return validations;   
    }
}
