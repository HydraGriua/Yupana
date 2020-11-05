package com.acme.yupanaapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class OpenApiConfiguration {
    public OpenAPI yupanaOpenApi(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Yupana Application API")
                        .description(
                                "Yupana API implemented with Spring Boot RESTful service and documented using springdoc-openapi and OpenAPI 3.0"
                        ));
    }
}
