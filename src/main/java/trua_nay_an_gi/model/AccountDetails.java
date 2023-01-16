package trua_nay_an_gi.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountDetails implements UserDetails {

	private Long id;
	private String username;
	private Boolean enabled;
	@JsonIgnore
	private String password;

	List<GrantedAuthority> authorities = null;

	public AccountDetails() {
	}

	public AccountDetails(Long id, String username, Boolean enabled, String password,
			List<GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.enabled = enabled;
		this.password = password;
		this.authorities = authorities;
	}

	public static AccountDetails build(Account account) {
		List<GrantedAuthority> authorities = account.getAccountRoleMapSet().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole().getName())).collect(Collectors.toList());
		return new AccountDetails(account.getId(), account.getUserName(), account.isEnabled(), account.getPassword(),
				authorities);

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
