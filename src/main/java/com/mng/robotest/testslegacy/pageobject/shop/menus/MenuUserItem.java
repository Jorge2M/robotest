package com.mng.robotest.testslegacy.pageobject.shop.menus;

import static com.mng.robotest.tests.conf.AppEcom.outlet;
import static com.mng.robotest.tests.conf.AppEcom.shop;
import static com.mng.robotest.tests.conf.AppEcom.votf;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabeceraMostFrequent.IconoCabecera;
import com.mng.robotest.testslegacy.pageobject.shop.menus.desktop.ModalUserSesionShopDesktop.MenuUserDesktop;
import com.mng.robotest.testslegacy.pageobject.shop.menus.device.SecMenusUserDevice.MenuUserDevice;

public class MenuUserItem {
	
	public enum UserMenu {
		LUPA(Arrays.asList(shop, outlet, votf)),
		INICIAR_SESION(Arrays.asList(shop, outlet)),
		CERRAR_SESION(Arrays.asList(shop, outlet)),
		REGISTRATE(Arrays.asList(shop, outlet)),
		MI_CUENTA(Arrays.asList(shop, outlet)),
		FAVORITOS(Arrays.asList(shop, votf)),
		BOLSA(Arrays.asList(shop, outlet)),
		MIS_COMPRAS(Arrays.asList(shop)),
		PEDIDOS(Arrays.asList(outlet)),
		MANGO_LIKES_YOU(Arrays.asList(shop)),
		AYUDA(Arrays.asList(shop, outlet, votf)),
		CAMBIO_PAIS(Arrays.asList(outlet));

		List<AppEcom> apps;
		private UserMenu(List<AppEcom> apps) {
			this.apps = apps;
		}
		
		public List<AppEcom> getAppsSupported() {
			return this.apps;
		}
	}
	
	public enum TypeMenu {
		ICONO_CABECERA_SHOP_DESKTOP_MOBILE,
		MENU_USER_DESKTOP,
		MENU_USER_DEVICE
	}
	
	private final UserMenu menu;
	private final Channel channel;
	private final AppEcom app;
	private final ElementPage link;
	private final TypeMenu type;

	public MenuUserItem(UserMenu menu, Channel channel, AppEcom app) {
		this.menu = menu;
		this.channel = channel;
		this.app = app;
		this.link = getMenuLink(menu);
		this.type = getTypeMenu(link);
	}
	
	public UserMenu getMenu() {
		return menu;
	}
	public ElementPage getLink() {
		return link;
	}
	public TypeMenu getType() {
		return type;
	}
	
	private TypeMenu getTypeMenu(ElementPage link) {
		if (link instanceof IconoCabecera) {
			return TypeMenu.ICONO_CABECERA_SHOP_DESKTOP_MOBILE;
		}
		if (link instanceof MenuUserDesktop) {
			return TypeMenu.MENU_USER_DESKTOP;
		}
		if (link instanceof MenuUserDevice) {
			return TypeMenu.MENU_USER_DEVICE;
		}
		return null;
	}
	
	private ElementPage getMenuLink(UserMenu menu) {
		switch (menu) {
			case LUPA:
				return getMenuLupa();
			case INICIAR_SESION:
				return getMenuIniciarSesion();
			case CERRAR_SESION:
				return getMenuCerrarSesion();
			case REGISTRATE:
				return getMenuRegistrate();
			case MI_CUENTA:
				return getMenuMiCuenta();
			case FAVORITOS:
				return getMenuFavoritos();
			case MIS_COMPRAS:
				return getMenuMisCompras();
			case PEDIDOS:
				return getMenuPedidos();
			case MANGO_LIKES_YOU:
				return getMenuMangoLikesYou();
			case AYUDA:
				return getMenuAyuda();
			case CAMBIO_PAIS:
				return getMenuCambioPais();
			default:
				return null;
		}
	}
	
	private ElementPage getMenuLupa() {
		return IconoCabecera.LUPA;
	}
	
	private ElementPage getMenuIniciarSesion() {
		return IconoCabecera.INICIAR_SESION;
	}
	
	private ElementPage getMenuCerrarSesion() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.CERRAR_SESION;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.CERRAR_SESION;
		}
		return null;
	}
	
	private ElementPage getMenuRegistrate() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.REGISTRATE;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.REGISTRATE;
		}
		return null;
	}
	
	private ElementPage getMenuMiCuenta() {
		if (app==AppEcom.outlet || channel==Channel.tablet) {
			if (channel==Channel.desktop) {
				return IconoCabecera.MICUENTA;
			}
			if (channel.isDevice()) {
				return MenuUserDevice.MI_CUENTA;
			}
		}
		if (app==AppEcom.shop || app==AppEcom.votf) {
			return IconoCabecera.MICUENTA;
		}
		return null;
	}
	
	private ElementPage getMenuFavoritos() {
		if (channel.isDevice()) {
			return MenuUserDevice.FAVORITOS;
		}

		return IconoCabecera.FAVORITOS;
	}
	
	private ElementPage getMenuMisCompras() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.MIS_COMPRAS;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.MIS_COMPRAS;
		}
		return null;
	}
	
	private ElementPage getMenuPedidos() {
		return MenuUserDevice.PEDIDOS;
	}
	
	private ElementPage getMenuMangoLikesYou() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.MANGO_LIKES_YOU;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.MANGO_LIKES_YOU;
		}
		return null;
	}
	
	private ElementPage getMenuAyuda() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.AYUDA;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.AYUDA;
		}
		return null;
	}
	
	private ElementPage getMenuCambioPais() {
		return MenuUserDevice.CAMBIO_PAIS;
	}
}
