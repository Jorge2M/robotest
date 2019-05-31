package com.mng.robotest.test80.arq.annotations.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import com.mng.robotest.test80.arq.utils.State;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {
	String description() default "";
	State level() default State.Warn;
	boolean avoidEvidences() default false;
}