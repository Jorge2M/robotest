package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoDatosTrj.TypePant;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago.PageMercpago1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago.PageMercpagoConfStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago.PageMercpagoDatosTrjStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago.PageMercpagoLoginStpV;

public class PagoMercadopago extends PagoStpV {

	private final static String codigoSeguridad = "123";
	
    public PagoMercadopago(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
        dCtxPago = checkoutFlow.checkout(From.MetodosPago);
        int maxSeconds = 5;
        PageMercpago1rstStpV.validateIsPageUntil(maxSeconds, driver);
        PageMercpago1rstStpV.clickLinkRegistration(driver);
        if (execPay) {
            PageMercpagoLoginStpV.loginMercadopago(dataPedido.getPago(), dCtxSh.channel, driver);
            PageMercpagoDatosTrjStpV pageMercpagoDatosTrjStpV = PageMercpagoDatosTrjStpV.newInstance(dCtxSh.channel, driver);
            if (pageMercpagoDatosTrjStpV.getPageObject().getTypeInput()==TypePant.inputDataTrjNew) {
            	fluxFromInputDataTrj(dataPedido, pageMercpagoDatosTrjStpV);
            }
            else {
            	pageMercpagoDatosTrjStpV.inputCvcAndPay(codigoSeguridad);
            }
            
            dataPedido.setCodtipopago("D");
        }
    }
    
    private void fluxFromInputDataTrj(DataPedido dataPedido, PageMercpagoDatosTrjStpV pageMercpagoDatosTrjStpV) 
    throws Exception {
        pageMercpagoDatosTrjStpV.inputNumTarjeta(dataPedido.getPago().getNumtarj());
        PageMercpagoDatosTrjStpV.InputData inputData = new PageMercpagoDatosTrjStpV.InputData();
        inputData.setMesVencimiento(dataPedido.getPago().getMescad());
        inputData.setAnyVencimiento(dataPedido.getPago().getAnycad());
        inputData.setCodigoSeguridad(codigoSeguridad);
        pageMercpagoDatosTrjStpV.inputDataAndPay(inputData);
        PageMercpagoConfStpV.clickPagar(dCtxSh.channel, driver);
    }
}
