package com.mng.robotest.tests.domains.manto.pageobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.datastored.DataPedido;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDetallePedido extends PageBase {

	private static final String TAG_ID_PEDIDO = "@tagIdPedido";
	public static final String XP_IMNPORTE_TOTAL = "//span[text()[contains(.,'TOTAL:')]]/../following-sibling::*[1]";
	private static final String XP_CODIGO_POSTAL = "//table[1]/tbody/tr/td[2]//tr[11]";
	private static final String XP_LINK_ENVIO_TIENDA = "//td[text()[contains(.,'ENVIO A TIENDA')]]";
	private static final String XP_LABEL_ID_PEDIDO = "//td/label[text()[contains(.,'" + TAG_ID_PEDIDO + "')]]";
	private static final String XP_ESTADO_PEDIDO = "//span[text()[contains(.,'res_banco')]]/../following-sibling::*[1]";
	private static final String XP_TIPO_SERVICIO = "//span[text()[contains(.,'tipo servicio')]]/../following-sibling::*[1]";
	private static final String XP_LINK_VOLVER_PEDIDOS = "//a[text()[contains(.,'volver a pedidos')]]";
	private static final String XP_REFERENCIA_ARTICULO = "//a[@onclick[contains(.,'var div =')]]";
	private static final String XP_LINK_DETALLES_CLIENTE = "//input[@value='Detalles Cliente']";
	private static final String	XP_IR_A_GENERAR_BUTTON = "//input[@value='ir a Generar']";

	private String getXPathLabelIdPedido(String idPedido) {
		return (XP_LABEL_ID_PEDIDO.replace(TAG_ID_PEDIDO, idPedido));
	}

	public boolean isPage() {
		String xpath = "//td[text()[contains(.,'DETALLES PEDIDOS')]]";
		return state(PRESENT, xpath).check();
	}

	public boolean isPage(String idPedido) {
		if (isPage()) {
			String xpathLabelIdPedido = getXPathLabelIdPedido(idPedido);
			return state(VISIBLE, xpathLabelIdPedido).check();
		}
		return false;
	}

	public String getCodigoPais() {
		return getElement(XP_CODIGO_POSTAL).getText();
	}

	public String getTiendaIfExists() {
		if (state(PRESENT, XP_LINK_ENVIO_TIENDA).check()) {
			String lineaTexto = getElement(XP_LINK_ENVIO_TIENDA).getText();
			Pattern pattern = Pattern.compile("(.*?)ENVIO A TIENDA(.*?)(\\d+)");
			Matcher matcher = pattern.matcher(lineaTexto);
			if (matcher.find()) {
				return matcher.group(3);
			}
		}
		return "";
	}

	public String getEstadoPedido() {
		return getElement(XP_ESTADO_PEDIDO).getText();
	}
	
	public String getTipoServicio() {
		return getElement(XP_TIPO_SERVICIO).getText();
	}
	
	public boolean isCodPaisPedido(String codPaisPedido) {
		return getCodigoPais().contains(codPaisPedido);
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
		String estado = getElement(XP_ESTADO_PEDIDO).getText();
		return (estado.contains("-1 - NULL"));
	}
	
	public boolean isCorrectState(DataPedido dataPedido) {
		String estadoPant = getEstadoPedido();
		for (Integer estado : dataPedido.getPago().getEstados()) {
			if (estadoPant.contains(estado + " -")) {
				return true;
			}
		}
		return false;
	}

	public void gotoListaPedidos() {
		if (state(PRESENT, XP_LINK_VOLVER_PEDIDOS).check()) {
			click(XP_LINK_VOLVER_PEDIDOS).exec();
		}
	}

	public List<String> getReferenciasArticulosDetallePedido() {
		List <String> referenciasText = new ArrayList<>();
		List<WebElement> referencias = getElements(XP_REFERENCIA_ARTICULO);
		for (WebElement referencia : referencias){
			referenciasText.add(referencia.getText().replace(" ", ""));
		}
		return referenciasText;
	}

	public void clickLinkDetallesCliente() {
		click(XP_LINK_DETALLES_CLIENTE).exec();
	}
	
	public void clickIrAGenerarButton() {
		click(XP_IR_A_GENERAR_BUTTON).exec();
	}
}
