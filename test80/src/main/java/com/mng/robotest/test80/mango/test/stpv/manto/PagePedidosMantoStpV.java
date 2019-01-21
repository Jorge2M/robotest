package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageDetalleCliente;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageDetallePedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.PagePedidos;
import com.mng.robotest.test80.mango.test.pageobject.manto.PagePedidos.IdColumn;
import com.mng.robotest.test80.mango.test.pageobject.manto.PagePedidos.TypeDetalle;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.DataDeliveryPoint;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Pedidos en Manto
 * @author jorge.munoz
 *
 */
@SuppressWarnings("javadoc")
public class PagePedidosMantoStpV {

    /**
     * Se valida que está apareciendo una línea de pedido con los datos del pedido
     * @return si existe el link correspondiente al código de pedido 
     */
    public static boolean validaLineaPedido(DataPedido dataPedido, AppEcom appE, datosStep datosStep, DataFmwkTest dFTest) {
//    	//TODO tratamiento específico temporal para el entorno de CI con Adyen -> Level.Info 
//    	//(hasta que dispongamos de la CI que despliega Adyen y el resto de artefactos satelitales)
//        State levelByCIAdyen = State.Warn;
//    	if (dataPedido.getPago().isAdyen() &&
//        	UtilsMangoTest.isEntornoCI(appE, dFTest))
//    		levelByCIAdyen = State.Info;

        boolean existsLinkPedido = true;
        int maxSecondsToWait = 30;
        String descripValidac =
            "1) Desaparece la capa de Loading de \"Consultando\"" + " (lo esperamos hasta " + maxSecondsToWait + " segundos) + <br>" +
            "2) En la columna " + IdColumn.idpedido.textoColumna + " aparece el código de pedido: " + dataPedido.getCodigoPedidoManto() + "<br>" +                
            "3) Aparece un solo pedido <br>";
        if (appE!=AppEcom.outlet) //En el caso de Outlet no tenemos la información del TPV que toca
            descripValidac+=
            "4) En la columna " + IdColumn.tpv.textoColumna + " Aparece el Tpv asociado: " + dataPedido.getPago().getTpv().getId() + "<br>";
        
        descripValidac+=
            "5) En la columna " + IdColumn.email.textoColumna + " aparece el email asociado: " + dataPedido.getEmailCheckout() + "<br>" +
            "6) En pantalla aparece el importe asociado: " +  dataPedido.getImporteTotalManto() + "<br>" +
            "7) En la columna " + IdColumn.tarjeta.textoColumna + " aparece el tipo de tarjeta: " + dataPedido.getCodtipopago();
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PagePedidos.isInvisibleCapaLoadingUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!PagePedidos.isPresentDataInPedido(IdColumn.idpedido, dataPedido.getCodigoPedidoManto(), TypeDetalle.pedido, 0/*wait*/, dFTest.driver)) {
                fmwkTest.addValidation(2, State.Warn, listVals);
                existsLinkPedido = false;
            }
            //3)
            if (PagePedidos.getNumLineas(dFTest.driver)!=1)
                fmwkTest.addValidation(3, State.Warn, listVals);            
            //4)
            if (appE!=AppEcom.outlet) { 
                if (!PagePedidos.isPresentDataInPedido(IdColumn.tpv, dataPedido.getPago().getTpv().getId(), TypeDetalle.pedido, 0/*wait*/, dFTest.driver))            	
                    fmwkTest.addValidation(4, State.Warn, listVals);
            }
            //5)
            if (!PagePedidos.isPresentDataInPedido(IdColumn.email, dataPedido.getEmailCheckout(), TypeDetalle.pedido, 0/*wait*/, dFTest.driver))
                fmwkTest.addValidation(5, State.Warn, listVals);
            //6)
            String xpathCeldaImporte = PagePedidos.getXPathCeldaLineaPedido(IdColumn.total, TypeDetalle.pedido, dFTest.driver);
            if (!ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), xpathCeldaImporte, dFTest.driver))
                fmwkTest.addValidation(6, State.Warn, listVals);
            //7)
            if (!PagePedidos.isPresentDataInPedido(IdColumn.tarjeta, dataPedido.getCodtipopago(), TypeDetalle.pedido, 0/*wait*/, dFTest.driver))
                fmwkTest.addValidation(7, State.Warn, listVals);     
                                       
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals); 
        }  
        finally {fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return existsLinkPedido;
    }
    

    /**
     * Se busca un pedido con DNI para las pruebas en manto
     * @return DataPedido con el ID pedido seteado
     */
	public static DataPedido getPedidoUsuarioRegistrado(DataPedido dPedidoPrueba, DataFmwkTest dFTest)
			throws Exception {
		int posicionPedidoActual = 6;
		int posicionMaxPaginaPedidos = 105;

		datosStep datosStep = new datosStep(
				"Buscamos pedidos con id registro",
				"Debemos obtener el ID del pedido");
		datosStep.setGrab_ErrorPageIfProblem(false);
		try {
			do {
				posicionPedidoActual++;
				posicionPedidoActual = PagePedidos.getPosicionPedidoUsuarioRegistrado(posicionPedidoActual, dFTest.driver);
				dPedidoPrueba.setCodpedido(PagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual, dFTest.driver));

				//PagePedidos.clickLinkPedidoInLineas(dFTest.driver, PagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual, dFTest.driver));

				//PageDetalleCliente.clickLinkVolverPedidos(dFTest.driver);ro

				if (posicionPedidoActual == posicionMaxPaginaPedidos) {
					posicionPedidoActual = 6;
					PagePedidos.clickPaginaSiguientePedidos(dFTest.driver);
				}
			} while (dPedidoPrueba.getCodpedido().equals(""));

			PagePedidos.clickLinkPedidoInLineas(dFTest.driver, PagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual, dFTest.driver), TypeDetalle.pedido);

			datosStep.setExcepExists(false);
			datosStep.setResultSteps(State.Ok);

		} finally {
			datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));
		}

		String descripValidac = "1) Tenemos código de pedido " + dPedidoPrueba.getCodpedido();
		datosStep.setExcepExists(true);
		datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			// 1)
			if (dPedidoPrueba.getCodpedido().equals(""))
				fmwkTest.addValidation(1, State.Defect, listVals);

			datosStep.setExcepExists(false);
			datosStep.setResultSteps(listVals);
		} finally {
			fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest);
		}

		return dPedidoPrueba;
	}

	
	/**
	 * Se busca la información del pedido
	 * 
	 * @return DataPedido con la información del pedido seteada
	 */
	public static DataPedido getDataPedido(DataPedido dPedidoPrueba, DataFmwkTest dFTest) throws Exception {
		DataBag dBagPrueba = new DataBag();
		List<String> referencias = new ArrayList<>();
		ArticuloScreen articulo;

		datosStep datosStep = new datosStep("Buscamos pedidos con id registro para obtener información del cliente",
				"Debemos obtener la información del cliente");
		datosStep.setGrab_ErrorPageIfProblem(false);
		try {

			referencias = PageDetallePedido.getReferenciasArticulosDetallePedido(dFTest.driver);

			for (String referencia : referencias) {
				articulo = new ArticuloScreen();
				articulo.setReferencia(referencia);
				dBagPrueba.addArticulo(articulo);
			}
			dPedidoPrueba.setDataBag(dBagPrueba);

			datosStep.setExcepExists(false);
			datosStep.setResultSteps(State.Ok);

		} finally {
			datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));
		}

		String descripValidac = "1) El pedido tiene las referencias " + referencias.toString();
		datosStep.setExcepExists(true);
		datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			// 1)
			if (dPedidoPrueba.getDataBag().getListArticulos().isEmpty())
				fmwkTest.addValidation(1, State.Defect, listVals);

			datosStep.setExcepExists(false);
			datosStep.setResultSteps(listVals);
		} finally {
			fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest);
		}

		return dPedidoPrueba;
	}

	
	/**
	 * Se busca la información del cliente
	 * 
	 * @return DataPedido con la información del cliente seteada
	 */
	public static DataPedido getDataCliente(DataPedido dPedidoPrueba, DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep("Buscamos pedidos con id registro para obtener información del cliente",
				"Debemos obtener la información del cliente");
		datosStep.setGrab_ErrorPageIfProblem(false);
		try {
			PageDetallePedido.clickLinkDetallesCliente(dFTest.driver);

			dPedidoPrueba.getPago().setDni(PageDetalleCliente.getUserDniText(dFTest.driver));
			if (dPedidoPrueba.getPago().getDni().equals(""))
				dPedidoPrueba.getPago().setDni("41507612h");
			
			dPedidoPrueba.getPago().setUseremail(PageDetalleCliente.getUserEmailText(dFTest.driver));

			PageDetalleCliente.clickLinkVolverPedidos(dFTest.driver);

			datosStep.setExcepExists(false);
			datosStep.setResultSteps(State.Ok);

		} finally {
			datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));
		}

		String descripValidac = "1) Tenemos el DNI del cliente " + dPedidoPrueba.getPago().getDni() + "<br>"
				+ "2) Tenemos el Email del cliente " + dPedidoPrueba.getPago().getUseremail();
		datosStep.setExcepExists(true);
		datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			// 1)
			if (dPedidoPrueba.getPago().getDni().equals(""))
				fmwkTest.addValidation(1, State.Defect, listVals);
			// 2)
			if (dPedidoPrueba.getPago().getUseremail().equals(""))
				fmwkTest.addValidation(2, State.Defect, listVals);

			datosStep.setExcepExists(false);
			datosStep.setResultSteps(listVals);
		} finally {
			fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest);
		}

		return dPedidoPrueba;
	}

	
	/**
	 * Se busca una tienda física real en el gestor de pedidos
	 * 
	 * @return DataPedido con la tienda física añadida
	 */
	public static DataPedido getTiendaFisicaListaPedidos(DataPedido dPedidoPrueba, DataFmwkTest dFTest)
			throws Exception {
		DataDeliveryPoint dEnvioPrueba = new DataDeliveryPoint();

		datosStep datosStep = new datosStep(
			"Un pedido con tienda física en la lista de pedidos",
			"Debemos obtener una tienda física válida");
		datosStep.setGrab_ErrorPageIfProblem(false);
		try {
			dPedidoPrueba.setDataDeliveryPoint(dEnvioPrueba);
			dPedidoPrueba.getDataDeliveryPoint().setCodigo(PagePedidos.getTiendaFisicaFromListaPedidos(dFTest.driver));

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} 
		finally {
			datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));
		}

		String descripValidac = "1) Tenemos la tienda física " + dPedidoPrueba.getDataDeliveryPoint().getCodigo();
		datosStep.setExcepExists(true);
		datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			// 1)
			if (dPedidoPrueba.getDataDeliveryPoint().getCodigo().equals(""))
				fmwkTest.addValidation(1, State.Defect, listVals);

			datosStep.setExcepExists(false);
			datosStep.setResultSteps(listVals);
		} finally {
			fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest);
		}

		return dPedidoPrueba;
	}
	
	
	
	
	/*
	public static DataPedido getDataPedidoUsuarioRegistrado(DataPedido dPedidoPrueba, DataFmwkTest dFTest) throws Exception {
		DataBag dBagPrueba = new DataBag();
		List <String> referencias = new ArrayList<>();
		Articulo articulo;
		int posicionPedidoActual = 6;
		int posicionMaxPaginaPedidos = 105;
		
		datosStep datosStep = new datosStep       (
		        "Buscamos y clickamos un pedido con id registro para obtener información del pedido", 
		        "Acabamos encontrando un pedido válido (que tenga informado el DNI)");
		    datosStep.setGrab_ErrorPageIfProblem(false);
		    try {
		    	do{
		    		posicionPedidoActual++;
			    	posicionPedidoActual = PagePedidos.getPosicionPedidoUsuarioRegistrado(posicionPedidoActual, dFTest.driver);
	
			    	PagePedidos.clickLinkPedidoInLineas(dFTest.driver, PagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual, dFTest.driver));
			    	
			    	PageDetallePedido.clickLinkDetallesCliente(dFTest.driver);
			    	
			    	dPedidoPrueba.getPago().setDni(PageDetalleCliente.getUserDniText(dFTest.driver));
			    	
			    	PageDetalleCliente.clickLinkVolverPedidos(dFTest.driver);
			    	
			    	if (posicionPedidoActual == posicionMaxPaginaPedidos){
			    		posicionPedidoActual = 6;
			    		PagePedidos.clickPaginaSiguientePedidos(dFTest.driver);
			    	}
		    	}while (dPedidoPrueba.getPago().getDni().equals(""));
		    	
		    	
		    	dPedidoPrueba.setCodpedido(PagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual, dFTest.driver));
		    	PagePedidos.clickLinkPedidoInLineas(dFTest.driver, PagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual, dFTest.driver));
		    	
		    	referencias = PageDetallePedido.getReferenciasArticulosDetallePedido(dFTest.driver);
		    	
		    	for (String referencia : referencias){
		    		articulo = new Articulo();
		    		articulo.setReferencia(referencia);
		    		dBagPrueba.addArticulo(articulo);
		    	}
		    	dPedidoPrueba.setDataBag(dBagPrueba);
		    	
		    	PageDetallePedido.clickLinkDetallesCliente(dFTest.driver);
		    	
		    	dPedidoPrueba.getPago().setDni(PageDetalleCliente.getUserDniText(dFTest.driver));
		    	dPedidoPrueba.getPago().setUseremail(PageDetalleCliente.getUserEmailText(dFTest.driver));
		            
		    	PageDetalleCliente.clickLinkVolverPedidos(dFTest.driver);
		    	
		        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		    }
		    finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
			
		    String descripValidac = 
		        "1) El código de pedido no está vacío (estamos obteniendo " + dPedidoPrueba.getCodpedido() + ")<br>" +
		        "2) La refrerencia del pedido no está vacía (El pedido tiene las referencias " + referencias.toString() + ")<br>" +
		        "3) ...Tenemos el DNI del cliente " + dPedidoPrueba.getPago().getDni() + "<br>" +
		        "3) ...Tenemos el Email del cliente " + dPedidoPrueba.getPago().getUseremail();
	            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
	            try {
	                List<SimpleValidation> listVals = new ArrayList<>();
	                //1)
	                if (dPedidoPrueba.getCodpedido().equals(""))
	                    fmwkTest.addValidation(1, State.Defect, listVals);
	                //2) 
	                if (dPedidoPrueba.getDataBag().getListArticulos().isEmpty())
	                    fmwkTest.addValidation(2, State.Defect, listVals);            
	                //3)
	                if (dPedidoPrueba.getPago().getDni().equals(""))
	                    fmwkTest.addValidation(3, State.Defect, listVals);
	                //4)
	                if (dPedidoPrueba.getPago().getUseremail().equals(""))
	                    fmwkTest.addValidation(4, State.Defect, listVals);
	    
	                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	            } 
	            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
		
		return dPedidoPrueba;
	}
	*/
	
	
}
