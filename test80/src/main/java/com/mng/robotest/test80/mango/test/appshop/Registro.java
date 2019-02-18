package com.mng.robotest.test80.mango.test.appshop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.Test80mng.TypeAccessFmwk;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.utils;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.otras.Constantes.ThreeState;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.DataMango;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.DataNino;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataNinos;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.DataNino.sexoType;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro.DataRegType;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro.PageData;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMiCuentaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.modales.ModalSuscripcionStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroDirecStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroFinStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroIniStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroNinosStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroSegundaStpV;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;


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
    @Parameters({"brwsr-path", "urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) 
    throws Exception {
        //Recopilación de parámetros
        dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;

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
        }
        else {
            dCtxSh.pais = this.paisFactory;
            dCtxSh.idioma = this.idiomaFactory;
        }        
        
        //Almacenamiento final a nivel de Thread (para disponer de 1 x cada @Test)
        TestCaseData.storeInThread(dCtxSh);
        TestCaseData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, this.index_fact, dCtxSh.channel, context, method);
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
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        dCtxSh.userRegistered = false;
        if (dCtxSh.appE==AppEcom.votf)
            return;
            
        //Step. Acceso a Shop/Outlet/VOTF
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
            
        //Seleccionamos el menú superior "Registrate"
        SecMenusWrapperStpV.secMenuUser.selectRegistrate(dCtxSh.channel, dCtxSh, dFTest);
        
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
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        dCtxSh.userRegistered = false;
            
        //En caso de ejecución desde .bat no ejecutaremos el Registro 
        if (utils.getTypeAccessFmwk(dFTest.ctx)==TypeAccessFmwk.Bat)
            return;
        
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        //Validación modal suscripcion RGPD
        if (!dCtxSh.userRegistered) {
        	ModalSuscripcionStpV.validaRGPDModal(dCtxSh, dFTest);
        }
        
        SecMenusWrapperStpV.secMenuUser.selectRegistrate(dCtxSh.channel, dCtxSh, dFTest);
        if(clickRegister) {
	        String emailNonExistent = DataMango.getEmailNonExistentTimestamp();
	        HashMap<String,String> dataRegistro = PageRegistroIniStpV.sendDataAccordingCountryToInputs(dCtxSh.pais, emailNonExistent, clickPubli, dFTest);
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
	            
	        PageRegistroDirecStpV.sendDataAccordingCountryToInputs(dataRegistro, dCtxSh.pais, dFTest);
	        PageRegistroDirecStpV.clickFinalizarButton(dFTest);
	        PageRegistroFinStpV.clickIrDeShoppingButton(dCtxSh, dFTest);
	        //Validacion footer RGPD
	        SecFooterStpV.validaRGPDFooter(clickRegister, dCtxSh, dFTest);
	        
	        if (loginAfter) {
	            //Step. Cerramos sesión y nos volvemos a identificar con los datos del registro
	            String emailUsr = dataRegistro.get("cfEmail");
	            String password = dataRegistro.get("cfPass");
	            SecMenusWrapperStpV.secMenuUser.logoffLogin(emailUsr, password, dCtxSh.channel, dCtxSh.appE, dFTest);
	                
	            //Step. Ejecutamos la consulta de Mis datos comprobando que son coherentes con los utilizados en el registro
	            PageMiCuentaStpV.goToMisDatosAndValidateData(dataRegistro, dCtxSh.pais.getCodigo_pais(), dCtxSh.appE, dCtxSh.channel, dFTest);
	                
	            //Step. Ejecutamos la consulta de suscripciones comprobando que los datos son coherentes con los utilizados en el registro
	            PageMiCuentaStpV.goToSuscripcionesAndValidateData(dataRegistro, dCtxSh.appE, dCtxSh.channel, dFTest);        
	        }
	        
	        //TODO Checkout temporal para Loyalty
	        if (loyaltyTest) {
	        	testPago(dataRegistro, dFTest);
	        }

        }
        else {
        	SecFooterStpV.validaRGPDFooter(clickRegister, dCtxSh, dFTest);
        }
    }
    
    private void testPago(HashMap<String,String> dataRegistro, DataFmwkTest dFTest) throws Exception {
        dCtxSh.userRegistered = true;
        dCtxSh.userConnected = dataRegistro.get("cfEmail");
        
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = true;  
        FTCkout.validaPagos = true;
        FTCkout.emailExist = true; 
        FTCkout.trjGuardada = false;
        FTCkout.isEmpl = false;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        
        int maxArticlesAwayVale = 1;
        List<ArticleStock> listArticles = UtilsTestMango.getArticlesForTestDependingVale(dCtxSh, maxArticlesAwayVale);
        
        DataBag dataBag = dCtxPago.getDataPedido().getDataBag();
        SecBolsaStpV.altaListaArticulosEnBolsa(listArticles, dataBag, dCtxSh, dFTest);

        //Steps. Seleccionar el botón comprar y completar el proceso hasta la página de checkout con los métodos de pago
        dCtxPago.getFTCkout().testCodPromocional = true;
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, dFTest);
        
        //Pago
        Pago pagoVisaToTest = this.españa.getPago("VISA");
        DataPedido dataPedido = new DataPedido(dCtxSh.pais);
        dataPedido.setPago(pagoVisaToTest);
        
        PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, dCtxSh.channel, dFTest.driver);
        dCtxPago.setDataPedido(dataPedido);
        dCtxPago.getDataPedido().setEmailCheckout(dCtxSh.userConnected);
        PagoNavigationsStpV.testPagoFromCheckoutToEnd(dCtxPago, dCtxSh, pagoVisaToTest, dFTest);
    }
    
    @SuppressWarnings("static-access")
    @Test (
        groups={"Registro", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, alwaysRun=true, 
        description="Alta/Registro de un usuario (sin seleccionar el link de publicidad)")
    public void REG003_RegistroOK_NoPubli() throws Exception {
    	boolean clickPubli = false;
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        dCtxSh.userRegistered = false;
            
        //En caso de ejecución desde .bat no ejecutaremos el Registro 
        if (utils.getTypeAccessFmwk(dFTest.ctx)==TypeAccessFmwk.Bat) {
            return;
        }
        
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        SecMenusWrapperStpV.secMenuUser.selectRegistrate(dCtxSh.channel, dCtxSh, dFTest);
        String emailNonExistent = DataMango.getEmailNonExistentTimestamp();
        HashMap<String,String> dataRegistro = PageRegistroIniStpV.sendDataAccordingCountryToInputs(dCtxSh.pais, emailNonExistent, clickPubli, dFTest);
        PageRegistroIniStpV.clickRegistrateButton(dCtxSh.pais, false/*usrExists*/, dCtxSh.appE, dataRegistro, dFTest);
        PageRegistroDirecStpV.sendDataAccordingCountryToInputs(dataRegistro, dCtxSh.pais, dFTest);
        PageRegistroDirecStpV.clickFinalizarButton(dFTest);
        PageRegistroFinStpV.clickIrDeShoppingButton(dCtxSh, dFTest);
    }    
}
