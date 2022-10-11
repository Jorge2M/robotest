package com.mng.robotest.domains.compra.pageobject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.factoryes.entities.EgyptCity;
import com.mng.robotest.test.pageobject.shop.PopupFindAddress;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class Page2IdentCheckout extends PageBase {

	private final Pais pais = dataTest.getPais();
	private final EgyptCity egyptCity;
	
	private static final String VALUE = "value";
	
	private static final String XPATH_MAIN_FORM = "//form[@action[contains(.,'/expressregister')]]";
	private static final String XPATH_INPUT_PASSWORD = "//input[@id[contains(.,'cfPass')]]";
	private static final String XPATH_INPUT_NOMBRE_USR = "//input[@id[contains(.,':cfName')]]";
	private static final String XPATH_INPUT_APELLIDOS_USR = "//input[@id[contains(.,':cfSname')]]";
	private static final String XPATH_INPUT_MIDDLENAME_USR = "//input[@id[contains(.,':cfMiddleName')]]";
	private static final String XPATH_INPUT_TELEFONO = "//input[@id[contains(.,':cfTelf')]]";
	private static final String XPATH_INPUT_DIRECCION1 = "//input[@id[contains(.,':cfDir1')]]";
	private static final String XPATH_INPUT_DIRECCION2 = "//input[@id[contains(.,':cfDir2')]]";
	private static final String XPATH_CHECK_PUBLICIDAD = "//input[@id[contains(.,':cfPubli')] or @id[contains(.,'_cfPubli')]]/..";
	private static final String XPATH_INPUT_EMAIL = "//input[@id[contains(.,':cfEmail')]]";
	private static final String XPATH_INPUT_DNI = "//input[@id[contains(.,':cfDni')]]";
	private static final String XPATH_INPUT_CODPOST = "//input[@id[contains(.,':cfCp')]]";
	private static final String XPATH_INPUT_PROV_ESTADO_ACTIVE = "//input[@id[contains(.,':cfState')] and not(@disabled) and not(@readonly)]";
	private static final String XPATH_INPUT_POBLACION_ACTIVE = "//input[@id[contains(.,':cfCity')] and not(@disabled) and not(@readonly)]";
	private static final String XPATH_SELECT_PAIS = "//select[@id[contains(.,':pais')]]";
	private static final String XPATH_SELECT_PROV_PAIS = "//select[@id[contains(.,'provinciaPais')] or @id[contains(.,'nivelProvincia')]]";
	private static final String XPATH_SELECT_ESTADOS_PAIS = "//select[@id[contains(.,'estadosPais')] or @id[contains(.,':nivelCity')]]";
	private static final String XPATH_SELECT_LOCALIDADES_PROVCITY = "//select[@id[contains(.,'localidadesProvCity')] or @id[contains(.,':nivelCityArea')] or @id[contains(.,'nivelLocalidad')]]";
	private static final String XPATH_SELECT_DISTRITO = "//select[@id[contains(.,'nivelDistrito')]]";
	private static final String XPATH_SELECT_LOCALIDADES_NEIGHBOURHOODCITY = "//select[@id[contains(.,'localidadesNeighbourhoodCity')] or @id[contains(.,'nivelSubdistrito')]]";
	private static final String XPATH_SELECT_CODPOSTAL = "//select[@id[contains(.,'nivelCodigoPostal')]]";
	private static final String XPATH_CHECK_HOMBRE = "//div[@id[contains(.,':cfGener_H')]]";
	private static final String XPATH_CHECK_CONDICIONES = "//input[@id[contains(.,':cfPriv')]]";
	private static final String XPATH_BOTON_FIND_ADDRESS = "//input[@class[contains(.,'load-button')] and @type='button']";
	private static final String XPATH_BOTON_CONTINUAR = "//div[@class='submitContent']/input[@type='submit']";
	private static final String XPATH_MSG_ADUANAS = "//div[@class='aduanas']";
	
	//Con el substring simulamos un ends-with (que no está disponible en xpath 1.0)
	private static final String XPATH_SELECT_LOCALIDADES = "//select[substring(@id, string-length(@id) - string-length('localidades') +1) = 'localidades']";

	public Page2IdentCheckout() {
		this.egyptCity = null;
	}
	
	public Page2IdentCheckout(EgyptCity egyptCity) {
		this.egyptCity = egyptCity;
	}
	
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_MAIN_FORM).wait(seconds).check();
	}
	
	public boolean checkEmail(String email) {
		if (state(State.Visible, XPATH_INPUT_EMAIL).check()) {
			String emailScreen = getElement(XPATH_INPUT_EMAIL).getAttribute(VALUE);
			if (emailScreen!=null) {
				return (email.compareTo(emailScreen)==0);
			}
		}
		return false;
	}

	public boolean isInputPasswordAccordingEmail(boolean emailYetExists) {
		boolean isVisiblePassword = state(Visible, XPATH_INPUT_PASSWORD).check();
		return (emailYetExists!=isVisiblePassword);
	}

	private boolean setInputIfVisible(String xpathInput, String valueToSet) {
		boolean datoSeteado = false;
		try {
			List<WebElement> cfElementList = getElementsVisible(xpathInput);			
			if (!cfElementList.isEmpty()) {
				String cfElementReadonly = cfElementList.get(0).getAttribute("readonly");
				if (cfElementReadonly == null || cfElementReadonly.compareTo("true")!=0) {
					if (cfElementList.get(0).getAttribute(VALUE).compareTo(valueToSet) != 0) {
						cfElementList.get(0).clear();
						cfElementList.get(0).sendKeys(valueToSet);
						cfElementList.get(0).sendKeys(Keys.TAB);
					}
					
					datoSeteado = true;
				}	
			}
		}
		catch (org.openqa.selenium.StaleElementReferenceException e) {
			/*
			 * Seguimos pues es posible que el dato esté seteado tal como es preciso 
			 */
		}
		
		return datoSeteado;
	}
	
	public boolean isNombreUsuarioVisible(int seconds) {
		return state(State.Visible, XPATH_INPUT_NOMBRE_USR).wait(seconds).check();
	}
	
	public void setNombreUsuarioIfVisible(String nombreUsr, Map<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPATH_INPUT_NOMBRE_USR, nombreUsr);
		if (datoSeteado) {
			datosRegistro.put("cfName", nombreUsr);
		}
	}
	
	public void setApellidosUsuarioIfVisible(String apellidosUsr, Map<String,String> dataPago) {
		boolean datoSeteado = setInputIfVisible(XPATH_INPUT_APELLIDOS_USR, apellidosUsr);
		if (datoSeteado) {
			dataPago.put("cfSname", apellidosUsr);
		}
	}
	
	public void setMiddleNameUsuarioIfVisible(String middleNameUsr, Map<String,String> dataPago) {
		boolean datoSeteado = setInputIfVisible(XPATH_INPUT_MIDDLENAME_USR, middleNameUsr);
		if (datoSeteado) {
			dataPago.put("cfMiddleName", middleNameUsr);
		}
	}
	
	public void setPasswordIfVisible(String password, Map<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPATH_INPUT_PASSWORD, password);
		if (datoSeteado) {
			datosRegistro.put("cfPass", password);
		}
	}
	
	public void setTelefonoIfVisible(String movil, Map<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPATH_INPUT_TELEFONO, movil);
		if (datoSeteado) {
			datosRegistro.put("cfTelf", movil);
		}
	}	

	public void setInputPoblacionIfVisible(String cfCity, Map<String,String> datosRegistro) {
		waitLoadPage();
		state(Clickable, XPATH_INPUT_POBLACION_ACTIVE).wait(1).check();
		boolean datoSeteado = setInputIfVisible(XPATH_INPUT_POBLACION_ACTIVE, cfCity);
		if (datoSeteado) {
			datosRegistro.put("cfCity", cfCity);
		}
	}

	public void setInputDireccion1IfVisible(String direccion1) {
		setInputIfVisible(XPATH_INPUT_DIRECCION1, direccion1);
	}
	
	public void setInputDireccion1IfVisible(String direccion1, Map<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPATH_INPUT_DIRECCION1, direccion1);
		if (datoSeteado)
			datosRegistro.put("cfDir1", direccion1);
	}	
	
	public void setInputDireccion2IfVisible(String direccion1, Map<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPATH_INPUT_DIRECCION2, direccion1);
		if (datoSeteado) {
			datosRegistro.put("cfDir2", direccion1);
		}
	}
	
	public void setInputProvEstadoIfVisible(String cfState, Map<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPATH_INPUT_PROV_ESTADO_ACTIVE, cfState);
		if (datoSeteado) {
			datosRegistro.put("cfState", cfState);
		}
	}	

	public void setInputDniIfVisible(String dni, Map<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPATH_INPUT_DNI, dni);		
		if (datoSeteado) {
			datosRegistro.put("cfDni", dni);
		}
	}

	/**
	 * Se introduce el código postal y si se detectan un 'onkeyup' se espera un máximo de 2 segundos a que esté disponible la lista de poblaciones
	 */
	public boolean setCodPostalIfExistsAndWait(String codPostal) {
		boolean datoSeteado = setInputIfVisible(XPATH_INPUT_CODPOST, codPostal);
		if (datoSeteado) {			
			List<WebElement> cfCodpostalList = getElementsVisible(XPATH_INPUT_CODPOST);
			if (!cfCodpostalList.isEmpty() &&
				//Si existe el tag 'onkeyup' (se desencadena petición Ajax) tenemos que esperaremos un máximo de 2 segundos hasta que aparezca el desplegable con las poblaciones
				cfCodpostalList.get(0).getAttribute("onkeyup")!=null && 
				cfCodpostalList.get(0).getAttribute("onkeyup").compareTo("")!=0) {
					state(Visible, XPATH_SELECT_LOCALIDADES).wait(2).check();
			}
		}
		return datoSeteado;
	}

	public void setCodPostalIfExistsAndWait(String codigoPostal, Map<String,String> datosRegistro) {
		boolean datoSeteado = setCodPostalIfExistsAndWait(codigoPostal);
		if (datoSeteado) {
			datosRegistro.put("cfCp", codigoPostal);
		}
	}	
	
	/**
	 * Seteamos el email (si el campo de input existe, no está protegido y no está ya informado con ese valor)
	 */
	public String setEmailIfExists(String email) {
		String emailRegistro = email;
		
		// Revisamos si está visible el campo de input
		List<WebElement> cfEmailList = getElementsVisible(XPATH_INPUT_EMAIL);
		if (!cfEmailList.isEmpty()) {
			//Revisamos si está protegido el campo de input
			String cfMailStatus = cfEmailList.get(0).getAttribute("disabled");
			if (cfMailStatus != null && cfMailStatus.compareTo("true") == 0) {
				emailRegistro = cfEmailList.get(0).getAttribute(VALUE);
			} else {
				emailRegistro = email;
				//Revisamos si el input ya está seteado con ese valor
				if (cfEmailList.get(0).getAttribute(VALUE).compareTo(emailRegistro) != 0) {
					cfEmailList.get(0).clear();
					cfEmailList.get(0).sendKeys(emailRegistro);
					cfEmailList.get(0).sendKeys(Keys.TAB);
				}
			}
		}		
		
		return emailRegistro;
	}
	
	public void setEmailIfExists(String email, Map<String,String> datosRegistro) {
		datosRegistro.put("cfEmail", setEmailIfExists(email));
	}

	public boolean setPaisIfVisibleAndNotSelected() {
		boolean datoSeteado = false;
		List<WebElement> paisCf = getElementsVisible(XPATH_SELECT_PAIS);
		if (!paisCf.isEmpty()) {
			String xpathSelectedPais = XPATH_SELECT_PAIS + "/option[@selected='selected' and @value='" + pais.getAddress() + "']";
			if (state(Present, xpathSelectedPais).check()) {
				new Select(paisCf.get(0)).selectByValue(pais.getCodigo_pais());
				datoSeteado = true;
			}
		}

		return datoSeteado;
	}

	public void setPaisIfVisibleAndNotSelected(Map<String,String> datosRegistro) {
		boolean datoSeteado = setPaisIfVisibleAndNotSelected();
		if (datoSeteado) {
			datosRegistro.put(":pais", pais.getCodigo_pais());
		}
	}	
	
	public void clickBotonFindAddress() {
		getElement(XPATH_BOTON_FIND_ADDRESS).click();
		waitMillis(3000);
	}

	/**
	 * Si existe, utiliza el botón "Find Address" para establecer la dirección (actualmente sólo existe en Corea del Sur)
	 */
	public void setDireccionWithFindAddressIfExists() throws Exception {
		String codPostalSeteado = getCodigoPostal();
		if (pais.getCodpos().compareTo(codPostalSeteado)!=0 &&
			state(Visible, By.xpath(XPATH_BOTON_FIND_ADDRESS)).check()) {
			clickBotonFindAddress();
			String mainWindowHandle = driver.getWindowHandle();
			try {
				PopupFindAddress popupFindAddress = new PopupFindAddress();
				String popupBuscador = popupFindAddress.goToPopupAndWait(mainWindowHandle, 5);
				if ("".compareTo(popupBuscador)!=0 && popupFindAddress.isIFrameUntil(0)) {
					popupFindAddress.switchToIFrame();
					if (popupFindAddress.isBuscadorClickableUntil(2)) {
						popupFindAddress.setDataBuscador(pais.getCodpos());
						popupFindAddress.clickButtonLupa();
						popupFindAddress.clickFirstDirecc();
					}
				}
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Exception clicking Find Address button", e);
			}
			finally { driver.switchTo().window(mainWindowHandle); }
		}
	}

	public String getCodigoPostal() {
		if (state(Present, XPATH_INPUT_CODPOST).check()) {
			return getElement(XPATH_INPUT_CODPOST).getAttribute(VALUE);
		}
		return "";
	}

	public void clickPublicidadIfVisible(Map<String,String> datosRegistro) {
		if (state(Present, XPATH_CHECK_PUBLICIDAD).check()) {
			moveToElement(XPATH_CHECK_PUBLICIDAD);
			if (state(Visible, XPATH_CHECK_PUBLICIDAD).check()) {
				getElement(XPATH_CHECK_PUBLICIDAD).click();
				datosRegistro.put("cfPubli", "true");
				return;
			}
		}
		datosRegistro.put("cfPubli", "false");
	}

	/**
	 * @param posInSelect: elemento del desplegable que queremos desplegar (comenzando desde el 1)
	 */
	public String setSelectLocalidadesIfVisible(int posInSelect) throws InterruptedException {
		String datoSeteado = "";
		boolean staleElement = true;
		int i=0;
		waitMillis(500);
		
		//Tenemos problemas aleatorios de StaleElementReferenceException con este elemento
		//Probamos hasta 3 veces mientras que obtengamos la Excepción
		while (staleElement && i<3 && "".compareTo(datoSeteado)==0) {
			List<WebElement> localidadesList = getElementsVisible(XPATH_SELECT_LOCALIDADES);
			if (!localidadesList.isEmpty()) {
				try {
					new Select(localidadesList.get(0)).selectByIndex(posInSelect);
					datoSeteado = localidadesList.get(0).getAttribute(VALUE);
					staleElement = false;
				}
				catch (StaleElementReferenceException e) {
					Thread.sleep(500);
					Log4jTM.getLogger().warn("Exception setting localidad from select", e);
				}
			}
			
			i+=1;
		}

		return datoSeteado;
	}
	
	public void setSelectLocalidadesIfVisible(int posInSelect, Map<String,String> datosRegistro) throws Exception {
		String datoSeteado = setSelectLocalidadesIfVisible(posInSelect);
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("cfCity", datoSeteado);
		}
	}
	
	/**
	 * @param posInSelect: elemento del desplegable que queremos desplegar (comenzando desde el 1)
	 */
	private static final String FIRST_PROVINCIA_UKRANIE = "Ananivskyi";
	private static final String XPATH_OPTION_FIRST_PROV_UKRANIE = "//div[@class[contains(.,'choices')] and text()[contains(.,'" + FIRST_PROVINCIA_UKRANIE + "')]]";
	
	public String setSelectProv1PaisIfVisible(Channel channel) {
		String datoSeteado = "";
		WebElement provinciaPais = getElementPriorizingDisplayed(XPATH_SELECT_PROV_PAIS);
		if (provinciaPais!=null) {
			switch (PaisShop.getPais(pais)) {
			case UKRAINE:
				if (channel==Channel.desktop) {
					return selectProvinciaUkraineDesktop();
				}
				break;
			case EGYPT:
				if (egyptCity!=null) {
				    return selectProvinciaEgyptCity(provinciaPais);
				}
				break;
			default:
				new Select(provinciaPais).selectByIndex(1);
				datoSeteado = provinciaPais.getAttribute(VALUE);
				return datoSeteado;
			}
		}	  
		return "";
	}
	
	private String selectProvinciaEgyptCity(WebElement provinciaPais) {
		new Select(provinciaPais).selectByVisibleText(egyptCity.getState());
		return egyptCity.getState();
	}
	
	private String selectProvinciaUkraineDesktop() {
		getElement(XPATH_SELECT_PROV_PAIS + "/..").click();
		getElement(XPATH_OPTION_FIRST_PROV_UKRANIE).click();
		return FIRST_PROVINCIA_UKRANIE;
	}
	
	public void setSelectProvPaisIfVisible(Map<String,String> datosRegistro, Channel channel) {
		String datoSeteado = setSelectProv1PaisIfVisible(channel);
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("provinciaPais", datoSeteado);
		}
	}   
	
	public String setSelectEstados1PaisIfVisible() throws InterruptedException {
		String datoSeteado = "";
		
		//Tenemos problemas aleatorios de StaleElementReferenceException con este elemento
		//Probamos hasta 3 veces mientras que obtengamos la Excepción
		boolean staleElement = true;
		int i=0;
		while (staleElement && i<3) {
			List<WebElement> estadosPaisList = getElementsVisible(XPATH_SELECT_ESTADOS_PAIS);
			if (!estadosPaisList.isEmpty()) {
				try {
					Select select = new Select(estadosPaisList.get(0));
					select.selectByIndex(1);
					datoSeteado = select.getFirstSelectedOption().getText();
					staleElement = false;
				}
				catch (StaleElementReferenceException e) {
					Thread.sleep(500);
					Log4jTM.getLogger().warn("Exception selecting Estados from select", e);
				}
			}
			i+=1;
		}
		
		return datoSeteado;
	}
	
	public void setSelectEstadosPaisIfVisible(Map<String,String> datosRegistro) throws InterruptedException {
	   	String datoSeteado = "";
		if ("001".compareTo(pais.getCodigo_pais())==0) {
			datoSeteado = setSeletEstadoEspanya("Barcelona");
		} else {
			datoSeteado = setSelectEstados1PaisIfVisible();
		}
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("estadosPais", datoSeteado);
		}
	}	
	
	public String setSeletEstadoEspanya(String provincia) {
		waitLoadPage();
		WebElement provinciaPais = getElementPriorizingDisplayed(XPATH_SELECT_ESTADOS_PAIS);
		if (provinciaPais!=null) {
			String selected = new Select(provinciaPais).getFirstSelectedOption().getText();
			if (selected.compareTo(provincia)!=0) {
				new Select(provinciaPais).selectByVisibleText(provincia);
			}
			return provincia;
		}	  
		return "";
	}
	
	private enum TypeLocalidad { PROV_CITY, DISTRITO, COD_POSTAL, NEIGHBOURHOOD_CITY }
	
	private String setSelectLocalidadesProvCity(int posInSelect) throws Exception {
		return (setSelectLocalidades(TypeLocalidad.PROV_CITY, posInSelect));
	}
	private String setSelectLocalidadesNeighbourhoodCity(int posInSelect) throws Exception {
		return (setSelectLocalidades(TypeLocalidad.NEIGHBOURHOOD_CITY, posInSelect));
	}
	private String setSelectDistrito(int posInSelect) throws Exception {
		return (setSelectLocalidades(TypeLocalidad.DISTRITO, posInSelect));
	}
	private String setSelectCodPostal(int posInSelect) throws Exception {
		return (setSelectLocalidades(TypeLocalidad.COD_POSTAL, posInSelect));
	}
	private String setSelectLocalidades(TypeLocalidad typeLocalidad, int posInSelect) throws Exception {
		String datoSeteado = "";
		String xpathSelect = "";
		switch (typeLocalidad) {
		case PROV_CITY:
			xpathSelect = XPATH_SELECT_LOCALIDADES_PROVCITY;
			break;
		case DISTRITO:
			xpathSelect = XPATH_SELECT_DISTRITO;
			break;
		case COD_POSTAL:
			xpathSelect = XPATH_SELECT_CODPOSTAL;
			break;
		case NEIGHBOURHOOD_CITY:
			xpathSelect = XPATH_SELECT_LOCALIDADES_NEIGHBOURHOODCITY;
			break;
		}

		//Tenemos problemas aleatorios de StaleElementReferenceException con este elemento
		//Probamos hasta 3 veces mientras que obtengamos la Excepción
		boolean staleElement = true;
		int i=0;
		while (staleElement && i<3) {
			List<WebElement> localidadesList = getElementsVisible(xpathSelect);
			if (!localidadesList.isEmpty()) {
				try {
					datoSeteado = selectLocalidad(localidadesList.get(0), posInSelect);
					staleElement = false;
				}
				catch (StaleElementReferenceException | NoSuchElementException e) {
					Thread.sleep(1000);
					Log4jTM.getLogger().warn("Exception selecting localidad from select. ", e);
				}
			}
			i+=1;
		}
		
		return datoSeteado;
	}
	
	private String selectLocalidad(WebElement localidad, int posInSelect) {
		if (PaisShop.getPais(pais)==PaisShop.EGYPT && 
			egyptCity!=null) {
			new Select(localidad).selectByVisibleText(egyptCity.getCity());
			return egyptCity.getCity();
		}

		new Select(localidad).selectByIndex(posInSelect);
		return localidad.getAttribute(VALUE);
	}

	public void setSelectLocalidadesProvCity(int posInSelect, Map<String,String> datosRegistro) throws Exception {
		String datoSeteado = setSelectLocalidadesProvCity(posInSelect);
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("localidadesProvCity", datoSeteado);
		}
	}	 
	public void setSelectDistrito(int posInSelect, Map<String,String> datosRegistro) throws Exception {
		String datoSeteado = setSelectDistrito(posInSelect);
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("distrito", datoSeteado);
		}
	}
	public void setSelectCodPostal(int posInSelect, Map<String,String> datosRegistro) throws Exception {
		String datoSeteado = setSelectCodPostal(posInSelect);
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("selectCodPosta", datoSeteado);
		}
	}
	public void setSelectLocalidadesNeighbourhoodCity(int posInSelect, Map<String,String> datosRegistro) throws Exception {
		String datoSeteado = setSelectLocalidadesNeighbourhoodCity(posInSelect);
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("localidadesNeighbourhoodCity", datoSeteado);
		}
	}
	
	public boolean setCheckHombreIfVisible() {
		boolean datoSeteado = false;
		List<WebElement> cfGenerHList = getElementsVisible(XPATH_CHECK_HOMBRE);
		if (!cfGenerHList.isEmpty()) {
			cfGenerHList.get(0).click();
		}		
		
		return datoSeteado;		
	}
	
	public void setCheckHombreIfVisible(Map<String,String> datosRegistro) {
		boolean datoSeteado = setCheckHombreIfVisible();
		if (datoSeteado) {
			datosRegistro.put("cfGener", "H");
		}
	}	
	
	public boolean setCheckCondicionesIfVisible() {
		boolean datoSeteado = false;
		List<WebElement> cfPriv = getElementsVisible(XPATH_CHECK_CONDICIONES + "/../../div[@class='checkbox__image']");
		if (!cfPriv.isEmpty()) { //Revisamos si el check NO está marcado 
			getElement(XPATH_CHECK_CONDICIONES).click();
			datoSeteado = true;
		}		
		return datoSeteado;		
	}
	
	public void setCheckCondicionesIfVisible(Map<String,String> datosRegistro) {
		boolean datoSeteado = setCheckCondicionesIfVisible();
		if (datoSeteado) {
			datosRegistro.put("cfPriv", "true");
		}
	}
	
	public Map<String,String> inputDataPorDefectoSegunPais(
			String emailUsr, boolean testCharNoLatinos, boolean clickPubli, Channel channel)
					throws Exception {
		
		Map<String,String> datosSeteados = new HashMap<>();
		String nombreUsr = "Jorge";
		String apellidosUsr = "Muñoz Martínez";
		String middleNameUsr = "Sputnik";
		String direccion1 = pais.getAddress();
		if (testCharNoLatinos) {
			direccion1 = pais.getDireccharnolatinos().getText();
		}
		String direccion2 = "6";
		String cfCity = "VILAFRANCA";
		String codPostalPais = pais.getCodpos();
		String cfState = "BARCELONA";		
		String movil = "665015122";
		movil = pais.getTelefono();
		String dni = pais.getDni();
		String passStandard = GetterSecrets.factory().getCredentials(SecretType.SHOP_STANDARD_USER).getPassword();
		
		// Lo repetimos 2 veces porque el sendKeys sufre un bug ocasional que envía los datos a inputs incorrectos
		isNombreUsuarioVisible(3);
		for (int i = 0; i < 2; i++) {
			setNombreUsuarioIfVisible(nombreUsr, datosSeteados);
			setApellidosUsuarioIfVisible(apellidosUsr, datosSeteados);
			setMiddleNameUsuarioIfVisible(middleNameUsr, datosSeteados);
			setTelefonoIfVisible(movil, datosSeteados);
			setPasswordIfVisible(passStandard, datosSeteados);
			setEmailIfExists(emailUsr, datosSeteados);
			setInputDireccion1IfVisible(direccion1, datosSeteados);
			setInputDireccion2IfVisible(direccion2, datosSeteados);
			setDireccionWithFindAddressIfExists();
			setPaisIfVisibleAndNotSelected(datosSeteados);
			setCodPostalIfExistsAndWait(codPostalPais, datosSeteados);
			setInputPoblacionIfVisible(cfCity, datosSeteados);
			setSelectLocalidadesIfVisible(1, datosSeteados);
			setSelectProvPaisIfVisible(datosSeteados, channel); // Desplegable provincia país (p.e. Turquía)
			setCheckCondicionesIfVisible(datosSeteados); // Selección aceptación de condiciones (actualmente sólo en Turquía)
			setSelectLocalidadesProvCity(1, datosSeteados); // Desplegable específico de Turquía
			setSelectDistrito(1, datosSeteados);
			setSelectCodPostal(1, datosSeteados);
			setSelectLocalidadesNeighbourhoodCity(1, datosSeteados); // Desplegable específico de Turquía
			setInputProvEstadoIfVisible(cfState, datosSeteados);
			setInputDniIfVisible(dni, datosSeteados);
			setSelectEstadosPaisIfVisible(datosSeteados);
			if (i==0 && clickPubli) {
				clickPublicidadIfVisible(datosSeteados);
				setCheckHombreIfVisible(datosSeteados);
			}
		}
		
		return datosSeteados;
	}

	public boolean isContinuarClickableUntil(int seconds) {
		return state(Clickable, XPATH_BOTON_CONTINUAR).wait(seconds).check();
	}

	public void clickBotonContinuarAndWait(int seconds) {
		click(XPATH_BOTON_CONTINUAR).waitLoadPage(seconds).exec();
 
		//Hay una especie de bug (p.e. en el caso de Turquía) que hace que en ocasiones el click no tenga efecto
		if (state(Present, XPATH_BOTON_CONTINUAR).check()) {
			waitMillis(1500);
			click(XPATH_BOTON_CONTINUAR).type(TypeClick.javascript).exec();
		}
	}

	public boolean isDisplayedAvisoAduanas() {
		return state(Visible, XPATH_MSG_ADUANAS).wait(1).check();
	}
	
}