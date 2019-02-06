package com.mng.robotest.test80.mango.test.stpv.navigations.manto;

import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.robotest.test80.Test80mng.TypeAccessFmwk;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.utils;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
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

@SuppressWarnings("javadoc")
public class PedidosNavigations {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);

    public static void testPedidosEnManto(CopyOnWriteArrayList<DataPedido> listPedidos, AppEcom appE, DataFmwkTest dFTest) throws Exception {
    	//En el caso de Votf se ha de realizar un paso manual para que los pedidos aparezcan en Manto
    	if (appE!=AppEcom.votf) {  
	        DataMantoAccess dMantoAcc = new DataMantoAccess();
	        dMantoAcc.urlManto = dFTest.ctx.getCurrentXmlTest().getParameter(Constantes.paramUrlmanto);
	        dMantoAcc.userManto = dFTest.ctx.getCurrentXmlTest().getParameter(Constantes.paramUsrmanto);
	        dMantoAcc.passManto = dFTest.ctx.getCurrentXmlTest().getParameter(Constantes.paramPasmanto);
	        dMantoAcc.appE = appE;
	        testPedidosEnManto(dMantoAcc, listPedidos, dFTest);
    	}
    }
    
    private static void testPedidosEnManto(DataMantoAccess dMantoAcc, CopyOnWriteArrayList<DataPedido> listPedidos, DataFmwkTest dFTest) 
    throws Exception {
        //En caso de ejecución desde .bat no accederemos a Manto 
        if (utils.getTypeAccessFmwk(dFTest.ctx)==TypeAccessFmwk.Bat)
            return;

        //Si existen pedidos que validar y no se trata de un acceso desde la línea de comandos (típicamente .bat)
        TypeAccessFmwk typeAccess = utils.getTypeAccessFmwk(dFTest.ctx);
        if (listPedidos!=null && listPedidos.size()>0 && typeAccess!=TypeAccessFmwk.Bat) {
            PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, dFTest);
            PedidosNavigations.validacionListaPagosStpVs(listPedidos, dMantoAcc.appE, dFTest);
        }
    }
    
    /**
     * Partiendo de la página de menús, ejecutamos todos los pasos/validaciones para validar una lista de pedidos
     * @param listPaisPedido lista de pedidos a validar
     */
    public static void validacionListaPagosStpVs(CopyOnWriteArrayList<DataPedido> listDataPedidos, AppEcom appE, DataFmwkTest dFTest) throws Exception {
        //Bucle para obtener la lista de Países -> Pedidos
        for (DataPedido dataPedido : listDataPedidos) {
            //Sólo consultamos el pedido si el pago se realizó de forma correcta
            if (dataPedido.getResejecucion()==State.Ok) {
                try {
                    //Ejecutamos todo el flujo de pasos/validaciones para validar un pedido concreto y volvemos a la página de pedidos
                    validaPedidoStpVs(dataPedido, appE, dFTest);
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
    public static void validaPedidoStpVs(DataPedido dataPedido, AppEcom appE, DataFmwkTest dFTest) throws Exception {
        //Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
        PageSelTdaMantoStpV.selectTienda(dataPedido.getCodigoAlmacen(), dataPedido.getCodigoPais(), appE, dFTest);
        
        //Establecemos los filtros de las bolsas con el día de hoy + el pedido + el código de país asociado al pedido y pulsamos "Buscar"
        PageMenusMantoStpV.goToBolsas(dFTest);
        DatosStep datosStep = SecFiltrosMantoStpV.setFiltrosHoyYbuscar(dataPedido, TypeSearch.BOLSA, dFTest);
        boolean existLinkPedido = PageBolsasMantoStpV.validaLineaBolsa(dataPedido, appE, datosStep, dFTest);
        if (existLinkPedido) {
            PageConsultaPedidoBolsaStpV.detalleFromListaPedBol(dataPedido, TypeDetalle.bolsa, appE, dFTest);
        }
        
        if (appE!=AppEcom.votf) {
            //Establecemos los filtros de los pedidos con el día de hoy + el pedido + el código de país asociado al pedido y pulsamos "Buscar"
            PageMenusMantoStpV.goToPedidos(dFTest);
            datosStep = SecFiltrosMantoStpV.setFiltrosHoyYbuscar(dataPedido, TypeSearch.PEDIDO, dFTest);
            existLinkPedido = PagePedidosMantoStpV.validaLineaPedido(dataPedido, appE, datosStep, dFTest);
                                        
            //Si existe el link del pedido, Accedemos al detalle del pedido (la página de detalle es común para consulta de pedido/bolsa)
            if (existLinkPedido) {
                PageConsultaPedidoBolsaStpV.detalleFromListaPedBol(dataPedido, TypeDetalle.pedido, appE, dFTest);
            }
        }
        
        PageDetallePedido.gotoListaPedidos(dFTest.driver);
    }
}
