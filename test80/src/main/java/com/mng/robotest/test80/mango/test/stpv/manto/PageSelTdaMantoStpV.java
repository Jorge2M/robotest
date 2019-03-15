package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.TiendaMantoEnum.TiendaManto;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageSelTda;
import com.mng.robotest.test80.mango.test.pageobject.manto.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageMenusManto;

/**
 * Ejecución de pasos/validaciones relacionados con la página "Selección de la tienda en Manto" (la posterior al login)
 * @author jorge.munoz
 *
 */

public class PageSelTdaMantoStpV {
	
    public static DatosStep selectTienda(String codigoAlmacen, String codigoPais, AppEcom appE, DataFmwkTest dFTest) 
    throws Exception {
        TiendaManto tienda = TiendaManto.getTienda(codigoAlmacen, codigoPais, appE);
        
        //Step.
        DatosStep datosStep = new DatosStep       (
            "Seleccionamos el entorno \"" + tienda + "\"", 
            "Aparece la página de Menús");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            //Si no estamos en la página de selección de tienda vamos a ella mediante selección del botón "Seleccionar tienda"
            if (!PageSelTda.isPage(dFTest.driver))
                SecCabecera.clickButtonSelTienda(dFTest.driver);
            
            //Seleccionamos el entorno asociado al almacén (Alemania, Europa Palau...)
            PageSelTda.selectTienda(tienda, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally {
            if (dFTest.ctx!=null)
                StepAspect.storeDataAfterStep(datosStep); 
        }
        
        //Validaciones
        String descripValidac = 
            "1) Aparece la página del Menú principal de Manto donde se encuentran todas las opciones de éste";
        datosStep.setNOKstateByDefault();
        ChecksResult listVals = ChecksResult.getNew(datosStep);
        try {
            if (!PageMenusManto.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
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
    

    /**
     * Accede a la tienda asociada al almacén (sólo si no estamos en ella ya)
     */
    public static void goToTiendaPais(String codigoAlmacen, String codigoPais,  AppEcom appE, DataFmwkTest dFTest) 
    throws Exception {
        String tiendaActual = SecCabecera.getLitTienda(dFTest.driver);
        TiendaManto tiendaToGo = TiendaManto.getTienda(codigoAlmacen, codigoPais, appE);
        if (!tiendaActual.contains(tiendaToGo.litPantManto))
            selectTienda(codigoAlmacen, codigoPais, appE, dFTest);
    }
}
