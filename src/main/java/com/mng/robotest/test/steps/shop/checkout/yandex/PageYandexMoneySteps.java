package com.mng.robotest.test.steps.shop.checkout.yandex;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test.pageobject.shop.checkout.yandex.PageYandexMoney;

public class PageYandexMoneySteps {

	static final String tagUrlYandex = "@TagUrlYandex";
	@Step (
		description="Accedemos a la URL de <b>YandexMoney</b>: " + tagUrlYandex, 
		expected="Aparece la página de YandexMoney")
	public static void accessInNewTab(String tabTitle, WebDriver driver) throws Exception {
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagUrlYandex, PageYandexMoney.urlAccess);
		PageYandexMoney.goToPageInNewTab(tabTitle, driver);
		checkIsPage(driver);
	}
	
	@Validation
	private static ChecksTM checkIsPage(WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece el input para el <b>Payment Code</b>",
			PageYandexMoney.isVisibleInputPaymentCode(driver), State.Warn);
	 	checks.add(
			"Aparece el input para el importe",
			PageYandexMoney.isVisibleInputImport(driver), State.Warn);
	 	return checks;
	}
	
	@Step (
		description="Introducimos el paymentCode <b>#{paymentCode}</b>, el importe <b>#{importe}</b> y pulsamos el botón de Pago", 
		expected="Aparece la página de resultado del pago a nivel de Yandex")
	public static void inputDataAndPay(String paymentCode, String importe, WebDriver driver) {
		PageYandexMoney.inputPaymentCode(paymentCode, driver);
		PageYandexMoney.inputImport(importe, driver);
		PageYandexMoney.clickPayButton(driver);
		PageYandexMoneyResultSteps.validateIsResultOk(driver);
	}
	
	@Step (
		description="Cerramos la actual pestaña con nombre <b>#{tabTitle}</b>", 
		expected="Desaparece la pestaña")
	public static void closeTabByTitle(String tabTitle, String windowHandlePageToSwitch, WebDriver driver) {
		PageYandexMoney.closeActualTabByTitle(tabTitle, driver);
		driver.switchTo().window(windowHandlePageToSwitch);
	}
}
