package com.mng.robotest.test80.mango.test.stpv.votf;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageSelectLineaVOTF;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;

@SuppressWarnings("javadoc")
public class PageSelectLineaVOTFStpV {

    public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) { 
        String descripValidac = 
            "1) Aparece el banner correspondiente a SHE<br>" +
            "2) Aparece el banner correspondiente a MAN<br>" +
            "3) Aparece el banner correspondiente a NIÑAS<br>" +
            "4) Aparece el banner correspondiente a NIÑOS<br>" +
            "5) Aparece el banner correspondiente a VIOLETA";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageSelectLineaVOTF.isBannerPresent(LineaType.she, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!PageSelectLineaVOTF.isBannerPresent(LineaType.he, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (!PageSelectLineaVOTF.isBannerPresent(LineaType.nina, dFTest.driver))
                fmwkTest.addValidation(3, State.Warn, listVals);
            //4)
            if (!PageSelectLineaVOTF.isBannerPresent(LineaType.nino, dFTest.driver))
                fmwkTest.addValidation(4, State.Warn, listVals);            
            //5)
            if (!PageSelectLineaVOTF.isBannerPresent(LineaType.violeta, dFTest.driver))
                fmwkTest.addValidation(5, State.Warn, listVals);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void selectMenuAndLogoMango(int numMenu, Pais pais, DataFmwkTest dFTest) throws Exception {
        //Step. 
        datosStep datosStep = new datosStep(
            "Seleccionar el " + numMenu + "o menu de " + LineaType.she + " y finalmente seleccionar el logo de Mango",
            "Aparece la página inicial de SHE");
        try {
            PageSelectLineaVOTF.clickBanner(LineaType.she, dFTest.driver);
            PageSelectLineaVOTF.clickMenu(LineaType.she, numMenu, dFTest.driver);
            SecCabecera.getNew(Channel.desktop, AppEcom.votf, dFTest.driver)
            	.clickLogoMango();
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        SectionBarraSupVOTFStpV.validate(pais.getAccesoVOTF().getUsuario(), datosStep, dFTest);
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }
}
