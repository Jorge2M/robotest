package com.mng.robotest.test80.mango.test.pageobject.votfcons;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


/**
 * Se definen las funciones de interacción con el iframe de resultado que aparece en la página de la cónsola de VOTF (PageConsola.java)
 * @author jorge.munoz
 *
 */
public class IframeResult extends WebdrvWrapp {
    private static final String XPathBlockResultado = "//*[@class[contains(.,'response__content')]]";
    private static final String XPathBlockTransportes = "//div[@class[contains(.,'transportes__content')]]";
    private static final String XPathLintTipoStock = "//div[@class[contains(.,'masinfo')]]/span[text()[contains(.,'TipoStock: (')]]";
    
    //xpath correspondiente al bloque del resultado del pedido que aparece al seleccionar el botón "Realizar Solicitud A Tienda" o "A Domicilio"
    private static final String XPathBlockResultPedido = XPathBlockResultado + "/div[@class='pedido__content']";
    
    //xpath correspondiente al bloque que contiene el código de pedido y que aparece al seleccionar el botón "Realizar Solicitud A Tienda" o "A Domicilio"
    private static final String XPathBlockCodigoPedido = XPathBlockResultPedido + "/span";
    
    //xpath correspondiente al bloque que contiene la lista de pedidos resultante de la selección del botón "Obtener pedidos"
    private static final String XPathBlockListPedidos = XPathBlockResultado + "//div[@id[contains(.,'obtencionPedidos')]]";
    
    //xpath correspondiente al bloque de la lista de pedidos (resultante de selección del botón "Obtener Pedidos")
    private static final String XPathBlockListaPedidosFull = XPathBlockResultado + "//span[@class='pedido']";

	public static boolean resultadoContainsText(WebDriver driver, String text) {
		if (WebdrvWrapp.isElementPresent(driver, By.xpath(XPathBlockResultado))) {
			return (driver.findElement(By.xpath(XPathBlockResultado)).getText().contains(text));
		}
		return false;
	}

    public static boolean existsTransportes(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathBlockTransportes))); 
    }    
    
    public static boolean transportesContainsTipos(WebDriver driver, String codigosTransporte) {
        boolean contains = true;
        String[] listTrans = codigosTransporte.trim().split("\n");
        for (int i=0; i<listTrans.length; i++) {
            if (!isElementPresent(driver, By.xpath(XPathBlockTransportes + "//table//tr[" + (i+3) + "]/td[1][text()='" + listTrans[i] + "']"))) { 
               contains = false;
            }
        }
        
        return contains;
    }
    
    public static boolean isPresentTipoStock(WebDriver driver) { 
        return (isElementPresent(driver, By.xpath(XPathLintTipoStock)));
    }
    
    public static boolean isPresentCodigoPedido(int maxSeconds, WebDriver driver) {
        if (!isElementPresentUntil(driver, By.xpath(XPathBlockResultPedido), maxSeconds)) {
            return false;
        }
        return (driver.findElement(By.xpath(XPathBlockResultPedido)).getText().contains("Código pedido")); 
    }
    
    public static String getCodigoPedido(WebDriver driver) {
        String codigoPedido = "";
        if (isElementPresent(driver, By.xpath(XPathBlockCodigoPedido))) {
            codigoPedido = driver.findElement(By.xpath(XPathBlockCodigoPedido)).getText();
        }
        return codigoPedido;
    }
    
    
    
	/**
	 * @return un pedido en formato largo (+2 dígitos) de los que aparece en la lista de pedidos (resultante de selección del botón "Obtener Pedidos") a partir de un pedido en formato corto
	 */
	public static String getPedidoFromListaPedidosUntil(String codPedidoShort, int maxSeconds, WebDriver driver) throws Exception {
		String pedido = "";
		for (int i=0; i<maxSeconds; i++) {
			pedido = getPedidoFromListaPedidos(codPedidoShort, driver);
			if ("".compareTo(pedido)!=0) {
				return pedido;
			}
			waitMillis(1000);
		}
		return pedido;
	}
	
	private static String getPedidoFromListaPedidos(String codPedidoShort, WebDriver driver) throws Exception {
		String pedidoFull = "";
		waitForPageLoaded(driver);
		List<WebElement> listPedidos = driver.findElements(By.xpath(XPathBlockListaPedidosFull));

		//En cada elemento buscamos el pedido en formato corto
		Iterator<WebElement> it = listPedidos.iterator();
		while (it.hasNext()) {
			WebElement pedido = it.next();
			if (pedido.getText().contains(codPedidoShort)) {
				pedidoFull = pedido.getText();
			}
		}

		return pedidoFull;
	}

    public static boolean resCreacionPedidoOk(WebDriver driver) { 
        boolean resultado = false;
        if (isElementPresent(driver, By.xpath(XPathBlockResultado))) {
            resultado = driver.findElement(By.xpath(XPathBlockResultado)).getText().contains("Resultado creación pedido: (0) Total");
        }
        return resultado;
    }

    /**
     * @return si está presente el bloque correspondiente a la lista de pedidos (resultante de selección del botón "Obtener Pedidos")
     */
    public static boolean isPresentListaPedidosUntil(int maxSeconds, WebDriver driver) {
        if (!isElementPresentUntil(driver, By.xpath(XPathBlockListPedidos), maxSeconds)) {
            return false;
        }
        return (driver.findElement(By.xpath(XPathBlockListPedidos)).getText().contains("Pedidos:"));
    }
    
    /**
     * @return si la selección del botón "Seleccionar pedido" ha sido Ok
     */
    public static boolean resSelectPedidoOk(WebDriver driver, String codigoPedidoFull) {
        boolean resultado = false;
        if (isElementPresent(driver, By.xpath(XPathBlockResultPedido))) {
            resultado = driver.findElement(By.xpath(XPathBlockResultPedido)).getText().contains("Seleccionado: " + codigoPedidoFull);
        }
        return resultado;        
    }

	public static boolean isLineaPreconfirmado(WebDriver driver) {
		if (isElementPresentUntil(driver, By.xpath(XPathBlockResultado), 1)) {
			return driver.findElement(By.xpath(XPathBlockResultado)).getText().contains("Preconfirmado");
		}
		return false;
	}

	public static boolean isPedidoInXML(String codigoPedidoFull, WebDriver driver) {
		if (isElementPresent(driver, By.xpath(XPathBlockResultado))) {
			return driver.findElement(By.xpath(XPathBlockResultado + "//span")).getText().contains("<pedido>" + codigoPedidoFull + "</pedido>");
		}
		return false;
	}

    /**
     * @return si la selección del botón "Confirmar pedido" ha sido Ok
     */
    public static boolean resConfPedidoOk(WebDriver driver, String codigoPedidoFull) {
        boolean resultado = false;
        if (isElementPresent(driver, By.xpath(XPathBlockResultado))) {
            //En el bloque de "Petición/Resultado" aparece una línea "Confirmado: + codigoPedidoFull"
            resultado = driver.findElement(By.xpath(XPathBlockResultado)).getText().contains("Confirmado: " + codigoPedidoFull);
        }
        return resultado;        
    }
}
