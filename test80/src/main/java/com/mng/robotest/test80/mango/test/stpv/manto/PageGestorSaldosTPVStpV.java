package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorSaldosTPV;

public class PageGestorSaldosTPVStpV {

	@Validation
	public static ChecksResult validateIsPage(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Estamos en la página " + PageGestorSaldosTPV.titulo,
			PageGestorSaldosTPV.isPage(driver), State.Defect);
	 	validations.add(
			"Aparece el input de fecha de TPV",
			PageGestorSaldosTPV.isVisibleTPVInput(driver), State.Defect);
	 	return validations;
	}
	
	@Step (
		description="Introducimos una TPV válida y damos click a \"Consultar Saldos\"",
		expected="Muestra la tabla de saldos con el ID de la TPV en ella",
		saveErrorPage=SaveWhen.Never)
	public static void searchValidTPV(String tpv, WebDriver driver) throws Exception {
		PageGestorSaldosTPV.insertTPVAndClickConsultarSaldos(tpv, driver);
		checkAfterConsultSaldosTpv(tpv, driver);
	}
	
	@Validation
	private static ChecksResult checkAfterConsultSaldosTpv(String tpv, WebDriver driver) throws Exception {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece la tabla de saldos",
			PageGestorSaldosTPV.isTablaSaldosVisible(driver), State.Defect);
	 	validations.add(
			"Aparece el ID de la TPV <b>" + tpv + "</b> en la tabla",
			PageGestorSaldosTPV.isTPVIDVisible(tpv, driver), State.Defect);
		return validations;
	}
	
	@Step (
		description="Introducimos una TPV no válida y damos click a \"Consultar Saldos\"",
		expected="Muestra el mensaje conforme la TPV no existe",
		saveErrorPage=SaveWhen.Never)
	public static void searchUnvalidTPV(String tpv, WebDriver driver) throws Exception {
		PageGestorSaldosTPV.insertTPVAndClickConsultarSaldos(tpv, driver);
		checkIsVisibleMsgTpvSelectedNotExists(driver);
	}
	
	@Validation (
		description="Aparece el mensaje \"La tpv seleccionada no existe\"",
		level=State.Defect)
	private static boolean checkIsVisibleMsgTpvSelectedNotExists(WebDriver driver) throws Exception {
		return (PageGestorSaldosTPV.isUnvalidTPVMessageVisible(driver));
	}
}
