package com.mng.robotest.testslegacy.datastored;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.mng.robotest.tests.domains.favoritos.entity.Favorite;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

public class DataBag implements Serializable { 

	private static final long serialVersionUID = 1L;
	
	List<ArticuloScreen> listArticulos = new ArrayList<>();
	String importeTotal = "";
	String importeTransp = "" ;
	
	public List<ArticuloScreen> getListArticulos() {
		return this.listArticulos;
	}
	
	public void addArticles(DataBag dataBag) {
		listArticulos.addAll(dataBag.getListArticulos());
	}
	
	public String getImporteTotal() {
		return this.importeTotal;
	}
	
	public void setImporteTotal(String importeTotal) {
		this.importeTotal = importeTotal;
	}
	
	public float getImporteTotalFloat() {
		return (ImporteScreen.getFloatFromImporteMangoScreen(this.importeTotal));
	}
	
	public String getImporteTransp() {
		return this.importeTransp;
	}
	
	public void setImporteTransp(String importeTransp) {
		this.importeTransp = importeTransp;
	}
	
	public float getImporteTranspFloat() {
		return (ImporteScreen.getFloatFromImporteMangoScreen(this.importeTransp));
	}	

	public void clearBag() {
		this.listArticulos.clear();
	}
	
	public void add(ArticuloScreen articulo) {
		this.listArticulos.add(0, articulo);
	}
	
	public void remove(int index) {
		this.listArticulos.remove(index);
	}
	
	public void remove(Favorite favorite) {
		for (var article : listArticulos) {
			if (article.getReferencia().compareTo(favorite.getReferencia())==0 &&
				article.getCodigoColor().compareTo(favorite.getCodigoColor())==0) {
				removeArticulo(article);
				break;
			}
		}
	}
	
	public void removeArticulo(Object article) {
		this.listArticulos.remove(article);
	}	
	
	public ArticuloScreen getArticulo(int index) {
		return this.listArticulos.get(index);
	}
	
	public List<ArticuloScreen> getListArticlesTypeViewInBolsa() {
		List<ArticuloScreen> listToReturn = new ArrayList<>();
		ListIterator<ArticuloScreen> it = this.listArticulos.listIterator();
		while (it.hasNext()) {
			var articulo = it.next();
			var articuloInList = articulo.searchInList(listToReturn);
			if (articuloInList.isPresent()) {
				articuloInList.get().incrementarCantidad(1);
			} else {
				listToReturn.add(articulo);
			}
		}
		return listToReturn;
	}
	
	/**
	 * Metodo que setea el articulo dentro de una lista de articulos a partir de su referencia
	 * @param articulo Articulo con precio y referencia a agregar a la lista
	 */
	public void updateArticulo(ArticuloScreen articulo) {
		ListIterator<ArticuloScreen> it = this.listArticulos.listIterator();
		ArticuloScreen articuloTemp;
		while (it.hasNext()){
			articuloTemp = it.next();
			if(articuloTemp.getReferencia().compareTo(articulo.getReferencia())==0)
				it.set(articulo);
		}
	}	
	
	/**
	 * @return una lista formateada con la lista de artículos + descuentos
	 */
	public String getListArtDescHTML() {
		String listaStr = "";
		Iterator<ArticuloScreen> itArt = this.listArticulos.iterator();
		while (itArt.hasNext()) {
			ArticuloScreen art = itArt.next();
			String articuloStr = art.getReferencia() + " (" + art.getNombre() + ") ";
			String descuentoStr = "<b>Sin descuento</b>";
			if (art.getValePais()!=null) {	  
				descuentoStr = " - Descuento <b>" + art.getValePais().getPorcDescuento() + "%</b>";
			}
			listaStr = listaStr + articuloStr + descuentoStr + "<br>";
		}
		
		return listaStr;
	}
	
	/**
	 * @return la suma correspondiente al precio real total (con descuentos aplicados) de la lista de artículos 
	 */
	public float getPrecioRealTotalBolsa() {
		float total = 0;
		for (ArticuloScreen art : this.listArticulos)
			total += ImporteScreen.getFloatFromImporteMangoScreen(art.getPrecio()) * art.getNumero();
		
		return total;
	}
	
	/**
	 * @return la suma correspondiente al precio original total (sin descuentos aplicados) de la lista de artículos
	 */
	public float getPrecioOriginalTotalBolsa() {
		float total = 0;
		for (ArticuloScreen art : this.listArticulos)
			total += ImporteScreen.getFloatFromImporteMangoScreen(art.getPrecioSinDesc()) * art.getNumero();
		
		return total;
	}	
}
