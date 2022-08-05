package com.mng.robotest.test.steps.shop.checkout.envio;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test.pageobject.shop.checkout.envio.ModalDroppoints;

@SuppressWarnings({"static-access"})
public class SecConfirmDatosSteps {

	@Validation (
		description="Es visible la capa de confirmación de los datos (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public static boolean validateIsVisible(int maxSeconds, Channel channel, WebDriver driver) {
		return (ModalDroppoints.secConfirmDatos.isVisibleUntil(maxSeconds, channel, driver));
	}
	
	public static void setDataIfNeeded(String codigoPais,WebDriver driver) throws Exception {
		if (PaisShop.DEUTSCHLAND == PaisShop.getPais(codigoPais)) {
			if (ModalDroppoints.secConfirmDatos.isVisibleInputPostNumberIdDeutschland(driver)) {
				inputPostNumberId("0038594352", driver);
			}
		}
	}
	
	@Step (
		description="Introducir <b>#{postNumberId}</b> en el Post Number Id",
		expected="El valor queda correctamente introducido")
	public static void inputPostNumberId(String postNumberId, WebDriver driver) throws Exception {
		ModalDroppoints.secConfirmDatos.sendDataInputPostNumberIdDeutschland(postNumberId, driver);
	}
	
	@Step (
		description="Clickamos el botón \"Confirmar Datos\"", 
		expected="La dirección de envío se establece a la de la tienda")
	public static void clickConfirmarDatosButton(Channel channel, AppEcom app, DataPedido dataPedido, WebDriver driver) {
		ModalDroppoints.secConfirmDatos.clickConfirmarDatosButtonAndWait(5, driver);
		SeleniumUtils.waitForPageLoaded(driver, 2);	   
		checkConfirmacionCambioDireccionEnvio(dataPedido, channel, app, driver);
	}
	
	@Validation
	private static ChecksTM checkConfirmacionCambioDireccionEnvio(DataPedido dataPedido, Channel channel, AppEcom app, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 2;
		checks.add(
			"Desaparece la capa de Droppoints (lo esperamos hasta " + maxSeconds + " segundos)",
			ModalDroppoints.isInvisibleUntil(maxSeconds, channel, driver), State.Warn);
		
		DataDeliveryPoint dataDp = dataPedido.getDataDeliveryPoint();
		String textDireccionEnvioCompleta = new PageCheckoutWrapper(channel, app, driver).getTextDireccionEnvioCompleta();
		dataPedido.setDireccionEnvio(textDireccionEnvioCompleta);
		checks.add(
			"Se modifica la dirección de envío por la del Delivery Point (" + dataDp.getDireccion() + ")",
			textDireccionEnvioCompleta.contains(dataDp.getDireccion()), State.Defect);
		checks.add(
			"Se modifica el código postal de envío por el del Delivery Point (" + dataDp.getCPandPoblacion() + ")",
			textDireccionEnvioCompleta.contains(dataDp.getCPandPoblacion()), State.Defect);
		return checks;
	}
}
