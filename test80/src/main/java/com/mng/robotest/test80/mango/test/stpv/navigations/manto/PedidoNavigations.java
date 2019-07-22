package com.mng.robotest.test80.mango.test.stpv.navigations.manto;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.arq.utils.otras.TypeAccessFmwk;
import com.mng.robotest.test80.data.TestMakerContext;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageDetallePedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos.TypeDetalle;
import com.mng.robotest.test80.mango.test.stpv.manto.DataMantoAccess;
import com.mng.robotest.test80.mango.test.stpv.manto.PageBolsasMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageLoginMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageMenusMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageSelTdaMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.SecFiltrosMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.SecFiltrosMantoStpV.TypeSearch;
import com.mng.robotest.test80.mango.test.stpv.manto.pedido.PageConsultaPedidoBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.pedido.PageGenerarPedidoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.pedido.PagePedidosMantoStpV;
import static com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido.EstadoPedido.*;

public class PedidoNavigations {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);

    public static void testPedidosEnManto(DataCheckPedidos dataCheckPedidos, AppEcom appE, DataFmwkTest dFTest) throws Exception {
    	//En el caso de Votf se ha de realizar un paso manual para que los pedidos aparezcan en Manto
    	if (appE!=AppEcom.votf) {  
	        DataMantoAccess dMantoAcc = new DataMantoAccess();
	        dMantoAcc.urlManto = dFTest.ctx.getCurrentXmlTest().getParameter(Constantes.paramUrlmanto);
	        dMantoAcc.userManto = dFTest.ctx.getCurrentXmlTest().getParameter(Constantes.paramUsrmanto);
	        dMantoAcc.passManto = dFTest.ctx.getCurrentXmlTest().getParameter(Constantes.paramPasmanto);
	        dMantoAcc.appE = appE;
	        testPedidosEnManto(dMantoAcc, dataCheckPedidos, dFTest);
    	}
    }
    
    private static void testPedidosEnManto(DataMantoAccess dMantoAcc, DataCheckPedidos dataCheckPedidos, DataFmwkTest dFTest) 
    throws Exception {
        //Si existen pedidos que validar y no se trata de un acceso desde la línea de comandos (típicamente .bat)
        TestMakerContext tMakerCtx = TestCaseData.getTestMakerContext(dFTest.ctx);
        TypeAccessFmwk typeAccess = tMakerCtx.getInputData().getTypeAccess();
        if (dataCheckPedidos.areChecksToExecute() && typeAccess!=TypeAccessFmwk.Bat) {
            PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, dFTest.driver);
            PedidoNavigations.validacionListPedidosStpVs(dataCheckPedidos, dMantoAcc.appE, dFTest.driver);
        }
    }
    
    /**
     * Partiendo de la página de menús, ejecutamos todos los pasos/validaciones para validar una lista de pedidos
     * @param listPaisPedido lista de pedidos a validar
     */
    public static void validacionListPedidosStpVs(DataCheckPedidos dataCheckPedidos, AppEcom appE, WebDriver driver) 
    throws Exception {
    	List<CheckPedido> listChecks = dataCheckPedidos.getListChecks();
        for (DataPedido dataPedido : dataCheckPedidos.getListPedidos()) {
            if (dataPedido.isResultadoOk()) {
                try {
                    validaPedidoStpVs(dataPedido, listChecks, appE, driver);
                }
                catch (Exception e) {
                    pLogger.warn("Problem in validation of Pedido", e);
                }      
            }
        }
    }    
    
    /**
     * Se ejecuta todo el flujo de pasos/validaciones para validar un pedido concreto y volvemos a la página de pedidos
     */
    public static void validaPedidoStpVs(DataPedido dataPedido, List<CheckPedido> listChecks, AppEcom app, WebDriver driver) 
    throws Exception {
        PageSelTdaMantoStpV.selectTienda(dataPedido.getCodigoAlmacen(), dataPedido.getCodigoPais(), app, driver);
        if (listChecks.contains(CheckPedido.consultarBolsa)) {
        	consultarBolsaStpV(dataPedido, app, driver);
        }
        
        if (app!=AppEcom.votf) {
	        if (listChecks.contains(CheckPedido.consultarPedido)) {
	        	consultarPedidoStpV(dataPedido, app, driver);	
	        }
	        
	        if (listChecks.contains(CheckPedido.anular)) {
	        	anularPedidoStpV(dataPedido, app, driver);
	        }
        }
        
        if (listChecks.contains(CheckPedido.consultarBolsa)) {
        	
        }
    }
    
    private static void consultarBolsaStpV(DataPedido dataPedido, AppEcom app, WebDriver driver) throws Exception {
        PageMenusMantoStpV.goToBolsas(driver);
        SecFiltrosMantoStpV.setFiltrosHoyYbuscar(dataPedido, TypeSearch.BOLSA, driver);
        boolean existLinkPedido = PageBolsasMantoStpV.validaLineaBolsa(dataPedido, app, driver).getExistsLinkCodPed();
        if (existLinkPedido) {
            PageConsultaPedidoBolsaStpV.detalleFromListaPedBol(dataPedido, TypeDetalle.bolsa, app, driver);
        }
    }
    
    private static void consultarPedidoStpV(DataPedido dataPedido, AppEcom app, WebDriver driver) throws Exception {
        PageMenusMantoStpV.goToPedidos(driver);
        SecFiltrosMantoStpV.setFiltrosHoyYbuscar(dataPedido, TypeSearch.PEDIDO, driver);
        boolean existLinkPedido = PagePedidosMantoStpV.validaLineaPedido(dataPedido, app, driver).getExistsLinkCodPed();
        if (existLinkPedido) { 
            PageConsultaPedidoBolsaStpV.detalleFromListaPedBol(dataPedido, TypeDetalle.pedido, app, driver);
        }
    }
    
    private static void anularPedidoStpV(DataPedido dataPedido, AppEcom app, WebDriver driver) throws Exception {
    	if (!PageDetallePedido.isPage(dataPedido.getCodigoPedidoManto(), driver)) {
    		consultarPedidoStpV(dataPedido, app, driver);
    	}
    	
    	PageConsultaPedidoBolsaStpV.clickButtonIrAGenerar(dataPedido.getCodigoPedidoManto(), driver);
    	PageGenerarPedidoStpV.changePedidoToEstado(ANULADO, driver);
    }
}
