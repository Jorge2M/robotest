package com.mng.robotest.test.steps.manto;

import java.util.List;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.steps.envio.DataDeliveryPoint;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.manto.PageDetalleCliente;
import com.mng.robotest.test.pageobject.manto.pedido.PageDetallePedido;
import com.mng.robotest.test.pageobject.manto.pedido.PagePedidos;
import com.mng.robotest.test.pageobject.manto.pedido.PagePedidos.TypeDetalle;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.mng.robotest.test.pageobject.manto.pedido.PagePedidos.IdColumn.*;


public class PagePedidosMantoSteps extends StepBase {

	private final PagePedidos pagePedidos = new PagePedidos();
	
	@Validation
	public ChecksResultWithFlagLinkCodPed validaLineaPedido(DataPedido dataPedido) {
		ChecksResultWithFlagLinkCodPed checks = ChecksResultWithFlagLinkCodPed.getNew();
		
		int seconds = 30;
	 	checks.add(
			"Desaparece la capa de Loading de \"Consultando\"" + " (lo esperamos hasta " + seconds + " segundos)",
			pagePedidos.isInvisibleCapaLoadingUntil(seconds), State.Defect);
	 	
	 	checks.setExistsLinkCodPed(pagePedidos.isPresentDataInPedido(IDPEDIDO, dataPedido.getCodigoPedidoManto(), TypeDetalle.PEDIDO, 0));
	 	
	 	checks.add(
			"En la columna " + IDPEDIDO.getTextoColumna() + " aparece el código de pedido: " + dataPedido.getCodigoPedidoManto(),
			checks.getExistsLinkCodPed(), State.Warn);
	 	
	 	checks.add(
			"Aparece un solo pedido",
			pagePedidos.getNumLineas()==1, State.Warn);
		
		if (app!=AppEcom.outlet) {
		 	checks.add(
				"En la columna " + TPV.getTextoColumna() + " Aparece el Tpv asociado: " + dataPedido.getPago().getTpv().getId(),
				pagePedidos.isPresentDataInPedido(TPV, dataPedido.getPago().getTpv().getId(), TypeDetalle.PEDIDO, 0), 
				State.Warn);
		}

	 	checks.add(
			"En la columna " + EMAIL.getTextoColumna() + " aparece el email asociado: " + dataPedido.getEmailCheckout(),
			pagePedidos.isPresentDataInPedido(EMAIL, dataPedido.getEmailCheckout(), TypeDetalle.PEDIDO, 0), 
			State.Warn);
	 	
	 	String xpathCeldaImporte = pagePedidos.getXPathCeldaLineaPedido(TOTAL, TypeDetalle.PEDIDO);
	 	checks.add(
			"En pantalla aparece el importe asociado: " +  dataPedido.getImporteTotalManto(),
			ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), xpathCeldaImporte, driver), 
			State.Warn);
	 	
	 	checks.add(
			"En la columna " + TARJETA.getTextoColumna() + " aparece el tipo de tarjeta: " + dataPedido.getCodtipopago(),
			pagePedidos.isPresentDataInPedido(TARJETA, dataPedido.getCodtipopago(), TypeDetalle.PEDIDO, 0), 
			State.Warn);
		
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
	
	@Validation (
		description="Tenemos código de pedido #{codPedido}",
		level=State.Defect)
	private static boolean checkCodigoPedido(String codPedido, WebDriver driver) {
		return (("".compareTo(codPedido))!=0);
	}

	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorData=SaveWhen.Never)
	public DataPedido getDataPedido(DataPedido dPedidoPrueba) {
		DataBag dBagPrueba = new DataBag();
		List<String> referencias = new PageDetallePedido().getReferenciasArticulosDetallePedido();
		for (String referencia : referencias) {
			ArticuloScreen articulo = new ArticuloScreen();
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
	private boolean checkPedidoWithReferences(List<String> referencias, DataPedido dPedidoPrueba) {
		return (!dPedidoPrueba.getDataBag().getListArticulos().isEmpty());
	}

	@Step (
		description="Buscamos pedidos con id registro para obtener información del cliente",
		expected="Debemos obtener la información del cliente",
		saveErrorData=SaveWhen.Never)
	public DataPedido getDataCliente(DataPedido dPedidoPrueba) {
		new PageDetallePedido().clickLinkDetallesCliente();
		PageDetalleCliente pageDetalleCliente = new PageDetalleCliente();
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
		ChecksTM checks = ChecksTM.getNew();
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
	public DataPedido getTiendaFisicaListaPedidos(DataPedido dPedidoPrueba) {
		DataDeliveryPoint dEnvioPrueba = new DataDeliveryPoint();
		dPedidoPrueba.setDataDeliveryPoint(dEnvioPrueba);
		dPedidoPrueba.getDataDeliveryPoint().setCodigo(pagePedidos.getTiendaFisicaFromListaPedidos());
		String codigoDeliveryPoint = dPedidoPrueba.getDataDeliveryPoint().getCodigo();
		checkTiendaFisica(codigoDeliveryPoint);

		return dPedidoPrueba;
	}
	
	@Validation (
		description="Tenemos la tienda física #{codigoDeliveryPoint}",
		level=State.Defect)
	private boolean checkTiendaFisica(String codigoDeliveryPoint) {
		return (!codigoDeliveryPoint.equals(""));
	}
}
