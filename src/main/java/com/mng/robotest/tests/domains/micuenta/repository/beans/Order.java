package com.mng.robotest.tests.domains.micuenta.repository.beans;

import java.util.List;

public class Order {
    private String id;
    private String purchaseId;
    private String shippingPrice;
    private double shippingPriceAmount;
    private String shippingPriceCurrency;
    private String type;
    private boolean hasFreeShipping;
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
    private String status;
    private String creationDate;
    private String creationDateTime;
    private List<Shipping> shippings;
    private List<Line> lines;
    private Delivery delivery;
    private Billing billing;
    private DetailOperations detailOperations;
    private boolean active;
    private String alert;
    private String deliveryMethod;
    private String deliveryStore;
    private String barcode;
    private String tax;
    private Double taxAmount;
    private String taxCurrency;
    private String subTotal;
    private double subTotalAmount;
    private String subTotalCurrency;
    private String seller;
    private String maxReturnDate;
    private List<String> unavailableReturnMethods;
    private boolean isPartner;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isHasFreeShipping() {
		return hasFreeShipping;
	}
	public void setHasFreeShipping(boolean hasFreeShipping) {
		this.hasFreeShipping = hasFreeShipping;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(String creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	public List<Shipping> getShippings() {
		return shippings;
	}
	public void setShippings(List<Shipping> shippings) {
		this.shippings = shippings;
	}
	public List<Line> getLines() {
		return lines;
	}
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}
	public Delivery getDelivery() {
		return delivery;
	}
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	public Billing getBilling() {
		return billing;
	}
	public void setBilling(Billing billing) {
		this.billing = billing;
	}
	public DetailOperations getDetailOperations() {
		return detailOperations;
	}
	public void setDetailOperations(DetailOperations detailOperations) {
		this.detailOperations = detailOperations;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public String getDeliveryStore() {
		return deliveryStore;
	}
	public void setDeliveryStore(String deliveryStore) {
		this.deliveryStore = deliveryStore;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public Double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getTaxCurrency() {
		return taxCurrency;
	}
	public void setTaxCurrency(String taxCurrency) {
		this.taxCurrency = taxCurrency;
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
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getMaxReturnDate() {
		return maxReturnDate;
	}
	public void setMaxReturnDate(String maxReturnDate) {
		this.maxReturnDate = maxReturnDate;
	}
	public List<String> getUnavailableReturnMethods() {
		return unavailableReturnMethods;
	}
	public void setUnavailableReturnMethods(List<String> unavailableReturnMethods) {
		this.unavailableReturnMethods = unavailableReturnMethods;
	}
	public boolean isPartner() {
		return isPartner;
	}
	public void setPartner(boolean isPartner) {
		this.isPartner = isPartner;
	}


}
