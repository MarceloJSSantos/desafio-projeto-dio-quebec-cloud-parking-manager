package br.marcelojssantos.cloudparkingmanager;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Gerenciador de estacionamento em Nuvem",
        description = "API REST",
        version = "1.0.1",
        contact = @Contact(name = "Marcelo Santos",
        url = "",email = "email@email.com")))
public class SwaggerConfig {
//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("springshop-public")
//                .packagesToScan("br.marcelojssantos.cloudparkingmanager.controller")
//                .pathsToMatch("/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("springshop-admin")
//                .pathsToMatch("/admin/**")
//                .build();
//    }

}
