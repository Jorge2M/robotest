package com.mng.robotest.test.appshop.paisaplicavale;

import java.io.Serializable;
import org.testng.annotations.*;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.suites.PagosPaisesSuite.VersionPagosSuite;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class PaisAplicaVale implements Serializable {

	private static final long serialVersionUID = 7404913971070937277L;
	
	private String index_fact;
	
	public final int prioridad;
	private final ConfigCheckout fTCkoutIni;
	private final Pais pais;
	private final IdiomaPais idioma;

	public PaisAplicaVale(VersionPagosSuite version, Pais pais, IdiomaPais idioma, int prioridad) {
		this.prioridad = prioridad;
		this.pais = pais;
		this.idioma = idioma;
		this.fTCkoutIni = ConfigCheckout.config()
				.emaiExists()
				.version(version).build();
		
		this.index_fact = 
			pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + " - " + 
			idioma.getCodigo().getLiteral();
	}
	
	@Test (
		groups={"Pagos", "shop-movil-web", "Canal:desktop,mobile_App:shop,outlet"}, alwaysRun=true, 
		description="Compra usuario no registrado")
	public void CHK001_Compra() throws Exception {
		TestCaseTM.addNameSufix(this.index_fact);
		new Chk001(pais, idioma, fTCkoutIni).execute();
	}
	
}
