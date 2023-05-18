package com.example.mbanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class MbankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MbankingApplication.class, args);
    }
    @GetMapping("/test")
    public String testVerifyView() {
        return "auth/verify";
    }

}
