package ai.lifo.stream.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

/**
 * @author zhanglifeng
 * @since 2024-06-27
 */

@SpringBootConfiguration
public class LoggingConsumer {

    @Bean
    public Consumer<Person> log() {
        return person -> System.out.println("Received: " + person);
    }

//    @Bean
//    public Consumer<Person> log2() {
//        return person -> System.out.println("Received2: " + person);
//    }



    @Setter
    @Getter
    public static class Person {

        private String name;

        public String toString() {
            return this.name;
        }
    }
}
