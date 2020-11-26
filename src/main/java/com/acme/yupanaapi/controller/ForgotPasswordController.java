package com.acme.yupanaapi.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.standard.expression.MessageExpression;

import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.model.Utility;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SellerService sellerservices;

	@GetMapping("/forgot_password")
	public String showForgotPasswordForm() {
		 return "/forgot_password";
	}

	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request, Model model) throws UnsupportedEncodingException, MessagingException  {
		 String email = request.getParameter("email");
		    String token = RandomString.make(30);
		    try {
		    	System.err.println("ENTRO A OLVIDE CONTRASEÑA");
		    	 sellerservices.UpdateResetPasswordToken(token, email);
		         String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
		         sendEmail(email,resetPasswordLink);
		         model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
		         System.out.println(email);
		    } catch (ResourceNotFoundException ex) {
		        model.addAttribute("error", ex.getMessage());
		    }
		         
		    return "redirect:/reset_password_form";
	}

	public void sendEmail(String recipientEmail, String link)
	        throws MessagingException, UnsupportedEncodingException {
	    MimeMessage message = mailSender.createMimeMessage();              
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    System.err.println("Aqui el correo");
	    helper.setFrom("nicolvasquesilva@gmail", "Shopme Support");
	    helper.setTo(recipientEmail);
	     
	    String subject = "Here's the link to reset your password";
	     
	    String content = "<p>Hello,</p>"
	            + "<p>You have requested to reset your password.</p>"
	            + "<p>Click the link below to change your password:</p>"
	            + "<p><a href=\"" + link + "\">Change my password</a></p>"
	            + "<br>"
	            + "<p>Ignore this email if you do remember your password, "
	            + "or you have not made the request.</p>";
	     
	    helper.setSubject(subject);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	}

	@GetMapping("/reset_password_form")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
		Seller seller = sellerservices.getByResetPasswordToken(token);
		    model.addAttribute("token", token);
		     
		    if (seller == null) {
		    	System.err.println("ENTRO A REEVIARCONTRASEA");
		        model.addAttribute("message", "Invalid Token");
		        return "message";
		    }
		     
		    return "/reset_password_form";
	}

	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
	    String token = request.getParameter("token");
	    String password = request.getParameter("password");
	     
	    Seller seller = sellerservices.getByResetPasswordToken(token);
	    model.addAttribute("title", "Reset your password");
	     
	    if (seller == null) {
	        model.addAttribute("message", "Invalid Token");
	        return "message";
	    } else {           
	        sellerservices.UpdatePassword(seller, password);
	         
	        model.addAttribute("message", "You have successfully changed your password.");
	    }
	     
	    return "message";
	}
}
