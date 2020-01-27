package org.mng.testgoogle.access;

import java.util.Arrays;

import org.mng.testgoogle.access.datatmaker.Suites;
import org.mng.testgoogle.test.suite.SmokeTestSuite;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.domain.InputParamsBasic;
import com.mng.testmaker.domain.SuiteMaker;

public class CreatorSuiteRunTestGoogle extends CreatorSuiteRun {

	private CreatorSuiteRunTestGoogle() throws Exception {
		super();
	}
	private CreatorSuiteRunTestGoogle(InputParamsBasic inputParams) throws Exception {
		super(inputParams);
	}
	public static CreatorSuiteRunTestGoogle getNew() throws Exception {
		return new CreatorSuiteRunTestGoogle();
	}
	public static CreatorSuiteRunTestGoogle getNew(InputParamsBasic inputParams) throws Exception {
		return new CreatorSuiteRunTestGoogle(inputParams);
	}
	
	@Override
	public SuiteMaker getSuiteMaker() throws Exception {
		try {
			switch ((Suites)inputParams.getSuite()) {
			case SmokeTest:
				return (new SmokeTestSuite(inputParams));
			case OtherSuite:  
			default:
			}
		}
		catch (IllegalArgumentException e) {
			System.out.println("Suite Name not valid. Posible values: " + Arrays.asList(Suites.values()));
		}

		return null;
	}
}
