package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pasarelaotras;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PagePasarelaOtrasStpV {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public static void validateIsPage(String importeTotal, Pais pais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String validacion1 = "";
        if (channel==Channel.desktop)
            validacion1 = 
            "1) En la página resultante figura el importe total de la compra (" + importeTotal + ")"; 
            
        String descripValidac = 
            validacion1 + "<br>" + 
            "2) No se trata de la página de precompra (no aparece los logos de formas de pago)";
        datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (channel==Channel.desktop) {
                if (!ImporteScreen.isPresentImporteInScreen(importeTotal, pais.getCodigo_pais(), dFTest.driver)) {
                    listVals.add(1, State.Warn);
                }
            }
            if (PageCheckoutWrapper.isPresentMetodosPago(pais, channel, dFTest.driver)) {
                listVals.add(2, State.Defect);            
            }
    
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            pLogger.warn("Problem validating \"Otras\" pasarela for country {}", pais.getNombre_pais(), e);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
