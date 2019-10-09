package com.mng.robotest.test80.mango.test.stpv.votfcons;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.votfcons.IframeResult;
import com.mng.robotest.test80.mango.test.pageobject.votfcons.PageConsola;

public class ConsolaVotfStpV {

	@Step (
		description="Accedemos a la página inicial de VOTF",
		expected="Aparece la página inicial de VOTF")
    public static void accesoPagInicial(String urlVOTF, WebDriver driver) {
        driver.get(urlVOTF);
        checkAfterAccessInitialPage(driver);
    }
	
	@Validation
	private static ChecksResult checkAfterAccessInitialPage(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
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
		description="Introducimos el artículo disponible #{articulo} (a nivel de  artículo disponible y de compra) + la tienda #{tienda}",
		expected="Aparecen datos correspondientes a " + PageConsola.msgConsTiposEnvioOK,
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
		description="Introducimos el artículo #{articulo} (a nivel de  artículo disponible y de compra) + Seleccionar el botón \"Consultar Disponibilidad Envío Domicilio\"",
		expected="Aparece la tabla de transportes con los tipos",
		saveErrorData=SaveWhen.Never)
    public static void consultarDispEnvDomic(String articulo, WebDriver driver) throws Exception {
        PageConsola.inputArticDispYCompra(driver, articulo);
        PageConsola.clickButtonConsultarDispEnvioDomicilio(driver);
        checkAfterClickConsultDispEnvioDomicilio(driver);
    }
	
	@Validation
	private static ChecksResult checkAfterClickConsultDispEnvioDomicilio(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsToWait = 5;
		boolean isDataSelectCodigoTrasnp = PageConsola.isDataSelectCodigoTransporte(maxSecondsToWait, driver);
	 	validations.add(
			"En el desplegable \"Código de Transporte\" aparecen datos (lo esperamos hasta XX segundos)",
			isDataSelectCodigoTrasnp, State.Defect);
	 	String codigosTransporte = "";
	 	if (isDataSelectCodigoTrasnp) {
	 		codigosTransporte = PageConsola.getCodigoTransporte(driver);
	 	}
	 	
        String paginaPadre = driver.getWindowHandle();
	 	try {
		 	PageConsola.switchToResultIFrame(driver);
		 	validations.add(
				"En el bloque de \"Petición/Resultado\" aparece una tabla \"transportes__content\"",
				IframeResult.existsTransportes(driver), State.Defect);
		 	validations.add(
				"En la tabla figuran los tipos " + codigosTransporte.replace("\n", ","),
				IframeResult.transportesContainsTipos(driver, codigosTransporte), State.Defect);
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
	private static ChecksResult checkAfterClickConsultDispEnvioTienda(String paginaPadre, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
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
		description="Seleccionar el botón \"Realizar Solicitud A Tienda\"",
		expected="El pedido se crea correctamente",
		saveErrorData=SaveWhen.Never)
    public static String realizarSolicitudTienda(String articulo, WebDriver driver) throws Exception {
        PageConsola.inputArticDispYCompra(driver, articulo);
        PageConsola.clickButtonSolATienda(driver);
        String codigoPedido = switchToIframeAndCheckAfterSolicitudAtienda(driver);
        return codigoPedido;
    }
	
	private static String switchToIframeAndCheckAfterSolicitudAtienda(WebDriver driver) {
        String paginaPadre = driver.getWindowHandle();
        PageConsola.switchToResultIFrame(driver);
        String codigoPedido = IframeResult.getCodigoPedido(driver);
        checkAfterSolicitudAtiendaInIframe(codigoPedido, driver);
        driver.switchTo().window(paginaPadre);
        return codigoPedido;
	}
	
	@Validation
	private static ChecksResult checkAfterSolicitudAtiendaInIframe(String codigoPedido, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"En el bloque de \"Petición/Resultado\" aparece una línea correspondiente al \"Código de pedido\"",
			IframeResult.isPresentCodigoPedido(driver), State.Warn);
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
	
	private static String switchToIframeAndCheckAfterObtenerPedidos(String codigoPedido, WebDriver driver) {
        String paginaPadre = driver.getWindowHandle();
        PageConsola.switchToResultIFrame(driver);
        String codigoPedidoFull = IframeResult.getPedidoFromListaPedidos(driver, codigoPedido);
        checkAfterObtenerPedidosInIframe(codigoPedido, codigoPedidoFull, driver);
        driver.switchTo().window(paginaPadre);
        return codigoPedidoFull;
	}
	
	@Validation
	private static ChecksResult checkAfterObtenerPedidosInIframe(String codigoPedido, String codigoPedidoFull, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"En el bloque de \"Petición/Resultado\" aparece una línea correspondiente al \"Pedidos\"",
			IframeResult.isPresentListaPedidos(driver), State.Warn);
	 	validations.add(
			"En la lista de pedidos aparece el generado anteriormente: " + codigoPedido,
			"".compareTo(codigoPedidoFull)!=0, State.Defect);
		return validations;
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
	private static ChecksResult checkAfterPreconfirmarPedido(String codigoPedidoFull, WebDriver driver) {
        ChecksResult validations = ChecksResult.getNew();
        String paginaPadre = driver.getWindowHandle();
        try {
	        PageConsola.switchToResultIFrame(driver);
	        boolean[] resultado = IframeResult.resSelPreconfPedidoOk(driver, codigoPedidoFull);
		 	validations.add(
				"En el bloque de \"Petición/Resultado\" aparece una línea \"Preconfirmado\"", 
				resultado[0], State.Warn);
		 	validations.add(
				"Aparece un XML con el dato \"&lt;pedido&gt;" + codigoPedidoFull + "&lt;/pedido&gt;\"",
				resultado[1], State.Defect);
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
	private static ChecksResult checkAfterConfirmarPedido(String codigoPedidoFull, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
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