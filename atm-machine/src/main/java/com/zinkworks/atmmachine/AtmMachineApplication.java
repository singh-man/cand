package com.zinkworks.atmmachine;

import com.zinkworks.atmmachine.notes.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.function.Function;

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

    @Bean("allNotes")
    public NoteDispenser currencyDispenser(@Qualifier("fifty") NoteDispenser dispenseNoteFifty,
                                           @Qualifier("twenty") NoteDispenser dispenseNoteTwenty,
                                           @Qualifier("ten") NoteDispenser dispenseNoteTen,
                                           @Qualifier("five") NoteDispenser dispenseNoteFive) {
        dispenseNoteFifty.nextDispenser(dispenseNoteTwenty);
        dispenseNoteTwenty.nextDispenser(dispenseNoteTen);
        dispenseNoteTen.nextDispenser(dispenseNoteFive);

        return dispenseNoteFifty;
    }

    @Bean(name = "chainedCurrencyDispenser")
    public Function<DispenserResult_2, DispenserResult_2> chainedCurrencyDispenser(@Qualifier("fifty") NoteDispenser dispenseNoteFifty,
                                                                                   @Qualifier("twenty") NoteDispenser dispenseNoteTwenty,
                                                                                   @Qualifier("ten") NoteDispenser dispenseNoteTen,
                                                                                   @Qualifier("five") NoteDispenser dispenseNoteFive) {
        Function<DispenserResult_2, DispenserResult_2> fifty = a -> dispenseNoteFifty.dispense(a);
        Function<DispenserResult_2, DispenserResult_2> twenty = a -> dispenseNoteTwenty.dispense(a);
        Function<DispenserResult_2, DispenserResult_2> ten = a -> dispenseNoteTen.dispense(a);
        Function<DispenserResult_2, DispenserResult_2> five = a -> dispenseNoteFive.dispense(a);
        return twenty.andThen(ten).andThen(five).compose(fifty);
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