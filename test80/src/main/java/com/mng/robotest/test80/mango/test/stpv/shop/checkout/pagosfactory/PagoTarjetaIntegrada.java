package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d.PageD3DJPTestSelectOptionStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d.PageD3DLoginStpV;

@SuppressWarnings("javadoc")
public class PagoTarjetaIntegrada extends PagoStpV {

    public PagoTarjetaIntegrada(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        DatosStep datosStep = PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        
        if (execPay) {
            dataPedido.setCodtipopago("U");
            datosStep = PageCheckoutWrapperStpV.inputDataTrjAndConfirmPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
            switch (dataPedido.getPago().getTipotarjEnum()) {
            case VISAD3D:
                boolean isD3D = PageD3DLoginStpV.validateIsPage(dataPedido.getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), datosStep, this.dFTest);
                dataPedido.setCodtipopago("Y");
                if (isD3D)
                    datosStep = PageD3DLoginStpV.loginAndClickSubmit(dataPedido.getPago().getUsrd3d(), dataPedido.getPago().getPassd3d(), this.dFTest);
                
                break;
            case VISAD3D_JP:
                boolean isD3DJP = PageD3DJPTestSelectOptionStpV.validateIsPage(dataPedido.getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), datosStep, this.dFTest);
                dataPedido.setCodtipopago("Y");
                if (isD3DJP)
                    datosStep = PageD3DJPTestSelectOptionStpV.clickSubmitButton(this.dFTest);
                
                break;
            case VISAESTANDAR:
            default:
            }
            
            

        }
        
        return datosStep;
    }
}
