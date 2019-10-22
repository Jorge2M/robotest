package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageAccesoMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageAccesoMisCompras.TypeBlock;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageDetallePedidoStpV;

public class PageAccesoMisComprasStpV {

	@Validation
    public static ChecksResult validateIsPage(WebDriver driver) {
        ChecksResult validations = ChecksResult.getNew();
        validations.add(
        	"Aparece la página de \"Acceso a Mis Compras\"",
        	PageAccesoMisCompras.isPage(driver), State.Warn);
        validations.add(
        	"Aparecen el bloque \"Ya estoy registrado\"",
        	PageAccesoMisCompras.isPresentBlock(TypeBlock.SiRegistrado, driver), State.Warn);
        validations.add(
        	"Aparece el bloque de \"No estoy registrado\"",
        	PageAccesoMisCompras.isPresentBlock(TypeBlock.NoRegistrado, driver), State.Warn);
        return validations;
    }
    
	@Step (
		description="Seleccionar el bloque \"#{typeBlock}\"", 
        expected="Se hace visible el bloque de #{typeBlock}")
    public static void clickBlock(TypeBlock typeBlock, WebDriver driver) {
        PageAccesoMisCompras.clickBlock(typeBlock, driver);
        checkIsVisibleBlock(typeBlock, 1, driver);
    }
	
	@Validation (
		description="Se hace visible el bloque de \"#{typeBlock}\" (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
	private static boolean checkIsVisibleBlock(TypeBlock typeBlock, int maxSecondsWait, WebDriver driver) {
	    return (PageAccesoMisCompras.isVisibleBlockUntil(typeBlock, maxSecondsWait, driver));
	}
    
	@Step (
		description="En el bloque de \"Si Registrado\", introducir el usuario/password (#{usuario}/#{password}) y pulsar \"Entrar\"", 
        expected="Aparece la página de \"Mis compras\"")
    public static void enterForSiRegistrado(String usuario, String password, Channel channel, WebDriver driver) throws Exception {
        PageAccesoMisCompras.inputUserPasswordBlockSi(usuario, password, driver); 
        PageAccesoMisCompras.clickEntrarBlockSi(driver);   
        PageMisComprasStpV pageMisComprasStpV = PageMisComprasStpV.getNew(channel, driver);
        pageMisComprasStpV.validateIsPage();
    }
    
	final static String tagUsuario = "@TagUsuario";
	@Step (
		description="En el bloque de \"No Registrado\", introducir el usuario/núm pedido (" + tagUsuario + "/#{dataPedido.getCodpedido()}) y pulsar \"Buscar pedido\"", 
        expected="Aparece la página de detalle del pedido")
    public static void buscarPedidoForNoRegistrado(DataPedido dataPedido, WebDriver driver) throws Exception {
        String usuario = dataPedido.getEmailCheckout();
        TestMaker.getCurrentStepInExecution().replaceInDescription(tagUsuario, usuario);
        
        PageAccesoMisCompras.inputUserAndNumPedidoBlockNo(usuario, dataPedido.getCodpedido(), driver); 
        PageAccesoMisCompras.clickBuscarPedidoBlockNo(driver); 
        
        PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(driver);
        pageDetPedidoStpV.validateIsPageOk(dataPedido, driver);      
    }
}
