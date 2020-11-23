package com.acme.yupanaapi.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.repository.AuthorityRepository;
import com.acme.yupanaapi.domain.repository.SellerRepository;


@Service 
public class AddSellerDB implements CommandLineRunner {

	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		String password = new BCryptPasswordEncoder().encode("seller1");
		Seller seller1 = sellerRepository.findById(1).get();
		seller1.setEmail("holacomostas@gmail.com");
		seller1.setPassword( password);
		
		/*Authority authority = new Authority();   voy a considerarlo más tarde
		authority.setAuthority("ROLE_SELLER");
		authority.setSeller(seller1);
		
		authorityRepository.save(authority);*/
		
		this.sellerRepository.saveAndFlush(seller1);
	}

}
