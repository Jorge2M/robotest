package com.mng.robotest.domains.micuenta.steps;


import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.micuenta.pageobjects.ModalDetalleArticulo;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.generic.beans.ArticuloScreen;

public class ModalDetalleArticuloSteps extends StepBase {
	
	private final ModalDetalleArticulo modalDetalleArticulo;
	
	private ModalDetalleArticuloSteps(ModalDetalleArticulo modalObject) {
		this.modalDetalleArticulo = modalObject;
	}
	public static ModalDetalleArticuloSteps getNew(ModalDetalleArticulo modalObject) {
		return new ModalDetalleArticuloSteps(modalObject);
	}
	
	@Validation
	public ChecksTM validateIsOk(ArticuloScreen articulo) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 2;
		checks.add(
			"Aparece el modal correspondiente al artículo (lo esperamos hasta " + maxSeconds + " segundos)",
			modalDetalleArticulo.isVisible(maxSeconds), State.Warn);
		
		checks.add(
			"Se muestra la referencia " + articulo.getReferencia(),
			modalDetalleArticulo.existsReferencia(articulo.getReferencia(), 1), State.Warn);
		
		checks.add(
			"Se muestra el nombre " + articulo.getNombre(),
			modalDetalleArticulo.getNombre().compareTo(articulo.getNombre())==0, State.Warn);
		
		checks.add(
			"Se muestra el precio " + articulo.getPrecio(),
			modalDetalleArticulo.getPrecio().contains(articulo.getPrecio()), State.Warn);
		
		return checks;
	}
	
//	@Step (
//		description="Damos click al botón de \"Buscar talla en tienda\"",
//		expected="Aparece el modal de busqueda en tienda")
//	public void clickBuscarTiendaButton_Desktop() {
//		modalDetalleMisCompras.getDesktopVersion().clickBuscarTallaTiendaButton();
//		checkAppearsModalSearchTallaTienda(3);
//	}
//	
//	@Validation (
//		description="Aparece el modal de búsqueda de talla en tienda (lo esperamos hasta #{maxSeconds} segundos)",
//		level=State.Defect)
//	private boolean checkAppearsModalSearchTallaTienda(int maxSeconds) {
//		return (ModalBuscadorTiendasMisCompras.isVisible(maxSeconds, driver));
//	}
//	
//	@Step (
//		description="Cerramos el modal del buscador de tiendas mediante click en el aspa superior derecha",
//		expected="Desaparece el modal del buscador de tiendas")
//	public void clickCloseModalBuscadorTiendas_Desktop() throws Exception {
//		ModalBuscadorTiendasMisCompras.clickAspaForClose(driver);
//		checkIsInvisibleModalBuscadorTiendas();
//	}
//	
//	@Validation (
//		description="No está visible el modal del buscador de tiendas",
//		level=State.Warn)
//	private boolean checkIsInvisibleModalBuscadorTiendas() {
//		return (!ModalBuscadorTiendasMisCompras.isVisible(0, driver));
//	}
}
