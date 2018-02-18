package com.prperiscal.resolver.projection.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

/**
 * <p>Resolver for registered projections.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class ProjectionResolver {

    private final Map<String, Class<? extends Projection>> projectionMap;

    /**
     * <p>Resolve a projection by full name.
     *
     * @param projectionName Name of the projection
     * @return Optional of the Projection class
     * @since 1.0.0
     */
    public Optional<Class<? extends Projection>> resolve(String projectionName) {
        Validate.notEmpty(projectionName, "The validated projectionName attribute is empty!");

        return Optional.ofNullable(projectionMap.get(projectionName));
    }

    /**
     * <p>Resolve a projection by combining names of the base object and the shot projection name.
     * <p>For example, a projection called <code>UserWithAllAttributes</code> which is a projection for <code>User.class</code>
     * can be resolved by <code>resolve(User.class, "WithAllAttributes")</code>.
     * <p>This facilitate defining a common standard name for different projections.
     *
     * @param sourceType Class from which the projection will be created
     * @param projectionName Short name of the projection
     * @return Optional of the Projection class
     * @since 1.0.0
     */
    public Optional<Class<? extends Projection>> resolve(Class<?> sourceType, String projectionName) {
        Validate.notNull(sourceType, "The validated sourceType attribute is null!");
        Validate.notEmpty(projectionName, "The validated projectionName attribute is empty!");

        return Optional.ofNullable(projectionMap.get(getFullClassName(sourceType, projectionName)));
    }

    private String getFullClassName(Class<?> sourceType, String projectionName){
        if(projectionName.startsWith(sourceType.getSimpleName())){
            return projectionName;
        }
        String projectionNameUpper = Character.toUpperCase(projectionName.charAt(0)) + projectionName.substring(1);
        return sourceType.getSimpleName().concat(projectionNameUpper);
    }
}
