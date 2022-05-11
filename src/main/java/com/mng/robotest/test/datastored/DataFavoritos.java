package com.mng.robotest.test.datastored;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.mng.robotest.test.generic.beans.ArticuloScreen;

/**
 * Clase que recopila los datos de la bolsa
 * @author jorge.munoz
 *
 */

public class DataFavoritos {

	ArrayList<ArticuloScreen> listArticulos = new ArrayList<>();
	
	public ArrayList<ArticuloScreen> getListArticulos() {
		return this.listArticulos;
	}
	
	public void clear() {
		this.listArticulos.clear();
	}
	
	public boolean isEmpty() {
		return (getListArticulos().size()==0);
	}
	
	public void addArticulo(ArticuloScreen articulo) {
		this.listArticulos.add(articulo);
	}
	
	public void addToLista(List<ArticuloScreen> lista) {
		Iterator<ArticuloScreen> it = lista.iterator();
		while (it.hasNext())
			this.addArticulo(it.next());
	}
	
	public void removeFromLista(List<ArticuloScreen> lista) {
		Iterator<ArticuloScreen> it = lista.iterator();
		while (it.hasNext())
			this.removeArticulo(it.next());
	}
	
	public void removeArticulo(ArticuloScreen articulo) {
		ArrayList<ArticuloScreen> listResult = new ArrayList<ArticuloScreen>();
		for (ArticuloScreen artFavs : this.listArticulos) {
			if (artFavs.getReferencia().compareTo(articulo.getReferencia())!=0 ||
				artFavs.getCodigoColor().compareTo(articulo.getCodigoColor())!=0) {
				listResult.add(artFavs);
			}
		}
		
		this.listArticulos = listResult;
	}
	
	public void removeArticulo(int index) {
		this.listArticulos.remove(index);
	}
	
	public ArticuloScreen getArticulo(int index) {
		return this.listArticulos.get(index);
	}
	
	/**
	 * Metodo que setea el articulo dentro de una lista de articulos a partir de su referencia
	 * @param articulo Articulo con precio y referencia a agregar a la lista
	 */
	public void updateArticulo(ArticuloScreen articulo) {
		ListIterator<ArticuloScreen> it = this.listArticulos.listIterator();
		ArticuloScreen articuloTemp;
		while (it.hasNext()) {
			articuloTemp = it.next();
			if(articuloTemp.getReferencia().compareTo(articulo.getReferencia())==0)
				it.set(articulo);
		}
	}
	
	/**
	 * @return una lista formateada con la lista de artículos
	 */
	public String getListArtDescHTML() {
		String listaStr = "";
		Iterator<ArticuloScreen> itArt = this.listArticulos.iterator();
		while (itArt.hasNext()) {
			ArticuloScreen art = itArt.next();
			String articuloStr = art.getReferencia() + " [" + art.getNombre() + "]" + " [color: " + art.getCodigoColor() + "]";
			listaStr = listaStr + articuloStr + "<br>";
		}
		
		return listaStr;
	}
}
