package com.mng.robotest.tests.domains.compra.payments.yandex.pageobjects;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageYandexMoneyResult extends PageBase {
	
	public static final String MSG_TRANSFER_OK = "Обработка завершена. Запрос выполнен успешно. Зачисление перевода проведено успешно";
	public static final String XPATH_DIV_RESULT_MSG = "//div[@class='docbook-para']";
	
	public boolean isPage() {
		return state(Visible, XPATH_DIV_RESULT_MSG).check();
	}
		
	public boolean isVisibleMsgTransferOk() {
		WebElement divResult = getElement(XPATH_DIV_RESULT_MSG);
		return (state(Visible, XPATH_DIV_RESULT_MSG).check() &&
				divResult.getText().contains(MSG_TRANSFER_OK));
	}
}
