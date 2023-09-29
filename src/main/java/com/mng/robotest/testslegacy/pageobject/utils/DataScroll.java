package com.mng.robotest.testslegacy.pageobject.utils;

import com.github.jorge2m.testmaker.domain.suitetree.StepTM;

public class DataScroll {
	private StepTM step;
	private int paginaFinal;
	private int articulosMostrados;
	private boolean finalAlcanzado;
	private int articulosTotalesPagina;
	private int articulosDobleTamano;
	
	public StepTM getStep() {
		return step;
	}
	public void setStep(StepTM step) {
		this.step = step;
	}
	public int getPaginaFinal() {
		return paginaFinal;
	}
	public void setPaginaFinal(int paginaFinal) {
		this.paginaFinal = paginaFinal;
	}
	public int getArticulosMostrados() {
		return articulosMostrados;
	}
	public void setArticulosMostrados(int articulosMostrados) {
		this.articulosMostrados = articulosMostrados;
	}
	public boolean isFinalAlcanzado() {
		return finalAlcanzado;
	}
	public void setFinalAlcanzado(boolean finalAlcanzado) {
		this.finalAlcanzado = finalAlcanzado;
	}
	public int getArticulosTotalesPagina() {
		return articulosTotalesPagina;
	}
	public void setArticulosTotalesPagina(int articulosTotalesPagina) {
		this.articulosTotalesPagina = articulosTotalesPagina;
	}
	public int getArticulosDobleTamano() {
		return articulosDobleTamano;
	}
	public void setArticulosDobleTamano(int articulosDobleTamano) {
		this.articulosDobleTamano = articulosDobleTamano;
	}
}
