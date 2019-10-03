package com.mng.robotest.test80.mango.test.stpv.shop.modales;


import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalBuscadorTiendasMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalDetalleMisCompras;

public class ModalDetalleMisComprasStpV {
    
	private final WebDriver driver;
	private final ModalDetalleMisCompras modalDetalleMisCompras;
	
	private ModalDetalleMisComprasStpV(ModalDetalleMisCompras modalObject, WebDriver driver) {
		this.driver = driver;
		this.modalDetalleMisCompras = modalObject;
	}
	public static ModalDetalleMisComprasStpV getNew(ModalDetalleMisCompras modalObject, WebDriver driver) {
		return new ModalDetalleMisComprasStpV(modalObject, driver);
	}
	
    @Validation
    public ChecksResult checkModalArticle(String infoArticle) {
    	ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"Aparece el modal con información del artículo",
    		modalDetalleMisCompras.isVisible(), State.Defect);
    	validations.add(
    		"La información del artículo que aparece en el modal es correcta " + infoArticle,
    		modalDetalleMisCompras.isReferenciaValidaModal(infoArticle), State.Defect);
    	return validations;
    }
	
	@Step (
		description="Damos click al botón de \"Buscar talla en tienda\"",
        expected="Aparece el modal de busqueda en tienda")
    public void clickBuscarTiendaButton() throws Exception {
		modalDetalleMisCompras.clickBuscarTallaTiendaButton();
		checkAppearsModalSearchTallaTienda();
    }
	
	@Validation (
		description="Aparece el modal de búsqueda de talla en tienda",
		level=State.Defect)
	private boolean checkAppearsModalSearchTallaTienda() {
		return (ModalBuscadorTiendasMisCompras.isVisible(driver));
	}
	
	@Step (
		description="Cerramos el modal del buscador de tiendas mediante click en el aspa superior derecha",
		expected="Desaparece el modal del buscador de tiendas")
	public void clickCloseModalBuscadorTiendas() throws Exception {
		ModalBuscadorTiendasMisCompras.clickAspaForClose(driver);
		checkIsInvisibleModalBuscadorTiendas();
	}
	
	@Validation (
		description="No está visible el modal del buscador de tiendas",
		level=State.Warn)
	private boolean checkIsInvisibleModalBuscadorTiendas() {
		return (!ModalBuscadorTiendasMisCompras.isVisible(driver));
	}
	
	@Step (
		description="Clickamos el link para volver a la lista de \"Mis compras\"",
		expected="Volvemos a la página de Mis Compras")
	public void gotoListaMisCompras() throws Exception {
		modalDetalleMisCompras.gotoListaMisCompras();
	}
}
