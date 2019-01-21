package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio;

import static com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.DataSearchDeliveryPoint.DataSearchDp.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.SecSelectDPoint.TypeDeliveryPoint;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.DataSearchDeliveryPoint.DataSearchDp;

public class TipoTransporteEnum {

	/**
	 * Enum que identifica los distintos tipos de transportes (servicios) que se
	 * ofrecen.
	 * 
	 */
	public enum TipoTransporte {
		/** Transporte Estandar */
		STANDARD("STANDARD", "S", "Estandar", true, "STD", false, false),
		/** Transporte Express */
		EXPRESS("EXPRESS","E", "Express", true, "EXP", false, false),
		/** [Droppoint] Transporte Packstation */
		PACKSTATION("PACKSTATION","P", "Packstation", false, "DRP", false, true, CodigoPostal),
		/** [Droppoint] Transporte Pickpoint */
		PICKPOINT("PICKPOINT","Q", "Pickpoint", false, "DRP", false, true, Provincia),
		/** [Droppoint] Transporte Droppoint */
		DROPPOINT("DROPPOINT","D", "Droppoint", false, "DRP", false, true, CodigoPostal),
		/** [Droppoint] Transporte PostNL */
		POSTNLPOINT("POSTNLPOINT","L", "PostNL", false, "DRP", false, true, CodigoPostal),
		/** [Droppoint] Transporte Postnord */
		POSTNORD("POSTNORD","O", "Postnord", false, "DRP", false, true, CodigoPostal),
		/** [Droppoint] Transporte ASM */
		ASM("ASM","A", "ASM", false, "DRP", false, true, CodigoPostal),
		/** [Droppoint] Transporte Chronopost */
		CHRONOPOST("CHRONOPOST","C", "CHR", false, "DRP", false, true, CodigoPostal),
		/** [Droppoint] Transporte Correos */
		CORREOS("CORREOS","R", "Correos", false, "DRP", false, true, CodigoPostal),
		/** [Droppoint] Transporte Bpost */
		BPOST("BPOST","B", "Bpost", false, "DRP", false, true, CodigoPostal),
		/** [Droppoint] Transporte InPost */
		INPOST("INPOST","I", "InPost", false, "DRP", false, true, CodigoPostal),
		/** [Droppoint] Transporte Collect Plus */
		COLLECT_PLUS("COLLECT_PLUS","F", "Collect Plus", false, "DRP", false, true, CodigoPostal),
		/** [Droppoint] Transporte Meest */
		MEEST("MEEST","G", "Meest", false, "DRP", false, true, CodigoPostal),
		/** Transporte Nextday */
		NEXTDAY("NEXTDAY","N", "24 Horas", true, "24H", false, false), //Urgente - No horario personalizado
		/** Transporte Nextday con franjas */
		NEXTDAY_FRANJAS("NEXTDAY_FRANJAS","X", "Next Day", true, "NXT", true, false), //Urgente - Sí horario personalizado
		/** Transporte Sameday */
		SAMEDAY("SAMEDAY","M", "Sameday", true, "SAM", false, false),
		/** Transporte Sameday / Nextday */
		SAMEDAY_NEXTDAY_FRANJAS("SAMEDAY_NEXTDAY_FRANJAS","Y", "Same Day Next Day", true, "SAM", true, false),
		/** Transporte Palau */
		PALAU("PALAU","U", "Palau", false, "PAL", false, false),
		/** Transporte Hangar */
		HANGAR("HANGAR","H", "Hangar", false, "HAN", false, false),
		/** Transporte Llica */
		LLICA("LLICA","Z", "Llica", false, "LLC", false, false),
		/** Transporte Envio a Tienda */
		TIENDA("TIENDA","T", "Envio Tienda", false, "TDA", false, true, Provincia);
		;
		
		/**
		 * Id identificativo del transporte para analytics
		 */
		private String idAnalytics = null;

		/**
		 * Código identificativo de cada tipo_transporte
		 */
		private String codigo = null;

		/**
		 * Descripcion interna del tipo de transporte
		 */
		private String descripcion = null;

		/**
		 * Indica si el tipo de transporte es de entrega a domicilio
		 */
		private boolean entregaDomicilio = false;

		/**
		 * Codigo con el que se comunica el transporte
		 */
		private String codigoIntercambio = null;
		
		/**
		 * Indica si es preciso seleccionar una franja horaria para la entrega
		 */
		private boolean franjaHoraria = false;

		/**
		 * Indica si el tipo de transporte es de tipo punto de entrega
		 */
		private boolean droppoint = false;
		
		/**
		 * Indica tipo de dato por el que es preciso buscar el Droppoint
		 */
		private DataSearchDp dataSearchDp = null;

		private TipoTransporte(String idAnalytics, String _code, String _descripcion, boolean entregaDomicilio,
							   String _codigoIntercambio, boolean franjaHoraria, boolean droppoint, DataSearchDp dataSearchDp) {
			this(idAnalytics, _code, _descripcion, entregaDomicilio, _codigoIntercambio, franjaHoraria, droppoint);
			this.dataSearchDp = dataSearchDp;
		}
		
		private TipoTransporte(String idAnalytics, String _code, String _descripcion, boolean entregaDomicilio,
							   String _codigoIntercambio, boolean franjaHoraria, boolean droppoint) {
			this.idAnalytics = idAnalytics;
			this.codigo = _code;
			this.descripcion = _descripcion;
			this.entregaDomicilio = entregaDomicilio;
			this.codigoIntercambio = _codigoIntercambio;
			this.franjaHoraria = franjaHoraria;
			this.droppoint = droppoint;
		}

		public String getIdAnalytics() {
			return this.idAnalytics.toLowerCase();
		}

		public String getCodigo() {
			return this.codigo;
		}

		public String getDescripcion() {
			return this.descripcion;
		}

		public boolean isEntregaDomicilio() {
			return this.entregaDomicilio;
		}

		public String getCodigoIntercambio() {
			return this.codigoIntercambio;
		}

		public boolean isFranjaHoraria() {
			return this.franjaHoraria;
		}
		
		public boolean isDroppoint() {
			return this.droppoint;
		}
		
		public DataSearchDp getDataSearchDp() {
			return this.dataSearchDp;
		}
		

		/**
		 * A partir de un tipo Transporte, se obtiene el TipoTransporte. Si por algun
		 * motivo (solo deberia ocurrir en entornos de desarrollo o test) fuese NULL
		 * podemos pasarle un transporte por defecto para que nos devuelva este.
		 * 
		 * @param tipo
		 * @param byDefault
		 *            el transporte por defecto si no se encuentra el principal
		 * @return Tipo de transporte.
		 */
		public static TipoTransporte getTipoTransporte(final String tipo, final TipoTransporte byDefault) {
			// Para un tipo null devolvemos el byDefault
			if (tipo == null) {
				return byDefault;
			}

			// Buscamos entre todos los valores
			for (TipoTransporte t : TipoTransporte.values()) {
				if (t.getCodigo().equals(tipo)) {
					return t;
				}
			}

			// Si no se ha encontrado intentamos buscarlo por el name del enum
			try {
				return TipoTransporte.valueOf(tipo);
			}
			// Si no existe el tipo que se pide se lanza esta excepcion. La controlamos para
			// evitar que malas invocaciones
			// al metodo generen casques. En este caso se devuelve el byDefault
			catch (IllegalArgumentException e) {
				return byDefault;
			}
		}

		/**
		 * A partir de un tipo Transporte, se obtiene el TipoTransporte
		 * 
		 * @param tipo
		 * @return Tipo de transporte.
		 */
		public static TipoTransporte getTipoTransporte(final String tipo) {
			// Si no existiera el transporte, por defecto que nos devuelva STANDARD
			return TipoTransporte.getTipoTransporte(tipo, STANDARD);
		}
		
		public TypeDeliveryPoint getTypeDeliveryPoint() {
			if (this.isDroppoint()) {
				if (this.compareTo(TIENDA)==0)
					return TypeDeliveryPoint.tienda;
				else
					return TypeDeliveryPoint.droppoint;
			}
			
			return TypeDeliveryPoint.any;
		}
	}
}
