package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageSelTda;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageJCAS;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Login de Manto
 * @author jorge.munoz
 *
 */
public class PageLoginMantoStpV {

    @SuppressWarnings("javadoc")
    public static DatosStep login(String urlManto, String usrManto, String pasManto, DataFmwkTest dFTest) throws Exception {
        //Step. Acceder a la página de menús de Manto
        DatosStep datosStep = new DatosStep       (
            "<b style=\"color:brown;\">ACCEDER A MANTO</b> (" + urlManto + ")",
            "Aparece la página de selección de login o selección de tienda");
        datosStep.setGrab_ErrorPageIfProblem(false);
        try {
        	dFTest.driver.manage().deleteAllCookies();
            dFTest.driver.get(urlManto);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally {
            if (dFTest.ctx!=null)
                datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); 
        }

        if (!PageSelTda.isPage(dFTest.driver)) {
            //Step. Acceder a la página de menús de Manto
            datosStep = new DatosStep       (
                "Identificarse desde la página de Jasig CAS con " + usrManto,
                "Aparece la página de selección de la tienda");
            datosStep.setGrab_ErrorPageIfProblem(false);
            try {
                PageJCAS.identication(dFTest.driver, usrManto, pasManto);
                
                datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
            }
            finally { 
                if (dFTest.ctx!=null)
                    datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); 
            }
        }
        
        //Validaciones
        String descripValidac = 
            "1) Aparece la página de selección de la tienda";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageSelTda.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally {
            if (dFTest.ctx!=null)
                fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); 
        }
    
        return datosStep;
    }
}
