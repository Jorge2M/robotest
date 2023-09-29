package com.mng.robotest.tests.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepMantoBase;
import com.mng.robotest.tests.domains.manto.pageobjects.PageGestionarClientes;

import static com.mng.robotest.tests.domains.manto.pageobjects.PageGestionarClientes.TypeThirdButton.*;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

public class PageGestionarClientesSteps extends StepMantoBase {

	private final PageGestionarClientes pageGestionarClientes = new PageGestionarClientes();
	
	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Estamos en la página " + PageGestionarClientes.TITULO,
			pageGestionarClientes.isPage());
	 	
	 	checks.add(
			"Aparece el apartado de \"Buscar clientes\"",
			pageGestionarClientes.isVisibleFormBuscarClientes());
	 	
	 	checks.add(
			"Aparece el apartado de \"Tratar clientes\"",
			pageGestionarClientes.isVisibleFormTratarClientes());
	 	
		return checks;
	}

	@Step (
		description="Introducimos el DNI <b>#{dni}</b> y pulsamos el botón \"Buscar\"",
		expected="Aparece una lista de clientes válida",
		saveErrorData=SaveWhen.Never,
		saveImagePage=SaveWhen.Always)
	public void inputDniAndClickBuscar(String dni) {
		pageGestionarClientes.inputDniAndClickBuscarButton(dni, 20);	 
		checkAfterSearchByDni(dni);
	}
	
	@Validation
	private ChecksTM checkAfterSearchByDni(String dni) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Se muestra la tabla de información",
			pageGestionarClientes.isVisibleTablaInformacion());
	 	
	 	checks.add(
			"Aparece el DNI <b>" + dni + "</b> en la tabla",
			pageGestionarClientes.getDniTabla(dni));
	 	
		int seconds = 1;
	 	checks.add(String.format(
			"Aparece el botón de Alta o Baja (los esperamos un máximo de %s segundos)", seconds),
			pageGestionarClientes.isVisibleThirdButton(BAJA, seconds) ||
			pageGestionarClientes.isVisibleThirdButton(ALTA, seconds));
	 	
	 	return checks;
	}

	@Step(
		description="Tras haber introducido un DNI y haber dado al botón \"Buscar\", damos click al botón \"Detalles\"",
		expected="Muestra los detalles del cliente correctamente",
		saveErrorData=SaveWhen.Never)
	public void clickDetallesButton(String dni) {
		String idCliente = pageGestionarClientes.getIdClienteTablaFromDni(dni);
		pageGestionarClientes.clickDetallesButtonAndWaitSeconds(3);	
		checkAfterClickDetalles(dni, idCliente);
	}
	
	@Validation
	private ChecksTM checkAfterClickDetalles(String dni, String idCliente) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece el id del cliente <b>" + idCliente + "</b>",
			pageGestionarClientes.isVisibleIdClienteClickDetallesButton(idCliente));
	 	
	 	checks.add(
			"Aparece el dni del cliente <b>" + dni + "</b>",
			pageGestionarClientes.isVisibleDniClickDetallesButton(dni));
	 	
	 	return checks;
	}
}
