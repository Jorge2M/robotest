package com.mng.robotest.domains.compra.payments.yandex;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.yandex.steps.PageYandex1rstSteps;
import com.mng.robotest.domains.compra.payments.yandex.steps.PageYandexMoneySteps;
import com.mng.robotest.domains.compra.payments.yandex.steps.PageYandexPayingByCodeSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoYandex extends PagoSteps {
	
	public PagoYandex(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = this.dataPago.getDataPedido();
		
		PageYandex1rstSteps pageYandex1rstSteps = new PageYandex1rstSteps();
		pageYandex1rstSteps.validateIsPage(dataPedido.getEmailCheckout(), dataPedido.getImporteTotal(), dataTest.getCodigoPais());
		if (execPay) {
			this.dataPago.getDataPedido().setCodtipopago("?");
			String telefono = "+7 900 000 00 00";
			String paymentCode = pageYandex1rstSteps.inputTlfnAndclickContinuar(telefono, dataPedido.getImporteTotal());			
			String windowHandlePageYandex1rst = driver.getWindowHandle();

			if (pageYandex1rstSteps.hasFailed()) {
				paymentCode = pageYandex1rstSteps.retry(dataPedido.getImporteTotal(), dataTest.getCodigoPais());
			}

			String tabNameYandexMoney = "yandexMoney";
			PageYandexMoneySteps pageYandexMoneySteps = new PageYandexMoneySteps();
			pageYandexMoneySteps.accessInNewTab(tabNameYandexMoney);
			pageYandexMoneySteps.inputDataAndPay(paymentCode, dataPedido.getImporteTotal());
			pageYandexMoneySteps.closeTabByTitle(tabNameYandexMoney, windowHandlePageYandex1rst);
			new PageYandexPayingByCodeSteps().clickBackToMango();
		}
	}	
}
