package com.mng.robotest.test80.mango.test.stpv.votfcons;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.votfcons.IframeResult;
import com.mng.robotest.test80.mango.test.pageobject.votfcons.PageConsola;

public class ConsolaVotfStpV {

	@Step (
		description="Nos hemos posicionado en la página inicial de VOTF",
		expected="Aparece la página inicial de VOTF")
    public static void accesoPagInicial(/*String urlVOTF,*/ WebDriver driver) {
        //driver.get(urlVOTF);
        checkAfterAccessInitialPage(driver);
    }
	
	@Validation
	private static ChecksTM checkAfterAccessInitialPage(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece el apartado \"Test servicios VOTF\"",
			PageConsola.existTestServVOTF(driver), State.Warn);
	 	validations.add(
			"Aparece el apartado \"Consola comandos VOTF\"",
			PageConsola.existConsolaComVOTF(driver), State.Warn);
		return validations;
	}
    
	@Step (
		description="Seleccionamos el entorno de #{entorno} en los apartados \"Test servicios VOTF\" y \"Consola comandos VOTF\"",
		expected="El entorno se selecciona correctamente",
		saveErrorData=SaveWhen.Never)
    public static void selectEntornoTestAndCons(String entorno, WebDriver driver) {
        PageConsola.selectEntornoTestServ(driver, entorno);
        PageConsola.selectEntornoConsolaCom(driver, entorno);
    }
    
	@Step (
		description=
			"Introducimos el artículo disponible <b>#{articulo}</b> (a nivel de  artículo disponible y de compra) + la tienda <b>#{tienda}</b>",
		expected=
			"Aparecen datos correspondientes a " + PageConsola.msgConsTiposEnvioOK,
		saveErrorData=SaveWhen.Never)
    public static void inputArticleAndTiendaDisp(String articulo, String tienda, WebDriver driver) throws Exception {
        PageConsola.inputArticDispYCompra(driver, articulo);
        PageConsola.inputTiendas(tienda, driver);
    }
    
	@Step (
		description="Selección botón \"Consultar tipos de envío\"",
		expected="Aparecen datos correspondientes a " + PageConsola.msgConsTiposEnvioOK,
		saveErrorData=SaveWhen.Never)
    public static void consultarTiposEnvio(WebDriver driver) throws Exception {
		PageConsola.clickButtonConsTiposEnvios(driver);           
        String paginaPadre = driver.getWindowHandle();
        checkAfterConsultarTiposEnvio(paginaPadre, driver);
    }
	
	@Validation (
		description="En el bloque de \"Petición/Resultado\" aparece el literal \"" + PageConsola.msgConsTiposEnvioOK + "\"",
		level=State.Warn)
	private static boolean checkAfterConsultarTiposEnvio(String paginaPadre, WebDriver driver) {
		boolean resultado = true;
        try {
            PageConsola.switchToResultIFrame(driver);
            if (!IframeResult.resultadoContainsText(driver, PageConsola.msgConsTiposEnvioOK)) {
                resultado = false;
            }
        } 
        finally {
            driver.switchTo().window(paginaPadre);
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
	public static boolean consultarDispEnvDomic(String articulo, WebDriver driver) throws Exception {
		PageConsola.inputArticDispYCompra(driver, articulo);
		PageConsola.clickButtonConsultarDispEnvioDomicilio(driver);
		return checkAfterClickConsultDispEnvioDomicilio(driver);
	}
	
	private static boolean checkAfterClickConsultDispEnvioDomicilio(WebDriver driver) {
		String codsTransporte = checkCodTransporteWithData(driver).getData();
		if ("".compareTo(codsTransporte)!=0) {
			checkPetResultadoAfterClickConsEnvioDomic(codsTransporte, driver);
			return true;
		}
		return false;
	}
	
	@Validation
	private static ChecksResultWithStringData checkCodTransporteWithData(WebDriver driver) {
		ChecksResultWithStringData validations = ChecksResultWithStringData.getNew();
		int maxSeconds = 10;
		boolean isDataSelectCodigoTrasnp = PageConsola.isDataSelectCodigoTransporte(maxSeconds, driver);
	 	validations.add(
			"En el desplegable \"Código de Transporte\" aparecen datos (lo esperamos hasta " + maxSeconds + " segundos)",
			isDataSelectCodigoTrasnp, State.Warn);
		String codigosTransporte = "";
		if (isDataSelectCodigoTrasnp) {
			codigosTransporte = PageConsola.getCodigoTransporte(driver);
		}
		validations.setData(codigosTransporte);
		return validations;
	}
	
	@Validation
	private static ChecksTM checkPetResultadoAfterClickConsEnvioDomic(String codsTransporte, WebDriver driver) {
		ChecksResultWithStringData validations = ChecksResultWithStringData.getNew();
		String paginaPadre = driver.getWindowHandle();
		try {
			PageConsola.switchToResultIFrame(driver);
			validations.add(
				"En el bloque de \"Petición/Resultado\" aparece una tabla \"transportes__content\"",
				IframeResult.existsTransportes(driver), State.Defect);
		 	validations.add(
				"En la tabla figuran los tipos " + codsTransporte.replace("\n", ","),
				IframeResult.transportesContainsTipos(driver, codsTransporte), State.Defect);
		}
		finally {
			driver.switchTo().window(paginaPadre);
		}
		return validations;
	}

	@Step (
		description="Introducimos el artículo #{articulo} (a nivel de  artículo disponible y de compra) + Seleccionar el botón \"Consultar Disponibilidad Envío Tienda\"",
		expected="Aparece el bloque de transportes y el tipo de stock",
		saveErrorData=SaveWhen.Never)
    public static void consultarDispEnvTienda(String articulo, WebDriver driver) throws Exception {
        PageConsola.inputArticDispYCompra(driver, articulo);
        PageConsola.consDispEnvioTienda(driver);
        String paginaPadre = driver.getWindowHandle();
        checkAfterClickConsultDispEnvioTienda(paginaPadre, driver);
    }
	
	@Validation
	private static ChecksTM checkAfterClickConsultDispEnvioTienda(String paginaPadre, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
        paginaPadre = driver.getWindowHandle();
		try {
	        PageConsola.switchToResultIFrame(driver);
		 	validations.add(
				"En el bloque de \"Petición/Resultado\" NO aparece una tabla \"transportes__content\"",
				!IframeResult.existsTransportes(driver), State.Defect);
		 	validations.add(
				"Aparece una línea de \"TipoStock:\" con contenido",
				IframeResult.isPresentTipoStock(driver), State.Defect);
		}
		finally {
            driver.switchTo().window(paginaPadre);
        }
		
		return validations;
	}
    
	@Step (
		description="Seleccionar el botón \"Realizar Solicitud A Domicilio\"",
		expected="El pedido se crea correctamente",
		saveErrorData=SaveWhen.Never)
    public static String realizarSolicitudTienda(String articulo, WebDriver driver) throws Exception {
        PageConsola.inputArticDispYCompra(driver, articulo);
        PageConsola.clickButtonSolADomicilio(driver);
        String codigoPedido = switchToIframeAndCheckAfterSolicitudAdomicilio(driver);
        return codigoPedido;
    }
	
	private static String switchToIframeAndCheckAfterSolicitudAdomicilio(WebDriver driver) {
		String paginaPadre = driver.getWindowHandle();
		PageConsola.switchToResultIFrame(driver);
		ChecksResultWithStringData checks = checkAfterSolicitudAdomicilioInIframe(driver);
		driver.switchTo().window(paginaPadre);
		return checks.getData();
	}
	
	@Validation
	private static ChecksResultWithStringData checkAfterSolicitudAdomicilioInIframe(WebDriver driver) {
		ChecksResultWithStringData validations = ChecksResultWithStringData.getNew();
		int maxSeconds = 5;
		validations.add(
			"En el bloque de \"Petición/Resultado\" aparece una línea correspondiente al \"Código de pedido\"" + 
			"(la esperamos hasta " + maxSeconds + " segundos)",
			IframeResult.isPresentCodigoPedido(maxSeconds, driver), State.Warn);
		
		String codigoPedido = IframeResult.getCodigoPedido(driver);
		validations.setData(codigoPedido);
		validations.add(
			"Aparece un código de pedido",
			"".compareTo(codigoPedido)!=0, State.Defect);
		validations.add(
			"Aparece el literal \"Resultado creación pedido: (0) Total\"",
			IframeResult.resCreacionPedidoOk(driver), State.Warn);
	 	return validations;
	}

	@Step (
		description="Seleccionar el botón \"Obtener Pedidos\"",
		expected="Aparece la lista de pedidos",
		saveErrorData=SaveWhen.Never)
	public static String obtenerPedidos(String codigoPedido, WebDriver driver) throws Exception {
		PageConsola.clickButtonObtenerPedidos(driver);
		String codigoPedidoFull = switchToIframeAndCheckAfterObtenerPedidos(codigoPedido, driver);
		return codigoPedidoFull;
	}
	
	private static String switchToIframeAndCheckAfterObtenerPedidos(String codigoPedido, WebDriver driver) throws Exception {
		String paginaPadre = driver.getWindowHandle();
		PageConsola.switchToResultIFrame(driver);

		checkAfterObtenerPedidosInIframe(codigoPedido, driver);
		String codigoPedidoFull = IframeResult.getPedidoFromListaPedidosUntil(codigoPedido, 5, driver);
		driver.switchTo().window(paginaPadre);
		return codigoPedidoFull;
	}
	
	private static String checkAfterObtenerPedidosInIframe(String codigoPedido, WebDriver driver) throws Exception {
		checkIsLineaPedidos(5, driver);
		if ("".compareTo(codigoPedido)==0) {
			return codigoPedido;
		}
		
		checkIsPresentPedidoInList(codigoPedido, 5, driver);
		return IframeResult.getPedidoFromListaPedidosUntil(codigoPedido, 1, driver);
	}
	
	@Validation (
		description = "En el bloque de \"Petición/Resultado\" aparece una línea correspondiente al \"Pedidos\" (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private static boolean checkIsLineaPedidos(int maxSeconds, WebDriver driver) {
		return IframeResult.isPresentListaPedidosUntil(maxSeconds, driver);
	}
	
	@Validation (
		description = "En la lista de pedidos aparece el generado anteriormente: #{codigoPedido} (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private static boolean checkIsPresentPedidoInList(String codigoPedido, int maxSeconds, WebDriver driver) throws Exception {
		String codigoPedidoFull = IframeResult.getPedidoFromListaPedidosUntil(codigoPedido, maxSeconds, driver);
	 	return "".compareTo(codigoPedidoFull)!=0;
	}

	@Step (
		description="Seleccionar el pedido #{codigoPedidoFull} en el desplegable \"Pedido\" y pulsar \"Seleccionar pedido\"",
		expected="Aparece el pedido seleccionado",
		saveErrorData=SaveWhen.Never)
	public static void seleccionarPedido(String codigoPedidoFull, WebDriver driver) throws Exception {
		PageConsola.selectPedido(driver, codigoPedidoFull);
		PageConsola.clickButtonSelectPedido(driver);
		checkAfterSelectPedido(codigoPedidoFull, driver);
	}
	
	@Validation (
		description="En el bloque de \"Petición/Resultado\" aparece una línea \"Seleccionado: #{codigoPedidoFull}\"",
		level=State.Warn)
	private static boolean checkAfterSelectPedido(String codigoPedidoFull, WebDriver driver) {
		boolean resultado = true;
		String paginaPadre = driver.getWindowHandle();
		try {  
			PageConsola.switchToResultIFrame(driver);
			if (IframeResult.resSelectPedidoOk(driver, codigoPedidoFull)) {
				resultado = false;
			}
		}
		finally {
			driver.switchTo().window(paginaPadre);
		}

		return resultado;
	}

	@Step (
		description="Pulsar el botón \"Preconfirmar Pedido\"",
		expected="Aparece el pedido como preconfirmado",
		saveErrorData=SaveWhen.Never)
	public static void selectPreconfPedido(String codigoPedidoFull, WebDriver driver) throws Exception {
		PageConsola.clickButtonPreconfPedido(driver);
		checkAfterPreconfirmarPedido(codigoPedidoFull, driver);
	}
	
	@Validation
	private static ChecksTM checkAfterPreconfirmarPedido(String codigoPedidoFull, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		String paginaPadre = driver.getWindowHandle();
		try {
			PageConsola.switchToResultIFrame(driver);
		 	validations.add(
				"En el bloque de \"Petición/Resultado\" aparece una línea \"Preconfirmado\"", 
				IframeResult.isLineaPreconfirmado(driver), State.Warn);
		 	validations.add(
				"Aparece un XML con el dato \"&lt;pedido&gt;" + codigoPedidoFull + "&lt;/pedido&gt;\"",
				IframeResult.isPedidoInXML(codigoPedidoFull, driver), State.Defect);
		}
		finally {
			driver.switchTo().window(paginaPadre);
		}

		return validations;
	}

	@Step (
		description="Pulsar el botón \"Confirmar Pedido\"",
		expected="Aparece el pedido confirmado",
		saveErrorData=SaveWhen.Never)
    public static void selectConfPedido(String codigoPedidoFull, WebDriver driver) throws Exception {
		PageConsola.clickButtonConfPedido(driver);
        checkAfterConfirmarPedido(codigoPedidoFull, driver);
    }
	
	@Validation
	private static ChecksTM checkAfterConfirmarPedido(String codigoPedidoFull, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
        String paginaPadre = driver.getWindowHandle();
        try {
			PageConsola.switchToResultIFrame(driver);
		 	validations.add(
				"En el bloque de \"Petición/Resultado\" aparece una línea \"Confirmado: " + codigoPedidoFull + "\"", 
				IframeResult.resConfPedidoOk(driver, codigoPedidoFull), State.Warn);     
        }
        finally {
            driver.switchTo().window(paginaPadre);
        }       
        
        return validations;
	}
}