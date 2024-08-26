package br.com.webscraping.config.customgrant;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CustomUserAuthorities {

	private String username;
	private Collection<? extends GrantedAuthority> authorities;

	public CustomUserAuthorities(String username, Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.authorities = authorities;
	}

}
