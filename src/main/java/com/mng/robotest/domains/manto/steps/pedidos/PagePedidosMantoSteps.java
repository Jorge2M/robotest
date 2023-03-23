package com.mng.robotest.domains.manto.steps.pedidos;

import static com.mng.robotest.domains.manto.pageobjects.PagePedidos.IdColumn.*;

import java.util.List;
import org.openqa.selenium.WebDriver;

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
import com.mng.robotest.domains.manto.pageobjects.PagePedidos.TypeDetalle;
import com.mng.robotest.domains.manto.steps.ChecksResultWithFlagLinkCodPed;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePedidosMantoSteps extends StepMantoBase {

	private final PagePedidos pagePedidos = new PagePedidos();
	
	@Validation
	public ChecksResultWithFlagLinkCodPed validaLineaPedido(DataPedido dataPedido) {
		ChecksResultWithFlagLinkCodPed checks = ChecksResultWithFlagLinkCodPed.getNew();
		
		int seconds = 30;
	 	checks.add(
			"Desaparece la capa de Loading de \"Consultando\"" + " (lo esperamos hasta " + seconds + " segundos)",
			pagePedidos.isInvisibleCapaLoadingUntil(seconds));
	 	
	 	checks.setExistsLinkCodPed(pagePedidos.isPresentDataInPedido(IDPEDIDO, dataPedido.getCodigoPedidoManto(), TypeDetalle.PEDIDO, 0));
	 	
	 	checks.add(
			"En la columna " + IDPEDIDO.getTextoColumna() + " aparece el código de pedido: " + dataPedido.getCodigoPedidoManto(),
			checks.getExistsLinkCodPed(), Warn);
	 	
	 	checks.add(
			"Aparece un solo pedido",
			pagePedidos.getNumLineas()==1, Warn);
		
		if (app!=AppEcom.outlet) {
		 	checks.add(
				"En la columna " + TPV.getTextoColumna() + " Aparece el Tpv asociado: " + dataPedido.getPago().getTpv().getId(),
				pagePedidos.isPresentDataInPedido(TPV, dataPedido.getPago().getTpv().getId(), TypeDetalle.PEDIDO, 0), 
				Warn);
		}

	 	checks.add(
			"En la columna " + EMAIL.getTextoColumna() + " aparece el email asociado: " + dataPedido.getEmailCheckout(),
			pagePedidos.isPresentDataInPedido(EMAIL, dataPedido.getEmailCheckout(), TypeDetalle.PEDIDO, 0), 
			Warn);
	 	
	 	String xpathCeldaImporte = pagePedidos.getXPathCeldaLineaPedido(TOTAL, TypeDetalle.PEDIDO);
	 	checks.add(
			"En pantalla aparece el importe asociado: " +  dataPedido.getImporteTotalManto(),
			ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), xpathCeldaImporte, driver), 
			Warn);
	 	
	 	checks.add(
			"En la columna " + TARJETA.getTextoColumna() + " aparece el tipo de tarjeta: " + dataPedido.getCodtipopago(),
			pagePedidos.isPresentDataInPedido(TARJETA, dataPedido.getCodtipopago(), TypeDetalle.PEDIDO, 0), 
			Warn);
		
		return checks;
	}
	
	@Step (
		description="Buscamos pedidos con id registro",
		expected="Debemos obtener el ID del pedido",
		saveErrorData=SaveWhen.Never)
	public DataPedido getPedidoUsuarioRegistrado(DataPedido dPedidoPrueba) {
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
		} 
		while (dPedidoPrueba.getCodpedido().equals(""));

		pagePedidos.clickLinkPedidoInLineas(pagePedidos.getCodigoPedidoUsuarioRegistrado(posicionPedidoActual), TypeDetalle.PEDIDO);
		checkCodigoPedido(dPedidoPrueba.getCodpedido(), driver);
		
		return dPedidoPrueba;
	}
	
	@Validation (description="Tenemos código de pedido #{codPedido}")
	private static boolean checkCodigoPedido(String codPedido, WebDriver driver) {
		return (("".compareTo(codPedido))!=0);
	}

	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorData=SaveWhen.Never)
	public DataPedido getDataPedido(DataPedido dPedidoPrueba) {
		var dBagPrueba = new DataBag();
		List<String> referencias = new PageDetallePedido().getReferenciasArticulosDetallePedido();
		for (String referencia : referencias) {
			var articulo = new ArticuloScreen();
			articulo.setReferencia(referencia);
			dBagPrueba.addArticulo(articulo);
		}
		dPedidoPrueba.setDataBag(dBagPrueba);

		checkPedidoWithReferences(referencias, dPedidoPrueba);
		return dPedidoPrueba;
	}
	
	@Validation (description="El pedido tiene las referencias #{referencias.toString()}")
	private boolean checkPedidoWithReferences(List<String> referencias, DataPedido dPedidoPrueba) {
		return (!dPedidoPrueba.getDataBag().getListArticulos().isEmpty());
	}

	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorData=SaveWhen.Never)
	public DataPedido getDataCliente(DataPedido dPedidoPrueba) {
		new PageDetallePedido().clickLinkDetallesCliente();
		var pageDetalleCliente = new PageDetalleCliente();
		dPedidoPrueba.getPago().setDni(pageDetalleCliente.getUserDniText());
		if (dPedidoPrueba.getPago().getDni().equals("")) {
			dPedidoPrueba.getPago().setDni("41507612h");
		}
		
		dPedidoPrueba.getPago().setUseremail(pageDetalleCliente.getUserEmailText());
		pageDetalleCliente.clickLinkVolverPedidos();
		checkAfterSearchPedido(dPedidoPrueba, driver);
		
		return dPedidoPrueba;
	}

	@Validation
	private static ChecksTM checkAfterSearchPedido(DataPedido dPedidoPrueba, WebDriver driver) {
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
		saveErrorData=SaveWhen.Never)
	public DataPedido getTiendaFisicaListaPedidos(DataPedido dPedidoPrueba) {
		var dEnvioPrueba = new DataDeliveryPoint();
		dPedidoPrueba.setDataDeliveryPoint(dEnvioPrueba);
		dPedidoPrueba.getDataDeliveryPoint().setCodigo(pagePedidos.getTiendaFisicaFromListaPedidos());
		String codigoDeliveryPoint = dPedidoPrueba.getDataDeliveryPoint().getCodigo();
		checkTiendaFisica(codigoDeliveryPoint);

		return dPedidoPrueba;
	}
	
	@Validation (description="Tenemos la tienda física #{codigoDeliveryPoint}")
	private boolean checkTiendaFisica(String codigoDeliveryPoint) {
		return (!codigoDeliveryPoint.equals(""));
	}
}
