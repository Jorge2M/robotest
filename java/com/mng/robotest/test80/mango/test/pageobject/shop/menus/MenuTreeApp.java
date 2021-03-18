package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;


public class MenuTreeApp {
	private static final List<MenuTreeApp> libraryMenusForEachApp = new ArrayList<>();
	public final Set<Menu1rstLevel> libraryMenusFirstLevel = new HashSet<>();
	AppEcom app;

    private MenuTreeApp(AppEcom app) {
    	this.app = app;
    }
    
    static synchronized MenuTreeApp of(AppEcom app) {
    	for (MenuTreeApp menuTree : libraryMenusForEachApp) {
    		if (app==menuTree.getApp()) {
    			return menuTree;
    		}
    	}
    	
    	//If doesnt exists -> creation, initialization and store in cache
    	MenuTreeApp menuTree = new MenuTreeApp(app);
    	menuTree.setInitialMenus();
    	menuTree.getLibraryMenusForEachApp().add(menuTree);
    	return menuTree;
    }
    
    public List<MenuTreeApp> getLibraryMenusForEachApp() {
    	return libraryMenusForEachApp;
    }
    
    public static Menu1rstLevel getMenuLevel1From(AppEcom app, KeyMenu1rstLevel keyMenu) {
    	return (MenuTreeApp.of(app).getMenuLevel1From(keyMenu));
    }
    
    public static Menu2onLevel getMenuLevel2From(Menu1rstLevel menu1rstLevel, String nombreMenu2onLevel) {
    	KeyMenu2onLevel key2onLevel = KeyMenu2onLevel.from(menu1rstLevel, nombreMenu2onLevel.toLowerCase());
    	return (getMenuLevel2From(key2onLevel));
    }
    
    private Menu1rstLevel getMenuLevel1From(KeyMenu1rstLevel key) {
    	for (Menu1rstLevel menu1rstLevel : libraryMenusFirstLevel) {
    		if (key.equals(menu1rstLevel.getKey())) {
    			return menu1rstLevel;
    		}
    	}
        
    	Menu1rstLevel newMenu = Menu1rstLevel.from(this.app, key, key.dataMenu, false, "*".split(","));
//    	menus1rstLevel.add(newMenu); 
    	return newMenu;
    }

    private static Menu2onLevel getMenuLevel2From(KeyMenu2onLevel key) {
        for (Menu2onLevel menu2onLevel : key.menu1rstLevel.getListMenus2onLevel()) {
            if (key.equals(menu2onLevel.getKey())) {
            	return menu2onLevel;
            }
        }
        
        return null;
    }
    
    Set<Menu1rstLevel> getMenus1rstLevel() {
    	return libraryMenusFirstLevel;
    }
    
    AppEcom getApp() {
    	return this.app;
    }
    
    //Initialization...
    
    private void setInitialMenus() {
    	addMenu1rstLevelMujerNuevo();
    	addMenus2onLevelOfMujerVestidos();
    	addMenus2onLevelOfMujerCamisas();
    	addMenus2onLevelOfHombreAbrigosAnoraks();
    	//addMenu1rstLevelHombreZapatos();
    }
    
    private void addMenu1rstLevelMujerNuevo() {
    	KeyMenu1rstLevel keyMujerNuevo = KeyMenu1rstLevel.from(LineaType.she, null/*sublineaType*/, "New Now");
	    Menu1rstLevel menu1rstLevelNuevo = Menu1rstLevel.from(app, keyMujerNuevo, "nuevo", false, "*".split(","));
	    libraryMenusFirstLevel.add(menu1rstLevelNuevo);
    }
    
    private void addMenus2onLevelOfMujerVestidos() {
    	KeyMenu1rstLevel keyMujerVestidos = KeyMenu1rstLevel.from(LineaType.she, null, "vestidos");
    	Menu1rstLevel menu1rstLevelVestidos = Menu1rstLevel.from(
    		app, keyMujerVestidos, "vestidos", true, "Vestido,Pichi,Peto,Mono,Caftán,Blusón".split(",")
	    );
	    if (app==AppEcom.outlet) {
	    	menu1rstLevelVestidos.addMenu2onLevel(Menu2onLevel.from(menu1rstLevelVestidos, "vestidos-largos", "".split(",")));
	    	menu1rstLevelVestidos.addMenu2onLevel(Menu2onLevel.from(menu1rstLevelVestidos, "vestidos-cortos", "".split(",")));
	    	menu1rstLevelVestidos.addMenu2onLevel(Menu2onLevel.from(menu1rstLevelVestidos, "vestidos-midi", "".split(",")));
	    	//menu1rstLevelVestidos.addMenu2onLevel(Menu2onLevel.from(menu1rstLevelVestidos, "vestidos-de-fiesta", "".split(",")));
	    } else {
	    	menu1rstLevelVestidos.addMenu2onLevel(Menu2onLevel.from(menu1rstLevelVestidos, "largos", "".split(",")));
	    	menu1rstLevelVestidos.addMenu2onLevel(Menu2onLevel.from(menu1rstLevelVestidos, "cortos", "".split(",")));
	    	menu1rstLevelVestidos.addMenu2onLevel(Menu2onLevel.from(menu1rstLevelVestidos, "midi", "".split(",")));
	    	//menu1rstLevelVestidos.addMenu2onLevel(Menu2onLevel.from(menu1rstLevelVestidos, "fiesta", "".split(",")));
	    }
	
	    libraryMenusFirstLevel.add(menu1rstLevelVestidos);
    }
    
    private void addMenus2onLevelOfMujerCamisas() {
    	KeyMenu1rstLevel keyMujerCamisas = KeyMenu1rstLevel.from(LineaType.she, null, "camisas");
	    Menu1rstLevel menu1rstLeveCamisas  = Menu1rstLevel.from(
	    	this.app, keyMujerCamisas, "camisas", true, "Camisa,Blusa,Bluson,Blusón,Top,Bustier,Body,Camisero,Poncho,Corsé".split(",")
	    );
	    menu1rstLeveCamisas.addMenu2onLevel(Menu2onLevel.from(menu1rstLeveCamisas, "camisas", "".split(",")));
	    menu1rstLeveCamisas.addMenu2onLevel(Menu2onLevel.from(menu1rstLeveCamisas, "blusas", "".split(",")));
	    menu1rstLeveCamisas.addMenu2onLevel(Menu2onLevel.from(menu1rstLeveCamisas, "tops", "Camisa,Blusa,Top".split(",")));
	    libraryMenusFirstLevel.add(menu1rstLeveCamisas);
    }
    
    private void addMenus2onLevelOfHombreAbrigosAnoraks() {
    	KeyMenu1rstLevel keyMujerCamisas = KeyMenu1rstLevel.from(LineaType.he, null, "abrigos");
	    Menu1rstLevel menuAbrigos  = Menu1rstLevel.from(this.app, keyMujerCamisas, "abrigos", true, "Anoraks".split(","));
	    menuAbrigos.addMenu2onLevel(Menu2onLevel.from(menuAbrigos, "anoraks", "".split(",")));
	    libraryMenusFirstLevel.add(menuAbrigos);
    }
    
//    private void addMenu1rstLevelHombreZapatos() {
//	    if (app!=AppEcom.votf) {
//	    	KeyMenu1rstLevel keyHombreZapatos = KeyMenu1rstLevel.from(LineaType.he, null, "zapatos");
//	        Menu1rstLevel menu1rstLevelZapatos  = Menu1rstLevel.from(app, keyHombreZapatos, "accesorios-zapatos", true, "*".split(","));
//	        libraryMenusFirstLevel.add(menu1rstLevelZapatos);
//	    }
//    }
}
