package com.acme.yupanaapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.repository.SellerRepository;

@Service
public class SellerDetailsImpl implements UserDetailsService {

	@Autowired
	SellerRepository sellerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Seller seller= sellerRepository.findByUsername(username);
		UserBuilder builder = null; 
		if(seller!=null) {
			builder = User.withUsername(username);
			builder.disabled(false);
			builder.password(seller.getPassword());
			builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
		}else {
			throw new UsernameNotFoundException("usuario no encontrado");
		}
		return builder.build();
	}

}
