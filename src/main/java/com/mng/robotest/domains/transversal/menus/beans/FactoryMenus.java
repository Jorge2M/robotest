package com.mng.robotest.domains.transversal.menus.beans;

import java.util.Arrays;

import com.mng.robotest.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

public class FactoryMenus {

	public enum MenuItem { 
		VESTIDOS_SHE, 
		CAMISAS_BASICAS_SHE, 
		CAMISAS_SHE, 
		ABRIGOS_SHE,
		CARDIGANS_Y_JERSEIS_JERSEIS_SHE,
		CARDIGANS_Y_JERSEIS_SHE,
		ABRIGOS_HE };
	
	public static MenuWeb get(MenuItem menu) {
		switch (menu) {
		case VESTIDOS_SHE:
			return getVestidosShe();
		case CAMISAS_BASICAS_SHE, CAMISAS_SHE:
			return getCamisasBasicasShe();
		case ABRIGOS_SHE:
			return getAbrigosShe();
		case CARDIGANS_Y_JERSEIS_JERSEIS_SHE, CARDIGANS_Y_JERSEIS_SHE:
			return getCardigansJerseisJerseisShe();
		case ABRIGOS_HE:
		default:
			return getAbrigosHe();
		}
	}

	private static MenuWeb getVestidosShe() {
		return new MenuWeb
			.Builder("Vestidos")
			.linea(LineaType.she)
			.group(GroupType.PRENDAS)
			.articles(Arrays.asList("Vestido", "Pichi", "Peto", "Mono", "Caftán", "Blusón"))
			.subMenusShop(Arrays.asList("largos", "cortos", "midi"))
			.subMenusOutlet(Arrays.asList("Vestidos largos", "Vestidos cortos", "Vestidos midi"))
			.build();
	}
	
	private static MenuWeb getCamisasBasicasShe() {
		return new MenuWeb
			.Builder("Camisas")
			.linea(LineaType.she)
			.group(GroupType.PRENDAS)
			.articles(Arrays.asList(
					"Camisa", "Blusa", "Bluson", "Blusón", "Top", "Bustier", "Body", "Camisero", 
					"Poncho", "Corsé"))
			.subMenus(Arrays.asList("camisas", "blusas", "básicas"))	
			.subMenu("básicas")
			.articlesSubMenu(Arrays.asList("Camisa", "Blusa", "Top"))
			.build();
	}
	
	private static MenuWeb getAbrigosShe() {
		return new MenuWeb
			.Builder("Abrigos")
			.linea(LineaType.she)
			.group(GroupType.PRENDAS)
			.articles(Arrays.asList("Anorak", "Abrigo", "Gabardina", "Trench", "Parka", "Chubasquero"))
			.subMenus(Arrays.asList("abrigos", "anoraks", "trench"))
			.subMenu("trench")
			.articlesSubMenu(Arrays.asList("Gabardina", "Parka", "Trench", "Chubasquero"))
			.build();
	}
	
	private static MenuWeb getCardigansJerseisJerseisShe() {
		return new MenuWeb
			.Builder("Cardigans y jerséis")
			.linea(LineaType.she)
			.group(GroupType.PRENDAS)
			.articles(Arrays.asList("Jersey", "Chaleco", "Top", "Sudadera", "Cárdigan", "Cardigan"))
			.subMenus(Arrays.asList("jerséis", "cárdigans"))
			.subMenu("jerséis")
			.build();
	}

	private static MenuWeb getAbrigosHe() {
		return new MenuWeb
			.Builder("Abrigos")
			.linea(LineaType.he)
			.group(GroupType.PRENDAS)
			.articles(Arrays.asList("Abrigo", "Anorak"))
			.subMenus(Arrays.asList("abrigos", "anoraks"))
			.build();
	}
}
