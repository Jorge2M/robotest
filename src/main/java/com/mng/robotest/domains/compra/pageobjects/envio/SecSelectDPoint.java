package com.mng.robotest.domains.compra.pageobjects.envio;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.compra.steps.envio.DataDeliveryPoint;
import com.mng.robotest.domains.compra.steps.envio.DataSearchDeliveryPoint;
import com.mng.robotest.domains.transversal.PageBase;


public class SecSelectDPoint extends PageBase {

	public static enum TypeDeliveryPoint { TIENDA, DROPPOINT, ANY }

	private static final String XPATH_SEARCH_BOX = "//div[@class[contains(.,'searchBox')]]";
	private static final String XPATH_INPUT_PROVINCIA = XPATH_SEARCH_BOX + "//input[@class[contains(.,'searchValue')]]";
	private static final String XPATH_SELECT_PROVINCIA = XPATH_SEARCH_BOX + "//select[@class[contains(.,'id_city')]]";
	private static final String XPATH_CONTAINER_LIST = "//div[@class='list__container' or @class[contains(.,'list-container')]]"; //Desktop or Movil
	private static final String XPATH_DELIVERY_POINT = XPATH_CONTAINER_LIST + "//div[@data-shopid]";
	private static final String XPATH_DELIVERY_POINT_SELECTED = XPATH_DELIVERY_POINT.replace("@data-shopid", "@data-shopid and @class[contains(.,'selected')]");
	private static final String XPATH_NAME_DPOINT = "//p[@class='dp__title' or @class='dp-title']";   
	private static final String XPATH_ADDRESS_DPOINT = "//p[@class='dp__address' or @class='dp-address']";
	private static final String XPATH_POSTAL_CODE_DPOINT = "//p[@class='dp__postalcode' or @class='dp-postal-code']";
	private static final String XPATH_COLECCIONES_DPOINT = "//p[@class='dp__brands' or @class='dp-brands']";
	private static final String XPATH_TELEFONO_DPOINT = "//p[@class='dp__phone' or @class='dp-phone']";  
	private static final String XPATH_SELECCIONAR_BUTTON = XPATH_CONTAINER_LIST + "//span[@id[contains(.,'selectButton')]]";

	public void sendProvincia(String provincia) {
		if (isInputProvinciaVisible()) {
			sendProvinciaToInput(provincia);
		} else {
			sendProvinciaToSelect(provincia);
		}
	}

	private boolean isInputProvinciaVisible() {
		return state(Visible, XPATH_INPUT_PROVINCIA).check();
	}
 
	private void sendProvinciaToInput(String provincia) {
		getElement(XPATH_INPUT_PROVINCIA).clear();
		getElement(XPATH_INPUT_PROVINCIA).sendKeys(provincia);
		getElement(XPATH_INPUT_PROVINCIA).sendKeys(Keys.RETURN);
	}

	private void sendProvinciaToSelect(String provincia) {
		new Select(getElement(XPATH_SELECT_PROVINCIA)).selectByVisibleText(provincia);
	}

	public List<WebElement> getListDeliveryPoints() {
		return getElements(XPATH_DELIVERY_POINT);
	}

	public WebElement getDeliveryPointSelected() {
		return getElement(XPATH_DELIVERY_POINT_SELECTED);
	}

	public boolean isDroppointSelected(int position) {
		List<WebElement> listDp = getListDeliveryPoints();
		WebElement dpElem = listDp.get(position-1);
		return (dpElem.getAttribute("class")!=null && dpElem.getAttribute("class").contains("selected"));
	}

	public boolean isDroppointVisibleUntil(int position, int seconds) {
		String xpathDeliveryPoint = "(" + XPATH_DELIVERY_POINT + ")[" + position + "]";  
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
		WebElement dpTitle = getElementVisible(dpElement, By.xpath(XPATH_NAME_DPOINT));
		if (dpTitle!=null && !dpTitle.getText().toLowerCase().contains("mango")) {
			return TypeDeliveryPoint.DROPPOINT;
		}
		return TypeDeliveryPoint.TIENDA;
	}

	public DataDeliveryPoint clickDeliveryPointAndGetData(int position) throws Exception {
		List<WebElement> listDp = getListDeliveryPoints();
		listDp.get(position-1).click();
		return getDataDeliveryPointSelected();
	}

	public DataDeliveryPoint getDataDeliveryPointSelected() {
		DataDeliveryPoint dataDp = new DataDeliveryPoint();
		waitLoadPage(); //For avoid StaleElementReferenceException
		WebElement dpSelected = getDeliveryPointSelected();
		dataDp.setTypeDeliveryPoint(getTypeDeliveryPoint(dpSelected));
		dataDp.setCodigo(dpSelected.getAttribute("data-shopid"));

		//Recuperamos los datos del DeliveryPoint mirando de evitar excepciones de "StaleElement"
		String xpathDpoint = XPATH_DELIVERY_POINT_SELECTED + XPATH_NAME_DPOINT;
		if (state(Present, xpathDpoint).check()) {
			dataDp.setName(getElement(xpathDpoint).getText());
		}
		
		String xpathColDpoint = XPATH_DELIVERY_POINT_SELECTED + XPATH_COLECCIONES_DPOINT;
		if (state(Present, xpathColDpoint).check()) {
			dataDp.setColecciones(getElement(xpathColDpoint).getText());			
		}
		
		String xpathAddDpoint = XPATH_DELIVERY_POINT_SELECTED + XPATH_ADDRESS_DPOINT;
		if (state(Present, xpathAddDpoint).check()) {
			dataDp.setDireccion(getElement(xpathAddDpoint).getText());
		}
		
		String xpathCodDpoint = XPATH_DELIVERY_POINT_SELECTED + XPATH_POSTAL_CODE_DPOINT;
		if (state(Present, xpathCodDpoint).check()) {
			dataDp.setCodPostal(getElement(xpathCodDpoint).getText());
		}
		
		if (state(Present, XPATH_TELEFONO_DPOINT).check()) {
			dataDp.setTelefono(getElement(XPATH_TELEFONO_DPOINT).getText());
		}
		return dataDp;
	}

	public void clickSelectButtonAndWait(int seconds) {
		click(XPATH_SELECCIONAR_BUTTON).waitLoadPage(seconds).exec();
	}

	public boolean deliveryPointSelectedContainsPoblacionUntil(DataSearchDeliveryPoint dataSearchDp, int seconds) 
			throws Exception {
		for (int i=0; i<seconds; i++) {
			try {
				DataDeliveryPoint dataDp = getDataDeliveryPointSelected();
				switch (dataSearchDp.typeData) {
				case Provincia:
					String provincia = dataSearchDp.data;
					String provinciaLow = dataSearchDp.data.toLowerCase();
					String prov1rstLetterCapital = provinciaLow.substring(0, 1).toUpperCase() + provinciaLow.substring(1);
					if (dataDp.getCPandPoblacion().contains(provincia) || 
						dataDp.getCPandPoblacion().contains(prov1rstLetterCapital)) {
						return true;
					}
					break;
				case CodigoPostal:
					if (dataDp.getCodPostal().contains(dataSearchDp.data)) {
						return true;
					}
					break;
				}
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Exception selecting Provincia in droppoints", e);
			}
			Thread.sleep(1000);
		}
		
		return false;
	}
}
