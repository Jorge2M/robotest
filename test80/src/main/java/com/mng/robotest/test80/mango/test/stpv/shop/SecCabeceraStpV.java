package com.mng.robotest.test80.mango.test.stpv.shop;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraDesktop;

@SuppressWarnings("javadoc")
public class SecCabeceraStpV {

	private final SecCabecera secCabecera;
	private final DataFmwkTest dFTest;
	private final DataCtxShop dCtxSh;
	
	private SecCabeceraStpV(DataCtxShop dCtxSh, DataFmwkTest dFTest) {
		this.dFTest = dFTest;
		this.dCtxSh = dCtxSh;
		this.secCabecera = SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
	}
	
	public static SecCabeceraStpV getNew(DataCtxShop dCtxSh, DataFmwkTest dFTest) {
		return (new SecCabeceraStpV(dCtxSh, dFTest));
	}
	
    public void validaLogoDesktop(LineaType lineaType, DatosStep datosStep) {
        //Validaciones.
    	int maxSecondsWait = 1;
        String descripValidac = 
            "1) Aparece el logo/link correcto correspondiente al canal, país, línea " +
               "(esperamos hasta " + maxSecondsWait + " segundos)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!((SecCabeceraDesktop)secCabecera).isPresentLogoCorrectUntil(dCtxSh.pais, lineaType, maxSecondsWait)) {
                listVals.add(1, State.Warn);
            }
                
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    /**
     * Selecciona el logo superior de Mango
     */
    public void selecLogo() throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el logo de Mango", 
            "Se accede a la página principal de la línea");
        datosStep.setGrabImage(true);
        try {
            secCabecera.clickLogoMango();
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }              
    }    
    
    public void validateIconoBolsa(DatosStep datosStep) {
        String validacion1 = "";
        if ("true".compareTo(dCtxSh.pais.getShop_online())==0)
            validacion1 = "<b>Sí</b> es posible comprar (aparece la capa relacionada con la bolsa)";
        else
            validacion1 = "<b>No</b> es posible comprar (no aparece la capa relacionada con la bolsa)";
    
        String descripValidac = 
            "1) " + validacion1;
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if ("true".compareTo(dCtxSh.pais.getShop_online())==0) {
                if (!secCabecera.isVisibleIconoBolsa()) {
                    listVals.add(1, State.Warn);
                }
            }
            else {
                if (secCabecera.isVisibleIconoBolsa()) {
                    listVals.add(1, State.Warn);
                }
            }
    
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
