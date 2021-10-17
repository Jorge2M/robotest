package com.mng.robotest.test80.mango.test.utils.awssecrets;


public interface GetterSecrets {

	public enum SecretType {
		SHOP_PERFORMANCE_USER("SHOP_USER"),
		SHOP_STANDARD_USER("SHOP_USER"),
		SHOP_ROBOT_USER("SHOP_USER"),
		SHOP_JORGE_USER("SHOP_USER"),
		MANTO_USER("MANTO_USER"),
		BROWSERSTACK_USER("BROWSERSTACK_USER"),
		VOTF_USER("VOTF_USER"),
		EMPLOYEE_DATA("EMPLOYEE_DATA");
		
		private final String secretName;
		private SecretType(String secretName) {
			this.secretName = secretName;
		}
		
		public String getSecretName() {
			return secretName;
		}
	}
	
	public static GetterSecrets factory() {
		GetterSecrets getterLocal = new GetterLocalSecrets();
		if (getterLocal.isAvailable()) {
			return getterLocal;
		}
		return new GetterAwsSecrets();
	}
	
	public default boolean isAvailable() {
		try {
			Secret credentials = getCredentials(SecretType.SHOP_PERFORMANCE_USER);
			return (credentials!=null && credentials.getUser()!=null);
		}
		catch (Exception e) {
			return false;
		}
		
	}
	
	public Secret getCredentials(SecretType secret);
	
}
