package com.mng.sapfiori.test.testcase.webobject;

import com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader.FieldFilterFechaI;
import com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader.FieldFilterFromListI;
import com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader.FieldFilterWithConditionsI;

public class FiltersPageClassifProductos {
	
	public enum FilterFecha implements FieldFilterFechaI {
		FechaValida("Fecha válida el");
		
		String label;
		private FilterFecha(String label) {
			this.label = label;
		}
		@Override
		public String getLabel() {
			return label;
		}
	}
	
	public enum FilterFromList implements FieldFilterFromListI {
		EsquemaNumeracion("Esquema numeración", false),
		Producto("Producto", true),
		ClaseProducto("Clase de producto", true),
		GrupoProductos("Grupo de productos", true),
		Sector("Sector", true);
		
		String label;
		boolean multiSelect;
		private FilterFromList(String label, boolean multiSelect) {
			this.label = label;
			this.multiSelect = multiSelect;
		}
		@Override
		public String getLabel() {
			return label;
		}
		@Override
		public boolean isMultiSelect() {
			return multiSelect;
		}
	}
	
	public enum FilterWithConditions implements FieldFilterWithConditionsI {
		DescripcionProducto("Descripción producto");
		
		String label;
		private FilterWithConditions(String label) {
			this.label = label;
		}
		
		@Override
		public String getLabel() {
			return label;
		}
	}
}
