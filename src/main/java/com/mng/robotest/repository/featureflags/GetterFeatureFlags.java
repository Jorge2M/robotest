package com.mng.robotest.repository.featureflags;

import java.util.HashMap;

import com.mango.mngfeatureflag.MngFeatureFlag;
import com.mango.mngfeatureflag.MngFeatureFlagBuilder;
import com.mango.mngfeatureflag.domain.FeatureFlagEnvironment;
import com.mango.mngfeatureflag.domain.context.Context;
import com.mng.robotest.domains.base.PageBase;

//Source of truth: 
//https://bitbucket.intranet.mango.es/projects/SB/repos/shop-featureflags/browse/features_pre.json

public class GetterFeatureFlags {

	private final static MngFeatureFlag mngFeatureFlag = new MngFeatureFlagBuilder()
            .withEnvironment(getEnvironment())
            .withApplicationName("shop")
            .withPollingIntervalMinutes(10)
            .build(); 
	
	public static boolean isEnabledPersonalization(String codeCountry) {
        return !isCountryInBlackList(codeCountry);		
	}
	
	private static boolean isCountryInBlackList(String codeCountry) {
        HashMap<String, String> params = new HashMap<>();
        params.put("country", codeCountry);
		return mngFeatureFlag.isEnabled("plp-personalization-blacklist", new Context(params));
	}
	
	private static FeatureFlagEnvironment getEnvironment () {
		if (PageBase.isEnvPRO()) {
			return FeatureFlagEnvironment.PRO; 
		}
		return FeatureFlagEnvironment.PRE;
	}
}
