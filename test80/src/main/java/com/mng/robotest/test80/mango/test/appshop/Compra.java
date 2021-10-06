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
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.getdata.products.GetterProducts;
import com.mng.robotest.test80.mango.test.getdata.products.Menu;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData.Importe;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test80.mango.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.NavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checqueregalo.PageChequeRegaloInputDataStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMiCuentaStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

import static com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow.BuilderCheckout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebDriver;


public class Compra {
	
	private final static Pais españa = PaisGetter.get(PaisShop.España);
	private final static Pais italia = PaisGetter.get(PaisShop.Italia);
	private final static Pais francia = PaisGetter.get(PaisShop.France);
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
		description="[Usuario registrado][Tarjeta guardada][Productos Home][No aceptación cookies] Compra con descuento empleado. Verificar compra en sección 'Mis compras'") //Lo marcamos con prioridad 2 para dar tiempo a que otro caso de prueba registre la tarjeta 
	public void COM001_Compra_HomeProducts_TrjSaved_Empl() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userConnected = "test.performance10@mango.com";
		dCtxSh.passwordUser = "Mango123";
		dCtxSh.userRegistered = true;

		//To checkout
		FlagsTestCkout FTCkout = new FlagsTestCkout();
		FTCkout.validaPasarelas = true;  
		FTCkout.validaPagos = true;
		FTCkout.emailExist = true; 
		FTCkout.trjGuardada = true;
		FTCkout.isEmpl = true;
		FTCkout.acceptCookies = false;
		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
		dCtxPago.setFTCkout(FTCkout);
		
		dCtxPago = new BuilderCheckout(dCtxSh, dCtxPago, driver)
        	.pago(españa.getPago("VISA"))
        	.listArticles(getArticlesHome(dCtxSh, driver))
        	.build()
        	.checkout(From.Prehome);

		//Validación en Manto de los Pedidos (si existen)
		List<CheckPedido> listChecks = Arrays.asList(
			CheckPedido.consultarBolsa, 
			CheckPedido.consultarPedido);
		DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
		PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
	}
	
	private List<Garment> getArticlesHome(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		if (dCtxSh.appE==AppEcom.outlet) {
			return null;
		}
		
		GetterProducts getterProducts = new GetterProducts.Builder(españa.getCodigo_alf(), dCtxSh.appE, driver)
			.linea(LineaType.home)
			.menusCandidates(Arrays.asList(
					Menu.Albornoces, 
					Menu.Toallas, 
					Menu.Alfombras))
			.build();
		
		return getterProducts.getWithStock();
	}

	private enum TypeCheque {Old, New}
	
	@Test (
		groups={"Compra", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Consulta datos cheque existente y posterior compra Cheque regalo New (España)")
	public void COM004_Cheque_Regalo_New() throws Exception {
		testChequeRegalo(TypeCheque.New);
	}
	
	@Test (
		groups={"Compra", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Compra cheque regalo Old (Francia)")
	public void COM007_Cheque_Regalo_Old() throws Exception {
		testChequeRegalo(TypeCheque.Old);
	}
	
	private void testChequeRegalo(TypeCheque typeCheque) throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;        
		dCtxSh.userRegistered = true;
		if (typeCheque==TypeCheque.New) {
			dCtxSh.pais = españa;
		} else {
			dCtxSh.pais = francia;
			dCtxSh.userConnected = "francia.test@mango.com";
			dCtxSh.passwordUser = "mango123";
		}

		//Creamos una estructura para ir almacenando los datos del proceso de pagos
		String nTarjeta;
		String cvvTarjeta = "";
		AccesoStpV.oneStep(dCtxSh, false, driver);
		new SecBolsaStpV(dCtxSh, driver).clear();
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		secMenusStpV.seleccionLinea(LineaType.she, null, dCtxSh);
		
		PageChequeRegaloInputDataStpV pageChequeRegaloInputDataStpV = new PageChequeRegaloInputDataStpV(dCtxSh.pais, driver);
		SecFooterStpV secFooterStpV = new SecFooterStpV(dCtxSh.channel, dCtxSh.appE, driver);
		if (typeCheque==TypeCheque.Old) {
			secFooterStpV.clickLinkFooter(FooterLink.cheque_regalo_old, false);
			pageChequeRegaloInputDataStpV.clickQuieroComprarChequeRegalo();
			pageChequeRegaloInputDataStpV.seleccionarCantidades(Importe.euro50);
		} else {
			secFooterStpV.clickLinkFooter(FooterLink.cheque_regalo, false);
			if(dCtxSh.channel.isDevice()){
				nTarjeta = "100000040043";
				cvvTarjeta = "618";
				pageChequeRegaloInputDataStpV.paginaConsultarSaldo(nTarjeta);
				pageChequeRegaloInputDataStpV.insertCVVConsultaSaldo(cvvTarjeta);
			}
			pageChequeRegaloInputDataStpV.seleccionarCantidades(Importe.euro50);
			pageChequeRegaloInputDataStpV.clickQuieroComprarChequeRegalo();
		}

		ChequeRegalo chequeRegalo = new ChequeRegalo();
		chequeRegalo.setNombre("Jorge");
		chequeRegalo.setApellidos("Muñoz Martínez");
		chequeRegalo.setEmail(Constantes.mail_standard);
		chequeRegalo.setImporte(Importe.euro50);
		chequeRegalo.setMensaje("Te conocía aún antes de haberte formado en el vientre de tu madre");
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

		dCtxPago = new BuilderCheckout(dCtxSh, dCtxPago, driver)
        	.pago(españa.getPago("VISA"))
        	.build()
        	.checkout(From.Identification);
		
		if (dCtxPago.getFTCkout().validaPedidosEnManto) {
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
        
        //Hasta página de Checkout
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = false;  
        FTCkout.validaPagos = false;
        FTCkout.emailExist = true; 
        FTCkout.trjGuardada = false;
        FTCkout.isEmpl = false;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        
        dCtxPago = new BuilderCheckout(dCtxSh, dCtxPago, driver)
        	.finalCountrys(Arrays.asList(francia))
    		.build()
    		.checkout(From.Prehome);
        
        if (dCtxPago.getFTCkout().validaPedidosEnManto) {
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
            
            dCtxPago = new BuilderCheckout(dCtxSh, dCtxPago, driver)
    			.build()
    			.checkout(From.Prehome);
                    
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
            if (dCtxPago.getFTCkout().validaPedidosEnManto) {
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
        
        dCtxPago = new BuilderCheckout(dCtxSh, dCtxPago, driver)
			.build()
			.checkout(From.Prehome);
        
        if (dCtxPago.getFTCkout().validaPedidosEnManto) {
        	List<CheckPedido> listChecks = Arrays.asList(
        		CheckPedido.consultarBolsa, 
        		CheckPedido.consultarPedido);
            DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
            PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
        }
    }
    
}