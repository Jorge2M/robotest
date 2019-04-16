package com.mng.robotest.test80.mango.test.stpv.shop.modales;


import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalBuscadorTiendasMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalDetalleMisCompras;

public class ModalDetalleMisComprasStpV {
    
    /**
     * Cerrar el buscador de tiendas mediante selección de la aspa
     */
	@Step (
		description="Damos click al botón de \"Buscar talla en tienda\"",
        expected="Aparece el modal de busqueda en tienda")
    public static void clickBuscarTiendaButton(WebDriver driver) throws Exception {
		ModalDetalleMisCompras.clickBuscarTallaTiendaButton(driver);
		checkAppearsModalSearchTallaTienda(driver);
    }
	
	@Validation (
		description="Aparece el modal de búsqueda de talla en tienda",
		level=State.Defect)
	private static boolean checkAppearsModalSearchTallaTienda(WebDriver driver) {
		return (ModalBuscadorTiendasMisCompras.isVisible(driver));
	}
	
	@Step (
		description="Cerramos el modal del buscador de tiendas mediante click en el aspa superior derecha",
		expected="Desaparece el modal del buscador de tiendas")
	public static void clickCloseModalBuscadorTiendas(WebDriver driver) throws Exception {
		ModalBuscadorTiendasMisCompras.clickAspaForClose(driver);
		checkIsInvisibleModalBuscadorTiendas(driver);
	}
	
	@Validation (
		description="No está visible el modal del buscador de tiendas",
		level=State.Warn)
	private static boolean checkIsInvisibleModalBuscadorTiendas(WebDriver driver) {
		return (!ModalBuscadorTiendasMisCompras.isVisible(driver));
	}
}
