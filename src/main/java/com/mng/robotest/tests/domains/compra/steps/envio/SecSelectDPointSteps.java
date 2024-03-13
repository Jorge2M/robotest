package com.mng.robotest.tests.domains.compra.steps.envio;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.ModalDroppoints;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.SecSelectDPoint.TypeDeliveryPoint;
import com.mng.robotest.tests.domains.compra.steps.envio.DataSearchDeliveryPoint.DataSearchDp;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecSelectDPointSteps extends StepBase {
	
	private final ModalDroppoints modalDroppoints = new ModalDroppoints();
	
	private static final String TAG_SEARCH_DP = "@TagSearchDp";
	
	@Step (
		description="Introducimos la provincia <b>" + TAG_SEARCH_DP + "</b> + Return", 
		expected="Aparecen puntos de recogida de " + TAG_SEARCH_DP)
	public void searchPoblacion(DataSearchDeliveryPoint dataSearchDp) {
		replaceStepDescription(TAG_SEARCH_DP, dataSearchDp.getData());
		replaceStepExpected(TAG_SEARCH_DP, dataSearchDp.getData());
		
		TypeDeliveryPoint typeDp = dataSearchDp.getTipoTransporte().getTypeDeliveryPoint();
		modalDroppoints.sendProvincia(dataSearchDp.getData());
		PageObjTM.waitForPageLoaded(driver, 5);   

		checkDroppointSelectedContainsDirecc(dataSearchDp);
		validaDeliveryPointOfType(typeDp);
	}
	
	@Validation
	private ChecksTM checkDroppointSelectedContainsDirecc(DataSearchDeliveryPoint dataSearchDp) {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		State stateVal = WARN;
		if (dataSearchDp.getTypeData()==DataSearchDp.CODIGO_POSTAL) {
			stateVal = INFO;
		}
	 	checks.add(
			"La dirección del droppoint seleccionado contiene <b>" + dataSearchDp.getData() + 
			"</b> " + getLitSecondsWait(seconds),
			modalDroppoints.deliveryPointSelectedContainsPoblacionUntil(dataSearchDp, seconds), stateVal);
	 	
		return checks;
	}
	
	@Validation
	public ChecksTM validaDeliveryPointOfType(TypeDeliveryPoint typeDp) {
		var checks = ChecksTM.getNew();
		int seconds = 3;
	 	checks.add(
			"Es visible el 1er delivery point de la lista " + getLitSecondsWait(seconds),
			modalDroppoints.isDroppointVisibleUntil(1, seconds));
	 	
	 	checks.add(
			"El 1er delivery point de la lista es de tipo <b>" + typeDp + "</b>",
			modalDroppoints.getTypeDeliveryPoint(1)==typeDp);
	 	
	 	return checks;
	}
	
	@Step (
		description="Clickamos en el <b>#{position}º</b> droppoint", 
		expected="El droppoint queda seleccionado")
	public DataDeliveryPoint clickDeliveryPointAndGetData(int position) {
		DataDeliveryPoint dataDpToReturn = modalDroppoints.clickDeliveryPointAndGetData(position);
		checkIsSelectedDroppoint(position);
		return dataDpToReturn;
	}
	
	@Validation (
		description="Queda seleccionado el Droppoint #{position}")
	private boolean checkIsSelectedDroppoint(int position) {
		return modalDroppoints.isDroppointSelected(position);
	}
	
	@Step (
		description="Clickamos el botón de \"Select\" de la capa de Droppoints", 
		expected="Desaparece al capa de droppoint")
	public void clickSelectButton() {
		int seconds = 5;
		modalDroppoints.clickSelectButtonAndWait(seconds);
		new ModalDroppointsSteps().getSecConfirmDatosSteps().checkIsVisible(7);
	}
}
