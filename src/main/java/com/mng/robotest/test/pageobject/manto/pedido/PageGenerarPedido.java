package com.mng.robotest.test.pageobject.manto.pedido;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;


public class PageGenerarPedido extends PageBase {

	private static final String XPATH_ESTADO_PEDIDO_SELECT = "//span[text()[contains(.,'Estado Pedido')]]/../..//select";
	private static final String XPATH_INPUT_PURCHORDER_NUM = "//span[text()[contains(.,'PurchorderNum')]]/../..//input";
	private static final String XPATH_GENERAR_FICHERO_BUTTON = "//input[@value='Generar Fichero Pedido']";
	private static final String XPATH_MESSAGE_OK_FICHERO_CREADO = "//span[text()[contains(.,'Fichero creado correctamente')]]";
	private static final String XPATH_RADIO_INFORMA_BANCO = "//input[@id[contains(.,'devolucionBanco')]]";
	
	public static enum EstadoPedido {
		OK(0),	
		FALTA_PICKING(1),	
		DENEGADO(2),	
		PENDIENTERESPUESTA(3),	
		PENDIENTE(4),	
		PENDIENTEPAGO(5),	
		RECHAZADO(6),	
		SUSTITUCION(7),	
		TEMPORAL_DEVOLUCION(8),	
		ANULADO(9),	
		DB(10),	
		NULL(-1);
		
		public int value;
		EstadoPedido(int value) {
			this.value = value;
		}
	}
	
	public boolean isPage(String idPedido) {
		WebElement inputIdPedido = getElementWeb(By.xpath(XPATH_INPUT_PURCHORDER_NUM), driver);
		if (inputIdPedido!=null) {
			String valueInput = inputIdPedido.getAttribute("value");
			if (valueInput!=null) {
				return (valueInput.contains(idPedido));
			}
		}
		return false;
	}
	
	public void selectEstado(EstadoPedido estado) {
		String value = String.valueOf(estado.value);
		select(By.xpath(XPATH_ESTADO_PEDIDO_SELECT), value)
			.type(Value).wait(30).exec();
	}
	
	public EstadoPedido getEstadoPedido() {
		String estado = driver
				.findElement(By.xpath(XPATH_ESTADO_PEDIDO_SELECT + "/option[@selected]"))
				.getAttribute("value");
		
		return EstadoPedido.valueOf(estado);
	}
	
	public void clickGenerarFicheroPedido() {
		click(By.xpath(XPATH_GENERAR_FICHERO_BUTTON)).exec();
	}
	
	public boolean isVisibleMessageFileCreated() {
		return state(Visible, By.xpath(XPATH_MESSAGE_OK_FICHERO_CREADO)).check();
	}
	
	public void clickInformarBancoEnCasoCancelacionAlGenerarPedido() {
		click(By.xpath(XPATH_RADIO_INFORMA_BANCO)).exec();
	}
}
