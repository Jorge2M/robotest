package com.mng.robotest.testslegacy.generic.beans;

import java.util.Calendar;
import java.util.Date;

public class ValeDiscount {
	
	private final String codigoVale;
	private final int porcDescuento;
	private final String textoCheckout;
	private final Calendar fechaInicio;
	private final Calendar fechaFin;

	public ValeDiscount(String codigoVale, int porcDescuento, String textoCheckout, Calendar fechaInicio, Calendar fechaFin) {
		this.codigoVale = codigoVale;
		this.porcDescuento = porcDescuento;
		this.textoCheckout = textoCheckout;
		this.fechaInicio = fechaInicio;
		this.fechaFin =  fechaFin;
	}	
	
	public ValeDiscount(String codigoVale, int porcDescuento, String textoCheckout) {
		this(codigoVale, porcDescuento, textoCheckout, getToday(), getTomorrow());
	}
	
	private static Calendar getToday() {
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		return today;
	}
	
	private static Calendar getTomorrow() {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTime(new Date());
		tomorrow.add(Calendar.DATE, 1);
		return tomorrow;
	}
	
	public String getCodigoVale() {
		return this.codigoVale;
	}

	public int getPorcDescuento() {
		return this.porcDescuento;
	}
	
	public String getTextoCheckout() {
		return this.textoCheckout;
	}

	public Calendar getFechaInicio() {
		return this.fechaInicio;
	}
	
	public Calendar getFechaFin() {
		return this.fechaFin;
	}

	public boolean isInDates() {
		Calendar currDtCal = Calendar.getInstance();
		return (currDtCal.getTimeInMillis() > this.fechaInicio.getTimeInMillis() &&
				currDtCal.getTimeInMillis() < this.fechaFin.getTimeInMillis());
	}
}
