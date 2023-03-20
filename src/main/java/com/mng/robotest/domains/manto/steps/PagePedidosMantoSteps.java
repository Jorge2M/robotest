package com.mng.robotest.domains.manto.steps;

import java.util.ArrayList;
import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.compra.steps.envio.DataDeliveryPoint;
import com.mng.robotest.domains.manto.pageobjects.PageDetalleCliente;
import com.mng.robotest.domains.manto.pageobjects.PageDetallePedido;
import com.mng.robotest.domains.manto.pageobjects.PagePedidos;
import com.mng.robotest.domains.manto.pageobjects.PagePedidos.IdColumn;
import com.mng.robotest.domains.manto.pageobjects.PagePedidos.TypeDetalle;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.utils.ImporteScreen;

public class PagePedidosMantoSteps extends StepMantoBase {

	private final PagePedidos pagePedidos = new PagePedidos();
	
	@Validation
	public ChecksResultWithFlagLinkCodPed validaLineaPedido(DataPedido dataPedido) {
		ChecksResultWithFlagLinkCodPed checks = ChecksResultWithFlagLinkCodPed.getNew();
		
		int seconds = 30;
	 	checks.add(
	 		"Desaparece la capa de Loading de \"Consultando\"" + 
	 		" (lo esperamos hasta " + seconds + " segundos)",
			pagePedidos.isInvisibleCapaLoadingUntil(seconds), State.Warn);
	 	
		checks.setExistsLinkCodPed(
			pagePedidos.isPresentDataInPedido(IdColumn.IDPEDIDO, dataPedido.getCodigoPedidoManto(), TypeDetalle.PEDIDO, 0));
		
	 	checks.add(
			"En la columna " + IdColumn.IDPEDIDO.getTextoColumna() + " aparece el código de pedido: " + dataPedido.getCodigoPedidoManto(),
			checks.getExistsLinkCodPed(), State.Warn);
	 	
	 	checks.add(
			"Aparece un solo pedido",
			pagePedidos.getNumLineas()==1, State.Warn);
		
	 	//En el caso de Outlet no tenemos la información del TPV que toca
	 	if (app!=AppEcom.outlet) {
		 	checks.add(
				"En la columna " + IdColumn.TPV.getTextoColumna() + 
				" Aparece el Tpv asociado: " + dataPedido.getPago().getTpv().getId(),
				pagePedidos.isPresentDataInPedido(IdColumn.TPV, dataPedido.getPago().getTpv().getId(), TypeDetalle.PEDIDO, 0), 
				State.Warn);
	 	}
		
	 	checks.add(
			"En la columna " + IdColumn.EMAIL.getTextoColumna() + " aparece el email asociado: " + dataPedido.getEmailCheckout(),
			pagePedidos.isPresentDataInPedido(IdColumn.EMAIL, dataPedido.getEmailCheckout(), TypeDetalle.PEDIDO, 0), 
			State.Warn);
	 	
	 	String xpathCeldaImporte = pagePedidos.getXPathCeldaLineaPedido(IdColumn.TOTAL, TypeDetalle.PEDIDO);
	 	checks.add(
			"En pantalla aparece el importe asociado: " +  dataPedido.getImporteTotalManto(),
			ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), xpathCeldaImporte, driver), 
			State.Warn);
		
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

		pagePedidos.clickLinkPedidoInLineas(pagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual), TypeDetalle.PEDIDO);
		checkCodePedidoOk(dPedidoPrueba);
	}
	
	@Validation (
		description="Tenemos código de pedido #{dPedidoPrueba.getCodpedido()}",
		level=State.Defect)
	private boolean checkCodePedidoOk(DataPedido dPedidoPrueba) {
		return (!dPedidoPrueba.getCodpedido().equals(""));
	}

	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorData=SaveWhen.Never)
	public void setDataPedidoStep(DataPedido dPedidoPrueba) {
		DataBag dBagPrueba = new DataBag();
		List<String> referencias = new ArrayList<>();
		ArticuloScreen articulo;
		referencias = new PageDetallePedido().getReferenciasArticulosDetallePedido();
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
		level=State.Defect)
	private boolean checkPedidoWithReferences(List<String> referencias, DataPedido dPedidoPrueba) {
		return (!dPedidoPrueba.getDataBag().getListArticulos().isEmpty());
	}
	
	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorData=SaveWhen.Never)
	public void setDataCliente(DataPedido dPedidoPrueba) {
		PageDetallePedido pageDetallePedido = new PageDetallePedido();
		pageDetallePedido.clickLinkDetallesCliente();
		PageDetalleCliente pageDetalleCliente = new PageDetalleCliente();
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
			!dPedidoPrueba.getPago().getDni().equals(""), State.Defect);
	 	
	 	checks.add(
			"Tenemos el Email del cliente " + dPedidoPrueba.getPago().getUseremail(),
			!dPedidoPrueba.getPago().getUseremail().equals(""), State.Defect);
	 	
		return checks;
	}
	
	@Step (
		description="Un pedido con tienda física en la lista de pedidos",
		expected="Debemos obtener una tienda física válida",
		saveErrorData=SaveWhen.Never)
	public void setTiendaFisicaListaPedidos(DataPedido dPedidoPrueba) {
		DataDeliveryPoint dEnvioPrueba = new DataDeliveryPoint();
		dPedidoPrueba.setDataDeliveryPoint(dEnvioPrueba);
		dPedidoPrueba.getDataDeliveryPoint().setCodigo(pagePedidos.getTiendaFisicaFromListaPedidos());
		checkIsTiendaFisica(dPedidoPrueba.getDataDeliveryPoint().getCodigo());
	}
	
	@Validation (
		description="Tenemos la tienda física #{codigoDeliveryPoint}",
		level=State.Defect)
	private boolean checkIsTiendaFisica(String codigoDeliveryPoint) {
		return (!codigoDeliveryPoint.equals(""));
	}
}
