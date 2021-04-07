package com.mng.robotest.test80.mango.test.appshop;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData.Importe;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test80.mango.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.NavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checqueregalo.PageChequeRegaloInputDataStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMiCuentaStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebDriver;


public class Compra {
	
	private final static Pais españa = PaisGetter.get(PaisShop.España);
	private final static Pais italia = PaisGetter.get(PaisShop.Italia);
	private final static IdiomaPais castellano = españa.getListIdiomas().get(0);
	private final static IdiomaPais italiano = italia.getListIdiomas().get(0);

	public Compra() {}

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais=españa;
		dCtxSh.idioma=castellano;
		//dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
		return dCtxSh;
	}

	@Test (
		groups={"Compra", "Canal:desktop,mobile_App:shop,outlet"}, alwaysRun=true, priority=2, 
		description="[Usuario registrado][Tarjeta guardada] Compra con descuento empleado. Verificar compra en sección 'Mis compras'") //Lo marcamos con prioridad 2 para dar tiempo a que otro caso de prueba registre la tarjeta 
	public void COM001_Compra_TrjSaved_Empl() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userConnected = "test.performance10@mango.com";
		dCtxSh.passwordUser = "Mango123";
		dCtxSh.userRegistered = true;

		//To checkout
		FlagsTestCkout FTCkout = new FlagsTestCkout();
		FTCkout.validaPasarelas = false;  
		FTCkout.validaPagos = false;
		FTCkout.emailExist = true; 
		FTCkout.trjGuardada = true;
		FTCkout.isEmpl = true;
		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
		dCtxPago.setFTCkout(FTCkout);
		
        PagoNavigationsStpV pagoNavigationsStpV = new PagoNavigationsStpV(dCtxSh, dCtxPago, driver);
		pagoNavigationsStpV.testFromLoginToExecPaymetIfNeeded();

		//Pago
		Pago pagoVisaToTest = españa.getPago("VISA");
		DataPedido dataPedido = new DataPedido(dCtxSh.pais);
		dataPedido.setPago(pagoVisaToTest);

		PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(dCtxSh.channel, dCtxSh.appE, driver);
		pageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido);
		dCtxPago.setDataPedido(dataPedido);
		dCtxPago.getDataPedido().setEmailCheckout(dCtxSh.userConnected);
		dCtxPago.getFTCkout().validaPasarelas = true;
		dCtxPago.getFTCkout().validaPagos = true;
		pagoNavigationsStpV.testPagoFromCheckoutToEnd(pagoVisaToTest);

		//Validación en Manto de los Pedidos (si existen)
		List<CheckPedido> listChecks = Arrays.asList(
			CheckPedido.consultarBolsa, 
			CheckPedido.consultarPedido);
		DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
		PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
	}

	@Test (
		groups={"Compra", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="[Usuario registrado] Consulta datos cheque existente y posterior compra Cheque regalo")
	public void COM004_Cheque_Regalo_UsrReg_emailExist() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;        
		dCtxSh.userRegistered = true;
		dCtxSh.pais = españa;

		//Creamos una estructura para ir almacenando los datos del proceso de pagos
		String nTarjeta;
		String cvvTarjeta = "";
		AccesoStpV.oneStep(dCtxSh, false, driver);
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		secMenusStpV.seleccionLinea(LineaType.she, null, dCtxSh);
		(new SecFooterStpV(dCtxSh.channel, dCtxSh.appE, driver)).clickLinkFooter(FooterLink.cheque_regalo, false);
		PageChequeRegaloInputDataStpV pageChequeRegaloInputDataStpV = new PageChequeRegaloInputDataStpV(driver);
		if(dCtxSh.channel.isDevice()){
			nTarjeta = "100000040043";
			cvvTarjeta = "618";
			pageChequeRegaloInputDataStpV.paginaConsultarSaldo(nTarjeta);
			pageChequeRegaloInputDataStpV.insertCVVConsultaSaldo(cvvTarjeta);
		}

		pageChequeRegaloInputDataStpV.seleccionarCantidades(Importe.euro50);
		pageChequeRegaloInputDataStpV.clickQuieroComprarChequeRegalo();

		ChequeRegalo chequeRegalo = new ChequeRegalo();
		chequeRegalo.setNombre("Jorge");
		chequeRegalo.setApellidos("Muñoz Martínez");
		chequeRegalo.setEmail(Constantes.mail_standard);
		chequeRegalo.setImporte(Importe.euro50);
		chequeRegalo.setMensaje("Ya sólo queda por determinar si el universo partió de cero o del infinito");
		pageChequeRegaloInputDataStpV.inputDataAndClickComprar(dCtxSh.channel, dCtxSh.appE, chequeRegalo);

		//Ejecutar el pago
		FlagsTestCkout fTCkout = new FlagsTestCkout();
		fTCkout.validaPasarelas = true;  
		fTCkout.validaPagos = true;
		fTCkout.validaPedidosEnManto = true;
		fTCkout.emailExist = false; 
		fTCkout.trjGuardada = false;
		fTCkout.isEmpl = false;
		fTCkout.isChequeRegalo = true;
		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
		dCtxPago.setFTCkout(fTCkout);
		Pago pagoVISA = españa.getPago("VISA");
		dCtxPago.getDataPedido().setPago(pagoVISA);
		DataPedido dataPedido = dCtxPago.getDataPedido();
		
		PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(dCtxSh.channel, dCtxSh.appE, driver);
		dataPedido.setImporteTotal(pageCheckoutWrapper.getPrecioTotalFromResumen());
		dataPedido.setDireccionEnvio("");
		dataPedido.setEmailCheckout(dCtxSh.userConnected);
		
        PagoNavigationsStpV pagoNavigationsStpV = new PagoNavigationsStpV(dCtxSh, dCtxPago, driver);
		pagoNavigationsStpV.checkPasarelaPago();
		if (fTCkout.validaPedidosEnManto) {
			List<CheckPedido> listChecks = Arrays.asList(
				CheckPedido.consultarBolsa, 
				CheckPedido.consultarPedido,
				CheckPedido.anular); 
			DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
			PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
		}
	}

    @Test (
        groups={"Compra", "Canal:desktop_App:shop,outlet"}, alwaysRun=true,
        description="[Usuario no registrado] Compra con cambio datos en dirección de envío en checkout")
    public void COM003_Compra_y_CambioPais_Noreg_emailExist() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userRegistered = false;
            
        //Indicamos la lista de países hacia los que queremos cambiar/verificar en la página de precompra 
        List<Pais> paisesDestino = new ArrayList<>();
        paisesDestino.add(italia);
        
        //Hasta página de Checkout
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = false;  
        FTCkout.validaPagos = false;
        FTCkout.emailExist = true; 
        FTCkout.trjGuardada = false;
        FTCkout.isEmpl = false;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        
        PagoNavigationsStpV pagoNavigationsStpV = new PagoNavigationsStpV(dCtxSh, dCtxPago, driver);
        pagoNavigationsStpV.testFromLoginToExecPaymetIfNeeded(paisesDestino);
        if (FTCkout.validaPedidosEnManto) {
        	List<CheckPedido> listChecks = Arrays.asList(
        		CheckPedido.consultarBolsa, 
        		CheckPedido.consultarPedido);
            DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
            PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
        }
    }
            
    @SuppressWarnings("static-access")
    @Test (
        groups={"Compra", "Canal:all_App:all"}, alwaysRun=true,
        description="[Usuario no registrado] Pre-compra. Cierre/Inicio sesión correcto")
    public void COM005_Compra_noReg_emailNoExist() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userRegistered = false;
	    
        //No permitiremos la ejecución diaria de este tipo de checkout porque implica la ejecución 
        //de un registro de usuario con el nuevo email introducido 
        InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
        if (inputParamsSuite.getTypeAccess()!=TypeAccess.Bat) {
            //Hasta página de Checkout
            FlagsTestCkout FTCkout = new FlagsTestCkout();
            FTCkout.validaPasarelas = false;  
            FTCkout.validaPagos = false;
            FTCkout.emailExist = false; 
            FTCkout.trjGuardada = false;
            FTCkout.isEmpl = false;
            DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
            dCtxPago.setFTCkout(FTCkout);
            
            PagoNavigationsStpV pagoNavigationsStpV = new PagoNavigationsStpV(dCtxSh, dCtxPago, driver);
            pagoNavigationsStpV.testFromLoginToExecPaymetIfNeeded();
                    
            //Seleccionamos el logo de Mango (necesitamos acceder a una página con los links del menú superior)
            NavigationsStpV.gotoPortada(dCtxSh, driver);
            
            if (dCtxSh.appE!=AppEcom.votf) {
                //Cerramos sesión y nos volvemos a identificar con los datos del registro
                String usrEmail = dCtxPago.getDatosRegistro().get("cfEmail");
                String password = dCtxPago.getDatosRegistro().get("cfPass");
                SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
                secMenusStpV.getMenusUser().logoffLogin(usrEmail, password);
                    
                //Ejecutamos la consulta de Mis datos comprobando que son coherentes con los utilizados en el registro
                PageMiCuentaStpV pageMiCuentaStpV = PageMiCuentaStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
                HashMap<String,String> datosRegistro = dCtxPago.getDatosRegistro();
                datosRegistro.put("cfEmail", usrEmail);
                datosRegistro.put("cfPass", password);
                datosRegistro.put("", "Barcelona");
                datosRegistro.put("provinciaPais", "Barcelona");
                pageMiCuentaStpV.goToMisDatosAndValidateData(datosRegistro, dCtxSh.pais.getCodigo_pais());
            }            
            
            //Validación en Manto de los Pedidos (si existen)
            if (FTCkout.validaPedidosEnManto) {
            	List<CheckPedido> listChecks = Arrays.asList(
            		CheckPedido.consultarBolsa, 
            		CheckPedido.consultarPedido);
                DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
                PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
            }
        }
    }

    @Test (
        groups={"Compra", "Canal:tablet_App:votf"}, alwaysRun=true,
        description="description=[Usuario no registrado] Test en VOTF compra desde tienda Italia")
    public void COM006_Compra_Francia_Tienda() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.pais=italia;
        dCtxSh.idioma=italiano;   
        dCtxSh.userRegistered = false;
        
        //Hasta página de Checkout
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = true;  
        FTCkout.validaPagos = true;
        FTCkout.validaPedidosEnManto = true;
        FTCkout.emailExist = true; 
        FTCkout.trjGuardada = false;
        FTCkout.isEmpl = false;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        
        PagoNavigationsStpV pagoNavigationsStpV = new PagoNavigationsStpV(dCtxSh, dCtxPago, driver);
        pagoNavigationsStpV.testFromLoginToExecPaymetIfNeeded();
        if (FTCkout.validaPedidosEnManto) {
        	List<CheckPedido> listChecks = Arrays.asList(
        		CheckPedido.consultarBolsa, 
        		CheckPedido.consultarPedido);
            DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
            PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
        }
    }
}