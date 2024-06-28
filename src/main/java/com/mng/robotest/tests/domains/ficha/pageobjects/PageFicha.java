package com.mng.robotest.tests.domains.ficha.pageobjects;

import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.PageFichaDesktopNoGenesis;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.PageFichaDeviceNoGenesis;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecDetalleProduct.ItemBreadcrumb;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ColorType;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.SecSliders.Slider;
import com.mng.robotest.tests.domains.ficha.pageobjects.genesis.PageFichaGenesis;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.Talla;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

public interface PageFicha {

	public abstract boolean isPage(int seconds);
	public abstract boolean isFichaArticuloUntil(String refArticulo, int seconds);
	public abstract boolean isVisibleBolsaButton(int seconds);
	public abstract void clickAnadirBolsaButtonAndWait();
	public abstract void selectAnadirAFavoritosButton();
	public abstract void selectRemoveFromFavoritosButton();
	public abstract boolean isVisibleButtonElimFavoritos(int seconds);
	public abstract boolean isVisibleButtonAnadirFavoritos(int seconds);
	public abstract boolean isVisibleBuscarEnTiendaLink();
	public abstract boolean isModalNoStockVisible(int seconds);
	
	public abstract void selectBuscarEnTiendaLink();
	public abstract void selectEnvioGratisTienda();
	public abstract void selectDetalleDelProducto();
	public abstract void selectLinkCompartir();
	public abstract boolean isVisibleDescription();
	public abstract boolean isVisibleBreadcrumbs(int seconds);
	public abstract boolean isVisibleItemBreadCrumb(ItemBreadcrumb item);
	public abstract boolean isVisibleBlockKcSafety();
	
	public boolean isClickableColor(String colourCode);
	public void clickColor(int posColor);
	public void selectColor(String color);
	public void selectColorWaitingForAvailability(String colourCode);
	public String getCodeColor(ColorType colorType);
	public boolean isPresentColor(ColorType colorType);
	public List<String> getColorsGarment();
	public String getTituloArt();
	public String getPrecioFinalArticulo();
	public String getNombreColorSelected();
	public String getPrecioTachadoFromFichaArt();
	public void selectTallaByValue(String tallaValue);
	public boolean selectGuiaDeTallasIfVisible();
	public boolean isVisibleCapaAvisame();
	public boolean isVisibleAvisoSeleccionTalla();
	public boolean isVisibleListTallasForSelectUntil(int seconds);

	public ArticuloScreen getArticuloObject();
	public boolean isTallaUnica();
	public Talla getTallaSelected();
	public String getTallaSelectedAlf();
	public void selectTallaByValue(Talla talla);
	public void selectTallaByLabel(String tallaLabel);
	public void selectTallaByIndex(int posicion);
	public void selectFirstTallaAvailable();
	public String getTallaAlf(int posicion);
	public String getTallaCodNum(int posicion);
	public int getNumOptionsTallasNoDisponibles();
	public int getNumOptionsTallas();
	public void closeTallas();
	public int getNumColors();
	public boolean isVisibleSlider(Slider slider);
	public int getNumArtVisiblesSlider(Slider slider);
	
	public boolean isVisibleStickyContent(int seconds);
	public boolean isInvisibleStickyContent(int seconds);
	public boolean isVisibleReferenciaStickyContent(String referencia);
	public boolean isVisibleTallaLabelStickyContent(String tallaLabel);
	public boolean isVisibleColorNameStickyContent(String colorName);
	
	public boolean isVisibleModalDatosEnvio(int seconds);
	public void closeModalDatosEnvio();
	
	public boolean isVisibleGuiaTallas(int seconds);
	public boolean isInvisibleGuiaTallas(int seconds);
	public void closeGuiaTallas();
	
	public boolean isLinkAddBordado(int seconds);
	public void clickAddBordado();
	
	public static PageFicha make(Channel channel, AppEcom app, Pais pais) {
		if (pais.isFichaGenesis(pais, app)) {
			return new PageFichaGenesis();
		}
		if (channel.isDevice()) {
			return new PageFichaDeviceNoGenesis();
		} else {
			return new PageFichaDesktopNoGenesis();
		}
	}
	
}
