package com.mng.robotest.test80.mango.test.appshop;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.mng.robotest.test80.mango.test.stpv.shop.SecCabeceraStpV;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.test.data.Constantes.ThreeState;
import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.DataMango;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.DataNino;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataNinos;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.DataNino.sexoType;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro.DataRegType;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro.PageData;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMiCuentaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.modales.ModalSuscripcionStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroDirecStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroFinStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroIniStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroNinosStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroSegundaStpV;
import com.mng.robotest.test80.mango.test.suites.RegistrosSuite.VersionRegistroSuite;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

public class Registro implements Serializable {
    
	private static final long serialVersionUID = 9220128375933995114L;
	
	private final static Pais españa = PaisGetter.get(PaisShop.España);
    private final static IdiomaPais castellano = españa.getListIdiomas().get(0);
    
    private String index_fact = "";
    public int prioridad;
    private Pais paisFactory = null;
    private IdiomaPais idiomaFactory = null;
    
    //Si añadimos un constructor para el @Factory hemos de añadir este constructor para la invocación desde SmokeTest
    public Registro() {}
    
    //Constructor para invocación desde @Factory
    public Registro(Pais pais, IdiomaPais idioma, int prioridad) {
        this.paisFactory = pais;
        this.idiomaFactory = idioma;
        this.index_fact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
        this.prioridad = prioridad;
    }
    
    private DataCtxShop getCtxShForTest() throws Exception {
    	InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
        dCtxSh.setChannel(inputParamsSuite.getChannel());
        //dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();

        //Si el acceso es normal (no es desde una @Factory) utilizaremos el España/Castellano
        if (this.paisFactory==null) {
            dCtxSh.pais = españa;
            dCtxSh.idioma = castellano;
        } else {
            dCtxSh.pais = paisFactory;
            dCtxSh.idioma = idiomaFactory;
        }
        return dCtxSh;
    }

    @SuppressWarnings("static-access")
    @Test (
        groups={"Registro", "Canal:all_App:all"},
        description="Registro con errores en la introducción de los datos")
    public void REG001_RegistroNOK() throws Exception {
    	DataCtxShop dCtxSh = getCtxShForTest();
    	WebDriver driver = TestMaker.getDriverTestCase();
		TestCaseTM.addNameSufix(this.index_fact);
        dCtxSh.userRegistered = false;
        if (dCtxSh.appE==AppEcom.votf) {
            return;
        }
            
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
        SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
        userMenusStpV.selectRegistrate(dCtxSh);
        
        //Step. Click inicial a Registrate (sin haber introducido ningún dato) -> Aparecerán los correspondientes mensajes de error
        HashMap<String,String> dataRegister = new HashMap<>();        
        PageRegistroIniStpV pageRegistroIniStpV = PageRegistroIniStpV.getNew(driver);
        pageRegistroIniStpV.clickRegistrateButton(dCtxSh.pais, false, dCtxSh.appE, dataRegister);
                    
        //Step. Introducir datos incorrectos y validar mensajes de error
        ListDataRegistro dataKOToSend = new ListDataRegistro();
        dataKOToSend.add(DataRegType.name, "Jorge111", false);
        dataKOToSend.add(DataRegType.apellidos, "Muñoz Martínez333", false);
        dataKOToSend.add(DataRegType.email, "jorge.munoz", false);
        dataKOToSend.add(DataRegType.password, "passsinnumeros", false);
        dataKOToSend.add(DataRegType.telefono, "66501512A", false);
        dataKOToSend.add(DataRegType.codpostal, "0872A", false);
        String dataToSendInHtmlFormat = dataKOToSend.getFormattedHTMLData(PageData.pageInicial);
        pageRegistroIniStpV.sendFixedDataToInputs(dataKOToSend, dataToSendInHtmlFormat);

        //Step. Introducir datos correctos pero usuario ya existente
        ListDataRegistro dataToSend = new ListDataRegistro(); 
        dataToSend.add(DataRegType.name, "Jorge", true);
        dataToSend.add(DataRegType.apellidos, "Muñoz Martínez", true);
        dataToSend.add(DataRegType.email, Constantes.mail_standard, true);
        dataToSend.add(DataRegType.password, "Sirjorge74", true);
        dataToSend.add(DataRegType.telefono, "665015122", true);
        dataToSend.add(DataRegType.codpostal, "08720", true);
        dataToSendInHtmlFormat = dataToSend.getFormattedHTMLData(PageData.pageInicial);
        pageRegistroIniStpV.sendFixedDataToInputs(dataToSend, dataToSendInHtmlFormat);
        pageRegistroIniStpV.clickRegistrateButton(dCtxSh.pais, true, dCtxSh.appE, dataRegister);
    }

    @SuppressWarnings("static-access")
    @Test (
        groups={"Registro", "Canal:desktop,mobile_App:shop,outlet", "SupportsFactoryCountrys"}, alwaysRun=true, 
        description="Alta/Registro de un usuario (seleccionando link de publicidad) y posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
    public void REG002_RegistroOK_publi() throws Exception {
    	DataCtxShop dCtxSh = getCtxShForTest();
    	WebDriver driver = TestMaker.getDriverTestCase();
		TestCaseTM.addNameSufix(this.index_fact);
    	InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
        if (inputParamsSuite.getTypeAccess()==TypeAccess.Bat) {
            return;
        }
        
    	VersionRegistroSuite version = VersionRegistroSuite.V3;
    	if (isAccesFromFactory()) {
    		version = VersionRegistroSuite.valueOf(inputParamsSuite.getVersion());
    	}
    	
    	registro_e_irdeshopping_sipubli(dCtxSh, version, driver);
    }
    
    @SuppressWarnings("static-access")
    @Test (
        groups={"Registro", "Canal:desktop_App:shop,outlet"}, alwaysRun=true, 
        description="Alta/Registro de un usuario (sin seleccionar el link de publicidad)")
    public void REG003_RegistroOK_NoPubli() throws Exception {
		TestCaseTM.addNameSufix(this.index_fact);
    	InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
        if (inputParamsSuite.getTypeAccess()==TypeAccess.Bat) {
            return; 
        }
        
        DataCtxShop dCtxSh = getCtxShForTest();
		WebDriver driver = TestMaker.getDriverTestCase();
		registro_e_irdeshopping_nopubli(dCtxSh, driver);
	}

	public static Map<String,String> registro_e_irdeshopping_nopubli(DataCtxShop dCtxSh, WebDriver driver) 
	throws Exception {
		dCtxSh.userRegistered = false;
		AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
		SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		userMenusStpV.selectRegistrate(dCtxSh);
		String emailNonExistent = DataMango.getEmailNonExistentTimestamp();
		PageRegistroIniStpV pageRegistroIniStpV = PageRegistroIniStpV.getNew(driver);
		HashMap<String,String> dataRegistro = 
				pageRegistroIniStpV.sendDataAccordingCountryToInputs(dCtxSh.pais, emailNonExistent, false, dCtxSh.channel);
		pageRegistroIniStpV.clickRegistrateButton(dCtxSh.pais, false, dCtxSh.appE, dataRegistro);
		PageRegistroDirecStpV.sendDataAccordingCountryToInputs(dataRegistro, dCtxSh.pais, dCtxSh.channel, driver);
		PageRegistroDirecStpV.clickFinalizarButton(driver);
		PageRegistroFinStpV.clickIrDeShoppingButton(dCtxSh, driver);
		userMenusStpV.checkVisibilityLinkMangoLikesYou();

		return dataRegistro;
	}
	
	public static Map<String,String> registro_e_irdeshopping_sipubli(DataCtxShop dCtxSh, VersionRegistroSuite version, WebDriver driver) 
	throws Exception {
        dCtxSh.userRegistered = false;
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
        if (!dCtxSh.userRegistered) {
        	ModalSuscripcionStpV.validaRGPDModal(dCtxSh, driver);
        }
        
        SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
        userMenusStpV.selectRegistrate(dCtxSh);
        Map<String,String> dataRegistro = null;
        SecFooterStpV secFooterStpV = new SecFooterStpV(dCtxSh.channel, dCtxSh.appE, driver);
        if(version.register()) {
	        String emailNonExistent = DataMango.getEmailNonExistentTimestamp();
	        PageRegistroIniStpV pageRegistroIniStpV = PageRegistroIniStpV.getNew(driver);
	        dataRegistro = 
	        	pageRegistroIniStpV.sendDataAccordingCountryToInputs(dCtxSh.pais, emailNonExistent, true, dCtxSh.channel);
	        pageRegistroIniStpV.clickRegistrateButton(dCtxSh.pais, false, dCtxSh.appE, dataRegistro);
	        boolean paisConNinos = dCtxSh.pais.getShoponline().stateLinea(LineaType.nina, dCtxSh.appE)==ThreeState.TRUE;
	        PageRegistroSegundaStpV.setDataAndLineasRandom("23/4/1974", paisConNinos, 2, dCtxSh.pais, dataRegistro, driver);
	        if (paisConNinos) {
	            ListDataNinos listaNinos = new ListDataNinos();
	            listaNinos.add(new DataNino(sexoType.nina, "Martina Muñoz Rancaño", "11/10/2010"));
	            listaNinos.add(new DataNino(sexoType.nina, "Irene Muñoz Rancaño", "29/8/2016"));
	            PageRegistroNinosStpV.sendNinoDataAndContinue(listaNinos, dCtxSh.pais, driver);
	        }
	            
	        PageRegistroDirecStpV.sendDataAccordingCountryToInputs(dataRegistro, dCtxSh.pais, dCtxSh.channel, driver);
	        PageRegistroDirecStpV.clickFinalizarButton(driver);
	        PageRegistroFinStpV.clickIrDeShoppingButton(dCtxSh, driver);

            SecCabeceraStpV secCabeceraStpV = SecCabeceraStpV.getNew(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver);
            secCabeceraStpV.selecLogo();

	        secFooterStpV.validaRGPDFooter(version.register(), dCtxSh);
	        if (version.loginAfterRegister()) {
	            String emailUsr = dataRegistro.get("cfEmail");
	            String password = dataRegistro.get("cfPass");
	            userMenusStpV.logoffLogin(emailUsr, password);
	            PageMiCuentaStpV pageMiCuentaStpV = PageMiCuentaStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
	            pageMiCuentaStpV.goToMisDatosAndValidateData(dataRegistro, dCtxSh.pais.getCodigo_pais());
	            pageMiCuentaStpV.goToSuscripcionesAndValidateData(dataRegistro);        
	        }
        } else {
        	secFooterStpV.validaRGPDFooter(version.register(), dCtxSh);
        }
        
        return dataRegistro;
	}

	private boolean isAccesFromFactory() {
		return (this.paisFactory!=null);
	}
}
