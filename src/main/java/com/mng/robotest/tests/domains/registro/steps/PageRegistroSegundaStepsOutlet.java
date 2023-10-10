package com.mng.robotest.tests.domains.registro.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroSegundaOutlet;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.Constantes.ThreeState;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageRegistroSegundaStepsOutlet extends StepBase {
	
	private PageRegistroSegundaOutlet pageRegistroSegunda = new PageRegistroSegundaOutlet();
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
		
		if (app==AppEcom.outlet) {
			lineasComaSeparated+=",outlet";
			numLineas+=1;
		}

		dataRegistro.put("numlineas", String.valueOf(numLineas));
		dataRegistro.put("lineascomaseparated", lineasComaSeparated);
		int seconds = 5;
		checks.add(
			"Aparece la 2ª página de introducción de datos " + getLitSecondsWait(seconds),
			pageRegistroSegunda.isPageUntil(seconds), Warn);
		checks.add(
			Check.make(
			    "Se pueden seleccionar las colecciones " + lineasComaSeparated,
			    pageRegistroSegunda.isPresentInputForLineas(lineasComaSeparated), Info)
			.store(StoreType.None).build());
		
		int numColecciones = pageRegistroSegunda.getNumColecciones();
		checks.add(
			Check.make(
			    "Aparece un número de colecciones coincidente con el número de líneas (" + numLineas + ")",
			    numColecciones==numLineas, Info)
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
		
		setStepDescription(stepDescription);
		pageRegistroSegunda.setFechaNacimiento(fechaNacimiento);
		String lineasDesmarcadas = pageRegistroSegunda.desmarcarLineasRandom(dataRegistroOK.get("lineascomaseparated"));
		replaceStepDescription(tagListaRandom, lineasDesmarcadas);
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
		
		checksDefault();
	}
}