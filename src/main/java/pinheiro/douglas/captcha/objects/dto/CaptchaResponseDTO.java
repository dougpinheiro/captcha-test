package pinheiro.douglas.captcha.objects.dto;

import lombok.Data;
import pinheiro.douglas.captcha.objects.Captcha;

@Data
public class CaptchaResponseDTO extends Captcha {
	private String challengeImage;
}
