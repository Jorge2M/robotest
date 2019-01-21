package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraOnline;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageDetallePedido.DetallePedido;

@SuppressWarnings("javadoc")
public class PageDetallePedidoStpV {
	PageDetallePedido pageDetalle;
	
    public PageDetallePedidoStpV(WebDriver driver) {
		PageDetallePedido pageDetalle = DetallePedido.New.getPageObject();
		if (pageDetalle.isPage(driver))
			this.pageDetalle = pageDetalle;
		else
			this.pageDetalle = DetallePedido.Old.getPageObject();
    }
    
    public PageDetallePedido getPageDetalle() {
    	return this.pageDetalle;
    }
    
    public void validateIsPageOk(CompraOnline compraOnline, String codPais, datosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        String codPedido = compraOnline.numPedido;
        String importeTotal = compraOnline.importe.replaceAll("[^\\d.,]", "");  //Eliminamos la divisa;
        validateIsPageOk(codPedido, importeTotal, codPais, datosStep, dFTest);
        
        //+Validaciones
        int maxSecondsToWait = 2;
        String descripValidac =
        	"1) Es visible alguna prenda (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparecen " + compraOnline.numPrendas + " prendas"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);                 
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!pageDetalle.isVisiblePrendaUntil(maxSecondsToWait, dFTest.driver)) 
                fmwkTest.addValidation(1, State.Info, listVals);            
            //2)
            if (pageDetalle.getNumPrendas(dFTest.driver)!=compraOnline.numPrendas)
                fmwkTest.addValidation(2, State.Warn, listVals);
                                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals); 
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
        
    public void validateIsPageOk(DataPedido dataPedido, AppEcom app, datosStep datosStep, DataFmwkTest dFTest) {
        String codPedido = dataPedido.getCodpedido();
        String importeTotalManto = dataPedido.getImporteTotalManto();
        String codPais = dataPedido.getCodigoPais();
        
//    	//TODO tratamiento específico temporal para el entorno de CI con Adyen -> Level.Info 
//    	//(hasta que dispongamos de la CI que despliega Adyen y el resto de artefactos satelitales)
//        boolean isAdyenAndCI = (dataPedido.getPago().isAdyen() && UtilsMangoTest.isEntornoCI(app, dFTest));
        validateIsPageOk(codPedido, importeTotalManto, codPais, datosStep, dFTest);
    }
    
    private void validateIsPageOk(String codPedido, String importeTotalWithoutCurrency, String codPais, 
    							  datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de detalle del pedido<br>" + 
            "2) En la página figura el Nº de pedido: " + codPedido + "<br>" +
            "3) Como total figura el importe: " + importeTotalWithoutCurrency;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);                 
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!pageDetalle.isPage(dFTest.driver)) {
//            	//TODO tratamiento específico temporal para el entorno de CI con Adyen -> Level.Info 
//            	//(hasta que dispongamos de la CI que despliega Adyen y el resto de artefactos satelitales)
//                if (isAdyenAndCI)
//                	fmwkTest.addValidation(1, State.Info, listVals);
//                else
                	fmwkTest.addValidation(1, State.Warn, listVals);
            }
            //2)
            if (!dFTest.driver.getPageSource().contains(codPedido)) 
                fmwkTest.addValidation(2, State.Info, listVals);
            //3)
            if (!pageDetalle.isPresentImporteTotal(importeTotalWithoutCurrency, codPais, dFTest.driver))
                fmwkTest.addValidation(3, State.Info, listVals);
                                                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals); 
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public datosStep clickBackButton(Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step.
        datosStep datosStep = new datosStep (
            "Seleccionar el botón \"Volver\"", 
            "Se vuelve a la página anterior");
        try {
            pageDetalle.clickBackButton(channel, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
