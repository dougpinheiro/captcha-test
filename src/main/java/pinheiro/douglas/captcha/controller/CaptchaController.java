package pinheiro.douglas.captcha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pinheiro.douglas.captcha.service.CaptchaService;

@RestController
public class CaptchaController {
	
	@Autowired
	private CaptchaService captchaService;

	@GetMapping("/captcha")
	public ResponseEntity<String> captcha() {
		String captcha = captchaService.generateCaptcha();
		return ResponseEntity.ok(captcha);
	}

	@PostMapping("/recaptcha")
	public ResponseEntity<String> captcha(@RequestParam(name = "cIn") String cIn) {
		var validated = false;
		try {
			validated = captchaService.validate(cIn);			
		} catch (Exception e) {
			return new ResponseEntity<>("Invalid Captcha", HttpStatus.UNAUTHORIZED);
		}
		return ResponseEntity.ok(String.valueOf(validated));
	}
	
}
