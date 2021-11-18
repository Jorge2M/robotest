package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Enumera los diferentes tipos de imagen con los que trabaja la tienda:
 * <ul>
 * <li>reverso</li>
 * <li>frontal</li>
 * <li>outfit</li>
 * <li>detalle</li>
 * <li>pasarela</li>
 * <li>bodegon</li>
 * </ul>
 * 
 * @author rmore
 * 
 */

public enum TipoImagenProducto implements Serializable {

	/**
	 * Imagenes de los colores
	 */
	BASE("pastillaBase", false, 0, "http://st.mngbcn.com/rcs/col_nrf_v3/", "https://st.mngbcn.com/rcs/col_nrf_v3/", ".png", 
			"", ""),
	/**
	 * Imagenes pastilla personalizada
	 */
	PROPIOS("pastillaPersonalizada", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/colv3/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/colv3/", ".png",
			"C", "PP"),
	/**
	 * Imagen del reverso
	 */
	REVERSO("reverso", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg",
			"R", "R"),
	/**
	 * Imagenes compuestas del outfit
	 */
	OUTFIT("outfit", true, 1, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/outfit/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/outfit/#size#/", ".jpg", 
			"-99999999_01", ""),
	/**
	 * Outfit de dos columnas
	 */
	OUTFIT_A2("outfitA2", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/outfit/#size#/A2/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/outfit/#size#/A2/", ".jpg", 
			"-99999999_02", ""),
	/**
	 * Outfit de tres columnas
	 */
	OUTFIT_A3("outfitA3", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/outfit/#size#/A3/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/outfit/#size#/A3/", ".jpg", 
			"-99999999_03", ""),
	/**
	 * Imagenes de detalles "generico
	 */
	DETALLES("detalles", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg",
			"", ""),
	/**
	 * Imagenes de detalles de producto
	 */
	DETALLES_1("detalles_1", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"D1", "D1"),
	/**
	 * Imagenes de detalles de producto
	 */
	DETALLES_2("detalles_2", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"D2", "D2"),
	/**
	 * Imagenes de detalles de producto
	 */
	DETALLES_3("detalles_3", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"D3", "D3"),
	/**
	 * Imagenes de detalles de producto
	 */
	DETALLES_4("detalles_4", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"D4", "D4"),
	/**
	 * Imagenes de detalles de producto
	 */
	DETALLES_5("detalles_5", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"D5", "D5"),
	/**
	 * Imagenes de detalles de producto
	 */
	DETALLES_6("detalles_6", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"D6", "D6"),
	/**
	 * Imagenes de detalles de producto
	 */
	DETALLES_7("detalles_7", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"D7", "D7"),
	/**
	 * Imagenes de detalles de producto
	 */
	DETALLES_8("detalles_8", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"D8", "D8"),
	/**
	 * Imagenes de detalles de producto
	 */
	DETALLES_9("detalles_9", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"D9", "D9"),
	/**
	 * Outfit de pasarela
	 */
	PASARELA("pasarela", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/pasarela/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/pasarela/#size#/", ".jpg", 
			"", "OP"),
	/**
	 * Imagen del producto en bodegon
	 */
	BODEGON("bodegon", true, 3, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg",
			"B", "B"),

	/**
	 * Imagen del producto en bodegon b3 fondo blanco
	 */
	BODEGON_B3("bodegon_b3", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"B3", "B3"),
	/**
	 * Imagen del producto en bodegon b4
	 */
	BODEGON_B4("bodegon_b4", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"B4", "B4"),
	/**
	 * Imagen frontal con fondo blanco
	 */
	FRONTAL_BLANCO("frontalBlanco", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"FB", "FB"),
	/**
	 * Imagen del reverso con fondo blanco
	 */
	REVERSO_BLANCO("reversoBlanco", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"RB", "RB"),
	/**
	 * Imagen de la suela del calzado
	 */
	SUELA("suela", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg", 
			"S", "S"),
	/**
	 * Imagen de la suela del calzado
	 */
	CENITAL("cenital", false, 0, "http://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/fotos/#size#/", ".jpg",
			"VC", "VC"),
	/**
	 * Configuracion para los videos
	 */
	VIDEO("video", false, 0, "http://st1.mngbcn.com/rcs/pics/static/T#temporada#/videos/S6/A#columns#/", "https://st.mngbcn.com/rcs/pics/static/T#temporada#/videos/S6/A#columns#/", ".mp4", 
			"", "V");

	private String identificador = null;
	private boolean principal = false;
	private int indexPrincipal = 0;
	private String directorio = null;
	private String directorioHttps = null;
	private String extension = null;
	private String sufijo = null;
	private String idDB = null;

	private static List<TipoImagenProducto> listPrincipales = null;

	static {
		TipoImagenProducto.listPrincipales = new LinkedList<>();
		for (TipoImagenProducto tipo : TipoImagenProducto.values()) {
			if (tipo.isPrincipal()) {
				int index = tipo.indexPrincipal - 1;
				if (index > TipoImagenProducto.listPrincipales.size()) {
					index = TipoImagenProducto.listPrincipales.size();
				}
				TipoImagenProducto.listPrincipales.add(index, tipo);
			}
		}
	}

	private TipoImagenProducto(final String identificador, final boolean principal, final int indexPrincipal, final String directorio, final String directorioHttps,
			final String extension, final String sufijo, final String idDB) {
		this.identificador = identificador;
		this.directorio = directorio;
		this.directorioHttps = directorioHttps;
		this.extension = extension;
		this.sufijo = sufijo;
		this.principal = principal;
		if (this.principal) {
			this.indexPrincipal = indexPrincipal;
		} else {
			this.indexPrincipal = 0;
		}
		this.idDB = idDB;
	}

	/**
	 * Nos da la lista de {@link TipoImagenProducto} que se consideran principales. Aquellas que permiten que una combinacion de producto+color sea visible.
	 * 
	 * @return tipos de imagenes de producto principales.
	 */
	public static List<TipoImagenProducto> getTiposPrincipales() {
		return Collections.unmodifiableList(TipoImagenProducto.listPrincipales);
	}

	/**
	 * 
	 * @param identificador
	 *			Identificador relacionado con el tipo de imagen. Debe ser igual al valor retornado por {@link #getIdentificador()} de alguno de los tipos
	 * @return {@link TipoImagenProducto} relacionada con el tipo
	 */
	public static TipoImagenProducto getTipoImagenFormIdentificador(final String identificador) {
		TipoImagenProducto tipo = null;
		for (TipoImagenProducto tip : TipoImagenProducto.values()) {
			if (tip.getIdentificador().equals(identificador)) {
				tipo = tip;
				break;
			}
		}
		return tipo;
	}

	public static TipoImagenProducto getTipoImagenProductoFromSufijo(final String sufijo) {
		TipoImagenProducto tipo = null;
		for (TipoImagenProducto tip : TipoImagenProducto.values()) {
			if (tip.getSufijo().equals(sufijo)) {
				tipo = tip;
			}
		}
		return tipo;
	}
	
	public static TipoImagenProducto getTipoImagenProductoFromIdDB(final String idDB) {
		TipoImagenProducto tipo = null;
		for (TipoImagenProducto tip : TipoImagenProducto.values()) {
			if (tip.getIdDB().equals(idDB)) {
				tipo = tip;
			}
		}
		return tipo;
	}	

	/**
	 * @return Acceso al identificador en forma de String:reverso, frontal, bodegon, outfut, detalle o pasarela.
	 */
	public String getIdentificador() {
		return this.identificador;
	}

	/**
	 * @return directorio parametrizado segun temporada y tamanyo donde residen las imagenes
	 */
	public String getDirectorio() {
		return this.directorio;
	}

	/**
	 * @return directorio parametrizado segun temporada y tamanyo donde residen las imagenes seguras
	 */
	public String getDirectorioHttps() {
		return this.directorioHttps;
	}

	/**
	 * @return extension de las imagenes
	 */
	public String getExtension() {
		return this.extension;
	}

	/**
	 * @return Sufijo opcional del nombre del fichero
	 */
	public String getSufijo() {
		return this.sufijo;
	}

	/**
	 * @return idDB Identificador de la tabla maestra de bbdd maestros_tipos_fotos
	 */
	public String getIdDB() {
		return this.idDB;
	}

	/**
	 * @return Si el tipo de imagen se cosidera princpal.
	 */
	public boolean isPrincipal() {
		return this.principal;
	}

	/**
	 * Indica si el Tipo de imagen de producto es un detalle
	 * 
	 * @return boolean
	 */
	public boolean isTipoDetalle() {
		return this.getIdDB().contains("D");
	}

	/**
	 * Indica si el tipo de imagen es de tipo outfit
	 * 
	 * @return boolean
	 */
	public boolean isTipoOutfit() {
		return this.getIdDB().contains("O") && !this.getIdDB().equals("OP");
	}

	/**
	 * Indica si el tipo de imagen es de tipo outfit
	 * 
	 * @return boolean
	 */
	public boolean isTipoPasarela() {
		return this.getIdDB().equals("OP");
	}

	/**
	 * Devuelve el indice del detalle
	 * 
	 * @return indice del detalle
	 */
	public int getIndexDetalle() {
		int indice = 1;
		if (this.isTipoDetalle()) {
			indice = Integer.valueOf(this.idDB.substring(1, 2)).intValue();
		}
		return indice;
	}

	/**
	 * Devuelve el numero de columnas que ocupa el outfit
	 * 
	 * @return indice del detalle
	 */
	public int getColumnas() {
		int columnas = 1;
		if (this.isTipoOutfit()) {
			columnas = Integer.valueOf(this.idDB.substring(1, 2)).intValue();
		}
		return columnas;
	}

	/**
	 * @param id
	 * @param numColumnas
	 * @return Devuelve el TipoImagenProducto, dado un identificador y teniendo en cuenta el numero de columnas. Esto se hace para identificar las outfits panoramicas
	 */
	public static TipoImagenProducto getTipoImagenProductoPanoramica(final String id, final int numColumnas) {
		TipoImagenProducto tipoImagenProducto = TipoImagenProducto.getTipoImagenFormIdentificador(id);
		if (tipoImagenProducto != null && tipoImagenProducto.getIdDB() != null) {
			if (tipoImagenProducto.isTipoOutfit() && numColumnas > 1) {
				String idDB = tipoImagenProducto.getIdDB();
				tipoImagenProducto = TipoImagenProducto.getTipoImagenProductoFromIdDB(idDB.substring(0, idDB.length() - 1).concat(String.valueOf(numColumnas)));
			}
		}
		return tipoImagenProducto;
	}

	/**
	 * Devuelve una lista de todos los {@link TipoImagenProducto} de detalles
	 * 
	 * @return lista de todos los {@link TipoImagenProducto} de detalles
	 */
	public static List<TipoImagenProducto> getDetallesTipoImagenes() {
		List<TipoImagenProducto> detalles = new LinkedList<>();
		for (TipoImagenProducto tip : TipoImagenProducto.values()) {
			if (tip.isTipoDetalle()) {
				detalles.add(tip);
			}
		}
		return detalles;
	}

	 /**
	 * A partir de de un tipo de outfit y el numero de columnas calcula el {@link TipoImagenProducto}
	 * 
	 * @param tipo
	 *			tipo de outfit ( O/P)
	 * @param numColumnas
	 *			numero de columnas
	 * @return {@link TipoImagenProducto}
	 */
	public static TipoImagenProducto getTipoOutfit(final String tipo, final int numColumnas) {
		TipoImagenProducto tip = null;
		if (tipo.equalsIgnoreCase("O")) {
			if (numColumnas == 1) {
				tip = TipoImagenProducto.OUTFIT;
			} else if (numColumnas == 2) {
				tip = TipoImagenProducto.OUTFIT_A2;
			} else {
				tip = TipoImagenProducto.OUTFIT_A3;
			}
		} else {
			tip = TipoImagenProducto.PASARELA;
		}
		return tip;
	}
}