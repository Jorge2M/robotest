package com.mng.robotest.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.loyalty.pageobjects.PageHistorialLikes;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageHistorialLikesSteps extends StepBase {

	private final PageHistorialLikes pageHistorialLikes = new PageHistorialLikes();
	
	@Validation (
		description="Aparece el historial de movimientos de Loyalty (lo esperamos #{seconds} segundos)",
		level=Defect)
	public boolean checkHistorialVisible(int seconds) {
		return pageHistorialLikes.isMovimientoVisible(seconds);
	}
	
	@Validation
	public ChecksTM checkPointsForEnvioTiendaPayment(int points, String idPedido) {
		var checks = ChecksTM.getNew();
		var mov1Opt = pageHistorialLikes.getLoyaltyMovement(1);
		checks.add(
			"El primer movimiento es de <b>" + points + "</b> puntos",
			!mov1Opt.isEmpty() && mov1Opt.get().getPoints()==points, Defect);
			
		checks.add(
			"El primer movimiento hace referencia al código de pedido <b>" + idPedido + "</b>",
			!mov1Opt.isEmpty() && mov1Opt.get().getConcepto().contains(idPedido), Defect);
		
		var mov2Opt = pageHistorialLikes.getLoyaltyMovement(2);
		checks.add(
			"El segundo movimiento es de <b>10</b> puntos",
			!mov2Opt.isEmpty() && mov2Opt.get().getPoints()==10, Defect);
		
		return checks;
	}

}
