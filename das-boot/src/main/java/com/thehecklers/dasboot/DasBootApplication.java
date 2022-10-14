package com.thehecklers.dasboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Random;

@EnableScheduling
@SpringBootApplication
public class DasBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DasBootApplication.class, args);
    }

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:9090");
    }

}

@RestController
class Something {
    private final WebClient client;

    Something(WebClient client) {
        this.client = client;
    }

    @GetMapping("/ping")
    public String ping() {
        return client
                .get()
                .uri("/pong?text=Greetings from the ping service!")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

//    @Value("${bean.owner:Abc}")
//    private String beanowner;
//
//    @GetMapping("/")
//	public String getBeanOwner() {
//		return "Bean is owned by: " + beanowner;
//	}
}

//@Component
class Mayhem {
    private final Random rnd = new Random();
    private final DoNotTouch dnt;

    Mayhem(DoNotTouch dnt) {
        this.dnt = dnt;
    }

    @Scheduled(fixedRate = 1_000L)
    public void createMayhem() {
        dnt.setLastTag(rnd.nextInt(1000));
    }
}

//@Component
class Chaos {
	private final Random rnd = new Random();
    private final DoNotTouch dnt;

    Chaos(DoNotTouch dnt) {
        this.dnt = dnt;
    }

    @Scheduled(fixedRate = 1_000L)
    public void createChaos() {
        dnt.setLastTag(rnd.nextInt(1000));
    }
}

//@Component
class DoNotTouch {
    // RWX
    private int lastTag = 777;

    public int getLastTag() {
        return lastTag;
    }

    public void setLastTag(int lastTag) {
        this.lastTag = lastTag;
        System.out.println("Last Tag: " + lastTag);
    }
}