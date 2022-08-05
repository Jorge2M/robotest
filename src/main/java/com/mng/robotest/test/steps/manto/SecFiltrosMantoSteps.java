package com.mng.robotest.test.steps.manto;

import java.time.LocalDate;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.manto.SecCabecera;
import com.mng.robotest.test.pageobject.manto.SecFiltros;


public class SecFiltrosMantoSteps {

	public enum TypeSearch { BOLSA, PEDIDO } 
	
	private static final String TAG_NOMBRE_PAGO = "@TagNombrePago";
	private static final String TAG_LIT_TIENDA = "@TagLitTienda";
	
	private final SecFiltros secFiltros;
	
	public SecFiltrosMantoSteps(WebDriver driver) {
		secFiltros = new SecFiltros(driver);
	}
	
	public void setFiltrosYbuscar(DataPedido dataPedido, TypeSearch typeSearch) throws Exception {
		LocalDate fechaHoy = secFiltros.getFechaHastaValue();
		LocalDate fechaManyana = fechaHoy.plusDays(1);
		setFiltrosYbuscar(dataPedido, typeSearch, fechaHoy, fechaManyana);
	}
	
	@Step (
		description=
			"Buscamos a nivel de #{typeSearch} el pedido <b style=\"color:blue;\">#{dataPedido.getCodigoPedidoManto()}</b> con filtros: <br>" +
			"- Método pago: <b>" + TAG_NOMBRE_PAGO + "</b><br>" +
			"- Tienda: <b>" + TAG_LIT_TIENDA + "</b><br>" +
			"- País: <b>#{dataPedido.getNombrePais()}</b> (#{dataPedido.getCodigoPais()})<br>" +
			"- Fecha desde: #{fechaDesde.toString()}<br>" +
			"- Fecha hasta: #{fechaHasta.toString()}",
		expected="La búsqueda es correcta",
		saveErrorData=SaveWhen.Never)
	public void setFiltrosYbuscar(
			DataPedido dataPedido, @SuppressWarnings("unused") TypeSearch typeSearch, LocalDate fechaDesde, LocalDate fechaHasta) 
					throws Exception {
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(TAG_NOMBRE_PAGO, dataPedido.getPago().getNombre());
		step.replaceInDescription(TAG_LIT_TIENDA, SecCabecera.getLitTienda(secFiltros.driver));
		
		if (dataPedido.getCodigoPedidoManto()!=null) {
			secFiltros.setFiltroCodPedido(dataPedido.getCodigoPedidoManto());
		}
		secFiltros.setFiltroCodPaisIfExists(dataPedido.getCodigoPais());
		secFiltros.setFiltroFDesde(fechaDesde);
		secFiltros.setFiltroFHasta(fechaHasta);
		secFiltros.clickButtonBuscar();
	}
	
	public void setFiltrosWithoutChequeRegaloYbuscar(DataPedido dataPedido, TypeSearch typeSearch, LocalDate fechaDesde, LocalDate fechaHasta) 
			throws Exception {
		setFiltroForAvoidChequeRegalo();
		setFiltrosYbuscar(dataPedido, typeSearch, fechaDesde, fechaHasta);
	}
	
	@Step (
		description="Introducimos un importe total de 19.99 euros para evitar obtener pedidos asociados a cheques regalo",
		expected="El filtro se setea correctamente")
	public void setFiltroForAvoidChequeRegalo() {
		secFiltros.setFiltroImporteTotal("19.99");
	}
}
