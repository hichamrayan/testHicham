package ca.hicham.test.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ca.hicham.test.dao.UserRepository;
import ca.hicham.test.model.User;

@Component
public class AuthenticationFacade {

	@Autowired
	UserRepository userRepository;
	
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public Optional<User> getCurrentUser() {
		if(getAuthentication() == null)
			return Optional.empty();
		
		if(getAuthentication().getPrincipal() instanceof MyUserPrincipal)
			return Optional.of(((MyUserPrincipal)getAuthentication().getPrincipal()).getUser());
		return Optional.empty();
	}
	
}
