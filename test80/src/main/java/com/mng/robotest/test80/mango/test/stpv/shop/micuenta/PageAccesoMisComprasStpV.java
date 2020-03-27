package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageAccesoMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageAccesoMisCompras.TypeBlock;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageDetallePedidoStpV;

public class PageAccesoMisComprasStpV {

	private final WebDriver driver;
	private final PageAccesoMisCompras pageAccesoMisCompras;
	
	private PageAccesoMisComprasStpV(WebDriver driver) {
		this.driver = driver;
		this.pageAccesoMisCompras = new PageAccesoMisCompras(driver);
	}
	public static PageAccesoMisComprasStpV getNew(WebDriver driver) {
		return new PageAccesoMisComprasStpV(driver);
	}
	
	@Validation
    public ChecksTM validateIsPage() {
        ChecksTM validations = ChecksTM.getNew();
        validations.add(
        	"Aparece la página de \"Acceso a Mis Compras\"",
        	pageAccesoMisCompras.isPage(), State.Warn);
        validations.add(
        	"Aparecen el bloque \"Ya estoy registrado\"",
        	pageAccesoMisCompras.isPresentBlock(TypeBlock.SiRegistrado), State.Warn);
        validations.add(
        	"Aparece el bloque de \"No estoy registrado\"",
        	pageAccesoMisCompras.isPresentBlock(TypeBlock.NoRegistrado), State.Warn);
        return validations;
    }
    
	@Step (
		description="Seleccionar el bloque \"#{typeBlock}\"", 
        expected="Se hace visible el bloque de #{typeBlock}")
    public void clickBlock(TypeBlock typeBlock) {
        pageAccesoMisCompras.clickBlock(typeBlock);
        checkIsVisibleBlock(typeBlock, 1);
    }
	
	@Validation (
		description="Se hace visible el bloque de \"#{typeBlock}\" (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
	private boolean checkIsVisibleBlock(TypeBlock typeBlock, int maxSecondsWait) {
		return (pageAccesoMisCompras.isVisibleBlockUntil(typeBlock, maxSecondsWait));
	}
    
	@Step (
		description="En el bloque de \"Si Registrado\", introducir el usuario/password (#{usuario}/#{password}) y pulsar \"Entrar\"", 
        expected="Aparece la página de \"Mis compras\"")
    public void enterForSiRegistrado(String usuario, String password, Channel channel) throws Exception {
        pageAccesoMisCompras.inputUserPasswordBlockSi(usuario, password); 
        pageAccesoMisCompras.clickEntrarBlockSi();
        PageMisComprasStpV pageMisComprasStpV = PageMisComprasStpV.getNew(channel, driver);
        pageMisComprasStpV.validateIsPage();
    }

	final static String tagUsuario = "@TagUsuario";
	@Step (
		description=
			"En el bloque de \"No Registrado\", introducir el usuario/núm pedido " + 
			"(" + tagUsuario + " / <b style=\"color:blue;\">#{dataPedido.getCodpedido()}</b>)" + 
			" y pulsar \"Buscar pedido\"", 
		expected="Aparece la página de detalle del pedido")
	public void buscarPedidoForNoRegistrado(DataPedido dataPedido) throws Exception {
		String usuario = dataPedido.getEmailCheckout();
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagUsuario, usuario);

		pageAccesoMisCompras.inputUserAndNumPedidoBlockNo(usuario, dataPedido.getCodpedido()); 
		pageAccesoMisCompras.clickBuscarPedidoBlockNo(); 

		PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(driver);
		pageDetPedidoStpV.validateIsPageOk(dataPedido);
	}
}
