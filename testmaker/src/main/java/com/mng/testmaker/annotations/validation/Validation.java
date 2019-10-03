package com.mng.testmaker.annotations.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.mng.testmaker.utils.State;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {
	String description() default "";
	State level() default State.Warn;
	boolean avoidEvidences() default false;
}
