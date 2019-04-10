package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.List;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
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

	@Validation
    public static ChecksResultWithFlagLinkCodPed validaLineaPedido(DataPedido dataPedido, AppEcom appE, WebDriver driver) {
        ChecksResultWithFlagLinkCodPed validations = ChecksResultWithFlagLinkCodPed.getNew();
    	
        int maxSecondsWait = 30;
	 	validations.add(
			"Desaparece la capa de Loading de \"Consultando\"" + " (lo esperamos hasta " + maxSecondsWait + " segundos) + <br>",
			PagePedidos.isInvisibleCapaLoadingUntil(maxSecondsWait, driver), State.Defect);
	 	
	 	validations.setExistsLinkCodPed(PagePedidos.isPresentDataInPedido(IdColumn.idpedido, dataPedido.getCodigoPedidoManto(), TypeDetalle.pedido, 0, driver));
	 	validations.add(
			"En la columna " + IdColumn.idpedido.textoColumna + " aparece el código de pedido: " + dataPedido.getCodigoPedidoManto() + "<br>",
			validations.getExistsLinkCodPed(), State.Warn);
	 	validations.add(
			"Aparece un solo pedido <br>",
			PagePedidos.getNumLineas(driver)==1, State.Warn);
    	
        if (appE!=AppEcom.outlet) {
    	 	validations.add(
				"En la columna " + IdColumn.tpv.textoColumna + " Aparece el Tpv asociado: " + dataPedido.getPago().getTpv().getId() + "<br>",
				PagePedidos.isPresentDataInPedido(IdColumn.tpv, dataPedido.getPago().getTpv().getId(), TypeDetalle.pedido, 0, driver), 
				State.Warn);
        }

	 	validations.add(
			"En la columna " + IdColumn.email.textoColumna + " aparece el email asociado: " + dataPedido.getEmailCheckout() + "<br>",
			PagePedidos.isPresentDataInPedido(IdColumn.email, dataPedido.getEmailCheckout(), TypeDetalle.pedido, 0, driver), 
			State.Warn);
	 	
	 	String xpathCeldaImporte = PagePedidos.getXPathCeldaLineaPedido(IdColumn.total, TypeDetalle.pedido, driver);
	 	validations.add(
			"En pantalla aparece el importe asociado: " +  dataPedido.getImporteTotalManto() + "<br>",
			ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), xpathCeldaImporte, driver), 
			State.Warn);
	 	validations.add(
			"En la columna " + IdColumn.tarjeta.textoColumna + " aparece el tipo de tarjeta: " + dataPedido.getCodtipopago(),
			PagePedidos.isPresentDataInPedido(IdColumn.tarjeta, dataPedido.getCodtipopago(), TypeDetalle.pedido, 0, driver), 
			State.Warn);
        
        return validations;
    }
    
	@Step (
		description="Buscamos pedidos con id registro",
		expected="Debemos obtener el ID del pedido",
		saveErrorPage=SaveWhen.Never)
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
		} 
		while (dPedidoPrueba.getCodpedido().equals(""));

		PagePedidos.clickLinkPedidoInLineas(driver, PagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual, driver), TypeDetalle.pedido);
		checkCodigoPedido(dPedidoPrueba.getCodpedido(), driver);
		
		return dPedidoPrueba;
	}
	
	@Validation (
		description="Tenemos código de pedido #{codPedido}",
		level=State.Defect)
	private static boolean checkCodigoPedido(String codPedido, WebDriver driver) {
		return (("".compareTo(codPedido))!=0);
	}

	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorPage=SaveWhen.Never)
	public static DataPedido getDataPedido(DataPedido dPedidoPrueba, WebDriver driver) throws Exception {
		DataBag dBagPrueba = new DataBag();
		List<String> referencias = PageDetallePedido.getReferenciasArticulosDetallePedido(driver);
		for (String referencia : referencias) {
			ArticuloScreen articulo = new ArticuloScreen();
			articulo.setReferencia(referencia);
			dBagPrueba.addArticulo(articulo);
		}
		dPedidoPrueba.setDataBag(dBagPrueba);

		checkPedidoWithReferences(referencias, dPedidoPrueba, driver);
		return dPedidoPrueba;
	}
	
	@Validation (
		description="El pedido tiene las referencias #{referencias.toString()}",
		level=State.Defect)
	private static boolean checkPedidoWithReferences(List<String> referencias, DataPedido dPedidoPrueba, WebDriver driver) {
		return (!dPedidoPrueba.getDataBag().getListArticulos().isEmpty());
	}

	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorPage=SaveWhen.Never)
	public static DataPedido getDataCliente(DataPedido dPedidoPrueba, WebDriver driver) throws Exception {
		PageDetallePedido.clickLinkDetallesCliente(driver);
		dPedidoPrueba.getPago().setDni(PageDetalleCliente.getUserDniText(driver));
		if (dPedidoPrueba.getPago().getDni().equals("")) {
			dPedidoPrueba.getPago().setDni("41507612h");
		}
		dPedidoPrueba.getPago().setUseremail(PageDetalleCliente.getUserEmailText(driver));
		PageDetalleCliente.clickLinkVolverPedidos(driver);
		checkAfterSearchPedido(dPedidoPrueba, driver);
		
		return dPedidoPrueba;
	}

	@Validation
	private static ChecksResult checkAfterSearchPedido(DataPedido dPedidoPrueba, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Tenemos el DNI del cliente " + dPedidoPrueba.getPago().getDni() + "<br>",
			!dPedidoPrueba.getPago().getDni().equals(""), State.Defect);
	 	validations.add(
			"Tenemos el Email del cliente " + dPedidoPrueba.getPago().getUseremail(),
			!dPedidoPrueba.getPago().getUseremail().equals(""), State.Defect);
	 	return validations;
	}
	
	@Step (
		description="Un pedido con tienda física en la lista de pedidos", 
		expected="Debemos obtener una tienda física válida",
		saveErrorPage=SaveWhen.Never)
	public static DataPedido getTiendaFisicaListaPedidos(DataPedido dPedidoPrueba, WebDriver driver)
			throws Exception {
		DataDeliveryPoint dEnvioPrueba = new DataDeliveryPoint();
		dPedidoPrueba.setDataDeliveryPoint(dEnvioPrueba);
		dPedidoPrueba.getDataDeliveryPoint().setCodigo(PagePedidos.getTiendaFisicaFromListaPedidos(driver));
		String codigoDeliveryPoint = dPedidoPrueba.getDataDeliveryPoint().getCodigo();
		checkTiendaFisica(codigoDeliveryPoint, driver);

		return dPedidoPrueba;
	}
	
	@Validation (
		description="Tenemos la tienda física #{codigoDeliveryPoint}",
		level=State.Defect)
	private static boolean checkTiendaFisica(String codigoDeliveryPoint, WebDriver driver) {
		return (!codigoDeliveryPoint.equals(""));
	}
}
