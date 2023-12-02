package com.poly.EasyLearning;

import com.poly.EasyLearning.model.MyOAuth2User;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class EasyLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyLearningApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder;
	}
}

@Controller
class test{

	@GetMapping("/login")
	public String login(){

		return "login";
	}

	@GetMapping("/result")
	public String result(@AuthenticationPrincipal MyOAuth2User oAuth2User, Model model){

		if(oAuth2User != null){
			model.addAttribute("user", oAuth2User.getName());
		}
		return "result 4:40 12/2/2023";
	}
}
