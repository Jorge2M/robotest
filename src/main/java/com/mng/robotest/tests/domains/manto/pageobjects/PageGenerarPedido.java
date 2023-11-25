package com.mng.robotest.tests.domains.manto.pageobjects;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

public class PageGenerarPedido extends PageBase {

	private static final String XP_ESTADO_PEDIDO_SELECT = "//span[text()[contains(.,'Estado Pedido')]]/../..//select";
	private static final String XP_INPUT_PURCHORDER_NUM = "//span[text()[contains(.,'PurchorderNum')]]/../..//input";
	private static final String XP_GENERAR_FICHERO_BUTTON = "//input[@value='Generar Fichero Pedido']";
	private static final String XP_MESSAGE_OK_FICHERO_CREADO = "//span[text()[contains(.,'Fichero creado correctamente')]]";
	private static final String XP_RADIO_INFORMA_BANCO = "//input[@id[contains(.,'devolucionBanco')]]";
	
	public enum EstadoPedido {
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
		
		private int value;
		EstadoPedido(int value) {
			this.value = value;
		}
		public static EstadoPedido getEstado(int value) {
			for (EstadoPedido item : EstadoPedido.values()) {
				if (item.value==value) {
					return item;
				}
			}
			return NULL;
		}
	}
	
	public boolean isPage(String idPedido) {
		WebElement inputIdPedido = getElementWeb(XP_INPUT_PURCHORDER_NUM);
		if (inputIdPedido!=null) {
			String valueInput = inputIdPedido.getAttribute("value");
			if (valueInput!=null) {
				return (valueInput.contains(idPedido));
			}
		}
		return false;
	}
	
	public void selectEstado(EstadoPedido estado) {
		try {
			selectEstadoStaleUnsafe(estado);
		} 
		catch (StaleElementReferenceException e) {
			PageObjTM.waitMillis(1000);
			selectEstadoStaleUnsafe(estado);
		}
	}
	
	private void selectEstadoStaleUnsafe(EstadoPedido estado) {
		String value = String.valueOf(estado.value);
		select(XP_ESTADO_PEDIDO_SELECT, value)
			.type(Value).wait(30).exec();
	}
	
	public EstadoPedido getEstadoPedido() {
		String estado = getElement(XP_ESTADO_PEDIDO_SELECT + "/option[@selected]")
				.getAttribute("value");
		
		return EstadoPedido.getEstado(Integer.valueOf(estado));
	}
	
	public void clickGenerarFicheroPedido() {
		click(XP_GENERAR_FICHERO_BUTTON).exec();
	}
	
	public boolean isVisibleMessageFileCreated() {
		return state(Visible, XP_MESSAGE_OK_FICHERO_CREADO).check();
	}
	
	public void clickInformarBancoEnCasoCancelacionAlGenerarPedido() {
		click(XP_RADIO_INFORMA_BANCO).exec();
	}
}
