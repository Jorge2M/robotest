package com.mng.robotest.domains.login.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.bolsa.steps.PageIniciarSesionBolsaMobileSteps;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.steps.PageResultPagoSteps;

import static com.mng.robotest.domains.bolsa.steps.SecBolsaSteps.FluxBolsaCheckout.*;

public class Log004 extends TestBase {
	
	@Override
	public void execute() throws Exception {
		//Acceso y login
		//Forzar SoftLogin
		//Ejecutar mis compras -> aparece login
		//Acceder a checkout -> no aparece login
		//Finalizar compra y guardar tarjeta
		//Acceder a checkout -> aparece login
//		access();
//		altaArticulosBolsa();
//		clickComprarAndSelectIniciarSesion();
//		iniciarSesion();
//		if (!isPRO()) {
//			executeVisaPayment();
//			clickVerMisCompras();
//			checkNoElementsInBag();
//		}
	}

}
