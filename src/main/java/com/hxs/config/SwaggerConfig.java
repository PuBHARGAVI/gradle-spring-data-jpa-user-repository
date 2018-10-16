package com.hxs.config;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 *  Simple Swagger2 Documentation Configuration
 *
 * @author HSteidel
 */
@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfig {

    @Value("${info.version}")
    private String appVersion;

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                    .select()
                    .paths(paths())
                    .apis(RequestHandlerSelectors.any())
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, getGlobalRespMessages())
                .globalResponseMessage(RequestMethod.POST, getGlobalRespMessages())
                .globalResponseMessage(RequestMethod.PUT, getGlobalRespMessages())
                .globalResponseMessage(RequestMethod.PATCH, getGlobalRespMessages())
                .globalResponseMessage(RequestMethod.DELETE, getGlobalRespMessages());
    }


    private List<ResponseMessage> getGlobalRespMessages(){
        final String API_ERROR = "ApiErrorResponse";
        return  Lists.newArrayList( new ResponseMessageBuilder()
                                        .code(500)
                                        .message("Unexpected error")
                                        .responseModel(new ModelRef(API_ERROR)).build(),
                                    new ResponseMessageBuilder()
                                        .code(400)
                                        .message("Invalid request")
                                        .responseModel(new ModelRef(API_ERROR)).build(),
                                    new ResponseMessageBuilder()
                                        .code(401)
                                        .message("Inadequate permissions")
                                        .responseModel(new ModelRef(API_ERROR)).build(),
                                    new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Operation forbidden")
                                        .responseModel(new ModelRef(API_ERROR)).build()

                );
    }

    private Predicate<String> paths() {
        return or(
                regex("/authentication.*"),
                regex("/users.*"),
                regex("/permissions.*"),
                regex("/groups.*"),
                regex("/roles.*"));
    }


    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                    .title("User CRUD API")
                    .description("Rest API documentation for a User CRUD API.")
                    .build();
    }
}