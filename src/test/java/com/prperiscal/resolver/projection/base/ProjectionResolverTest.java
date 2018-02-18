package com.prperiscal.resolver.projection.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
public class ProjectionResolverTest {

    private ProjectionResolver projectionResolver;

    @Before
    public void loadProjectionResolver() {
        Map<String, Class<? extends Projection>> projectionMap = Maps.newHashMap();
        projectionMap.put("ProjectionTest", ProjectionTest.class);

        projectionResolver = new ProjectionResolver(projectionMap);
    }

    @Test
    public void resolveTest() throws Exception {
        Class<? extends Projection> projection = projectionResolver.resolve("ProjectionTest").get();
        assertThat(projection).isAssignableFrom(ProjectionTest.class);
    }

    @Test
    public void resolveWithClassTest() throws Exception {
        Class<? extends Projection> projection = projectionResolver.resolve(Projection.class, "Test").get();
        assertThat(projection).isAssignableFrom(ProjectionTest.class);
    }

    @Test
    public void resolveWithClassMinusTest() throws Exception {
        Class<? extends Projection> projection = projectionResolver.resolve(Projection.class, "test").get();
        assertThat(projection).isAssignableFrom(ProjectionTest.class);
    }

    @Test(expected = NullPointerException.class)
    public void resolveWithClassNullTest() throws Exception {
        Class<? extends Projection> projection = projectionResolver.resolve(Projection.class, "null")
                                                                   .orElseThrow(NullPointerException::new);
    }

    @Test(expected = NullPointerException.class)
    public void resolveNullTest() throws Exception {
        Class<? extends Projection> projection = projectionResolver.resolve("null")
                                                                   .orElseThrow(NullPointerException::new);
    }

}