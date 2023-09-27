package com.mng.robotest.domains.votfconsole.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.votfconsole.pageobjects.IframeResult;
import com.mng.robotest.domains.votfconsole.pageobjects.PageConsola;
import com.mng.robotest.domains.votfconsole.utils.ChecksResultWithStringData;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ConsolaVotfSteps extends StepBase {

	private final PageConsola pageConsola = new PageConsola();
	private final IframeResult iframeResult = new IframeResult();
	
	@Step (
		description="Nos hemos posicionado en la página inicial de VOTF",
		expected="Aparece la página inicial de VOTF")
	public void accesoPagInicial() {
		checkAfterAccessInitialPage();
	}
	
	@Validation
	private ChecksTM checkAfterAccessInitialPage() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece el apartado \"Test servicios VOTF\"",
			pageConsola.existTestServVOTF(), Warn);
	 	
	 	checks.add(
			"Aparece el apartado \"Consola comandos VOTF\"",
			pageConsola.existConsolaComVOTF(), Warn);
	 	
		return checks;
	}
	
	@Step (
		description="Seleccionamos el entorno de #{entorno} en los apartados \"Test servicios VOTF\" y \"Consola comandos VOTF\"",
		expected="El entorno se selecciona correctamente",
		saveErrorData=SaveWhen.Never)
	public void selectEntornoTestAndCons(String entorno) {
		pageConsola.selectEntornoTestServ(entorno);
		pageConsola.selectEntornoConsolaCom(entorno);
	}
	
	@Step (
		description=
			"Introducimos el artículo disponible <b>#{articulo}</b> (a nivel de  artículo disponible y de compra) + la tienda <b>#{tienda}</b>",
		expected=
			"Aparecen datos correspondientes a " + PageConsola.MSG_CONS_TIPOS_ENVIO_OK,
		saveErrorData=SaveWhen.Never)
	public void inputArticleAndTiendaDisp(String articulo, String tienda) {
		pageConsola.inputArticDispYCompra(articulo);
		pageConsola.inputTiendas(tienda);
	}
	
	@Step (
		description="Selección botón \"Consultar tipos de envío\"",
		expected="Aparecen datos correspondientes a " + PageConsola.MSG_CONS_TIPOS_ENVIO_OK,
		saveErrorData=SaveWhen.Never)
	public void consultarTiposEnvio() {
		pageConsola.clickButtonConsTiposEnvios();
		String paginaPadre = pageConsola.driver.getWindowHandle();
		checkAfterConsultarTiposEnvio(paginaPadre);
	}
	
	@Validation (
		description="En el bloque de \"Petición/Resultado\" aparece el literal \"" + PageConsola.MSG_CONS_TIPOS_ENVIO_OK + "\"",
		level=Warn)
	private boolean checkAfterConsultarTiposEnvio(String paginaPadre) {
		boolean resultado = true;
		try {
			pageConsola.switchToResultIFrame();
			if (!iframeResult.resultadoContainsText(PageConsola.MSG_CONS_TIPOS_ENVIO_OK)) {
				resultado = false;
			}
		} 
		finally {
			pageConsola.driver.switchTo().window(paginaPadre);
		}
		
		return resultado;
	}
	
	@Step (
		description=
			"Introducimos el artículo <b>#{articulo}</b> (a nivel de  artículo disponible y de compra) + " + 
			"Seleccionar el botón \"Consultar Disponibilidad Envío Domicilio\"",
		expected=
			"Aparece la tabla de transportes con los tipos",
		saveErrorData=SaveWhen.Never)
	public boolean consultarDispEnvDomic(String articulo) {
		pageConsola.inputArticDispYCompra(articulo);
		pageConsola.clickButtonConsultarDispEnvioDomicilio();
		return checkAfterClickConsultDispEnvioDomicilio().areAllChecksOvercomed();
	}
	
	@Validation
	private ChecksTM checkAfterClickConsultDispEnvioDomicilio() {
		ChecksTM checks = ChecksResultWithStringData.getNew();
		String paginaPadre = pageConsola.driver.getWindowHandle();
		try {
			pageConsola.switchToResultIFrame();
			checks.add(
				"En el bloque de \"Petición/Resultado\" aparece una tabla \"Transportes\"",
				iframeResult.existsTransportes(), Warn);
			
			checks.add(
				"En el bloque de \"Petición/Resultado\" aparece una tabla \"Disponibilidad\"",
				iframeResult.existsDisponibilidad(), Warn);
			
		 	checks.add(
				"En la tabla \"Disponibilidad\" figura el campo <b>Disponible=true</b>",
				iframeResult.flagDisponibleIsTrue(), Warn);
		}
		finally {
			pageConsola.driver.switchTo().window(paginaPadre);
		}
		return checks;
	}
	
	@Step (
		description="Introducimos el artículo #{articulo} (a nivel de  artículo disponible y de compra) + Seleccionar el botón \"Consultar Disponibilidad Envío Tienda\"",
		expected="Aparece el bloque de transportes y el tipo de stock",
		saveErrorData=SaveWhen.Never)
	public void consultarDispEnvTienda(String articulo) {
		pageConsola.inputArticDispYCompra(articulo);
		pageConsola.consDispEnvioTienda();
		String paginaPadre = pageConsola.driver.getWindowHandle();
		checkAfterClickConsultDispEnvioTienda(paginaPadre);
	}
	
	@Validation
	private ChecksTM checkAfterClickConsultDispEnvioTienda(String paginaPadre) {
		var checks = ChecksTM.getNew();
		try {
			pageConsola.switchToResultIFrame();
		 	checks.add(
				"En el bloque de \"Petición/Resultado\" NO aparece una tabla \"transportes__content\"",
				!iframeResult.existsTransportes());
		 	
		 	checks.add(
				"Aparece una línea de \"TipoStock:\" con contenido",
				iframeResult.isPresentTipoStock());
		}
		finally {
			pageConsola.driver.switchTo().window(paginaPadre);
		}
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el botón \"Realizar Solicitud A Domicilio\"",
		expected="El pedido se crea correctamente",
		saveErrorData=SaveWhen.Never)
	public String realizarSolicitudTienda(String articulo) {
		pageConsola.inputArticDispYCompra(articulo);
		pageConsola.clickButtonSolADomicilio();
		return switchToIframeAndCheckAfterSolicitudAdomicilio();
	}
	
	private String switchToIframeAndCheckAfterSolicitudAdomicilio() {
		String paginaPadre = pageConsola.driver.getWindowHandle();
		pageConsola.switchToResultIFrame();
		ChecksResultWithStringData checks = checkAfterSolicitudAdomicilioInIframe();
		pageConsola.driver.switchTo().window(paginaPadre);
		return checks.getData();
	}
	
	@Validation
	private ChecksResultWithStringData checkAfterSolicitudAdomicilioInIframe() {
		ChecksResultWithStringData checks = ChecksResultWithStringData.getNew();
		int seconds = 5;
		checks.add(
			"En el bloque de \"Petición/Resultado\" aparece una línea correspondiente al \"Código de pedido\"" + 
			"(la esperamos hasta " + seconds + " segundos)",
			iframeResult.isPresentCodigoPedido(seconds), Warn);
		
		String codigoPedido = iframeResult.getCodigoPedido();
		checks.setData(codigoPedido);
		checks.add(
			"Aparece un código de pedido",
			"".compareTo(codigoPedido)!=0);
		
		checks.add(
			"Aparece el literal \"Resultado creación pedido: (0) Total\"",
			iframeResult.resCreacionPedidoOk(), Warn);
		
	 	return checks;
	}

	@Step (
		description="Seleccionar el botón \"Obtener Pedidos\"",
		expected="Aparece la lista de pedidos",
		saveErrorData=SaveWhen.Never)
	public String obtenerPedidos(String codigoPedido) {
		pageConsola.clickButtonObtenerPedidos();
		return switchToIframeAndCheckAfterObtenerPedidos(codigoPedido);
	}
	
	private String switchToIframeAndCheckAfterObtenerPedidos(String codigoPedido) {
		String paginaPadre = pageConsola.driver.getWindowHandle();
		pageConsola.switchToResultIFrame();

		checkAfterObtenerPedidosInIframe(codigoPedido);
		String codigoPedidoFull = iframeResult.getPedidoFromListaPedidosUntil(codigoPedido, 5);
		pageConsola.driver.switchTo().window(paginaPadre);
		return codigoPedidoFull;
	}
	
	private String checkAfterObtenerPedidosInIframe(String codigoPedido) {
		checkIsLineaPedidos(5);
		if ("".compareTo(codigoPedido)==0) {
			return codigoPedido;
		}
		
		checkIsPresentPedidoInList(codigoPedido, 5);
		return iframeResult.getPedidoFromListaPedidosUntil(codigoPedido, 1);
	}
	
	@Validation (
		description = "En el bloque de \"Petición/Resultado\" aparece una línea correspondiente al \"Pedidos\" " + SECONDS_WAIT,
		level=Warn)
	private boolean checkIsLineaPedidos(int seconds) {
		return iframeResult.isPresentListaPedidosUntil(seconds);
	}
	
	@Validation (
		description = "En la lista de pedidos aparece el generado anteriormente: #{codigoPedido} " + SECONDS_WAIT)
	private boolean checkIsPresentPedidoInList(String codigoPedido, int seconds) {
		String codigoPedidoFull = iframeResult.getPedidoFromListaPedidosUntil(codigoPedido, seconds);
	 	return "".compareTo(codigoPedidoFull)!=0;
	}

	@Step (
		description="Seleccionar el pedido #{codigoPedidoFull} en el desplegable \"Pedido\" y pulsar \"Seleccionar pedido\"",
		expected="Aparece el pedido seleccionado",
		saveErrorData=SaveWhen.Never)
	public void seleccionarPedido(String codigoPedidoFull) {
		pageConsola.selectPedido(codigoPedidoFull);
		pageConsola.clickButtonSelectPedido();
		checkAfterSelectPedido(codigoPedidoFull);
	}
	
	@Validation (
		description="En el bloque de \"Petición/Resultado\" aparece una línea \"Seleccionado: #{codigoPedidoFull}\"",
		level=Warn)
	private boolean checkAfterSelectPedido(String codigoPedidoFull) {
		boolean resultado = true;
		String paginaPadre = pageConsola.driver.getWindowHandle();
		try {  
			pageConsola.switchToResultIFrame();
			if (iframeResult.resSelectPedidoOk(codigoPedidoFull)) {
				resultado = false;
			}
		}
		finally {
			pageConsola.driver.switchTo().window(paginaPadre);
		}

		return resultado;
	}

	@Step (
		description="Pulsar el botón \"Preconfirmar Pedido\"",
		expected="Aparece el pedido como preconfirmado",
		saveErrorData=SaveWhen.Never)
	public void selectPreconfPedido(String codigoPedidoFull) {
		pageConsola.clickButtonPreconfPedido();
		checkAfterPreconfirmarPedido(codigoPedidoFull);
	}
	
	@Validation
	private ChecksTM checkAfterPreconfirmarPedido(String codigoPedidoFull) {
		var checks = ChecksTM.getNew();
		String paginaPadre = pageConsola.driver.getWindowHandle();
		try {
			pageConsola.switchToResultIFrame();
		 	checks.add(
				"En el bloque de \"Petición/Resultado\" aparece una línea \"Preconfirmado\"", 
				iframeResult.isLineaPreconfirmado(), Warn);
		 	
		 	checks.add(
				"Aparece un XML con el dato \"&lt;pedido&gt;" + codigoPedidoFull + "&lt;/pedido&gt;\"",
				iframeResult.isPedidoInXML(codigoPedidoFull));
		}
		finally {
			pageConsola.driver.switchTo().window(paginaPadre);
		}

		return checks;
	}

	@Step (
		description="Pulsar el botón \"Confirmar Pedido\"",
		expected="Aparece el pedido confirmado",
		saveErrorData=SaveWhen.Never)
	public void selectConfPedido(String codigoPedidoFull) {
		pageConsola.clickButtonConfPedido();
		checkAfterConfirmarPedido(codigoPedidoFull);
	}
	
	@Validation
	private ChecksTM checkAfterConfirmarPedido(String codigoPedidoFull) {
		var checks = ChecksTM.getNew();
		String paginaPadre = pageConsola.driver.getWindowHandle();
		try {
			pageConsola.switchToResultIFrame();
		 	checks.add(
				"En el bloque de \"Petición/Resultado\" aparece una línea \"Confirmado: " + codigoPedidoFull + "\"", 
				iframeResult.resConfPedidoOk(codigoPedidoFull), Warn);	 
		}
		finally {
			pageConsola.driver.switchTo().window(paginaPadre);
		}	   
		
		return checks;
	}
}