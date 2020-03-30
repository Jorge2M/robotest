package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d.PageD3DJPTestSelectOptionStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d.PageD3DLoginStpV;

public class PagoTarjetaIntegrada extends PagoStpV {

    public PagoTarjetaIntegrada(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        
        if (execPay) {
            dataPedido.setCodtipopago("U");
        	String metodoPago = dCtxPago.getDataPedido().getPago().getNombre();
            if (dCtxPago.getFTCkout().trjGuardada && 
            	PageCheckoutWrapperStpV.isTarjetaGuardadaAvailable(metodoPago, dCtxSh.channel, driver)) {
            	PageCheckoutWrapperStpV.selectTrjGuardadaAndConfirmPago(dCtxPago, "737", dCtxSh.channel, driver);
            } else {
            	PageCheckoutWrapperStpV.inputDataTrjAndConfirmPago(dCtxPago, dCtxSh.channel, driver);
            }
            switch (dataPedido.getPago().getTipotarjEnum()) {
            case VISAD3D:
            	PageD3DLoginStpV pageD3DLoginStpV = new PageD3DLoginStpV(driver);
                boolean isD3D = pageD3DLoginStpV.validateIsD3D(1);
                pageD3DLoginStpV.isImporteVisible(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais());
                dataPedido.setCodtipopago("Y");
                if (isD3D) {
                    pageD3DLoginStpV.loginAndClickSubmit(dataPedido.getPago().getUsrd3d(), dataPedido.getPago().getPassd3d());
                }
                
                break;
            case VISAD3D_JP:
            	boolean isD3DJP = PageD3DJPTestSelectOptionStpV.validateIsD3D(1, driver);
            	PageD3DJPTestSelectOptionStpV.isImporteVisible(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), driver);
                dataPedido.setCodtipopago("Y");
                if (isD3DJP) {
                    PageD3DJPTestSelectOptionStpV.clickSubmitButton(driver);
                }
                
                break;
            case VISAESTANDAR:
            default:
            }
        }
    }
}
