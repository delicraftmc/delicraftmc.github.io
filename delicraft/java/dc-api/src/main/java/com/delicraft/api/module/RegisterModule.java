package com.delicraft.api.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegisterModule {
  String id();

  String name();

  String description() default "";

  String version() default "1.0.0";

  ModuleCategory category() default ModuleCategory.UTILITY;

  String[] authors() default {"Delicraft Team"};

  boolean defaultEnabled() default false;
}
