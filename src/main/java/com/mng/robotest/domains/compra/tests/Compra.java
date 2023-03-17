package com.mng.robotest.domains.compra.tests;

import org.testng.annotations.*;

import com.mng.robotest.domains.base.PageBase;

public class Compra {

	@Test (
		groups={"Compra_Tarjeta_Guardada", "Canal:desktop,mobile_App:shop,outlet"}, alwaysRun=true, priority=2, 
		description="[Usuario registrado][Tarjeta guardada][Productos Home e Intimissimi (Shop)][No aceptación cookies] Compra con descuento empleado. Verificar compra en sección 'Mis compras'") //Lo marcamos con prioridad 2 para dar tiempo a que otro caso de prueba registre la tarjeta 
	public void COM001_Compra_Home_Intimissimi_TrjSaved_Empl() throws Exception {
		new Com001().execute();
	}
	
	@Test (
		groups={"Compra_Sin_Cookies", "Canal:all_App:shop,outlet"}, alwaysRun=true,
		description="[Usuario no registrado] Compra sin permitir todas las cookies y con tarjeta real")
	public void COM002_Compra_Tarjeta_Real_No_Cookies() throws Exception {
		new Com002().execute();
	}

	@Test (
		groups={"Compra_Multidireccion", "Canal:desktop_App:shop,outlet"}, alwaysRun=true,
		description="[Usuario no registrado] Compra con cambio datos en dirección de envío y facturación en checkout")
	public void COM003_Compra_y_CambioPais_Noreg_emailExist() throws Exception {
		if (!new PageBase().isPRO()) {
			new Com003().execute();
		}
	}
	
	@Test (
		groups={"Compra_Chequeregalo", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Consulta datos cheque existente y posterior compra Cheque regalo New (España)")
	public void COM004_Cheque_Regalo_New() throws Exception {
		new Com004().execute();
	}
	
	@Test (
		groups={"Registro_Express", "Canal:all_App:all"}, alwaysRun=true,
		description="Registro Express, cierre/inicio sesión correcto")
	public void COM005_Compra_noReg_emailNoExist() throws Exception {
		new Com005().execute();
	}

	@Test (
		groups={"Compra_Votf", "Canal:tablet_App:votf"}, alwaysRun=true,
		description="description=[Usuario no registrado] Test en VOTF compra desde tienda Italia")
	public void COM006_Compra_Francia_Tienda() throws Exception {
		new Com006().execute();
	}	
	
	@Test (
		groups={"Compra_Chequeregalo", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Compra cheque regalo Old (Francia)")
	public void COM007_Cheque_Regalo_Old() throws Exception {
		new Com007().execute();
	}
	
	@Test (
		groups={"Compra", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Compra Serbia [usuario registrado] [MVP Checkout]")
	public void COM008_Compra_MVP() throws Exception {
		new Com008().execute();
	}
	
	@Test (
		groups={"Compra_Multialmacen", "Canal:all_App:shop"}, alwaysRun=true,
		description="[Usuario registrado] Compra utilizando artículos Multialmacén")
	public void COM011_Compra_Multialmacen() throws Exception {
		new Com011().execute();
	}	
	
	
}