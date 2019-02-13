package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.WhenSave;
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
	    datosStep.setSaveErrorPage(WhenSave.Never);
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
    	    datosStep.setSaveErrorPage(WhenSave.Never);
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
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageSelTda.isPage(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                
            datosStep.setListResultValidations(listVals);
        }  
        finally {
            if (dFTest.ctx!=null) {
            	listVals.checkAndStoreValidations(descripValidac);
            }
        }
    
        return datosStep;
    }
}
