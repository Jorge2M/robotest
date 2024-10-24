package com.mng.robotest.tests.domains.manto.steps;

import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepMantoBase;
import com.mng.robotest.tests.domains.compra.steps.envio.DataDeliveryPoint;
import com.mng.robotest.tests.domains.manto.pageobjects.PageDetalleCliente;
import com.mng.robotest.tests.domains.manto.pageobjects.PageDetallePedido;
import com.mng.robotest.tests.domains.manto.pageobjects.PagePedidos;
import com.mng.robotest.testslegacy.datastored.DataBag;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.mng.robotest.tests.domains.manto.pageobjects.PagePedidos.IdColumn.*;
import static com.mng.robotest.tests.domains.manto.pageobjects.PagePedidos.TypeDetalle.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class PagePedidosMantoSteps extends StepMantoBase {

	private final PagePedidos pgPedidos = new PagePedidos();
	
	@Validation
	public ChecksResultWithFlagLinkCodPed validaLineaPedido(DataPedido dataPedido) {
		ChecksResultWithFlagLinkCodPed checks = ChecksResultWithFlagLinkCodPed.getNew();
		
		int seconds = 30;
	 	checks.add(String.format(
	 		"Desaparece la capa de Loading de \"Consultando\"" + getLitSecondsWait(seconds) ),
			pgPedidos.isInvisibleCapaLoadingUntil(seconds), WARN);
	 	
		checks.setExistsLinkCodPed(
			pgPedidos.isPresentDataInPedido(IDPEDIDO, dataPedido.getCodigoPedidoManto(), PEDIDO, 0));
		
	 	checks.add(String.format(
	 		"En la columna %s aparece el código de pedido: %s", IDPEDIDO.getTextoColumna(), dataPedido.getCodigoPedidoManto()),
			checks.getExistsLinkCodPed(), WARN);
	 	
	 	checks.add(
			"Aparece un solo pedido",
			pgPedidos.getNumLineas()==1, WARN);
		
	 	checks.add(String.format(
	 		"En la columna %s aparece el email asociado: %s", EMAIL.getTextoColumna(), dataPedido.getEmailCheckout()),
	 		pgPedidos.isPresentDataInPedido(EMAIL, dataPedido.getEmailCheckout(), PEDIDO, 0), WARN);
	 	
	 	String xpathCeldaImporte = pgPedidos.getXPathCeldaLineaPedido(TOTAL, PEDIDO);
	 	checks.add(
			"En pantalla aparece el importe asociado: " +  dataPedido.getImporteTotalManto(),
			ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), xpathCeldaImporte, driver), 
			WARN);
		
		return checks;
	}
	
	@Step (
		description="Buscamos pedidos con id registro",
		expected="Debemos obtener el ID del pedido",
		saveErrorData=NEVER)
	public void setPedidoUsuarioRegistrado(DataPedido dPedidoPrueba) {
		int posicionPedidoActual = 6;
		int posicionMaxPaginaPedidos = 105;
		do {
			posicionPedidoActual++;
			posicionPedidoActual = pgPedidos.getPosicionPedidoUsuarioRegistrado(posicionPedidoActual);
			dPedidoPrueba.setCodpedido(pgPedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual));
			if (posicionPedidoActual == posicionMaxPaginaPedidos) {
				posicionPedidoActual = 6;
				pgPedidos.clickPaginaSiguientePedidos();
			}
		} while (dPedidoPrueba.getCodpedido().equals(""));

		pgPedidos.clickLinkPedidoInLineas(pgPedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual), PEDIDO);
		checkCodePedidoOk(dPedidoPrueba);
	}
	
	@Validation (
		description="Tenemos código de pedido #{dPedidoPrueba.getCodpedido()}")
	private boolean checkCodePedidoOk(DataPedido dPedidoPrueba) {
		return (!dPedidoPrueba.getCodpedido().equals(""));
	}

	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorData=NEVER)
	public void setDataPedidoStep(DataPedido dPedidoPrueba) {
		var dBagPrueba = new DataBag();
		List<String> referencias = new PageDetallePedido().getReferenciasArticulosDetallePedido();
		ArticuloScreen articulo;
		for (String referencia : referencias) {
			articulo = new ArticuloScreen();
			articulo.setReferencia(referencia);
			dBagPrueba.add(articulo);
		}
		dPedidoPrueba.setDataBag(dBagPrueba);
		checkPedidoWithReferences(referencias, dPedidoPrueba);
	}

	@Validation (
		description="El pedido tiene las referencias #{referencias.toString()}")
	private boolean checkPedidoWithReferences(List<String> referencias, DataPedido dPedidoPrueba) {
		return (!dPedidoPrueba.getDataBag().getListArticulos().isEmpty());
	}
	
	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorData=NEVER)
	public void setDataCliente(DataPedido dPedidoPrueba) {
		new PageDetallePedido().clickLinkDetallesCliente();
		var pageDetalleCliente = new PageDetalleCliente();
		dPedidoPrueba.getPago().setDni(pageDetalleCliente.getUserDniText());
		if (dPedidoPrueba.getPago().getDni().equals("")) {
			dPedidoPrueba.getPago().setDni("41507612h");
		}
		
		dPedidoPrueba.getPago().setUseremail(pageDetalleCliente.getUserEmailText());
		pageDetalleCliente.clickLinkVolverPedidos();
		checkAfterSearchPedidosWithIdRegister(dPedidoPrueba);
	}

	@Validation
	private ChecksTM checkAfterSearchPedidosWithIdRegister(DataPedido dPedidoPrueba) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Tenemos el DNI del cliente " + dPedidoPrueba.getPago().getDni(),
			!dPedidoPrueba.getPago().getDni().equals(""));
	 	
	 	checks.add(
			"Tenemos el Email del cliente " + dPedidoPrueba.getPago().getUseremail(),
			!dPedidoPrueba.getPago().getUseremail().equals(""));
	 	
		return checks;
	}
	
	@Step (
		description="Un pedido con tienda física en la lista de pedidos",
		expected="Debemos obtener una tienda física válida",
		saveErrorData=NEVER)
	public void setTiendaFisicaListaPedidos(DataPedido dPedidoPrueba) {
		var dEnvioPrueba = new DataDeliveryPoint();
		dPedidoPrueba.setDataDeliveryPoint(dEnvioPrueba);
		dPedidoPrueba.getDataDeliveryPoint().setCodigo(pgPedidos.getTiendaFisicaFromListaPedidos());
		checkIsTiendaFisica(dPedidoPrueba.getDataDeliveryPoint().getCodigo());
	}
	
	@Validation (description="Tenemos la tienda física #{codigoDeliveryPoint}")
	private boolean checkIsTiendaFisica(String codigoDeliveryPoint) {
		return (!codigoDeliveryPoint.equals(""));
	}
}
