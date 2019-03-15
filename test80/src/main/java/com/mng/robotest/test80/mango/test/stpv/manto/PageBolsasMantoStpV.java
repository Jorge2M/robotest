package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
    
    public static boolean validaLineaBolsa(DataPedido dataPedido, AppEcom appE, DataFmwkTest dFTest) {
    	DatosStep datosStep = TestCaseData.getDatosLastStep();
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
        datosStep.setNOKstateByDefault(); 
        ChecksResult listVals = ChecksResult.getNew(datosStep);
        try {
            if (!PageBolsas.presentLinkPedidoInBolsaUntil(dataPedido.getCodigoPedidoManto(), maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
                existsLinkCodPed = false;
            }
            else {
                dataPedido.setIdCompra(PageBolsas.getIdCompra(dataPedido.getCodigoPedidoManto(), dFTest.driver));
            }
            if (PageBolsas.getNumLineas(dFTest.driver)!=1) {
                listVals.add(2, State.Warn);
            }
            if (appE!=AppEcom.outlet) {
                if (!PageBolsas.presentIdTpvInBolsa(dFTest.driver, dataPedido.getPago().getTpv().getId())) {
                    listVals.add(3, State.Warn);
                }
            }
            if (!PageBolsas.presentCorreoInBolsa(dFTest.driver, dataPedido.getEmailCheckout())) {
                listVals.add(4, State.Warn);   
            }

            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return existsLinkCodPed;
    }
}
