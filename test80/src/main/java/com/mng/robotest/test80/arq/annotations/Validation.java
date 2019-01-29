package com.mng.robotest.test80.arq.annotations;

import com.mng.robotest.test80.arq.utils.State;

@SuppressWarnings("javadoc")
public @interface Validation {
	String description() default "";
	State level() default State.Warn;
}
