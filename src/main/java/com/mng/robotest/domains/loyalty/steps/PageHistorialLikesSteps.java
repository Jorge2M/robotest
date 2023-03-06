package com.mng.robotest.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.loyalty.pageobjects.PageHistorialLikes;
import com.mng.robotest.domains.transversal.StepBase;

public class PageHistorialLikesSteps extends StepBase {

	private final PageHistorialLikes pageHistorialLikes = new PageHistorialLikes();
	
	@Validation (
		description="Aparece el historial de movimientos de Loyalty (lo esperamos #{seconds} segundos)",
		level=State.Defect)
	public boolean checkHistorialVisible(int seconds) {
		return pageHistorialLikes.isMovimientoVisible(seconds);
	}
	
	@Validation
	public ChecksTM checkPointsForEnvioTiendaPayment(int points, String idPedido) {
		var checks = ChecksTM.getNew();
		var mov1Opt = pageHistorialLikes.getLoyaltyMovement(1);
		checks.add(
			"El primer movimiento es de <b>" + points + "</b> puntos",
			!mov1Opt.isEmpty() && mov1Opt.get().getPoints()==points, State.Defect);
			
		//-> sólo faltaría el control del código de pedido
		
		var mov2Opt = pageHistorialLikes.getLoyaltyMovement(2);
		checks.add(
			"El segundo movimiento es de <b>10</b> puntos",
			!mov2Opt.isEmpty() && mov1Opt.get().getPoints()==10, State.Defect);
		
		return checks;
	}

}
