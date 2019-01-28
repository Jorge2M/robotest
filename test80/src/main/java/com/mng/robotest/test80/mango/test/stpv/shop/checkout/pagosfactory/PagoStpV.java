package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;

@SuppressWarnings("javadoc")
public abstract class PagoStpV {
    public DataCtxShop dCtxSh;
    public DataCtxPago dCtxPago;
    public DataFmwkTest dFTest;
    public static String MsgNoPayImplemented = "No está diponible la parte del test que permite completar/ejecutar el pago";
    
    public boolean isAvailableExecPay = false;
    
    public PagoStpV(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        this.dCtxSh = dCtxSh;
        this.dCtxPago = dCtxPago;
        this.dFTest = dFTest;
    }
    
    public boolean isAvailableExecPay() {
        return this.isAvailableExecPay;
    }
    
    /**
     * @param execPay indica si, además de las pruebas iniciales de la pasarela, hemos de ejecutar el último/s pasos que confirman el pago y generan un pedido
     * @return
     */
    public abstract DatosStep testPagoFromCheckout(boolean execPay) throws Exception;

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
