package trua_nay_an_gi.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "roles")
public class Roles implements GrantedAuthority{
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	    private String name;
	    @OneToMany(mappedBy = "role")
	    @JsonBackReference
	    private Set<AccountRoleMap> accountRoleMapSet;

	@Override
	public String getAuthority() {
		return name;
	}

	public Roles() {
	}

	public Roles(Long id, String name, Set<AccountRoleMap> accountRoleMapSet) {
		super();
		this.id = id;
		this.name = name;
		this.accountRoleMapSet = accountRoleMapSet;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	

}
