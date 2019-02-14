package com.mng.robotest.test80.mango.test.stpv.votf;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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

    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) { 
        String descripValidac = 
            "1) Aparece el banner correspondiente a SHE<br>" +
            "2) Aparece el banner correspondiente a MAN<br>" +
            "3) Aparece el banner correspondiente a NIÑAS<br>" +
            "4) Aparece el banner correspondiente a NIÑOS<br>" +
            "5) Aparece el banner correspondiente a VIOLETA";
        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageSelectLineaVOTF.isBannerPresent(LineaType.she, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PageSelectLineaVOTF.isBannerPresent(LineaType.he, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (!PageSelectLineaVOTF.isBannerPresent(LineaType.nina, dFTest.driver)) {
                listVals.add(3, State.Warn);
            }
            if (!PageSelectLineaVOTF.isBannerPresent(LineaType.nino, dFTest.driver)) {
                listVals.add(4, State.Warn);            
            }
            if (!PageSelectLineaVOTF.isBannerPresent(LineaType.violeta, dFTest.driver)) {
                listVals.add(5, State.Warn);
            }
                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static void selectMenuAndLogoMango(int numMenu, Pais pais, DataFmwkTest dFTest) throws Exception {
        //Step. 
        DatosStep datosStep = new DatosStep(
            "Seleccionar el " + numMenu + "o menu de " + LineaType.she + " y finalmente seleccionar el logo de Mango",
            "Aparece la página inicial de SHE");
        try {
            PageSelectLineaVOTF.clickBanner(LineaType.she, dFTest.driver);
            PageSelectLineaVOTF.clickMenu(LineaType.she, numMenu, dFTest.driver);
            SecCabecera.getNew(Channel.desktop, AppEcom.votf, dFTest.driver)
            	.clickLogoMango();
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        //Validaciones
        SectionBarraSupVOTFStpV.validate(pais.getAccesoVOTF().getUsuario(), datosStep, dFTest);
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }
}
