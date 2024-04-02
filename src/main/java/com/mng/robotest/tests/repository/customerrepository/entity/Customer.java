package com.mng.robotest.tests.repository.customerrepository.entity;

import java.util.List;

public class Customer {

    public Registration registration;
    public List<Email> emails;

    public boolean isContactable() {
    	return emails.get(0).contactable;
    }
    
    public boolean isOnline() {
    	return emails.get(0).webAccount;
    }
    
}
