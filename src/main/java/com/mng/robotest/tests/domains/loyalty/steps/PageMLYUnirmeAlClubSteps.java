package com.mng.robotest.tests.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageMLYUnirmeAlClub;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;

public class PageMLYUnirmeAlClubSteps extends StepBase {

	private final PageMLYUnirmeAlClub pgMLYUnirmeAlClub = new PageMLYUnirmeAlClub();
	
	@Validation (description="Aparece la página de MLY Unirme al Club " + SECONDS_WAIT)
	public boolean isPage(int seconds) {
		return pgMLYUnirmeAlClub.isPage(seconds);
	}	
	
	@Step (
		description="Seleccionar el botón <b>Unirme al club</b>", 
		expected="Aparece la capa del registro")
	public void selectUnirmeAlClub(String email) {
		pgMLYUnirmeAlClub.selectUnirmeAlClub();
		isVisibleCreaTuCuenta(1);
	}
	
	@Step (description="Seleccionar el link <b>¿Qué son los likes?</b>")
	public void selectQueSonLosLikes() {
		pgMLYUnirmeAlClub.selectQueSonLosLikes();
		isVisibleModalTodoSobreLosLikes(1);
	}
	
	@Validation (description="Es visible el modal <b>Todo sobre los Likes</b> " + SECONDS_WAIT)	
	public boolean isVisibleModalTodoSobreLosLikes(int seconds) {
		return pgMLYUnirmeAlClub.isVisibleModalTodoSobreLosLikes(seconds); 
	}
	
	@Validation (description="Es visible el modal para crear tu cuenta " + SECONDS_WAIT)	
	public boolean isVisibleCreaTuCuenta(int seconds) {
		return pgMLYUnirmeAlClub.isVisibleCrearCuenta(seconds); 
	}
	
	@Step (
		description="Seleccionar el botón <b>Unirme al club</b>", 
		expected="Aparece la capa de crea tu cuenta")
	public void selectUnirmeAlClub() {
		pgMLYUnirmeAlClub.selectUnirmeAlClub();
		isVisibleCreaTuCuenta(3);
	}
	
	@Step (
			description=
			"Registrarse con los datos:<br>" + 
			"  - Email: <b>#{data.getEmail()}</b><br>" +
			"  - Contraseña: <b>#{data.getPassword()}</b></br>" + 
			"  - Mobil: <b>#{data.getMovil()}</b>",
		expected="El registro es correcto y estamos logados")
	public void registro(DataNewRegister data) {
		pgMLYUnirmeAlClub.inputEmail(data.getEmail());
		pgMLYUnirmeAlClub.inputPassword(data.getPassword());
		pgMLYUnirmeAlClub.inputMobil(data.getMovil());
		pgMLYUnirmeAlClub.clickCrearCuenta();
		
		new AccesoSteps().checkIsLogged();
	}
	
}
