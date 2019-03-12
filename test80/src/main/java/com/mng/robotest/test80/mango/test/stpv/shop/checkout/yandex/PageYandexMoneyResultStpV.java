package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandexMoneyResult;

public class PageYandexMoneyResultStpV {
    
	@Validation
    public static ListResultValidation validateIsResultOk(WebDriver driver) {
    	ListResultValidation validations = ListResultValidation.getNew();
	 	validations.add(
			"Aparece la página de resultado de Yandex Money<br>",
			PageYandexMoneyResult.isPage(driver), State.Defect);
	 	validations.add(
			"Aparece un mensaje de transferencia con éxito",
			PageYandexMoneyResult.isVisibleMsgTransferOk(driver), State.Defect);
		return validations;   
    }
}
