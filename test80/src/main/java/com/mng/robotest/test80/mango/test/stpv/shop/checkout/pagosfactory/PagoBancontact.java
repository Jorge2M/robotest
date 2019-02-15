package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypeTarj;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d.PageD3DLoginStpV;


public class PagoBancontact extends PagoStpV {
    
    public PagoBancontact(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        boolean isD3D = true;
        DatosStep datosStep = PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        this.dCtxPago.getFTCkout().trjGuardada = false;
        
        if (execPay) {
        	datosStep = PageCheckoutWrapperStpV.inputDataTrjAndConfirmPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
            DataPedido dataPedido = this.dCtxPago.getDataPedido(); 
            dataPedido.setCodtipopago("U");
            Pago pago = dataPedido.getPago();
            //En el caso de Bancontact siempre saltará el D3D
            if (dataPedido.getPago().getTipotarjEnum()==TypeTarj.VISAD3D) {
                isD3D = PageD3DLoginStpV.validateIsPage(dataPedido.getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), datosStep, this.dFTest);                
                if (isD3D) {
                    dataPedido.setCodtipopago("Y");
                    datosStep = PageD3DLoginStpV.loginAndClickSubmit(pago.getUsrd3d(), pago.getPassd3d(), this.dFTest);
                }
            }
        }
        
        return datosStep;
    }    
}
