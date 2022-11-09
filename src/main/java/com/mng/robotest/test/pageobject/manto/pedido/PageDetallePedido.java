package com.mng.robotest.test.pageobject.manto.pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.datastored.DataPedido;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageDetallePedido extends PageBase {

	private static final String TAG_ID_PEDIDO = "@tagIdPedido";
	public static final String XPATH_IMNPORTE_TOTAL = "//span[text()[contains(.,'TOTAL:')]]/../following-sibling::*[1]";
	private static final String XPATH_CODIGO_POSTAL = "//table[1]/tbody/tr/td[2]//tr[11]";
	private static final String XPATH_LINK_ENVIO_TIENDA = "//td[text()[contains(.,'ENVIO A TIENDA')]]";
	private static final String XPATH_LABEL_ID_PEDIDO = "//td/label[text()[contains(.,'" + TAG_ID_PEDIDO + "')]]";
	private static final String XPATH_ESTADO_PEDIDO = "//span[text()[contains(.,'res_banco')]]/../following-sibling::*[1]";
	private static final String XPATH_TIPO_SERVICIO = "//span[text()[contains(.,'tipo servicio')]]/../following-sibling::*[1]";
	private static final String XPATH_LINK_VOLVER_PEDIDOS = "//a[text()[contains(.,'volver a pedidos')]]";
	private static final String XPATH_REFERENCIA_ARTICULO = "//a[@onclick[contains(.,'var div =')]]";
	private static final String XPATH_LINK_DETALLES_CLIENTE = "//input[@value='Detalles Cliente']";
	
	private static final String	XPATH_IR_A_GENERAR_BUTTON = "//input[@value='ir a Generar']";
	private static final String	XPATH_DETALLES_CLIENTE_BUTTON = "//input[@value='Detalles Cliente']";
	private static final String	XPATH_DEVOLUCIONES = "//input[@value='Devoluciones']";

	private String getXPathLabelIdPedido(String idPedido) {
		return (XPATH_LABEL_ID_PEDIDO.replace(TAG_ID_PEDIDO, idPedido));
	}

	public boolean isPage() {
		String xpath = "//td[text()[contains(.,'DETALLES PEDIDOS')]]";
		return state(Present, xpath).check();
	}

	public boolean isPage(String idPedido) {
		if (isPage()) {
			String xpathLabelIdPedido = getXPathLabelIdPedido(idPedido);
			return state(Visible, xpathLabelIdPedido).check();
		}
		return false;
	}

	public String getCodigoPais() {
		return getElement(XPATH_CODIGO_POSTAL).getText();
	}

	public String getTiendaIfExists() {
		if (state(Present, XPATH_LINK_ENVIO_TIENDA).check()) {
			String lineaTexto = getElement(XPATH_LINK_ENVIO_TIENDA).getText();
			Pattern pattern = Pattern.compile("(.*?)ENVIO A TIENDA(.*?)(\\d+)");
			Matcher matcher = pattern.matcher(lineaTexto);
			if (matcher.find()) {
				return matcher.group(3);
			}
		}
		return "";
	}

	public String getEstadoPedido() {
		return getElement(XPATH_ESTADO_PEDIDO).getText();
	}
	
	public String getTipoServicio() {
		return getElement(XPATH_TIPO_SERVICIO).getText();
	}
	
	public boolean isCodPaisPedido(String codPaisPedido) {
		String paisLit = getCodigoPais();
		return (paisLit.contains(codPaisPedido));
	}
	
	public String get1rstLineDatosEnvioText() {
		return getElement("//table[1]/tbody/tr/td[2]//tr[4]").getText();
	}
	
	public boolean isDireccionPedido(String direcPedido) {
		String dirLinea1 = getElement("//table[1]/tbody/tr/td[2]//tr[8]").getText();
		String dirLinea2 = getElement("//table[1]/tbody/tr/td[2]//tr[9]").getText();
		String dirLinea3 = getElement("//table[1]/tbody/tr/td[2]//tr[10]").getText();
		
		String codPostal = (dirLinea3.split("-").length>0) ? dirLinea3.split("-")[0].trim() : "";
		String poblacion = (dirLinea3.split("-").length>1) ? dirLinea3.split("-")[1].trim() : "";
		
		return (direcPedido.contains(dirLinea1) &&
				direcPedido.contains(dirLinea2) &&
				direcPedido.contains(codPostal) &&
				direcPedido.contains(poblacion));
	}
	
	public boolean isPedidoInStateMenos1NULL() {
		String estado = getElement(XPATH_ESTADO_PEDIDO).getText();
		return (estado.contains("-1 - NULL"));
	}
	
	public boolean isStateInTpvStates(DataPedido dataPedido) {
		boolean estadoEncontrado = false;
		StringTokenizer st = new StringTokenizer(dataPedido.getPago().getTpv().getEstado(), ";");
		String estadoPant = getEstadoPedido();
		while(st.hasMoreTokens()) {
			String estado = st.nextToken();
			if (estadoPant.contains(estado + " -")) {
				estadoEncontrado = true;
			}
		}
		return estadoEncontrado;
	}

	public void gotoListaPedidos() {
		if (state(Present, XPATH_LINK_VOLVER_PEDIDOS).check()) {
			click(XPATH_LINK_VOLVER_PEDIDOS).exec();
		}
	}

	public List<String> getReferenciasArticulosDetallePedido() {
		List <String> referenciasText = new ArrayList<>();
		List<WebElement> referencias = getElements(XPATH_REFERENCIA_ARTICULO);
		
		for (WebElement referencia : referencias){
			referenciasText.add(referencia.getText().replace(" ", ""));
		}
		return referenciasText;
	}

	public void clickLinkDetallesCliente() {
		click(XPATH_LINK_DETALLES_CLIENTE).exec();
	}
	
	public void clickIrAGenerarButton() {
		click(XPATH_IR_A_GENERAR_BUTTON).exec();
	}
}
