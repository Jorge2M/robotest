package com.mng.robotest.tests.domains.compra.tests;

import org.testng.annotations.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class Compra {

	@Test (
		testName="COM001",			
		groups={"Compra", "Smoke", "Checkout", "Micuenta", "Miscompras", "Canal:desktop,mobile_App:shop,outlet"}, alwaysRun=true, priority=2, 
		description="[Usuario registrado][Tarjeta guardada][Productos Home (Shop)][No aceptación cookies][Descuento empleado][Mi Cuenta][Mis Compras]") 
	public void compraHomeTrjSavedEmpl() throws Exception {
		new Com001().execute();
	}
	
	@Test (
		testName="COM003",			
		groups={"Compra", "Checkout", "Multidireccion", "Canal:desktop_App:shop,outlet"}, alwaysRun=true,
		description="[Usuario no registrado] Compra con cambio datos en dirección de envío y facturación en checkout",
		retry=false)
	public void compraCambioPaisNoregEmailExist() throws Exception {
		if (!new PageBase().isPRO()) {
			new Com003().execute();
		}
	}
	
	@Test (
		testName="COM005",			
		groups={"Registro_Express", "Smoke", "Bolsa", "Canal:all_App:all"}, alwaysRun=true,
		description="Registro Express, cierre/inicio sesión correcto")
	public void compraNoRegEmailNoExist() throws Exception {
		new Com005().execute();
	}

	@Test (
		testName="COM011",			
		groups={"Compra", "Multialmacen", "Canal:all_App:shop"}, alwaysRun=true,
		description="[Usuario registrado] Compra utilizando artículos Multialmacén")
	public void compraMultialmacen() throws Exception {
		new Com011().execute();
	}	
	
}