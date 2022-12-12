package com.mng.robotest.domains.compra.pageobjects.envio;

import org.openqa.selenium.Keys;

import com.mng.robotest.domains.compra.pageobjects.envio.SecSelectDPoint.TypeDeliveryPoint;
import com.mng.robotest.domains.compra.steps.envio.DataDeliveryPoint;
import com.mng.robotest.domains.compra.steps.envio.DataSearchDeliveryPoint;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalDroppoints extends PageBase {
	
	private final SecSelectDPoint secSelectDPoint = new SecSelectDPoint();
	private final SecConfirmDatos secConfirmDatos = new SecConfirmDatos();
	
	private static final String XPATH_PANEL_GENERAL_DESKTOP = "//span[@id[contains(.,'panelDroppointsGeneral')]]";
	private static final String XPATH_PANEL_GENERAL_MOVIL = "//span[@id[contains(.,'panelDroppointsMenuMobile')]]";
	private static final String XPATH_MSG_CARGANDO = "//div[@class='loading-panel']";
	private static final String XPATH_ERROR_MESSAGE = "//div[@class='errorNotFound']";
	private static final String XPATH_CP_INPUT_BOX = "//div[@class='list__container']//input[@class[contains(.,searchBoxInput)]]";
	
	private String getXPathPanelGeneral() {
		switch (channel) {
		case desktop:
		case tablet:
			return XPATH_PANEL_GENERAL_DESKTOP;
		default:
		case mobile:
			return XPATH_PANEL_GENERAL_MOVIL;
		}
	}
	
	public boolean isVisible() {
		return isVisibleUntil(0);
	}
	
	public boolean isVisibleUntil(int seconds) {
		String xpathPanelGeneral = getXPathPanelGeneral();
		return state(Visible, xpathPanelGeneral).wait(seconds).check();
	}
	
	public boolean isInvisibleUntil(int seconds) {
		String xpathPanelGeneral = getXPathPanelGeneral();
		return state(Invisible, xpathPanelGeneral).wait(seconds).check();
	}
	
	public boolean isInvisibleCargandoMsgUntil(int seconds) {
		return state(Invisible, XPATH_MSG_CARGANDO).wait(seconds).check();
	}
	
	public boolean isErrorMessageVisibleUntil() {
		return state(Visible, XPATH_ERROR_MESSAGE).wait(2).check();
	}
	
	public void sendProvincia(String provincia) {
		secSelectDPoint.sendProvincia(provincia);
	}
	
	public boolean deliveryPointSelectedContainsPoblacionUntil(
			DataSearchDeliveryPoint dataSearchDp, int seconds) {
		return secSelectDPoint.deliveryPointSelectedContainsPoblacionUntil(dataSearchDp, seconds);
	}
	
	public boolean isDroppointVisibleUntil(int position, int seconds) {
		return secSelectDPoint.isDroppointVisibleUntil(position, seconds);
	}
	
	public TypeDeliveryPoint getTypeDeliveryPoint(int seconds) {
		return secSelectDPoint.getTypeDeliveryPoint(seconds);
	}
	
	public DataDeliveryPoint clickDeliveryPointAndGetData(int position) {
		return secSelectDPoint.clickDeliveryPointAndGetData(position);
	}
	
	public boolean isDroppointSelected(int position) {
		return secSelectDPoint.isDroppointSelected(position);
	}
	
	public void clickSelectButtonAndWait(int seconds) {
		secSelectDPoint.clickSelectButtonAndWait(seconds);
	}

	public void searchAgainByUserCp(String cp) {
		getElement(XPATH_CP_INPUT_BOX).clear();
		getElement(XPATH_CP_INPUT_BOX).sendKeys(cp);
		getElement(XPATH_CP_INPUT_BOX).sendKeys(Keys.ENTER);
	}

	public SecSelectDPoint getSecSelectDPoint() {
		return secSelectDPoint;
	}

	public SecConfirmDatos getSecConfirmDatos() {
		return secConfirmDatos;
	}
}