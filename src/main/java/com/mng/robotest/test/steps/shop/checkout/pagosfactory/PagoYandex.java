package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.yandex.PageYandex1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.yandex.PageYandexMoneySteps;
import com.mng.robotest.test.steps.shop.checkout.yandex.PageYandexPayingByCodeSteps;

public class PagoYandex extends PagoSteps {
	
	public PagoYandex(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = this.dCtxPago.getDataPedido();
		PageYandex1rstSteps.validateIsPage(dataPedido.getEmailCheckout(), dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), driver);
		if (execPay) {
			this.dCtxPago.getDataPedido().setCodtipopago("?");
			String telefono = "+7 900 000 00 00";
			String paymentCode = PageYandex1rstSteps.inputTlfnAndclickContinuar(telefono, dataPedido.getImporteTotal(), 
																			   dCtxSh.pais.getCodigo_pais(), driver);
			String windowHandlePageYandex1rst = driver.getWindowHandle();

			if (PageYandex1rstSteps.hasFailed(driver)) {
				paymentCode = PageYandex1rstSteps.retry(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), driver);
			}

			String tabNameYandexMoney = "yandexMoney";
			PageYandexMoneySteps.accessInNewTab(tabNameYandexMoney, driver);
			PageYandexMoneySteps.inputDataAndPay(paymentCode, dataPedido.getImporteTotal(), driver);
			PageYandexMoneySteps.closeTabByTitle(tabNameYandexMoney, windowHandlePageYandex1rst, driver);
			PageYandexPayingByCodeSteps.clickBackToMango(this.dCtxSh.channel, driver);
		}
	}	
}
