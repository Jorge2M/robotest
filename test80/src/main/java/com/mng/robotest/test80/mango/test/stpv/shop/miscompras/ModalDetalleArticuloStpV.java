package com.mng.robotest.test80.mango.test.stpv.shop.miscompras;


import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.ModalDetalleArticulo;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalBuscadorTiendasMisCompras;

public class ModalDetalleArticuloStpV {
    
	private final WebDriver driver;
	private final ModalDetalleArticulo modalDetalleMisCompras;
	
	private ModalDetalleArticuloStpV(ModalDetalleArticulo modalObject, WebDriver driver) {
		this.driver = driver;
		this.modalDetalleMisCompras = modalObject;
	}
	public static ModalDetalleArticuloStpV getNew(ModalDetalleArticulo modalObject, WebDriver driver) {
		return new ModalDetalleArticuloStpV(modalObject, driver);
	}
    
    @Validation
    public ChecksTM validateIsOk(ArticuloScreen articulo) {
        ChecksTM validations = ChecksTM.getNew();
        validations.add(
        	"Aparece el modal correspondiente al artículo",
        	modalDetalleMisCompras.isVisible(), State.Warn);
        validations.add(
        	"Se muestra la referencia " + articulo.getReferencia(),
        	modalDetalleMisCompras.getReferencia().compareTo(articulo.getReferencia())==0, State.Warn);
        validations.add(
        	"Se muestra el nombre " + articulo.getNombre(),
        	modalDetalleMisCompras.getNombre().compareTo(articulo.getNombre())==0, State.Warn);
        validations.add(
        	"Se muestra el precio " + articulo.getPrecio(),
        	modalDetalleMisCompras.getPrecio().contains(articulo.getPrecio()), State.Warn);
        return validations;
    }
	
	@Step (
		description="Damos click al botón de \"Buscar talla en tienda\"",
        expected="Aparece el modal de busqueda en tienda")
    public void clickBuscarTiendaButton_Desktop() {
		modalDetalleMisCompras.getDesktopVersion().clickBuscarTallaTiendaButton();
		checkAppearsModalSearchTallaTienda(3);
    }
	
	@Validation (
		description="Aparece el modal de búsqueda de talla en tienda (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean checkAppearsModalSearchTallaTienda(int maxSeconds) {
		return (ModalBuscadorTiendasMisCompras.isVisible(maxSeconds, driver));
	}
	
	@Step (
		description="Cerramos el modal del buscador de tiendas mediante click en el aspa superior derecha",
		expected="Desaparece el modal del buscador de tiendas")
	public void clickCloseModalBuscadorTiendas_Desktop() throws Exception {
		ModalBuscadorTiendasMisCompras.clickAspaForClose(driver);
		checkIsInvisibleModalBuscadorTiendas();
	}
	
	@Validation (
		description="No está visible el modal del buscador de tiendas",
		level=State.Warn)
	private boolean checkIsInvisibleModalBuscadorTiendas() {
		return (!ModalBuscadorTiendasMisCompras.isVisible(0, driver));
	}
}
