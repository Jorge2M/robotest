package com.mng.robotest.test.utils.awssecrets;

import com.github.jorge2m.testmaker.conf.Log4jTM;

public interface GetterSecrets {

	public enum SecretType {
		SHOP_PERFORMANCE_USER("SHOP_USER"),
		SHOP_STANDARD_USER("SHOP_USER"),
		SHOP_FRANCIA_USER("SHOP_USER"),
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
		GetterSecrets getterSecrets = new GetterLocalFileSecrets();
		if (getterSecrets.isAvailable()) {
			return getterSecrets;
		}
		return new GetterHardCodeSecrets();
		//TODO de momento desactivamos los secretos hasta que se consigan dar de alta en el 
		//k8s-tools
//		getterSecrets = new GetterAwsSecrets();
//		if (getterSecrets.isAvailable()) {
//			return getterSecrets;
//		}
	}
	
	public default boolean isAvailable() {
		try {
			Secret credentials = getCredentials(SecretType.SHOP_PERFORMANCE_USER);
			return (credentials!=null && credentials.getUser()!=null);
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem retrieving Secret ", e);
			return false;
		}
		
	}
	
	public Secret getCredentials(SecretType secret);
	
}