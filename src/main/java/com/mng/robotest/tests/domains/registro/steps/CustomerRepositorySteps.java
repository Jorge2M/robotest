package com.mng.robotest.tests.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.registro.exceptions.CustomerNotCreatedException;
import com.mng.robotest.tests.domains.registro.exceptions.CustomerNotFoundException;
import com.mng.robotest.tests.repository.customerrepository.CustomerRepositoryClient;
import com.mng.robotest.tests.repository.customerrepository.entity.Customer;

public class CustomerRepositorySteps extends StepBase {

	@Step (
		description="Creamos el cliente <b>#{email}</b> en el CR con contactabilidad=true online=false", 
		expected="El cliente se crea correctamente")
	public Customer makeClientWithContactability(String email) throws CustomerNotCreatedException {
		var customerClient = new CustomerRepositoryClient();
		var customerOpt = customerClient.createCustomerWithContactability(email);
		if (customerOpt.isEmpty()) {
			throw new CustomerNotCreatedException("Problem creating " + email + " CR customer"); 
		}
		return customerOpt.get();
	}

	@Validation(
		description="El usuario en CR tiene contactabilidad=true y online=true")
	public boolean isUserMantainsContactability(String email) {
		var customerClient = new CustomerRepositoryClient();  
		var customerOpt = customerClient.getCustomer(email);
		if (customerOpt.isEmpty()) {
			throw new CustomerNotFoundException("Problem getting " + email + "CR customer"); 
		}
		
		var customer = customerOpt.get();
		return customer.isContactable() && customer.isOnline();
	}
	
}
