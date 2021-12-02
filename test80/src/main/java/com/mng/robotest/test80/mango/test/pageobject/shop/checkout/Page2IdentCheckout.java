package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.test80.mango.test.beans.Pais;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.PopupFindAddress;
import com.mng.robotest.test80.mango.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test80.mango.test.utils.awssecrets.GetterSecrets.SecretType;

public class Page2IdentCheckout extends PageObjTM {
	
	private final static String XPathMainForm = "//form[@action[contains(.,'/expressregister')]]";
	private final static String XPathInputPassword = "//input[@id[contains(.,'cfPass')]]";
	private final static String XPathInputNombreUsr = "//input[@id[contains(.,':cfName')]]";
	private final static String XPathInputApellidosUsr = "//input[@id[contains(.,':cfSname')]]";
	private final static String XPathInputMiddleNameUsr = "//input[@id[contains(.,':cfMiddleName')]]";
	private final static String XPathInputTelefono = "//input[@id[contains(.,':cfTelf')]]";
	private final static String XPathInputDireccion1 = "//input[@id[contains(.,':cfDir1')]]";
	private final static String XPathInputDireccion2 = "//input[@id[contains(.,':cfDir2')]]";
	private final static String XPathCheckPublicidad = "//input[@id[contains(.,':cfPubli')] or @id[contains(.,'_cfPubli')]]/..";
	private final static String XPathInputEmail = "//input[@id[contains(.,':cfEmail')]]";
	private final static String XPathInputDNI = "//input[@id[contains(.,':cfDni')]]";
	private final static String XPathInputCodPost = "//input[@id[contains(.,':cfCp')]]";
	private final static String XPathInputProvEstadoActive = "//input[@id[contains(.,':cfState')] and not(@disabled) and not(@readonly)]";
	private final static String XPathInputPoblacionActive = "//input[@id[contains(.,':cfCity')] and not(@disabled) and not(@readonly)]";
	private final static String XPathSelectPais = "//select[@id[contains(.,':pais')]]";
	private final static String XPathSelectProvPais = "//select[@id[contains(.,'provinciaPais')] or @id[contains(.,'nivelProvincia')]]";
	private final static String XPathSelectEstadosPais = "//select[@id[contains(.,'estadosPais')] or @id[contains(.,':nivelCity')]]";
	private final static String XPathSelectLocalidadesProvCity = "//select[@id[contains(.,'localidadesProvCity')] or @id[contains(.,':nivelCityArea')] or @id[contains(.,'nivelLocalidad')]]";
	private final static String XPathSelectDistrito = "//select[@id[contains(.,'nivelDistrito')]]";
	private final static String XPathSelectLocalidadesNeighbourhoodCity = "//select[@id[contains(.,'localidadesNeighbourhoodCity')] or @id[contains(.,'nivelSubdistrito')]]";
	private final static String XPathSelectCodPostal = "//select[@id[contains(.,'nivelCodigoPostal')]]";
	private final static String XPathCheckHombre = "//div[@id[contains(.,':cfGener_H')]]";
	private final static String XPathCheckCondiciones = "//input[@id[contains(.,':cfPriv')]]";
	private final static String XPathBotonFindAddress = "//input[@class[contains(.,'load-button')] and @type='button']";
	private final static String XPathBotonContinuar = "//div[@class='submitContent']/input[@type='submit']";
	private final static String XPathMsgAduanas = "//div[@class='aduanas']";
	private final static String XPathTextRGPD = "//p[@class='gdpr-text gdpr-profiling']";
	
	//Con el substring simulamos un ends-with (que no está disponible en xpath 1.0)
	private static String XPathSelectLocalidades = "//select[substring(@id, string-length(@id) - string-length('localidades') +1) = 'localidades']";

	public Page2IdentCheckout(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPageUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathMainForm)).wait(maxSeconds).check());
	}
	
	public boolean checkEmail(String email) {
		By byEmail = By.xpath(XPathInputEmail);
		if (state(State.Visible, byEmail).check()) {
			String emailScreen = driver.findElement(byEmail).getAttribute("value");
			if (emailScreen!=null) {
				return (email.compareTo(emailScreen)==0);
			}
		}
		return false;
	}

	public boolean isInputPasswordAccordingEmail(boolean emailYetExists) {
		boolean isVisiblePassword = state(Visible, By.xpath(XPathInputPassword)).check();
		if (emailYetExists==isVisiblePassword) {
			return false;
		}
		return true;
	}

	private boolean setInputIfVisible(String xpathInput, String valueToSet) {
		boolean datoSeteado = false;
		try {
			List<WebElement> cfElementList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(xpathInput));
			if (cfElementList.size() > 0) {
				String cfElementReadonly = cfElementList.get(0).getAttribute("readonly");
				if (cfElementReadonly == null || cfElementReadonly.compareTo("true")!=0) {
					if (cfElementList.get(0).getAttribute("value").compareTo(valueToSet) != 0) {
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
	
	public void setNombreUsuarioIfVisible(String nombreUsr, HashMap<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPathInputNombreUsr, nombreUsr);
		if (datoSeteado) {
			datosRegistro.put("cfName", nombreUsr);
		}
	}
	
	public void setApellidosUsuarioIfVisible(String apellidosUsr, HashMap<String,String> dataPago) {
		boolean datoSeteado = setInputIfVisible(XPathInputApellidosUsr, apellidosUsr);
		if (datoSeteado) {
			dataPago.put("cfSname", apellidosUsr);
		}
	}
	
	public void setMiddleNameUsuarioIfVisible(String middleNameUsr, HashMap<String,String> dataPago) {
		boolean datoSeteado = setInputIfVisible(XPathInputMiddleNameUsr, middleNameUsr);
		if (datoSeteado) {
			dataPago.put("cfMiddleName", middleNameUsr);
		}
	}
	
	public void setPasswordIfVisible(String password, HashMap<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPathInputPassword, password);
		if (datoSeteado) {
			datosRegistro.put("cfPass", password);
		}
	}
	
	public void setTelefonoIfVisible(String movil, HashMap<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPathInputTelefono, movil);
		if (datoSeteado) {
			datosRegistro.put("cfTelf", movil);
		}
	}	

	public void setInputPoblacionIfVisible(String cfCity, HashMap<String,String> datosRegistro) throws Exception {
		waitForPageLoaded(driver);
		state(Clickable, By.xpath(XPathInputPoblacionActive)).wait(1).check();
		boolean datoSeteado = setInputIfVisible(XPathInputPoblacionActive, cfCity);
		if (datoSeteado) {
			datosRegistro.put("cfCity", cfCity);
		}
	}

	public void setInputDireccion1IfVisible(String direccion1) {
		setInputIfVisible(XPathInputDireccion1, direccion1);
	}
	
	public void setInputDireccion1IfVisible(String direccion1, HashMap<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPathInputDireccion1, direccion1);
		if (datoSeteado)
			datosRegistro.put("cfDir1", direccion1);
	}	
	
	public void setInputDireccion2IfVisible(String direccion1, HashMap<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPathInputDireccion2, direccion1);
		if (datoSeteado) {
			datosRegistro.put("cfDir2", direccion1);
		}
	}
	
	public void setInputProvEstadoIfVisible(String cfState, HashMap<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPathInputProvEstadoActive, cfState);
		if (datoSeteado) {
			datosRegistro.put("cfState", cfState);
		}
	}	

	public void setInputDniIfVisible(String dni, HashMap<String,String> datosRegistro) {
		boolean datoSeteado = setInputIfVisible(XPathInputDNI, dni);		
		if (datoSeteado) {
			datosRegistro.put("cfDni", dni);
		}
	}

	/**
	 * Se introduce el código postal y si se detectan un 'onkeyup' se espera un máximo de 2 segundos a que esté disponible la lista de poblaciones
	 */
	public boolean setCodPostalIfExistsAndWait(String codPostal) {
		boolean datoSeteado = setInputIfVisible(XPathInputCodPost, codPostal);
		if (datoSeteado) {			
			List<WebElement> cfCodpostalList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathInputCodPost));
			if (cfCodpostalList.size() > 0) {
				//Si existe el tag 'onkeyup' (se desencadena petición Ajax) tenemos que esperaremos un máximo de 2 segundos hasta que aparezca el desplegable con las poblaciones
				if (cfCodpostalList.get(0).getAttribute("onkeyup")!=null && 
					cfCodpostalList.get(0).getAttribute("onkeyup").compareTo("")!=0) {
					state(Visible, By.xpath(XPathSelectLocalidades)).wait(2).check();
				}
			}
		}

		return datoSeteado;
	}

	public void setCodPostalIfExistsAndWait(String codigoPostal, HashMap<String,String> datosRegistro) {
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
		List<WebElement> cfEmailList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathInputEmail));
		if (cfEmailList.size() > 0) {
			//Revisamos si está protegido el campo de input
			String cfMailStatus = cfEmailList.get(0).getAttribute("disabled");
			if (cfMailStatus != null && cfMailStatus.compareTo("true") == 0) {
				emailRegistro = cfEmailList.get(0).getAttribute("value");
			} else {
				emailRegistro = email;
				//Revisamos si el input ya está seteado con ese valor
				if (cfEmailList.get(0).getAttribute("value").compareTo(emailRegistro) != 0) {
					cfEmailList.get(0).clear();
					cfEmailList.get(0).sendKeys(emailRegistro);
					cfEmailList.get(0).sendKeys(Keys.TAB);
				}
			}
		}		
		
		return emailRegistro;
	}
	
	public void setEmailIfExists(String email, HashMap<String,String> datosRegistro) {
		datosRegistro.put("cfEmail", setEmailIfExists(email));
	}

	public boolean setPaisIfVisibleAndNotSelected(String codigoPais) {
		boolean datoSeteado = false;
		List<WebElement> paisCf = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathSelectPais));
		if (paisCf.size() > 0) {
			String xpathSelectedPais = XPathSelectPais + "/option[@selected='selected' and @value='" + codigoPais + "']";
			if (state(Present, By.xpath(xpathSelectedPais)).check()) {
				new Select(paisCf.get(0)).selectByValue(codigoPais);
				datoSeteado = true;
			}
		}

		return datoSeteado;
	}

	public void setPaisIfVisibleAndNotSelected(String codigoPais, HashMap<String,String> datosRegistro) {
		boolean datoSeteado = setPaisIfVisibleAndNotSelected(codigoPais);
		if (datoSeteado) {
			datosRegistro.put(":pais", codigoPais);
		}
	}	
	
	public void clickBotonFindAddress() throws Exception {
		driver.findElement(By.xpath(XPathBotonFindAddress)).click();
		Thread.sleep(3000);
	}

	/**
	 * Si existe, utiliza el botón "Find Address" para establecer la dirección (actualmente sólo existe en Corea del Sur)
	 */
	public void setDireccionWithFindAddressIfExists(String codPostalPais) throws Exception {
		String codPostalSeteado = getCodigoPostal();
		if (codPostalPais.compareTo(codPostalSeteado)!=0 &&
			state(Visible, By.xpath(XPathBotonFindAddress)).check()) {
			clickBotonFindAddress();
			String mainWindowHandle = driver.getWindowHandle();
			try {
				String popupBuscador = PopupFindAddress.goToPopupAndWait(mainWindowHandle, 5, driver);
				if ("".compareTo(popupBuscador)!=0 && PopupFindAddress.isIFrameUntil(0, driver)) {
					PopupFindAddress.switchToIFrame(driver);
					if (PopupFindAddress.isBuscadorClickableUntil(2/*maxSecondsToWait*/, driver)) {
						PopupFindAddress.setDataBuscador(driver, codPostalPais);
						PopupFindAddress.clickButtonLupa(driver);
						PopupFindAddress.clickFirstDirecc(driver);
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
		if (state(Present, By.xpath(XPathInputCodPost)).check()) {
			return (driver.findElement(By.xpath(XPathInputCodPost)).getAttribute("value"));
		}
		return "";
	}

	public void clickPublicidadIfVisible(HashMap<String,String> datosRegistro) {
		By byCheckPublic = By.xpath(XPathCheckPublicidad);
		if (state(Present, byCheckPublic).check()) {
			moveToElement(byCheckPublic, driver);
			if (state(Visible, byCheckPublic, driver).check()) {
				driver.findElement(byCheckPublic).click();
				datosRegistro.put("cfPubli", "true");
				return;
			}
		}
		datosRegistro.put("cfPubli", "false");
	}

	/**
	 * @param posInSelect: elemento del desplegable que queremos desplegar (comenzando desde el 1)
	 */
	public String setSelectLocalidadesIfVisible(int posInSelect) throws Exception {
		String datoSeteado = "";
		boolean staleElement = true;
		int i=0;
		Thread.sleep(500);
		
		//Tenemos problemas aleatorios de StaleElementReferenceException con este elemento
		//Probamos hasta 3 veces mientras que obtengamos la Excepción
		while (staleElement && i<3 && "".compareTo(datoSeteado)==0) {
			List<WebElement> localidadesList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathSelectLocalidades));
			if (localidadesList.size() > 0) {
				try {
					new Select(localidadesList.get(0)).selectByIndex(posInSelect);
					datoSeteado = localidadesList.get(0).getAttribute("value");
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
	
	public void setSelectLocalidadesIfVisible(int posInSelect, HashMap<String,String> datosRegistro) throws Exception {
		String datoSeteado = setSelectLocalidadesIfVisible(posInSelect);
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("cfCity", datoSeteado);
		}
	}
	
	/**
	 * @param posInSelect: elemento del desplegable que queremos desplegar (comenzando desde el 1)
	 */
	private final static String firstProvinciaUkranie = "Ananivskyi";
	private final static String XPathOptionFirstProvUkranie = "//div[@class[contains(.,'choices')] and text()[contains(.,'" + firstProvinciaUkranie + "')]]";
	public String setSelectProv1PaisIfVisible(String codCountry, Channel channel) {
		String datoSeteado = "";
		WebElement provinciaPais = UtilsMangoTest.findElementPriorizingDisplayed(driver, By.xpath(XPathSelectProvPais));
		if (provinciaPais!=null) {
			if (codCountry.compareTo(PaisShop.Ukraine.getCodigoPais())==0 &&
				channel==Channel.desktop) {
				driver.findElement(By.xpath(XPathSelectProvPais + "/..")).click();
				driver.findElement(By.xpath(XPathOptionFirstProvUkranie)).click();
				return firstProvinciaUkranie;
			} else {
				new Select(provinciaPais).selectByIndex(1);
				datoSeteado = provinciaPais.getAttribute("value");
				return datoSeteado;
			}
		}	  
		
		return "";
	}
	
	public void setSelectProvPaisIfVisible(HashMap<String,String> datosRegistro, String codPais, Channel channel) {
		String datoSeteado = setSelectProv1PaisIfVisible(codPais, channel);
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("provinciaPais", datoSeteado);
		}
	}   
	
	public String setSelectEstados1PaisIfVisible() throws Exception {
		String datoSeteado = "";
		
		//Tenemos problemas aleatorios de StaleElementReferenceException con este elemento
		//Probamos hasta 3 veces mientras que obtengamos la Excepción
		boolean staleElement = true;
		int i=0;
		while (staleElement && i<3) {
			List<WebElement> estadosPaisList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathSelectEstadosPais));
			if (estadosPaisList.size() > 0) {
				try {
					Select select = new Select(estadosPaisList.get(0));
					select.selectByIndex(1);
					datoSeteado = select.getFirstSelectedOption().getText();
					//datoSeteado = estadosPaisList.get(0).getAttribute("value");
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
	
	public void setSelectEstadosPaisIfVisible(HashMap<String,String> datosRegistro, String codPais) throws Exception {
	   	String datoSeteado = "";
		if ("001".compareTo(codPais)==0) {
			datoSeteado = setSeletEstadoEspanya("Barcelona");
		} else {
			datoSeteado = setSelectEstados1PaisIfVisible();
		}
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("estadosPais", datoSeteado);
		}
	}	
	
	public String setSeletEstadoEspanya(String provincia) throws Exception {
		waitForPageLoaded(driver);
		WebElement provinciaPais = UtilsMangoTest.findElementPriorizingDisplayed(driver, By.xpath(XPathSelectEstadosPais));
		if (provinciaPais!=null) {
			String selected = new Select(provinciaPais).getFirstSelectedOption().getText();
			if (selected.compareTo(provincia)!=0) {
				new Select(provinciaPais).selectByVisibleText(provincia);
			}
			return provincia;
		}	  
		return "";
	}
	
	private enum TypeLocalidad {ProvCity, Distrito, CodPostal, NeighbourhoodCity}
	private String setSelectLocalidadesProvCity(int posInSelect) throws Exception {
		return (setSelectLocalidades(TypeLocalidad.ProvCity, posInSelect));
	}
	private String setSelectLocalidadesNeighbourhoodCity(int posInSelect) throws Exception {
		return (setSelectLocalidades(TypeLocalidad.NeighbourhoodCity, posInSelect));
	}
	private String setSelectDistrito(int posInSelect) throws Exception {
		return (setSelectLocalidades(TypeLocalidad.Distrito, posInSelect));
	}
	private String setSelectCodPostal(int posInSelect) throws Exception {
		return (setSelectLocalidades(TypeLocalidad.CodPostal, posInSelect));
	}
	private String setSelectLocalidades(TypeLocalidad typeLocalidad, int posInSelect) throws Exception {
		String datoSeteado = "";
		String xpathSelect = "";
		switch (typeLocalidad) {
		case ProvCity:
			xpathSelect = XPathSelectLocalidadesProvCity;
			break;
		case Distrito:
			xpathSelect = XPathSelectDistrito;
			break;
		case CodPostal:
			xpathSelect = XPathSelectCodPostal;
			break;
		case NeighbourhoodCity:
			xpathSelect = XPathSelectLocalidadesNeighbourhoodCity;
			break;
		}

		//Tenemos problemas aleatorios de StaleElementReferenceException con este elemento
		//Probamos hasta 3 veces mientras que obtengamos la Excepción
		boolean staleElement = true;
		int i=0;
		while (staleElement && i<3) {
			List<WebElement> localidadesList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(xpathSelect));
			if (localidadesList.size() > 0) {
				try {
					new Select(localidadesList.get(0)).selectByIndex(posInSelect);
					datoSeteado = localidadesList.get(0).getAttribute("value");
					staleElement = false;
				}
				catch (StaleElementReferenceException e) {
					Thread.sleep(1000);
					Log4jTM.getLogger().warn("Exception selecting localidad from select. ", e);
				}
				catch (NoSuchElementException e) {
					Thread.sleep(1000);
					Log4jTM.getLogger().warn("Exception selecting localidad from select. ", e);
				}
			}
			i+=1;
		}
		
		return datoSeteado;
	}
	
	public void setSelectLocalidadesProvCity(int posInSelect, HashMap<String,String> datosRegistro) throws Exception {
		String datoSeteado = setSelectLocalidadesProvCity(posInSelect);
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("localidadesProvCity", datoSeteado);
		}
	}	 
	public void setSelectDistrito(int posInSelect, HashMap<String,String> datosRegistro) throws Exception {
		String datoSeteado = setSelectDistrito(posInSelect);
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("distrito", datoSeteado);
		}
	}
	public void setSelectCodPostal(int posInSelect, HashMap<String,String> datosRegistro) throws Exception {
		String datoSeteado = setSelectCodPostal(posInSelect);
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("selectCodPosta", datoSeteado);
		}
	}
	public void setSelectLocalidadesNeighbourhoodCity(int posInSelect, HashMap<String,String> datosRegistro) throws Exception {
		String datoSeteado = setSelectLocalidadesNeighbourhoodCity(posInSelect);
		if ("".compareTo(datoSeteado)!=0) {
			datosRegistro.put("localidadesNeighbourhoodCity", datoSeteado);
		}
	}
	
	public boolean setCheckHombreIfVisible() {
		boolean datoSeteado = false;
		List<WebElement> cfGener_HList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathCheckHombre));
		if (cfGener_HList.size() > 0) {
			cfGener_HList.get(0).click();
		}		
		
		return datoSeteado;		
	}
	
	public void setCheckHombreIfVisible(HashMap<String,String> datosRegistro) {
		boolean datoSeteado = setCheckHombreIfVisible();
		if (datoSeteado) {
			datosRegistro.put("cfGener", "H");
		}
	}	
	
	public boolean setCheckCondicionesIfVisible() {
		boolean datoSeteado = false;
		List<WebElement> cfPriv = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathCheckCondiciones + "/../../div[@class='checkbox__image']"));
		if (cfPriv.size() > 0) { //Revisamos si el check NO está marcado 
			driver.findElement(By.xpath(XPathCheckCondiciones)).click();
			datoSeteado = true;
		}		
		return datoSeteado;		
	}
	
	public void setCheckCondicionesIfVisible(HashMap<String,String> datosRegistro) {
		boolean datoSeteado = setCheckCondicionesIfVisible();
		if (datoSeteado) {
			datosRegistro.put("cfPriv", "true");
		}
	}
	
	/**
	 * Función que introduce los datos de cliente (sirve para la 1a página del registro y el checkout)
	 */
	public HashMap<String,String> inputDataPorDefectoSegunPais(
			Pais pais, String emailUsr, boolean testCharNoLatinos, boolean clickPubli, Channel channel) throws Exception {
		HashMap<String,String> datosSeteados = new HashMap<>();
		String nombreUsr = "Jorge";
		String apellidosUsr = "Muñoz Martínez";
		String middleNameUsr = "Sputnik";
		String codigoPais = pais.getCodigo_pais();
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
		for (int i = 0; i < 2; i++) {
			setNombreUsuarioIfVisible(nombreUsr, datosSeteados);
			setApellidosUsuarioIfVisible(apellidosUsr, datosSeteados);
			setMiddleNameUsuarioIfVisible(middleNameUsr, datosSeteados);
			setTelefonoIfVisible(movil, datosSeteados);
			setPasswordIfVisible(passStandard, datosSeteados);
			setEmailIfExists(emailUsr, datosSeteados);
			setInputDireccion1IfVisible(direccion1, datosSeteados);
			setInputDireccion2IfVisible(direccion2, datosSeteados);
			setDireccionWithFindAddressIfExists(codPostalPais);
			setPaisIfVisibleAndNotSelected(codigoPais, datosSeteados);
			setCodPostalIfExistsAndWait(codPostalPais, datosSeteados);
			setInputPoblacionIfVisible(cfCity, datosSeteados);
			setSelectLocalidadesIfVisible(1, datosSeteados);
			setSelectProvPaisIfVisible(datosSeteados, pais.getCodigo_pais(), channel); // Desplegable provincia país (p.e. Turquía)
			setCheckCondicionesIfVisible(datosSeteados); // Selección aceptación de condiciones (actualmente sólo en Turquía)
			setSelectLocalidadesProvCity(1, datosSeteados); // Desplegable específico de Turquía
			setSelectDistrito(1, datosSeteados);
			setSelectCodPostal(1, datosSeteados);
			setSelectLocalidadesNeighbourhoodCity(1, datosSeteados); // Desplegable específico de Turquía
			setInputProvEstadoIfVisible(cfState, datosSeteados);
			setInputDniIfVisible(dni, datosSeteados);
			setSelectEstadosPaisIfVisible(datosSeteados, codigoPais);
			if (i==0 && clickPubli) {
				clickPublicidadIfVisible(datosSeteados);
				setCheckHombreIfVisible(datosSeteados);
			}
		}
		
		return datosSeteados;
	}

	public boolean isContinuarClickableUntil(int maxSeconds) {
		return (state(Clickable, By.xpath(XPathBotonContinuar)).wait(maxSeconds).check());
	}

	public void clickBotonContinuarAndWait(int maxSeconds) {
		click(By.xpath(XPathBotonContinuar)).waitLoadPage(maxSeconds).exec();
 
		//Hay una especie de bug (p.e. en el caso de Turquía) que hace que en ocasiones el click no tenga efecto
		if (state(Present, By.xpath(XPathBotonContinuar)).check()) {
			waitMillis(1500);
			click(By.xpath(XPathBotonContinuar)).type(TypeClick.javascript).exec();
		}
	}

	public boolean isDisplayedAvisoAduanas() {
		return (state(Visible, By.xpath(XPathMsgAduanas)).wait(1).check());
	}

	public boolean isTextoRGPDVisible() {
		return (state(Visible, By.xpath(XPathTextRGPD)).check());
	}
}