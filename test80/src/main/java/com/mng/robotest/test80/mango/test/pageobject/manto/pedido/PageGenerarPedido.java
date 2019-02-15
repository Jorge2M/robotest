package com.mng.robotest.test80.mango.test.pageobject.manto.pedido;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.pageobject.ElementPage;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class PageGenerarPedido extends WebdrvWrapp {

	public static enum GestionPostCompra implements ElementPage {
        EstadoPedidoSelect("//span[text()[contains(.,'Estado Pedido')]]/../..//select"),
        InputPurchorderNum("//span[text()[contains(.,'PurchorderNum')]]/../..//input"),
        GenerarFicheroButton("//input[@value='Generar Fichero Pedido']"),
        MessageOkFicheroCreado("//span[text()[contains(.,'Fichero creado correctamente')]]");

        private String xPath;
        GestionPostCompra(String xPath) {
            this.xPath = xPath;
        }

        @Override
        public String getXPath() {
            return this.xPath;
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
		WebElement inputIdPedido = getElementWeb(GestionPostCompra.InputPurchorderNum, driver);
		if (inputIdPedido!=null) {
			String valueInput = inputIdPedido.getAttribute("value");
			if (valueInput!=null) {
				return (valueInput.contains(idPedido));
			}
		}
		
		return false;
	}
	
	public static void selectEstado(EstadoPedido estado, WebDriver driver) throws Exception {
		selectByValue(
			GestionPostCompra.EstadoPedidoSelect, 
			String.valueOf(estado.value), 
			OptionSelect.ByValue, driver);
		WebdrvWrapp.waitForPageLoaded(driver);
	}
}
