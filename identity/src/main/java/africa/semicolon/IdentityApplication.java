package africa.semicolon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.pulsar.annotation.EnablePulsar;

@SpringBootApplication
@EnableDiscoveryClient
@EnablePulsar
public class IdentityApplication {
    public static void main(String[] args){
        SpringApplication.run(IdentityApplication.class, args);
    }
}