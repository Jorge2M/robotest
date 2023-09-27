package com.mng.robotest.domains.micuenta.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.domains.micuenta.beans.Ticket;
import com.mng.robotest.domains.micuenta.pageobjects.PageDetalleCompra;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.utils.DataFichaArt;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ModalDetalleCompraSteps extends StepBase {
	
	private final PageDetalleCompra pageDetalleCompra = PageDetalleCompra.make(channel);
	
	public void validateIsOk(Ticket compraTienda) {
		ChecksTM checks = checkIsDataVisible();
		if (!checks.calculateStateValidation().isMoreCriticThan(Warn)) {
			checkDataContent(compraTienda);
		}
	}
	
	@Validation
	public ChecksTM checkIsDataVisible() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
		checks.add(
			"Es visible la capa correspondiente al detalle del tícket de compra " + getLitSecondsWait(seconds),
			pageDetalleCompra.isVisibleDataTicket(seconds), Warn);
		
		seconds = 2;
		checks.add(
			"Son visibles los datos del tícket " + getLitSecondsWait(seconds),
			pageDetalleCompra.isVisibleDataTicket(seconds));
		
		checks.add(
			"Figura un id de tícket " + getLitSecondsWait(seconds),
			pageDetalleCompra.isVisibleIdTicket(seconds));
		
		checks.add(
			"Figura alguna prenda " + getLitSecondsWait(seconds),
			pageDetalleCompra.isVisiblePrendaUntil(seconds), Warn);
		
		return checks;
	}
	
	@Validation
	private ChecksTM checkDataContent(Ticket compra) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Figura un id de tícket " + compra.getId(),
			pageDetalleCompra.getIdTicket(compra.getType()).compareTo(compra.getId())==0, Warn);
		checks.add(
			"Figura el importe " + compra.getPrecio(),
			pageDetalleCompra.getImporte().contains(compra.getPrecio()), Warn);
		checks.add(
			"Existen " + compra.getNumItems() + " prendas",
			pageDetalleCompra.getNumPrendas()==compra.getNumItems(), Warn);
		return checks;
	}
	
	@Step (
		description="Seleccionar el #{posArticulo}o artículo de la Compra", 
		expected="Aparece la ficha del artículo")
	public void selectArticulo(int posArticulo) {
		ArticuloScreen articulo = pageDetalleCompra.getDataArticulo(posArticulo);
		pageDetalleCompra.selectArticulo(posArticulo);
		var dataFichaArt = new DataFichaArt(articulo.getReferencia(), articulo.getNombre());
		new PageFichaSteps().checkDetallesProducto(dataFichaArt);
	}
	
	@Validation (description="Es visible la dirección <b>#{address}</b>")
	public boolean checkIsVisibleDirection(String address) {
		return pageDetalleCompra.isVisibleDireccionEnvio(address);
	}
}
