package com.mng.robotest.domains.registro.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroSegundaOutlet;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.test.data.Constantes.ThreeState;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;

public class PageRegistroSegundaStepsOutlet extends StepBase {
	
	private PageRegistroSegundaOutlet pageRegistroSegunda = new PageRegistroSegundaOutlet();
	private final Pais pais = dataTest.getPais();
	
	@Validation
	public ChecksTM validaIsPageRegistroOK(Map<String,String> dataRegistro) {
		ChecksTM checks = ChecksTM.getNew();
		String lineasComaSeparated = "";
		int numLineas = 0;
		if (pais.getShoponline().stateLinea(LineaType.she, app)==ThreeState.TRUE) {
			lineasComaSeparated = LineaType.she.name();
			numLineas+=1;
		}
		
		if (pais.getShoponline().stateLinea(LineaType.he, app)==ThreeState.TRUE) {
			lineasComaSeparated+="," + LineaType.he.name();
			numLineas+=1;
		}
		
		if (pais.getShoponline().stateLinea(LineaType.nina, app)==ThreeState.TRUE ||
			pais.getShoponline().stateLinea(LineaType.nino, app)==ThreeState.TRUE) {
			lineasComaSeparated+="," + LineaType.kids.name();
			numLineas+=1;
		}
		
		if (pais.getShoponline().stateLinea(LineaType.violeta, app)==ThreeState.TRUE) {
			lineasComaSeparated+="," + LineaType.violeta.name();
			numLineas+=1;
		}
		
		if (app==AppEcom.outlet) {
			lineasComaSeparated+=",outlet";
			numLineas+=1;
		}

		dataRegistro.put("numlineas", String.valueOf(numLineas));
		dataRegistro.put("lineascomaseparated", lineasComaSeparated);
		int seconds = 5;
		checks.add(
			"Aparece la 2ª página de introducción de datos (la esperamos hasta " + seconds + " segs)",
			pageRegistroSegunda.isPageUntil(seconds), State.Warn);
		checks.add(
			Check.make(
			    "Se pueden seleccionar las colecciones " + lineasComaSeparated,
			    pageRegistroSegunda.isPresentInputForLineas(lineasComaSeparated), State.Info)
			.store(StoreType.None).build());
		
		int numColecciones = pageRegistroSegunda.getNumColecciones();
		checks.add(
			Check.make(
			    "Aparece un número de colecciones coincidente con el número de líneas (" + numLineas + ")",
			    numColecciones==numLineas, State.Info)
			.store(StoreType.None).build());
		
		return checks;
	}
	
	/**
	 * Introduce los datos en la página: una fecha de nacimiento + un número de niños + selección random de líneas 
	 * @param fechaNacimiento fecha en formato "DD/MM/AAAA"
	 */
	@Step (
		description="@rewritable",
		expected="Aparece la página de introducción de datos del niño o la de datos de la dirección (según se podían o no seleccionar niños)")
	public void setDataAndLineasRandom(
			String fechaNacimiento, boolean paisConNinos, int numNinos, Map<String,String> dataRegistroOK) {
		String tagListaRandom = "@lineasRandom";
		String stepDescription = 
			"Introducir datos adicionales y pulsar \"Continue\" si no existen niños: <br>" +
			"  1) Introducir la fecha de nacimiento: <b>" + fechaNacimiento + "</b><br>" + 
			"  2) Desmarcar una serie de líneas al azar: <b> " + tagListaRandom + "</b>";
		if (paisConNinos) {
			stepDescription+=
			"<br>" +
			"  3) Seleccionar el número de niños: <b>" + numNinos + "</b>";
		}
		
		//Rewrite description step
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.setDescripcion(stepDescription);

		pageRegistroSegunda.setFechaNacimiento(fechaNacimiento);
		String lineasDesmarcadas = pageRegistroSegunda.desmarcarLineasRandom(dataRegistroOK.get("lineascomaseparated"));
		step.setDescripcion(step.getDescripcion().replace(tagListaRandom, lineasDesmarcadas));
		dataRegistroOK.put("clicklineas", lineasDesmarcadas);
		if (paisConNinos) {
			pageRegistroSegunda.setNumeroNinos(numNinos);
		} else {
			pageRegistroSegunda.clickButtonContinuar();
		}				

		if (paisConNinos) {
			new PageRegistroNinosStepsOutlet().validaIsPageWithNinos(numNinos);
		} else {
			new PageRegistroDirecStepsOutlet().isPageFromPais();
		}
		
		GenericChecks.checkDefault();
	}
}
