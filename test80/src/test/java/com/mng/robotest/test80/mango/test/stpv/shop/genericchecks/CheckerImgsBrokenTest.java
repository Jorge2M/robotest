package com.mng.robotest.test80.mango.test.stpv.shop.genericchecks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.validateMockitoUsage;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.testreports.html.ResultadoErrores;
import com.github.jorge2m.testmaker.testreports.html.ResultadoErrores.Resultado;

class CheckerImgsBrokenTest {

	@Test
	void testCheckImgBrokenInWhiteList() {
		//Given
		CheckerImgsBroken checkerSpy = getCheckerSpy(Arrays.asList(
				"https://st.mngbcn.com/images/imgWar/loadingGif/teen.gif"));
		
		//When
		ChecksTM checksTM = checkerSpy.check(null);
		
		//Then
		assertTrue(checksTM.get(0).isOvercomed());
	}
	
	@Test
	void testCheckImgBrokenNotInWhiteList() {
		//Given
		CheckerImgsBroken checkerSpy = getCheckerSpy(Arrays.asList(
				"https://shop.mango.com/images/prueba.gif"));
		
		//When
		ChecksTM checksTM = checkerSpy.check(null);
		
		//Then
		assertTrue(!checksTM.get(0).isOvercomed());
	}
	
	@Test
	void testCheckImgBrokenMisedInWhiteList() {
		//Given
		CheckerImgsBroken checkerSpy = getCheckerSpy(Arrays.asList(
				"https://st.mngbcn.com/images/imgWar/loadingGif/teen.gif",
				"https://shop.mango.com/images/prueba.gif"));
		
		//When
		ChecksTM checksTM = checkerSpy.check(null);
		
		//Then
		assertTrue(!checksTM.get(0).isOvercomed());
	}
	
	private CheckerImgsBroken getCheckerSpy(List<String> listBrokenImages) {
		ResultadoErrores resultadoImgsBroken = new ResultadoErrores(); 
		resultadoImgsBroken.setListaLogError(listBrokenImages);
		resultadoImgsBroken.setResultado(Resultado.ERRORES);
		
		CheckerImgsBroken checkerSpy = Mockito.spy(CheckerImgsBroken.class); 
		Mockito.doReturn(resultadoImgsBroken).when(checkerSpy).imagesBroken(any(WebDriver.class), any(Channel.class), anyInt());
		return checkerSpy;
	}

}
