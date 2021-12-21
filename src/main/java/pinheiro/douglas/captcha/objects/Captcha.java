package pinheiro.douglas.captcha.objects;

import lombok.Data;

@Data
public class Captcha {
	private String captchaTxt;
	private String challenge;
}
