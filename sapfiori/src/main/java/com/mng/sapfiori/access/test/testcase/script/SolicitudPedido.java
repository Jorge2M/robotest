package com.mng.sapfiori.access.test.testcase.script;

import static com.mng.sapfiori.access.test.testcase.webobject.pedidos.InputFieldPedido.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.sapfiori.access.test.testcase.stpv.iconsmenu.PageIconsMenuStpV;
import com.mng.sapfiori.access.test.testcase.stpv.login.PageLoginStpV;
import com.mng.sapfiori.access.test.testcase.stpv.pedidos.InputDataSolPedido;
import com.mng.sapfiori.access.test.testcase.stpv.pedidos.PageGestionSolPedidoBuyerStpV;
import com.mng.sapfiori.access.test.testcase.stpv.pedidos.PagePosSolicitudPedidoStpV;
import com.mng.sapfiori.access.test.testcase.stpv.pedidos.PageSolicitudPedidoStpV;
import com.mng.sapfiori.access.test.testcase.webobject.pedidos.InfoGeneralSolPedido;
import com.mng.sapfiori.access.test.testcase.webobject.pedidos.InputFieldPedido;
import com.mng.testmaker.domain.TestCaseTM;

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
		inputDataAndCheckLineaPedido(pagePosSolicitudPedidoStpV);
		modifyPrizeLineaPedidoAndSave(pageSolPedidoStpV);
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

	private void inputDataAndCheckLineaPedido(PagePosSolicitudPedidoStpV pagePosSolicitudPedidoStpV) throws Exception {
		List<InputDataSolPedido> dataPedido = new ArrayList<>();
		Calendar hoyMas7dias = Calendar.getInstance();
		hoyMas7dias.add(Calendar.DATE, 7);
		String fechaEntrega =
			hoyMas7dias.get(Calendar.DAY_OF_MONTH) + "." +
			hoyMas7dias.get(Calendar.MONTH) + "." +
			hoyMas7dias.get(Calendar.YEAR);
		//dataPedido.add(InputDataSolPedido.getForSearchAndSelect(Material, "*AMERICANA*", "183103702092"));
		dataPedido.add(InputDataSolPedido.getForSearchAndSelect(Material, "*BLUSA THMARACU*", "2064284MPV001"));
		dataPedido.add(InputDataSolPedido.getForSelectValue(Centro, "LLIÇÀ"));
		dataPedido.add(InputDataSolPedido.getForSearchAndSelect(Temporada, 1));
		String currentYear = String.valueOf(hoyMas7dias.get(Calendar.YEAR));
		dataPedido.add(InputDataSolPedido.getForSelectValue(AñoTemporada, currentYear));
		dataPedido.add(InputDataSolPedido.getForSelectValue(OrgCompras, "P000"));
		dataPedido.add(InputDataSolPedido.getForSelectValue(GrupoCompras, "Man"));
		dataPedido.add(InputDataSolPedido.getForSendText(Customer, "RET"));

		Random r = new Random();
		Integer quantity = r.ints(100, 500).findFirst().getAsInt();
		InputDataSolPedido inputDataQuantity = InputDataSolPedido.getForSendText(Quantity, String.valueOf(quantity));
		dataPedido.add(inputDataQuantity);

		dataPedido.add(InputDataSolPedido.getForSendText(FechaEntrega, fechaEntrega));
		//dataPedido.add(InputDataSolPedido.getForSearchAndSelect(IdCurvaDistrib, "1940003HPM", "1940003HPM"));
		dataPedido.add(InputDataSolPedido.getForSearchAndSelect(IdCurvaDistrib, "1000000080", "1000000080"));
		pagePosSolicitudPedidoStpV.inputData(dataPedido);
		PageSolicitudPedidoStpV pageSolicitudPedidoStpV = 
			pagePosSolicitudPedidoStpV.clickAplicar();
		
		pageSolicitudPedidoStpV.checkFieldIn1rstLineaPedidos(inputDataQuantity, 2);
	}

	public void modifyPrizeLineaPedidoAndSave(PageSolicitudPedidoStpV pageSolPedidoStpV) throws Exception {
		pageSolPedidoStpV.inputFielValuedIn1rstLinePedidos(InputFieldPedido.Precio, "5,00");
		pageSolPedidoStpV.clickButtonGuardar();
		System.out.println("Fin");
	}
}
