package com.mng.robotest.testslegacy.appshop.paisaplicavale;

import java.io.Serializable;
import org.testng.annotations.*;

import com.mng.robotest.tests.conf.suites.PagosPaisesSuite.VersionPagosSuite;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.github.jorge2m.testmaker.domain.TestFromFactory;

public class PaisAplicaVale implements TestFromFactory, Serializable {

	private static final long serialVersionUID = 7404913971070937277L;
	
	private String indexFact;
	
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
		
		this.indexFact = 
			pais.getNombrePais() + " (" + pais.getCodigoPais() + ") " + " - " + 
			idioma.getCodigo().getLiteral();
	}
	
	@Override
	public String getIdTestInFactory() {
		return indexFact;
	}
	
	@Test (
		groups={"Pagos", "shop-movil-web", "Canal:desktop,mobile_App:shop,outlet"}, alwaysRun=true, 
		description="Compra usuario no registrado")
	public void CHK001_Compra() throws Exception {
		new Chk001(pais, idioma, fTCkoutIni).execute();
	}
	
}
