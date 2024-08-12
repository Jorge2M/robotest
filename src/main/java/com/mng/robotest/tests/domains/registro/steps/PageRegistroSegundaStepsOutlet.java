package com.mng.robotest.tests.domains.registro.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.menus.entity.LineaType;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroSegundaOutlet;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.Constantes.ThreeState;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;

public class PageRegistroSegundaStepsOutlet extends StepBase {
	
	private PageRegistroSegundaOutlet pgRegistroSegunda = new PageRegistroSegundaOutlet();
	private final Pais pais = dataTest.getPais();
	
	@Validation
	public ChecksTM validaIsPageRegistroOK(Map<String,String> dataRegistro) {
		var checks = ChecksTM.getNew();
		String lineasComaSeparated = "";
		int numLineas = 0;
		if (pais.getShoponline().stateLinea(LineaType.SHE, app)==ThreeState.TRUE) {
			lineasComaSeparated = LineaType.SHE.name();
			numLineas+=1;
		}
		
		if (pais.getShoponline().stateLinea(LineaType.HE, app)==ThreeState.TRUE) {
			lineasComaSeparated+="," + LineaType.HE.name();
			numLineas+=1;
		}
		
		if (pais.getShoponline().stateLinea(LineaType.NINA, app)==ThreeState.TRUE ||
			pais.getShoponline().stateLinea(LineaType.NINO, app)==ThreeState.TRUE) {
			lineasComaSeparated+="," + LineaType.KIDS.name();
			numLineas+=1;
		}
		
		if (isOutlet()) {
			lineasComaSeparated+=",outlet";
			numLineas+=1;
		}

		dataRegistro.put("numlineas", String.valueOf(numLineas));
		dataRegistro.put("lineascomaseparated", lineasComaSeparated);
		int seconds = 5;
		checks.add(
			"Aparece la 2ª página de introducción de datos " + getLitSecondsWait(seconds),
			pgRegistroSegunda.isPage(seconds), WARN);
		checks.add(
			Check.make(
			    "Se pueden seleccionar las colecciones " + lineasComaSeparated,
			    pgRegistroSegunda.isPresentInputForLineas(lineasComaSeparated), INFO)
			.store(NONE).build());
		
		int numColecciones = pgRegistroSegunda.getNumColecciones();
		checks.add(
			Check.make(
			    "Aparece un número de colecciones coincidente con el número de líneas (" + numLineas + ")",
			    numColecciones==numLineas, INFO)
			.store(NONE).build());
		
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
		
		setStepDescription(stepDescription);
		pgRegistroSegunda.setFechaNacimiento(fechaNacimiento);
		String lineasDesmarcadas = pgRegistroSegunda.desmarcarLineasRandom(dataRegistroOK.get("lineascomaseparated"));
		replaceStepDescription(tagListaRandom, lineasDesmarcadas);
		dataRegistroOK.put("clicklineas", lineasDesmarcadas);
		if (paisConNinos) {
			pgRegistroSegunda.setNumeroNinos(numNinos);
		} else {
			pgRegistroSegunda.clickButtonContinuar();
		}				

		if (paisConNinos) {
			new PageRegistroNinosStepsOutlet().validaIsPageWithNinos(numNinos);
		} else {
			new PageRegistroDirecStepsOutlet().isPageFromPais();
		}
		
		checksDefault();
	}
}
