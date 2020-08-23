package ca.hicham.test.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.hicham.test.dao.UserRepository;
import ca.hicham.test.forms.Alert;
import ca.hicham.test.model.FileRecord;
import ca.hicham.test.model.User;
import ca.hicham.test.security.AuthenticationFacade;
import ca.hicham.test.security.MyPasswordEncoder;

@Controller
public class RootController {
	
	@Autowired
	AuthenticationFacade authenticationFacade;
	
	@GetMapping("/")
	String getRootPage() {
		System.out.println(authenticationFacade.getAuthentication().getClass().toString());
	  if(authenticationFacade.getAuthentication() instanceof AnonymousAuthenticationToken)
		  return "redirect:/login";
	  return "home";
	  
	}
	
	@GetMapping("/login")
	String getLoginPage() {
		return "login";
	}
	@GetMapping("/signup")
	String getSignUpPage(Model model) {
		model.addAttribute("user",new User());
		return "signup";
	}
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MyPasswordEncoder encoder;
	
	@PostMapping("/signup")
	String createUser(@ModelAttribute User user,RedirectAttributes redirectAttributes) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		redirectAttributes.addFlashAttribute("msg", new Alert("success","user created successufully"));
		return "redirect:/login"; // Envoyer un flash
	}
	
	@GetMapping("/error")
	String getError(@ModelAttribute Error error,Model model) {
		model.addAttribute("error",error);
		return "error";
	}
	
}
