package org.javakid.Finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinderApplication {

    public static void main(String[] args) {
	    SpringApplication.run(FinderApplication.class, args);
        System.out.println("Go to Swagger http://localhost:8080/swagger-ui.html");
    }
}
