package com.mng.robotest.domains.manto.steps;

import java.time.LocalDate;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.manto.pageobjects.SecFiltros;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.manto.SecCabecera;

public class SecFiltrosMantoSteps extends StepMantoBase {

	public enum TypeSearch { BOLSA, PEDIDO } 
	
	private static final String TAG_NOMBRE_PAGO = "@TagNombrePago";
	private static final String TAG_LIT_TIENDA = "@TagLitTienda";
	
	private final SecFiltros secFiltros = new SecFiltros();
	
	public void setFiltrosYbuscar(DataPedido dataPedido, TypeSearch typeSearch) {
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
			DataPedido dataPedido, TypeSearch typeSearch, LocalDate fechaDesde, LocalDate fechaHasta) {
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(TAG_NOMBRE_PAGO, dataPedido.getPago().getNombre());
		step.replaceInDescription(TAG_LIT_TIENDA, new SecCabecera().getLitTienda());
		
		if (dataPedido.getCodigoPedidoManto()!=null) {
			secFiltros.setFiltroCodPedido(dataPedido.getCodigoPedidoManto());
		}
		secFiltros.setFiltroCodPaisIfExists(dataPedido.getCodigoPais());
		secFiltros.setFiltroFDesde(fechaDesde);
		secFiltros.setFiltroFHasta(fechaHasta);
		secFiltros.clickButtonBuscar();
	}
	
	public void setFiltrosWithoutChequeRegaloYbuscar(
			DataPedido dataPedido, TypeSearch typeSearch, LocalDate fechaDesde, LocalDate fechaHasta) {
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
