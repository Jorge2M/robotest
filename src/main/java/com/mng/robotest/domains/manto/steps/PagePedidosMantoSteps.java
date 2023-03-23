package com.mng.robotest.domains.manto.steps;

import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.compra.steps.envio.DataDeliveryPoint;
import com.mng.robotest.domains.manto.pageobjects.PageDetalleCliente;
import com.mng.robotest.domains.manto.pageobjects.PageDetallePedido;
import com.mng.robotest.domains.manto.pageobjects.PagePedidos;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.mng.robotest.domains.manto.pageobjects.PagePedidos.IdColumn.*;
import static com.mng.robotest.domains.manto.pageobjects.PagePedidos.TypeDetalle.*;
import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePedidosMantoSteps extends StepMantoBase {

	private final PagePedidos pagePedidos = new PagePedidos();
	
	@Validation
	public ChecksResultWithFlagLinkCodPed validaLineaPedido(DataPedido dataPedido) {
		ChecksResultWithFlagLinkCodPed checks = ChecksResultWithFlagLinkCodPed.getNew();
		
		int seconds = 30;
	 	checks.add(String.format(
	 		"Desaparece la capa de Loading de \"Consultando\" (lo esperamos hasta %s segundos)", seconds),
			pagePedidos.isInvisibleCapaLoadingUntil(seconds), Warn);
	 	
		checks.setExistsLinkCodPed(
			pagePedidos.isPresentDataInPedido(IDPEDIDO, dataPedido.getCodigoPedidoManto(), PEDIDO, 0));
		
	 	checks.add(String.format(
	 		"En la columna %s aparece el código de pedido: %s", IDPEDIDO.getTextoColumna(), dataPedido.getCodigoPedidoManto()),
			checks.getExistsLinkCodPed(), Warn);
	 	
	 	checks.add(
			"Aparece un solo pedido",
			pagePedidos.getNumLineas()==1, Warn);
		
	 	//En el caso de Outlet no tenemos la información del TPV que toca
	 	if (app!=AppEcom.outlet) {
		 	checks.add(String.format(
		 		"En la columna %s Aparece el Tpv asociado: %s", TPV.getTextoColumna(), dataPedido.getPago().getTpv().getId()),
				pagePedidos.isPresentDataInPedido(TPV, dataPedido.getPago().getTpv().getId(), PEDIDO, 0), Warn);
	 	}
		
	 	checks.add(String.format(
	 		"En la columna %s aparece el email asociado: %s", EMAIL.getTextoColumna(), dataPedido.getEmailCheckout()),
	 		pagePedidos.isPresentDataInPedido(EMAIL, dataPedido.getEmailCheckout(), PEDIDO, 0), Warn);
	 	
	 	String xpathCeldaImporte = pagePedidos.getXPathCeldaLineaPedido(TOTAL, PEDIDO);
	 	checks.add(
			"En pantalla aparece el importe asociado: " +  dataPedido.getImporteTotalManto(),
			ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), xpathCeldaImporte, driver), 
			Warn);
		
		return checks;
	}
	
	@Step (
		description="Buscamos pedidos con id registro",
		expected="Debemos obtener el ID del pedido",
		saveErrorData=SaveWhen.Never)
	public void setPedidoUsuarioRegistrado(DataPedido dPedidoPrueba) {
		int posicionPedidoActual = 6;
		int posicionMaxPaginaPedidos = 105;
		do {
			posicionPedidoActual++;
			posicionPedidoActual = pagePedidos.getPosicionPedidoUsuarioRegistrado(posicionPedidoActual);
			dPedidoPrueba.setCodpedido(pagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual));
			if (posicionPedidoActual == posicionMaxPaginaPedidos) {
				posicionPedidoActual = 6;
				pagePedidos.clickPaginaSiguientePedidos();
			}
		} while (dPedidoPrueba.getCodpedido().equals(""));

		pagePedidos.clickLinkPedidoInLineas(pagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual), PEDIDO);
		checkCodePedidoOk(dPedidoPrueba);
	}
	
	@Validation (
		description="Tenemos código de pedido #{dPedidoPrueba.getCodpedido()}",
		level=Defect)
	private boolean checkCodePedidoOk(DataPedido dPedidoPrueba) {
		return (!dPedidoPrueba.getCodpedido().equals(""));
	}

	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorData=SaveWhen.Never)
	public void setDataPedidoStep(DataPedido dPedidoPrueba) {
		var dBagPrueba = new DataBag();
		List<String> referencias = new PageDetallePedido().getReferenciasArticulosDetallePedido();
		ArticuloScreen articulo;
		for (String referencia : referencias) {
			articulo = new ArticuloScreen();
			articulo.setReferencia(referencia);
			dBagPrueba.addArticulo(articulo);
		}
		dPedidoPrueba.setDataBag(dBagPrueba);
		checkPedidoWithReferences(referencias, dPedidoPrueba);
	}

	@Validation (
		description="El pedido tiene las referencias #{referencias.toString()}",
		level=Defect)
	private boolean checkPedidoWithReferences(List<String> referencias, DataPedido dPedidoPrueba) {
		return (!dPedidoPrueba.getDataBag().getListArticulos().isEmpty());
	}
	
	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorData=SaveWhen.Never)
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
			!dPedidoPrueba.getPago().getDni().equals(""), Defect);
	 	
	 	checks.add(
			"Tenemos el Email del cliente " + dPedidoPrueba.getPago().getUseremail(),
			!dPedidoPrueba.getPago().getUseremail().equals(""), Defect);
	 	
		return checks;
	}
	
	@Step (
		description="Un pedido con tienda física en la lista de pedidos",
		expected="Debemos obtener una tienda física válida",
		saveErrorData=SaveWhen.Never)
	public void setTiendaFisicaListaPedidos(DataPedido dPedidoPrueba) {
		var dEnvioPrueba = new DataDeliveryPoint();
		dPedidoPrueba.setDataDeliveryPoint(dEnvioPrueba);
		dPedidoPrueba.getDataDeliveryPoint().setCodigo(pagePedidos.getTiendaFisicaFromListaPedidos());
		checkIsTiendaFisica(dPedidoPrueba.getDataDeliveryPoint().getCodigo());
	}
	
	@Validation (
		description="Tenemos la tienda física #{codigoDeliveryPoint}",
		level=Defect)
	private boolean checkIsTiendaFisica(String codigoDeliveryPoint) {
		return (!codigoDeliveryPoint.equals(""));
	}
}
