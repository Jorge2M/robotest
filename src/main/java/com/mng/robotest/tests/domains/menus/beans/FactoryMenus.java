package com.mng.robotest.tests.domains.menus.beans;

import java.util.Arrays;

import com.mng.robotest.tests.domains.menus.pageobjects.MenuWeb;
import com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;

public class FactoryMenus {

	public enum MenuItem { 
		VESTIDOS_SHE, 
		CAMISAS_BASICAS_SHE, 
		CAMISAS_SHE, 
		ABRIGOS_SHE,
		JERSEIS_Y_CARDIGANS_JERSEIS_SHE,
		JERSEIS_Y_CARDIGANS_SHE,
		ABRIGOS_HE 
	}
	
	public static MenuWeb get(MenuItem menu) {
		switch (menu) {
		case VESTIDOS_SHE:
			return getVestidosShe();
		case CAMISAS_BASICAS_SHE, CAMISAS_SHE:
			return getCamisasBasicasShe();
		case ABRIGOS_SHE:
			return getAbrigosShe();
		case JERSEIS_Y_CARDIGANS_JERSEIS_SHE, JERSEIS_Y_CARDIGANS_SHE:
			return getCardigansJerseisJerseisShe();
		case ABRIGOS_HE:
		default:
			return getAbrigosHe();
		}
	}

	private static MenuWeb getVestidosShe() {
		return new MenuWeb
			.Builder("Vestidos")
			.linea(LineaType.SHE)
			.group(GroupType.PRENDAS)
			.articles(Arrays.asList("Vestido", "Pichi", "Peto", "Mono", "Caftán", "Blusón"))
			.subMenusShop(Arrays.asList("largos", "cortos", "midi"))
			.subMenusOutlet(Arrays.asList("Vestidos largos", "Vestidos cortos", "Vestidos midi"))
			.build();
	}
	
	private static MenuWeb getCamisasBasicasShe() {
		return new MenuWeb
			.Builder("Camisas")
			.linea(LineaType.SHE)
			.group(GroupType.PRENDAS)
			.articles(Arrays.asList(
					"Camisa", "Blusa", "Bluson", "Blusón", "Top", "Bustier", "Body", "Camisero", 
					"Poncho", "Corsé"))
			.subMenusShop(Arrays.asList("camisas", "blusas", "básicas"))	
			.subMenusOutlet(Arrays.asList("camisas manga larga", "camisas manga corta"))
			.subMenu("básicas")
			.articlesSubMenu(Arrays.asList("Camisa", "Blusa", "Top"))
			.build();
	}
	
	private static MenuWeb getAbrigosShe() {
		return new MenuWeb
			.Builder("Abrigos")
			.linea(LineaType.SHE)
			.group(GroupType.PRENDAS)
			.articles(Arrays.asList("Anorak", "Abrigo", "Gabardina", "Trench", "Parka", "Chubasquero"))
			.subMenusShop(Arrays.asList("abrigos", "gabardinas"))
			.subMenusOutlet(Arrays.asList("abrigos", "anoraks"))
			.subMenu("performance")
			.articlesSubMenu(Arrays.asList("Anorak"))
			.build();
	}
	
	private static MenuWeb getCardigansJerseisJerseisShe() {
		return new MenuWeb
			.Builder("Cárdigans y jerséis")
			.linea(LineaType.SHE)
			.group(GroupType.PRENDAS)
			.articles(Arrays.asList("Jersey", "Chaleco", "Top", "Sudadera", "Cárdigan", "Cardigan"))
			.subMenus(Arrays.asList("jerséis", "cárdigans"))
			.subMenu("jerséis")
			.build();
	}

	private static MenuWeb getAbrigosHe() {
		return new MenuWeb
			.Builder("Abrigos")
			.linea(LineaType.HE)
			.group(GroupType.PRENDAS)
			.articles(Arrays.asList("Abrigo", "Anorak", "Parka", "Trench", "Chaquetón", "Sobrecamisa", "Chaleco", "Chaqueta", "Cazadora"))
			.subMenusShop(Arrays.asList("abrigos", "trench"))
			.subMenusOutlet(Arrays.asList("todo", "chaquetas de piel"))
			.build();
	}
}
