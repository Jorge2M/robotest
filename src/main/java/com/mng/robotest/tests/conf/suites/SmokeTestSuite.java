package com.mng.robotest.tests.conf.suites;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.conf.factories.ListPagosEspana;
import com.mng.robotest.tests.domains.ayuda.tests.Ayuda;
import com.mng.robotest.tests.domains.bolsa.tests.Bolsa;
import com.mng.robotest.tests.domains.buscador.tests.Buscador;
import com.mng.robotest.tests.domains.changecountry.tests.ChangeCountry;
import com.mng.robotest.tests.domains.chatbot.tests.Chatbot;
import com.mng.robotest.tests.domains.chequeregalo.tests.ChequeRegalo;
import com.mng.robotest.tests.domains.compra.tests.Compra;
import com.mng.robotest.tests.domains.compra.tests.CompraMultiAddress;
import com.mng.robotest.tests.domains.compranew.tests.CompraNew;
import com.mng.robotest.tests.domains.favoritos.tests.Favoritos;
import com.mng.robotest.tests.domains.ficha.tests.Ficha;
import com.mng.robotest.tests.domains.footer.tests.Footer;
import com.mng.robotest.tests.domains.galeria.tests.Galeria;
import com.mng.robotest.tests.domains.login.tests.Login;
import com.mng.robotest.tests.domains.loyalty.tests.Loyalty;
import com.mng.robotest.tests.domains.menus.tests.PaisIdioma;
import com.mng.robotest.tests.domains.micuenta.tests.MiCuenta;
import com.mng.robotest.tests.domains.otros.tests.Otras;
import com.mng.robotest.tests.domains.reembolsos.tests.Reembolsos;
import com.mng.robotest.tests.domains.registro.tests.Registro;
import com.mng.robotest.tests.domains.setcookies.tests.Setcookies;

public class SmokeTestSuite extends SuiteMangoMaker {

	public SmokeTestSuite(InputParamsMango inputParams) {
		super(inputParams);
	}

	@Override
	List<Class<?>> getClasses() {
		return Arrays.asList(
			Otras.class,
			ChangeCountry.class,
			Chatbot.class,
			Login.class,
			Bolsa.class,
			Ficha.class,
			Ayuda.class,
			Buscador.class,
			Footer.class,
			Registro.class,
			PaisIdioma.class,
			Galeria.class,
			Compra.class,
			CompraNew.class,
			Setcookies.class,
			ChequeRegalo.class,
			CompraMultiAddress.class,
			ListPagosEspana.class,
			MiCuenta.class,
			Favoritos.class,
			Reembolsos.class,
			Loyalty.class
		);
	}

}
