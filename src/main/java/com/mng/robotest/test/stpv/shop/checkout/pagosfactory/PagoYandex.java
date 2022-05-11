package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.checkout.yandex.PageYandex1rstStpv;
import com.mng.robotest.test.stpv.shop.checkout.yandex.PageYandexMoneyStpV;
import com.mng.robotest.test.stpv.shop.checkout.yandex.PageYandexPayingByCodeStpV;

public class PagoYandex extends PagoStpV {
	
	public PagoYandex(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.MetodosPago);
		DataPedido dataPedido = this.dCtxPago.getDataPedido();
		PageYandex1rstStpv.validateIsPage(dataPedido.getEmailCheckout(), dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), driver);
		if (execPay) {
			this.dCtxPago.getDataPedido().setCodtipopago("?");
			String telefono = "+7 900 000 00 00";
			String paymentCode = PageYandex1rstStpv.inputTlfnAndclickContinuar(telefono, dataPedido.getImporteTotal(), 
																			   dCtxSh.pais.getCodigo_pais(), driver);
			String windowHandlePageYandex1rst = driver.getWindowHandle();

			if (PageYandex1rstStpv.hasFailed(driver)) {
				paymentCode = PageYandex1rstStpv.retry(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), driver);
			}

			String tabNameYandexMoney = "yandexMoney";
			PageYandexMoneyStpV.accessInNewTab(tabNameYandexMoney, driver);
			PageYandexMoneyStpV.inputDataAndPay(paymentCode, dataPedido.getImporteTotal(), driver);
			PageYandexMoneyStpV.closeTabByTitle(tabNameYandexMoney, windowHandlePageYandex1rst, driver);
			PageYandexPayingByCodeStpV.clickBackToMango(this.dCtxSh.channel, driver);
		}
	}	
}
