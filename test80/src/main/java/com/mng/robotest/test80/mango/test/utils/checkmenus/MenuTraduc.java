package com.mng.robotest.test80.mango.test.utils.checkmenus;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.data.CodIdioma;

public class MenuTraduc {
	
	public enum MenuShe implements MenuI {
		
	    avance_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Nueva Temporada"),
	    	Translation.getNew(CodIdioma.FR, "Nouvelle Saison"))),
	    
	    nuevo(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "New Now"),
	    	Translation.getNew(CodIdioma.FR, "Nouveau"))),
	    
	    Oros_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Gold Plated Collection"),
	    	Translation.getNew(CodIdioma.FR, "Collection plaquée or"))),
	    
	    boho_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Bohemian Style"),
	    	Translation.getNew(CodIdioma.FR, "Style Bohème"))),
	    
	    AccessoriesEdition_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Accessories Edition"),
	    	Translation.getNew(CodIdioma.FR, "Édition Accessoires"))),
	    
	    essentials_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Essential prices"),
	    	Translation.getNew(CodIdioma.FR, "Essential prices"))),
	    
	    best_sellers_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Best sellers"),
	    	Translation.getNew(CodIdioma.FR, "Best sellers"))),
	    
	    Rebajas_she_desktop(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Rebajas"),
	    	Translation.getNew(CodIdioma.FR, "Soldes"))),
	    
	    promocionado_nueva_temporada_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Promoción especial"),
	    	Translation.getNew(CodIdioma.FR, "Promotion spéciale"))),
	    
	    vestidos_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Vestidos"),
	    	Translation.getNew(CodIdioma.FR, "Robe"))),
	    
	    monos_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Monos"),
	    	Translation.getNew(CodIdioma.FR, "Combinaison"))),
	    
	    camisas_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Camisas"),
	    	Translation.getNew(CodIdioma.FR, "Chemise"))),
	    
	    camisetas_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Camisetas y tops"),
	    	Translation.getNew(CodIdioma.FR, "T-shirts et tops"))),
	    
	    cardigans_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Cárdigans y jerséis"),
	    	Translation.getNew(CodIdioma.FR, "Gilet et Pull"))),
	    
	    chaquetas_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Chaquetas"),
	    	Translation.getNew(CodIdioma.FR, "Veste"))),
	    
	    abrigos_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Abrigos"),
	    	Translation.getNew(CodIdioma.FR, "Manteau"))),
	    
	    trajes_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Trajes"),
	    	Translation.getNew(CodIdioma.FR, "Costume"))),
	    
	    pantalones_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Pantalones"),
	    	Translation.getNew(CodIdioma.FR, "Pantalon"))),
	    
	    vaqueros_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Vaqueros"),
	    	Translation.getNew(CodIdioma.FR, "Jean"))),
	    
	    shorts_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Shorts"),
	    	Translation.getNew(CodIdioma.FR, "Short"))),
	    
	    faldas_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Faldas"),
	    	Translation.getNew(CodIdioma.FR, "Jupe"))),
	    
	    bikinis_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Bikinis y bañadores"),
	    	Translation.getNew(CodIdioma.FR, "Bikini et maillot de bain"))),
	    
	    bodies_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Bodys y bralettes"),
	    	Translation.getNew(CodIdioma.FR, "Body et brassiere"))),
	    
	    zapatos_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Zapatos"),
	    	Translation.getNew(CodIdioma.FR, "Chaussure"))),
	    
	    bolsos_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Bolsos"),
	    	Translation.getNew(CodIdioma.FR, "Sac"))),
	    
	    bisuteria_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Bisutería"),
	    	Translation.getNew(CodIdioma.FR, "Bijoux"))),
	    
	    marroquineria_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Marroquinería"),
	    	Translation.getNew(CodIdioma.FR, "Maroquinerie"))),
	    
	    fulares_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Fulares"),
	    	Translation.getNew(CodIdioma.FR, "Echarpe"))),
	    
	    gafas_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Gafas de sol"),
	    	Translation.getNew(CodIdioma.FR, "Lunette de soleil"))),
	    
	    cinturones_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Cinturones"),
	    	Translation.getNew(CodIdioma.FR, "Ceinture"))),
	    
	    sombreros_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Sombreros"),
	    	Translation.getNew(CodIdioma.FR, "Chapeau"))),
	    
	    masaccesorios_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Más accesorios"),
	    	Translation.getNew(CodIdioma.FR, "Plus d'accessoires"))),
	    
	    bano2019_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Baño 2019"),
	    	Translation.getNew(CodIdioma.FR, "Bain 2019"))),
	    
	    ExclusivoOnline_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Exclusivo Online"),
	    	Translation.getNew(CodIdioma.FR, "Uniquement en ligne"))),
	    
	    weddings(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Weddings&Parties"),
	    	Translation.getNew(CodIdioma.FR, "Weddings&Parties"))),
	    
	    office_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Looks de oficina"),
	    	Translation.getNew(CodIdioma.FR, "Tenues de bureau"))),
	    
	    leather_and_more_coleccion(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Piel"),
	    	Translation.getNew(CodIdioma.FR, "Cuir"))),
	    
	    maternity_she(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Premamá"),
	    	Translation.getNew(CodIdioma.FR, "Vêtements de grossesse")));
	
	    private List<Translation> translations;
	    private MenuShe(List<Translation> translations) {
	    	this.translations = translations;
	    }
	
	    @Override
	    public String getId() {
	    	return this.name();
	    }
	    
	    @Override
		public List<Translation> getTranslations() {
			return this.translations;
		}
	}
	
	public enum MenuHe implements MenuI {
		
	    avance_he(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Nueva Temporada"),
	    	Translation.getNew(CodIdioma.FR, "Nouvelle Saison"))),
	    
	    nuevo_he(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "New Now"),
	    	Translation.getNew(CodIdioma.FR, "Nouveau"))),

	    DenimGuide_he(Arrays.asList(
	    	Translation.getNew(CodIdioma.ES, "Denim Guide"),
	    	Translation.getNew(CodIdioma.FR, "Denim Guide"))),

        essentials_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Essential prices"),
        	Translation.getNew(CodIdioma.FR, "Essential prices"))),

        best_sellers_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Best sellers"),
        	Translation.getNew(CodIdioma.FR, "Best sellers"))),

        Rebajas_he_desktop(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Rebajas"),
        	Translation.getNew(CodIdioma.FR, "Soldes"))),

        promocionado_nueva_temporada_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Promoción especial"),
        	Translation.getNew(CodIdioma.FR, "Promotion spéciale"))),
        
        camisas_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Camisas"),
        	Translation.getNew(CodIdioma.FR, "Chemises"))),

        camisetas_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Camisetas"),
        	Translation.getNew(CodIdioma.FR, "T-shirts"))),

        polos_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Polos"),
        	Translation.getNew(CodIdioma.FR, "Polos"))),

        cardigans_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Jerséis y cárdigans"),
        	Translation.getNew(CodIdioma.FR, "Pull-overs et cardigans"))),

        sudaderas_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Sudaderas"),
        	Translation.getNew(CodIdioma.FR, "Sweat"))),

        americanas_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Americanas"),
        	Translation.getNew(CodIdioma.FR, "Blazers"))),

        trajes_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Trajes"),
        	Translation.getNew(CodIdioma.FR, "Costumes"))),

        chaquetas_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Chaquetas"),
        	Translation.getNew(CodIdioma.FR, "Vestes"))),

        piel_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Piel"),
        	Translation.getNew(CodIdioma.FR, "Cuir"))),

        abrigos_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Abrigos"),
        	Translation.getNew(CodIdioma.FR, "Manteaux"))),

        pantalones_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Pantalones"),
        	Translation.getNew(CodIdioma.FR, "Pantalons"))),

        tejanos_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Vaqueros"),
        	Translation.getNew(CodIdioma.FR, "Jeans"))),

        bermudas_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Bermudas"),
        	Translation.getNew(CodIdioma.FR, "Bermudas"))),

        bano_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Bañadores"),
        	Translation.getNew(CodIdioma.FR, "Maillots de bain"))),

        underwear_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Ropa interior"),
        	Translation.getNew(CodIdioma.FR, "Sous-vêtements"))),
        
        sastreria_trajes(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Trajes"),
        	Translation.getNew(CodIdioma.FR, "Costumes"))),

        sastreria_americanas(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Americanas vestir"),
        	Translation.getNew(CodIdioma.FR, "Vestes de costume"))),

        sastreria_pantalones(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Pantalones vestir"),
        	Translation.getNew(CodIdioma.FR, "Pantaloons de costume"))),

        sastreria_chalecos(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Chalecos"),
        	Translation.getNew(CodIdioma.FR, "Gilets"))),

        sastreria_camisas(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Cammmmmmmmmmmmmisas vestir"),
        	Translation.getNew(CodIdioma.FR, "Chemises sans"))),

        sastreria_camisasnoniron(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Camisas Non-Iron"),
        	Translation.getNew(CodIdioma.FR, "repassage"))),

        sastreria_abrigos(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Abrigos tailored"),
        	Translation.getNew(CodIdioma.FR, "Manteaux Tailored"))),

        sastreria_zapatos(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Zapatos vestir"),
        	Translation.getNew(CodIdioma.FR, "Chaussures de costume"))),

        sastreria_corbatas(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Corbatas y pajaritas"),
        	Translation.getNew(CodIdioma.FR, "Cravates et nœuds de papillon"))),

        sastreria_cinturones(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Cinturones y tirantes"),
        	Translation.getNew(CodIdioma.FR, "Ceintures et bretelles"))),

        sastreria_complementos(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Complementos traje"),
        	Translation.getNew(CodIdioma.FR, "Accessoires de costume"))),

        CATHESUITGUIDE092018(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Suit Guide"),
        	Translation.getNew(CodIdioma.FR, "Suit guide"))),
        	
        zapatos_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Zapatos"),
        	Translation.getNew(CodIdioma.FR, "Chaussures"))),

        bolsas_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Bolsos"),
        	Translation.getNew(CodIdioma.FR, "Sacs"))),

        carteras_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Carteras"),
        	Translation.getNew(CodIdioma.FR, "Portefeuilles - Porte-monnaies"))),

        cinturones_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Cinturones y tirantes"),
        	Translation.getNew(CodIdioma.FR, "Ceintures et bretelles"))),

        corbatas_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Corbatas y pajaritas"),
        	Translation.getNew(CodIdioma.FR, "Cravates et nœuds papillon"))),

        sombreros_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Sombreros y gorras"),
        	Translation.getNew(CodIdioma.FR, "Chapeaux et casquettes"))),

        perfumes_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Perfumes"))),

        gafas_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Gafas de sol"),
        	Translation.getNew(CodIdioma.FR, "Lunettes de soleil"))),

        complementostraje_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Complementos traje"),
        	Translation.getNew(CodIdioma.FR, "Accessoires de costume"))),

        otros_accesorios_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Más accesorios"),
        	Translation.getNew(CodIdioma.FR, "Plus d'accessoires"))),

        lino_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Lino"),
        	Translation.getNew(CodIdioma.FR, "Lin"))),

        personalizacion_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Personalización"),
        	Translation.getNew(CodIdioma.FR, "Personnalisation"))),

        exclusivos(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Exclusivo Online"),
        	Translation.getNew(CodIdioma.FR, "Uniquement en ligne"))),

        leather_collection_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Leather and more"),
        	Translation.getNew(CodIdioma.FR, "Leather and more"))),

        the_basics_he(Arrays.asList(
        	Translation.getNew(CodIdioma.ES, "Básicos"),
        	Translation.getNew(CodIdioma.FR, "Les Essentiaux")));

	    private List<Translation> translations;
	    private MenuHe(List<Translation> translations) {
	    	this.translations = translations;
	    }
	
	    @Override
	    public String getId() {
	    	return this.name();
	    }
	    
	    @Override
		public List<Translation> getTranslations() {
			return this.translations;
		}
	}
	
	public static List<Label> getLabels(LineaType linea, CodIdioma codIdioma) {
		List<Label> listLabels = new ArrayList<>();
		List<MenuI> listMenus = getListMenus(linea);
		for (MenuI menu : listMenus) {
			List<Translation> translations = menu.getTranslations();
			for (Translation translation : translations) {
				if (translation.getCodIdioma() == codIdioma) {
					listLabels.add(translation);
					break;
				}
			}
		}
		return listLabels;
	}
	
	private static List<MenuI> getListMenus(LineaType linea) {
		switch (linea) {
		case she:
			return Arrays.asList(MenuShe.values());
		case he:
			return Arrays.asList(MenuHe.values());
		default:
			return null;
		}
	}
}