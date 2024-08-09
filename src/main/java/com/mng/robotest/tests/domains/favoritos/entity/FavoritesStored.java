package com.mng.robotest.tests.domains.favoritos.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

public class FavoritesStored {

	List<Favorite> listArticulos = new ArrayList<>();
	
	public List<Favorite> getListArticulos() {
		return this.listArticulos;
	}
	
	public void clear() {
		this.listArticulos.clear();
	}
	
	public boolean isEmpty() {
		return (getListArticulos().isEmpty());
	}
	
	public void addArticulo(Favorite articulo) {
		this.listArticulos.add(articulo);
	}
	
	public void addArticlesToLista(List<ArticuloScreen> listArticles) {
		var listFavorites = getFromListArticles(listArticles);
		addToLista(listFavorites);
	} 
	
	public void addToLista(List<Favorite> lista) {
		Iterator<Favorite> it = lista.iterator();
		while (it.hasNext()) {
			this.addArticulo(it.next());
		}
	}
	
	public void removeArticlesFromLista(List<ArticuloScreen> listArticles) {
		var listFavorites = getFromListArticles(listArticles);
		removeFromLista(listFavorites);
	}
	
	private List<Favorite> getFromListArticles(List<ArticuloScreen> listaArticles) {
		List<Favorite> listFavorites = new ArrayList<>();
		for (var article : listaArticles) {
			listFavorites.add(Favorite.from(article));
		}
		return listFavorites;
	}
	
	public void removeFromLista(List<Favorite> lista) {
		Iterator<Favorite> it = lista.iterator();
		while (it.hasNext()) {
			this.removeArticulo(it.next());
		}
	}
	
	public void removeArticulo(Favorite articulo) {
		List<Favorite> listResult = new ArrayList<>();
		for (var artFavs : this.listArticulos) {
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
	
	public Favorite getArticulo(int index) {
		return this.listArticulos.get(index);
	}
	
	/**
	 * Metodo que setea el articulo dentro de una lista de articulos a partir de su referencia
	 * @param articulo Articulo con precio y referencia a agregar a la lista
	 */
	public void updateArticulo(Favorite articulo) {
		ListIterator<Favorite> it = this.listArticulos.listIterator();
		Favorite articuloTemp;
		while (it.hasNext()) {
			articuloTemp = it.next();
			if(articuloTemp.getReferencia().compareTo(articulo.getReferencia())==0)
				it.set(articulo);
		}
	}
	
	public String getListArtDescHTML() {
		String listaStr = "";
		Iterator<Favorite> itArt = this.listArticulos.iterator();
		while (itArt.hasNext()) {
			Favorite art = itArt.next();
			String articuloStr = art.getReferencia() + " [color: " + art.getCodigoColor() + "]";
			listaStr = listaStr + articuloStr + "<br>";
		}
		
		return listaStr;
	}
}
