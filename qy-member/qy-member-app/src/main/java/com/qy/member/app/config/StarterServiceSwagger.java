package com.qy.member.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxp on 2022年3月27日09:35:12
 */
@Configuration
@EnableSwagger2
public class StarterServiceSwagger {
    @Bean
      public Docket createRestApi() {
        String swagger_path = "com.qy";

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swagger_path))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                //描述
                .description("更多swagger2相关文章请访问官网：http://swagger.io/")
                //创建人
                .contact(new Contact("dev","","dev@fast.ecp.com"))
                //版本
                .version("1.0")
                .build();
    }
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList= new ArrayList<SecurityScheme>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImFya2NzZC1qd3QifQ.eyJ1c2VyX2lkIjoiMTQzODM0NDY5ODM1Njg3OTM2MiIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE2NDg4OTQ4OTIsImp0aSI6IjBkOWM1MjFlLTBmZDktNGU4ZC04MTNiLTBmMDcyMjA0ZGE5MCIsImNsaWVudF9pZCI6IndlYiJ9.OXsf5gtHuU-xZ9GwOHml8Dq1dibNjw56WBpDv2IFo8McTUJh4N4z-Nd6Pdr6wGm6VPIpwuCFOhM-uWVkpaKhxkK9I-jZLCjaaXTxuaWimoFUsYk2Pk1NQonuwSht_CWh-lOqUD4J0kmhdrACam4-ASS-ufYuvNlWD1qaLgf-E2OzbLyOK3CFRuKPOnWOy4mc8tu11TTQiZlfHVhYDj87kjebX9rRVeu1M7KGMtdIYEfXfiBz-fnwvsnjewk5tJcAT48Ni3JDb5wGNixeAfmxmNWIdmFutorZVo108kgUJmdDzlhdHzqvGqCiIoTbg8b0HAm7G_jV9pQYazaXHngb1w"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        //.forPaths(PathSelectors.regex("^(?!auth).*$"))
                        //这里.any()代表所有形式的端口路径，上边的是menus的正则表达式
                        .forPaths(PathSelectors.any())
                        .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences=new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
}
