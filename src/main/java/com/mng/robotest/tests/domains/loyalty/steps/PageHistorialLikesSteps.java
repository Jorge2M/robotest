package com.mng.robotest.tests.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageHistorialLikes;

public class PageHistorialLikesSteps extends StepBase {

	private final PageHistorialLikes pgHistorialLikes = new PageHistorialLikes();
	
	@Validation (description="Aparece el historial de movimientos de Loyalty " + SECONDS_WAIT)
	public boolean checkHistorialVisible(int seconds) {
		return pgHistorialLikes.isMovimientoVisible(seconds);
	}
	
	@Validation(description = "El primer movimiento es de <b>#{likes}</b> puntos")
	public boolean isLastMovementOf(int likes) {
		var mov1Opt = pgHistorialLikes.getLoyaltyMovement(1);
		return (!mov1Opt.isEmpty() && mov1Opt.get().getPoints()==likes);		
	}
	
	@Validation
	public ChecksTM checkPointsForEnvioTiendaPayment(int points, String idPedido) {
		var checks = ChecksTM.getNew();
		checks.add(
			"En los primeros 3 movimientos existe uno de <b>" + points + "</b> puntos asociado al código de pedido <b>" + idPedido + "</b>",
			isInFirst3Movements(points, idPedido));
			
		int pointsExpected = 10;
		checks.add(
			"En los primeros 3 movimientos existe uno de <b>" + pointsExpected + "</b> puntos",
			isInFirst3Movements(pointsExpected));
		
		return checks;
	}
	
	private boolean isInFirst3Movements(int points) {
		return isInFirst3Movements(points, "");
	}
	
	private boolean isInFirst3Movements(int points, String idPedido) {
		for (int i=1; i<=3; i++) {
			var movementOpt = pgHistorialLikes.getLoyaltyMovement(i);
			if (movementOpt.isEmpty()) {
				return false;
			}
			
			var movement = movementOpt.get();
			if (movement.getPoints()==points && 
				movement.getConcepto().contains(idPedido)) {
				return true;
			}
		}
		return false;
	}

}
