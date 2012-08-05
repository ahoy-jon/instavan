/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.inject.Produces;
import javax.inject.Qualifier;

/**
 *
 * @author waxzce
 */
public class APIkeyInjector {
    @Produces
    @SincerelyAPI
    public String getapikey(){
        return "GB0I6Q1QUCBWR8WYO5IQX5LRDCP2OX3B5I8BQTHQ";
    }
    
    
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
    public @interface SincerelyAPI {
        
    }
}
