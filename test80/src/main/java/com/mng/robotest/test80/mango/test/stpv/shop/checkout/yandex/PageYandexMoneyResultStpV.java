package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandexMoneyResult;

public class PageYandexMoneyResultStpV {
    
	@Validation
    public static ChecksTM validateIsResultOk(WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece la página de resultado de Yandex Money",
			PageYandexMoneyResult.isPage(driver), State.Defect);
	 	validations.add(
			"Aparece un mensaje de transferencia con éxito",
			PageYandexMoneyResult.isVisibleMsgTransferOk(driver), State.Defect);
		return validations;   
    }
}
