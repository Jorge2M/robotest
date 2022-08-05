package com.mng.robotest.test.steps.shop.checkout.envio;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.mng.robotest.test.pageobject.shop.checkout.envio.ModalDroppoints;
import com.mng.robotest.test.pageobject.shop.checkout.envio.SecSelectDPoint.TypeDeliveryPoint;
import com.mng.robotest.test.steps.shop.checkout.envio.DataSearchDeliveryPoint.DataSearchDp;

@SuppressWarnings({"static-access"})
public class SecSelectDPointSteps {
	
	static final String tagSearchDp = "@TagSearchDp";
	@Step (
		description="Introducimos la provincia <b>" + tagSearchDp + "</b> + Return", 
		expected="Aparecen puntos de recogida de " + tagSearchDp)
	public static void searchPoblacion(DataSearchDeliveryPoint dataSearchDp, WebDriver driver) 
	throws Exception {
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(tagSearchDp, dataSearchDp.data);
		step.replaceInExpected(tagSearchDp, dataSearchDp.data);
		
		TypeDeliveryPoint typeDp = dataSearchDp.tipoTransporte.getTypeDeliveryPoint();
		ModalDroppoints.secSelectDPoint.sendProvincia(dataSearchDp.data, driver);
		SeleniumUtils.waitForPageLoaded(driver, 5);   

		checkDroppointSelectedContainsDirecc(dataSearchDp, driver);
		validaDeliveryPointOfType(typeDp, driver);
	}
	
	@Validation
	private static ChecksTM checkDroppointSelectedContainsDirecc(DataSearchDeliveryPoint dataSearchDp, WebDriver driver) 
	throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
		State stateVal = State.Warn;
		if (dataSearchDp.typeData==DataSearchDp.CodigoPostal) {
			stateVal = State.Info;
		}
	 	checks.add(
			"La dirección del droppoint seleccionado contiene <b>" + dataSearchDp.data + 
			"</b> (lo esperamos hasta " + maxSeconds + " segundos)",
			ModalDroppoints.secSelectDPoint.
				deliveryPointSelectedContainsPoblacionUntil(dataSearchDp, maxSeconds, driver), stateVal);
		return checks;
	}
	
	@Validation
	public static ChecksTM validaDeliveryPointOfType(TypeDeliveryPoint typeDp, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 3;
	 	checks.add(
			"Es visible el 1er delivery point de la lista (lo esperamos hasta " + maxSeconds + " segundos)",
			ModalDroppoints.secSelectDPoint.isDroppointVisibleUntil(1, maxSeconds, driver), State.Defect);
	 	checks.add(
			"El 1er delivery point de la lista es de tipo <b>" + typeDp + "</b>",
			ModalDroppoints.secSelectDPoint.getTypeDeliveryPoint(1, driver)==typeDp, State.Defect);
	 	return checks;
	}
	
	@Step (
		description="Clickamos en el <b>#{position}º</b> droppoint", 
		expected="El droppoint queda seleccionado")
	public static DataDeliveryPoint clickDeliveryPointAndGetData(int position, WebDriver driver) throws Exception {
		DataDeliveryPoint dataDpToReturn = ModalDroppoints.secSelectDPoint.clickDeliveryPointAndGetData(position, driver);
		checkIsSelectedDroppoint(position, driver);
		return dataDpToReturn;
	}
	
	@Validation (
		description="Queda seleccionado el Droppoint #{position}",
		level=State.Defect)
	private static boolean checkIsSelectedDroppoint(int position, WebDriver driver) {
		return (ModalDroppoints.secSelectDPoint.isDroppointSelected(position, driver));
	}
	
	@Step (
		description="Clickamos el botón de \"Select\" de la capa de Droppoints", 
		expected="Desaparece al capa de droppoint")
	public static void clickSelectButton(Channel channel, WebDriver driver) {
		int maxSeconds = 5;
		ModalDroppoints.secSelectDPoint.clickSelectButtonAndWait(maxSeconds, driver);
		ModalDroppointsSteps.secConfirmDatos.validateIsVisible(3, channel, driver);
	}
}
