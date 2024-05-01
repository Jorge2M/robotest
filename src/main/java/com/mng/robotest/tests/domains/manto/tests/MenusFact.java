package com.mng.robotest.tests.domains.manto.tests;

import java.io.Serializable;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.domain.TestFromFactory;

public class MenusFact implements TestFromFactory, Serializable {

	private static final long serialVersionUID = -5780907750259210736L;

	public enum Section {
		PEDIDOS("Pedidos"),
		VALES("Vales"),
		CLIENTES("Clientes"),
		DEVOLUCIONES("Devoluciones"),
		ADMINISTRACION_TRANSPORTES("Administración transportes"),
		BLOQUEO_PRODUCTOS("Bloqueo Productos"),
		MARKETPLACES("Marketplaces"),
		BLOQUES("Bloques"),
		ATENCION_AL_CLIENTE("Atencion al cliente"),
		ESTADISTICAS("Estadisticas."),
		TIENDAS_FISICAS("Tiendas físicas"),
		PRODUCTOS("Productos"),
		ORDEN("Orden"),
		PROMOCIONES("Promociones"),
		REBAJAS("Rebajas"),
		OTRAS("Otras");
		
		private final String cabecera;
		private Section(String cabecera) {
			this.cabecera = cabecera;
		}
		public String getCabecera() {
			return cabecera;
		}
	}	
	
	private final Section section;
	int prioridad = 1;
	
	/**
	 * Constructor para invocación desde @Factory
	 */
	public MenusFact(Section section, int prioridad) {
		this.section = section;
		this.prioridad = prioridad;
	}
	
	@Override
	public String getIdTestInFactory() {
		if (section!=null) {
			return section.getCabecera();
		}
		return "";
	}

	@Test(
		testName="MAN009",			
		groups={"Manto", "Canal:desktop_App:all"},
		description="Consulta de menús")
	public void consultaMenus() {
		new Man900(section).execute();
	}
}
