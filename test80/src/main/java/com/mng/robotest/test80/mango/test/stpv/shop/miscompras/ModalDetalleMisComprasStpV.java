package com.mng.robotest.test80.mango.test.stpv.shop.miscompras;


import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.ModalDetalleMisComprasDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalBuscadorTiendasMisCompras;

public class ModalDetalleMisComprasStpV {
    
	private final WebDriver driver;
	private final ModalDetalleMisComprasDesktop modalDetalleMisCompras;
	
	private ModalDetalleMisComprasStpV(ModalDetalleMisComprasDesktop modalObject, WebDriver driver) {
		this.driver = driver;
		this.modalDetalleMisCompras = modalObject;
	}
	public static ModalDetalleMisComprasStpV getNew(ModalDetalleMisComprasDesktop modalObject, WebDriver driver) {
		return new ModalDetalleMisComprasStpV(modalObject, driver);
	}
	
    @Validation
    public ChecksTM checkModalArticle(String infoArticle) {
    	ChecksTM validations = ChecksTM.getNew();
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
    public void clickBuscarTiendaButton() {
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
	public void gotoListaMisCompras() {
		modalDetalleMisCompras.gotoListaMisCompras();
	}
}
