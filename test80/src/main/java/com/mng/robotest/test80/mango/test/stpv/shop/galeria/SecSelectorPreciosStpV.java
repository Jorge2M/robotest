package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;

@SuppressWarnings({"javadoc", "static-access"})
public class SecSelectorPreciosStpV {

    public static void validaIsSelector(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac = 
            "1) Es visible el selector de precios"; 
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageGaleriaDesktop.secSelectorPrecios.isVisible(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    /**
     * Selecciona un intervalo de precio mínimo/precio máximo. 
     * No es posible pasar como parámetro el mínimo/máximo pues lo único que podemos hacer es 'click por la derecha' + 'click por la izquierda'
      */
    public static DatosStep seleccionaIntervalo(AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
    
        int minimoOrig = 0;
        int maximoOrig = 0;
        int minimoFinal = 0;
        int maximoFinal = 0;
        String tagMinimo = "[MINIMO]";
        String tagMaximo = "[MAXIMO]";
            
        DatosStep datosStep = new DatosStep       (
            "Utilizar el selector de precio: Mínimo=" + tagMinimo + " Máximo=" + tagMaximo, 
            "Aparecen artículos con precio en el intervalo seleccionado");
        try {
            //Obtenemos los mínimo/máximo originales
            minimoOrig = PageGaleriaDesktop.secSelectorPrecios.getImporteMinimo(dFTest.driver);
            maximoOrig = PageGaleriaDesktop.secSelectorPrecios.getImporteMaximo(dFTest.driver);
                    
            //Seleccionamos un intervalo de mínimo/máximo
            PageGaleriaDesktop.secSelectorPrecios.clickMinAndMax(30/*margenPixelsIzquierda*/, 30/*margenPixelsDerecha*/, dFTest.driver);
    
            //Obtenemos el nuevo mínimo/máximo
            minimoFinal = PageGaleriaDesktop.secSelectorPrecios.getImporteMinimo(dFTest.driver);
            maximoFinal = PageGaleriaDesktop.secSelectorPrecios.getImporteMaximo(dFTest.driver);
                    
            //Sustituímos los tags
            datosStep.setDescripcion(datosStep.getDescripcion().replace(tagMinimo, String.valueOf(minimoFinal)));
            datosStep.setDescripcion(datosStep.getDescripcion().replace(tagMaximo, String.valueOf(maximoFinal)));
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }         
            
        //Validaciones
        PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, dFTest.driver);
        String descripValidac = 
            "1) El nuevo mínimo es mayor que el anterior. Era de <b>" + minimoOrig + "</b> y ahora es <b>" + minimoFinal + "</b><br>" + 
            "2) El nuevo máximo es menor que el anterior. Era de <b>" + maximoOrig + "</b> y ahora es <b>" + maximoFinal + "</b><br>" +
            "3) Todos los precios están en el intervalo [" + minimoFinal + ", " + maximoFinal + "]";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (minimoFinal <= minimoOrig) {
                listVals.add(1, State.Warn);
            }
            if (maximoFinal >= maximoOrig) {
                listVals.add(2, State.Warn);
            }
            if (!pageGaleria.preciosInIntervalo(minimoFinal, maximoFinal)) {
                listVals.add(3, State.Warn);
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
        
        return datosStep;
    }
}
