package com.mng.robotest.test80.mango.test.appshop;

import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.xmlprogram.InputDataTestMaker;
import com.mng.robotest.test80.data.TestMakerContext;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.stpv.votfcons.ConsolaVotfStpV;

public class ConsolaVotf extends GestorWebDriver {

    String prodDisponible1 = "";
    
    @BeforeMethod
    @Parameters({"prodDisponible1" })
    public void login(String prodDisponible1I, ITestContext context, Method method) throws Exception {
        TestMakerContext tMakerCtx = TestCaseData.getTestMakerContext(context);
        InputDataTestMaker inputData = tMakerCtx.getInputData();
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(AppEcom.votf);
        dCtxSh.setChannel(Channel.desktop);
        dCtxSh.urlAcceso = inputData.getUrlBase();
        
        Utils.storeDataShopForTestMaker(inputData.getTypeWebDriver(), "", dCtxSh, context, method);
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
		WebDriver driver = TestCaseData.getWebDriver();
    	
		String paginaIniVOTF = (String) context.getAttribute("appPath");
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