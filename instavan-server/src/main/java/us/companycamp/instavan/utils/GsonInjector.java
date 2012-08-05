/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class GsonInjector {
    
    @Produces
    @GsonInjector.GsonQualifier
    public Gson providegson(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
    public @interface GsonQualifier {
        
    }
    
}
