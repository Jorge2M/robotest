package com.mng.robotest.test80.mango.test.pageobject.manto.pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.testmaker.service.webdriver.wrapper.ElementPage;


public class PageDetallePedido {

	private final static String tagIdPedido = "@tagIdPedido";
    public final static String XPathImporteTotal = "//span[text()[contains(.,'TOTAL:')]]/../following-sibling::*[1]";
    private final static String XPathCodigoPais = "//table[1]/tbody/tr/td[2]//tr[11]";
    private final static String XPathLinkEnvioTienda = "//td[text()[contains(.,'ENVIO A TIENDA')]]";
    private final static String XPathLabelIdPedido = "//td/label[text()[contains(.,'" + tagIdPedido + "')]]";
    private final static String XPathEstadoPedido = "//span[text()[contains(.,'res_banco')]]/../following-sibling::*[1]";
    private final static String XPathTipoServicio = "//span[text()[contains(.,'tipo servicio')]]/../following-sibling::*[1]";
    private final static String XPathLinkVolverPedidos = "//a[text()[contains(.,'volver a pedidos')]]";
    private final static String XPathRefereciaArticulo = "//a[@onclick[contains(.,'var div =')]]";
    private final static String XPathLinkDetallesCliente = "//input[@value='Detalles Cliente']";
    
    public static enum RightButtons implements ElementPage {
        IrAGenerar("//input[@value='ir a Generar']"),
        DetallesCliente("//input[@value='Detalles Cliente']"),
        Devoluciones("//input[@value='Devoluciones']");

        private String xPath;
        
        RightButtons(String xPath) {
            this.xPath = xPath;
        }

        @Override
        public String getXPath() {
            return this.xPath;
        }
    }
    
    private static String getXPathLabelIdPedido(String idPedido) {
    	return (XPathLabelIdPedido.replace(tagIdPedido, idPedido));
    }

	public static boolean isPage(WebDriver driver) {
		String xpath = "//td[text()[contains(.,'DETALLES PEDIDOS')]]";
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean isPage(String idPedido, WebDriver driver) {
		if (isPage(driver)) {
			String xpathLabelIdPedido = getXPathLabelIdPedido(idPedido);
			return (state(Visible, By.xpath(xpathLabelIdPedido), driver).check());
		}
		
		return false;
	}

    public static String getCodigoPais(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathCodigoPais)).getText());
    }

	public static String getTiendaIfExists(WebDriver driver) {
		if (state(Present, By.xpath(XPathLinkEnvioTienda), driver).check()) {
			String lineaTexto = driver.findElement(By.xpath(XPathLinkEnvioTienda)).getText();
			Pattern pattern = Pattern.compile("(.*?)ENVIO A TIENDA(.*?)(\\d+)");//(^[0-9])
			Matcher matcher = pattern.matcher(lineaTexto);
			if (matcher.find()) {
				return matcher.group(3);
			}
		}
		return "";
	}

    public static String getEstadoPedido(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathEstadoPedido)).getText());
    }
    
    public static String getTipoServicio(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathTipoServicio)).getText());
    }
    
    /**
     * @param codPaisPedido
     * @return si el código de país que figura en pantalla se corresponde con el código de país del pedido 
     */
    public static boolean isCodPaisPedido(WebDriver driver, String codPaisPedido) {
        String paisLit = getCodigoPais(driver);
        return (paisLit.contains(codPaisPedido));
    }
    
    public static String get1rstLineDatosEnvioText(WebDriver driver) {
        return (driver.findElement(By.xpath("//table[1]/tbody/tr/td[2]//tr[4]")).getText());
    }
    
    /**
     * @param direcPedido
     * @return si los datos de la dirección en pantalla están incluídos en la dirección válida del pedido
     */
    public static boolean isDireccionPedido(WebDriver driver, String direcPedido) {
        
        //Obtenemos las 3 líneas que contienen la dirección del pedido
        String dirLinea1 = driver.findElement(By.xpath("//table[1]/tbody/tr/td[2]//tr[8]")).getText();
        String dirLinea2 = driver.findElement(By.xpath("//table[1]/tbody/tr/td[2]//tr[9]")).getText();
        String dirLinea3 = driver.findElement(By.xpath("//table[1]/tbody/tr/td[2]//tr[10]")).getText();
        
        //Extraemos el código postal y la población de la línea-3
        String codPostal = (dirLinea3.split("-").length>0) ? dirLinea3.split("-")[0].trim() : "";
        String poblacion = (dirLinea3.split("-").length>1) ? dirLinea3.split("-")[1].trim() : "";
        
        //Validamos que los datos extraídos de la página están contenidos en la dirección del pedido
        if (direcPedido.contains(dirLinea1) &&
            direcPedido.contains(dirLinea2) &&
            direcPedido.contains(codPostal) &&
            direcPedido.contains(poblacion)) {
            return true;
        }
        
        return false;
    }
    
    /**
     * @param driver
     * @return si el pedido está en estado -1 -NULL
     */
    public static boolean isPedidoInStateMenos1NULL(WebDriver driver) {
        String estado = driver.findElement(By.xpath(XPathEstadoPedido)).getText();
        return (estado.contains("-1 - NULL"));
    }
    
    /**
     * @param pedido
     * @return si el estado de pantalla se encuentra entre los posibles estados según el tpv
     */
    public static boolean isStateInTpvStates(WebDriver driver, DataPedido dataPedido) {
        boolean estadoEncontrado = false;
        StringTokenizer st = new StringTokenizer(dataPedido.getPago().getTpv().getEstado(), ";");
        String estadoPant = getEstadoPedido(driver);
        while(st.hasMoreTokens()) {
            String estado = st.nextToken();
            if (estadoPant.contains(estado + " -")) {
                estadoEncontrado = true;
            }
        }
        
        return estadoEncontrado;
    }

	public static void gotoListaPedidos(WebDriver driver) throws Exception {
		if (state(Present, By.xpath(XPathLinkVolverPedidos), driver).check()) {
			clickAndWaitLoad(driver, By.xpath(XPathLinkVolverPedidos));
		}
	}

	/**
	 * @param driver
	 * @return referencias del pedido
	 */
	public static List<String> getReferenciasArticulosDetallePedido(WebDriver driver) {
		List <String> referenciasText = new ArrayList<>();
		List<WebElement> referencias = driver.findElements(By.xpath(XPathRefereciaArticulo));
		
		for (WebElement referencia : referencias){
			referenciasText.add(referencia.getText().replace(" ", ""));
		}
		return referenciasText;
	}

	/**
	 * @param driver
	 * @throws Exception
	 */
	public static void clickLinkDetallesCliente(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathLinkDetallesCliente));
	}

	
}
