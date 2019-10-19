package com.mng.robotest.test80.mango.test.appshop;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.testmaker.service.TestMaker;
import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.mango.test.stpv.votfcons.ConsolaVotfStpV;

public class ConsolaVotf {

    private String prodDisponible1 = "";
    
    @BeforeMethod(groups={"Canal:desktop_App:votf"})
    @Parameters({"prodDisponible1" })
    public void login(String prodDisponible1I) throws Exception {
        this.prodDisponible1 = prodDisponible1I;
    }

    @Test (
    	groups={"Canal:desktop_App:votf"},
    	description="[PRE] Generar pedido mediante la consola de VOTF")
    public void VOFT001_GenerarPedido() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
    	
    	InputParams inputParamsSuite = (InputParams)TestMaker.getTestCase().getInputParamsSuite();
		String paginaIniVOTF = inputParamsSuite.getUrlBase();
		ConsolaVotfStpV.accesoPagInicial(paginaIniVOTF, driver);
		ConsolaVotfStpV.selectEntornoTestAndCons("Preproducción", driver);
		ConsolaVotfStpV.inputArticleAndTiendaDisp(this.prodDisponible1, "00011459", driver);
		ConsolaVotfStpV.consultarTiposEnvio(driver);
		ConsolaVotfStpV.consultarDispEnvDomic(this.prodDisponible1, driver);	
		ConsolaVotfStpV.consultarDispEnvTienda(this.prodDisponible1, driver);
		String codigoPedido = ConsolaVotfStpV.realizarSolicitudTienda(this.prodDisponible1, driver);
		String codigoPedidoFull = ConsolaVotfStpV.obtenerPedidos(codigoPedido, driver);
		ConsolaVotfStpV.seleccionarPedido(codigoPedidoFull, driver);
		ConsolaVotfStpV.selectPreconfPedido(codigoPedidoFull, driver);
		ConsolaVotfStpV.selectConfPedido(codigoPedidoFull, driver);
    }
}