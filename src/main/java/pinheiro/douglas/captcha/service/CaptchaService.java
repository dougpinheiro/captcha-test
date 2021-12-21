package pinheiro.douglas.captcha.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import pinheiro.douglas.captcha.objects.Captcha;

@Service
@SessionScope
public class CaptchaService {

	private Captcha actualCaptcha;
	
	public String generateCaptcha() {
		var captcha = new Captcha();
		var response = "";
		try {
			 var bufferedImage = new BufferedImage(150, 40, BufferedImage.TYPE_INT_RGB);
			 Graphics g = bufferedImage.getGraphics();
			 var font1 = Font.createFont(Font.PLAIN, CaptchaService.class.getClassLoader().getResourceAsStream("fonts/kindergarten.ttf"));
			 font1 = font1.deriveFont(Font.ITALIC, 26);
			 g.setFont(font1);
			 String w1 = getWord(4);
			 String w2 = getWord(4);
			 String word = w1+" "+w2; 
			 g.drawString(word, 8, 28);
			 actualCaptcha = new Captcha();
			 actualCaptcha.setCaptchaTxt(word.trim().replace(" ", ""));
			 actualCaptcha.setChallenge(new String(Base64.getEncoder().encode(MessageDigest.getInstance("SHA-256").digest(actualCaptcha.getCaptchaTxt().getBytes())), StandardCharsets.UTF_8));
			 captcha.setCaptchaTxt(word);
			 g.setColor(Color.red);
			 g.drawLine(0, new Random().nextInt(30), 150, new Random().nextInt(30));
			 g.setColor(Color.blue);
			 g.drawLine(0, new Random().nextInt(30), 150, new Random().nextInt(30));
			 var baos = new ByteArrayOutputStream();
			 ImageIO.write(bufferedImage, "PNG", baos);
			 ImageIO.write(bufferedImage, "PNG", new File("d:/captcha.png"));			 
			 response = Base64.getEncoder().encodeToString(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) { 
			e.printStackTrace(); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private String getWord(int size) {
		String codes = "2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,j,k,m,n,p,q,r,s,t,u,v,x,y,z,A,B,C,D,E,F,G,H,J,K,M,N,P,Q,R,S,T,U,V,X,Y,Z";
		String[] l = codes.split(",");
		var sb = new StringBuilder();
		for(var i=0; i<size; i++) {
			sb.append(l[new Random().nextInt(l.length-1)]);
		}
		return sb.toString();
	}

	public boolean validate(String cIn) throws NoSuchAlgorithmException {
		String inputText = cIn.trim().replace(" ", "");
		String sFound = new String(Base64.getEncoder().encode(MessageDigest.getInstance("SHA-256").digest(inputText.getBytes())), StandardCharsets.UTF_8);
		if(sFound.equals(actualCaptcha.getChallenge())) {
			actualCaptcha = null;
			return true;
		}
		actualCaptcha = null;
		return false;
	}
	
}
