package com.mng.robotest.test.pageobject.shop.checkout.envio;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.checkout.envio.SecSelectDPoint.TypeDeliveryPoint;
import com.mng.robotest.test.steps.shop.checkout.envio.DataDeliveryPoint;
import com.mng.robotest.test.steps.shop.checkout.envio.DataSearchDeliveryPoint;

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
			return XPATH_PANEL_GENERAL_DESKTOP;
		default:
		case mobile:
			return XPATH_PANEL_GENERAL_MOVIL;
		}
	}
	
	public boolean isVisible() {
		return isVisibleUntil(0);
	}
	
	public boolean isVisibleUntil(int maxSeconds) {
		String xpathPanelGeneral = getXPathPanelGeneral();
		return (state(Visible, By.xpath(xpathPanelGeneral)).wait(maxSeconds).check());
	}
	
	public boolean isInvisibleUntil(int maxSeconds) {
		String xpathPanelGeneral = getXPathPanelGeneral();
		return (state(Invisible, By.xpath(xpathPanelGeneral)).wait(maxSeconds).check());
	}
	
	public boolean isInvisibleCargandoMsgUntil(int maxSeconds) {
		return (state(Invisible, By.xpath(XPATH_MSG_CARGANDO)).wait(maxSeconds).check());
	}
	
	public boolean isErrorMessageVisibleUntil() {
		return (state(Visible, By.xpath(XPATH_ERROR_MESSAGE)).wait(2).check());
	}
	
	public void sendProvincia(String provincia) {
		secSelectDPoint.sendProvincia(provincia);
	}
	
	public boolean deliveryPointSelectedContainsPoblacionUntil(DataSearchDeliveryPoint dataSearchDp, int maxSecondsToWait) 
			throws Exception {
		return secSelectDPoint.deliveryPointSelectedContainsPoblacionUntil(dataSearchDp, maxSecondsToWait);
	}
	
	public boolean isDroppointVisibleUntil(int position, int maxSeconds) {
		return secSelectDPoint.isDroppointVisibleUntil(position, maxSeconds);
	}
	
	public TypeDeliveryPoint getTypeDeliveryPoint(int maxSeconds) {
		return secSelectDPoint.getTypeDeliveryPoint(maxSeconds);
	}
	
	public DataDeliveryPoint clickDeliveryPointAndGetData(int position) throws Exception {
		return secSelectDPoint.clickDeliveryPointAndGetData(position);
	}
	
	public boolean isDroppointSelected(int position) {
		return secSelectDPoint.isDroppointSelected(position);
	}
	
	public void clickSelectButtonAndWait(int maxSeconds) {
		secSelectDPoint.clickSelectButtonAndWait(maxSeconds);
	}

	public void searchAgainByUserCp(String cp) {
		driver.findElement(By.xpath(XPATH_CP_INPUT_BOX)).clear();
		driver.findElement(By.xpath(XPATH_CP_INPUT_BOX)).sendKeys(cp);
		driver.findElement(By.xpath(XPATH_CP_INPUT_BOX)).sendKeys(Keys.ENTER);
	}

	public SecSelectDPoint getSecSelectDPoint() {
		return secSelectDPoint;
	}

	public SecConfirmDatos getSecConfirmDatos() {
		return secConfirmDatos;
	}
}