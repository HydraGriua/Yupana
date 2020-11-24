package com.acme.yupanaapi.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.repository.SellerRepository;


@Service
public class SellerDetailsService   implements UserDetailsService{

	@Autowired
	private SellerRepository sellerRepository; 
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			Optional<Seller> optional = this.sellerRepository.findByEmail(email);
			if (optional.isPresent()) {
				SellerDetails SellerDetails = new SellerDetails( optional.get() );
				return SellerDetails;
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new UsernameNotFoundException("El vendedor ingresado no existe");	
	}

}
