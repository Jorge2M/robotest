package com.mng.robotest.domains.compra.beans;

import java.io.Serializable;

import com.mng.robotest.test.suites.PagosPaisesSuite.VersionPagosSuite;

public class ConfigCheckout implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 914173092359264587L;
	
	public final boolean checkPasarelas;
	public final boolean checkPagos;
	public final boolean checkManto;
	public final boolean checkMisCompras;
	public final boolean emailExists; 
	public final boolean checkSavedCard;
	public final boolean userIsEmployee;
	public final boolean acceptCookies;
	public final boolean checkPromotionalCode;
	public final boolean chequeRegalo;
	public final boolean storeCredit;
	public final boolean checkLoyaltyPoints;
	public final boolean stressMode;
	
	private ConfigCheckout(boolean checkPasarelas,	boolean checkPagos,	boolean checkManto,	boolean checkMisCompras,
			boolean emailExists, boolean checkSavedCard, boolean userIsEmployee, boolean acceptCookies,
			boolean checkPromotionalCode, boolean chequeRegalo,	boolean storeCredit, boolean checkLoyaltyPoints,
			boolean stressMode) {
		this.checkPasarelas = checkPasarelas;
		this.checkPagos = checkPagos;
		this.checkManto = checkManto;
		this.checkMisCompras = checkMisCompras;
		this.emailExists = emailExists; 
		this.checkSavedCard = checkSavedCard;
		this.userIsEmployee = userIsEmployee;
		this.acceptCookies = acceptCookies;
		this.checkPromotionalCode = checkPromotionalCode;
		this.chequeRegalo = chequeRegalo;
		this.storeCredit = storeCredit;
		this.checkLoyaltyPoints = checkLoyaltyPoints;
		this.stressMode = stressMode;
	}
	
	public Object clone() {
		Object obj=null;
		try{
			obj=super.clone();
		} 
		catch(CloneNotSupportedException ex){
			System.out.println(" no se puede duplicar");
		}
		return obj;
	}
	
	public static ConfigCheckout.Builder config() {
		return new ConfigCheckout.Builder();
	}
	
	public static class Builder {
		
		private boolean checkPasarelas;
		private boolean checkPagos;
		private boolean checkManto;
		private boolean checkMisCompras;
		private boolean emailExists; 
		private boolean checkSavedCard;
		private boolean userIsEmployee;
		private boolean acceptCookies = true;
		private boolean checkPromotionalCode;
		private boolean chequeRegalo;
		private boolean storeCredit;
		private boolean checkLoyaltyPoints;
		private boolean stressMode;
		
		public Builder version(VersionPagosSuite version) {
			this.checkPasarelas = version.validaPasarelas();
			this.checkPagos = version.validaPagos();
			this.checkManto = version.validaPedidosEnManto();
			this.checkMisCompras = version.forceTestMisCompras();
			return this;
		}
		
		public Builder checkPasarelas() {
			this.checkPasarelas = true;
			return this;
		}
		public Builder checkPasarelas(boolean check) {
			this.checkPasarelas = check;
			return this;
		}		
		public Builder checkPagos() {
			checkPasarelas();
			this.checkPagos = true;
			return this;
		}
		public Builder checkPagos(boolean check) {
			this.checkPagos = check;
			return this;
		}		
		public Builder checkManto() {
			checkPagos();
			this.checkManto = true;
			return this;
		}
		public Builder checkManto(boolean check) {
			this.checkManto = check;
			return this;
		}		
		public Builder checkMisCompras() {
			this.checkMisCompras = true;
			return this;
		}
		public Builder checkSavedCard() {
			this.checkSavedCard = true;
			return this;
		}
		public Builder checkSavedCard(boolean check) {
			this.checkSavedCard = check;
			return this;
		}		
		public Builder checkPromotionalCode() {
			this.checkPromotionalCode = true;
			return this;
		}
		public Builder checkPromotionalCode(boolean promotion) {
			this.checkPromotionalCode = promotion;
			return this;
		}		
		public Builder checkLoyaltyPoints() {
			this.checkLoyaltyPoints = true;
			return this;
		}
		public Builder emaiExists() {
			this.emailExists = true;
			return this;
		}
		public Builder emaiExists(boolean check) {
			this.emailExists = check;
			return this;
		}		

		public Builder userIsEmployee() {
			this.userIsEmployee = true;
			return this;
		}
		public Builder userIsEmployee(boolean employee) {
			this.userIsEmployee = employee;
			return this;
		}
		public Builder acceptCookies() {
			this.acceptCookies = true;
			return this;
		}
		public Builder acceptCookies(boolean check) {
			this.acceptCookies = check;
			return this;
		}		
		public Builder chequeRegalo() {
			this.chequeRegalo = true;
			return this;
		}
		public Builder storeCredit() {
			this.storeCredit = true;
			return this;
		}
		public Builder stressMode() {
			this.stressMode = true;
			return this;
		}		
		
		public ConfigCheckout build() {
			return (
				new ConfigCheckout(
						checkPasarelas, 	
						checkPagos,
						checkManto,
						checkMisCompras,
						emailExists,
						checkSavedCard,
						userIsEmployee,
						acceptCookies,
						checkPromotionalCode,
						chequeRegalo,
						storeCredit,
						checkLoyaltyPoints,
						stressMode));
		}
	}
}
