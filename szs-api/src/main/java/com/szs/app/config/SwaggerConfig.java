package com.szs.app.config;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {


  @Bean
  public OpenAPI openAPI() {

    // Security 스키마 설정
    SecurityScheme securityScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("Authorization")
        .in(SecurityScheme.In.HEADER)
        .name(HttpHeaders.AUTHORIZATION);

    // Security 요청 설정
    SecurityRequirement addSecurityItem = new SecurityRequirement();
    addSecurityItem.addList("Authorization");

    Info info = new Info()
        .title("Springdoc API 문서")
        .version("1.0.0")
        .description("spring docs swagger UI")
        .contact(new Contact().name("noah").email("alsltnpf1209@gmail.com"));

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("Authorization", securityScheme))
        // .security(Arrays.asList(addSecurityItem))
        .addSecurityItem(addSecurityItem)
        .info(info);
  }
}
