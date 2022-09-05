package com.mng.robotest.domains.micuenta.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;


public abstract class ModalDetalleArticulo extends PageBase {

	public abstract boolean isVisible(int maxSeconds);
	public abstract boolean isInvisible(int maxSeconds);
	public abstract void clickAspaForClose();
	public abstract String getReferencia();
	public abstract String getNombre();
	public abstract String getPrecio();
	public abstract boolean isReferenciaValidaModal(String idArticulo);

	
	public static ModalDetalleArticulo make(Channel channel) {
		switch (channel) {
		case desktop:
			return new ModalDetalleArticuloDesktop();
		case mobile:
		case tablet:
			return new ModalDetalleArticuloMobile();
		}
		return null;
	}
	
	public ModalDetalleArticuloDesktop getDesktopVersion() {
		return (ModalDetalleArticuloDesktop)this;
	}
	
	public boolean existsReferencia(String referenciaExpected, int maxSeconds) {
		if (existsReferencia(referenciaExpected)) {
			return true;
		}
		for (int i=0; i<maxSeconds; i++) {
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
