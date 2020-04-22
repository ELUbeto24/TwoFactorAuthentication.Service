package com.conekta.tfa.service.utility;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@Retention(RetentionPolicy.RUNTIME)
@SpringBootApplication(scanBasePackages = {"com.conekta.tfa.service."})
public @interface UtilitiesAnnotation {

}
