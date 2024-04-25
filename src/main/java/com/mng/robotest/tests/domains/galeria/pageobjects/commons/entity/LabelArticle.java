package com.mng.robotest.tests.domains.galeria.pageobjects.commons.entity;

import java.util.Arrays;
import java.util.List;

public enum LabelArticle {
	COMING_SOON (Arrays.asList(
			"Coming Soon",
			"Coming soon")),
	NEW_NOW (Arrays.asList(
			"New Now", 
			"جديد الآن", 
			"در حال حاضر جدید")),
	NEW_COLLECTION (Arrays.asList(
			"Nueva Colección", 
			"New Collection", 
			"Nouvelle Collection",		   
			"Neue Kollektion",	
			"Nova Col·lecció", 
			"Nova Colecção",
			"新品上市", 
			"Yeni Koleksiyon",
			"Новая Kоллекция",
			"Nieuwe Collectie",
			"Nuova Collezione",
			"Nowa Kolekcja",
			"Nova Colecção",
			"Νέα Συλλογή",
			"Nová kolekce",
			"Új kollekció",
			"Ny kollektion", 
			"Ny kolleksjon",
			"Noua colecție", 
			"تشكيلة جديدة",
			"Nova kolekcija",
			"کالکشن جدید")); 
	
	List<String> listTraducciones;
	private LabelArticle(List<String> listTraducciones) {
		this.listTraducciones = listTraducciones;
	}
	
	public List<String> getListTraducciones() {
		return listTraducciones;
	}
}
