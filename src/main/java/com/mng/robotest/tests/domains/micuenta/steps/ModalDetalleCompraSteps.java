package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.tests.domains.micuenta.beans.Ticket;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageDetalleCompra;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;
import com.mng.robotest.testslegacy.pageobject.utils.DataFichaArt;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ModalDetalleCompraSteps extends StepBase {
	
	private final PageDetalleCompra pgDetalleCompra = PageDetalleCompra.make(channel);
	
	public void validateIsOk(Ticket compraTienda) {
		ChecksTM checks = checkIsDataVisible();
		if (!checks.calculateStateValidation().isMoreCriticThan(WARN)) {
			checkDataContent(compraTienda);
		}
	}
	
	@Validation
	public ChecksTM checkIsDataVisible() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
		checks.add(
			"Es visible la capa correspondiente al detalle del tícket de compra " + getLitSecondsWait(seconds),
			pgDetalleCompra.isVisibleDataTicket(seconds), WARN);
		
		seconds = 2;
		checks.add(
			"Son visibles los datos del tícket " + getLitSecondsWait(seconds),
			pgDetalleCompra.isVisibleDataTicket(seconds));
		
		checks.add(
			"Figura un id de tícket " + getLitSecondsWait(seconds),
			pgDetalleCompra.isVisibleIdTicket(seconds));
		
		checks.add(
			"Figura alguna prenda " + getLitSecondsWait(seconds),
			pgDetalleCompra.isVisiblePrendaUntil(seconds), WARN);
		
		return checks;
	}
	
	@Validation
	private ChecksTM checkDataContent(Ticket compra) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Figura un id de tícket " + compra.getId(),
			pgDetalleCompra.getIdTicket(compra.getType()).compareTo(compra.getId())==0, WARN);
		
		checks.add(
			"Figura el importe " + compra.getPrecio(),
			pgDetalleCompra.getImporte().contains(compra.getPrecio()), WARN);
		
		checks.add(
			"Existen " + compra.getNumItems() + " prendas",
			pgDetalleCompra.getNumPrendas()==compra.getNumItems(), WARN);
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el #{posArticulo}o artículo de la Compra", 
		expected="Aparece la ficha del artículo")
	public void selectArticulo(int posArticulo) {
		ArticuloScreen articulo = pgDetalleCompra.getDataArticulo(posArticulo);
		pgDetalleCompra.selectArticulo(posArticulo);
		var dataFichaArt = new DataFichaArt(articulo.getReferencia(), articulo.getNombre());
		new PageFichaSteps().checkDetallesProducto(dataFichaArt);
	}
	
	@Validation (description="Es visible la dirección <b>#{address}</b>")
	public boolean checkIsVisibleDirection(String address) {
		return pgDetalleCompra.isVisibleDireccionEnvio(address);
	}
}
