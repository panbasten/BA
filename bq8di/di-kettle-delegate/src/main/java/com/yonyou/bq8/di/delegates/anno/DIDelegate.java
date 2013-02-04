package com.yonyou.bq8.di.delegates.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.TYPE)
public @interface DIDelegate {
	public String type();
}
