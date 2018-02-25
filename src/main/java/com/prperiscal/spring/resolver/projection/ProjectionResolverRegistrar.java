package com.prperiscal.spring.resolver.projection;

import static java.util.stream.Collectors.toMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.prperiscal.spring.resolver.projection.base.Projection;
import com.prperiscal.spring.resolver.projection.base.ProjectionResolver;
import com.prperiscal.spring.resolver.projection.exception.IllegalProjectionDefinitionException;
import com.prperiscal.spring.resolver.projection.utils.ClassUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * <p>Configuration class to fetch and register all the projection objects.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
class ProjectionResolverRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    // patterned after Spring Integration IntegrationComponentScanRegistrar

    private ResourceLoader resourceLoader;

    private Environment environment;

    public ProjectionResolverRegistrar() {
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        scanProjections(metadata, registry);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private void scanProjections(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false,
                                                                                                              this.environment);
        scanner.setResourceLoader(this.resourceLoader);

        Set<String> basePackages = getBasePackages(metadata);

        AssignableTypeFilter assignableTypeFilter = new AssignableTypeFilter(Projection.class);
        scanner.addIncludeFilter(assignableTypeFilter);

        Set<String> projections = Sets.newHashSet();
        basePackages.stream().map(scanner::findCandidateComponents).forEachOrdered(
                candidateComponents -> candidateComponents.stream().map(BeanDefinition::getBeanClassName)
                                                          .forEach(projections::add));
        registerProjections(registry, projections);
    }

    /* for testing*/ void registerProjections(BeanDefinitionRegistry registry, Set<String> projections) {
        try {
            Map<String, Class<? extends Projection>> projectionMap = projections.stream().collect(
                    toMap(ClassUtil::removePackage, ClassUtil::classFromName));

            String className = ProjectionResolver.class.getSimpleName();
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ProjectionResolver.class);
            builder.addConstructorArgValue(projectionMap);

            BeanDefinitionHolder holder = new BeanDefinitionHolder(builder.getBeanDefinition(), className,
                                                                   new String[]{});
            BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
        } catch (IllegalStateException illegalState) {
            throw new IllegalProjectionDefinitionException(
                    "Error while trying to load projections. Basic projection class name must be unique between projections!",
                    illegalState.getCause());
        } catch (IllegalArgumentException illegalArgument) {
            throw new IllegalProjectionDefinitionException(illegalArgument.getMessage(), illegalArgument.getCause());
        }
    }

    private Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(EnableProjectionResolver.class.getCanonicalName());

        Set<String> basePackages = new HashSet<>();
        for(String pkg : (String[]) attributes.get("value")) {
            if(StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for(String pkg : (String[]) attributes.get("basePackages")) {
            if(StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for(Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }

        if(basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }

}