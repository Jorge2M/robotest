package com.mng.robotest.tests.domains.votfconsole.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class IframeResult extends PageBase {
	
	private static final String XPATH_BLOCK_RESULTADO = "//*[@class[contains(.,'response__content')]]";
	private static final String XPATH_BLOCK_TRANSPORTES = "//div[@class[contains(.,'transportes__content')]]";
	private static final String XPATH_BLOCK_DISPONIBILIDAD = "//div[@class[contains(.,'articulos__content')]]";
	private static final String XPATH_CAMPO_DISPONIBLE = XPATH_BLOCK_DISPONIBILIDAD + "//tr[@class]/td[2]";
	private static final String XPATH_LINT_TIPO_STOCK = "//div[@class[contains(.,'masinfo')]]/span[text()[contains(.,'TipoStock: (')]]";
	private static final String XPATH_BLOCK_RESULT_PEDIDO = XPATH_BLOCK_RESULTADO + "/div[@class='pedido__content']";
	private static final String XPATH_BLOCK_CODIGO_PEDIDO = XPATH_BLOCK_RESULT_PEDIDO + "/span";
	private static final String XPATH_BLOCK_LIST_PEDIDOS = XPATH_BLOCK_RESULTADO + "//div[@id[contains(.,'obtencionPedidos')]]";
	private static final String XPATH_BLOCK_LISTA_PEDIDOS_FULL= XPATH_BLOCK_RESULTADO + "//span[@class='pedido']";
	
	public boolean resultadoContainsText(String text) {
		if (state(Present, XPATH_BLOCK_RESULTADO).check()) {
			return getElement(XPATH_BLOCK_RESULTADO).getText().contains(text);
		}
		return false;
	}

	public boolean existsTransportes() {
		return state(Present, XPATH_BLOCK_TRANSPORTES).check();
	}	
	
	public boolean existsDisponibilidad() {
		return state(Present, XPATH_BLOCK_DISPONIBILIDAD).check();
	}	
	
	public boolean flagDisponibleIsTrue() {
		if (state(Present, XPATH_CAMPO_DISPONIBLE).check()) {
			return ("true".compareTo(getElement(XPATH_CAMPO_DISPONIBLE).getText())==0);
		}
		return false;
	}
	
	public boolean transportesContainsTipos(String codigosTransporte) {
		boolean contains = true;
		String[] listTrans = codigosTransporte.trim().split("\n");
		for (int i=0; i<listTrans.length; i++) {
			String xpath = XPATH_BLOCK_TRANSPORTES + "//table//tr[" + (i+3) + "]/td[1][text()='" + listTrans[i] + "']";
			if (!state(Present, xpath).check()) { 
			   contains = false;
			}
		}
		return contains;
	}
	
	public boolean isPresentTipoStock() { 
		return state(Present, XPATH_LINT_TIPO_STOCK).check();
	}
	
	public boolean isPresentCodigoPedido(int seconds) {
		if (!state(Present, XPATH_BLOCK_RESULT_PEDIDO).wait(seconds).check()) {
			return false;
		}
		return getElement(XPATH_BLOCK_RESULT_PEDIDO).getText().contains("Código pedido"); 
	}
	
	public String getCodigoPedido() {
		String codigoPedido = "";
		if (state(Present, XPATH_BLOCK_CODIGO_PEDIDO).check()) {
			codigoPedido = getElement(XPATH_BLOCK_CODIGO_PEDIDO).getText();
		}
		return codigoPedido;
	}
	
	public String getPedidoFromListaPedidosUntil(String codPedidoShort, int seconds) {
		String pedido = "";
		for (int i=0; i<seconds; i++) {
			pedido = getPedidoFromListaPedidos(codPedidoShort);
			if ("".compareTo(pedido)!=0) {
				return pedido;
			}
			waitMillis(1000);
		}
		return pedido;
	}
	
	private String getPedidoFromListaPedidos(String codPedidoShort) {
		String pedidoFull = "";
		waitLoadPage();
		var listPedidos = getElements(XPATH_BLOCK_LISTA_PEDIDOS_FULL);

		//En cada elemento buscamos el pedido en formato corto
		var it = listPedidos.iterator();
		while (it.hasNext()) {
			var pedido = it.next();
			if (pedido.getText().contains(codPedidoShort)) {
				pedidoFull = pedido.getText();
			}
		}
		return pedidoFull;
	}

	public boolean resCreacionPedidoOk() { 
		boolean resultado = false;
		if (state(Present, XPATH_BLOCK_RESULTADO).check()) {
			resultado = getElement(XPATH_BLOCK_RESULTADO).getText().contains("Resultado creación pedido: (0) Total");
		}
		return resultado;
	}

	public boolean isPresentListaPedidosUntil(int seconds) {
		if (!state(Present, XPATH_BLOCK_LIST_PEDIDOS).wait(seconds).check()) {
			return false;
		}
		return getElement(XPATH_BLOCK_LIST_PEDIDOS).getText().contains("Pedidos:");
	}
	
	public boolean resSelectPedidoOk(String codigoPedidoFull) {
		boolean resultado = false;
		if (state(Present, XPATH_BLOCK_RESULT_PEDIDO).check()) {
			resultado = getElement(XPATH_BLOCK_RESULT_PEDIDO).getText().contains("Seleccionado: " + codigoPedidoFull);
		}
		return resultado;
	}

	public boolean isLineaPreconfirmado() {
		if (state(Present, XPATH_BLOCK_RESULTADO).wait(1).check()) {
			return getElement(XPATH_BLOCK_RESULTADO).getText().contains("Preconfirmado");
		}
		return false;
	}

	public boolean isPedidoInXML(String codigoPedidoFull) {
		if (state(Present, XPATH_BLOCK_RESULTADO).check()) {
			return getElement(XPATH_BLOCK_RESULTADO + "//span").getText()
					.contains("<pedido>" + codigoPedidoFull + "</pedido>");
		}
		return false;
	}

	public boolean resConfPedidoOk(String codigoPedidoFull) {
		boolean resultado = false;
		if (state(Present, XPATH_BLOCK_RESULTADO).check()) {
			//En el bloque de "Petición/Resultado" aparece una línea "Confirmado: + codigoPedidoFull"
			resultado = getElement(XPATH_BLOCK_RESULTADO).getText()
					.contains("Confirmado: " + codigoPedidoFull);
		}
		return resultado;
	}
}
