package com.mng.robotest.test.steps.shop.checkout.giropay;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test.pageobject.shop.checkout.giropay.PageGiropay1rst;
import com.mng.robotest.test.utils.ImporteScreen;


public class PageGiropay1rstSteps {

	private final PageGiropay1rst pageGiropay1rst;
	private final Channel channel;
	
	public PageGiropay1rstSteps(Channel channel) {
		this.pageGiropay1rst = new PageGiropay1rst(channel);
		this.channel = channel;
	}
	
	@Validation
	public ChecksTM validateIsPage(String nombrePago, String importeTotal, String codPais) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago.toLowerCase() + "</b>",
			pageGiropay1rst.isPresentIconoGiropay(), State.Warn);	

		State stateVal = State.Warn;
		StoreType store = StoreType.Evidences;
		if (channel.isDevice()) {
			stateVal = State.Info;
			store = StoreType.None;
		}
		checks.add(
			Check.make(
			    "Aparece el importe de la compra: " + importeTotal,
			    ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, pageGiropay1rst.driver), stateVal)
			.store(store).build());
		
		checks.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			pageGiropay1rst.isPresentCabeceraStep(), State.Warn);	

		if (channel==Channel.desktop) {
			int maxSeconds = 2;
			checks.add(
				"Figura un botón de pago (lo esperamos hasta " + maxSeconds + " segundos)",
				pageGiropay1rst.isPresentButtonPagoDesktopUntil(maxSeconds), State.Defect);
		}
		
		return checks;
	}

	@Step (
		description="Pulsamos el botón para continuar con el Pago", 
		expected="Aparece la página de Test de introducción de datos de Giropay")
	public void clickButtonContinuePay() throws Exception {
		pageGiropay1rst.clickButtonContinuePay();
		new PageGiropayInputBankSteps().checkIsPage();
	}
}
