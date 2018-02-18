package com.prperiscal.resolver.projection.utils;

import static lombok.AccessLevel.PRIVATE;

import com.prperiscal.resolver.projection.base.Projection;
import lombok.NoArgsConstructor;

/**
 * <p>Util methods to help with class operations.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@NoArgsConstructor(access = PRIVATE)
public final class ClassUtil {

    /**
     * <p>Method to retrieve a class object through the given class name.
     *
     * @param className The full class name
     * @return class <code><? extends Projection></code>
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Projection> classFromName(String className) {
        try {
            return (Class<? extends Projection>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(String.format("Projection '%s' is not available!", className), e);
        }
    }

    /**
     * <p>Converts a full class name into a single base name.
     *
     * @param fullClassName The full class name, including package information
     * @return String the base class name
     * @since 1.0.0
     */
    public static String removePackage(String fullClassName){
        return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
    }

}
