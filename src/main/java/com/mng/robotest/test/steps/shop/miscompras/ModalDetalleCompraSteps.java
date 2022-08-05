package com.mng.robotest.test.steps.shop.miscompras;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.micuenta.Ticket;
import com.mng.robotest.test.pageobject.shop.miscompras.ModalDetalleArticulo;
import com.mng.robotest.test.pageobject.shop.miscompras.PageDetalleCompra;

public class ModalDetalleCompraSteps {
	
	private final PageDetalleCompra modalDetalleCompra;
	private final ModalDetalleArticulo modalDetalleArticulo;
	private final ModalDetalleArticuloSteps modalDetalleArticuloSteps;
	
	private ModalDetalleCompraSteps(PageDetalleCompra section) {
		this.modalDetalleCompra = section;
		this.modalDetalleArticulo = modalDetalleCompra.getModalDetalleArticulo();
		this.modalDetalleArticuloSteps = ModalDetalleArticuloSteps.getNew(modalDetalleArticulo);
		
	}
	public static ModalDetalleCompraSteps getNew(PageDetalleCompra section) {
		return new ModalDetalleCompraSteps(section);
	}
	public ModalDetalleArticuloSteps getModalDetalleArticulo() {
		return modalDetalleArticuloSteps;
	}
	
	public void validateIsOk(Ticket compraTienda) {
		ChecksTM checks = checkIsDataVisible();
		if (!checks.calculateStateValidation().isMoreCriticThan(State.Warn)) {
			checkDataContent(compraTienda);
		}
	}
	
	@SuppressWarnings("static-access")
	@Validation
	private ChecksTM checkIsDataVisible() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 1;
		checks.add(
			"Es visible la capa correspondiente al detalle del tícket de compra (la esperamos hasta " + maxSeconds + " segundos)",
			modalDetalleCompra.isVisibleDataTicket(maxSeconds), State.Defect);
		maxSeconds = 2;
		checks.add(
			"Son visibles los datos del tícket (los esperamos hasta " + maxSeconds + " segundos)",
			modalDetalleCompra.isVisibleDataTicket(maxSeconds), State.Defect);
		checks.add(
			"Figura un id de tícket (lo esperamos hasta " + maxSeconds + " segundos)",
			modalDetalleCompra.isVisibleIdTicket(maxSeconds), State.Defect);
		checks.add(
			"Figura alguna prenda (la esperamos hasta " + maxSeconds + " segundos)",
			modalDetalleCompra.isVisiblePrendaUntil(maxSeconds), State.Warn);
		return checks;
	}
	
	@SuppressWarnings("static-access")
	@Validation
	private ChecksTM checkDataContent(Ticket compra) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Figura un id de tícket " + compra.getId(),
			modalDetalleCompra.getIdTicket(compra.getType()).compareTo(compra.getId())==0, State.Warn);
		checks.add(
			"Figura el importe " + compra.getPrecio(),
			modalDetalleCompra.getImporte().contains(compra.getPrecio()), State.Warn);
		checks.add(
			"Existen " + compra.getNumItems() + " prendas",
			modalDetalleCompra.getNumPrendas()==compra.getNumItems(), State.Warn);
		return checks;
	}
	
//	@SuppressWarnings("static-access")
//	@Validation (
//		description="Aparece la imagen correspondiente al código de barras de la compra",
//		level=State.Warn)
//	private boolean checkIsVisibleImgCodigoBarrasMovil() {
//		return (modalDetalleCompra.isVisibleCodigoBarrasImg());
//	}
	
	@SuppressWarnings("static-access")
	@Step (
		description="Seleccionar el #{posArticulo}o artículo de la Compra", 
		expected="Aparece la sección correspondiente al \"QuickView\" del artículo")
	public void selectArticulo(int posArticulo) {
		ArticuloScreen articulo = modalDetalleCompra.getDataArticulo(posArticulo);
		modalDetalleCompra.selectArticulo(posArticulo);
		modalDetalleArticuloSteps.validateIsOk(articulo);
	}
	
	@Step (
		description="Clickamos el link para volver a la lista de \"Mis compras\"",
		expected="Volvemos a la página de Mis Compras")
	public void gotoListaMisCompras() {
		if (modalDetalleArticulo.isVisible(0)) {
			modalDetalleArticulo.clickAspaForClose();
			modalDetalleArticulo.isInvisible(2);
		}
		modalDetalleCompra.gotoListaMisCompras();
	}
}
