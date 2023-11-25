package com.mng.robotest.tests.domains.compra.pageobjects.envio;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.compra.steps.envio.DataSearchDeliveryPoint.DataSearchDp.*;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compra.steps.envio.DataDeliveryPoint;
import com.mng.robotest.tests.domains.compra.steps.envio.DataSearchDeliveryPoint;


public class SecSelectDPoint extends PageBase {

	public enum TypeDeliveryPoint { TIENDA, DROPPOINT, ANY }

	private static final String XP_SEARCH_BOX = "//div[@class[contains(.,'searchBox')]]";
	private static final String XP_INPUT_PROVINCIA = XP_SEARCH_BOX + "//input[@class[contains(.,'searchValue')]]";
	private static final String XP_SELECT_PROVINCIA = XP_SEARCH_BOX + "//select[@class[contains(.,'id_city')]]";
	private static final String XP_CONTAINER_LIST = "//div[@class='list__container' or @class[contains(.,'list-container')]]"; //Desktop or Movil
	private static final String XP_DELIVERY_POINT = XP_CONTAINER_LIST + "//div[@data-shopid]";
	private static final String XP_DELIVERY_POINT_SELECTED = XP_DELIVERY_POINT.replace("@data-shopid", "@data-shopid and @class[contains(.,'selected')]");
	private static final String XP_NAME_DPOINT = "//p[@class='dp__title' or @class='dp-title']";   
	private static final String XP_ADDRESS_DPOINT = "//p[@class='dp__address' or @class='dp-address']";
	private static final String XP_POSTAL_CODE_DPOINT = "//p[@class='dp__postalcode' or @class='dp-postal-code']";
	private static final String XP_COLECCIONES_DPOINT = "//p[@class='dp__brands' or @class='dp-brands']";
	private static final String XP_TELEFONO_DPOINT = "//p[@class='dp__phone' or @class='dp-phone']";  
	private static final String XP_SELECCIONAR_BUTTON = XP_CONTAINER_LIST + "//span[@id[contains(.,'selectButton')]]";

	public void sendProvincia(String provincia) {
		if (isInputProvinciaVisible()) {
			sendProvinciaToInput(provincia);
		} else {
			sendProvinciaToSelect(provincia);
		}
	}

	private boolean isInputProvinciaVisible() {
		return state(Visible, XP_INPUT_PROVINCIA).check();
	}
 
	private void sendProvinciaToInput(String provincia) {
		getElement(XP_INPUT_PROVINCIA).clear();
		getElement(XP_INPUT_PROVINCIA).sendKeys(provincia);
		getElement(XP_INPUT_PROVINCIA).sendKeys(Keys.RETURN);
	}

	private void sendProvinciaToSelect(String provincia) {
		new Select(getElement(XP_SELECT_PROVINCIA)).selectByVisibleText(provincia);
	}

	public List<WebElement> getListDeliveryPoints() {
		return getElements(XP_DELIVERY_POINT);
	}

	public WebElement getDeliveryPointSelected() {
		return getElement(XP_DELIVERY_POINT_SELECTED);
	}

	public boolean isDroppointSelected(int position) {
		List<WebElement> listDp = getListDeliveryPoints();
		WebElement dpElem = listDp.get(position-1);
		return (dpElem.getAttribute("class")!=null && dpElem.getAttribute("class").contains("selected"));
	}

	public boolean isDroppointVisibleUntil(int position, int seconds) {
		String xpathDeliveryPoint = "(" + XP_DELIVERY_POINT + ")[" + position + "]";  
		return state(Visible, xpathDeliveryPoint).wait(seconds).check(); 
	}

	public TypeDeliveryPoint getTypeDeliveryPoint(int position) {
		List<WebElement> listDp = getListDeliveryPoints();
		if (listDp!=null && listDp.size()>=position) {
			WebElement dpElem = listDp.get(position-1);
			return getTypeDeliveryPoint(dpElem);
		}
		return null;
	}

	public TypeDeliveryPoint getTypeDeliveryPoint(WebElement dpElement) {
		WebElement dpTitle = getElementVisible(dpElement, By.xpath(XP_NAME_DPOINT));
		if (dpTitle!=null && !dpTitle.getText().toLowerCase().contains("mango")) {
			return TypeDeliveryPoint.DROPPOINT;
		}
		return TypeDeliveryPoint.TIENDA;
	}

	public DataDeliveryPoint clickDeliveryPointAndGetData(int position) {
		List<WebElement> listDp = getListDeliveryPoints();
		listDp.get(position-1).click();
		return getDataDeliveryPointSelected();
	}

	public DataDeliveryPoint getDataDeliveryPointSelected() {
		var dataDp = new DataDeliveryPoint();
		waitLoadPage(); //For avoid StaleElementReferenceException
		WebElement dpSelected = getDeliveryPointSelected();
		dataDp.setTypeDeliveryPoint(getTypeDeliveryPoint(dpSelected));
		dataDp.setCodigo(dpSelected.getAttribute("data-shopid"));

		//Recuperamos los datos del DeliveryPoint mirando de evitar excepciones de "StaleElement"
		String xpathDpoint = XP_DELIVERY_POINT_SELECTED + XP_NAME_DPOINT;
		if (state(Present, xpathDpoint).check()) {
			dataDp.setName(getElement(xpathDpoint).getText());
		}
		
		String xpathColDpoint = XP_DELIVERY_POINT_SELECTED + XP_COLECCIONES_DPOINT;
		if (state(Present, xpathColDpoint).check()) {
			dataDp.setColecciones(getElement(xpathColDpoint).getText());			
		}
		
		String xpathAddDpoint = XP_DELIVERY_POINT_SELECTED + XP_ADDRESS_DPOINT;
		if (state(Present, xpathAddDpoint).check()) {
			dataDp.setDireccion(getElement(xpathAddDpoint).getText());
		}
		
		String xpathCodDpoint = XP_DELIVERY_POINT_SELECTED + XP_POSTAL_CODE_DPOINT;
		if (state(Present, xpathCodDpoint).check()) {
			dataDp.setCodPostal(getElement(xpathCodDpoint).getText());
		}
		
		if (state(Present, XP_TELEFONO_DPOINT).check()) {
			dataDp.setTelefono(getElement(XP_TELEFONO_DPOINT).getText());
		}
		return dataDp;
	}

	public void clickSelectButtonAndWait(int seconds) {
		click(XP_SELECCIONAR_BUTTON).waitLoadPage(seconds).exec();
	}

	public boolean deliveryPointSelectedContainsPoblacionUntil(
			DataSearchDeliveryPoint dataSearchDp, int seconds) {
		for (int i=0; i<seconds; i++) {
			try {
				DataDeliveryPoint dataDp = getDataDeliveryPointSelected();
				if (dataSearchDp.getTypeData() == PROVINCIA) {
					String provincia = dataSearchDp.getData();
					String provinciaLow = dataSearchDp.getData().toLowerCase();
					String prov1rstLetterCapital = provinciaLow.substring(0, 1).toUpperCase() + provinciaLow.substring(1);
					if (dataDp.getCPandPoblacion().contains(provincia) || 
						dataDp.getCPandPoblacion().contains(prov1rstLetterCapital)) {
						return true;
					}
				}
				else { 
					if (dataDp.getCodPostal().contains(dataSearchDp.getData())) {
						return true;
					}
				}
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Exception selecting Provincia in droppoints", e);
			}
			waitMillis(1000);
		}
		
		return false;
	}
}
