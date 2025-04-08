package dev.gabbriellps.gestao.hospitalar.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestão Hospitalar API")
                        .version("v1")
                        .description("API para gestão hospitalar")
                        .termsOfService("https://github.com/GabbrielLopes")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://github.com/GabbrielLopes")));
    }


}
