package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageBolsas;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Bolsas en Manto
 * @author jorge.munoz
 *
 */
public class PageBolsasMantoStpV {

    /**
     * Se valida que está apareciendo una línea de bolsa con los datos del pedido
     * @return si existe el link correspondiente al código de pedido
     */
    @SuppressWarnings("javadoc")
    public static boolean validaLineaBolsa(DataPedido dataPedido, AppEcom appE, datosStep datosStep, DataFmwkTest dFTest) {
//    	//TODO tratamiento específico temporal para el entorno de CI con Adyen -> Level.Info 
//    	//(hasta que dispongamos de la CI que despliega Adyen y el resto de artefactos satelitales)
//        State levelByCIAdyen = State.Warn;
//    	if (dataPedido.getPago().isAdyen() &&
//        	UtilsMangoTest.isEntornoCI(appE, dFTest))
//    		levelByCIAdyen = State.Info;
    	
        boolean existsLinkCodPed = true;
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) En la columna 1 aparece el código de pedido: " + dataPedido.getCodigoPedidoManto() + " (lo esperamos hasta " + maxSecondsToWait + " segundos) <br>" +
            "2) Aparece una sola bolsa <br>";
        if (appE!=AppEcom.outlet) //En el caso de Outlet no tenemos la información del TPV que toca
            descripValidac+=
            "3) En la columna 8 Aparece el Tpv asociado: " + dataPedido.getPago().getTpv().getId() + "<br>";
        descripValidac+=
            "4) En la columna 7 aparece el email asociado: " + dataPedido.getEmailCheckout();
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);   
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageBolsas.presentLinkPedidoInBolsaUntil(dataPedido.getCodigoPedidoManto(), maxSecondsToWait, dFTest.driver)) {
                fmwkTest.addValidation(1, State.Warn, listVals);
                existsLinkCodPed = false;
            }
            else
                dataPedido.setIdCompra(PageBolsas.getIdCompra(dataPedido.getCodigoPedidoManto(), dFTest.driver));
            //2)
            if (PageBolsas.getNumLineas(dFTest.driver)!=1)
                fmwkTest.addValidation(2, State.Warn, listVals);            
            //3)
            if (appE!=AppEcom.outlet) {
                if (!PageBolsas.presentIdTpvInBolsa(dFTest.driver, dataPedido.getPago().getTpv().getId()))
                    fmwkTest.addValidation(3, State.Warn, listVals);
            }
            //4)
            if (!PageBolsas.presentCorreoInBolsa(dFTest.driver, dataPedido.getEmailCheckout()))
                fmwkTest.addValidation(4, State.Warn, listVals);   

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return existsLinkCodPed;
    }
}
