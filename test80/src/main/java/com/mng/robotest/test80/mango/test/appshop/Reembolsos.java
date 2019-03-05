package com.mng.robotest.test80.mango.test.appshop;
import org.testng.ITestContext;

import java.lang.reflect.Method;

import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.arq.utils.otras.*;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageReembolsos;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageReembolsos.TypeReembolso;
import com.mng.robotest.test80.mango.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PageReembolsosStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageResultPagoStpV;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;

/**
 * Script correspondiente a las pruebas de reembolsos
 * @author jorge.munoz
 *
 */

public class Reembolsos extends GestorWebDriver {
    Pais arabia = null;
    IdiomaPais arabia_arabe = null;    
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
    DataCtxShop dCtxSh;
    
    /**
     * Acciones previas a cualquier @Test
     * @throws Exception
     */
    @BeforeMethod (groups={"Otras", "Canal:all_App:all"})
    @Parameters({"brwsr-path","urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) throws Exception {
        //Recopilación de parámetros
        dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;
        
        //Si no existe, obtenemos el país España
        if (this.arabia==null) {
            //Integer codArabia = Integer.valueOf(632);
            //List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codArabia)));
            //this.arabia = UtilsMangoTest.getPaisFromCodigo("632", listaPaises);
            //this.arabia_arabe = this.arabia.getListIdiomas().get(0);
            //TODO mientras que tengamos problemas con el buscador en Arabia probaremos contra España
            Integer espanaCod = Integer.valueOf(1);
            List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(espanaCod)));
            this.arabia = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
            this.arabia_arabe = this.arabia.getListIdiomas().get(0);
        }
        
        dCtxSh.pais = this.arabia;
        dCtxSh.idioma = this.arabia_arabe;
        
        //Almacenamiento final a nivel de Thread (para disponer de 1 x cada @Test)
        TestCaseData.storeInThread(dCtxSh);
        TestCaseData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, "", dCtxSh.channel, context, method);
    }

    /**
     * Acciones posteriores a cualquier @Test
     * @throws Exception
     */    
    @SuppressWarnings("unused")
    @AfterMethod (groups={"Otras", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }		
	
    /**
     * Script correspondiente al caso de prueba que configura el reembolso vía transferencia y saldo en cuenta en arabia/inglés 
     */
    @Test (
        groups={"Reembolso", "Canal:all_App:shop"}, 
        description="Configura el reembolso vía transferencia y saldo en cuenta para un país/idioma determinado")
    public void REE001_configureReembolso() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
	    
        //Este test sólo aplica al entornos no productivos
        if (UtilsMangoTest.isEntornoPRO(dCtxSh.appE, dFTest))
            return;        
        
        //Revisamos si el país tiene configurado el saldo en cuenta
        boolean paisConSaldoCta = dCtxSh.pais.existsPagoStoreCredit();
        
        //Obtenemos el usuario/password de acceso
        dCtxSh.userConnected = Constantes.mail_standard;
        dCtxSh.passwordUser = Constantes.pass_standard;
        dCtxSh.userRegistered = true;
        if (dCtxSh.pais.getEmailuser()!=null && dCtxSh.pais.getPassuser()!=null) {
            dCtxSh.userConnected = dCtxSh.pais.getEmailuser();
            dCtxSh.passwordUser = dCtxSh.pais.getPassuser();
        }
            
        //Step. Accedemos a Arabia Saudi/Árabe. Nos registramos con un usuario asociado a ese país. Se vacía la bolsa/favorites
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest.driver);

        //Step (+validaciones) selección menú "Mi cuenta" + "Reembolsos"
        PageReembolsosStpV.gotoRefundsFromMenu(paisConSaldoCta, dCtxSh.appE, dCtxSh.channel, dFTest.driver);
        
        if (paisConSaldoCta) {
            if (PageReembolsos.isCheckedRadio(TypeReembolso.StoreCredit, dFTest.driver)) {
                PageReembolsosStpV.testConfTransferencia(dFTest.driver);
                PageReembolsosStpV.selectRadioSalCtaAndRefresh(dFTest.driver);
            }
            else {
                PageReembolsosStpV.selectRadioSalCtaAndRefresh(dFTest.driver);
                PageReembolsosStpV.testConfTransferencia(dFTest.driver);
            }
        }        
    }
    
    /**
     * Realiza un checkout utilizando el Saldo en Cuenta 
     */
    @Test (
        groups={"Reembolso", "Canal:all_App:shop"},
        description="Se realiza un Checkout utilizando Saldo en Cuenta. Se accede a la configuración al inicio y al final para comprobar que el saldo en cuenta se resta correctamente")
    public void REE002_checkoutWithSaldoCta() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        
        //Este test sólo aplica al entornos no productivos
        if (UtilsMangoTest.isEntornoPRO(dCtxSh.appE, dFTest))
            return;
        
        //Obtenemos el usuario/password de acceso
        dCtxSh.userConnected = Constantes.mail_standard;
        dCtxSh.passwordUser = Constantes.pass_standard;
        dCtxSh.userRegistered = true;
        if (dCtxSh.pais.getEmailuser()!=null && dCtxSh.pais.getPassuser()!=null) {
            dCtxSh.userConnected = dCtxSh.pais.getEmailuser();
            dCtxSh.passwordUser = dCtxSh.pais.getPassuser();
        }
        
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, true/*clearArticulos*/, dFTest.driver);
        //TestAB.activateTestABcheckoutMovilEnNPasos(0, dCtxSh, dFTest.driver);
        PageReembolsosStpV.gotoRefundsFromMenu(dCtxSh.pais.existsPagoStoreCredit(), dCtxSh.appE, dCtxSh.channel, dFTest.driver);
        PageReembolsosStpV.selectRadioSalCtaAndRefresh(dFTest.driver);
        if (PageReembolsos.isVisibleSaveButtonStoreCredit(dFTest.driver)) {
        	PageReembolsosStpV.clickSaveButtonStoreCredit(dFTest.driver);
        }
        float saldoCtaIni = PageReembolsos.getImporteStoreCredit(dFTest.driver);
        
        //Damos de alta 2 artículos en la bolsa
        DataBag dataBag = new DataBag(); 
        SecBolsaStpV.altaArticlosConColores(1, dataBag, dCtxSh, dFTest.driver);
        
        //Seleccionar el botón comprar y completar el proceso hasta la página de checkout con los métodos de pago
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.isStoreCredit = true;
        FTCkout.validaPasarelas = false;  
        FTCkout.validaPagos = false;
        FTCkout.emailExist = true; 
        FTCkout.trjGuardada = false;
        FTCkout.isEmpl = false;
        FTCkout.testCodPromocional = true;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        dCtxPago.getDataPedido().setDataBag(dataBag);
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, dFTest); 
        
        //Informamos datos varios necesarios para el proceso de pagos de modo que se pruebe el pago StoreCredit
        dCtxPago.getDataPedido().setEmailCheckout(dCtxSh.userConnected);
        dCtxPago.setUserWithStoreC(true);
        dCtxPago.setSaldoCta(saldoCtaIni);
        dCtxPago.getFTCkout().validaPagos = true;
        Pago pagoStoreCredit = dCtxSh.pais.getPago("STORECREDIT");
        dCtxPago.getDataPedido().setPago(pagoStoreCredit);
        PagoNavigationsStpV.checkPasarelaPago(dCtxPago, dCtxSh, dFTest);
        
        //Volvemos a la portada (Seleccionamos el link "Seguir de shopping" o el icono de Mango)
        PageResultPagoStpV.selectSeguirDeShopping(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        
        //Calculamos el saldo en cuenta que debería quedar (según si se ha realizado o no el pago);
        float saldoCtaEsperado;
        if (pagoStoreCredit.getTestpasarela().compareTo("s")==0 &&
            pagoStoreCredit.getTestpago().compareTo("s")==0) {
            DataPedido dataPedido = dCtxPago.getListPedidos().get(0);
            float importePago = ImporteScreen.getFloatFromImporteMangoScreen(dataPedido.getImporteTotalSinSaldoCta());
            saldoCtaEsperado = UtilsMangoTest.round(saldoCtaIni - importePago, 2);
        }
        else
            saldoCtaEsperado = saldoCtaIni;
        
        //Step (+validaciones) selección menú "Mi cuenta" + "Reembolsos"
        PageReembolsosStpV.gotoRefundsFromMenuAndValidaSalCta(dCtxSh.pais.existsPagoStoreCredit(), saldoCtaEsperado, dCtxSh.appE, dCtxSh.channel, dFTest.driver);
        
        //Validación en Manto de los Pedidos (si existen)
    	List<CheckPedido> listChecks = Arrays.asList(
    		CheckPedido.consultarBolsa, 
    		CheckPedido.consultarPedido);
        DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
        PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, dFTest);
    }
}