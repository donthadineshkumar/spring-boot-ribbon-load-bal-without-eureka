package com.ribbon.lb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/*
Here, we are interacting with another service, whose instance is hard-coded generally,
instead of hard coding those - we can use ribbon to load balance among those server instances
at client side.

you can do that by using client side load balancing using spring cloud ribbon.

This is the user-service, interacts with greetings-server
user-service 8888

greetings-server 8090

 */
@SpringBootApplication
@RestController
@RibbonClient(name = "chatbook", configuration = RibbonConfiguration.class)
public class SpringBootRibbonClientLoadBalancingApplication {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Autowired
	RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRibbonClientLoadBalancingApplication.class, args);
	}

	@GetMapping("/greet")
	public String greetings(@RequestParam(value="name", defaultValue="Boss") String name) {
		//normal hard-coded url
		/*String greetings
				= restTemplate.getForObject("http://localhost:8090/greet", String.class);*/

		//with load balanced ribbon - rest template
		String greetings
				= restTemplate.getForObject("http://chatbook/greet", String.class);
		return String.format("%s, %s", greetings, name);
	}

}
