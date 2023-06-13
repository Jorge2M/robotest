package com.mng.robotest.domains.micuenta.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.base.PageBase;


public abstract class ModalDetalleArticulo extends PageBase {

	public abstract boolean isVisible(int seconds);
	public abstract boolean isInvisible(int seconds);
	public abstract void clickAspaForClose();
	public abstract String getReferencia();
	public abstract String getNombre();
	public abstract String getPrecio();
	public abstract boolean isReferenciaValidaModal(String idArticulo);

	
	public static ModalDetalleArticulo make(Channel channel) {
		switch (channel) {
		case desktop:
			return new ModalDetalleArticuloDesktop();
		case mobile, tablet:
			return new ModalDetalleArticuloMobile();
		}
		return null;
	}
	
	public ModalDetalleArticuloDesktop getDesktopVersion() {
		return (ModalDetalleArticuloDesktop)this;
	}
	
	public boolean existsReferencia(String referenciaExpected, int seconds) {
		if (existsReferencia(referenciaExpected)) {
			return true;
		}
		for (int i=0; i<seconds; i++) {
			waitMillis(1000);
			if (existsReferencia(referenciaExpected)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean existsReferencia(String referenciaExpected) {
		try {
			String referencia = getReferencia();
			if (referencia.compareTo(referenciaExpected)==0) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
