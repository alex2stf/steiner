package com.arise.steiner.config;


import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
//                .apis(RequestHandlerSelectors.basePackage("com.qualitance"))
            .apis(RequestHandlerSelectors.basePackage("com.qualitance"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
            "Node Microservice",
            "microservice used to create/store/serach files and documents",
            "1.0.0.-Beta",
            "term of use",
            new Contact("Alexandru Stefan", "none", "alexandru.stefan@todo.com"),
            "License of API", "API license URL", Collections.emptyList());
    }


}