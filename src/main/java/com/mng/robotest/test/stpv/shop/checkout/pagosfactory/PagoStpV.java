package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import static com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.BuilderCheckout;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.stpv.shop.checkout.PageCheckoutWrapperStpV;

public abstract class PagoStpV {
	
	final CheckoutFlow checkoutFlow;
	final PageCheckoutWrapperStpV pageCheckoutWrapperStpV;
	
	public DataCtxShop dCtxSh;
	public DataCtxPago dCtxPago;
	public WebDriver driver;
	public static String MsgNoPayImplemented = "No está diponible la parte del test que permite completar/ejecutar el pago";

	public boolean isAvailableExecPay = false;

	public PagoStpV(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		this.dCtxSh = dCtxSh;
		this.dCtxPago = dCtxPago;
		this.driver = driver;
		this.checkoutFlow = new BuilderCheckout(dCtxSh, dCtxPago, driver).build();
		this.pageCheckoutWrapperStpV = new PageCheckoutWrapperStpV(dCtxSh.channel, dCtxSh.appE, driver);
	}

	public boolean isAvailableExecPay() {
		return this.isAvailableExecPay;
	}

	/**
	 * @param execPay indica si, además de las pruebas iniciales de la pasarela, hemos de ejecutar el último/s pasos que confirman el pago y generan un pedido
	 * @return
	 */
	public abstract void testPagoFromCheckout(boolean execPay) throws Exception;

	public void storePedidoForMantoAndResetData() {
		this.dCtxPago.storePedidoForManto();
		this.dCtxPago.setDataPedido(new DataPedido(this.dCtxSh.pais));
	}
}

class PaymethodWithoutTestPayImplemented extends Exception {
	private static final long serialVersionUID = 1L;
	public PaymethodWithoutTestPayImplemented(String msg){
		super(msg);
	}
 }