package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras;


public class SecQuickViewArticuloStpV {
    
    @SuppressWarnings("static-access")
    public static void validateIsOk(ArticuloScreen articulo, DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece el quickview correspondiente al artículo (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Se muestra la referencia " + articulo.getReferencia() + "<br>" +
            "3) Se muestra el nombre " + articulo.getNombre() + "<br>" +
            "4) Se muestra el precio " + articulo.getPrecio();
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageMisCompras.SecQuickViewArticulo.isVisibleUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (PageMisCompras.SecQuickViewArticulo.getReferencia(dFTest.driver).compareTo(articulo.getReferencia())!=0) {
                listVals.add(2, State.Warn);
            }
            if (PageMisCompras.SecQuickViewArticulo.getNombre(dFTest.driver).compareTo(articulo.getNombre())!=0) {
                listVals.add(3, State.Warn);
            }
            if (!PageMisCompras.SecQuickViewArticulo.getPrecio(dFTest.driver).contains(articulo.getPrecio())) {
                listVals.add(4, State.Warn);
            }
                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
