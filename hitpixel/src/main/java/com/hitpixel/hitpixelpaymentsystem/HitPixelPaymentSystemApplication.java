package com.hitpixel.hitpixelpaymentsystem;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Configuration
@EnableScheduling
@Slf4j
public class HitPixelPaymentSystemApplication {

    @Value("${server.port}")
    private String port;

    private String host = InetAddress.getLocalHost().getHostAddress();

    public HitPixelPaymentSystemApplication() throws UnknownHostException {
    }

    public static void main(String[] args) {
        SpringApplication.run(HitPixelPaymentSystemApplication.class, args);
    }

    /**
     * Model mapper to convert DAO TO DTO vice versa.
     *
     * @return model mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @PostConstruct
    public void init() {
        System.out.println(String.format("Using Spring-Doc-OpenAPI- \n" +
                "http://%s:%s/v3/api-docs/ \n" +
                "http://%s:%s/swagger-ui/index.html \n", this.host, port, this.host, port));
    }
}
