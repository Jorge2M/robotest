package com.mng.robotest.test.suites;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.domains.ayuda.tests.Ayuda;
import com.mng.robotest.domains.bolsa.tests.Bolsa;
import com.mng.robotest.domains.buscador.tests.Buscador;
import com.mng.robotest.domains.chequeregalo.tests.ChequeRegalo;
import com.mng.robotest.domains.compra.tests.Compra;
import com.mng.robotest.domains.compra.tests.CompraMultiAddress;
import com.mng.robotest.domains.favoritos.tests.Favoritos;
import com.mng.robotest.domains.ficha.tests.Ficha;
import com.mng.robotest.domains.footer.tests.Footer;
import com.mng.robotest.domains.galeria.tests.Galeria;
import com.mng.robotest.domains.login.tests.Login;
import com.mng.robotest.domains.loyalty.tests.Loyalty;
import com.mng.robotest.domains.micuenta.tests.MiCuenta;
import com.mng.robotest.domains.otros.tests.Otras;
import com.mng.robotest.domains.personalizacion.tests.Personalizacion;
import com.mng.robotest.domains.reembolsos.tests.Reembolsos;
import com.mng.robotest.domains.registro.tests.Registro;
import com.mng.robotest.domains.seo.tests.Seo;
import com.mng.robotest.test.appshop.paisidioma.PaisIdioma;
import com.mng.robotest.test.factoryes.ListPagosEspana;

public class SmokeTestSuite extends SuiteMangoMaker {

	public SmokeTestSuite(InputParamsMango inputParams) {
		super(inputParams);
	}

	@Override
	List<Class<?>> getClasses() {
		return Arrays.asList(
			Otras.class,
			Seo.class,
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
			ChequeRegalo.class,
			CompraMultiAddress.class,
			ListPagosEspana.class,
			MiCuenta.class,
			Favoritos.class,
			Reembolsos.class,
			Loyalty.class,
			Personalizacion.class
		);
	}

}
