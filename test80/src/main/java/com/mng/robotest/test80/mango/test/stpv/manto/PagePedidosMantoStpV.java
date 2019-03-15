package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageDetalleCliente;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageDetallePedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos.IdColumn;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos.TypeDetalle;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.DataDeliveryPoint;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Pedidos en Manto
 * @author jorge.munoz
 *
 */

public class PagePedidosMantoStpV {

    /**
     * Se valida que está apareciendo una línea de pedido con los datos del pedido
     * @return si existe el link correspondiente al código de pedido 
     */
    public static boolean validaLineaPedido(DataPedido dataPedido, AppEcom appE, DataFmwkTest dFTest) {
    	DatosStep datosStep = TestCaseData.getDatosCurrentStep();
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
        datosStep.setNOKstateByDefault();
        ChecksResult listVals = ChecksResult.getNew(datosStep);
        try {
            if (!PagePedidos.isInvisibleCapaLoadingUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PagePedidos.isPresentDataInPedido(IdColumn.idpedido, dataPedido.getCodigoPedidoManto(), TypeDetalle.pedido, 0/*wait*/, dFTest.driver)) {
                listVals.add(2, State.Warn);
                existsLinkPedido = false;
            }
            if (PagePedidos.getNumLineas(dFTest.driver)!=1) {
                listVals.add(3, State.Warn);            
            }
            if (appE!=AppEcom.outlet) { 
                if (!PagePedidos.isPresentDataInPedido(IdColumn.tpv, dataPedido.getPago().getTpv().getId(), TypeDetalle.pedido, 0/*wait*/, dFTest.driver)) {           	
                    listVals.add(4, State.Warn);
                }
            }
            if (!PagePedidos.isPresentDataInPedido(IdColumn.email, dataPedido.getEmailCheckout(), TypeDetalle.pedido, 0/*wait*/, dFTest.driver)) {
                listVals.add(5, State.Warn);
            }
            String xpathCeldaImporte = PagePedidos.getXPathCeldaLineaPedido(IdColumn.total, TypeDetalle.pedido, dFTest.driver);
            if (!ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), xpathCeldaImporte, dFTest.driver)) {
                listVals.add(6, State.Warn);
            }
            if (!PagePedidos.isPresentDataInPedido(IdColumn.tarjeta, dataPedido.getCodtipopago(), TypeDetalle.pedido, 0/*wait*/, dFTest.driver)) {
                listVals.add(7, State.Warn);
            }
                                       
            datosStep.setListResultValidations(listVals); 
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
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

		DatosStep datosStep = new DatosStep(
				"Buscamos pedidos con id registro",
				"Debemos obtener el ID del pedido");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
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
			StepAspect.storeDataAfterStep(datosStep);
		}

		String descripValidac = "1) Tenemos código de pedido " + dPedidoPrueba.getCodpedido();
		datosStep.setNOKstateByDefault();
		ChecksResult listVals = ChecksResult.getNew(datosStep);
		try {
			if (dPedidoPrueba.getCodpedido().equals("")) {
				listVals.add(1, State.Defect);
			}

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }

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

		DatosStep datosStep = new DatosStep("Buscamos pedidos con id registro para obtener información del cliente",
				"Debemos obtener la información del cliente");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
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
			StepAspect.storeDataAfterStep(datosStep);
		}

		String descripValidac = "1) El pedido tiene las referencias " + referencias.toString();
		datosStep.setNOKstateByDefault();
		ChecksResult listVals = ChecksResult.getNew(datosStep);
		try {
			if (dPedidoPrueba.getDataBag().getListArticulos().isEmpty()) {
				listVals.add(1, State.Defect);
			}

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }

		return dPedidoPrueba;
	}

	
	/**
	 * Se busca la información del cliente
	 * 
	 * @return DataPedido con la información del cliente seteada
	 */
	public static DataPedido getDataCliente(DataPedido dPedidoPrueba, DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep("Buscamos pedidos con id registro para obtener información del cliente",
				"Debemos obtener la información del cliente");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
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
			StepAspect.storeDataAfterStep(datosStep);
		}

		String descripValidac = "1) Tenemos el DNI del cliente " + dPedidoPrueba.getPago().getDni() + "<br>"
				+ "2) Tenemos el Email del cliente " + dPedidoPrueba.getPago().getUseremail();
		datosStep.setNOKstateByDefault();
		ChecksResult listVals = ChecksResult.getNew(datosStep);
		try {
			if (dPedidoPrueba.getPago().getDni().equals("")) {
				listVals.add(1, State.Defect);
			}
			if (dPedidoPrueba.getPago().getUseremail().equals("")) {
				listVals.add(2, State.Defect);
			}

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }

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

		DatosStep datosStep = new DatosStep(
			"Un pedido con tienda física en la lista de pedidos",
			"Debemos obtener una tienda física válida");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
		try {
			dPedidoPrueba.setDataDeliveryPoint(dEnvioPrueba);
			dPedidoPrueba.getDataDeliveryPoint().setCodigo(PagePedidos.getTiendaFisicaFromListaPedidos(dFTest.driver));

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} 
		finally {
			StepAspect.storeDataAfterStep(datosStep);
		}

		String descripValidac = "1) Tenemos la tienda física " + dPedidoPrueba.getDataDeliveryPoint().getCodigo();
		datosStep.setNOKstateByDefault();
		ChecksResult listVals = ChecksResult.getNew(datosStep);
		try {
			if (dPedidoPrueba.getDataDeliveryPoint().getCodigo().equals("")) {
				listVals.add(1, State.Defect);
			}

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }

		return dPedidoPrueba;
	}
}
