package com.mng.robotest.test80.mango.test.stpv.navigations.manto;

import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.InputParams;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.conf.TypeAccessFmwk;
import com.mng.testmaker.domain.TestCaseTM;
import com.mng.testmaker.domain.TestRunTM;
import com.mng.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageDetallePedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos.TypeDetalle;
import com.mng.robotest.test80.mango.test.stpv.manto.DataMantoAccess;
import com.mng.robotest.test80.mango.test.stpv.manto.PageBolsasMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageConsultaPedidoBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageLoginMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageMenusMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PagePedidosMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageSelTdaMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.SecFiltrosMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.SecFiltrosMantoStpV.TypeSearch;

public class PedidosNavigations {
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);

    public static void testPedidosEnManto(CopyOnWriteArrayList<DataPedido> listPedidos, AppEcom appE, WebDriver driver)
    throws Exception {
    	//En el caso de Votf se ha de realizar un paso manual para que los pedidos aparezcan en Manto
    	if (appE!=AppEcom.votf) {  
			TestCaseTM testCase = TestMaker.getTestCase();
			TestRunTM testRun = testCase.getTestRunParent();
	        DataMantoAccess dMantoAcc = new DataMantoAccess();
	        dMantoAcc.urlManto = testRun.getParameter(Constantes.paramUrlmanto);
	        dMantoAcc.userManto = testRun.getParameter(Constantes.paramUsrmanto);
	        dMantoAcc.passManto = testRun.getParameter(Constantes.paramPasmanto);
	        dMantoAcc.appE = appE;
	        testPedidosEnManto(dMantoAcc, listPedidos, driver);
    	}
    }
    
    private static void testPedidosEnManto(DataMantoAccess dMantoAcc, CopyOnWriteArrayList<DataPedido> listPedidos, WebDriver driver) 
    throws Exception {
        TypeAccessFmwk typeAccess = ((InputParams)TestMaker.getTestCase().getInputParamsSuite()).getTypeAccess();
        if (typeAccess==TypeAccessFmwk.Bat) {
            return;
        }

        //Si existen pedidos que validar y no se trata de un acceso desde la línea de comandos (típicamente .bat)
        if (listPedidos!=null && listPedidos.size()>0 && typeAccess!=TypeAccessFmwk.Bat) {
            PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
            PedidosNavigations.validacionListaPagosStpVs(listPedidos, dMantoAcc.appE, driver);
        }
    }
    
    /**
     * Partiendo de la página de menús, ejecutamos todos los pasos/validaciones para validar una lista de pedidos
     * @param listPaisPedido lista de pedidos a validar
     */
    public static void validacionListaPagosStpVs(CopyOnWriteArrayList<DataPedido> listDataPedidos, AppEcom appE, WebDriver driver) 
    throws Exception {
        //Bucle para obtener la lista de Países -> Pedidos
        for (DataPedido dataPedido : listDataPedidos) {
            //Sólo consultamos el pedido si el pago se realizó de forma correcta
            if (dataPedido.getResejecucion()==State.Ok) {
                try {
                    //Ejecutamos todo el flujo de pasos/validaciones para validar un pedido concreto y volvemos a la página de pedidos
                    validaPedidoStpVs(dataPedido, appE, driver);
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
    public static void validaPedidoStpVs(DataPedido dataPedido, AppEcom appE, WebDriver driver) throws Exception {
        //Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
        PageSelTdaMantoStpV.selectTienda(dataPedido.getCodigoAlmacen(), dataPedido.getCodigoPais(), appE, driver);
        
        //Establecemos los filtros de las bolsas con el día de hoy + el pedido + el código de país asociado al pedido y pulsamos "Buscar"
        PageMenusMantoStpV.goToBolsas(driver);
        SecFiltrosMantoStpV.setFiltrosHoyYbuscar(dataPedido, TypeSearch.BOLSA, driver);
        boolean existLinkPedido = PageBolsasMantoStpV.validaLineaBolsa(dataPedido, appE, driver).getExistsLinkCodPed();
        if (existLinkPedido) {
            PageConsultaPedidoBolsaStpV.detalleFromListaPedBol(dataPedido, TypeDetalle.bolsa, appE, driver);
        }
        
        if (appE!=AppEcom.votf) {
            PageMenusMantoStpV.goToPedidos(driver);
            SecFiltrosMantoStpV.setFiltrosHoyYbuscar(dataPedido, TypeSearch.PEDIDO, driver);
            boolean existsLinkCodPed = PagePedidosMantoStpV.validaLineaPedido(dataPedido, appE, driver).getExistsLinkCodPed();    
            if (existsLinkCodPed) {
                PageConsultaPedidoBolsaStpV.detalleFromListaPedBol(dataPedido, TypeDetalle.pedido, appE, driver);
            }
        }
        
        PageDetallePedido.gotoListaPedidos(driver);
    }
}
