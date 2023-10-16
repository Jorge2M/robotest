package com.mng.robotest.tests.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageHistorialLikes;

public class PageHistorialLikesSteps extends StepBase {

	private final PageHistorialLikes pageHistorialLikes = new PageHistorialLikes();
	
	@Validation (description="Aparece el historial de movimientos de Loyalty " + SECONDS_WAIT)
	public boolean checkHistorialVisible(int seconds) {
		return pageHistorialLikes.isMovimientoVisible(seconds);
	}
	
	@Validation
	public ChecksTM checkPointsForEnvioTiendaPayment(int points, String idPedido) {
		var checks = ChecksTM.getNew();
		var mov1Opt = pageHistorialLikes.getLoyaltyMovement(1);
		checks.add(
			"El primer movimiento es de <b>" + points + "</b> puntos",
			!mov1Opt.isEmpty() && mov1Opt.get().getPoints()==points);
			
		checks.add(
			"El primer movimiento hace referencia al código de pedido <b>" + idPedido + "</b>",
			!mov1Opt.isEmpty() && mov1Opt.get().getConcepto().contains(idPedido));
		
		var mov2Opt = pageHistorialLikes.getLoyaltyMovement(2);
		checks.add(
			"El segundo movimiento es de <b>10</b> puntos",
			!mov2Opt.isEmpty() && mov2Opt.get().getPoints()==10);
		
		return checks;
	}

}
