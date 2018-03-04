[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5cbcdfbd2776482aa230da3b327ec034)](https://www.codacy.com/app/prperiscal/spring-projection-resolver?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=prperiscal/spring-projection-resolver&amp;utm_campaign=Badge_Grade)

# Spring Projection Resolver

## Overview
Spring module to facilitate resolving projections by name. Offers a ProjectionResolver bean which can be injected in any object to resolve any projection available in the spring scope.



### What is a projection
Projections are a way for a client to request only specific fields from an object instead of the entire object. Using projections when you only need a few fields from an object is a good way to self-document your code, reduce payload of responses, and even allow the server to relax an otherwise time consuming computation or IO operation. You can read more on the motivation on [projections](https://github.com/linkedin/rest.li/wiki/Projections)

Therefore a projection will be, somehow, related with a more general model object. The purpose of this module is to retrieve, in a elegant and easy way, a projection by the name and the associated model object.

## Getting Started
* In a Maven .pom file:
```
<dependency>
  <groupId>com.prperiscal</groupId>
  <artifactId>spring-projection-resolver</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```
Also the repository will be necessary
```
<repositories>
  <repository>
    <id>Pablo-spring-projection-resolver</id>
    <url>https://packagecloud.io/Pablo/spring-projection-resolver/maven2</url>
    <releases>
      <enabled>true</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>
</repositories>
```


* In a Gradle build.gradle file:
```
compile 'com.prperiscal:spring-projection-resolver:1.0.0-SNAPSHOT'
```
Also the repository will be necessary
```
repositories {
    maven {
        url "https://packagecloud.io/Pablo/spring-projection-resolver/maven2"
    }
}
```

## How to use it

* Any projection which wants to be resolve has to implement the 'Projection' interface on this module (com.prperiscal.spring.resolver.projection.base)

* The main service application class needs to be annotated with: 
```
@EnableProjectionResolver
```
This enable the projection scan on spring startup.

* And just inject the projection resolve bean and starts resolving!

```
    @Autowire
    private ProjectionResolver projectionResolver;
```

For example, having a module base class "User" and we want to resolve projection dynamically:
```
final Class<? extends Projection> targetType = projectionResolver.resolve(User.class, projectionName).orElse(
                UserBase.class);
```

Projections can also be resolved by their short name. For example, a projection for _User_ called _UserWithGroups_ can be resolved by:

```
final Class<UserWithGroups> targetType = projectionResolver.resolve(User.class, "WithGroups").orElse(
                UserBase.class);
```

## Contributing

Please read [CONTRIBUTING](https://gist.github.com/prperiscal/900729941edc5d5ddaaf9e21e5055a62) for details on our code of conduct, and the process for submitting pull requests to us.

## Workflow

Please read [WORKFLOW-BRANCHING](https://gist.github.com/prperiscal/ce8b8b5a9e0f79378475243e2d227011) for details on our workflow and branching directives. 


