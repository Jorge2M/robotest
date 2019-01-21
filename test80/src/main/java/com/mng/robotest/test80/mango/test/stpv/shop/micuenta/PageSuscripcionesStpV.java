package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageSuscripciones;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageSuscripciones.idNewsletters;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;

@SuppressWarnings("javadoc")
public class PageSuscripcionesStpV {
    
    public static datosStep validaIsPage(datosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validaciones
        String descripValidac = 
            "1) Aparece la página de \"Suscripciones\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageSuscripciones.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
        
        return datosStep;
    }    
    
    public static void validaIsDataAssociatedToRegister(HashMap<String,String> datosRegOk, datosStep datosStep, DataFmwkTest dFTest) {
        int numLineasTotales = Integer.valueOf(datosRegOk.get("numlineas")).intValue();
        String lineasUnchecked = datosRegOk.get("clicklineas");
        StringTokenizer tokensLinDesmarcadas = new StringTokenizer(lineasUnchecked, ",");
        int numLinDesmarcadas = tokensLinDesmarcadas.countTokens();
        String descripValidac = 
            "1) Aparecen "  + numLineasTotales + " Newsletter<br>" +
            "2) Aparecen "  + numLinDesmarcadas + " suscripciones desmarcadas<br>" +
            "3) Aparecen desmarcadas las suscripciones de: " + lineasUnchecked;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (PageSuscripciones.getNumNewsletters(dFTest.driver)!=numLineasTotales)
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (PageSuscripciones.getNumNewslettersDesmarcadas(dFTest.driver)!=numLinDesmarcadas) 
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            while (tokensLinDesmarcadas.hasMoreElements()) {
                String lineaStr=tokensLinDesmarcadas.nextToken();
                if (!PageSuscripciones.isNewsletterDesmarcada(lineaStr, dFTest.driver))
                    fmwkTest.addValidation(3, State.Warn, listVals);
            }
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep selectNewslettersAndGuarda(ArrayList<idNewsletters> listNewsletters, DataFmwkTest dFTest) throws Exception {
        datosStep datosStep = new datosStep (
            "Seleccionar los checkbox de las Newsletter <b>" + listNewsletters.toString() + "</b> + Botón \"Guardar Cambios\"", 
            "Aparece la confirmación que los datos se han modificado");
        try {
            //Seleccionamos los checkbox asociados a las newsletters
            for (idNewsletters idNewsletter  :listNewsletters)
                PageSuscripciones.clickRadioNewsletter(dFTest.driver, idNewsletter);
                    
            PageSuscripciones.clickGuardarCambios(dFTest.driver);
                                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
            
        //Validaciones.
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece una pantalla de resultado OK (la esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageSuscripciones.isPageResOKUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
}
