package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.manto.SecFiltros;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la sección de filtros de Pedidos en Manto
 * @author jorge.munoz
 *
 */
@SuppressWarnings("javadoc")
public class SecFiltrosMantoStpV {

    public enum TypeSearch {BOLSA, PEDIDO} 
    
    /**
     * Establecemos los filtros y seleccionamos el botón "Buscar"
     */
    public static DatosStep setFiltrosHoyYbuscar(DataPedido dataPedido, TypeSearch typeSearch, DataFmwkTest dFTest) throws Exception {
        //Step. Seteamos los filtros
        DatosStep datosStep = new DatosStep     (
            "Buscamos a nivel de " + typeSearch + " el pedido <b style=\"color:blue;\">" + dataPedido.getCodigoPedidoManto() + "</b> con filtros: <br>" +
            "- Método pago: <b>" + dataPedido.getPago().getNombre() + "</b><br>" +
            "- Tienda: <b>" + SecCabecera.getLitTienda(dFTest.driver) + "</b><br>" +
            "- País: <b>" + dataPedido.getNombrePais() + "</b> (" + dataPedido.getCodigoPais() + ")", 
            "La búsqueda es correcta");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
        	if (dataPedido.getCodigoPedidoManto() != null)
	            SecFiltros.setFiltroCodPedido(dFTest.driver, dataPedido.getCodigoPedidoManto());
        	
        	SecFiltros.setFiltroCodPaisIfExists(dFTest.driver, dataPedido.getCodigoPais());
            String fechaHoy = SecFiltros.getFechaHastaValue(dFTest.driver);
            SecFiltros.setFiltroFDesde(dFTest.driver, fechaHoy);
            SecFiltros.clickButtonBuscar(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        return datosStep;
    }
}
