package com.acme.yupanaapi.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.standard.expression.MessageExpression;

import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.model.Utility;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

import net.bytebuddy.utility.RandomString;

@CrossOrigin
@Controller
public class ForgotPasswordController {
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SellerService sellerservices;

	@GetMapping("/forgot_password")
	public String showForgotPasswordForm(Model model) {
		
		 return "/forgot_password";
	}

	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request, Model model) throws UnsupportedEncodingException, MessagingException  {
		   String email = request.getParameter("email");
		   System.err.println(request.getParameter("email"));
		   String token = RandomString.make(30);
		    try {
		    	System.err.println("ENTRO A OLVIDE CONTRASEÑA");
		    	 sellerservices.UpdateResetPasswordToken(token, email);
		         String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password_form?token=" + token;
		         sendEmail(email,resetPasswordLink);
		         model.addAttribute("message", "Enviamos un mensaje a tu correo. Chequealo*");
		         System.out.println(email + token);
		    } catch (ResourceNotFoundException ex) {
		        model.addAttribute("error", ex.getMessage());
		    }
		         
		    return "/forgot_password";
	}

	public void sendEmail(String recipientEmail, String link)
	    throws MessagingException, UnsupportedEncodingException {
	    MimeMessage message = mailSender.createMimeMessage();              
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    System.err.println("Aqui el correo");
	    helper.setFrom("yupanaservice@gmail.com", "Yupana Suport");
	    helper.setTo(recipientEmail);
	     
	    String subject = "Aquí esta el link para resetar tu contraseña";
	     
	    String content = "<p>Hola,</p>"
	            + "<p>Ha solicitado restablecer su contraseña.</p>"
	            + "<p>Haga clic en el enlace de abajo para cambiar su contraseña:</p>"
	            + "<p><a href=\"" + link + "\">Cambiar mi contraseña</a></p>"
	            + "<br>"
	            + "<p>Ignore este correo electrónico si recuerda su contraseña, "
	            + "o no ha realizado la solicitud.</p>";
	     
	    helper.setSubject(subject);
	   
	    helper.setText(content, true);
	//    FileSystemResource file = 
	  //  helper.addAttachment(file.getFilename, file);
	    mailSender.send(message);
	}
	

	@GetMapping("/reset_password_form")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
		Seller seller = sellerservices.getByResetPasswordToken(token);
		    model.addAttribute("token", token);
		     
		    if (seller == null) {
		    	System.err.println("ENTRO A REEVIARCONTRASEA");
		        model.addAttribute("message", "Su link ya no esta disponible");
		        return "/message";
		    }
		     
		    return "/reset_password_form";
	}

	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
	    String token = request.getParameter("token");
	    String password = request.getParameter("password");
	     
	    Seller seller = sellerservices.getByResetPasswordToken(token);
	    System.out.println(token);
	    model.addAttribute("title", "Resete tu password");
	     
	    if (seller == null) {
	        model.addAttribute("message", "No es Correcto");
	        return "/reset_password_form";
	    } else {           
	        sellerservices.UpdatePassword(seller, password);
	         
	        model.addAttribute("message", "Se cambio su contraseña correctamente");
	    }
	     
	    return "redirect:/login";
	}
}
