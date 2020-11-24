package com.acme.yupanaapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@Builder
@Entity
@AllArgsConstructor
@Table(name="sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @NotNull
    private String Password;

    @NotNull
    private String storeAdress;

    @NotNull
    private String businessName;

    private boolean enable;
    
    @NotNull
    private String email;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    
    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Authority> authorities;
    public void addAuthority( String auth ) {
		Authority authority = new Authority();
		authority.setAuthority( auth ) ;
		authority.setSeller( this );
		
		this.authorities.add( authority );
	}
	public Iterable<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
}
