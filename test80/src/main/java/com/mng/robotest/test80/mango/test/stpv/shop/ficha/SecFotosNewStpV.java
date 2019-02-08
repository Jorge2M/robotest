package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.DataFoto;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecFotosNew;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.TipoImagenProducto;

@SuppressWarnings("javadoc")
public class SecFotosNewStpV {

    public static void validaLayoutFotosNew(boolean isFichaAccesorios, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac = 
            "1) La 1a foto es de tipo <b>" + TipoImagenProducto.DETALLES + " o " + TipoImagenProducto.OUTFIT + " o " + TipoImagenProducto.BODEGON + "</b>";
        datosStep.setStateIniValidations();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            DataFoto dataFoto = SecFotosNew.getDataFoto(1/*line*/, 1/*int position*/, dFTest.driver);
            if (dataFoto==null || (dataFoto.typeImage!=TipoImagenProducto.DETALLES 
            						&& dataFoto.typeImage!=TipoImagenProducto.OUTFIT
            						&& dataFoto.typeImage!=TipoImagenProducto.BODEGON) ) {
                listVals.add(1, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }        
        
        int numFotosExpected1rstLine_A = 1;
        int numFotosExpected1rstLine_B = 2;
        if (isFichaAccesorios) {
            numFotosExpected1rstLine_A = 2;
            numFotosExpected1rstLine_B = 2;
        }
            
        descripValidac = 
            "1) La 1a línea tiene " + numFotosExpected1rstLine_A + " o " + numFotosExpected1rstLine_B + " fotos<br>" +
            "2) La última línea tiene < 5 fotos<br>" +        
            "3) Las líneas intermedias (si las hay) tienen 2 fotos";
        datosStep.setStateIniValidations();
        listVals = ListResultValidation.getNew(datosStep);
        try {
            int numFotos1rstLine = SecFotosNew.getNumFotosLine(1, dFTest.driver);
            if (numFotos1rstLine!=numFotosExpected1rstLine_A &&
            	numFotos1rstLine!=numFotosExpected1rstLine_B) {
                listVals.add(1, State.Warn);
            }
            int numLinesFotos = SecFotosNew.getNumLinesFotos(dFTest.driver);
            if (numLinesFotos>1) {
                int numFotosLastLine = SecFotosNew.getNumFotosLine(numLinesFotos, dFTest.driver);                
                if (numFotosLastLine > 4) {
                    listVals.add(2, State.Warn);
                }
            }
            //3)
            if (numLinesFotos>2) {
                for (int i=2; i<numLinesFotos; i++) {
                    int numFotosLine = SecFotosNew.getNumFotosLine(i, dFTest.driver);
                    if (numFotosLine!=2) {
                        listVals.add(3, State.Warn);
                    }
                }
            }
            
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
}
