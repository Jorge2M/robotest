package com.mng.robotest.test80.mango.test.appshop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.mng.robotest.test80.mango.test.stpv.shop.SecCabeceraStpV;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.test.data.Constantes.ThreeState;
import com.mng.robotest.test80.arq.utils.otras.TypeAccessFmwk;
import com.mng.robotest.test80.arq.xmlprogram.InputDataTestMaker;
import com.mng.robotest.test80.data.TestMakerContext;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.DataMango;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
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

public class Registro extends GestorWebDriver {
    Pais españa = null;
    IdiomaPais castellano = null;
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
    boolean loyaltyTest = false;
    
    private String index_fact = "";
    public int prioridad;
    Pais paisFactory = null;
    IdiomaPais idiomaFactory = null;
    private DataCtxShop dCtxSh;
    
    //Si añadimos un constructor para el @Factory hemos de añadir este constructor para la invocación desde SmokeTest
    public Registro() {}
    
    //Constructor para invocación desde @Factory
    public Registro(Pais pais, IdiomaPais idioma, int prioridad) {
        this.paisFactory = pais;
        this.idiomaFactory = idioma;
        this.index_fact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
        this.prioridad = prioridad;
    }
    
    //TODO temporal para Loyalty
    public Registro(int iteration) {
        this.index_fact = String.valueOf(iteration);
        loyaltyTest = true;
    }
    
    @BeforeMethod(groups={"Registro", "Canal:all_App:all", "SupportsFactoryCountrys"})
    public void login(ITestContext context, Method method) throws Exception {
        //Recopilación de parámetros
        TestMakerContext tMakerCtx = TestCaseData.getTestMakerContext(context);
        InputDataTestMaker inputData = tMakerCtx.getInputData();
        dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom((AppEcom)inputData.getApp());
        dCtxSh.setChannel(inputData.getChannel());
        dCtxSh.urlAcceso = inputData.getUrlBase();

        //Si el acceso es normal (no es desde una @Factory) utilizaremos el España/Castellano
        if (this.paisFactory==null) {
            if (this.españa==null) {
                Integer codEspanya = Integer.valueOf(1);
                List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
                this.españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
                this.castellano = this.españa.getListIdiomas().get(0);
            }
            
            dCtxSh.pais = this.españa;
            dCtxSh.idioma = this.castellano;
        } else {
            dCtxSh.pais = this.paisFactory;
            dCtxSh.idioma = this.idiomaFactory;
        }        
        
        Utils.storeDataShopForTestMaker(inputData.getTypeWebDriver(), index_fact, dCtxSh, context, method);
    }

    @SuppressWarnings("unused")
    @AfterMethod (groups={"Registro", "Canal:all_App:all", "SupportsFactoryCountrys"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }       

    @SuppressWarnings("static-access")
    @Test (
        groups={"Registro", "Canal:desktop_App:all"},
        description="Registro con errores en la introducción de los datos")
    public void REG001_RegistroNOK() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.userRegistered = false;
        if (dCtxSh.appE==AppEcom.votf) {
            return;
        }
            
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest.driver);
        
        SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        userMenusStpV.selectRegistrate(dCtxSh);
        
        //Step. Click inicial a Registrate (sin haber introducido ningún dato) -> Aparecerán los correspondientes mensajes de error
        HashMap<String,String> dataRegister = new HashMap<>();        
        PageRegistroIniStpV.clickRegistrateButton(dCtxSh.pais, false/*usrExists*/, dCtxSh.appE, dataRegister, dFTest);
                    
        //Step. Introducir datos incorrectos y validar mensajes de error
        ListDataRegistro dataKOToSend = new ListDataRegistro();
        dataKOToSend.add(DataRegType.name, "Jorge111", false);
        dataKOToSend.add(DataRegType.apellidos, "Muñoz Martínez333", false);
        dataKOToSend.add(DataRegType.email, "jorge.munoz", false);
        dataKOToSend.add(DataRegType.password, "passsinnumeros", false);
        dataKOToSend.add(DataRegType.telefono, "66501512A", false);
        dataKOToSend.add(DataRegType.codpostal, "0872A", false);
        String dataToSendInHtmlFormat = dataKOToSend.getFormattedHTMLData(PageData.pageInicial);
        PageRegistroIniStpV.sendFixedDataToInputs(dataKOToSend, dataToSendInHtmlFormat, dFTest);

        //Step. Introducir datos correctos pero usuario ya existente
        ListDataRegistro dataToSend = new ListDataRegistro(); 
        dataToSend.add(DataRegType.name, "Jorge", true);
        dataToSend.add(DataRegType.apellidos, "Muñoz Martínez", true);
        dataToSend.add(DataRegType.email, Constantes.mail_standard, true);
        dataToSend.add(DataRegType.password, "Sirjorge74", true);
        dataToSend.add(DataRegType.telefono, "665015122", true);
        dataToSend.add(DataRegType.codpostal, "08720", true);
        dataToSendInHtmlFormat = dataToSend.getFormattedHTMLData(PageData.pageInicial);
        PageRegistroIniStpV.sendFixedDataToInputs(dataToSend, dataToSendInHtmlFormat, dFTest);
        
        //Step. Seleccionamos el botón "Regístrate" y validar que aparece el mensaje de error de usuario ya existente
        PageRegistroIniStpV.clickRegistrateButton(dCtxSh.pais, true/*usrExists*/, dCtxSh.appE, dataRegister, dFTest);
    }

    @SuppressWarnings("static-access")
    @Test (
        groups={"Registro", "Canal:all_App:shop", "Canal:all_App:outlet", "SupportsFactoryCountrys"}, alwaysRun=true, 
        description="Alta/Registro de un usuario (seleccionando link de publicidad) y posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
    @Parameters({"loginAfterRegister", "register"})    
    public void REG002_RegistroOK_publi(String loginAfterRegister, String register) throws Exception {
        boolean loginAfter = true;
        boolean clickPubli = true;
        boolean clickRegister = true;
        if ("false".compareTo(loginAfterRegister)==0) {
            loginAfter = false;
        }
        if ("false".compareTo(register)==0) {
        	clickRegister = false;
        }
        
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.userRegistered = false;
            
        //En caso de ejecución desde .bat no ejecutaremos el Registro 
        TestMakerContext tMakerCtx = TestCaseData.getTestMakerContext(dFTest.ctx);
        if (tMakerCtx.getInputData().getTypeAccess()==TypeAccessFmwk.Bat) {
            return;
        }
            
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest.driver);
        //Validación modal suscripcion RGPD
        if (!dCtxSh.userRegistered) {
        	ModalSuscripcionStpV.validaRGPDModal(dCtxSh, dFTest.driver);
        }
        
        SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        userMenusStpV.selectRegistrate(dCtxSh);
        if(clickRegister) {
	        String emailNonExistent = DataMango.getEmailNonExistentTimestamp();
	        HashMap<String,String> dataRegistro = 
	        	PageRegistroIniStpV.sendDataAccordingCountryToInputs(dCtxSh.pais, emailNonExistent, clickPubli, dCtxSh.channel, dFTest);
	        PageRegistroIniStpV.clickRegistrateButton(dCtxSh.pais, false/*usrExists*/, dCtxSh.appE, dataRegistro, dFTest);
	        boolean paisConNinos = dCtxSh.pais.getShoponline().stateLinea(LineaType.nina, dCtxSh.appE)==ThreeState.TRUE;
	        PageRegistroSegundaStpV.setDataAndLineasRandom("23/4/1974", paisConNinos, 2/*numNinos*/, dCtxSh.pais, dataRegistro, dFTest);
	        if (paisConNinos) {
	            //Step. Introducir datos correctos de los niños y pulsar "Continuar"
	            ListDataNinos listaNinos = new ListDataNinos();
	            listaNinos.add(new DataNino(sexoType.nina, "Martina Muñoz Rancaño", "11/10/2010"));
	            listaNinos.add(new DataNino(sexoType.nina, "Irene Muñoz Rancaño", "29/8/2016"));
	            PageRegistroNinosStpV.sendNinoDataAndContinue(listaNinos, dCtxSh.pais, dFTest);
	        }
	            
	        PageRegistroDirecStpV.sendDataAccordingCountryToInputs(dataRegistro, dCtxSh.pais, dCtxSh.channel, dFTest);
	        PageRegistroDirecStpV.clickFinalizarButton(dFTest);
	        PageRegistroFinStpV.clickIrDeShoppingButton(dCtxSh, dFTest);

	        //Seleccionar el logo de la cabecera
            SecCabeceraStpV secCabeceraStpV = SecCabeceraStpV.getNew(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, dFTest.driver);
            secCabeceraStpV.selecLogo();

	        //Validacion footer RGPD
	        SecFooterStpV.validaRGPDFooter(clickRegister, dCtxSh, dFTest.driver);
	        
	        if (loginAfter) {
	            //Step. Cerramos sesión y nos volvemos a identificar con los datos del registro
	            String emailUsr = dataRegistro.get("cfEmail");
	            String password = dataRegistro.get("cfPass");
	            userMenusStpV.logoffLogin(emailUsr, password);
	                
	            //Step. Ejecutamos la consulta de Mis datos comprobando que son coherentes con los utilizados en el registro
	            PageMiCuentaStpV.goToMisDatosAndValidateData(dataRegistro, dCtxSh.pais.getCodigo_pais(), dCtxSh.appE, dCtxSh.channel, dFTest.driver);
	                
	            //Step. Ejecutamos la consulta de suscripciones comprobando que los datos son coherentes con los utilizados en el registro
	            PageMiCuentaStpV.goToSuscripcionesAndValidateData(dataRegistro, dCtxSh.appE, dCtxSh.channel, dFTest.driver);        
	        }
	        
	        //TODO Checkout temporal para Loyalty
	        if (loyaltyTest) {
//	        	navegaGaleria(dFTest);
//	        	testPago(dataRegistro, dFTest);
	        }

        } else {
        	SecFooterStpV.validaRGPDFooter(clickRegister, dCtxSh, dFTest.driver);
        }
    }
    
    @SuppressWarnings("static-access")
    @Test (
        groups={"Registro", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, alwaysRun=true, 
        description="Alta/Registro de un usuario (sin seleccionar el link de publicidad)")
    public void REG003_RegistroOK_NoPubli() throws Exception {
    	boolean clickPubli = false;
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.userRegistered = false;
            
        //En caso de ejecución desde .bat no ejecutaremos el Registro 
        TestMakerContext tMakerCtx = TestCaseData.getTestMakerContext(dFTest.ctx);
        if (tMakerCtx.getInputData().getTypeAccess()==TypeAccessFmwk.Bat) {
            return;
        }
        
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, dFTest.driver);
        
        SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        userMenusStpV.selectRegistrate(dCtxSh);
        String emailNonExistent = DataMango.getEmailNonExistentTimestamp();
        HashMap<String,String> dataRegistro = 
        	PageRegistroIniStpV.sendDataAccordingCountryToInputs(dCtxSh.pais, emailNonExistent, clickPubli, dCtxSh.channel, dFTest);
        PageRegistroIniStpV.clickRegistrateButton(dCtxSh.pais, false, dCtxSh.appE, dataRegistro, dFTest);
        PageRegistroDirecStpV.sendDataAccordingCountryToInputs(dataRegistro, dCtxSh.pais, dCtxSh.channel, dFTest);
        PageRegistroDirecStpV.clickFinalizarButton(dFTest);
        PageRegistroFinStpV.clickIrDeShoppingButton(dCtxSh, dFTest);
        userMenusStpV.checkVisibilityLinkMangoLikesYou();
    }    
}
