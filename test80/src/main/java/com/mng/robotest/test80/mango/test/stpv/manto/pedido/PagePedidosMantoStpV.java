package com.mng.robotest.test80.mango.test.stpv.manto.pedido;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageDetalleCliente;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageDetallePedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos.IdColumn;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos.TypeDetalle;
import com.mng.robotest.test80.mango.test.stpv.manto.ChecksResultWithFlagLinkCodPed;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.DataDeliveryPoint;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Pedidos en Manto
 * @author jorge.munoz
 *
 */

public class PagePedidosMantoStpV {

	@Validation
    public static ChecksResultWithFlagLinkCodPed validaLineaPedido(DataPedido dataPedido, AppEcom appE, WebDriver driver) {
    	ChecksResultWithFlagLinkCodPed validations = ChecksResultWithFlagLinkCodPed.getNew();
    	
        int maxSecondsToWait = 30;
	 	validations.add(
			"Desaparece la capa de Loading de \"Consultando\"" + " (lo esperamos hasta " + maxSecondsToWait + " segundos)",
			PagePedidos.isInvisibleCapaLoadingUntil(maxSecondsToWait, driver), State.Warn);
	 	
        validations.setExistsLinkCodPed(
        	PagePedidos.isPresentDataInPedido(IdColumn.idpedido, dataPedido.getCodigoPedidoManto(), TypeDetalle.pedido, 0, driver));
	 	validations.add(
			"En la columna " + IdColumn.idpedido.textoColumna + " aparece el código de pedido: " + dataPedido.getCodigoPedidoManto(),
			validations.getExistsLinkCodPed(), State.Warn);
	 	validations.add(
			"Aparece un solo pedido",
			PagePedidos.getNumLineas(driver)==1, State.Warn);
    	
	 	//En el caso de Outlet no tenemos la información del TPV que toca
	 	if (appE!=AppEcom.outlet) {
		 	validations.add(
				"En la columna " + IdColumn.tpv.textoColumna + " Aparece el Tpv asociado: " + dataPedido.getPago().getTpv().getId(),
				PagePedidos.isPresentDataInPedido(IdColumn.tpv, dataPedido.getPago().getTpv().getId(), TypeDetalle.pedido, 0, driver), 
				State.Warn);
	 	}
    	
	 	validations.add(
			"En la columna " + IdColumn.email.textoColumna + " aparece el email asociado: " + dataPedido.getEmailCheckout(),
			PagePedidos.isPresentDataInPedido(IdColumn.email, dataPedido.getEmailCheckout(), TypeDetalle.pedido, 0, driver), 
			State.Warn);
	 	
	 	String xpathCeldaImporte = PagePedidos.getXPathCeldaLineaPedido(IdColumn.total, TypeDetalle.pedido, driver);
	 	validations.add(
			"En pantalla aparece el importe asociado: " +  dataPedido.getImporteTotalManto(),
			ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), xpathCeldaImporte, driver), 
			State.Warn);
//	 	validations.add(
//			"En la columna " + IdColumn.tarjeta.textoColumna + " aparece el tipo de tarjeta: " + dataPedido.getCodtipopago(),
//			PagePedidos.isPresentDataInPedido(IdColumn.tarjeta, dataPedido.getCodtipopago(), TypeDetalle.pedido, 0, driver), 
//			State.Warn);
        
        return validations;
    }
    
	@Step (
		description="Buscamos pedidos con id registro",
		expected="Debemos obtener el ID del pedido",
		saveErrorData=SaveWhen.Never)
	public static DataPedido getPedidoUsuarioRegistrado(DataPedido dPedidoPrueba, WebDriver driver) throws Exception {
		int posicionPedidoActual = 6;
		int posicionMaxPaginaPedidos = 105;
		do {
			posicionPedidoActual++;
			posicionPedidoActual = PagePedidos.getPosicionPedidoUsuarioRegistrado(posicionPedidoActual, driver);
			dPedidoPrueba.setCodpedido(PagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual, driver));
			if (posicionPedidoActual == posicionMaxPaginaPedidos) {
				posicionPedidoActual = 6;
				PagePedidos.clickPaginaSiguientePedidos(driver);
			}
		} while (dPedidoPrueba.getCodpedido().equals(""));

		PagePedidos.clickLinkPedidoInLineas(driver, PagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual, driver), TypeDetalle.pedido);
		checkCodePedidoOk(dPedidoPrueba);

		return dPedidoPrueba;
	}
	
	@Validation (
		description="Tenemos código de pedido #{dPedidoPrueba.getCodpedido()}",
		level=State.Defect)
	private static boolean checkCodePedidoOk(DataPedido dPedidoPrueba) {
		return (!dPedidoPrueba.getCodpedido().equals(""));
	}

	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorData=SaveWhen.Never)
	public static DataPedido getDataPedido(DataPedido dPedidoPrueba, WebDriver driver) throws Exception {
		DataBag dBagPrueba = new DataBag();
		List<String> referencias = new ArrayList<>();
		ArticuloScreen articulo;
		referencias = PageDetallePedido.getReferenciasArticulosDetallePedido(driver);
		for (String referencia : referencias) {
			articulo = new ArticuloScreen();
			articulo.setReferencia(referencia);
			dBagPrueba.addArticulo(articulo);
		}
		dPedidoPrueba.setDataBag(dBagPrueba);
		checkPedidoWithReferences(referencias, dPedidoPrueba);

		return dPedidoPrueba;
	}

	@Validation (
		description="El pedido tiene las referencias #{referencias.toString()}",
		level=State.Defect)
	private static boolean checkPedidoWithReferences(List<String> referencias, DataPedido dPedidoPrueba) {
		return (!dPedidoPrueba.getDataBag().getListArticulos().isEmpty());
	}
	
	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorData=SaveWhen.Never)
	public static DataPedido getDataCliente(DataPedido dPedidoPrueba, WebDriver driver) throws Exception {
		PageDetallePedido.clickLinkDetallesCliente(driver);
		dPedidoPrueba.getPago().setDni(PageDetalleCliente.getUserDniText(driver));
		if (dPedidoPrueba.getPago().getDni().equals("")) {
			dPedidoPrueba.getPago().setDni("41507612h");
		}
		
		dPedidoPrueba.getPago().setUseremail(PageDetalleCliente.getUserEmailText(driver));
		PageDetalleCliente.clickLinkVolverPedidos(driver);
		checkAfterSearchPedidosWithIdRegister(dPedidoPrueba);

		return dPedidoPrueba;
	}

	@Validation
	private static ChecksResult checkAfterSearchPedidosWithIdRegister(DataPedido dPedidoPrueba) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Tenemos el DNI del cliente " + dPedidoPrueba.getPago().getDni(),
			!dPedidoPrueba.getPago().getDni().equals(""), State.Defect);
	 	validations.add(
			"Tenemos el Email del cliente " + dPedidoPrueba.getPago().getUseremail(),
			!dPedidoPrueba.getPago().getUseremail().equals(""), State.Defect);
		return validations;
	}
	
	@Step (
		description="Un pedido con tienda física en la lista de pedidos",
		expected="Debemos obtener una tienda física válida",
		saveErrorData=SaveWhen.Never)
	public static DataPedido getTiendaFisicaListaPedidos(DataPedido dPedidoPrueba, WebDriver driver)
			throws Exception {
		DataDeliveryPoint dEnvioPrueba = new DataDeliveryPoint();
		dPedidoPrueba.setDataDeliveryPoint(dEnvioPrueba);
		dPedidoPrueba.getDataDeliveryPoint().setCodigo(PagePedidos.getTiendaFisicaFromListaPedidos(driver));
		checkIsTiendaFisica(dPedidoPrueba.getDataDeliveryPoint().getCodigo());
		return dPedidoPrueba;
	}
	
	@Validation (
		description="Tenemos la tienda física #{codigoDeliveryPoint}",
		level=State.Defect)
	private static boolean checkIsTiendaFisica(String codigoDeliveryPoint) {
		return (!codigoDeliveryPoint.equals(""));
	}
}
