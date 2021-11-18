package com.mng.robotest.test80.mango.test.pageobject.manto.pedido;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect.*;

public class PageGenerarPedido {

	public static enum GestionPostCompra implements ElementPage {
		EstadoPedidoSelect("//span[text()[contains(.,'Estado Pedido')]]/../..//select"),
		InputPurchorderNum("//span[text()[contains(.,'PurchorderNum')]]/../..//input"),
		GenerarFicheroButton("//input[@value='Generar Fichero Pedido']"),
		MessageOkFicheroCreado("//span[text()[contains(.,'Fichero creado correctamente')]]");

		private By by;
		GestionPostCompra(String xPath) {
			by = By.xpath(xPath);
		}

		@Override
		public By getBy() {
			return by;
		}
	}
	
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
	
	public static boolean isPage(String idPedido, WebDriver driver) {
		WebElement inputIdPedido = getElementWeb(GestionPostCompra.InputPurchorderNum.getBy(), driver);
		if (inputIdPedido!=null) {
			String valueInput = inputIdPedido.getAttribute("value");
			if (valueInput!=null) {
				return (valueInput.contains(idPedido));
			}
		}
		
		return false;
	}
	
	public static void selectEstado(EstadoPedido estado, WebDriver driver) {
		String value = String.valueOf(estado.value);
		select(GestionPostCompra.EstadoPedidoSelect.getBy(), value, driver)
			.type(Value).wait(30).exec();
	}
}
