package com.acme.yupanaapi.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.acme.yupanaapi.domain.model.Seller;

public class SellerDetails implements UserDetails{

	private static final long serialVersionUID = 1L;

	private Seller seller;
	public SellerDetails(Seller seller) {
		super();
		this.seller = seller;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		this.seller.getAuthorities().forEach(authority -> {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
			grantedAuthorities.add(grantedAuthority);
		});
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return this.seller.getPassword();
	}

	@Override
	public String getUsername() {
		return this.seller.getEmail();
	}
	
	public int getId() {
		return this.seller.getId();
	}
	
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.seller.isEnable();
	}
	
}
