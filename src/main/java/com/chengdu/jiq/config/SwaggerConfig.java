package com.chengdu.jiq.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.PathProvider;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathProvider(apiPathProvider())
                .select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringDemo")
                .description("测试程序, 仅供内部使用!")
                .termsOfServiceUrl("http://www.baidu.com")
//                .contact(new Contact("jiq", "http://www.baidu.com", "jiq408694711@163.com"))
                .version("1.0")
                .build();
    }

    private PathProvider apiPathProvider() {
        return new AbstractPathProvider() {
            @Override
            protected String applicationPath() {
                return "/springboot-service";
            }

            @Override
            protected String getDocumentationPath() {
                return "";
            }
        };
    }
}
