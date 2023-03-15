package com.mng.robotest.domains.transversal.prehome.steps;

import java.util.Arrays;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.domains.transversal.prehome.pageobjects.PageJCAS;
import com.mng.robotest.domains.transversal.prehome.pageobjects.PagePrehome;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.steps.navigations.shop.AccesoNavigations;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

public class PagePrehomeSteps extends StepBase {
	
	private final Pais pais = dataTest.getPais();
	private final IdiomaPais idioma = dataTest.getIdioma();
	
	private final PagePrehome pagePrehome = new PagePrehome();
	
	@Step (
		description="Acceder a la página de inicio y seleccionar el país <b>#{dataTest.getNombrePais()}</b>",
		expected="Se selecciona el país/idioma correctamente")
	public void seleccionPaisIdioma() {
		new AccesoNavigations().goToInitURL();
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
				    "Queda seleccionado el país con código " + pais.getCodigo_pais() + " (" + pais.getNombre_pais() + ")",
				    pagePrehome.isPaisSelected(), State.Warn)
			    .store(StoreType.None).build());
		}
		
		boolean isPaisWithMarcaCompra = pagePrehome.isPaisSelectedWithMarcaCompra();
		if (pais.isVentaOnline()) {
			checks.add(
			    Check.make(
				    "El país <b>Sí</b> tiene la marca de venta online\"",
				    isPaisWithMarcaCompra, State.Warn)
			    .store(StoreType.None).build());
		} else {
			checks.add(
			    Check.make(
				    "El país <b>No</b> tiene la marca de venta online\"",
				    !isPaisWithMarcaCompra, State.Warn)
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
		seleccionPaisIdiomaAndEnter(false);
	}
	
	private final String TagPais = "@TAGPAIS";
	private final String TagIdioma = "@TAGIDIOMA";
	@Step (
		description="Acceder a la página de inicio y seleccionar el país <b>" + TagPais + "</b>, el idioma <b>" + TagIdioma + "</b> y acceder",
		expected="Se accede correctamente al pais / idioma seleccionados",
		saveNettraffic=SaveWhen.Always)
	public void seleccionPaisIdiomaAndEnter(boolean execValidacs) throws Exception {
		TestMaker.getCurrentStepInExecution().replaceInDescription(TagPais, pais.getNombre_pais());
		TestMaker.getCurrentStepInExecution().replaceInDescription(TagIdioma, idioma.getLiteral());
		
		pagePrehome.accesoShopViaPrehome(true);
		
		GenericChecks.checkDefault();
		GenericChecks.from(Arrays.asList(
				GenericCheck.GOOGLE_ANALYTICS,
				GenericCheck.NET_TRAFFIC)).checks();
		
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
				title.contains("outlet"), State.Defect);
		} else {
			checks.add(
				"Aparece una pantalla en la que el título contiene <b>mango</b>",
				title.contains("mango"), State.Defect);
		}
		return checks;
	}
}