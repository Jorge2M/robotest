package com.mng.robotest.tests.domains.votfconsole.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.votfconsole.pageobjects.IframeResult;
import com.mng.robotest.tests.domains.votfconsole.pageobjects.PageConsola;
import com.mng.robotest.tests.domains.votfconsole.utils.ChecksResultWithStringData;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class ConsolaVotfSteps extends StepBase {

	private final PageConsola pgConsola = new PageConsola();
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
			pgConsola.existTestServVOTF(), WARN);
	 	
	 	checks.add(
			"Aparece el apartado \"Consola comandos VOTF\"",
			pgConsola.existConsolaComVOTF(), WARN);
	 	
		return checks;
	}
	
	@Step (
		description="Seleccionamos el entorno de #{entorno} en los apartados \"Test servicios VOTF\" y \"Consola comandos VOTF\"",
		expected="El entorno se selecciona correctamente",
		saveErrorData=NEVER)
	public void selectEntornoTestAndCons(String entorno) {
		pgConsola.selectEntornoTestServ(entorno);
		pgConsola.selectEntornoConsolaCom(entorno);
	}
	
	@Step (
		description=
			"Introducimos el artículo disponible <b>#{articulo}</b> (a nivel de  artículo disponible y de compra) + la tienda <b>#{tienda}</b>",
		expected=
			"Aparecen datos correspondientes a " + PageConsola.MSG_CONS_TIPOS_ENVIO_OK,
		saveErrorData=NEVER)
	public void inputArticleAndTiendaDisp(String articulo, String tienda) {
		pgConsola.inputArticDispYCompra(articulo);
		pgConsola.inputTiendas(tienda);
	}
	
	@Step (
		description="Selección botón \"Consultar tipos de envío\"",
		expected="Aparecen datos correspondientes a " + PageConsola.MSG_CONS_TIPOS_ENVIO_OK,
		saveErrorData=NEVER)
	public void consultarTiposEnvio() {
		pgConsola.clickButtonConsTiposEnvios();
		String paginaPadre = pgConsola.driver.getWindowHandle();
		checkAfterConsultarTiposEnvio(paginaPadre);
	}
	
	@Validation (
		description="En el bloque de \"Petición/Resultado\" aparece el literal \"" + PageConsola.MSG_CONS_TIPOS_ENVIO_OK + "\"",
		level=WARN)
	private boolean checkAfterConsultarTiposEnvio(String paginaPadre) {
		boolean resultado = true;
		try {
			pgConsola.switchToResultIFrame();
			if (!iframeResult.resultadoContainsText(PageConsola.MSG_CONS_TIPOS_ENVIO_OK)) {
				resultado = false;
			}
		} 
		finally {
			pgConsola.driver.switchTo().window(paginaPadre);
		}
		
		return resultado;
	}
	
	@Step (
		description=
			"Introducimos el artículo <b>#{articulo}</b> (a nivel de  artículo disponible y de compra) + " + 
			"Seleccionar el botón \"Consultar Disponibilidad Envío Domicilio\"",
		expected=
			"Aparece la tabla de transportes con los tipos",
		saveErrorData=NEVER)
	public boolean consultarDispEnvDomic(String articulo) {
		pgConsola.inputArticDispYCompra(articulo);
		pgConsola.clickButtonConsultarDispEnvioDomicilio();
		return checkAfterClickConsultDispEnvioDomicilio().areAllChecksOvercomed();
	}
	
	@Validation
	private ChecksTM checkAfterClickConsultDispEnvioDomicilio() {
		ChecksTM checks = ChecksResultWithStringData.getNew();
		String paginaPadre = pgConsola.driver.getWindowHandle();
		try {
			pgConsola.switchToResultIFrame();
			checks.add(
				"En el bloque de \"Petición/Resultado\" aparece una tabla \"Transportes\"",
				iframeResult.existsTransportes(), WARN);
			
			checks.add(
				"En el bloque de \"Petición/Resultado\" aparece una tabla \"Disponibilidad\"",
				iframeResult.existsDisponibilidad(), WARN);
			
		 	checks.add(
				"En la tabla \"Disponibilidad\" figura el campo <b>Disponible=true</b>",
				iframeResult.flagDisponibleIsTrue(), WARN);
		}
		finally {
			pgConsola.driver.switchTo().window(paginaPadre);
		}
		return checks;
	}
	
	@Step (
		description="Introducimos el artículo #{articulo} (a nivel de  artículo disponible y de compra) + Seleccionar el botón \"Consultar Disponibilidad Envío Tienda\"",
		expected="Aparece el bloque de transportes y el tipo de stock",
		saveErrorData=NEVER)
	public void consultarDispEnvTienda(String articulo) {
		pgConsola.inputArticDispYCompra(articulo);
		pgConsola.consDispEnvioTienda();
		String paginaPadre = pgConsola.driver.getWindowHandle();
		checkAfterClickConsultDispEnvioTienda(paginaPadre);
	}
	
	@Validation
	private ChecksTM checkAfterClickConsultDispEnvioTienda(String paginaPadre) {
		var checks = ChecksTM.getNew();
		try {
			pgConsola.switchToResultIFrame();
		 	checks.add(
				"En el bloque de \"Petición/Resultado\" NO aparece una tabla \"transportes__content\"",
				!iframeResult.existsTransportes());
		 	
		 	checks.add(
				"Aparece una línea de \"TipoStock:\" con contenido",
				iframeResult.isPresentTipoStock());
		}
		finally {
			pgConsola.driver.switchTo().window(paginaPadre);
		}
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el botón \"Realizar Solicitud A Domicilio\"",
		expected="El pedido se crea correctamente",
		saveErrorData=NEVER)
	public String realizarSolicitudTienda(String articulo) {
		pgConsola.inputArticDispYCompra(articulo);
		pgConsola.clickButtonSolADomicilio();
		return switchToIframeAndCheckAfterSolicitudAdomicilio();
	}
	
	private String switchToIframeAndCheckAfterSolicitudAdomicilio() {
		String paginaPadre = pgConsola.driver.getWindowHandle();
		pgConsola.switchToResultIFrame();
		var checks = checkAfterSolicitudAdomicilioInIframe();
		pgConsola.driver.switchTo().window(paginaPadre);
		return checks.getData();
	}
	
	@Validation
	private ChecksResultWithStringData checkAfterSolicitudAdomicilioInIframe() {
		var checks = ChecksResultWithStringData.getNew();
		int seconds = 5;
		checks.add(
			"En el bloque de \"Petición/Resultado\" aparece una línea correspondiente al \"Código de pedido\"" + 
			getLitSecondsWait(seconds),
			iframeResult.isPresentCodigoPedido(seconds), WARN);
		
		String codigoPedido = iframeResult.getCodigoPedido();
		checks.setData(codigoPedido);
		checks.add(
			"Aparece un código de pedido",
			"".compareTo(codigoPedido)!=0);
		
		checks.add(
			"Aparece el literal \"Resultado creación pedido: (0) Total\"",
			iframeResult.resCreacionPedidoOk(), WARN);
		
	 	return checks;
	}

	@Step (
		description="Seleccionar el botón \"Obtener Pedidos\"",
		expected="Aparece la lista de pedidos",
		saveErrorData=NEVER)
	public String obtenerPedidos(String codigoPedido) {
		pgConsola.clickButtonObtenerPedidos();
		return switchToIframeAndCheckAfterObtenerPedidos(codigoPedido);
	}
	
	private String switchToIframeAndCheckAfterObtenerPedidos(String codigoPedido) {
		String paginaPadre = pgConsola.driver.getWindowHandle();
		pgConsola.switchToResultIFrame();

		checkAfterObtenerPedidosInIframe(codigoPedido);
		String codigoPedidoFull = iframeResult.getPedidoFromListaPedidosUntil(codigoPedido, 5);
		pgConsola.driver.switchTo().window(paginaPadre);
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
		level=WARN)
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
		saveErrorData=NEVER)
	public void seleccionarPedido(String codigoPedidoFull) {
		pgConsola.selectPedido(codigoPedidoFull);
		pgConsola.clickButtonSelectPedido();
		checkAfterSelectPedido(codigoPedidoFull);
	}
	
	@Validation (
		description="En el bloque de \"Petición/Resultado\" aparece una línea \"Seleccionado: #{codigoPedidoFull}\"",
		level=WARN)
	private boolean checkAfterSelectPedido(String codigoPedidoFull) {
		boolean resultado = true;
		String paginaPadre = pgConsola.driver.getWindowHandle();
		try {  
			pgConsola.switchToResultIFrame();
			if (iframeResult.resSelectPedidoOk(codigoPedidoFull)) {
				resultado = false;
			}
		}
		finally {
			pgConsola.driver.switchTo().window(paginaPadre);
		}

		return resultado;
	}

	@Step (
		description="Pulsar el botón \"Preconfirmar Pedido\"",
		expected="Aparece el pedido como preconfirmado",
		saveErrorData=NEVER)
	public void selectPreconfPedido(String codigoPedidoFull) {
		pgConsola.clickButtonPreconfPedido();
		checkAfterPreconfirmarPedido(codigoPedidoFull);
	}
	
	@Validation
	private ChecksTM checkAfterPreconfirmarPedido(String codigoPedidoFull) {
		var checks = ChecksTM.getNew();
		String paginaPadre = pgConsola.driver.getWindowHandle();
		try {
			pgConsola.switchToResultIFrame();
		 	checks.add(
				"En el bloque de \"Petición/Resultado\" aparece una línea \"Preconfirmado\"", 
				iframeResult.isLineaPreconfirmado(), WARN);
		 	
		 	checks.add(
				"Aparece un XML con el dato \"&lt;pedido&gt;" + codigoPedidoFull + "&lt;/pedido&gt;\"",
				iframeResult.isPedidoInXML(codigoPedidoFull));
		}
		finally {
			pgConsola.driver.switchTo().window(paginaPadre);
		}

		return checks;
	}

	@Step (
		description="Pulsar el botón \"Confirmar Pedido\"",
		expected="Aparece el pedido confirmado",
		saveErrorData=NEVER)
	public void selectConfPedido(String codigoPedidoFull) {
		pgConsola.clickButtonConfPedido();
		checkAfterConfirmarPedido(codigoPedidoFull);
	}
	
	@Validation
	private ChecksTM checkAfterConfirmarPedido(String codigoPedidoFull) {
		var checks = ChecksTM.getNew();
		String paginaPadre = pgConsola.driver.getWindowHandle();
		try {
			pgConsola.switchToResultIFrame();
		 	checks.add(
				"En el bloque de \"Petición/Resultado\" aparece una línea \"Confirmado: " + codigoPedidoFull + "\"", 
				iframeResult.resConfPedidoOk(codigoPedidoFull), WARN);	 
		}
		finally {
			pgConsola.driver.switchTo().window(paginaPadre);
		}	   
		
		return checks;
	}
}