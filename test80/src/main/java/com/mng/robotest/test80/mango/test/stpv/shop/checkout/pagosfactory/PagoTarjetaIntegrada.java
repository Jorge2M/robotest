package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d.PageD3DJPTestSelectOptionStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d.PageD3DLoginStpV;

public class PagoTarjetaIntegrada extends PagoStpV {

    public PagoTarjetaIntegrada(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        
        if (execPay) {
            dataPedido.setCodtipopago("U");
            PageCheckoutWrapperStpV.inputDataTrjAndConfirmPago(dCtxPago, dCtxSh.channel, dFTest);
            switch (dataPedido.getPago().getTipotarjEnum()) {
            case VISAD3D:
                boolean isD3D = PageD3DLoginStpV.validateIsD3D(1, dFTest.driver);
                PageD3DLoginStpV.isImporteVisible(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dFTest.driver);
                dataPedido.setCodtipopago("Y");
                if (isD3D) {
                    PageD3DLoginStpV.loginAndClickSubmit(dataPedido.getPago().getUsrd3d(), dataPedido.getPago().getPassd3d(), dFTest.driver);
                }
                
                break;
            case VISAD3D_JP:
            	boolean isD3DJP = PageD3DJPTestSelectOptionStpV.validateIsD3D(1, dFTest.driver);
            	PageD3DJPTestSelectOptionStpV.isImporteVisible(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dFTest.driver);
                dataPedido.setCodtipopago("Y");
                if (isD3DJP) {
                    PageD3DJPTestSelectOptionStpV.clickSubmitButton(dFTest.driver);
                }
                
                break;
            case VISAESTANDAR:
            default:
            }
        }
        
        return TestCaseData.getDatosLastStep();
    }
}
