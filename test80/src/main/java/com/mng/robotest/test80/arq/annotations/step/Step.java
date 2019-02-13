package com.mng.robotest.test80.arq.annotations.step;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.WhenSave;

@SuppressWarnings("javadoc")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Step {
	String description() default "";
	String expected() default "";
	WhenSave saveImagePage() default WhenSave.IfProblem;
	WhenSave saveErrorPage() default WhenSave.IfProblem;
	WhenSave saveHtmlPage() default WhenSave.Never;
	WhenSave saveNettraffic() default WhenSave.Never;
}
