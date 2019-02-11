package com.mng.robotest.test80.mango.test.stpv.shop.checkout.amazon;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);           
        try {
            if (!PageAmazonIdent.isLogoAmazon(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PageAmazonIdent.isPageIdent(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            if (channel==Channel.desktop) {
                if (!ImporteScreen.isPresentImporteInScreen(dataPedido.getImporteTotal(), pais.getCodigo_pais(), dFTest.driver)) {
                    listVals.add(3, State.Warn);
                }
            }

            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
