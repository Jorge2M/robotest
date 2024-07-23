package com.mng.robotest.tests.repository.chequeregalo.entity;

import java.math.BigDecimal;

public class ChequeRegaloOutput {

    private String transactionCode;
    private String transactionReference;
    private String certificateNumber;
    private BigDecimal certificateBalance;
    private String certificateExpirationDate;
    
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public String getTransactionReference() {
		return transactionReference;
	}
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public String getCvc() {
		return transactionReference.split(":")[1];
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	public BigDecimal getCertificateBalance() {
		return certificateBalance;
	}
	public void setCertificateBalance(BigDecimal certificateBalance) {
		this.certificateBalance = certificateBalance;
	}
	public String getCertificateExpirationDate() {
		return certificateExpirationDate;
	}
	public void setCertificateExpirationDate(String certificateExpirationDate) {
		this.certificateExpirationDate = certificateExpirationDate;
	}

}
