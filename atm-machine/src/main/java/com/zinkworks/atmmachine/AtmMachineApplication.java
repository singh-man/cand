package com.zinkworks.atmmachine;

import com.zinkworks.atmmachine.notes.INoteDispenser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.stream.Stream;

@SpringBootApplication
public class AtmMachineApplication {

    @Value("${server.port}")
    private String port;

    @Value("${spring.h2.console.path}")
    private String h2Console;

    private String host = InetAddress.getLocalHost().getHostAddress();

    public AtmMachineApplication() throws UnknownHostException {
    }

    public static void main(String[] args) {
        SpringApplication.run(AtmMachineApplication.class, args);
    }

    @Bean
    public Stream<INoteDispenser> allNotesDispenser(@Qualifier("fifty") INoteDispenser dispenseNoteFifty,
                                                    @Qualifier("twenty") INoteDispenser dispenseNoteTwenty,
                                                    @Qualifier("ten") INoteDispenser dispenseNoteTen,
                                                    @Qualifier("five") INoteDispenser dispenseNoteFive) {
        return Stream.of(dispenseNoteFifty, dispenseNoteTwenty, dispenseNoteTen,
                dispenseNoteFive);
    }

    @PostConstruct
    public void init() {
        String hostPort = String.format("http://%s:%s", this.host, this.port);
        String x = new StringBuilder()
                .append("Using Spring-Doc-OpenAPI-\n")
                .append(hostPort + "/v3/api-docs/ \n")
                .append(hostPort + "/swagger-ui/index.html \n")
                .append(hostPort + h2Console)
                .toString();
        System.out.println(x);
    }
}