package com.eazybank.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(info = @Info(
        title = "EazyBank Accounts microservice OpenAPI Documentation",
        description = "EazyBank Accounts microservice REST API Documentation",
        version = "v1",
        contact = @Contact(
                name = "Shahrukh Tramboo",
                email = "mail@clayster.net",
                url = "https://www.clayster.net"
        ),
        license = @License(
                name = "Apache 2.0",
                url = "https://www.clayster.net"
        )
), externalDocs = @ExternalDocumentation(
        description = "EazyBak Accounts microservice REST API Documentation",
        url = "https://www.clayster.net"
))
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
