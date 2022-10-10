package com.mng.robotest.domains.compra.steps.envio;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.domains.compra.pageobject.envio.ModalDroppoints;
import com.mng.robotest.domains.compra.pageobject.envio.SecSelectDPoint.TypeDeliveryPoint;
import com.mng.robotest.domains.compra.steps.envio.DataSearchDeliveryPoint.DataSearchDp;
import com.mng.robotest.domains.transversal.StepBase;


@SuppressWarnings({"static-access"})
public class SecSelectDPointSteps extends StepBase {
	
	private final ModalDroppoints modalDroppoints = new ModalDroppoints();
	
	private static final String TAG_SEARCH_DP = "@TagSearchDp";
	
	@Step (
		description="Introducimos la provincia <b>" + TAG_SEARCH_DP + "</b> + Return", 
		expected="Aparecen puntos de recogida de " + TAG_SEARCH_DP)
	public void searchPoblacion(DataSearchDeliveryPoint dataSearchDp) throws Exception {
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(TAG_SEARCH_DP, dataSearchDp.data);
		step.replaceInExpected(TAG_SEARCH_DP, dataSearchDp.data);
		
		TypeDeliveryPoint typeDp = dataSearchDp.tipoTransporte.getTypeDeliveryPoint();
		modalDroppoints.sendProvincia(dataSearchDp.data);
		PageObjTM.waitForPageLoaded(driver, 5);   

		checkDroppointSelectedContainsDirecc(dataSearchDp);
		validaDeliveryPointOfType(typeDp);
	}
	
	@Validation
	private ChecksTM checkDroppointSelectedContainsDirecc(DataSearchDeliveryPoint dataSearchDp) 
			throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 5;
		State stateVal = State.Warn;
		if (dataSearchDp.typeData==DataSearchDp.CodigoPostal) {
			stateVal = State.Info;
		}
	 	checks.add(
			"La dirección del droppoint seleccionado contiene <b>" + dataSearchDp.data + 
			"</b> (lo esperamos hasta " + seconds + " segundos)",
			modalDroppoints.deliveryPointSelectedContainsPoblacionUntil(dataSearchDp, seconds), stateVal);
	 	
		return checks;
	}
	
	@Validation
	public ChecksTM validaDeliveryPointOfType(TypeDeliveryPoint typeDp) {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 3;
	 	checks.add(
			"Es visible el 1er delivery point de la lista (lo esperamos hasta " + seconds + " segundos)",
			modalDroppoints.isDroppointVisibleUntil(1, seconds), State.Defect);
	 	
	 	checks.add(
			"El 1er delivery point de la lista es de tipo <b>" + typeDp + "</b>",
			modalDroppoints.getTypeDeliveryPoint(1)==typeDp, State.Defect);
	 	
	 	return checks;
	}
	
	@Step (
		description="Clickamos en el <b>#{position}º</b> droppoint", 
		expected="El droppoint queda seleccionado")
	public DataDeliveryPoint clickDeliveryPointAndGetData(int position) throws Exception {
		DataDeliveryPoint dataDpToReturn = modalDroppoints.clickDeliveryPointAndGetData(position);
		checkIsSelectedDroppoint(position);
		return dataDpToReturn;
	}
	
	@Validation (
		description="Queda seleccionado el Droppoint #{position}",
		level=State.Defect)
	private boolean checkIsSelectedDroppoint(int position) {
		return modalDroppoints.isDroppointSelected(position);
	}
	
	@Step (
		description="Clickamos el botón de \"Select\" de la capa de Droppoints", 
		expected="Desaparece al capa de droppoint")
	public void clickSelectButton() {
		int seconds = 5;
		modalDroppoints.clickSelectButtonAndWait(seconds);
		new ModalDroppointsSteps().getSecConfirmDatosSteps().validateIsVisible(3);
	}
}
