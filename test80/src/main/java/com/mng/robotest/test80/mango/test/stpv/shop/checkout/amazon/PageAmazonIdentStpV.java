package com.mng.robotest.test80.mango.test.stpv.shop.checkout.amazon;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.amazon.PageAmazonIdent;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageAmazonIdentStpV {
    
    public static void validateIsPage(Pais pais, Channel channel, DataPedido dataPedido, DatosStep datosStep, DataFmwkTest dFTest) {
        String validacion3 = "";
        if (channel==Channel.desktop)
            validacion3 = "3) En la página resultante figura el importe total de la compra (" + dataPedido.getImporteTotal() + ")";
        
        String descripValidac = 
            "1) Aparece una página con el logo de Amazon<br>" +
            "2) Aparece los campos para la identificación (usuario/password)" +
            validacion3;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageAmazonIdent.isLogoAmazon(dFTest.driver)) 
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!PageAmazonIdent.isPageIdent(dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
                            
            if (channel==Channel.desktop) {
                //3)
                if (!ImporteScreen.isPresentImporteInScreen(dataPedido.getImporteTotal(), pais.getCodigo_pais(), dFTest.driver)) 
                    fmwkTest.addValidation(3,State.Warn, listVals);
            }

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}
