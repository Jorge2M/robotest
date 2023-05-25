package com.mng.robotest.test.steps.shop.genericchecks;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Matchers.*;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.testreports.html.ResultadoErrores;
import com.github.jorge2m.testmaker.testreports.html.ResultadoErrores.Resultado;
import com.mng.robotest.test.steps.shop.genericchecks.CheckerImgsBroken;

import static org.junit.Assert.*;

public class CheckerImgsBrokenTest {

	@Test
	public void testCheckImgBrokenInWhiteList() {
		//Given
		CheckerImgsBroken checkerSpy = getCheckerSpy(new ArrayList<>(Arrays.asList(
				"https://st.mngbcn.com/images/imgWar/loadingGif/teen.gif")));
		
		//When
		ChecksTM checksTM = checkerSpy.check(null);
		
		//Then
		assertTrue(checksTM.get(0).isOvercomed());
	}
	
	@Test
	public void testCheckImgBrokenNotInWhiteList() {
		//Given
		CheckerImgsBroken checkerSpy = getCheckerSpy(new ArrayList<>(Arrays.asList(
				"https://shop.mango.com/images/prueba.gif")));
		
		//When
		ChecksTM checksTM = checkerSpy.check(null);
		
		//Then
		assertTrue(!checksTM.get(0).isOvercomed());
	}
	
	@Test
	public void testCheckImgBrokenMisedInWhiteList() {
		//Given
		CheckerImgsBroken checkerSpy = getCheckerSpy(new ArrayList<>(Arrays.asList(
				"https://st.mngbcn.com/images/imgWar/loadingGif/teen.gif",
				"https://shop.mango.com/images/prueba.gif")));
		
		//When
		ChecksTM checksTM = checkerSpy.check(null);
		
		//Then
		assertTrue(!checksTM.get(0).isOvercomed());
	}
	
	private CheckerImgsBroken getCheckerSpy(ArrayList<String> listBrokenImages) {
		ResultadoErrores resultadoImgsBroken = new ResultadoErrores(); 
		resultadoImgsBroken.setListaLogError(listBrokenImages);
		resultadoImgsBroken.setResultado(Resultado.ERRORES);
		
		CheckerImgsBroken checkerSpy = Mockito.spy(new CheckerImgsBroken(State.Warn));
		Mockito.doReturn(resultadoImgsBroken).when(checkerSpy).imagesBroken(any(WebDriver.class), any(Channel.class), anyInt());
		return checkerSpy;
	}

}
