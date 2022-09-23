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
	
	public void validateIsOk(ArticuloScreen articulo) {
		checkIsVisible(2);
		validateIsOkArticle(articulo);
	}

	@Validation(
		description="Aparece el modal correspondiente al artículo (lo esperamos hasta #{seconds} segundos)",
		level=State.Warn)
	public boolean checkIsVisible(int seconds) { 
		return modalDetalleArticulo.isVisible(seconds);
	}
	
	@Validation
	public ChecksTM validateIsOkArticle(ArticuloScreen articulo) {
		ChecksTM checks = ChecksTM.getNew();
		
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
}
