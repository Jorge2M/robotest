package com.mng.robotest.test80.mango.test.stpv.votf;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.votf.SectionBarraSupVOTF;


public class SectionBarraSupVOTFStpV {

    public static void validate(String usuarioVOTF, DatosStep datosStep, DataFmwkTest dFTest) {
        String usuarioLit = SectionBarraSupVOTF.titleUserName + usuarioVOTF;
        String descripValidac = 
            "1) En la barra superior figura un \"" + usuarioLit + "\"";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SectionBarraSupVOTF.isPresentUsuario(usuarioVOTF, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }    
}
