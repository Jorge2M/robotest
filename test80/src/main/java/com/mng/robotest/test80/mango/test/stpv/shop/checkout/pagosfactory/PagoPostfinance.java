package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfSelectChannel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfSelectChannel.ChannelPF;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance.PagePostfCodSegStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance.PagePostfSelectChannelStpV;

public class PagoPostfinance extends PagoStpV {
    
    public PagoPostfinance(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = dCtxPago.getDataPedido();
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
        pagoNavigationsStpV.aceptarCompraDesdeMetodosPago();
        String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
        String importeTotal = dataPedido.getImporteTotal();
        String codPais = this.dCtxSh.pais.getCodigo_pais();
        if (isPageSelectChannel(2)) {
        	managePageSelectChannel();
        }
        PagePostfCodSegStpV.postfinanceValidate1rstPage(nombrePago, importeTotal, codPais, driver);
        
        if (execPay) {
            dataPedido.setCodtipopago("P");
            PagePostfCodSegStpV.inputCodigoSeguridadAndAccept("11152", nombrePago, driver);
        }
    }    
    
    private boolean isPageSelectChannel(int maxSeconds) {
    	return (new PagePostfSelectChannel(driver)).isPage(maxSeconds);
    }
    
    private void managePageSelectChannel() {
    	PagePostfSelectChannelStpV pagePostfSelectChannelStpV = new PagePostfSelectChannelStpV(driver);
    	pagePostfSelectChannelStpV.checkIsPage(2);
    	pagePostfSelectChannelStpV.selectChannel(ChannelPF.Card);
    }
}
