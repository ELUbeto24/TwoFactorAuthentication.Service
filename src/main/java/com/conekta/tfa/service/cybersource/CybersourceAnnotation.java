package com.conekta.tfa.service.cybersource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.autoconfigure.SpringBootApplication;

// Indicamos en que contexto existira la anotacion
@Retention(RetentionPolicy.RUNTIME)
@SpringBootApplication (scanBasePackages = {"com.conekta.tfa.service"})
public @interface CybersourceAnnotation {

}
