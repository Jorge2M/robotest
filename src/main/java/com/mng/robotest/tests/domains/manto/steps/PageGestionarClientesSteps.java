package com.mng.robotest.tests.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepMantoBase;
import com.mng.robotest.tests.domains.manto.pageobjects.PageGestionarClientes;

import static com.mng.robotest.tests.domains.manto.pageobjects.PageGestionarClientes.TypeThirdButton.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class PageGestionarClientesSteps extends StepMantoBase {

	private final PageGestionarClientes pgGestionarClientes = new PageGestionarClientes();
	
	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Estamos en la página " + PageGestionarClientes.TITULO,
			pgGestionarClientes.isPage());
	 	
	 	checks.add(
			"Aparece el apartado de \"Buscar clientes\"",
			pgGestionarClientes.isVisibleFormBuscarClientes());
	 	
	 	checks.add(
			"Aparece el apartado de \"Tratar clientes\"",
			pgGestionarClientes.isVisibleFormTratarClientes());
	 	
		return checks;
	}

	@Step (
		description="Introducimos el DNI <b>#{dni}</b> y pulsamos el botón \"Buscar\"",
		expected="Aparece una lista de clientes válida",
		saveErrorData=NEVER, saveImagePage=ALWAYS)
	public void inputDniAndClickBuscar(String dni) {
		pgGestionarClientes.inputDniAndClickBuscarButton(dni, 20);	 
		checkAfterSearchByDni(dni);
	}
	
	@Validation
	private ChecksTM checkAfterSearchByDni(String dni) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Se muestra la tabla de información",
			pgGestionarClientes.isVisibleTablaInformacion());
	 	
	 	checks.add(
			"Aparece el DNI <b>" + dni + "</b> en la tabla",
			pgGestionarClientes.getDniTabla(dni));
	 	
		int seconds = 1;
	 	checks.add(String.format(
			"Aparece el botón de Alta o Baja (los esperamos un máximo de %s segundos)", seconds),
			pgGestionarClientes.isVisibleThirdButton(BAJA, seconds) ||
			pgGestionarClientes.isVisibleThirdButton(ALTA, seconds));
	 	
	 	return checks;
	}

	@Step(
		description="Tras haber introducido un DNI y haber dado al botón \"Buscar\", damos click al botón \"Detalles\"",
		expected="Muestra los detalles del cliente correctamente",
		saveErrorData=NEVER)
	public void clickDetallesButton(String dni) {
		String idCliente = pgGestionarClientes.getIdClienteTablaFromDni(dni);
		pgGestionarClientes.clickDetallesButtonAndWaitSeconds(3);	
		checkAfterClickDetalles(dni, idCliente);
	}
	
	@Validation
	private ChecksTM checkAfterClickDetalles(String dni, String idCliente) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece el id del cliente <b>" + idCliente + "</b>",
			pgGestionarClientes.isVisibleIdClienteClickDetallesButton(idCliente));
	 	
	 	checks.add(
			"Aparece el dni del cliente <b>" + dni + "</b>",
			pgGestionarClientes.isVisibleDniClickDetallesButton(dni));
	 	
	 	return checks;
	}
}
