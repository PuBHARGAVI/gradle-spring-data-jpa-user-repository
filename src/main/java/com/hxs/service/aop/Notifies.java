package com.hxs.service.aop;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *   Sample annotation to be used for AOP purposes.
 *
 *   Mostly for showing intent. Meaning, someone else can see this and get a hint that "something" happens when the
 *   annotated method is called.
 *
 * @author HSteidel
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Notifies {
}
