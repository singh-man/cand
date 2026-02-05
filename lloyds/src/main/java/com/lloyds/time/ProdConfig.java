package com.lloyds.time;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.function.Function;

@Configuration
public class ProdConfig {

    @Bean
    @Profile("prod")
    public Function<String, String> helloWorld(@Qualifier("hello") Function<String, String> hello, @Qualifier("world") Function<String, String> world) {
        return hello.andThen(world).andThen(x -> x + "my");
    }
}
