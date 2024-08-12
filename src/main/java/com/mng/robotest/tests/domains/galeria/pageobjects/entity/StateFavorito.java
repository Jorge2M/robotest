package com.mng.robotest.tests.domains.galeria.pageobjects.entity;

public enum StateFavorito { 
	MARCADO, 
	DESMARCADO;
	
	public StateFavorito getOpposite() {
		if (this==MARCADO) {
			return DESMARCADO;
		}
		return MARCADO;
	}
}
