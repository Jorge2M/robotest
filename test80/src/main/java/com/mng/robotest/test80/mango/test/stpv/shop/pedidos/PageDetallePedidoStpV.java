package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
    
    public void validateIsPageOk(CompraOnline compraOnline, String codPais, DatosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        String codPedido = compraOnline.numPedido;
        String importeTotal = compraOnline.importe.replaceAll("[^\\d.,]", "");  //Eliminamos la divisa;
        validateIsPageOk(codPedido, importeTotal, codPais, datosStep, dFTest);
        
        //+Validaciones
        int maxSecondsToWait = 2;
        String descripValidac =
        	"1) Es visible alguna prenda (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparecen " + compraOnline.numPrendas + " prendas"; 
        datosStep.setNOKstateByDefault(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);               
        try { 
            if (!pageDetalle.isVisiblePrendaUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Info);            
            }
            if (pageDetalle.getNumPrendas(dFTest.driver)!=compraOnline.numPrendas) {
                listVals.add(2, State.Warn);
            }
                                                                
            datosStep.setListResultValidations(listVals); 
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
        
    public void validateIsPageOk(DataPedido dataPedido, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) {
        String codPedido = dataPedido.getCodpedido();
        String importeTotalManto = dataPedido.getImporteTotalManto();
        String codPais = dataPedido.getCodigoPais();
        
//    	//TODO tratamiento específico temporal para el entorno de CI con Adyen -> Level.Info 
//    	//(hasta que dispongamos de la CI que despliega Adyen y el resto de artefactos satelitales)
//        boolean isAdyenAndCI = (dataPedido.getPago().isAdyen() && UtilsMangoTest.isEntornoCI(app, dFTest));
        validateIsPageOk(codPedido, importeTotalManto, codPais, datosStep, dFTest);
    }
    
    private void validateIsPageOk(String codPedido, String importeTotalWithoutCurrency, String codPais, 
    							  DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de detalle del pedido<br>" + 
            "2) En la página figura el Nº de pedido: " + codPedido + "<br>" +
            "3) Como total figura el importe: " + importeTotalWithoutCurrency;
        datosStep.setNOKstateByDefault();       
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!pageDetalle.isPage(dFTest.driver)) {
//            	//TODO tratamiento específico temporal para el entorno de CI con Adyen -> Level.Info 
//            	//(hasta que dispongamos de la CI que despliega Adyen y el resto de artefactos satelitales)
//                if (isAdyenAndCI)
//                	listVals.add(1, State.Info);
//                else
                	listVals.add(1, State.Warn);
            }
            if (!dFTest.driver.getPageSource().contains(codPedido)) { 
                listVals.add(2, State.Info);
            }
            if (!pageDetalle.isPresentImporteTotal(importeTotalWithoutCurrency, codPais, dFTest.driver)) {
                listVals.add(3, State.Info);
            }
                                                            
            datosStep.setListResultValidations(listVals); 
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public DatosStep clickBackButton(Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep (
            "Seleccionar el botón \"Volver\"", 
            "Se vuelve a la página anterior");
        try {
            pageDetalle.clickBackButton(channel, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        return datosStep;
    }
}
