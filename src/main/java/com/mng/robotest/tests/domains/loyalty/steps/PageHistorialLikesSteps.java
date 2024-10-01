package com.mng.robotest.tests.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageHistorialLikes;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import static com.mng.robotest.tests.domains.loyalty.steps.PageHistorialLikesSteps.TypePoints.*;
import static com.github.jorge2m.testmaker.conf.State.*;

public class PageHistorialLikesSteps extends StepBase {

	private final PageHistorialLikes pgHistorialLikes = PageHistorialLikes.make(dataTest.getPais());
	
	public enum TypePoints { RECEIVED, USED }
	
	@Validation (description="Aparece el historial de movimientos de Loyalty " + SECONDS_WAIT)
	public boolean checkHistorialVisible(int seconds) {
		return pgHistorialLikes.isMovimientoVisible(seconds);
	}
	
	@Validation(description = "El primer movimiento es de <b>#{likes}</b> puntos recibidos")
	public boolean isLastMovementOf(int likes) {
		var mov1Opt = pgHistorialLikes.getLoyaltyMovement(1);
		return (!mov1Opt.isEmpty() && mov1Opt.get().getPointsReceived()==likes);		
	}
	
	@Validation(description = "Entre los primeros #{movements} hay uno de <b>#{likes}</b> puntos")
	public boolean isMovementOf(int likes, int movements) {
		for (int i=1; i<=movements; i++) {
			var movOpt = pgHistorialLikes.getLoyaltyMovement(i);
			if (movOpt.isEmpty()) {
				return false;
			}
			if (movOpt.get().getPointsReceived()==likes) {
				return true;
			}
		}
		return false;
	}
	
	public void checkPointsForEnvioTiendaPayment(int pointsUsed, int pointsReceived, String idPedido) {
		checkPointsPayment(pointsUsed, pointsReceived, idPedido);
		checkSpecificPointsEnvioTienda(10);
	}
	
	@Validation
	public ChecksTM checkPointsPayment(int pointsUsed, int pointsReceived, String idPedido) {
		var checks = ChecksTM.getNew();
		int marginPoints = 5;
		var levelError = DEFECT;
		if (UtilsTest.todayBeforeDate("2024-10-15") && isPRE()) { //Hay un problema de entorno en PRE que algún día habría que reclamar a Mercurio
			levelError = WARN;
		}
		checks.add(
			"En los primeros 4 movimientos existe uno de <b>" + pointsReceived + "</b> puntos recibidos asociado al código de pedido <b>" + idPedido + "</b>",
			isInFirst4Movements(RECEIVED, pointsReceived, idPedido), levelError);
		
		checks.add(
			"En los primeros 4 movimientos existe uno de <b>" + pointsUsed + "</b> (±" + marginPoints + ") puntos usados asociado al código de pedido <b>" + idPedido + "</b>",
			isInFirstXMovements(USED, pointsUsed, marginPoints, 4, idPedido), levelError);
			
		return checks;
	}
	
	@Validation(description = "En los primeros 4 movimientos existe uno de <b>#{points}</b> puntos recibidos")
	public boolean checkSpecificPointsEnvioTienda(int points) {
		return isInFirst4Movements(TypePoints.RECEIVED, points);
	}
	
	private boolean isInFirst4Movements(TypePoints type, int points) {
		return isInFirst4Movements(type, points, "");
	}
	
	private boolean isInFirst4Movements(TypePoints type, int points, String idPedido) {
		return isInFirstXMovements(type, points, 0, 4, idPedido);
	}
	
	private boolean isInFirstXMovements(TypePoints typePoints, int pointsExpected, int margin, int movements, String idPedido) {
		for (int i=1; i<=movements; i++) {
			var movementOpt = pgHistorialLikes.getLoyaltyMovement(i);
			if (movementOpt.isEmpty()) {
				return false;
			}
			
			var movement = movementOpt.get();
			var pointsExistent = (typePoints==RECEIVED) ? movement.getPointsReceived() : movement.getPointsUsed();
			if (pointsExistent >= pointsExpected - margin &&
				pointsExistent <= pointsExpected + margin &&
				movement.getConcepto().contains(idPedido)) {
				return true;
			}
		}
		return false;
	}

}