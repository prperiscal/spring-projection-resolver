package com.prperiscal.spring.resolver.projection;

import static org.mockito.internal.util.collections.Sets.newSet;

import java.util.Map;

import com.google.common.collect.Maps;
import com.prperiscal.spring.resolver.projection.base.Projection;
import com.prperiscal.spring.resolver.projection.base.ProjectionResolver;
import com.prperiscal.spring.resolver.projection.exception.IllegalProjectionDefinitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectionResolverRegistrarTests {

    @Mock
    private BeanDefinitionRegistry beanDefinitionRegistry;

    @Test
    public void registerProjectionsTests() {
        Map<String, Class<? extends Projection>> projectionMap = Maps.newHashMap();
        projectionMap.put("ProjectionTest", ProjectionTest.class);

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ProjectionResolver.class);
        builder.addConstructorArgValue(projectionMap);

        BeanDefinitionHolder holder = new BeanDefinitionHolder(builder.getBeanDefinition(),
                                                               ProjectionResolver.class.getSimpleName(),
                                                               new String[]{});

        ProjectionResolverRegistrar projectionResolverRegistrar = new ProjectionResolverRegistrar();
        projectionResolverRegistrar
                .registerProjections(beanDefinitionRegistry, newSet(ProjectionTest.class.getCanonicalName()));
        Mockito.verify(beanDefinitionRegistry, Mockito.times(1))
               .registerBeanDefinition(holder.getBeanName(), holder.getBeanDefinition());
    }

    @Test(expected = IllegalProjectionDefinitionException.class)
    public void registerInvalidProjectionsTests() {
        ProjectionResolverRegistrar projectionResolverRegistrar = new ProjectionResolverRegistrar();
        projectionResolverRegistrar.registerProjections(beanDefinitionRegistry,
                                                        newSet(ProjectionTest.class.getCanonicalName(),
                                                               com.prperiscal.spring.resolver.projection.base.ProjectionTest.class
                                                                       .getCanonicalName()));
    }

}
