package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypeTarj;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d.PageD3DLoginStpV;

public class PagoBancontact extends PagoStpV {
    
    public PagoBancontact(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        boolean isD3D = true;
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        this.dCtxPago.getFTCkout().trjGuardada = false;
        
        if (execPay) {
        	PageCheckoutWrapperStpV.inputDataTrjAndConfirmPago(this.dCtxPago, dCtxSh.channel, driver);
            DataPedido dataPedido = this.dCtxPago.getDataPedido(); 
            dataPedido.setCodtipopago("U");
            Pago pago = dataPedido.getPago();
            //En el caso de Bancontact siempre saltará el D3D
            if (dataPedido.getPago().getTipotarjEnum()==TypeTarj.VISAD3D) {
            	PageD3DLoginStpV pageD3DLoginStpV = new PageD3DLoginStpV(driver);
                isD3D = pageD3DLoginStpV.validateIsD3D(1);
                pageD3DLoginStpV.isImporteVisible(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais());
                if (isD3D) {
                    dataPedido.setCodtipopago("Y");
                    pageD3DLoginStpV.loginAndClickSubmit(pago.getUsrd3d(), pago.getPassd3d());
                }
            }
        }
    }    
}
