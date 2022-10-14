package com.thehecklers.dasbootbacking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DasBootBackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DasBootBackingApplication.class, args);
	}

}

@RestController
class BackingController {
	private final Sleeper sleeper;

	BackingController(Sleeper sleeper) {
		this.sleeper = sleeper;
	}

	@GetMapping("/pong")
	public String pong(@RequestParam String text) {
		System.out.println(text);

		sleeper.sleep();

		return "Back at you from the pong service!";
	}
}

@Component
class Sleeper {
	@NewSpan("Zzzzzzzzzzzzz")
	public void sleep() {
		try {
			Thread.sleep(1_000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}