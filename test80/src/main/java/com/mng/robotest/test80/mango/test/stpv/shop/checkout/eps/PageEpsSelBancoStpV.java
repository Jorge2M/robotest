package com.mng.robotest.test80.mango.test.stpv.shop.checkout.eps;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps.PageEpsSelBanco;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageEpsSelBancoStpV {

    public static void validateIsPage(String importeTotal, String codPais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Figura el icono correspondiente al pago <b>EPS</b><br>" +
            "2) Aparece el importe de la compra: " + importeTotal + "<br>" +
            "3) Aparece el logo del banco seleccionado";
        datosStep.setNOKstateByDefault();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageEpsSelBanco.isPresentIconoEps(dFTest.driver)) {
                listVals.add(1,State.Warn);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                if (channel==Channel.movil_web) {
                    listVals.add(2, State.Info);
                }
                else {
                    listVals.add(2, State.Warn);
                }
            }
            if (!PageEpsSelBanco.isVisibleIconoBanco(dFTest.driver)) {
                listVals.add(3, State.Warn);
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
    