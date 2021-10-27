package com.mng.robotest.test80.mango.test.utils.awssecrets;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mango.aws.secrets.client.AwsSecretsProvider;

public class GetterAwsSecrets implements GetterSecrets {
	
	@Override
	public Secret getCredentials(SecretType secret) {
		Log4jTM.getLogger().warn("Retrieving Aws Secrets 1...");
        AwsSecretsProvider<Secret> shopSecretAwsSecretsProvider = new AwsSecretsProvider<>(
                awsSecretsClientFor(getSecretArn(secret)),
                Secret.class,
                null
        );
        
        Log4jTM.getLogger().warn("Retrieving Aws Secrets 2...");
        
        Secret secretBean = null;
        try {
	        secretBean = shopSecretAwsSecretsProvider.getValue();
	        secretBean.setType(secret.toString());
        }
        catch (Exception e) {
        	Log4jTM.getLogger().warn("Retrieving Aws Secrets 3..." + e);
        }
        
        Log4jTM.getLogger().warn("Retrieving Aws Secrets 4..." + secret);
        
        return secretBean;
	}

    private String getSecretArn(SecretType secret) {
    	switch (secret) {
    	case SHOP_PERFORMANCE_USER:
		case SHOP_STANDARD_USER:
		case SHOP_ROBOT_USER:
		case SHOP_JORGE_USER:
    		return "arn:aws:secretsmanager:eu-west-1:687762684238:secret:pro/robotest/user-shop-ynflty";
    	case MANTO_USER:
    		return "arn:aws:secretsmanager:eu-west-1:687762684238:secret:pro/robotest/user-shop-ynflty";
    	case BROWSERSTACK_USER:
    		return "arn:aws:secretsmanager:eu-west-1:687762684238:secret:pro/robotest/user-shop-ynflty";
    	case VOTF_USER: 
    		return "arn:aws:secretsmanager:eu-west-1:687762684238:secret:pro/robotest/user-shop-ynflty";
    	case EMPLOYEE_DATA: 
    		return "arn:aws:secretsmanager:eu-west-1:687762684238:secret:pro/robotest/user-shop-ynflty";
    	default:
    		throw new IllegalArgumentException("Unexpected value '" + secret + "'");
    	}
    }

    private static AwsSecretsProvider.AWSSecretsClient awsSecretsClientFor(String secretKey) {
        return new AwsSecretsProvider.AWSSecretsClient(secretKey);
    }
	
}
