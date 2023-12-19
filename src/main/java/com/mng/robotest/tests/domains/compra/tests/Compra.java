package com.mng.robotest.tests.domains.compra.tests;

import org.testng.annotations.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class Compra {

	@Test (
		groups={"Compra", "Checkout", "Canal:desktop,mobile_App:shop,outlet"}, alwaysRun=true, priority=2, 
		description="[Usuario registrado][Tarjeta guardada][Productos Home (Shop)][No aceptación cookies] Compra con descuento empleado. Verificar compra en sección 'Mis compras'") //Lo marcamos con prioridad 2 para dar tiempo a que otro caso de prueba registre la tarjeta 
	public void COM001_Compra_Home_TrjSaved_Empl() throws Exception {
		new Com001().execute();
	}
	
	@Test (
		groups={"Compra", "Pedidomanto", "Checkout", "Canal:all_App:shop,outlet"}, alwaysRun=true,
		description="[Usuario no registrado] Compra sin permitir todas las cookies y con tarjeta real")
	public void COM002_Compra_Tarjeta_Real_No_Cookies() throws Exception {
		new Com002().execute();
	}

	@Test (
		groups={"Compra", "Checkout", "Multidireccion", "Canal:desktop_App:shop,outlet"}, alwaysRun=true,
		description="[Usuario no registrado] Compra con cambio datos en dirección de envío y facturación en checkout",
		retry=false)
	public void COM003_Compra_y_CambioPais_Noreg_emailExist() throws Exception {
		if (!new PageBase().isPRO()) {
			new Com003().execute();
		}
	}
	
	@Test (
		groups={"Registro_Express", "Bolsa", "Canal:all_App:all"}, alwaysRun=true,
		description="Registro Express, cierre/inicio sesión correcto")
	public void COM005_Compra_noReg_emailNoExist() throws Exception {
		new Com005().execute();
	}

	@Test (
		groups={"Compra", "Checkout", "Canal:tablet_App:votf"}, alwaysRun=true,
		description="description=[Usuario no registrado] Test en VOTF compra desde tienda Italia")
	public void COM006_Compra_Francia_Tienda() throws Exception {
		new Com006().execute();
	}	
	
	@Test (
		groups={"Compra", "Checkout", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Compra Serbia [usuario registrado]")
	public void COM008_Compra_Serbia() throws Exception {
		new Com008().execute();
	}
	
	@Test (
		groups={"Compra", "Multialmacen", "Canal:all_App:shop"}, alwaysRun=true,
		description="[Usuario registrado] Compra utilizando artículos Multialmacén")
	public void COM011_Compra_Multialmacen() throws Exception {
		new Com011().execute();
	}	
	
}