package com.mng.robotest.tests.domains.micuenta.repository.beans;

import java.util.List;

public class Purchase {
	
    private String purchaseId;
    private String totalPrice;
    private double totalPriceAmount;
    private String totalPriceCurrency;
    private String paidBalanceAmount;
    private double paidBalanceAmountValue;
    private String paidBalanceAmountCurrency;
    private String paidAmount;
    private double paidAmountValue;
    private String paidAmountCurrency;
    private String paymentMethod;
    private String type;
    private String creationDateTime;
    private String subTotal;
    private double subTotalAmount;
    private String subTotalCurrency;
    private String shippingPrice;
    private double shippingPriceAmount;
    private String shippingPriceCurrency;
    private boolean hasFreeShipping;
    private String countryIso;
    private List<Order> orders;
    
    public String getFirstOrderId() {
    	return getOrders().get(0).getId();
    }
    
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getTotalPriceAmount() {
		return totalPriceAmount;
	}
	public void setTotalPriceAmount(double totalPriceAmount) {
		this.totalPriceAmount = totalPriceAmount;
	}
	public String getTotalPriceCurrency() {
		return totalPriceCurrency;
	}
	public void setTotalPriceCurrency(String totalPriceCurrency) {
		this.totalPriceCurrency = totalPriceCurrency;
	}
	public String getPaidBalanceAmount() {
		return paidBalanceAmount;
	}
	public void setPaidBalanceAmount(String paidBalanceAmount) {
		this.paidBalanceAmount = paidBalanceAmount;
	}
	public double getPaidBalanceAmountValue() {
		return paidBalanceAmountValue;
	}
	public void setPaidBalanceAmountValue(double paidBalanceAmountValue) {
		this.paidBalanceAmountValue = paidBalanceAmountValue;
	}
	public String getPaidBalanceAmountCurrency() {
		return paidBalanceAmountCurrency;
	}
	public void setPaidBalanceAmountCurrency(String paidBalanceAmountCurrency) {
		this.paidBalanceAmountCurrency = paidBalanceAmountCurrency;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public double getPaidAmountValue() {
		return paidAmountValue;
	}
	public void setPaidAmountValue(double paidAmountValue) {
		this.paidAmountValue = paidAmountValue;
	}
	public String getPaidAmountCurrency() {
		return paidAmountCurrency;
	}
	public void setPaidAmountCurrency(String paidAmountCurrency) {
		this.paidAmountCurrency = paidAmountCurrency;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(String creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public double getSubTotalAmount() {
		return subTotalAmount;
	}
	public void setSubTotalAmount(double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}
	public String getSubTotalCurrency() {
		return subTotalCurrency;
	}
	public void setSubTotalCurrency(String subTotalCurrency) {
		this.subTotalCurrency = subTotalCurrency;
	}
	public String getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(String shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	public double getShippingPriceAmount() {
		return shippingPriceAmount;
	}
	public void setShippingPriceAmount(double shippingPriceAmount) {
		this.shippingPriceAmount = shippingPriceAmount;
	}
	public String getShippingPriceCurrency() {
		return shippingPriceCurrency;
	}
	public void setShippingPriceCurrency(String shippingPriceCurrency) {
		this.shippingPriceCurrency = shippingPriceCurrency;
	}
	public boolean isHasFreeShipping() {
		return hasFreeShipping;
	}
	public void setHasFreeShipping(boolean hasFreeShipping) {
		this.hasFreeShipping = hasFreeShipping;
	}
	public String getCountryIso() {
		return countryIso;
	}
	public void setCountryIso(String countryIso) {
		this.countryIso = countryIso;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}


}
