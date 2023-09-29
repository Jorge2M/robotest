package com.mng.robotest.testslegacy.pageobject.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;


public class ListDataArticleGalery {

	List<DataArticleGalery> listNombreYRef = new ArrayList<>();

	public Iterator<DataArticleGalery> getIterator() {
		return this.listNombreYRef.iterator();
	}
	
	public void setListNombreYRef(List<DataArticleGalery> listNombreYRef) {
		this.listNombreYRef = listNombreYRef;
	}
	
	public int size() {
		if (this.listNombreYRef==null) {
			return 0;
		}
		return this.listNombreYRef.size();
	}
	
	public DataArticleGalery get(int i) {
		return this.listNombreYRef.get(i);
	}
	
	public void add(DataArticleGalery item) {
		this.listNombreYRef.add(item);
	}
	
	public List<DataArticleGalery> getArticlesRepeated() {
		List<DataArticleGalery> duplicates = new ArrayList<>();
		var nombreYRefSet = new TreeSet<>(new NombreYRefComparator());
		for(DataArticleGalery nombreYRef : this.listNombreYRef) {
			if(nombreYRef!=null && !nombreYRefSet.add(nombreYRef)) {
				duplicates.add(nombreYRef);
			}
		}
		
		return duplicates;
	}
	
	public DataArticleGalery getFirstArticleThatNotFitWith(ListDataArticleGalery otherList) {
		for (int i=0; i<this.size(); i++) {
			if (i>=otherList.size()) {
				return this.get(i);
			}
			if (!this.get(i).equals(otherList.get(i))) {
				return this.get(i);					
			}
		}
		
		return null;
	}
	
	public boolean isArticleListEquals(ListDataArticleGalery otherList, int articlesCompare) {
		for (int i=0; i<articlesCompare; i++) {
			if (!this.get(i).equals(otherList.get(i)))
			   return false;					
		}
		
		return true;
	}
	
	public boolean isPresentArticleWithReferencia(String referencia) {
		return listNombreYRef.stream()
		        .anyMatch(a -> a.getReferencia().compareTo(referencia)==0);
	}
	
	/**
	/* Construye una tabla HTML mediante DIVs con 2 columnas que incluyan todos los artículos del nodo actual y el anterior
	 */
	public String getTableHTLMCompareArticlesGaleria(ListDataArticleGalery otherListArticles) {
	   String result = "";
	   //Table with Divs
	   result += "<div style=\"display:table;margin:3px;\">";
	   
	   //Cabecera
	   result += "<div style=\"display:table-row;font-weight:bold;text-align:center;\">";
	   result += "<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\"> ANTERIOR </div>";
	   result += "<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\"> ACTUAL </div>";
	   result += "</div>";
	   
	   //Rows
	   Iterator<DataArticleGalery> it1 = this.getIterator();
	   Iterator<DataArticleGalery> it2 = otherListArticles.getIterator();
	   while (it1.hasNext() || it2.hasNext()) {
		   result += "<div style=\"display:table-row;\">";
		   
		   //Artículo nodo actual
		   result += "<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\">";
		   if (it1.hasNext()) {
			   result+=it1.next().toString();
		   }
		   result+="</div>";
		   
		   //Artículo nodo anterior
		   result += "<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\">";
		   if (it2.hasNext()) {
			   result+=it2.next().toString();
		   }
		   result+="</div>";
		   result+="</div>";
	   }
	   
	   result += "</div>";
	   return (result);
	}	
}


