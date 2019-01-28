package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras;

@SuppressWarnings("javadoc")
public class SecQuickViewArticuloStpV {
    
    @SuppressWarnings("static-access")
    public static void validateIsOk(ArticuloScreen articulo, DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece el quickview correspondiente al artículo (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Se muestra la referencia " + articulo.getReferencia() + "<br>" +
            "3) Se muestra el nombre " + articulo.getNombre() + "<br>" +
            "4) Se muestra el precio " + articulo.getPrecio();
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMisCompras.SecQuickViewArticulo.isVisibleUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (PageMisCompras.SecQuickViewArticulo.getReferencia(dFTest.driver).compareTo(articulo.getReferencia())!=0)
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (PageMisCompras.SecQuickViewArticulo.getNombre(dFTest.driver).compareTo(articulo.getNombre())!=0)
                fmwkTest.addValidation(3, State.Warn, listVals);
            //4)
            if (!PageMisCompras.SecQuickViewArticulo.getPrecio(dFTest.driver).contains(articulo.getPrecio()))
                fmwkTest.addValidation(4, State.Warn, listVals);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}
