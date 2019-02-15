package com.mng.robotest.test80.mango.test.appshop;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.stpv.votfcons.ConsolaVotfStpV;


public class ConsolaVotf extends GestorWebDriver {

    String prodDisponible1 = "";
    
    @BeforeMethod
    @Parameters({ "brwsr-path", "urlBase", "prodDisponible1" })
    public void login(final String bpath, final String urlAcceso, final ITestContext context, final Method method, String prodDisponible1I) throws Exception {
    	TestCaseData.getAndStoreDataFmwk(bpath, urlAcceso, "", Channel.desktop, context, method);
        this.prodDisponible1 = prodDisponible1I;
    }

    @SuppressWarnings("unused")
    @AfterMethod(alwaysRun = true)
    public void logout(final ITestContext context, final Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }

    @Test (description="[PRE] Generar pedido mediante la consola de VOTF")
    public void VOFT001_GenerarPedido(final ITestContext context) throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
    	
		//Step. Acceso a la página inicial de VOTF
		String paginaIniVOTF = (String) context.getAttribute("appPath");
		ConsolaVotfStpV.accesoPagInicial(paginaIniVOTF, dFTest);
		
		//Step. Seleccion del entorno de Preproducción en ambos desplegables ("Test servicios VOTF" y "Consola comandos VOTF")
		ConsolaVotfStpV.selectEntornoTestAndCons("Preproducción", dFTest);
		
		//Step. Introducción de artículos con Stock + Tienda disponbile
		ConsolaVotfStpV.inputArticleAndTiendaDisp(this.prodDisponible1, "00011459", dFTest);
		
		//Step. Seleccionamos el botón "Consultar tipos de envío"
		ConsolaVotfStpV.consultarTiposEnvio(dFTest);
	
		//Step. Introducimos un determinado artículo y seleccionamos el botón "Consultar Disponibilidad Envío Domicilio"
		ConsolaVotfStpV.consultarDispEnvDomic(this.prodDisponible1, dFTest);	
		
	        //Step. Introducimos un determinado artículo y seleccionamos el botón "Consultar Disponibilidad Envío Tienda"	
		ConsolaVotfStpV.consultarDispEnvTienda(this.prodDisponible1, dFTest);
		
	        //Step. Introducimos un determinado artículo y seleccionamos el botón "Realizar solicitud a tienda"
		String codigoPedido = ConsolaVotfStpV.realizarSolicitudTienda(this.prodDisponible1, dFTest);
		
		//Step. Obtener los pedidos mediante selección del botón "Obtener pedidos" (del apartado "Consola comandos")
		String codigoPedidoFull = ConsolaVotfStpV.obtenerPedidos(codigoPedido, dFTest);
	
		//Step. Seleccionar el botón "Seleccionar pedido"
		ConsolaVotfStpV.seleccionarPedido(codigoPedidoFull, dFTest);
		
		//Step. Seleccionar el botón "Preconfirmar Pedido"
		ConsolaVotfStpV.selectPreconfPedido(codigoPedidoFull, dFTest);
		
		//Step. Seleccionar el botón "Confirmar Pedido"
		ConsolaVotfStpV.selectConfPedido(codigoPedidoFull, dFTest);
    }
}