package com.acme.yupanaapi.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acme.yupanaapi.exception.ResourceNotFoundException;

@CrossOrigin
@Controller
public class EnviarMensajeController {

	@Autowired
	private JavaMailSender mailSender;
	@GetMapping("/mystore/enviarReporte")
	public String viewEnviarReporte() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/mystore/enviarReporte";
	}
	@PostMapping("/mystore/enviarReporte")
	public String processForgotPassword(@RequestParam("file") MultipartFile file ,HttpServletRequest request, Model model,RedirectAttributes atributes) throws MessagingException, IOException {
		   String email = request.getParameter("email");
		   System.out.println(file);
		   System.err.println(email);
		   try {
			   System.err.println("ENTRO A ENVIAR REPORTE");
			   
			   if(file==null|| file.isEmpty()) {
				   atributes.addFlashAttribute("message", " Por favor seleccione un archivo");
				   return "redirect:status";
			   }
			   
			   sendFileM(email,file);
			   model.addAttribute("message", "Su mensaje fu enviado con éxito");
		   }catch(ResourceNotFoundException  ex) {
		        model.addAttribute("error", ex.getMessage());
		   }
		    return "redirect:/mystore/clients";
	}
	public void sendFileM(String recipientEmail, MultipartFile fileT) throws MessagingException, IOException {
			    MimeMessage message = mailSender.createMimeMessage();                
			    MimeMessageHelper helper = new MimeMessageHelper(message);
			    System.err.println("Aqui el correo");
			    helper.setFrom("yupanaservice@gmail.com", "Yupana Suport");
			    helper.setTo(recipientEmail);
			    System.err.println("AQUIIII EL CORREO");
			    //helper.addAttachment(file.getName(), file);
			    String subject = "Estiamdo cliente";
			     // tranformación a archivo
			   File file = convert(fileT);
			    String content = "<p>Hola,</p>"
			            + "<p>Su vendedor le envía Aquí el recibo de su crédito</p>"
			            + "<p>Haga clic en el archivo de abajo para descargar en pdf</p>"
			            + "<p><a href=\"" + file + "\">Descargar Archivo</a></p>"
			            + "<br>"
			            + "<p>Si tiene algun inconveniente por favor llamar a su vendedor lo antes posible, "
			            + "Gracias por su preferencia. El equipo de Yupana.</p>";
			     
			    helper.setSubject(subject);
			    helper.setText(content, true);
			    mailSender.send(message);
	}
	public static File convert(MultipartFile file) throws IOException {
		 File convertedFile = new File(file.getOriginalFilename());
	        convertedFile.createNewFile();
	        FileOutputStream fos = new FileOutputStream(convertedFile);
	        fos.write(file.getBytes());
	        fos.close();
	        return convertedFile;
	}
	
}
