package com.mng.robotest.domains.compra.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.generic.UtilsMangoTest;


public class Compra {

	@Test (
		groups={"Compra", "Canal:desktop,mobile_App:shop,outlet"}, alwaysRun=true, priority=2, 
		description="[Usuario registrado][Tarjeta guardada][Productos Home e Intimissimi (Shop)][No aceptación cookies] Compra con descuento empleado. Verificar compra en sección 'Mis compras'") //Lo marcamos con prioridad 2 para dar tiempo a que otro caso de prueba registre la tarjeta 
	public void COM001_Compra_Home_Intimissimi_TrjSaved_Empl() throws Exception {
		new Com001().execute();
	}
	
	@Test (
		groups={"Compra", "Canal:all_App:all"}, alwaysRun=true,
		description="[Usuario no registrado] Compra con tarjeta real")
	public void COM002_Compra_Tarjeta_Real() throws Exception {
		new Com002().execute();
	}

	@Test (
		groups={"Compra", "Canal:desktop_App:shop,outlet"}, alwaysRun=true,
		description="[Usuario no registrado] Compra con cambio datos en dirección de envío en checkout")
	public void COM003_Compra_y_CambioPais_Noreg_emailExist() throws Exception {
		if (!isPro()) {
			new Com003().execute();
		}
	}
	
	@Test (
		groups={"Compra", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Consulta datos cheque existente y posterior compra Cheque regalo New (España)")
	public void COM004_Cheque_Regalo_New() throws Exception {
		new Com004().execute();
	}
	
	@Test (
		groups={"Compra", "Canal:all_App:all"}, alwaysRun=true,
		description="[Usuario no registrado] Pre-compra. Cierre/Inicio sesión correcto")
	public void COM005_Compra_noReg_emailNoExist() throws Exception {
		new Com005().execute();
	}

	@Test (
		groups={"Compra", "Canal:tablet_App:votf"}, alwaysRun=true,
		description="description=[Usuario no registrado] Test en VOTF compra desde tienda Italia")
	public void COM006_Compra_Francia_Tienda() throws Exception {
		new Com006().execute();
	}	
	
	@Test (
		groups={"Compra", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Compra cheque regalo Old (Francia)")
	public void COM007_Cheque_Regalo_Old() throws Exception {
		new Com007().execute();
	}

	
	public boolean isPro() {
		WebDriver driver = TestMaker.getDriverTestCase();
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		AppEcom app = (AppEcom)inputParamsSuite.getApp();
		return UtilsMangoTest.isEntornoPRO(app, driver);
	}
}