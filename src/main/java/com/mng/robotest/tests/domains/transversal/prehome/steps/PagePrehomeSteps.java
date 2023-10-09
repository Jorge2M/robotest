package com.mng.robotest.tests.domains.transversal.prehome.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.transversal.acceso.navigations.AccesoFlows;
import com.mng.robotest.tests.domains.transversal.prehome.pageobjects.PageJCAS;
import com.mng.robotest.tests.domains.transversal.prehome.pageobjects.PagePrehome;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePrehomeSteps extends StepBase {
	
	private final Pais pais = dataTest.getPais();
	private final IdiomaPais idioma = dataTest.getIdioma();
	private final PagePrehome pagePrehome = new PagePrehome();
	
	private static final String TAG_PAIS= "@TAGPAIS";
	private static final String TAG_IDIOMA = "@TAGIDIOMA";
	
	@Step (
		description="Acceder a la página de inicio y seleccionar el país <b>#{dataTest.getNombrePais()}</b>",
		expected="Se selecciona el país/idioma correctamente")
	public void seleccionPaisIdioma() {
		new AccesoFlows().goToInitURL();
		new PageJCAS().identJCASifExists();
		pagePrehome.selecionPais();
		checkPaisSelected();
	}
	
	@Validation
	private ChecksTM checkPaisSelected() {
		var checks = ChecksTM.getNew();
		if (channel==Channel.desktop) {
			checks.add(
			    Check.make(
				    "Queda seleccionado el país con código " + pais.getCodigoPais() + " (" + pais.getNombrePais() + ")",
				    pagePrehome.isPaisSelected(), Warn)
			    .store(StoreType.None).build());
		}
		
		boolean isPaisWithMarcaCompra = pagePrehome.isPaisSelectedWithMarcaCompra();
		if (pais.isVentaOnline()) {
			checks.add(
			    Check.make(
				    "El país <b>Sí</b> tiene la marca de venta online\"",
				    isPaisWithMarcaCompra, Warn)
			    .store(StoreType.None).build());
		} else {
			checks.add(
			    Check.make(
				    "El país <b>No</b> tiene la marca de venta online\"",
				    !isPaisWithMarcaCompra, Warn)
			    .store(StoreType.None).build());
		}
		return checks;
	}
	
	@Step (
		description="Si es preciso seleccionamos el idioma y finalmente el botón \"Entrar\"",
		expected="Se accede a la Shop correctamente")
	public void entradaShopGivenPaisSeleccionado() {
		pagePrehome.selecionIdiomaAndEnter();
	}

	public void seleccionPaisIdiomaAndEnter() throws Exception {
		seleccionPaisIdiomaAndEnter(false, true);
	}
	
	public void seleccionPaisIdiomaAndEnter(boolean execValidacs, boolean acceptCookies) 
			throws Exception {
		pagePrehome.previousAccessShopSteps();
		accesoShopViaPrehome(pais, idioma, execValidacs, acceptCookies);
	}

	@Step (
		description=
			"Acceso <b style=\"color:brown;\">#{pais.getNombrePais()} / #{idioma.getLiteral()}</b> desde la PreHome",
		expected="Se accede correctamente al pais / idioma seleccionados",
		saveNettraffic=SaveWhen.Always)
	private void accesoShopViaPrehome(Pais pais, IdiomaPais idioma, boolean execValidacs, boolean acceptCookies) throws Exception {
		replaceStepDescription(TAG_PAIS, pais.getNombrePais());
		replaceStepDescription(TAG_IDIOMA, idioma.getLiteral());
		
		pagePrehome.accesoShopViaPrehome();
		pagePrehome.manageCookies(acceptCookies);
		
		checksDefault();
		checksGeneric()
			.googleAnalytics()
			.netTraffic().execute();
		
		if (execValidacs) {
			checkPagePostPreHome();
		}
	}	
	
	@Validation
	private ChecksTM checkPagePostPreHome() {
		var checks = ChecksTM.getNew();
		String title = driver.getTitle().toLowerCase();
		if (app==AppEcom.outlet) {
			checks.add(
				"Aparece una pantalla en la que el título contiene <b>outlet</b>",
				title.contains("outlet"));
		} else {
			checks.add(
				"Aparece una pantalla en la que el título contiene <b>mango</b>",
				title.contains("mango"));
		}
		return checks;
	}
}
