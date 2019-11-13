package com.mng.sapfiori.test.testcase.script;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.testmaker.domain.TestCaseTM;
import com.mng.sapfiori.test.testcase.stpv.iconsmenu.PageIconsMenuStpV;
import com.mng.sapfiori.test.testcase.stpv.login.PageLoginStpV;
import com.mng.sapfiori.test.testcase.stpv.pedidos.PageGestionSolPedidoBuyerStpV;
import com.mng.sapfiori.test.testcase.stpv.pedidos.PagePosSolicitudPedidoStpV;
import com.mng.sapfiori.test.testcase.stpv.pedidos.PagePosSolicitudPedidoStpV.InputDataSolPedido;
import com.mng.sapfiori.test.testcase.stpv.pedidos.PageSolicitudPedidoStpV;
import com.mng.sapfiori.test.testcase.webobject.pedidos.InfoGeneralSolPedido;
import com.mng.sapfiori.test.testcase.webobject.pedidos.PagePosSolicitudPedido.InputPage;

public class SolicitudPedido {

    public SolicitudPedido() {}
  
    @Test (
        groups={"ManagePRs", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="Manage PRs from Buyer")
    public void SOL001_SolicitudPedidoBuyer() throws Exception {
    	TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
    	WebDriver driver = testCase.getDriver();
        driver.get(testCase.getInputParamsSuite().getUrlBase());

        PageSolicitudPedidoStpV pageSolPedidoStpV = goToPageSolicitudPedido(driver);
        PagePosSolicitudPedidoStpV pagePosSolicitudPedidoStpV = goToPagePosSolicitudPedido(pageSolPedidoStpV);
        inputDataAndClickAplicar(pagePosSolicitudPedidoStpV);
    	//pageSolPedidoStpV.checkLineaPedido();
    }
    
    private PageSolicitudPedidoStpV goToPageSolicitudPedido(WebDriver driver) throws Exception {
		PageLoginStpV pageLoginStpV = PageLoginStpV.getNew(driver);
		PageIconsMenuStpV pageInitialStpV = 
			pageLoginStpV.inputCredentialsAndEnter("00556106", "Irene_2016");
	
		PageGestionSolPedidoBuyerStpV pageGestSolPedidoStpV = 
	    	pageInitialStpV.clickManagePurchaseRequisitionsBuyer();
		
		return ( 
			pageGestSolPedidoStpV.clickIconAñadirPedido());
    }
    
    private PagePosSolicitudPedidoStpV goToPagePosSolicitudPedido(PageSolicitudPedidoStpV pageSolPedidoStpV) throws Exception {
		InfoGeneralSolPedido infoGeneral = new InfoGeneralSolPedido();
		infoGeneral.descrSolPedido = "CINTURON JUDITH 30";
		infoGeneral.claseDocumento = "Solicitud Pedido TH";
		infoGeneral.detFuenteAprov = true;
		pageSolPedidoStpV.inputInfoGeneral(infoGeneral);
		return ( 
			pageSolPedidoStpV.añadirArticulo("Material"));
    }
    
    private void inputDataAndClickAplicar(PagePosSolicitudPedidoStpV pagePosSolicitudPedidoStpV) throws Exception {
    	List<InputDataSolPedido> dataPedido = new ArrayList<>();
        Calendar hoyMas7dias = Calendar.getInstance();
        hoyMas7dias.add(Calendar.DATE, 7);
    	String fechaEntrega =
        	hoyMas7dias.get(Calendar.DAY_OF_MONTH) + "." +
        	hoyMas7dias.get(Calendar.MONTH) + "." +
    		hoyMas7dias.get(Calendar.YEAR);
    	dataPedido.add(InputDataSolPedido.getForSearchAndSelect(InputPage.Material, "*AMERICANA*", "183103702092"));
    	dataPedido.add(InputDataSolPedido.getForSelectValue(InputPage.Centro, "LLIÇÀ"));
    	dataPedido.add(InputDataSolPedido.getForSearchAndSelect(InputPage.Temporada, 1));
    	String currentYear = String.valueOf(hoyMas7dias.get(Calendar.YEAR));
    	dataPedido.add(InputDataSolPedido.getForSelectValue(InputPage.AñoTemporada, currentYear));
    	dataPedido.add(InputDataSolPedido.getForSelectValue(InputPage.OrgCompras, "P000"));
    	dataPedido.add(InputDataSolPedido.getForSelectValue(InputPage.GrupoCompras, "Man"));
    	dataPedido.add(InputDataSolPedido.getForSendText(InputPage.Customer, "RET"));
    	dataPedido.add(InputDataSolPedido.getForSendText(InputPage.Quantity, "5000"));
    	dataPedido.add(InputDataSolPedido.getForSendText(InputPage.FechaEntrega, fechaEntrega));
    	dataPedido.add(InputDataSolPedido.getForSearchAndSelect(InputPage.IdCurvaDistrib, "2060049MCA002", "2060049MCA"));
    	pagePosSolicitudPedidoStpV.inputData(dataPedido);
    	pagePosSolicitudPedidoStpV.clickAplicar();
    }
}
