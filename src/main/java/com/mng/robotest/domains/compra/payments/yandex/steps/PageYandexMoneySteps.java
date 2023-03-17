package com.mng.robotest.domains.compra.payments.yandex.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.yandex.pageobjects.PageYandexMoney;

public class PageYandexMoneySteps extends StepBase {

	private final PageYandexMoney pageYandexMoney = new PageYandexMoney();
	
	private static final String TAG_URL_YANDEX = "@TagUrlYandex";
	
	@Step (
		description="Accedemos a la URL de <b>YandexMoney</b>: " + TAG_URL_YANDEX, 
		expected="Aparece la página de YandexMoney")
	public void accessInNewTab(String tabTitle) throws Exception {
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_URL_YANDEX, PageYandexMoney.URL_ACCESS);
		pageYandexMoney.goToPageInNewTab(tabTitle);
		checkIsPage();
	}
	
	@Validation
	private ChecksTM checkIsPage() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece el input para el <b>Payment Code</b>",
			pageYandexMoney.isVisibleInputPaymentCode(), State.Warn);
	 	
	 	checks.add(
			"Aparece el input para el importe",
			pageYandexMoney.isVisibleInputImport(), State.Warn);
	 	
	 	return checks;
	}
	
	@Step (
		description="Introducimos el paymentCode <b>#{paymentCode}</b>, el importe <b>#{importe}</b> y pulsamos el botón de Pago", 
		expected="Aparece la página de resultado del pago a nivel de Yandex")
	public void inputDataAndPay(String paymentCode, String importe) {
		pageYandexMoney.inputPaymentCode(paymentCode);
		pageYandexMoney.inputImport(importe);
		pageYandexMoney.clickPayButton();
		new PageYandexMoneyResultSteps().validateIsResultOk();
	}
	
	@Step (
		description="Cerramos la actual pestaña con nombre <b>#{tabTitle}</b>", 
		expected="Desaparece la pestaña")
	public void closeTabByTitle(String tabTitle, String windowHandlePageToSwitch) {
		pageYandexMoney.closeActualTabByTitle(tabTitle);
		driver.switchTo().window(windowHandlePageToSwitch);
	}
}
