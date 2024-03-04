package uz.edm.edmapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EdmApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EdmApiApplication.class, args);
    }

}
