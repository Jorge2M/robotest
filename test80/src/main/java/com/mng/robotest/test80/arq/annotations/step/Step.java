package com.mng.robotest.test80.arq.annotations.step;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;

@SuppressWarnings("javadoc")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Step {
	String description() default "";
	String expected() default "";
	SaveWhen saveImagePage() default SaveWhen.IfProblem;
	SaveWhen saveErrorPage() default SaveWhen.IfProblem;
	SaveWhen saveHtmlPage() default SaveWhen.Never;
	SaveWhen saveNettraffic() default SaveWhen.Never;
}
