package com.mng.robotest.test.pageobject.shop.menus;

import static com.mng.robotest.conftestmaker.AppEcom.outlet;
import static com.mng.robotest.conftestmaker.AppEcom.shop;
import static com.mng.robotest.conftestmaker.AppEcom.votf;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabeceraOutlet_Mobil.IconoCabOutletMobil;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera_MostFrequent.IconoCabeceraShop_DesktopMobile;
import com.mng.robotest.test.pageobject.shop.menus.desktop.ModalUserSesionShopDesktop.MenuUserDesktop;
import com.mng.robotest.test.pageobject.shop.menus.device.SecMenusUserDevice.MenuUserDevice;

public class MenuUserItem {
	
	public enum UserMenu {
		lupa(Arrays.asList(shop, outlet, votf)),
		iniciarSesion(Arrays.asList(shop, outlet)),
		cerrarSesion(Arrays.asList(shop, outlet)),
		registrate(Arrays.asList(shop, outlet)),
		miCuenta(Arrays.asList(shop, outlet)),
		favoritos(Arrays.asList(shop, votf)),
		bolsa(Arrays.asList(shop, outlet)),
		misCompras(Arrays.asList(shop)),
		pedidos(Arrays.asList(outlet)),
		mangoLikesYou(Arrays.asList(shop)),
		ayuda(Arrays.asList(shop, outlet, votf)),
		cambioPais(Arrays.asList(outlet));

		List<AppEcom> apps;
		private UserMenu(List<AppEcom> apps) {
			this.apps = apps;
		}
		
		public List<AppEcom> getAppsSupported() {
			return this.apps;
		}
	}
	
	public enum TypeMenu {
		IconoCabeceraShop_DesktopMobile,
		MenuUserDesktop,
		MenuUserDevice,
		IconoCabOutletMobil
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
		if (link instanceof IconoCabeceraShop_DesktopMobile) {
			return TypeMenu.IconoCabeceraShop_DesktopMobile;
		}
		if (link instanceof MenuUserDesktop) {
			return TypeMenu.MenuUserDesktop;
		}
		if (link instanceof MenuUserDevice) {
			return TypeMenu.MenuUserDevice;
		}
		if (link instanceof IconoCabOutletMobil) {
			return TypeMenu.IconoCabOutletMobil;
		}
		return null;
	}
	
	private ElementPage getMenuLink(UserMenu menu) {
		switch (menu) {
			case lupa:
				return getMenuLupa();
			case iniciarSesion:
				return getMenuIniciarSesion();
			case cerrarSesion:
				return getMenuCerrarSesion();
			case registrate:
				return getMenuRegistrate();
			case miCuenta:
				return getMenuMiCuenta();
			case favoritos:
				return getMenuFavoritos();
			case misCompras:
				return getMenuMisCompras();
			case pedidos:
				return getMenuPedidos();
			case mangoLikesYou:
				return getMenuMangoLikesYou();
			case ayuda:
				return getMenuAyuda();
			case cambioPais:
				return getMenuCambioPais();
			default:
				return null;
		}
	}
	
	private ElementPage getMenuLupa() {
		if (app==AppEcom.outlet && channel==Channel.mobile) {
			return IconoCabOutletMobil.LUPA;
		}
		return IconoCabeceraShop_DesktopMobile.lupa;
	}
	
	private ElementPage getMenuIniciarSesion() {
		if (app==AppEcom.outlet || channel==Channel.tablet) {
			if (channel==Channel.desktop) {
				return IconoCabeceraShop_DesktopMobile.iniciarsesion;
			}
			if (channel.isDevice()) {
				return MenuUserDevice.iniciarsesion;
			}
		}
		if (app==AppEcom.shop || app==AppEcom.votf) {
			return IconoCabeceraShop_DesktopMobile.iniciarsesion;
		}
		return null;
	}
	
	private ElementPage getMenuCerrarSesion() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.CERRAR_SESION;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.cerrarsesion;
		}
		return null;
	}
	
	private ElementPage getMenuRegistrate() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.REGISTRATE;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.registrate;
		}
		return null;
	}
	
	private ElementPage getMenuMiCuenta() {
		if (app==AppEcom.outlet || channel==Channel.tablet) {
			if (channel==Channel.desktop) {
				return IconoCabeceraShop_DesktopMobile.micuenta;
			}
			if (channel.isDevice()) {
				return MenuUserDevice.micuenta;
			}
		}
		if (app==AppEcom.shop || app==AppEcom.votf) {
			return IconoCabeceraShop_DesktopMobile.micuenta;
		}
		return null;
	}
	
	private ElementPage getMenuFavoritos() {
		if (channel.isDevice()) {
			return MenuUserDevice.favoritos;
		}

		return IconoCabeceraShop_DesktopMobile.favoritos;
	}
	
	private ElementPage getMenuMisCompras() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.MIS_COMPRAS;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.miscompras;
		}
		return null;
	}
	
	private ElementPage getMenuPedidos() {
		return MenuUserDevice.pedidos;
	}
	
	private ElementPage getMenuMangoLikesYou() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.MANGO_LIKES_YOU;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.mangolikesyou;
		}
		return null;
	}
	
	private ElementPage getMenuAyuda() {
		if (channel==Channel.desktop) {
			return MenuUserDesktop.AYUDA;
		}
		if (channel.isDevice()) {
			return MenuUserDevice.ayuda;
		}
		return null;
	}
	
	private ElementPage getMenuCambioPais() {
		return MenuUserDevice.cambiopais;
	}
}
