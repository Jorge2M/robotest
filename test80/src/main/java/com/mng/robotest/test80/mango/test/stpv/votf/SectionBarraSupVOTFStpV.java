package com.mng.robotest.test80.mango.test.stpv.votf;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.votf.SectionBarraSupVOTF;

@SuppressWarnings("javadoc")
public class SectionBarraSupVOTFStpV {

    public static void validate(String usuarioVOTF, DatosStep datosStep, DataFmwkTest dFTest) {
        String usuarioLit = SectionBarraSupVOTF.titleUserName + usuarioVOTF;
        String descripValidac = 
            "1) En la barra superior figura un \"" + usuarioLit + "\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SectionBarraSupVOTF.isPresentUsuario(usuarioVOTF, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }    
}
